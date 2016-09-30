package com.fangzhich.sneakerlab.cart.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.widget.DialogManager;
import com.fangzhich.sneakerlab.cart.data.entity.CartEntity;
import com.fangzhich.sneakerlab.main.ui.ReturnPolicyActivity;
import com.fangzhich.sneakerlab.order.ui.OrderConfirmedActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ShoppingCartActivity
 * Created by Khorium on 2016/9/1.
 */
public class ShoppingCartDialog {

    private Context mContext;
    private View mContentView;
    private DialogManager manager;

    @OnClick(R.id.bt_cancel)
    void cancel() {
        mPopupWindow.dismiss();
        manager.reShowSizeDialog();
    }

    //----------address dialog-------------
    @BindView(R.id.ship_layout)
    RelativeLayout shipLayout;
    @BindView(R.id.address)
    TextView address;
    @OnClick(R.id.bt_address_edit)
    void editAddress() {
        manager.startAddressDialog();
        manager.hideShoppingCartDialog();
    }

    //----------credit card dialog-------------
    @BindView(R.id.bill_layout)
    RelativeLayout billLayout;
    @BindView(R.id.credit_card_type)
    TextView creditCardType;
    @BindView(R.id.credit_cart_number)
    TextView creditCartNumber;
    @OnClick(R.id.bt_card_edit)
    void editCreditCardInfo() {
        manager.startCreditCardDialog();
        manager.hideShoppingCartDialog();
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
        mContext.startActivity(new Intent(mContext, ReturnPolicyActivity.class));
    }

    @OnClick(R.id.bt_checkout)
    void checkout() {
        Intent intent = new Intent(mContext, OrderConfirmedActivity.class);
        intent.putParcelableArrayListExtra("cartList", (ArrayList<CartEntity.CartItem>) adapter.getData());
        mContext.startActivity(intent);
        manager.closeAll();
        manager.closeProductDetail();
    }

    @BindView(R.id.rv_shoppingCart_list)
    RecyclerView recyclerView;

    private CartListAdapter adapter = new CartListAdapter();

    private PopupWindow mPopupWindow;

    private CartManager cartManager = new CartManager();

    private boolean isAddingToCart;

    private View mPopupContent;


    public ShoppingCartDialog initPopup(DialogManager manager, Context context) {
        this.manager = manager;
        mContext = context;
        mPopupContent = View.inflate(context, R.layout.dialog_shoppingcart, null);
        ButterKnife.bind(this, mPopupContent);

        mPopupWindow = new PopupWindow(mPopupContent, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        mPopupWindow.setAnimationStyle(R.style.Dialog);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        return this;
    }

    public ShoppingCartDialog addToCart(String product_id, String quantity, ArrayList<Integer> option, String recurring_id) {

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

    private void loadData() {
        recyclerView.setAdapter(adapter);
        adapter.loadData();
        adapter.setOnLoadDataListener(new CartListAdapter.OnLoadDataListener() {
            @Override
            public void loadCartData(CartEntity cart) {
                tvEstimatedShipping.setText("$0");
                for (CartEntity.Totals total: cart.totals) {
                    switch (total.title) {
                        case "Sub-Total": {
                            tvItemTotal.setText(total.text);
                            break;
                        }
                        case "Total": {
                            tvOrderTotal.setText(total.text);
                            break;
                        }
                        case "VAT (20%)": {
                            approx.setText(total.text);
                            break;
                        }
                    }
                }
            }
        });

    }

    public void showPopup(View contentView) {
        mContentView = contentView;
        if (!isAddingToCart) {
            loadData();
        }
        mPopupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
    }

    public boolean isShowing() {
        if (mPopupWindow == null) {
            return false;
        }
        return mPopupWindow.isShowing();
    }

    public void dismiss() {
        mPopupWindow.dismiss();
    }

    public View getContentView() {
        return mPopupContent;
    }


    public void hide() {
        if (mPopupWindow != null) {
            mPopupWindow.getContentView().setVisibility(View.GONE);
        }
    }

    public void show() {
        if (mPopupWindow != null) {
            mPopupWindow.getContentView().setVisibility(View.VISIBLE);
        }
    }
}
