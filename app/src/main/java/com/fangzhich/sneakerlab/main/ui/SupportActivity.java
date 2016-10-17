package com.fangzhich.sneakerlab.main.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * SupportActivity
 * Created by Khorium on 2016/9/1.
 */
public class SupportActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.support_intro)
    TextView supportIntro;
    @BindView(R.id.rv_support_record_list)
    RecyclerView rvSupportRecordList;
    private SupportListAdapter adapter;

    @Override
    public int setContentLayout() {
        return R.layout.activity_support;
    }

    @Override
    protected void initContentView() {
        initToolbar();
        initRecyclerView();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        title.setText(R.string.CustomerSupport);
    }

    private void initRecyclerView() {
        rvSupportRecordList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SupportListAdapter();
    }

    @Override
    protected void loadData() {
        adapter.loadData();
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
