package com.fangzhich.sneakerlab.cart.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.fangzhich.sneakerlab.App;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;

import butterknife.BindView;

/**
 * ShippingInfoActivity
 * Created by Khorium on 2016/12/7.
 */
public class EditBillingAddressActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private PaymentManager mPaymentManger;

    @Override
    public int setContentLayout() {
        return R.layout.activity_edit_billing_info;
    }

    @Override
    protected void initContentView() {
        mPaymentManger = ((App)getApplication()).mPaymentManager;

        initToolbar();
    }


    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
