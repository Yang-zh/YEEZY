package com.fangzhich.sneakerlab.cart.data.event;

import com.fangzhich.sneakerlab.cart.data.entity.CartEntity;

/**
 * MoveItemFromLaterToCartEvent
 * Created by Khorium on 2016/12/8.
 */
public class MoveItemFromLaterToCartEvent {

    public int position;
    public CartEntity.Cart cartItem;

    public MoveItemFromLaterToCartEvent(int position, CartEntity.Cart cartItem) {
        this.position = position;
        this.cartItem = cartItem;
    }
}
