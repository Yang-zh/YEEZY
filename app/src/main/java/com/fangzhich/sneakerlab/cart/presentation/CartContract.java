package com.fangzhich.sneakerlab.cart.presentation;

import com.fangzhich.sneakerlab.base.presentation.BasePresenter;
import com.fangzhich.sneakerlab.base.presentation.BaseView;
import com.fangzhich.sneakerlab.cart.data.entity.CartEntity;

import java.util.HashMap;

/**
 * CartListContract
 * Created by Khorium on 2016/9/23.
 */

public class CartContract {
    public interface Presenter extends BasePresenter {
        void getCartList();

        void addItemToCart(String product_id, String quantity, HashMap<String,String> option, String recurring_id);
        void editItemInCart(String cart_id, String quantity);
        void removeItemFromCart(String cart_id);

        void removeItemFromLaterCart(String cart_back_id);

        void moveItemFromLaterToCart(String cart_back_id);
        void moveItemFromCartToLater(String cart_id);
    }

    public interface View extends BaseView<Presenter> {
        void onLoadCartListSuccess(CartEntity cartList);
        void onLoadCartListFailed(Throwable throwable);
        void onLoadCartListMoreSuccess(CartEntity cart);

        void onAddItemSuccess();
        void onAddItemFailed(Throwable throwable);

        void onEditItemSuccess();
        void onEditItemFailed(Throwable throwable);

        void onRemoveItemSuccess();
        void onRemoveItemFailed(Throwable throwable);

        void onRemoveLaterCartItemSuccess();
        void onRemoveLaterCartItemFailed(Throwable throwable);

        void onMoveItemFromLaterToCartSuccess(CartEntity.Cart product);
        void onMoveItemFromLaterToCartFailed(Throwable throwable);

        void onMoveItemFromCartToLaterSuccess(CartEntity.CartBack cartBack);
        void onMoveItemFromCartToLaterFailed(Throwable throwable);
    }
}
