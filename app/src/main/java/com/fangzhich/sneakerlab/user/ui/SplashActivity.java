package com.fangzhich.sneakerlab.user.ui;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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
    @BindView(R.id.root_view)
    RelativeLayout rootView;

    @OnClick(R.id.splash)
    void onSplashClick() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("fromSplash", true);
        startActivity(intent);
        finish();
    }

    public static final int SUCCESS = 501;
    public static final int FAILED = 502;

    @OnClick(R.id.bt_sign_up)
    void startSignUpActivity() {
        startActivityForResult(new Intent(this, RegisterActivity.class), RegisterActivity.IS_REGISTER);
    }

    @OnClick(R.id.bt_sign_in)
    void startSignInActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("isFirstLogin", true);
        startActivityForResult(intent, LoginActivity.IS_LOGIN);
    }

    @OnClick(R.id.skip)
    void skip() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public int setContentLayout() {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_user_account;
    }

    @Override
    protected void initContentView() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Const.isLogin()) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                } else {
//                    splash.setVisibility(View.GONE);
                    rootView.setVisibility(View.VISIBLE);
                    splash.setVisibility(View.GONE);
                }
            }
        }, 2500);
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
