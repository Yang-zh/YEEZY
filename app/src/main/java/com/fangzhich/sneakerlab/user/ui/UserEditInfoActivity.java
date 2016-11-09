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
import com.fangzhich.sneakerlab.user.data.entity.UserInfoEntity;
import com.fangzhich.sneakerlab.util.Const;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * UserEditInfoActivity
 * Created by Khorium on 2016/11/9.
 */
public class UserEditInfoActivity extends BaseActivity {


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
        startActivity(new Intent(this,ChangeNameActivity.class));
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
        int ageValue = TextUtils.isEmpty(ageString)?1:Integer.parseInt(ageString);
        pickerView.initPickerView(R.layout.dialog_number_view, 1, 99, ageValue==0?1:ageValue, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerView.setClickable(false);
                final int quantity = pickerView.getValue();
                pickerView.dismiss();
//                CartApi.editItemInCart(cartItem.cart_id, String.valueOf(quantity), new SingleSubscriber<Object>() {
//                    @Override
//                    public void onSuccess(Object value) {
//                        pickerView.dismiss();
//                        holder.quantityDetail.setText(String.valueOf(quantity));
//                        loadData();
//                    }
//
//                    @Override
//                    public void onError(Throwable error) {
//                        ToastUtil.toast(error.getMessage());
//                    }
//                });
            }
        });
        pickerView.show();
    }

    @BindView(R.id.email)
    TextView email;
    @OnClick(R.id.bt_email)
    void changeEmail() {
        startActivity(new Intent(this,ChangeEmailActivity.class));
    }

    @BindView(R.id.tel)
    TextView tel;
    @OnClick(R.id.bt_tel)
    void changeTel() {
        startActivity(new Intent(this,ChangeTelActivity.class));
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
        sex.setText(userInfo.sex==0?"Secret":userInfo.sex==1?"Male":"Female");
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
}
