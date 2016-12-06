package com.fangzhich.sneakerlab.cart.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.View;

import com.fangzhich.sneakerlab.App;
import com.fangzhich.sneakerlab.base.widget.CustomDialog;
import com.fangzhich.sneakerlab.cart.data.entity.CartEntity;
import com.fangzhich.sneakerlab.order.widget.AddressDialog;
import com.fangzhich.sneakerlab.order.widget.CreditCardDialog;
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

    private ShoppingCartDialog mCartDialog = new ShoppingCartDialog();
    private CreditCardDialog mCardDialog = new CreditCardDialog();
    private AddressDialog mAddressDialog = new AddressDialog();
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
                startCheckOut(mProduct.product_id,quantity,null,"0");
                break;
        }
    }

    public void startCheckOut(String product_id, String quantity, HashMap<String,String> option, String recurring_id) {
        ToastUtil.toast("checkout!");
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

    public void hideShoppingCartDialog() {
        mCartDialog.hide();
    }

    public void reShowShoppingCartDialog() {
        mCartDialog.show();
    }

    //-----------CreditCard---------------------

    public void startCreditCardDialog(CartEntity.Payment card) {
        mCardDialog.initPopup(this, mContext).withCreditCard(card).showPopup(mContentView);
    }

    public void hideCreditCardDialog() {
        mCardDialog.hide();
    }

    public void reShowCreditCardDialog() {
        mCardDialog.show();
    }

    //-----------Address----------------------

    public void startAddressDialog(CartEntity.Address address) {
        mAddressDialog.initPopup(this, mContext).withAddress(address).showPopup(mContentView);
    }

    public void hideAddressDialog() {
        mAddressDialog.hide();
    }

    public void reShowAddressDialog() {
        mAddressDialog.show();
    }

    //----------Size-----------------
    public void startSizeDialog(ProductEntity product, ChargeType type, String quantity) {
        mSizeDialog.initPopup(this, mContext).withProductDetail(product, type, quantity).showPopup(mContentView);
    }

    public void hideSizeDialog() {
        mSizeDialog.hide();
    }

    public void reShowSizeDialog() {
        mSizeDialog.show();
    }


    //-----------------------other===================================
    public void closeAll() {
        if (mCartDialog.isShowing()) {
            mCartDialog.dismiss();
        }
        if (mCardDialog.isShowing()) {
            mCardDialog.dismiss();
        }
        if (mAddressDialog.isShowing()) {
            mAddressDialog.dismiss();
        }
        if (mSizeDialog.isShowing()) {
            mSizeDialog.dismiss();
        }
    }

    public void closeProductDetail() {
        if (activity != null) {
            activity.finish();
        }
    }

    public void showCustomDialog(int layout, CustomDialog.Listener listener) {
        new CustomDialog().initPopup(mContext, layout, listener).showPopup(mContentView, Gravity.CENTER);
    }

    public void saveAddress(String id,String address) {
        mCartDialog.saveAddress(id,address);
    }

    public void saveCreditCard(String type, String cardNumber,String year,String month,String cvv) {
        mCartDialog.saveCreditCard(type,cardNumber,year,month,cvv);
    }

}
