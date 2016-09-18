package com.fangzhich.yeezy.user.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.utils.SPUtils;
import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.base.ui.BaseActivity;
import com.fangzhich.yeezy.user.data.entity.LoginEntity;
import com.fangzhich.yeezy.main.ui.MainActivity;
import com.fangzhich.yeezy.user.presentation.contract.UserLoginContract;
import com.fangzhich.yeezy.user.presentation.presenter.UserLoginPresenter;
import com.fangzhich.yeezy.util.Constants;
import com.fangzhich.yeezy.util.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * LoginActivity
 * Created by Khorium on 2016/9/12.
 */
public class LoginActivity extends BaseActivity implements UserLoginContract.View{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @OnClick(R.id.bt_sign_in)

    void signIn() {
        mPresenter.login(email.getText().toString(),password.getText().toString());
    }
    @OnClick(R.id.forget_password)
    void forgetPassword() {
        mPresenter.forgetPassword();
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
        setPresenter(new UserLoginPresenter(this));
        initToolbar();
    }


    UserLoginPresenter mPresenter;

    @Override
    public void setPresenter(UserLoginContract.Presenter presenter) {
        mPresenter = (UserLoginPresenter) presenter;
    }

    @Override
    public void onLoginSuccess(LoginEntity entity) {
        LogUtils.toastInfo(Constants.User.LOGIN_SUCCESS);
        new SPUtils(this,"YEEZY").putBoolean("isLogin",true);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onLoginFailed(Throwable throwable) {
        LogUtils.toastInfo(Constants.User.LOGIN_FAILED);
        LogUtils.logTestError("onLoginFailed",throwable.getMessage());
        throwable.printStackTrace();

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
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
