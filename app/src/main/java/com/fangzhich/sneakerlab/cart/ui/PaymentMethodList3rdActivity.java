package com.fangzhich.sneakerlab.cart.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fangzhich.sneakerlab.App;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.base.ui.recyclerview.BaseRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ThirdPartyPaymentMethodActivity
 * Created by Khorium on 2016/12/7.
 */
public class PaymentMethodList3rdActivity extends BaseActivity {

    public static final int CHOOSE_3rd_PAYMENT_METHOD = 1001;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_third_party_payment_method)
    RecyclerView rvThirdPartyPaymentMethod;

    private PaymentManager mPaymentManger;
    private BaseRecyclerViewAdapter thirdPartyPaymentMethodAdapter;

    @Override
    public int setContentLayout() {
        return R.layout.activity_list_payment_method_third_party;
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
        thirdPartyPaymentMethodAdapter = new BaseRecyclerViewAdapter<String,ViewHolder>() {
            @Override
            public void loadData() {
                //todo
            }

            @Override
            public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
                View itemView = View.inflate(parent.getContext(),R.layout.item_payment_method_third_party,null);
                return new ViewHolder(itemView){};
            }

            @Override
            protected void onBindHolder(ViewHolder holder, int position) {
                //todo
            }
        };
        rvThirdPartyPaymentMethod.setLayoutManager(new LinearLayoutManager(this));
        rvThirdPartyPaymentMethod.setAdapter(thirdPartyPaymentMethodAdapter);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.check_paypal)
        ImageView check;
        @BindView(R.id.icon_paypal)
        ImageView icon;
        @BindView(R.id.text_paypal)
        ImageView text;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
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
