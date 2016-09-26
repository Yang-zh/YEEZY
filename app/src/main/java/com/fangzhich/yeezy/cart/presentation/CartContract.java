package com.fangzhich.yeezy.cart.presentation;

import com.fangzhich.yeezy.base.presentation.BasePresenter;
import com.fangzhich.yeezy.base.presentation.BaseView;
import com.fangzhich.yeezy.cart.data.entity.CartEntity;
import com.fangzhich.yeezy.cart.data.entity.CartEntity.CartItem.Option;

/**
 * CartListContract
 * Created by Khorium on 2016/9/23.
 */

public class CartContract {
    public interface Presenter extends BasePresenter {
        void getCartList();

        void addItemToCart(String product_id, String quantity, Option option, String recurring_id);

        void editItemInCart(String cart_id, String quantity);

        void removeItemFromCart(String cart_id);
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
    }
}
