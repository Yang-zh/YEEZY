package com.fangzhich.ivankajingle.user.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
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
import com.fangzhich.ivankajingle.R;
import com.fangzhich.ivankajingle.base.data.event.RxBus;
import com.fangzhich.ivankajingle.base.ui.BaseActivity;
import com.fangzhich.ivankajingle.base.ui.recyclerview.GridSpaceItemDecoration;
import com.fangzhich.ivankajingle.base.ui.recyclerview.OnScrollLoadMoreHelper;
import com.fangzhich.ivankajingle.base.widget.CustomDialog;
import com.fangzhich.ivankajingle.cart.ui.DialogManager;
import com.fangzhich.ivankajingle.main.data.event.UserInfoRefreshEvent;
import com.fangzhich.ivankajingle.main.ui.SettingActivity;
import com.fangzhich.ivankajingle.order.ui.OrderHistoryActivity;
import com.fangzhich.ivankajingle.product.ui.adapter.RelateProductListAdapter;
import com.fangzhich.ivankajingle.product.ui.adapter.ReviewListAdapter;
import com.fangzhich.ivankajingle.user.data.entity.AvatarEntity;
import com.fangzhich.ivankajingle.user.data.net.UserApi;
import com.fangzhich.ivankajingle.util.AvatarUtil;
import com.fangzhich.ivankajingle.util.Const;
import com.fangzhich.ivankajingle.util.ToastUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

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

    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";

    //head
    @BindView(R.id.headImage)
    ImageView headImage;

    CustomDialog dialog = new CustomDialog();
    @OnClick(R.id.headImageCover)
    void editAvatar() {
        dialog.initPopup(this, R.layout.dialog_choose_avatar, new CustomDialog.Listener() {
            @Override
            public void onInit(final PopupWindow dialog, View content) {
                content.findViewById(R.id.icon_gallery).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        choseHeadImageFromGallery();
                    }
                });
                content.findViewById(R.id.icon_camera).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        choseHeadImageFromCameraCapture();
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
        new DialogManager(this, getWindow().getDecorView()).startShoppingCartDialog();
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
        if (Const.getUserInfo().user_info.facebook_id!=null) {
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
        if (rxBus!=null && !rxBus.isUnsubscribed()) {
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
            case CODE_GALLERY_REQUEST:
                cropRawPhoto(data.getData());
                break;

            case CODE_CAMERA_REQUEST:
                if (AvatarUtil.hasSdcard()) {
                    File tempFile = new File(
                            Environment.getExternalStorageDirectory(),
                            IMAGE_FILE_NAME);
                    cropRawPhoto(Uri.fromFile(tempFile));
                } else {
                    Toast.makeText(getApplication(), "didn't find SD card", Toast.LENGTH_LONG)
                            .show();
                }
                break;

            case CODE_RESULT_REQUEST:
                if (data.getData() == null) {
                    ToastUtil.toast("not a valid image");
                    return;
                }
                Uri originalUri = data.getData();
                File file = AvatarUtil.getFileFromMediaUri(this, originalUri);
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

    // 从本地相册选取图片作为头像
    private void choseHeadImageFromGallery() {
        Intent intentFromGallery = new Intent();
        // 设置文件类型
        intentFromGallery.setType("image/*");
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
    }

    // 启动手机相机拍摄照片作为头像
    private void choseHeadImageFromCameraCapture() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 判断存储卡是否可用，存储照片文件
        if (AvatarUtil.hasSdcard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                    .fromFile(new File(Environment
                            .getExternalStorageDirectory(), IMAGE_FILE_NAME)));
        }

        startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
    }

    public void cropRawPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", false);
        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }
}
