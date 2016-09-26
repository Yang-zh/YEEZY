package com.fangzhich.yeezy.cart.ui;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.cart.data.entity.CartEntity;
import com.fangzhich.yeezy.cart.data.entity.CartEntity.CartItem.Option;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ShoppingCartActivity
 * Created by Khorium on 2016/9/1.
 */
public class ShoppingCartDialog {

    @OnClick(R.id.bt_cancel)
    void cancel() {
        mPopupWindow.dismiss();
    }

    @BindView(R.id.credit_card_type)
    TextView creditCardType;
    @BindView(R.id.credit_cart_number)
    TextView creditCartNumber;
    @OnClick(R.id.bt_edit)
    void editCreditCardInfo() {

    }

    @BindView(R.id.tv_item_total)
    TextView tvItemTotal;
    @BindView(R.id.tv_estimated_shipping)
    TextView tvEstimatedShipping;
    @BindView(R.id.tv_order_total)
    TextView tvOrderTotal;
    @BindView(R.id.approx)
    TextView approx;

    @OnClick(R.id.return_policy)
    void showReturnPolicy() {

    }

    @OnClick(R.id.bt_save_info)
    void saveInfo() {

    }

    @BindView(R.id.rv_shoppingCart_list)
    RecyclerView rvShoppingCartList;
    LinearLayoutManager layoutManager;
    CartListAdapter adapter;

    private PopupWindow mPopupWindow;

    private CartManager cartManager = new CartManager();

    private boolean isAddingToCart;



    public ShoppingCartDialog initPopup(Context context) {
        View mPopupContent = View.inflate(context, R.layout.dialog_shoppingcart, null);
        ButterKnife.bind(this, mPopupContent);

        mPopupWindow = new PopupWindow(mPopupContent, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        mPopupWindow.setAnimationStyle(R.style.Dialog);
        return this;
    }

    public ShoppingCartDialog addToCart(String product_id, String quantity, Option option, String recurring_id) {

        isAddingToCart = true;

        //if adding to cart, dialog will waiting for response,and load newest list after that.
        cartManager.addCartItem(product_id, quantity, option, recurring_id, new CartManager.AddItemCallBack() {

            //synchronized promise atomic
            @Override
            public synchronized void onSuccess() {
                isAddingToCart = false;
                loadData();
            }

            @Override
            public synchronized void onError(Throwable throwable) {
                isAddingToCart = false;
                loadData();
            }
        });
        return this;
    }

    public void showPopup(View contentView) {
        if (!isAddingToCart) {
            loadData();
        }
        mPopupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
    }

    private void loadData() {
        cartManager.getCartList(new CartManager.CartListCallBack() {
            @Override
            public void onSuccess(CartEntity cartList) {
                rvShoppingCartList.setLayoutManager(layoutManager);
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });
    }
}
