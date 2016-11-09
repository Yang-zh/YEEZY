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
import timber.log.Timber;

/**
 * ChangePasswordActivity
 * Created by Khorium on 2016/11/9.
 */
public class ChangePasswordActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.old_password)
    EditText oldPassword;
    @BindView(R.id.new_password)
    EditText newPassword;

    @OnClick(R.id.bt_change_password)
    void changePassword() {

        String newPassword = this.newPassword.getText().toString();

        if (newPassword.length()<6) {
            ToastUtil.toast("New password must longer than 6");
            return;
        }

        UserApi.editPassword(oldPassword.getText().toString(), this.newPassword.getText().toString(), new SingleSubscriber<Object>() {
            @Override
            public void onSuccess(Object value) {
                ToastUtil.toast("Change password success!");
                finish();
            }

            @Override
            public void onError(Throwable error) {
                ToastUtil.toast(error.getMessage());
                Timber.e(error);
            }
        });
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_change_password;
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
        title.setText(R.string.ChangePassword);
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