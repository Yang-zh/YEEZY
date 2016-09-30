package com.fangzhich.sneakerlab.user.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.user.data.entity.RegisterEntity;
import com.fangzhich.sneakerlab.user.presentation.contract.UserRegisterContract;
import com.fangzhich.sneakerlab.user.presentation.presenter.UserRegisterPresenter;
import com.fangzhich.sneakerlab.util.Const;
import com.fangzhich.sneakerlab.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * RegisterActivity
 * Created by Khorium on 2016/9/9.
 */
public class RegisterActivity extends BaseActivity implements UserRegisterContract.View{

    public static final int IS_REGISTER = 200;
    public static final int SUCCESS = 201;
    public static final int FAILED = 202;
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
    @BindView(R.id.confirmPassword)
    EditText confirmPassword;

    @OnClick(R.id.bt_create_account)
    void createAccount() {
        mPresenter.register(
                firstName.getText().toString(),
                lastName.getText().toString(),
                email.getText().toString(),
                password.getText().toString());
    }

    @OnClick(R.id.bt_facebook)
    void joinWithFaceBook() {
        ToastUtil.toast("Join with Facebook");
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
    public void onRegisterSuccess(RegisterEntity entity) {
        ToastUtil.toast(Const.User.REGISTER_SUCCESS);
        Const.setLogin(true);
        setResult(SplashActivity.SUCCESS);
        Intent intent = new Intent(this, UserInfoActivity.class);
        intent.putExtra("isFirstRegister",true);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRegisterFailed(Throwable throwable) {
        ToastUtil.toast(Const.User.REGISTER_FAILED);
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
            setResult(SplashActivity.FAILED);
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
