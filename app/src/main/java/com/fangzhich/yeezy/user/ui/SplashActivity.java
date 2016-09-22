package com.fangzhich.yeezy.user.ui;

import android.content.Intent;

import com.blankj.utilcode.utils.SPUtils;
import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.base.ui.BaseActivity;
import com.fangzhich.yeezy.main.ui.MainActivity;

import butterknife.OnClick;

/**
 * SplashActivity
 * Created by Khorium on 2016/9/18.
 */
public class SplashActivity extends BaseActivity {


    @OnClick(R.id.bt_sign_up)
    void startSignUpActivity() {
        startActivity(new Intent(this,RegisterActivity.class));
    }

    @OnClick(R.id.bt_sign_in)
    void startSignInActivity() {
        startActivity(new Intent(this,LoginActivity.class));
    }

    @OnClick(R.id.skip)
    void skip() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_user_account;
    }

    @Override
    protected void initContentView() {
        if (new SPUtils(this,"App").getBoolean("isLogin", false)){
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
