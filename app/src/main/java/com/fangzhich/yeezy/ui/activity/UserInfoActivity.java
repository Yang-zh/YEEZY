package com.fangzhich.yeezy.ui.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.TextView;

import com.fangzhich.yeezy.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * UserInfoActivity
 * Created by Khorium on 2016/9/1.
 */
public class UserInfoActivity extends BaseActivity{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.tv_birthday)
    TextView birthDay;
    @OnClick(R.id.bt_birthday)
    void changeBirthday() {

    }
    @BindView(R.id.spinner_sex)
    Spinner sexSpinner;

    @OnClick(R.id.bt_complete)
    void complete() {

    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_userinfo;
    }

    @Override
    protected void initContentView() {
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        title.setText(R.string.UserInfo);
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
