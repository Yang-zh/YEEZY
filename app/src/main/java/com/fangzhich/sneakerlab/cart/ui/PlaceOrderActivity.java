package com.fangzhich.sneakerlab.cart.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fangzhich.sneakerlab.App;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.base.ui.recyclerview.BaseRecyclerViewAdapter;
import com.fangzhich.sneakerlab.base.ui.recyclerview.LinearLayoutItemDecoration;
import com.fangzhich.sneakerlab.util.ToastUtil;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * PlaceOrderActivity
 * Created by Khorium on 2016/12/7.
 */
public class PlaceOrderActivity extends BaseActivity {


    private static PayPalConfiguration config = new PayPalConfiguration()

            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)

            .clientId("AXW45ZT-wBhh3i5k98TP3hBOPCbpQp-h8jaDfzyKHI6tPuNq2gW7EpOAyz_5bisrtpz8h-3Mep4DlgT6");

    private static final int SUCCESS = 1001;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fullname)
    TextView fullname;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.street)
    TextView street;
    @BindView(R.id.cityAndState)
    TextView cityAndState;
    @BindView(R.id.country)
    TextView country;
    @OnClick(R.id.shipping_layout)
    void openShippingAddressList() {
        Intent intent = new Intent(this,ShippingAddressListActivity.class);
        startActivityForResult(intent, ShippingAddressListActivity.CHOOSE_SHIPPING_ADDRESS);
    }

    @BindView(R.id.card_type)
    TextView cardType;
    @BindView(R.id.card_number)
    TextView cardNumber;
    @BindView(R.id.complete_payment_info_notice)
    TextView completePaymentInfoNotice;
    @OnClick(R.id.payment_method_layout)
    void openPaymentMethodList() {
        Intent intent = new Intent(this,BillingAddressListActivity.class);
        startActivityForResult(intent,BillingAddressListActivity.CHOOSE_BILLING_ADDRESS);
    }

    @BindView(R.id.billing_address)
    TextView billingAddress;
    @OnClick(R.id.billing_address_layout)
    void openBillingAddressList() {

    }

    @BindView(R.id.rv_place_order_product_list)
    RecyclerView rvPlaceOrderProductList;
    private BaseRecyclerViewAdapter<Object, ViewHolder> placeOrderProductListAdapter;


    @BindView(R.id.discount_type_short_run)
    TextView discountTypeShortRun;

    @BindView(R.id.sub_price)
    TextView subPrice;
    @BindView(R.id.shipping_price)
    TextView shippingPrice;
    @BindView(R.id.real_shipping_price)
    TextView realShippingPrice;
    @BindView(R.id.tax_price)
    TextView taxPrice;
    @BindView(R.id.total_price)
    TextView totalPrice;

    @BindView(R.id.product_price)
    TextView productPrice;

    @OnClick(R.id.bt_place_order)
    void placeOrder() {
        //todo
        payPal();
    }

    private void payPal() {
        PayPalPayment payment = new PayPalPayment(new BigDecimal("1.75"), "USD", "sample item",
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);

        // send the same configuration for restart resiliency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        startActivityForResult(intent, 0);
    }

    private PaymentManager mPaymentManger;

    @Override
    public int setContentLayout() {
        return R.layout.activity_place_order;
    }

    @Override
    protected void initContentView() {
        mPaymentManger = ((App) getApplication()).mPaymentManager;

        initPaypalService();

        initToolbar();

        initRecyclerView();
    }

    private void initPaypalService() {
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
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
        placeOrderProductListAdapter = new BaseRecyclerViewAdapter<Object,ViewHolder>() {

            @Override
            public void loadData() {
                //todo
            }

            @Override
            public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
                View itemView = View.inflate(parent.getContext(),R.layout.item_place_order_product_list,null);
                return new ViewHolder(itemView);
            }

            @Override
            protected void onBindHolder(ViewHolder holder, int position) {
                //todo
            }
        };
        rvPlaceOrderProductList.setLayoutManager(new LinearLayoutManager(this));
        rvPlaceOrderProductList.addItemDecoration(new LinearLayoutItemDecoration(this,LinearLayoutItemDecoration.VERTICAL_LIST,R.drawable.background_less_half_line));
        rvPlaceOrderProductList.setAdapter(placeOrderProductListAdapter);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_productImage)
        ImageView ivProductImage;
        @BindView(R.id.tv_productName)
        TextView tvProductName;
        @BindView(R.id.color_is)
        TextView colorIs;
        @BindView(R.id.color_detail)
        TextView colorDetail;
        @BindView(R.id.size_is)
        TextView sizeIs;
        @BindView(R.id.size_detail)
        TextView sizeDetail;
        @BindView(R.id.tv_productPrice)
        TextView tvProductPrice;
        @BindView(R.id.tv_productOriginalPrice)
        TextView tvProductOriginalPrice;
        @BindView(R.id.quantity)
        TextView quantity;

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
        switch (item.getItemId()) {
            case R.id.cancel:
                //todo
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_place_order, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        if (resultCode== PaymentActivity.RESULT_EXTRAS_INVALID) {
            ToastUtil.toast("invalid payment");
        }

        PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
        if (confirm != null) {
            try {
                Timber.e(confirm.toJSONObject().toString(4));
                ToastUtil.toast("Paypal Success");
                // TODO: send 'confirm' to your server for verification.
                // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                // for more details.

            } catch (JSONException e) {
                Timber.e(e);
            }
        }

        switch (requestCode) {
            case PaymentMethodListActivity.CHOOSE_PAYMENT_METHOD:
                break;
        }
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}