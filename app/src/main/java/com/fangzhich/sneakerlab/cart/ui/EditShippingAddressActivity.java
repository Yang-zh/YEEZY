package com.fangzhich.sneakerlab.cart.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.fangzhich.sneakerlab.App;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.base.widget.spinner.NiceSpinner;
import com.fangzhich.sneakerlab.cart.data.entity.CartEntity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ShippingInfoActivity
 * Created by Khorium on 2016/12/7.
 */
public class EditShippingAddressActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_address_bay)
    EditText etAddressBay;
    @BindView(R.id.et_address_street)
    EditText etAddressStreet;
    @BindView(R.id.et_address_unit)
    EditText etAddressUnit;
    @BindView(R.id.spinner_country)
    NiceSpinner spinnerCountry;
    @BindView(R.id.spinner_state)
    NiceSpinner spinnerState;
    @BindView(R.id.et_address_city)
    EditText etAddressCity;
    @BindView(R.id.et_address_code)
    EditText etAddressCode;
    @BindView(R.id.et_address_phone)
    EditText etAddressPhone;

    @BindView(R.id.bt_save_info)
    CardView btSaveInfo;
    @OnClick(R.id.bt_save_info)
    void saveShippingAddress() {
        if (mPaymentManger.isFirstPaying) {
            mPaymentManger.startCreditCardDialog(this,new CartEntity.Payment());
        } else {
            onBackPressed();
        }
    }

    private PaymentManager mPaymentManger;

    @Override
    public int setContentLayout() {
        return R.layout.activity_edit_shipping_info;
    }

    @Override
    protected void initContentView() {
        mPaymentManger = ((App) getApplication()).mPaymentManager;

        initToolbar();
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
}
