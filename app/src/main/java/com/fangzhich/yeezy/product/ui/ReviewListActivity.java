package com.fangzhich.yeezy.product.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.base.ui.BaseActivity;
import com.fangzhich.yeezy.base.ui.recyclerview.LinearLayoutItemDecoration;
import com.fangzhich.yeezy.product.ui.adapter.ReviewListAdapter;

import butterknife.BindView;

/**
 * ReviewListActivity
 * Created by Khorium on 2016/9/18.
 */
public class ReviewListActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_reviews)
    RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private ReviewListAdapter adapter;

    @Override
    public int setContentLayout() {
        return R.layout.activity_review_list;
    }

    @Override
    protected void initContentView() {
        initToolbar();
        initRecyclerView();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        title.setText(R.string.Reviews);
    }

    private void initRecyclerView() {

        manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        adapter = new ReviewListAdapter();
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new LinearLayoutItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
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
