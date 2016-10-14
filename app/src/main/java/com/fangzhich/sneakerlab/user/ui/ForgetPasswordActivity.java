package com.fangzhich.sneakerlab.user.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.user.data.net.UserApi;
import com.fangzhich.sneakerlab.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import rx.SingleSubscriber;

/**
 * ForgetPasswordActivity
 * Created by Khorium on 2016/9/27.
 */
public class ForgetPasswordActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_email)
    EditText etEmail;
    @OnClick(R.id.bt_submit)
    void submit() {
        UserApi.forgetPassword(etEmail.getText().toString(), new SingleSubscriber<Object>() {
            @Override
            public void onSuccess(Object value) {
                ToastUtil.toast("your new password has been sent to your email");
            }

            @Override
            public void onError(Throwable error) {
                ToastUtil.toast("can't send email,please retry");
            }
        });
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_forget_password;
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
        title.setText(R.string.ResetPassword);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
