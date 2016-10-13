package com.fangzhich.sneakerlab.cart.presentation;

import android.util.SparseIntArray;

import com.fangzhich.sneakerlab.cart.data.entity.CartEntity;
import com.fangzhich.sneakerlab.cart.data.net.CartApi;

import java.util.ArrayList;
import java.util.HashMap;

import rx.SingleSubscriber;

/**
 * CartPresenter
 * Created by Khorium on 2016/9/23.
 */

public class CartPresenter implements CartContract.Presenter{


    private final CartContract.View mView;

    public CartPresenter(CartContract.View view) {
        mView = view;
    }

    @Override
    public void getCartList() {
        CartApi.getCartList(new SingleSubscriber<CartEntity>() {
            @Override
            public void onSuccess(CartEntity cart) {
                mView.onLoadCartListSuccess(cart);
            }

            @Override
            public void onError(Throwable throwable) {
                mView.onLoadCartListFailed(throwable);
            }
        });
    }

    @Override
    public void addItemToCart(String product_id, String quantity, HashMap<String,String> option, String recurring_id) {
        CartApi.addItemToCart(product_id, quantity, option, recurring_id, new SingleSubscriber<Object>() {
            @Override
            public void onSuccess(Object value) {
                mView.onAddItemSuccess();
            }

            @Override
            public void onError(Throwable throwable) {
                mView.onAddItemFailed(throwable);
            }
        });
    }

    @Override
    public void editItemInCart(String cart_id, String quantity) {
        CartApi.editItemInCart(cart_id, quantity, new SingleSubscriber<Object>() {
            @Override
            public void onSuccess(Object value) {
                mView.onEditItemSuccess();
            }

            @Override
            public void onError(Throwable throwable) {
                mView.onEditItemFailed(throwable);
            }
        });
    }

    @Override
    public void removeItemFromCart(String cart_id) {
        CartApi.removeItemFromCart(cart_id, new SingleSubscriber<Object>() {
            @Override
            public void onSuccess(Object value) {
                mView.onRemoveItemSuccess();
            }

            @Override
            public void onError(Throwable throwable) {
                mView.onRemoveItemFailed(throwable);
            }
        });
    }
}
