package com.fangzhich.sneakerlab.cart.ui;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangzhich.sneakerlab.App;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.base.ui.recyclerview.BaseRecyclerViewAdapter;
import com.fangzhich.sneakerlab.base.ui.recyclerview.LinearLayoutItemDecoration;
import com.fangzhich.sneakerlab.base.widget.ProgressBar;
import com.fangzhich.sneakerlab.cart.data.entity.CheckOutInfoEntity;
import com.fangzhich.sneakerlab.cart.data.entity.PlaceOrderEntity;
import com.fangzhich.sneakerlab.cart.data.net.CartApi;
import com.fangzhich.sneakerlab.order.ui.OrderConfirmedActivity;
import com.fangzhich.sneakerlab.util.TagFormatUtil;
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
import rx.SingleSubscriber;
import timber.log.Timber;

/**
 * PlaceOrderActivity
 * Created by Khorium on 2016/12/7.
 */
public class PlaceOrderActivity extends BaseActivity {


    private static final int PAYPAL_PAYMENT_REQUEST = 1234;
    private static PayPalConfiguration config = new PayPalConfiguration()

            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION)
            .clientId("AdHjPzu6SGZIwnWKNHn7nXh4DDGOcStAttubBkFnTNlFvWf0IRS5tv3IDU6tYej5HTh7arRWR63VknEu");
//            .clientId("AVODZnzbtsZpms59_ymaPSVweZxTtrir8tw058SzhtjjLdomfZAqKrG71sgvHIneqdWEwe9bQG41Szy6");

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
        Intent intent = new Intent(this, ShippingAddressListActivity.class);
        startActivityForResult(intent, ShippingAddressListActivity.CHOOSE_SHIPPING_ADDRESS);
    }

    //    @BindView(R.id.card_type)
