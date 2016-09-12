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
 * SignUpActivity
 * Created by Khorium on 2016/9/9.
 */
public class SignUpActivity extends BaseActivity {

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
        LogUtils.getInstance().toastInfo("Create Account");
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
