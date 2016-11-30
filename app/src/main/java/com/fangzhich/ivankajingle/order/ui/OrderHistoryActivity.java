package com.fangzhich.ivankajingle.order.ui;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.ConvertUtils;
import com.fangzhich.ivankajingle.R;
import com.fangzhich.ivankajingle.base.ui.BaseActivity;
import com.fangzhich.ivankajingle.order.ui.adapter.OrderHistoryAdapter;
import com.fangzhich.ivankajingle.product.ui.adapter.ReviewListAdapter;

import butterknife.BindView;

/**
 * OrderHistoryActivity
 * Created by Khorium on 2016/9/1.
 */
public class OrderHistoryActivity extends BaseActivity {

    //request_code
    public static final int IS_REVIEWED = 101;
    public static final int IS_CONFIRMED = 102;
    public static final int IS_CANCELED = 103;

    //result_code
    public static final int CONFIRMED = 201;
    public static final int CANCELED = 202;
    public static final int REVIEWED = 203;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rv_history)
    RecyclerView recyclerView;
    OrderHistoryAdapter adapter;
    @BindView(R.id.no_data_notice)
    TextView noDataNotice;

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
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildAdapterPosition(view)!=0) {
                    outRect.top = ConvertUtils.dp2px(view.getContext(),5);
                }
            }
        });
        adapter = new OrderHistoryAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnAdapterStatusChangeListener(new ReviewListAdapter.OnAdapterStatusChangeListener() {
            @Override
            public void noData() {
                recyclerView.setVisibility(View.GONE);
                noDataNotice.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case IS_CANCELED:
                if (resultCode == CANCELED) {
                    adapter.notifyOrderStatusChanged(data.getStringExtra("order_id"),CANCELED);
                }
                break;
            case IS_CONFIRMED:
                if (resultCode == CONFIRMED) {
                    adapter.notifyOrderStatusChanged(data.getStringExtra("order_id"),CONFIRMED);
                }
                break;
            case IS_REVIEWED:
                if (resultCode == REVIEWED) {
                    adapter.notifyOrderStatusChanged(data.getStringExtra("order_id"),REVIEWED);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
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
