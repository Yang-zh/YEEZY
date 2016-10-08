package com.fangzhich.sneakerlab.order.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.order.ui.adapter.OrderHistoryAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * OrderHistoryActivity
 * Created by Khorium on 2016/9/1.
 */
public class OrderHistoryActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rv_history)
    RecyclerView recyclerView;
    OrderHistoryAdapter adapter;

    @Override
    public int setContentLayout() {
        return R.layout.activity_order_history;
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
        title.setText(R.string.OrderHistory);
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OrderHistoryAdapter();
        recyclerView.setAdapter(adapter);
        adapter.loadData();
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
