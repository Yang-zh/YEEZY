package com.fangzhich.yeezy.ui.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.util.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * SignInActivity
 * Created by Khorium on 2016/9/12.
 */
public class SignInActivity extends BaseActivity {

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
        LogUtils.getInstance().toastInfo("Sign in");
    }
    @OnClick(R.id.forget_password)
    void forgetPassword() {
        LogUtils.getInstance().toastInfo("Forget Password");
    }
    @OnClick(R.id.bt_facebook)
    void signInWithFacebook() {
        LogUtils.getInstance().toastInfo("Sign in with Facebook");
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_sign_in;
    }

    @Override
    protected void initContentView() {
        initToolbar();
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
    protected void loadData() {

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
