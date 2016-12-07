package com.fangzhich.sneakerlab.cart.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fangzhich.sneakerlab.App;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.base.widget.CustomDialog;
import com.fangzhich.sneakerlab.base.widget.spinner.NiceSpinner;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * PaymentMethodActivity
 * Created by Khorium on 2016/12/7.
 */
public class EditPaymentMethodActivity extends BaseActivity {

    private static final int SUCCESS = 1001;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_credit_card_number)
    EditText etCreditCardNumber;
    @BindView(R.id.et_security_code)
    EditText etSecurityCode;
    @BindView(R.id.spinner_country)
    NiceSpinner spinnerCountry;
    @BindView(R.id.spinner_state)
    NiceSpinner spinnerState;
    @BindView(R.id.et_billing_postal_code)
    EditText etBillingPostalCode;

    @BindView(R.id.icon_paypal)
    ImageView iconPaypal;
    @BindView(R.id.text_paypal)
    TextView textPaypal;
    @BindView(R.id.check_paypal)
    ImageView paypalCheck;
    @OnClick(R.id.paypal_layout)
    void chooseThirdPartyPaymentMethod() {
        mPaymentManger.showCustomDialog(this,R.layout.dialog_edit_payment_method_choose_paypal, new CustomDialog.Listener() {
            @Override
            public void onInit(final PopupWindow dialog, View content) {
                content.findViewById(R.id.bt_yes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        //todo
                        paypalCheck.setVisibility(View.VISIBLE);
                        saveOrCheckout();
                        onBackPressed();
                    }
                });
                content.findViewById(R.id.bt_no).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onDismiss(PopupWindow dialog, View content) {

            }
        });
    }

    @OnClick(R.id.bt_save_info)
    void saveOrCheckout() {

        if (!checkPaymentInfo()) {
            return;
        }

        if (mPaymentManger.isFirstPaying) {
            mPaymentManger.startPlaceOrderActivity(this);
        }
        onBackPressed();
    }

    private boolean checkPaymentInfo() {
        //todo
        return true;
    }

    private PaymentManager mPaymentManger;

    @Override
    public int setContentLayout() {
        return R.layout.activity_edit_payment_method;
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
            if (mPaymentManger.isFirstPaying)
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        switch (item.getItemId()) {
            case R.id.skip:
                mPaymentManger.showCustomDialog(this, R.layout.dialog_edit_payment_method_is_skip, new CustomDialog.Listener() {
                    @Override
                    public void onInit(final PopupWindow dialog, View content) {
                        content.findViewById(R.id.bt_yes).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                mPaymentManger.startPlaceOrderActivity(EditPaymentMethodActivity.this);
                                onBackPressed();
                            }
                        });
                        content.findViewById(R.id.bt_no).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }

                    @Override
                    public void onDismiss(PopupWindow dialog, View content) {

                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getIntent().getBooleanExtra("isFirstRegister", false)) {
            getMenuInflater().inflate(R.menu.menu_edit_payment_info, menu);
        }
        return true;
    }
}