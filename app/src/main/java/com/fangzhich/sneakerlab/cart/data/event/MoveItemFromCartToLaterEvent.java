package com.fangzhich.sneakerlab.cart.data.event;

import com.fangzhich.sneakerlab.cart.data.entity.CartEntity;

/**
 * MoveItemFromLaterToCartEvent
 * Created by Khorium on 2016/12/8.
 */
public class MoveItemFromCartToLaterEvent {

    public int position;
    public CartEntity.CartBack cartItem;

    public MoveItemFromCartToLaterEvent(int position, CartEntity.CartBack cartItem) {
        this.position = position;
        this.cartItem = cartItem;
    }
}
