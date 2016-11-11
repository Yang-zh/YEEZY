package com.fangzhich.sneakerlab.main.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.data.event.RxBus;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.main.data.event.UserInfoRefreshEvent;
import com.fangzhich.sneakerlab.user.ui.ChangePasswordActivity;
import com.fangzhich.sneakerlab.util.Const;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * SettingActivity
 * Created by Khorium on 2016/9/1.
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    public final static int LOG_OUT = 101;

    @OnClick(R.id.bt_currency)
    void curreny() {
        startActivity(new Intent(this, CurrencyActivity.class));
    }

    @OnClick(R.id.bt_language)
    void language() {
        startActivity(new Intent(this, LanguageActivity.class));
    }

    @OnClick(R.id.bt_return_policy)
    void returnPolicy() {
        startActivity(new Intent(this, ReturnPolicyActivity.class));
    }

    @OnClick(R.id.bt_change_password)
    void changePassword() {
        startActivity(new Intent(this, ChangePasswordActivity.class));
    }

    @OnClick(R.id.bt_logout)
    void logout() {
        Const.setLogin(false,null);
        RxBus.getDefault().post(new UserInfoRefreshEvent());
        setResult(LOG_OUT);
        finish();
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_settings;
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
        title.setText(R.string.Settings);
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
