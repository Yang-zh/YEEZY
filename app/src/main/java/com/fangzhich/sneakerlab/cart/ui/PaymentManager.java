package com.fangzhich.sneakerlab.cart.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;

import com.fangzhich.sneakerlab.App;
import com.fangzhich.sneakerlab.base.widget.CustomDialog;
import com.fangzhich.sneakerlab.cart.data.entity.CartEntity;
import com.fangzhich.sneakerlab.order.widget.AddressDialog;
import com.fangzhich.sneakerlab.product.data.entity.ProductEntity;
import com.fangzhich.sneakerlab.product.ui.SizeDialog;
import com.fangzhich.sneakerlab.util.ToastUtil;

import java.util.HashMap;

/**
 * PaymentManager
 * Created by Khorium on 2016/9/29.
 */

public class PaymentManager{

    private ProductEntity mProduct;
    public boolean isFirstPaying = true;

    public enum ChargeType {
        AddCart,BuyNow
    }


    private Context mContext;
    private View mContentView;
    private Activity activity;

    public PaymentManager(Activity context, View contentView) {
        mContentView = contentView;
        mContext = context;
        activity = context;
        ((App)activity.getApplication()).mPaymentManager = this;
    }
    private SizeDialog mSizeDialog = new SizeDialog();

    public void startPayment(ChargeType type, ProductEntity product, String quantity) {
        this.mProduct = product;
        if (product.options!=null && product.options.size()!=0) {
            startSizeDialog(mProduct,type,quantity);
            return;
        }
        switch (type) {
            case AddCart:
                startShoppingCartDialog(mProduct.product_id,quantity,null,"0");
                break;
            case BuyNow:
                startCheckOut(activity,mProduct.product_id,quantity,null,"0");
                break;
        }
    }

    public void startCheckOut(Context context, String product_id, String quantity, HashMap<String,String> option, String recurring_id) {
        ToastUtil.toast("checkout!");
        if (!false) {// todo check if need set payment method and shipping address info
            startAddressDialog(context, new CartEntity.Address());//todo
        } else {
            startPlaceOrderActivity(context);
        }
    }

    //------------ShoppingCart-------------------
    public void startPlaceOrderActivity(Context context) {
        Intent intent = new Intent(context, PlaceOrderActivity.class);
        context.startActivity(intent);
    }
    //------------ShoppingCart-------------------

    public void startShoppingCartDialog() {
        Intent intent = new Intent(activity,ShoppingCartActivity.class);
        mContext.startActivity(intent);
    }

    public void startShoppingCartDialog(String product_id, String quantity, HashMap<String,String> option, String recurring_id) {
        Intent intent = new Intent(activity,ShoppingCartActivity.class);
        intent.putExtra("product_id",product_id);
        intent.putExtra("quantity",quantity);
        intent.putExtra("option",option);
        intent.putExtra("recurring_id",recurring_id);
        mContext.startActivity(intent);
    }

    //-----------CreditCard---------------------

    public void startCreditCardDialog(Context context, CartEntity.Payment card) {
        Intent intent = new Intent(context,EditPaymentMethodActivity.class);
        context.startActivity(intent);
//        mCardDialog.initPopup(this, mContext).withCreditCard(card).showPopup(mContentView);
    }

    //-----------Address----------------------

    public void startAddressDialog(Context context, CartEntity.Address address) {
        Intent intent = new Intent(context,EditShippingAddressActivity.class);
        context.startActivity(intent);
//        mAddressDialog.initPopup(this, mContext).withAddress(address).showPopup(mContentView);
    }

    //----------Size-----------------
    public void startSizeDialog(ProductEntity product, ChargeType type, String quantity) {
        mSizeDialog.initPopup(this, mContext).withProductDetail(product, type, quantity).showPopup(mContentView);
    }


    //-----------------------other===================================
    public void closeAll() {
        if (mSizeDialog.isShowing()) {
            mSizeDialog.dismiss();
        }
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
