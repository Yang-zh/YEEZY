package com.fangzhich.sneakerlab.cart.ui;

import android.util.SparseIntArray;

import com.fangzhich.sneakerlab.cart.data.entity.CartEntity;
import com.fangzhich.sneakerlab.cart.presentation.CartContract;
import com.fangzhich.sneakerlab.cart.presentation.CartPresenter;

import java.util.ArrayList;
import java.util.HashMap;

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

    public void addCartItem(String product_id, String quantity, HashMap<String,String> option, String recurring_id, AddItemCallBack callBack) {
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

    public void removeLaterCartItem(String cart_id, RemoveLaterCartItemCallBack callBack) {
        mRemoveLaterItemCallBack = callBack;
        mPresenter.removeItemFromLaterCart(cart_id);
    }

    public void moveItemFromLaterToCart(String cart_id, MoveItemFromLaterToCartCallBack callBack) {
        moveItemFromLaterToCartCallBack = callBack;
        mPresenter.moveItemFromLaterToCart(cart_id);
    }

    public void moveItemFromCartToLater(String cart_id, MoveItemFromCartToLaterCallBack callBack) {
        moveItemFromCartToLaterCallBack = callBack;
        mPresenter.moveItemFromCartToLater(cart_id);
    }

    private CartListCallBack mCartListCallBack;
    private AddItemCallBack mAddItemCallBack;
    private EditItemCallBack mEditItemCallBack;
    private RemoveItemCallBack mRemoveItemCallBack;
    private RemoveLaterCartItemCallBack mRemoveLaterItemCallBack;
    private MoveItemFromLaterToCartCallBack moveItemFromLaterToCartCallBack;
    private MoveItemFromCartToLaterCallBack moveItemFromCartToLaterCallBack;


    // ------------Cart Operation CallBack------------


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

    @Override
    public void onAddItemSuccess() {
        mAddItemCallBack.onSuccess();
    }

    @Override
    public void onAddItemFailed(Throwable throwable) {
        mAddItemCallBack.onError(throwable);
    }

    @Override
    public void onEditItemSuccess() {
        mEditItemCallBack.onSuccess();
    }

    @Override
    public void onEditItemFailed(Throwable throwable) {
        mEditItemCallBack.onError(throwable);
    }

    @Override
    public void onRemoveItemSuccess() {
        mRemoveItemCallBack.onSuccess();
    }

    @Override
    public void onRemoveItemFailed(Throwable throwable) {
        mRemoveItemCallBack.onError(throwable);
    }

    // ---------------LaterList operation-------------------


    @Override
    public void onRemoveLaterCartItemSuccess() {
        mRemoveLaterItemCallBack.onSuccess();
    }

    @Override
    public void onRemoveLaterCartItemFailed(Throwable throwable) {
        mRemoveLaterItemCallBack.onError(throwable);
    }

    @Override
    public void onMoveItemFromLaterToCartSuccess(CartEntity.Cart product) {
        moveItemFromLaterToCartCallBack.onSuccess(product);
    }

    @Override
    public void onMoveItemFromLaterToCartFailed(Throwable throwable) {
        moveItemFromLaterToCartCallBack.onError(throwable);
    }

    @Override
    public void onMoveItemFromCartToLaterSuccess(CartEntity.CartBack cartBack) {
        moveItemFromCartToLaterCallBack.onSuccess(cartBack);
    }

    @Override
    public void onMoveItemFromCartToLaterFailed(Throwable throwable) {
        moveItemFromCartToLaterCallBack.onError(throwable);
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

    interface RemoveLaterCartItemCallBack {
        void onSuccess();
        void onError(Throwable throwable);
    }

    interface MoveItemFromLaterToCartCallBack {
        void onSuccess(CartEntity.Cart product);
        void onError(Throwable throwable);
    }

    interface MoveItemFromCartToLaterCallBack {
        void onSuccess(CartEntity.CartBack cartBack);
        void onError(Throwable throwable);
    }
}
