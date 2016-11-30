package com.fangzhich.ivankajingle.user.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.fangzhich.ivankajingle.R;
import com.fangzhich.ivankajingle.base.data.event.RxBus;
import com.fangzhich.ivankajingle.base.ui.BaseActivity;
import com.fangzhich.ivankajingle.base.widget.CustomDialog;
import com.fangzhich.ivankajingle.base.widget.PickerView;
import com.fangzhich.ivankajingle.main.data.event.UserInfoRefreshEvent;
import com.fangzhich.ivankajingle.user.data.entity.AvatarEntity;
import com.fangzhich.ivankajingle.user.data.entity.UserInfoEntity;
import com.fangzhich.ivankajingle.user.data.net.UserApi;
import com.fangzhich.ivankajingle.util.AvatarUtil;
import com.fangzhich.ivankajingle.util.Const;
import com.fangzhich.ivankajingle.util.ToastUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import timber.log.Timber;

/**
 * UserEditInfoActivity
 * Created by Khorium on 2016/11/9.
 */
public class UserEditInfoActivity extends BaseActivity {


    private static final int IS_CHANGE_USERNAME_SUCCESS = 1001;
    private static final int IS_CHANGE_EMAIL_SUCCESS = 1002;
    private static final int IS_CHANGE_TEL_SUCCESS = 1003;
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";

    public static final int CHANGE_SUCCESS = 2001;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.avatar)
    ImageView avatar;

    CustomDialog dialog = new CustomDialog();

    @OnClick(R.id.bt_avatar)
    void changeAvatar() {
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

    @OnClick(R.id.bt_userName)
    void changeUserName() {
        startActivityForResult(new Intent(this, ChangeNameActivity.class), IS_CHANGE_USERNAME_SUCCESS);
    }

    @BindView(R.id.sex)
    TextView sex;

    @OnClick(R.id.bt_sex)
    void changeSex() {
        final PickerView pickerView = new PickerView(this);
        final String[] genders = getResources().getStringArray(R.array.genders);
        pickerView.initPickerView(R.layout.dialog_number_view, genders, Const.getUserInfo().user_info.sex, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerView.setClickable(false);
                final int value = pickerView.getValue();
                UserInfoEntity.UserInfo userInfo = Const.getUserInfo().user_info;
                UserApi.editPersonalInfo(userInfo.firstname, userInfo.lastname, userInfo.telephone, String.valueOf(value), String.valueOf(userInfo.age), new SingleSubscriber<List>() {
                    @Override
                    public void onSuccess(List list) {
                        ToastUtil.toast("Change success!");
                        sex.setText(genders[value]);
                        Const.refreshSex(value);
                        pickerView.dismiss();
                    }

                    @Override
                    public void onError(Throwable error) {
                        pickerView.setClickable(true);
                        ToastUtil.toast(error.getMessage());
                        Timber.e(error);
                    }
                });
            }
        });
        pickerView.show();
    }

    @BindView(R.id.age)
    TextView age;

    @OnClick(R.id.bt_age)
    void changeAge() {
        final PickerView pickerView = new PickerView(this);
        String ageString = age.getText().toString();
        int ageValue = TextUtils.isEmpty(ageString) ? 1 : ageString.length() > 5 ? 1 : Integer.parseInt(ageString);
        pickerView.initPickerView(R.layout.dialog_number_view, 1, 99, ageValue == 0 ? 1 : ageValue, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerView.setClickable(false);
                final int quantity = pickerView.getValue();
                UserInfoEntity.UserInfo userInfo = Const.getUserInfo().user_info;
                UserApi.editPersonalInfo(userInfo.firstname, userInfo.lastname, userInfo.telephone, String.valueOf(userInfo.sex), String.valueOf(quantity), new SingleSubscriber<List>() {
                    @Override
                    public void onSuccess(List value) {
                        ToastUtil.toast("Change success!");
                        age.setText(String.valueOf(quantity));
                        Const.refreshAge(quantity);
                        pickerView.dismiss();
                    }

                    @Override
                    public void onError(Throwable error) {
                        pickerView.setClickable(true);
                        ToastUtil.toast(error.getMessage());
                        Timber.e(error);
                    }
                });
            }
        });
        pickerView.show();
    }

    @BindView(R.id.email)
    TextView email;

    @OnClick(R.id.bt_email)
    void changeEmail() {
        startActivityForResult(new Intent(this, ChangeEmailActivity.class), IS_CHANGE_EMAIL_SUCCESS);
    }

    @BindView(R.id.tel)
    TextView tel;

    @OnClick(R.id.bt_tel)
    void changeTel() {
        startActivityForResult(new Intent(this, ChangeTelActivity.class), IS_CHANGE_TEL_SUCCESS);
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_edit_user_info;
    }

    @Override
    protected void initContentView() {
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        title.setText(R.string.PersonalInfo);
    }

    @Override
    protected void loadData() {
        rxBus = RxBus.getDefault()
                .toObservable(UserInfoRefreshEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<UserInfoRefreshEvent>() {
                    @Override
                    public void call(UserInfoRefreshEvent userInfoRefreshEvent) {
                        refreshUserInfo();
                    }
                });
        refreshUserInfo();
    }

    private void refreshUserInfo() {
        UserInfoEntity.UserInfo userInfo = Const.getUserInfo().user_info;
        if (Const.getUserInfo().user_info.facebook_id != null) {
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
                            avatar.setImageBitmap(resource);
                            return false;
                        }
                    })
                    .into(avatar);
            if (userInfo.email.contains("fangzhi.com")) {
                email.setText("Please set your email address");
            } else {
                email.setText(userInfo.email);
            }
        } else {
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
                            avatar.setImageBitmap(resource);
                            return false;
                        }
                    })
                    .into(avatar);
            email.setText(userInfo.email);
        }
        userName.setText(Const.getUserInfo().user_info.firstname + " " + Const.getUserInfo().user_info.lastname);
        sex.setText(userInfo.sex == 0 ? "Secret" : userInfo.sex == 1 ? "Male" : "Female");
        age.setText(userInfo.age == 0 ? "Click to set your age" : String.valueOf(userInfo.age));
        tel.setText(TextUtils.isEmpty(userInfo.telephone) ? "Click to set your phone number" : userInfo.telephone);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
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
            case IS_CHANGE_EMAIL_SUCCESS:
                ToastUtil.toast(String.valueOf(resultCode));
                if (resultCode == CHANGE_SUCCESS) {
                    email.setText(Const.getUserInfo().user_info.email);
                }
                break;
            case IS_CHANGE_TEL_SUCCESS:
                if (resultCode == CHANGE_SUCCESS) {
                    tel.setText(Const.getUserInfo().user_info.telephone);
                }
                break;
            case IS_CHANGE_USERNAME_SUCCESS:
                if (resultCode == CHANGE_SUCCESS) {
                    String name = Const.getUserInfo().user_info.firstname + " " + Const.getUserInfo().user_info.lastname;
                    email.setText(name);
                }
                break;
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
