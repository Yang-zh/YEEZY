package com.fangzhich.sneakerlab.user.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.utils.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.data.event.RxBus;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.base.ui.recyclerview.GridSpaceItemDecoration;
import com.fangzhich.sneakerlab.base.ui.recyclerview.OnScrollLoadMoreHelper;
import com.fangzhich.sneakerlab.base.widget.CustomDialog;
import com.fangzhich.sneakerlab.cart.ui.PaymentManager;
import com.fangzhich.sneakerlab.main.data.event.UserInfoRefreshEvent;
import com.fangzhich.sneakerlab.main.ui.SettingActivity;
import com.fangzhich.sneakerlab.order.ui.OrderHistoryActivity;
import com.fangzhich.sneakerlab.product.ui.adapter.RelateProductListAdapter;
import com.fangzhich.sneakerlab.product.ui.adapter.ReviewListAdapter;
import com.fangzhich.sneakerlab.user.data.entity.AvatarEntity;
import com.fangzhich.sneakerlab.user.data.net.UserApi;
import com.fangzhich.sneakerlab.util.AvatarUtil;
import com.fangzhich.sneakerlab.util.Const;
import com.fangzhich.sneakerlab.util.ToastUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import timber.log.Timber;

/**
 * PersonalCenterActivity
 * Created by Khorium on 2016/9/28.
 */

public class PersonalCenterActivity extends BaseActivity {

    private static final int PERSONAL_CENTER = 101;
    //title
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;

    private static final int CODE_PERMISSION_REQUEST = 0xa4;

    //head
    @BindView(R.id.headImage)
    ImageView headImage;

    CustomDialog dialog = new CustomDialog();

