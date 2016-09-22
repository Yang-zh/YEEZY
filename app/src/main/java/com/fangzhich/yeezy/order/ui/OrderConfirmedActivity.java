package com.fangzhich.yeezy.order.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.blankj.utilcode.utils.SizeUtils;
import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.base.ui.BaseActivity;
import com.fangzhich.yeezy.base.ui.recyclerview.GridSpaceItemDecoration;
import com.fangzhich.yeezy.base.ui.recyclerview.OnScrollLoadMoreHelper;
import com.fangzhich.yeezy.product.ui.adapter.RelateProductListAdapter;
import com.fangzhich.yeezy.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * OrderConfirmedActivity
 * Created by Khorium on 2016/9/12.
 */
public class OrderConfirmedActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.brand)
    TextView brand;
    @BindView(R.id.item_total)
    TextView itemTotal;
    @BindView(R.id.shipping)
    TextView shipping;
    @BindView(R.id.order_total)
    TextView orderTotal;

    @OnClick(R.id.bt_viewOrders)
    void viewOrders() {

    }

    @OnClick(R.id.bt_continue_shipping)
    void continueShipping() {

    }

    @BindView(R.id.rv_related_list)
    RecyclerView recyclerView;
    RelateProductListAdapter adapter;
    GridLayoutManager manager;
    private final static int SPAN_COUNT = 2;

    @Override
    public int setContentLayout() {
        return R.layout.activity_order_confirmed;
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
        title.setText(R.string.app_name);
    }

    private void initRecyclerView() {
        manager = new GridLayoutManager(this, SPAN_COUNT);
        adapter = new RelateProductListAdapter();
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new GridSpaceItemDecoration(SizeUtils.dp2px(OrderConfirmedActivity.this, 8), SPAN_COUNT));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new OnScrollLoadMoreHelper(adapter));

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
        switch (item.getItemId()) {
            case R.id.skip:
                ToastUtil.toast("Skip");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_skip, menu);
        return true;
    }
}
