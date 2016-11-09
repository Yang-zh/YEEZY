package com.fangzhich.sneakerlab.user.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.user.data.entity.RegisterEntity;
import com.fangzhich.sneakerlab.user.data.entity.UserInfoEntity;
import com.fangzhich.sneakerlab.user.data.net.UserApi;
import com.fangzhich.sneakerlab.user.presentation.contract.UserRegisterContract;
import com.fangzhich.sneakerlab.user.presentation.presenter.UserRegisterPresenter;
import com.fangzhich.sneakerlab.util.Const;
import com.fangzhich.sneakerlab.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import rx.SingleSubscriber;
import timber.log.Timber;

/**
 * RegisterActivity
 * Created by Khorium on 2016/9/9.
 */
public class RegisterActivity extends BaseActivity implements UserRegisterContract.View{

    public static final int IS_REGISTER = 200;
    public static final int SUCCESS = 201;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.first_name)
    EditText firstName;
    @BindView(R.id.last_name)
    EditText lastName;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.bt_create_account)
    CardView btCreateAccount;
    @OnClick(R.id.bt_create_account)
    void createAccount() {
        btCreateAccount.setClickable(false);
        mPresenter.register(
                firstName.getText().toString(),
                lastName.getText().toString(),
                email.getText().toString(),
                password.getText().toString());
    }
    @Override
    public int setContentLayout() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected void initContentView() {
        setPresenter(new UserRegisterPresenter(this));
        initToolbar();
    }



    UserRegisterPresenter mPresenter;

    @Override
    public void setPresenter(UserRegisterContract.Presenter presenter) {
        mPresenter = (UserRegisterPresenter) presenter;
    }

    @Override
    public void onRegisterSuccess(final RegisterEntity entity) {
        ToastUtil.toast(Const.User.REGISTER_SUCCESS);
        UserApi.login(email.getText().toString(), password.getText().toString(), new SingleSubscriber<UserInfoEntity>() {
            @Override
            public void onSuccess(UserInfoEntity value) {
                Timber.d(value.toString());
                Const.setLogin(true,value);
                setResult(SUCCESS);
                finish();
            }

            @Override
            public void onError(Throwable error) {
                Timber.e(error);
                btCreateAccount.setClickable(true);
                ToastUtil.toastLong("sorry,register success but try login failed,please restart application and login by yourself");
            }
        });
    }

    @Override
    public void onRegisterFailed(Throwable throwable) {
        ToastUtil.toast(Const.User.REGISTER_FAILED);
        btCreateAccount.setClickable(true);
    }


    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        title.setText(R.string.CreateAccount);
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
