package com.fangzhich.sneakerlab.user.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.base.widget.PickerView;
import com.fangzhich.sneakerlab.cart.data.net.CartApi;
import com.fangzhich.sneakerlab.user.data.entity.UserInfoEntity;
import com.fangzhich.sneakerlab.user.data.net.UserApi;
import com.fangzhich.sneakerlab.util.Const;
import com.fangzhich.sneakerlab.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.SingleSubscriber;
import timber.log.Timber;

/**
 * UserEditInfoActivity
 * Created by Khorium on 2016/11/9.
 */
public class UserEditInfoActivity extends BaseActivity {


    private static final int IS_CHANGE_USERNAME_SUCCESS = 101;
    private static final int IS_CHANGE_EMAIL_SUCCESS = 102;
    private static final int IS_CHANGE_TEL_SUCCESS = 103;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.avatar)
    ImageView avatar;

    @OnClick(R.id.bt_avatar)
    void changeAvatar() {

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

    }

    @BindView(R.id.age)
    TextView age;

    @OnClick(R.id.bt_age)
    void changeAge() {
        final PickerView pickerView = new PickerView(this);
        String ageString = age.getText().toString();
        int ageValue = TextUtils.isEmpty(ageString) ? 1 : Integer.parseInt(ageString);
        pickerView.initPickerView(R.layout.dialog_number_view, 1, 99, ageValue == 0 ? 1 : ageValue, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerView.setClickable(false);
                final int quantity = pickerView.getValue();
                pickerView.dismiss();
                UserInfoEntity.UserInfo userInfo = Const.getUserInfo().user_info;
                UserApi.editPersonalInfo(userInfo.firstname, userInfo.lastname, userInfo.telephone, String.valueOf(userInfo.sex), String.valueOf(quantity), new SingleSubscriber<List>() {
                    @Override
                    public void onSuccess(List value) {
                        ToastUtil.toast("Change age success!");
                        age.setText(quantity);
                        finish();
                    }

                    @Override
                    public void onError(Throwable error) {
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
        UserInfoEntity.UserInfo userInfo = Const.getUserInfo().user_info;
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
        userName.setText(Const.getUserInfo().user_info.firstname + " " + Const.getUserInfo().user_info.lastname);
        sex.setText(userInfo.sex == 0 ? "Secret" : userInfo.sex == 1 ? "Male" : "Female");
        age.setText(String.valueOf(userInfo.age));
        email.setText(userInfo.email);
        tel.setText(userInfo.telephone);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case IS_CHANGE_EMAIL_SUCCESS:
                if (resultCode == ChangeEmailActivity.CHANGE_SUCCESS) {
                    email.setText(data.getStringExtra("email"));
                }
                break;
            case IS_CHANGE_TEL_SUCCESS:
                if (resultCode == ChangeTelActivity.CHANGE_SUCCESS) {
                    tel.setText(data.getStringExtra("tel"));
                }
                break;
            case IS_CHANGE_USERNAME_SUCCESS:
                if (resultCode == ChangeNameActivity.CHANGE_SUCCESS) {
                    userName.setText(data.getStringExtra("name"));
                }
                break;
        }
    }
}
