package com.fangzhich.ivankajingle.cart.data.event;

/**
 * CartItemRemoveEvent
 * Created by Khorium on 2016/10/18.
 */
public class CartItemRemoveEvent {

    public String cart_id;

    public CartItemRemoveEvent(String cart_id) {
        this.cart_id = cart_id;
    }
}
