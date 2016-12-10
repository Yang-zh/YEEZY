package com.fangzhich.sneakerlab.cart.ui;

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
 * BillingAddressListActivity
 * Created by Khorium on 2016/12/7.
 */
public class BillingAddressListActivity extends BaseActivity {

    public static final int CHOOSE_BILLING_ADDRESS = 1005;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_billing_address_list)
    RecyclerView rvBillingAddressList;

    private PaymentManager mPaymentManger;
    private BaseRecyclerViewAdapter billingAddressAdapter;

    @Override
    public int setContentLayout() {
        return R.layout.activity_list_billing_address;
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
        billingAddressAdapter = new BaseRecyclerViewAdapter<String, ViewHolder>() {
            @Override
            public void loadData() {
                //todo
            }

            @Override
            public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
                View itemView = View.inflate(parent.getContext(), R.layout.item_billing_address_list, null);
                return new ViewHolder(itemView) {
                };
            }

            @Override
            protected void onBindHolder(ViewHolder holder, int position) {
                //todo
            }
        };
        rvBillingAddressList.setLayoutManager(new LinearLayoutManager(this));
        rvBillingAddressList.setAdapter(billingAddressAdapter);
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
