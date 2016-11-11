package com.fangzhich.sneakerlab.order.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.blankj.utilcode.utils.SizeUtils;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.base.ui.recyclerview.GridSpaceItemDecoration;
import com.fangzhich.sneakerlab.base.ui.recyclerview.OnScrollLoadMoreHelper;
import com.fangzhich.sneakerlab.cart.data.entity.CartEntity;
import com.fangzhich.sneakerlab.product.ui.adapter.RelateProductListAdapter;

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
    @BindView(R.id.item_total)
    TextView itemTotal;
    @BindView(R.id.shipping)
    TextView shipping;
    @BindView(R.id.order_total)
    TextView orderTotal;
    private CartEntity cart;

    @OnClick(R.id.bt_viewOrders)
    void viewOrders() {
        startActivity(new Intent(this,OrderHistoryActivity.class));
        finish();
    }

    @OnClick(R.id.bt_continue_shipping)
    void continueShipping() {
        finish();
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
        cart = getIntent().getParcelableExtra("cart");
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
        title.setText(R.string.OrderConfirmed);
    }

    private void initRecyclerView() {
        manager = new GridLayoutManager(this, SPAN_COUNT);
        adapter = new RelateProductListAdapter();
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new GridSpaceItemDecoration(SizeUtils.dp2px(OrderConfirmedActivity.this, 8), SPAN_COUNT));
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addOnScrollListener(new OnScrollLoadMoreHelper(adapter));

    }

    @Override
    protected void loadData() {
        shipping.setText(cart.shiping.text);
        for (CartEntity.Totals total: cart.totals) {
            switch (total.title) {
                case "Sub-Total": {
                    itemTotal.setText(total.text);
                    break;
                }
                case "Total": {
                    orderTotal.setText(total.text);
                    break;
                }
            }
        }
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
