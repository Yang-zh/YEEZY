package com.fangzhich.sneakerlab.cart.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fangzhich.sneakerlab.App;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.data.event.RxBus;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.base.widget.CustomDialog;
import com.fangzhich.sneakerlab.base.widget.ProgressBar;
import com.fangzhich.sneakerlab.base.widget.spinner.NiceSpinner;
import com.fangzhich.sneakerlab.cart.data.event.GuideFlowFinishEvent;
import com.fangzhich.sneakerlab.user.data.entity.CreditCardEntity;
import com.fangzhich.sneakerlab.user.data.net.UserApi;
import com.fangzhich.sneakerlab.util.ToastUtil;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * PaymentMethodActivity
 * Created by Khorium on 2016/12/7.
 */
public class EditPaymentMethodActivity extends BaseActivity {

    public static final int CHOOSE_PAYMENT_METHOD = 1003;
    public static final int EDIT_PAYMENT_METHOD = 1005;
    public static final int ADD_PAYMENT_METHOD = 1006;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_credit_card_number)
    EditText etCreditCardNumber;
    @BindView(R.id.et_security_code)
    EditText etSecurityCode;
    @BindView(R.id.spinner_month)
    NiceSpinner spinnerMonth;
    @BindView(R.id.spinner_year)
    NiceSpinner spinnerYear;
    @BindView(R.id.et_billing_postal_code)
    EditText etBillingPostalCode;

    @BindView(R.id.divide_layout)
    RelativeLayout divideLayout;
    @BindView(R.id.paypal_layout)
    RelativeLayout paypalLayout;

    @BindView(R.id.icon_paypal)
    ImageView iconPaypal;
    @BindView(R.id.text_paypal)
    TextView textPaypal;
    @BindView(R.id.check_paypal)
    ImageView paypalCheck;
    private CreditCardEntity card;

    @OnClick(R.id.paypal_layout)
    void chooseThirdPartyPaymentMethod() {
        mPaymentManger.showCustomDialog(this, R.layout.dialog_edit_payment_method_choose_paypal, new CustomDialog.Listener() {
            @Override
            public void onInit(final PopupWindow dialog, View content) {
                content.findViewById(R.id.bt_yes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        mPaymentManger.isUsingPaypal = true;
                        paypalCheck.setVisibility(View.VISIBLE);
                        onBackPressed();
                        if (mPaymentManger.isFirstPaying) {
                            mPaymentManger.startPlaceOrderActivity(EditPaymentMethodActivity.this);
                        } else {
                            onBackPressed();
                        }
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
        final String cardNumber = etCreditCardNumber.getText().toString();
        if (TextUtils.isEmpty(cardNumber)) {
            ToastUtil.toast("Input Card number please");
            return;
        }

        final String securityCode = etSecurityCode.getText().toString();
        if (TextUtils.isEmpty(securityCode)) {
            ToastUtil.toast("Input Security code please");
            return;
        }


        String month = spinnerMonth.getSelectedItemText();
        if (TextUtils.isEmpty(month)) {
            ToastUtil.toast("Choose month of expiry date please");
            return;
        }
        String year = spinnerYear.getSelectedItemText();
        if (TextUtils.isEmpty(year)) {
            ToastUtil.toast("Choose year of expiry date please");
            return;
        }

        String[] date = DateFormat.format("yyyy-MM", System.currentTimeMillis()).toString().split("-");

        if (Integer.valueOf(date[0]) > Integer.valueOf(year)
                || (Integer.valueOf(date[0]).equals(Integer.valueOf(year)) && Integer.valueOf(date[1]) > Integer.valueOf(month))) {
            ToastUtil.toast("Your credit card may out of date");
            return;
        }

        String zipPostalCode = etBillingPostalCode.getText().toString();
        if (TextUtils.isEmpty(zipPostalCode)) {
            ToastUtil.toast("Input Zip/PostalCode please");
        }

        final ProgressBar progressBar = ProgressBar.getInstance();
        progressBar.init(this, new ProgressBar.Callback() {
            @Override
            public void onProgressBarClick(View v) {

            }
        }).show();

        if (card == null) {
            UserApi.addCreditCard(cardNumber, month, year.substring(year.length() - 2, year.length()), securityCode, zipPostalCode, new SingleSubscriber<String>() {
                @Override
                public void onSuccess(String value) {
                    progressBar.cancel();
                    ToastUtil.toast("save credit card info success");
                }

                @Override
                public void onError(Throwable error) {
                    progressBar.cancel();
                    ToastUtil.toast(error.getMessage());
                }
            });
        } else {
            UserApi.editCreditCard(card.credit_id, cardNumber, month, year, securityCode, zipPostalCode, new SingleSubscriber<Object>() {
                @Override
                public void onSuccess(Object value) {
                    progressBar.cancel();
                    ToastUtil.toast("save credit card info success");
                }

                @Override
                public void onError(Throwable error) {
                    progressBar.cancel();
                    ToastUtil.toast(error.getMessage());
                }
            });
        }

        if (mPaymentManger.isFirstPaying) {
            mPaymentManger.startPlaceOrderActivity(this);
        } else {
            setResult(RESULT_OK);
            onBackPressed();
        }
    }

    private PaymentManager mPaymentManger;

    @Override
    public int setContentLayout() {
        return R.layout.activity_edit_payment_method;
    }

    @Override
    protected void initContentView() {
        mPaymentManger = ((App) getApplication()).mPaymentManager;

        card = getIntent().getParcelableExtra("card");

        initToolbar();

        RxBus.getDefault()
                .toObservable(GuideFlowFinishEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<GuideFlowFinishEvent>() {
                    @Override
                    public void call(GuideFlowFinishEvent guideFlowFinishEvent) {
                        onBackPressed();
                    }
                });


    }


    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            if (mPaymentManger.isFirstPaying) {
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    @Override
    protected void loadData() {
        int action = getIntent().getIntExtra("action",0);
        if (action==ADD_PAYMENT_METHOD || action == EDIT_PAYMENT_METHOD) {
            divideLayout.setVisibility(View.GONE);
            paypalLayout.setVisibility(View.GONE);
        }

        spinnerMonth.attachDataSource(Arrays.asList(getResources().getStringArray(R.array.month)));
        spinnerYear.attachDataSource(Arrays.asList(getResources().getStringArray(R.array.year)));
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
        if (mPaymentManger.isFirstPaying) {
            getMenuInflater().inflate(R.menu.menu_edit_payment_info, menu);
        }
        return true;
    }
}