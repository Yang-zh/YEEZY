package com.fangzhich.sneakerlab.cart.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fangzhich.sneakerlab.App;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.base.ui.recyclerview.LinearLayoutItemDecoration;
import com.fangzhich.sneakerlab.cart.data.entity.CartEntity;
import com.fangzhich.sneakerlab.util.TagFormatUtil;
import com.fangzhich.sneakerlab.util.ToastUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * ShoppingCartActivity
 * Created by Khorium on 2016/12/6.
 */

public class ShoppingCartActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.shoppingCartNotice)
    TextView shoppingCartNotice;
    @BindView(R.id.rv_checkout_list)
    RecyclerView rvCheckoutList;

    @BindView(R.id.no_data_notice)
    RelativeLayout noDataNotice;
    @BindView(R.id.continue_shopping)
    TextView continueShopping;

    @BindView(R.id.later_for_shopping)
    TextView laterForShopping;
    @BindView(R.id.rv_later_list)
    RecyclerView rvLaterList;

    @BindView(R.id.product_price)
    TextView productPrice;
    @BindView(R.id.product_price_original)
    TextView productPriceOriginal;

    @BindView(R.id.bt_checkout)
    CardView btCheckout;

    @OnClick(R.id.bt_checkout)
    void checkOut() {
        mPaymentManger.startCheckOut(this,"","",null,"");
    }

    private PaymentManager mPaymentManger;
    private CartManager cartManager = new CartManager();
    private boolean isAddingToCart;

    CartListAdapter checkoutListAdapter = new CartListAdapter();
//    CartListAdapter laterListAdapter = new CartListAdapter();

    @Override
    public int setContentLayout() {
        return R.layout.activity_shoppingcart;
    }

    @Override
    protected void initContentView() {
        mPaymentManger = ((App)getApplication()).mPaymentManager;

        initToolbar();

        checkIfNeedAddToCart();

        initRecyclerView();

        initOthers();
    }


    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initRecyclerView() {
        rvCheckoutList.setLayoutManager(new LinearLayoutManager(this));
        rvCheckoutList.setNestedScrollingEnabled(false);
        rvCheckoutList.addItemDecoration(new LinearLayoutItemDecoration(this,LinearLayoutItemDecoration.VERTICAL_LIST,R.drawable.background_less_half_line));
        rvCheckoutList.setAdapter(checkoutListAdapter);
        rvLaterList.setLayoutManager(new LinearLayoutManager(this));
        rvLaterList.setNestedScrollingEnabled(false);
//        rvLaterList.setAdapter(laterListAdapter);
    }

    private void initOthers() {
        //todo
    }

    @Override
    protected void loadData() {
        checkoutListAdapter.loadData();
        checkoutListAdapter.setOnCartStatusChangeListener(new CartListAdapter.OnCartStatusChangeListener() {
            @Override
            public void loadCartData(CartEntity cart) {
                shoppingCartNotice.setVisibility(View.VISIBLE);
                rvCheckoutList.setVisibility(View.VISIBLE);
                noDataNotice.setVisibility(View.GONE);

                for (CartEntity.Totals total: cart.totals) {
                    switch (total.title) {
                        case "Sub-Total": {
                            //todo
                            productPrice.setText("Total:" +total.text);
                            break;
                        }
                        case "Total": {
                            //todo
                            productPriceOriginal.setText("Cost saving: "+total.text);
                            break;
                        }
                    }
                }
            }

            @Override
            public void checkSubscribe() {
                checkIfSubscribe();
            }

            @Override
            public void noData() {
                rvCheckoutList.setVisibility(View.GONE);
                noDataNotice.setVisibility(View.VISIBLE);
                continueShopping.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
            }
        });
//        cartManager.getCartList(new CartManager.CartListCallBack() {
//            @Override
//            public void onSuccess(CartEntity cart) {
//                //todo
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                //todo
//            }
//        });
    }

    private void checkIfNeedAddToCart() {
        Intent intent = getIntent();
        String product_id = intent.getStringExtra("product_id");
        if (!TextUtils.isEmpty(product_id)) {
            addToCart(product_id, intent);
        }
    }

    private void addToCart(String product_id, Intent intent) {
        String quantity = intent.getStringExtra("quantity");
        HashMap<String,String> option = (HashMap<String, String>) intent.getSerializableExtra("option");
        String recurring_id = intent.getStringExtra("recurring_id");


        isAddingToCart = true;

        //if adding to cart, dialog will waiting for response,and load newest list after that.
        cartManager.addCartItem(product_id, quantity, option, recurring_id, new CartManager.AddItemCallBack() {

            //synchronized promise atomic
            @Override
            public synchronized void onSuccess() {
                isAddingToCart = false;
                checkIfSubscribe();
                loadData();
            }

            @Override
            public synchronized void onError(Throwable throwable) {
                isAddingToCart = false;
                loadData();
                ToastUtil.toast(throwable.getMessage());
                Timber.e(throwable);
            }
        });
    }

    private void checkIfSubscribe() {
//        if (adapter.getData()!=null&&adapter.getData().size()>=0) {
//            FirebaseMessaging.getInstance().subscribeToTopic("cart");
//        } else {
//            FirebaseMessaging.getInstance().unsubscribeFromTopic("cart");
//        }
    }
}
