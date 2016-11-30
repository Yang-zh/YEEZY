package com.fangzhich.ivankajingle.product.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fangzhich.ivankajingle.R;
import com.fangzhich.ivankajingle.base.ui.BaseActivity;
import com.fangzhich.ivankajingle.base.ui.recyclerview.BaseRecyclerViewAdapter;
import com.fangzhich.ivankajingle.product.data.entity.ProductEntity;
import com.fangzhich.ivankajingle.product.data.entity.ShippingInfo;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * ProductShippingInfoActivity
 * Created by Khorium on 2016/11/11.
 */

public class ProductShippingInfoActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_shipping)
    RecyclerView rvShipping;

    BaseRecyclerViewAdapter adapter;
    private ProductEntity product;

    @Override
    public int setContentLayout() {
        return R.layout.activity_product_shippinginfo;
    }

    @Override
    protected void initContentView() {
        product = getIntent().getParcelableExtra("mProduct");
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
        title.setText(R.string.ShippingSpaceInfo);
    }

    private void initRecyclerView() {
        rvShipping.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BaseRecyclerViewAdapter<ShippingInfo,RecyclerView.ViewHolder>() {
            @Override
            public void loadData() {
                ArrayList<ShippingInfo> infos = new ArrayList<>();
                infos.add(new ShippingInfo(getResources().getString(R.string.EstimatedShipping),product.shipping_info.EstimatedShipping));
                infos.add(new ShippingInfo(getResources().getString(R.string.Availability),product.shipping_info.Availability));
                infos.add(new ShippingInfo(getResources().getString(R.string.EstimatedArrival),product.shipping_info.EstimatedArrival));
                infos.add(new ShippingInfo(getResources().getString(R.string.ShipsFrom),product.shipping_info.ShipsFrom));
                infos.add(new ShippingInfo(getResources().getString(R.string.ReturnPolicy),product.shipping_info.ReturnPolicy));
                mData = infos;
            }

            @Override
            public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
                View itemView = View.inflate(parent.getContext(),R.layout.item_shipping_info,null);
                return new RecyclerView.ViewHolder(itemView) {};
            }

            @Override
            protected void onBindHolder(RecyclerView.ViewHolder holder, int position) {
                View itemView = holder.itemView;
                ((TextView)itemView.findViewById(R.id.name)).setText(mData.get(position).name);
                ((TextView)itemView.findViewById(R.id.content)).setText(mData.get(position).content);
            }
        };
        rvShipping.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        adapter.loadData();
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
