package com.fangzhich.sneakerlab.user.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.user.data.entity.UserInfoEntity;
import com.fangzhich.sneakerlab.main.ui.MainActivity;
import com.fangzhich.sneakerlab.user.data.net.UserApi;
import com.fangzhich.sneakerlab.user.presentation.contract.UserLoginContract;
import com.fangzhich.sneakerlab.user.presentation.presenter.UserLoginPresenter;
import com.fangzhich.sneakerlab.util.Const;
import com.fangzhich.sneakerlab.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Time;
import java.util.Arrays;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;
import rx.SingleSubscriber;
import timber.log.Timber;

/**
 * LoginActivity
 * Created by Khorium on 2016/9/12.
 */
public class LoginActivity extends BaseActivity implements UserLoginContract.View {

    public static final int IS_LOGIN = 100;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;

    boolean isFirstLogin;
    private FacebookLoginManager facebookLoginManager;

    @OnClick(R.id.bt_sign_in)
    void signIn() {
        mPresenter.login(email.getText().toString(), password.getText().toString());
    }

    @OnClick(R.id.forget_password)
    void forgetPassword() {
        startActivity(new Intent(this, ForgetPasswordActivity.class));
    }

    @OnClick(R.id.bt_facebook)
    void loginByFacebook() {
        facebookLoginManager.loginByFacebook(this, new FacebookLoginManager.FacebookLoginCallBack() {
            @Override
            public void onLoginSuccess(UserInfoEntity value) {
                LoginActivity.this.onLoginSuccess(value);
            }

            @Override
            public void onLoginFailed(Throwable error) {
                LoginActivity.this.onLoginFailed(error);
            }
        });
    }


    @Override
    public int setContentLayout() {
        return R.layout.activity_sign_in;
    }

    @Override
    protected void initContentView() {
        isFirstLogin = getIntent().getBooleanExtra("isFirstLogin", false);
        setPresenter(new UserLoginPresenter(this));

        facebookLoginManager = new FacebookLoginManager();
        facebookLoginManager.initFaceBookLoginCallBackManager();
        initToolbar();
    }

    UserLoginPresenter mPresenter;

    @Override
    public void setPresenter(UserLoginContract.Presenter presenter) {
        mPresenter = (UserLoginPresenter) presenter;
    }

    @Override
    public void onLoginSuccess(UserInfoEntity entity) {
        ToastUtil.toast(Const.User.LOGIN_SUCCESS);
        Const.setLogin(true,entity);
        setResult(SplashActivity.SUCCESS);
        if (isFirstLogin) {
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }

    @Override
    public void onLoginFailed(Throwable throwable) {
        ToastUtil.toast(Const.User.LOGIN_FAILED);
        Timber.e(throwable.getMessage());

    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        title.setText(R.string.SignIn);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            setResult(SplashActivity.FAILED);
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookLoginManager.onActivityResult(requestCode,resultCode,data);
    }
}