//    TextView cardType;
    @BindView(R.id.text_credit_card)
    TextView textCreditCard;
    @BindView(R.id.card_number)
    TextView cardNumber;
    @BindView(R.id.complete_payment_info_notice)
    TextView completePaymentInfoNotice;
    @BindView(R.id.paypal_notice)
    TextView payPalNotice;


    @OnClick(R.id.payment_method_layout)
    void openPaymentMethodList() {
        Intent intent = new Intent(this, PaymentMethodListActivity.class);
        startActivityForResult(intent, PaymentMethodListActivity.CHOOSE_PAYMENT_METHOD);
    }

    @BindView(R.id.billing_address)
    TextView billingAddress;

    @OnClick(R.id.billing_address_layout)
    void openBillingAddressList() {
        Intent intent = new Intent(this, BillingAddressListActivity.class);
        startActivityForResult(intent, BillingAddressListActivity.CHOOSE_BILLING_ADDRESS);
    }

    @BindView(R.id.rv_place_order_product_list)
    RecyclerView rvPlaceOrderProductList;
    private BaseRecyclerViewAdapter<CheckOutInfoEntity.Products, ViewHolder> placeOrderProductListAdapter;


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

    final ProgressBar progressBar = ProgressBar.getInstance();

    @OnClick(R.id.bt_place_order)
    void placeOrder() {


        if (mPaymentManger.isUsingPaypal) {
            payPal();
        } else {

            progressBar.init(this, new ProgressBar.Callback() {
                @Override
                public void onProgressBarClick(View v) {

                }
            }).show();
            CartApi.placeOrder("2",checkOutInfo.address.address_id,checkOutInfo.payment.credit_id,"", new SingleSubscriber<PlaceOrderEntity>() {
                @Override
                public void onSuccess(PlaceOrderEntity value) {
                    Intent intent = new Intent(PlaceOrderActivity.this, OrderConfirmedActivity.class);
                    intent.putExtra("place_order",value);
                    startActivity(intent);
                    progressBar.cancel();
                    mPaymentManger.sendPaymentSuccessEvent();
                    finish();
                }

                @Override
                public void onError(Throwable error) {
                    progressBar.cancel();
                    ToastUtil.toast(error.getMessage());
                    Timber.e(error);
                }
            });
        }
    }

    private void payPal() {
        PayPalPayment payment = new PayPalPayment(new BigDecimal("0.01"), "USD", "sample item",
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);

        // send the same configuration for restart resiliency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        startActivityForResult(intent, PAYPAL_PAYMENT_REQUEST);
    }

    private PaymentManager mPaymentManger;
    public CheckOutInfoEntity checkOutInfo;

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

    @Override
    protected void loadData() {
        CartApi.checkOut(new SingleSubscriber<CheckOutInfoEntity>() {

            @Override
            public void onSuccess(CheckOutInfoEntity value) {
                checkOutInfo = value;
                applyData();
            }

            @Override
            public void onError(Throwable error) {
                Timber.e(error);
                ToastUtil.toast(error.getMessage());
            }
        });
    }

    private void applyData() {
        placeOrderProductListAdapter.loadData();

        fullname.setText(checkOutInfo.address.fullname);
        phone.setText(checkOutInfo.address.phone);
        street.setText(checkOutInfo.address.address_1);
        cityAndState.setText(checkOutInfo.address.city + checkOutInfo.address.suite);
        country.setText(checkOutInfo.address.country);

        if (mPaymentManger.isUsingPaypal) {
            textCreditCard.setVisibility(View.GONE);
            cardNumber.setVisibility(View.GONE);
            payPalNotice.setVisibility(View.VISIBLE);
        } else if (checkOutInfo.payment != null) {
            cardNumber.setText("****" + checkOutInfo.payment.card_number.substring(checkOutInfo.payment.card_number.length() - 3, checkOutInfo.payment.card_number.length()));
        } else {
            textCreditCard.setVisibility(View.GONE);
            cardNumber.setVisibility(View.GONE);
            completePaymentInfoNotice.setVisibility(View.VISIBLE);
        }

        shippingPrice.setText(checkOutInfo.shiping.text);
        shippingPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        realShippingPrice.setText("$0.00");
        for (CheckOutInfoEntity.Totals total : checkOutInfo.totals) {
            switch (total.title) {
                case "Sub-Total": {
                    subPrice.setText(total.text);
                    break;
                }
                case "Tax": {
                    taxPrice.setText(total.text);
                    break;
                }
                case "Total": {
                    totalPrice.setText(total.text);
                    productPrice.setText(total.text);
                    break;
                }
            }
        }
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
        placeOrderProductListAdapter = new BaseRecyclerViewAdapter<CheckOutInfoEntity.Products, ViewHolder>() {

            @Override
            public void loadData() {
                mData = checkOutInfo.products;
                notifyDataSetChanged();
            }

            @Override
            public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
                View itemView = View.inflate(parent.getContext(), R.layout.item_place_order_product_list, null);
                return new ViewHolder(itemView);
            }

            @Override
            protected void onBindHolder(ViewHolder holder, int position) {
                final CheckOutInfoEntity.Products cartItem = mData.get(position);
                holder.tvProductName.setText(cartItem.name);
                Glide.with(holder.itemView.getContext())
                        .load(cartItem.image)
                        .placeholder(R.mipmap.product_image_placeholder)
                        .fitCenter()
                        .into(holder.ivProductImage);
                holder.tvProductPrice.setText(TagFormatUtil.from(holder.itemView.getResources().getString(R.string.priceFormat))
                        .with("price", cartItem.special_price)
                        .format());
                holder.tvProductOriginalPrice.setText(TagFormatUtil.from(holder.itemView.getResources().getString(R.string.priceFormat))
                        .with("price", cartItem.original_price)
                        .format());
                holder.tvProductOriginalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                for (CheckOutInfoEntity.Products.Options option : cartItem.options) {
                    switch (option.name) {
                        case "size":
                        case "Size":
                            holder.sizeIs.setVisibility(View.VISIBLE);
                            holder.sizeDetail.setVisibility(View.VISIBLE);
                            holder.sizeDetail.setText(option.value);
                            break;
                        case "color":
                        case "Color":
                            holder.colorIs.setVisibility(View.VISIBLE);
                            holder.colorDetail.setVisibility(View.VISIBLE);
                            holder.colorDetail.setText(option.value);
                            break;
                    }
                }
                holder.quantity.setText("x" + cartItem.quantity);
            }
        };

        rvPlaceOrderProductList.setLayoutManager(new LinearLayoutManager(this));
        rvPlaceOrderProductList.addItemDecoration(new LinearLayoutItemDecoration(this, LinearLayoutItemDecoration.VERTICAL_LIST, R.drawable.background_less_half_line));
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
            ButterKnife.bind(this, itemView);
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
                onBackPressed();
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
        if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            ToastUtil.toast("invalid payment");
        }

        if (resultCode == RESULT_CANCELED) {
            progressBar.cancel();
        }

        if (resultCode != RESULT_OK) {
            return;
        }


        switch (requestCode) {
            case PAYPAL_PAYMENT_REQUEST:
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        Timber.e(confirm.toJSONObject().toString(4));
//                        ToastUtil.toast("Paypal Success");
                        // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                        // for more details.
                        progressBar.init(this, new ProgressBar.Callback() {
                            @Override
                            public void onProgressBarClick(View v) {

                            }
                        }).show();
                        CartApi.placeOrder("1",checkOutInfo.address.address_id,"",confirm.toJSONObject().toString(), new SingleSubscriber<PlaceOrderEntity>() {
                            @Override
                            public void onSuccess(PlaceOrderEntity value) {
                                Intent intent = new Intent(PlaceOrderActivity.this, OrderConfirmedActivity.class);
                                intent.putExtra("place_order",value);
                                startActivity(intent);
                                progressBar.cancel();
                                mPaymentManger.sendPaymentSuccessEvent();
                                finish();
                            }

                            @Override
                            public void onError(Throwable error) {
                                progressBar.cancel();
                                ToastUtil.toast(error.getMessage());
                                Timber.e(error);
                            }
                        });
                    } catch (JSONException e) {
                        Timber.e(e);
                    }
                }
                break;
            case PaymentMethodListActivity.CHOOSE_PAYMENT_METHOD:
                if (data!=null) {
                    checkOutInfo.payment = data.getParcelableExtra("payment");
                }
                applyData();
                break;
            case ShippingAddressListActivity.CHOOSE_SHIPPING_ADDRESS:
                if (data!=null) {
                    checkOutInfo.address = data.getParcelableExtra("address");
                }
                applyData();
                break;
            case BillingAddressListActivity.CHOOSE_BILLING_ADDRESS:
                applyData();
                break;
        }
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}