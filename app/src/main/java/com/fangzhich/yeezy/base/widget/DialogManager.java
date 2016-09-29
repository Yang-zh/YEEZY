package com.fangzhich.yeezy.base.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.fangzhich.yeezy.cart.ui.ShoppingCartDialog;
import com.fangzhich.yeezy.order.widget.AddressDialog;
import com.fangzhich.yeezy.order.widget.CreditCardDialog;
import com.fangzhich.yeezy.product.data.entity.ProductEntity;
import com.fangzhich.yeezy.product.ui.ProductDetailActivity;
import com.fangzhich.yeezy.product.widget.SizeDialog;

import java.util.ArrayList;

/**
 * DialogManager
 * Created by Khorium on 2016/9/29.
 */

public class DialogManager {

    Context mContext;
    View mContentView;
    ProductDetailActivity activity;

    public DialogManager(Context context,View contentView) {
        mContentView = contentView;
        mContext = context;
    }

    private ShoppingCartDialog mCartDialog = new ShoppingCartDialog();
    private CreditCardDialog mCardDialog = new CreditCardDialog();
    private AddressDialog mAddressDialog = new AddressDialog();
    private SizeDialog mSizeDialog = new SizeDialog();

    public DialogManager withProductDetailControl(ProductDetailActivity activity) {
        this.activity = activity;
        return this;
    }

    public void startShoppingCartDialog() {
        mCartDialog.initPopup(this,mContext).showPopup(mSizeDialog.isShowing()?mSizeDialog.getContentView():mContentView);
    }

    public void startShoppingCartDialog(String product_id, String quantity, ArrayList<Integer> option,String recurring_id) {
        mCartDialog.initPopup(this,mContext).addToCart(product_id,quantity,option,recurring_id).showPopup(mContentView);
    }

    public void startCreditCardDialog() {
        mCardDialog.initPopup(this,mContext).showPopup(mContentView);
    }

    public void startAddressDialog() {
        mAddressDialog.initPopup(this,mContext).showPopup(mContentView);
    }

    public void startSizeDialog() {
        mSizeDialog.initPopup(this,mContext).showPopup(mContentView);
    }

    public void startSizeDialog(ProductEntity product) {
        mSizeDialog.initPopup(this,mContext).withProductDetail(product).showPopup(mContentView);
    }


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
        if (activity!=null) {
            activity.finish();
        }
    }
}
