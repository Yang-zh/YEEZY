package com.fangzhich.sneakerlab.cart.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;

import com.fangzhich.sneakerlab.App;
import com.fangzhich.sneakerlab.base.data.event.RxBus;
import com.fangzhich.sneakerlab.base.widget.CustomDialog;
import com.fangzhich.sneakerlab.cart.data.entity.CheckOutInfoEntity;
import com.fangzhich.sneakerlab.cart.data.event.GuideFlowFinishEvent;
import com.fangzhich.sneakerlab.cart.data.event.PaymentSuccessEvent;
import com.fangzhich.sneakerlab.cart.data.net.CartApi;
import com.fangzhich.sneakerlab.product.data.entity.ProductEntity;
import com.fangzhich.sneakerlab.product.ui.SizeDialog;
import com.fangzhich.sneakerlab.util.ToastUtil;

import java.util.HashMap;

import rx.SingleSubscriber;
import timber.log.Timber;

/**
 * PaymentManager
 * <p>
 * AddCart:
 * ProductDetailActivity->startSizeDialog()->startPayment()->startShoppingCartActivity()->startCheckout()->Guide Flow(optional)->startPlaceOrder()
 * <p>
 * BuyNow:
 * ProductDetailActivity->startSizeDialog()->startPayment()->Guide Flow(optional)->startPlaceOrder()
 * <p>
 * Guide FLow:
 * startEditShippingAddressActivity()->startEditPaymentInfoActivity()(can skip)->place order...
 * <p>
 * Created by Khorium on 2016/9/29.
 */
public class PaymentManager {

    public boolean isUsingPaypal = false;

    public enum ChargeType {
        AddCart, BuyNow
    }

    private ProductEntity mProduct;

    public boolean isFirstPaying = true;


    private Context mContext;
    private View mContentView;
    private Activity activity;

    public PaymentManager(Activity context, View contentView) {
        mContentView = contentView;
        mContext = context;
        activity = context;
        ((App) activity.getApplication()).mPaymentManager = this;
    }

    private SizeDialog mSizeDialog = new SizeDialog();

    public void startPayment(ChargeType type, ProductEntity product, String quantity) {
        this.mProduct = product;
        if (product.options != null && product.options.size() != 0) {
            startSizeDialog(mProduct, type, quantity);
            return;
        }
        switch (type) {
            case AddCart:
                addItemToCartAndStartShoppingCartActivity(mProduct.product_id, quantity, null, "0");
                break;
            case BuyNow:
                startCheckOut(activity, mProduct.product_id, quantity, null, "0");
                break;
        }
    }

    public void startCheckOut(final Context context, String product_id, String quantity, HashMap<String, String> option, String recurring_id) {
        CartApi.checkOut(new SingleSubscriber<CheckOutInfoEntity>() {
            @Override
            public void onSuccess(CheckOutInfoEntity value) {
                if (value.address != null) {
                    isFirstPaying = false;
                    startPlaceOrderActivity(context);
                } else {
                    isFirstPaying = true;
                    startEditAddressActivity(context);
                }
            }

            @Override
            public void onError(Throwable error) {
                Timber.e(error);
                ToastUtil.toast(error.getMessage());
            }
        });
    }

    //------------ShoppingCart-------------------
    public void startPlaceOrderActivity(Context context) {
        if (isFirstPaying) {
            closeGuideFlow();
        }
        Intent intent = new Intent(context, PlaceOrderActivity.class);
        context.startActivity(intent);
    }
    //------------ShoppingCart-------------------

    public void startShoppingCartActivity() {
        Intent intent = new Intent(activity, ShoppingCartActivity.class);
        mContext.startActivity(intent);
    }

    public void addItemToCartAndStartShoppingCartActivity(String product_id, String quantity, HashMap<String, String> option, String recurring_id) {
        Intent intent = new Intent(activity, ShoppingCartActivity.class);
        intent.putExtra("product_id", product_id);
        intent.putExtra("quantity", quantity);
        intent.putExtra("option", option);
        intent.putExtra("recurring_id", recurring_id);
        mContext.startActivity(intent);
    }

    //-----------CreditCard---------------------

    public void startEditPaymentInfoActivity(Context context) {
        Intent intent = new Intent(context, EditPaymentMethodActivity.class);
        context.startActivity(intent);
//        mCardDialog.initPopup(this, mContext).withCreditCard(card).showPopup(mContentView);
    }

    //-----------Address----------------------

    public void startEditAddressActivity(Context context) {
        Intent intent = new Intent(context, EditShippingAddressActivity.class);
        context.startActivity(intent);
//        mAddressDialog.initPopup(this, mContext).withAddress(address).showPopup(mContentView);
    }

    //----------Size-----------------
    public void startSizeDialog(ProductEntity product, ChargeType type, String quantity) {
        mSizeDialog.initPopup(this, mContext).withProductDetail(product, type, quantity).showPopup(mContentView);
    }


    //-----------------------other===================================
    public void closeGuideFlow() {
        if (mSizeDialog.isShowing()) {
            mSizeDialog.dismiss();
        }
        RxBus.getDefault().post(new GuideFlowFinishEvent());
    }

    public void sendPaymentSuccessEvent() {
        RxBus.getDefault().post(new PaymentSuccessEvent());
    }

    public void closeProductDetail() {
        if (activity != null) {
            activity.finish();
        }
    }

    public void showCustomDialog(Activity activity, int layout, CustomDialog.Listener listener) {
        new CustomDialog().initPopup(activity, layout, listener).showPopup(activity.getWindow().getDecorView(), Gravity.CENTER);
    }
}
