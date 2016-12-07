package com.fangzhich.sneakerlab.cart.ui;

import android.media.Image;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fangzhich.sneakerlab.App;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.base.ui.recyclerview.BaseRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ShippingAddressListActivity
 * Created by Khorium on 2016/12/7.
 */
public class ShippingAddressListActivity extends BaseActivity {

    public static final int CHOOSE_SHIPPING_ADDRESS = 1001;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_shipping_address_list)
    RecyclerView rvShippingAddressList;

    private PaymentManager mPaymentManger;
    private BaseRecyclerViewAdapter shippingAddressAdapter;

    @Override
    public int setContentLayout() {
        return R.layout.activity_list_shipping_address;
    }

    @Override
    protected void initContentView() {
        mPaymentManger = ((App) getApplication()).mPaymentManager;

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
    }

    private void initRecyclerView() {
        shippingAddressAdapter = new BaseRecyclerViewAdapter<String, ViewHolder>() {
            @Override
            public void loadData() {
                //todo
            }

            @Override
            public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
                View itemView = View.inflate(parent.getContext(), R.layout.item_shipping_address_list, null);
                return new ViewHolder(itemView) {
                };
            }

            @Override
            protected void onBindHolder(ViewHolder holder, int position) {
                //todo
            }
        };
        rvShippingAddressList.setLayoutManager(new LinearLayoutManager(this));
        rvShippingAddressList.setAdapter(shippingAddressAdapter);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.icon_check)
        ImageView check;
        @BindView(R.id.fullname)
        TextView fullname;
        @BindView(R.id.street)
        TextView street;
        @BindView(R.id.cityAndState)
        TextView cityAndState;
        @BindView(R.id.country)
        TextView country;
        @BindView(R.id.bt_delete)
        TextView btDelete;
        @BindView(R.id.bt_edit)
        TextView btEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
