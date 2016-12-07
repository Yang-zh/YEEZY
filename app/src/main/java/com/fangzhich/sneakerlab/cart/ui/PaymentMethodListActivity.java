package com.fangzhich.sneakerlab.cart.ui;

import android.content.Intent;
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
import butterknife.OnClick;

/**
 * ThirdPartyPaymentMethodActivity
 * Created by Khorium on 2016/12/7.
 */
public class PaymentMethodListActivity extends BaseActivity {

    public static final int SUCCESS = 1001;
    public static final int CHOOSE_PAYMENT_METHOD = 1002;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_payment_method_list)
    RecyclerView rvPaymentMethodList;

    @BindView(R.id.payment_method_3rd)
    TextView paymentMethod3rd;
    @OnClick(R.id.payment_method_3rd_layout)
    void openPaymentMethod3rdList() {
        Intent intent = new Intent(this,PaymentMethodList3rdActivity.class);
        startActivityForResult(intent,PaymentMethodList3rdActivity.CHOOSE_3rd_PAYMENT_METHOD);
    }

    private PaymentManager mPaymentManger;
    private BaseRecyclerViewAdapter paymentMethodAdapter;

    @Override
    public int setContentLayout() {
        return R.layout.activity_list_payment_method;
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
        paymentMethodAdapter = new BaseRecyclerViewAdapter<String,ViewHolder>() {
            @Override
            public void loadData() {
                //todo
            }

            @Override
            public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
                View itemView = View.inflate(parent.getContext(),R.layout.item_payment_method,null);
                return new ViewHolder(itemView){};
            }

            @Override
            protected void onBindHolder(ViewHolder holder, int position) {
                //todo
            }
        };
        rvPaymentMethodList.setLayoutManager(new LinearLayoutManager(this));
        rvPaymentMethodList.setAdapter(paymentMethodAdapter);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_type)
        ImageView cardType;
        @BindView(R.id.card_number)
        ImageView cardNumber;
        @BindView(R.id.card_date)
        ImageView cardDate;
        @BindView(R.id.bt_edit)
        ImageView btEdit;
        @BindView(R.id.bt_delete)
        ImageView btDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode!=SUCCESS) {
            return;
        }
        switch (requestCode) {
            case PaymentMethodList3rdActivity.CHOOSE_3rd_PAYMENT_METHOD:
                //todo
                break;
        }
    }
}
