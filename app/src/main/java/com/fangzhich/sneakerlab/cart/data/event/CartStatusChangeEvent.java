package com.fangzhich.sneakerlab.cart.data.event;

/**
 * CartStatusChangeEvent
 * Created by Khorium on 2016/12/14.
 */
public class CartStatusChangeEvent {
    public int count;

    public CartStatusChangeEvent(int count) {
        this.count = count;
    }
}
