package com.fangzhich.sneakerlab.cart.data.event;

/**
 * CartItemQuantityChangeEvent
 * Created by Khorium on 2016/10/18.
 */
public class CartItemQuantityChangeEvent {
    private String cart_id;
    private int quantity;

    public CartItemQuantityChangeEvent(String cart_id, int quantity) {
        this.cart_id = cart_id;
        this.quantity = quantity;
    }
}
