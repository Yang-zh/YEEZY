package com.fangzhich.sneakerlab.cart.ui;

import android.content.Context;
import android.view.View;

import com.fangzhich.sneakerlab.cart.data.entity.CartEntity;
import com.fangzhich.sneakerlab.order.widget.AddressDialog;
import com.fangzhich.sneakerlab.order.widget.CreditCardDialog;
import com.fangzhich.sneakerlab.product.data.entity.ProductEntity;
import com.fangzhich.sneakerlab.product.ui.ProductDetailActivity;
import com.fangzhich.sneakerlab.product.widget.SizeDialog;

import java.util.HashMap;

/**
 * DialogManager
 * Created by Khorium on 2016/9/29.
 */

public class DialogManager {

    private Context mContext;
    private View mContentView;
    private ProductDetailActivity activity;

    public DialogManager(Context context, View contentView) {
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

    //------------ShoppingCart-------------------

    public void startShoppingCartDialog() {
        mCartDialog.initPopup(this, mContext).showPopup(mSizeDialog.isShowing() ? mSizeDialog.getContentView() : mContentView);
    }

    public void startShoppingCartDialog(String product_id, String quantity, HashMap<String,String> option, String recurring_id) {
        mCartDialog.initPopup(this, mContext).addToCart(product_id, quantity, option, recurring_id).showPopup(mContentView);
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

    public void startSizeDialog() {
        mSizeDialog.initPopup(this, mContext).showPopup(mContentView);
    }

    public void startSizeDialog(ProductEntity product) {
        mSizeDialog.initPopup(this, mContext).withProductDetail(product).showPopup(mContentView);
    }

    public void hideSizeDialog() {
        mSizeDialog.hide();
    }

    public void reShowSizeDialog() {
        mSizeDialog.show();
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
        if (activity != null) {
            activity.finish();
        }
    }

    public void saveAddress(String id,String address) {
        mCartDialog.saveAddress(id,address);
    }

    public void saveCreditCard(String type, String cardNumber,String year,String month,String cvv) {
        mCartDialog.saveCreditCard(type,cardNumber,year,month,cvv);
    }

}
