package com.fangzhich.yeezy.user.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.base.ui.BaseActivity;
import com.fangzhich.yeezy.user.data.entity.UserInfoEntity;
import com.fangzhich.yeezy.main.ui.MainActivity;
import com.fangzhich.yeezy.user.presentation.contract.UserLoginContract;
import com.fangzhich.yeezy.user.presentation.presenter.UserLoginPresenter;
import com.fangzhich.yeezy.util.Const;
import com.fangzhich.yeezy.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * LoginActivity
 * Created by Khorium on 2016/9/12.
 */
public class LoginActivity extends BaseActivity implements UserLoginContract.View{

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

    @OnClick(R.id.bt_sign_in)
    void signIn() {
        mPresenter.login(email.getText().toString(),password.getText().toString());
    }

    @OnClick(R.id.forget_password)
    void forgetPassword() {
        startActivity(new Intent(this,ForgetPasswordActivity.class));
    }

    @OnClick(R.id.bt_facebook)
    void signInWithFacebook() {
        mPresenter.loginByFaceBook();
    }


    @Override
    public int setContentLayout() {
        return R.layout.activity_sign_in;
    }

    @Override
    protected void initContentView() {
        isFirstLogin = getIntent().getBooleanExtra("isFirstLogin",false);
        setPresenter(new UserLoginPresenter(this));
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
        Const.setUserInfo(entity);
        Const.setLogin(true);
        if (isFirstLogin) {
            setResult(SplashActivity.SUCCESS);
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

}
