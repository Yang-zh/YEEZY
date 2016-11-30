package com.fangzhich.ivankajingle.cart.presentation;

import com.fangzhich.ivankajingle.base.presentation.BasePresenter;
import com.fangzhich.ivankajingle.base.presentation.BaseView;
import com.fangzhich.ivankajingle.cart.data.entity.CartEntity;

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