    @OnClick(R.id.headImageCover)
    void editAvatar() {
        if (Build.VERSION.SDK_INT >= 23) {
            ArrayList<String> permissions = new ArrayList<>();
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (permissions.size() != 0) {
                Timber.e(Arrays.toString(permissions.toArray()));
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_PERMISSION_REQUEST);
//                (String[]) permissions.toArray()
            } else {
                initImageDialog();
            }
        } else {
            initImageDialog();
        }
    }

    private void initImageDialog() {
        dialog.initPopup(this, R.layout.dialog_choose_avatar, new CustomDialog.Listener() {
            @Override
            public void onInit(final PopupWindow dialog, View content) {
                content.findViewById(R.id.icon_gallery).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AvatarUtil.choseHeadImageFromGallery(PersonalCenterActivity.this);
                    }
                });
                content.findViewById(R.id.icon_camera).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AvatarUtil.choseHeadImageFromCameraCapture(PersonalCenterActivity.this);
                    }
                });
                content.findViewById(R.id.bt_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onDismiss(PopupWindow dialog, View content) {

            }
        }).showPopup(getWindow().getDecorView(), Gravity.BOTTOM);
    }

    @BindView(R.id.userName)
    TextView userName;

    @OnClick(R.id.edit_info)
    void editInfo() {
        startActivity(new Intent(this, UserEditInfoActivity.class));
    }

    //list
    @OnClick(R.id.shoppingCart)
    void shoppingCart() {
        new PaymentManager(this, getWindow().getDecorView()).startShoppingCartActivity();
    }

    @OnClick(R.id.orderHistory)
    void orderHistory() {
        startActivity(new Intent(this, OrderHistoryActivity.class));
    }

    @OnClick(R.id.wishList)
    void wishlist() {
        startActivity(new Intent(this, WishListActivity.class));
    }


    //content
    @BindView(R.id.rv_related)
    RecyclerView recyclerView;
    private RelateProductListAdapter adapter;

    private boolean isInit;

    //no data view
    @BindView(R.id.no_data_notice)
    RelativeLayout noData;
    @BindView(R.id.content_view)
    RelativeLayout contentView;
    @BindView(R.id.continue_shopping)
    TextView continueShopping;


    @Override
    public int setContentLayout() {
        return R.layout.activity_person_center;
    }

    @Override
    protected void initContentView() {
        initToolbar();
        initRecyclerView();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        title.setText(getResources().getString(R.string.MyLab));
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new RelateProductListAdapter();
        recyclerView.addItemDecoration(new GridSpaceItemDecoration(SizeUtils.dp2px(this, 8), 2));
        recyclerView.addOnScrollListener(new OnScrollLoadMoreHelper(adapter));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
        adapter.setOnAdapterStatusChangeListener(new ReviewListAdapter.OnAdapterStatusChangeListener() {
            @Override
            public void noData() {
                recyclerView.setVisibility(View.GONE);
                noData.setVisibility(View.VISIBLE);
            }
        });
        continueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void loadData() {
        refreshUserInfo();
        rxBus = RxBus.getDefault()
                .toObservable(UserInfoRefreshEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<UserInfoRefreshEvent>() {
                    @Override
                    public void call(UserInfoRefreshEvent userInfoRefreshEvent) {
                        refreshUserInfo();
                    }
                });
        isInit = true;
    }

    private void refreshUserInfo() {
        if (Const.getUserInfo().user_info.facebook_id != null) {
            Timber.d(Const.getUserInfo().user_info.avatarimage);
            Glide.with(this)
                    .load(Const.getUserInfo().user_info.avatarimage)
                    .asBitmap()
                    .placeholder(R.mipmap.head_image_place_holder)
                    .fitCenter()
                    .listener(new RequestListener<String, Bitmap>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            headImage.setImageBitmap(resource);
                            return false;
                        }
                    })
                    .into(headImage);
        } else {
            Timber.d(Const.getUserInfo().user_info.avatar);
            Glide.with(this)
                    .load(Const.getUserInfo().user_info.avatar)
                    .asBitmap()
                    .placeholder(R.mipmap.head_image_place_holder)
                    .fitCenter()
                    .listener(new RequestListener<String, Bitmap>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            headImage.setImageBitmap(resource);
                            return false;
                        }
                    })
                    .into(headImage);
        }
        userName.setText(Const.getUserInfo().user_info.firstname + " " + Const.getUserInfo().user_info.lastname);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isInit) {
            adapter.loadData();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_person_center, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.settings:
                startActivityForResult(new Intent(this, SettingActivity.class), PERSONAL_CENTER);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    Subscription rxBus;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (rxBus != null && !rxBus.isUnsubscribed()) {
            rxBus.unsubscribe();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        switch (requestCode) {
            case PERSONAL_CENTER:
                if (resultCode == SettingActivity.LOG_OUT) {
                    finish();
                    break;
                }
            case CODE_PERMISSION_REQUEST:
                initImageDialog();
                break;
            case AvatarUtil.CODE_GALLERY_REQUEST:
                AvatarUtil.cropRawPhoto(PersonalCenterActivity.this,data.getData());
                break;
            case AvatarUtil.REQUEST_PICK_IMAGE_KITKAT:
                AvatarUtil.cropRawPhoto(PersonalCenterActivity.this,data.getData());
                break;
            case AvatarUtil.CODE_CAMERA_REQUEST:
                if (AvatarUtil.hasSdcard()) {
                    File tempFile = new File(
                            Environment.getExternalStorageDirectory(),
                            AvatarUtil.IMAGE_FILE_NAME);
                    AvatarUtil.cropRawPhoto(PersonalCenterActivity.this,Uri.fromFile(tempFile));
                } else {
                    Toast.makeText(getApplication(), "didn't find SD card", Toast.LENGTH_LONG)
                            .show();
                }
                break;

            case AvatarUtil.CODE_RESULT_REQUEST:
//                if (data.getData()==null) {
//                    ToastUtil.toast("invalid image");
//                    return;
//                }
                File file = AvatarUtil.getFileFromMediaUri(this,AvatarUtil.tempUri);
                Bitmap photoBmp = null;
                try {
                    photoBmp = AvatarUtil.getBitmapFormUri(this, Uri.fromFile(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int degree = AvatarUtil.getBitmapDegree(file != null ? file.getAbsolutePath() : null);
                Bitmap photo = AvatarUtil.rotateBitmapByDegree(photoBmp, degree);

                ByteArrayOutputStream output = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100, output);
                photo.recycle();

                byte[] result = output.toByteArray();

                sendEditAvatarRequest(result);

                try {
                    output.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void sendEditAvatarRequest(byte[] result) {
        UserApi.editAvatar(result, new SingleSubscriber<AvatarEntity>() {
            @Override
            public void onSuccess(AvatarEntity value) {
                ToastUtil.toast("upload Avatar success!");
                Const.refreshAvatar(value.avatar);
                dialog.dismiss();
            }

            @Override
            public void onError(Throwable error) {
                Timber.d(error);
                ToastUtil.toast(error.getMessage());
            }
        });
    }

}
