package com.fangzhich.yeezy.user.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.base.ui.BaseActivity;
import com.fangzhich.yeezy.data.net.Bean.RegisterEntity;
import com.fangzhich.yeezy.user.presentation.contract.UserRegisterContract;
import com.fangzhich.yeezy.user.presentation.presenter.UserRegisterPresenter;
import com.fangzhich.yeezy.util.C;
import com.fangzhich.yeezy.util.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * RegisterActivity
 * Created by Khorium on 2016/9/9.
 */
public class RegisterActivity extends BaseActivity implements UserRegisterContract.View{

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
        LogUtils.getInstance().toastInfo("Join with Facebook");
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected void initContentView() {
        initToolbar();
    }



    UserRegisterPresenter mPresenter;

    @Override
    public void setPresenter(UserRegisterContract.Presenter presenter) {
        mPresenter = (UserRegisterPresenter) presenter;
    }
    @Override
    public void onRegisterSuccess(RegisterEntity entity) {
        LogUtils.toastInfo(C.User.REGISTER_SUCCESS);
    }

    @Override
    public void onRegisterFailed(Throwable throwable) {
        LogUtils.toastInfo(C.User.REGISTER_FAILED);
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
