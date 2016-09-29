package com.fangzhich.yeezy.user.ui;

import android.content.Intent;
import android.widget.TextView;

import com.blankj.utilcode.utils.SPUtils;
import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.base.ui.BaseActivity;
import com.fangzhich.yeezy.main.ui.MainActivity;
import com.fangzhich.yeezy.util.Const;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * SplashActivity
 * Created by Khorium on 2016/9/18.
 */
public class SplashActivity extends BaseActivity {


    public static final int SUCCESS = 501;
    public static final int FAILED = 502;

    @OnClick(R.id.bt_sign_up)
    void startSignUpActivity() {
        startActivityForResult(new Intent(this,RegisterActivity.class),RegisterActivity.IS_REGISTER);
    }

    @OnClick(R.id.bt_sign_in)
    void startSignInActivity() {
        Intent intent = new Intent(this,LoginActivity.class);
        intent.putExtra("isFirstLogin",true);
        startActivityForResult(intent,LoginActivity.IS_LOGIN);
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
        if (Const.isLogin()){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case LoginActivity.IS_LOGIN: {
                if (resultCode == SUCCESS) {
                    finish();
                }
            }
            case RegisterActivity.IS_REGISTER: {
                if (resultCode == SUCCESS) {
                    finish();
                }
            }
        }
    }
}
