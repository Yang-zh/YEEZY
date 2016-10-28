package com.fangzhich.sneakerlab.user.ui;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.main.ui.MainActivity;
import com.fangzhich.sneakerlab.util.Const;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * SplashActivity
 * Created by Khorium on 2016/9/18.
 */
public class SplashActivity extends BaseActivity {

    @BindView(R.id.splash)
    ImageView splash;
    @OnClick(R.id.splash)
    void onSplashClick() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("fromSplash",true);
        startActivity(intent);
        finish();
    }

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
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Const.isLogin()){
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                } else {
                    splash.setVisibility(View.GONE);
                }
            }
        },3000);
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
