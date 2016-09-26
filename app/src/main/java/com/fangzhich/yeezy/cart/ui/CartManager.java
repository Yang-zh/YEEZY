package com.fangzhich.yeezy.cart.ui;

import com.fangzhich.yeezy.cart.data.entity.CartEntity;
import com.fangzhich.yeezy.cart.data.entity.CartEntity.CartItem.Option;
import com.fangzhich.yeezy.cart.presentation.CartContract;
import com.fangzhich.yeezy.cart.presentation.CartPresenter;

/**
 * CartManager
 * Created by Khorium on 2016/9/23.
 */

public class CartManager implements CartContract.View{

    private CartPresenter mPresenter;

    public CartManager() {
        setPresenter(new CartPresenter(this));
    }

    @Override
    public void setPresenter(CartContract.Presenter presenter) {
        mPresenter = (CartPresenter) presenter;
    }

    public void  getCartList(CartListCallBack callBack) {
        mCartListCallBack = callBack;
        mPresenter.getCartList();
    }

    public void addCartItem(String product_id, String quantity, Option option, String recurring_id, AddItemCallBack callBack) {
        mAddItemCallBack = callBack;
        mPresenter.addItemToCart(product_id,quantity,option,recurring_id);
    }

    public void editCartItem(String cart_id, String quantity, EditItemCallBack callBack) {
        mEditItemCallBack = callBack;
        mPresenter.editItemInCart(cart_id,quantity);
    }

    public void removeCartItem(String cart_id, RemoveItemCallBack callBack) {
        mRemoveItemCallBack = callBack;
        mPresenter.removeItemFromCart(cart_id);
    }



    // ------------Cart Operation CallBack------------

    private CartListCallBack mCartListCallBack;

    @Override
    public void onLoadCartListSuccess(CartEntity cart) {
        mCartListCallBack.onSuccess(cart);
    }

    @Override
    public void onLoadCartListFailed(Throwable throwable) {
        mCartListCallBack.onError(throwable);
    }

    @Override
    public void onLoadCartListMoreSuccess(CartEntity cart) {

    }

    private AddItemCallBack mAddItemCallBack;

    @Override
    public void onAddItemSuccess() {
        mAddItemCallBack.onSuccess();
    }

    @Override
    public void onAddItemFailed(Throwable throwable) {
        mAddItemCallBack.onError(throwable);
    }

    private EditItemCallBack mEditItemCallBack;

    @Override
    public void onEditItemSuccess() {
        mEditItemCallBack.onSuccess();
    }

    @Override
    public void onEditItemFailed(Throwable throwable) {
        mEditItemCallBack.onError(throwable);
    }

    private RemoveItemCallBack mRemoveItemCallBack;

    @Override
    public void onRemoveItemSuccess() {
        mRemoveItemCallBack.onSuccess();
    }

    @Override
    public void onRemoveItemFailed(Throwable throwable) {
        mRemoveItemCallBack.onError(throwable);
    }

    //-------------Callback interface----------

    interface CartListCallBack {
        void onSuccess(CartEntity cart);
        void onError(Throwable throwable);
    }

    interface AddItemCallBack {
        void onSuccess();
        void onError(Throwable throwable);
    }

    interface EditItemCallBack {
        void onSuccess();
        void onError(Throwable throwable);
    }

    interface RemoveItemCallBack {
        void onSuccess();
        void onError(Throwable throwable);
    }
}
