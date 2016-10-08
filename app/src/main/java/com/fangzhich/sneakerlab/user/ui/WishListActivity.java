package com.fangzhich.sneakerlab.user.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.user.ui.adapter.WishListAdapter;

import butterknife.BindView;

/**
 * WishListActivity
 * Created by Khorium on 2016/9/28.
 */

public class WishListActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rv_wish_list)
    RecyclerView recyclerView;
    private WishListAdapter adapter;


    @Override
    public int setContentLayout() {
        return R.layout.activity_wish_list;
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
        title.setText(R.string.Cerf);
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new WishListAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
