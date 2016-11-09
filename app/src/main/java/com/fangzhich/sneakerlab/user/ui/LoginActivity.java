package com.fangzhich.sneakerlab.user.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.main.ui.MainActivity;
import com.fangzhich.sneakerlab.user.data.entity.UserInfoEntity;
import com.fangzhich.sneakerlab.user.presentation.contract.UserLoginContract;
import com.fangzhich.sneakerlab.user.presentation.presenter.UserLoginPresenter;
import com.fangzhich.sneakerlab.util.Const;
import com.fangzhich.sneakerlab.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

import static com.fangzhich.sneakerlab.user.ui.RegisterActivity.SUCCESS;

/**
 * LoginActivity
 * Created by Khorium on 2016/9/12.
 */
public class LoginActivity extends BaseActivity implements UserLoginContract.View {

    public static final int IS_LOGIN = 100;

    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;

    @OnClick(R.id.bt_close)
    void close() {
        finish();
    }

    @OnClick(R.id.bt_create_account)
    void createAccount() {
        startActivityForResult(new Intent(this, RegisterActivity.class), RegisterActivity.IS_REGISTER);
    }

    @OnClick(R.id.bt_forget_password)
    void forgetPassword() {
        startActivity(new Intent(this, ForgetPasswordActivity.class));
    }

    private FacebookLoginManager facebookLoginManager;

    @OnClick(R.id.bt_sign_in)
    void signIn() {
        mPresenter.login(email.getText().toString(), password.getText().toString());
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
        setPresenter(new UserLoginPresenter(this));

        facebookLoginManager = new FacebookLoginManager();
        facebookLoginManager.initFaceBookLoginCallBackManager();
    }

    UserLoginPresenter mPresenter;

    @Override
    public void setPresenter(UserLoginContract.Presenter presenter) {
        mPresenter = (UserLoginPresenter) presenter;
    }

    @Override
    public void onLoginSuccess(UserInfoEntity entity) {
        ToastUtil.toast(Const.User.LOGIN_SUCCESS);
        Const.setLogin(true, entity);
        setResult(SplashActivity.SUCCESS);
        finish();
    }

    @Override
    public void onLoginFailed(Throwable throwable) {
        ToastUtil.toast(Const.User.LOGIN_FAILED);
        Timber.e(throwable.getMessage());
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
        switch (requestCode) {
            case RegisterActivity.IS_REGISTER: {
                if (resultCode == SUCCESS) {
                    finish();
                }
            }
        }
        facebookLoginManager.onActivityResult(requestCode, resultCode, data);
    }
}
