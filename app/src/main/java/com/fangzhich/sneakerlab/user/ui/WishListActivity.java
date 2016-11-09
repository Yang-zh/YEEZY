package com.fangzhich.sneakerlab.user.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.SizeUtils;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.base.ui.recyclerview.GridSpaceItemDecoration;
import com.fangzhich.sneakerlab.base.ui.recyclerview.OnScrollLoadMoreHelper;
import com.fangzhich.sneakerlab.product.ui.adapter.ReviewListAdapter;
import com.fangzhich.sneakerlab.user.ui.adapter.WishListAdapter;

import butterknife.BindView;

/**
 * WishListActivity
 * Created by Khorium on 2016/10/14.
 */

public class WishListActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rv_related)
    RecyclerView recyclerView;
    @BindView(R.id.no_data_notice)
    RelativeLayout noDataNotice;
    @BindView(R.id.continue_shopping)
    TextView continueShopping;
    private WishListAdapter adapter;

    private boolean isInit = false;


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
        title.setText(getResources().getString(R.string.Collection));
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new WishListAdapter();
        recyclerView.addItemDecoration(new GridSpaceItemDecoration(SizeUtils.dp2px(this,8),2));
        recyclerView.addOnScrollListener(new OnScrollLoadMoreHelper(adapter));
        recyclerView.setAdapter(adapter);
        adapter.setOnAdapterStatusChangeListener(new ReviewListAdapter.OnAdapterStatusChangeListener() {
            @Override
            public void noData() {
                recyclerView.setVisibility(View.GONE);
                noDataNotice.setVisibility(View.VISIBLE);
            }
        });
        continueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void loadData() {
        isInit = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isInit) {
            adapter.loadData();
        }
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
