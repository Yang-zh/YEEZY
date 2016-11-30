package com.fangzhich.ivankajingle.main.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.fangzhich.ivankajingle.R;
import com.fangzhich.ivankajingle.base.ui.BaseActivity;
import com.fangzhich.ivankajingle.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ContactActivity
 * Created by Khorium on 2016/9/1.
 */
public class ContactActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;

    @OnClick(R.id.email)
    void sendEmail() {
        ToastUtil.toast("sendEmail");
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_contact;
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
        title.setText(R.string.ContactUs);
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

