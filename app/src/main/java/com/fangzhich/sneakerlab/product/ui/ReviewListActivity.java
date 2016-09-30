package com.fangzhich.sneakerlab.product.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.base.ui.recyclerview.LinearLayoutItemDecoration;
import com.fangzhich.sneakerlab.product.ui.adapter.ReviewListAdapter;

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
    private String mProduct_id;

    @Override
    public int setContentLayout() {
        return R.layout.activity_review_list;
    }

    @Override
    protected void initContentView() {
        mProduct_id = getIntent().getStringExtra("product_id");
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

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        ReviewListAdapter adapter = new ReviewListAdapter(mProduct_id);
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
