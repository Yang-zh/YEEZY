package com.fangzhich.sneakerlab.cart.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fangzhich.sneakerlab.App;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.data.event.RxBus;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.base.ui.recyclerview.LinearLayoutItemDecoration;
import com.fangzhich.sneakerlab.cart.data.entity.CartEntity;
import com.fangzhich.sneakerlab.cart.data.event.MoveItemFromCartToLaterEvent;
import com.fangzhich.sneakerlab.cart.data.event.MoveItemFromLaterToCartEvent;
import com.fangzhich.sneakerlab.util.TagFormatUtil;
import com.fangzhich.sneakerlab.util.ToastUtil;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
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

    CartListAdapter checkoutListAdapter = new CartListAdapter();
    CartListLaterAdapter laterListAdapter = new CartListLaterAdapter();

    @Override
    public int setContentLayout() {
        return R.layout.activity_shoppingcart;
    }

    @Override
    protected void initContentView() {
        mPaymentManger = ((App)getApplication()).mPaymentManager;

        initRxBus();

        initToolbar();

        checkIfNeedAddToCart();

        initRecyclerView();
    }

    private void initRxBus() {
        fromLaterToCartObserver = RxBus.getDefault().toObservable(MoveItemFromLaterToCartEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<MoveItemFromLaterToCartEvent>() {
                    @Override
                    public void call(MoveItemFromLaterToCartEvent event) {
                        checkoutListAdapter.addItem(event.cartItem);
                        laterListAdapter.notifyItemRemoved(event.position);
                    }
                });
        formCartToLaterObserver = RxBus.getDefault().toObservable(MoveItemFromCartToLaterEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<MoveItemFromCartToLaterEvent>() {
                    @Override
                    public void call(MoveItemFromCartToLaterEvent event) {
                        laterListAdapter.addItem(event.cartItem);
                        checkoutListAdapter.notifyItemRemoved(event.position);
                    }
                });
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
        rvLaterList.addItemDecoration(new LinearLayoutItemDecoration(this,LinearLayoutItemDecoration.VERTICAL_LIST,R.drawable.background_less_half_line));
        rvLaterList.setAdapter(laterListAdapter);
    }

    @Override
    protected void loadData() {
        cartManager.getCartList(new CartManager.CartListCallBack() {
            @Override
            public void onSuccess(CartEntity cart) {
                if (cart.cart==null || cart.cart.size()==0) {
                    rvCheckoutList.setVisibility(View.GONE);
                    noDataNotice.setVisibility(View.VISIBLE);
                    continueShopping.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    });
                    return;
                }

                //no data notice
                shoppingCartNotice.setVisibility(View.VISIBLE);
                rvCheckoutList.setVisibility(View.VISIBLE);
                noDataNotice.setVisibility(View.GONE);

                //recycler view
                checkoutListAdapter.setData(cart.cart);
                checkoutListAdapter.notifyDataSetChanged();
                laterListAdapter.setData(cart.cartback);
                laterListAdapter.notifyDataSetChanged();

                //bottom bar
                int total = 0;
                int original = 0;
                for (CartEntity.Cart product: cart.cart) {
                    total += Integer.valueOf(product.special_price);
                    original += Integer.valueOf(product.original_price);
                }
                productPrice.setText("Total:" +total);
                productPriceOriginal.setText("Cost saving: "+original);
            }

            @Override
            public void onError(Throwable throwable) {
                ToastUtil.toast("connect to server failed");
                Timber.e(throwable);
            }
        });
        checkoutListAdapter.loadData();
        checkoutListAdapter.setOnCartStatusChangeListener(new CartListAdapter.OnCartStatusChangeListener() {
            @Override
            public void checkSubscribe() {
                checkIfSubscribe();
            }
        });
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

        //if adding to cart, dialog will waiting for response,and load newest list after that.
        cartManager.addCartItem(product_id, quantity, option, recurring_id, new CartManager.AddItemCallBack() {

            //synchronized promise atomic
            @Override
            public synchronized void onSuccess() {
                checkIfSubscribe();
                loadData();
            }

            @Override
            public synchronized void onError(Throwable throwable) {
                loadData();
                ToastUtil.toast(throwable.getMessage());
                Timber.e(throwable);
            }
        });
    }

    private void checkIfSubscribe() {
        boolean isItemsInCart = checkoutListAdapter.getData()!=null&&checkoutListAdapter.getData().size()>=0;
        boolean isItemsInLater = laterListAdapter.getData()!=null&&laterListAdapter.getData().size()>=0;
        if (isItemsInCart || isItemsInLater) {
            FirebaseMessaging.getInstance().subscribeToTopic("cart");
        } else {
            FirebaseMessaging.getInstance().unsubscribeFromTopic("cart");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Subscription fromLaterToCartObserver;
    private Subscription formCartToLaterObserver;

    @Override
    protected void onDestroy() {
        if (fromLaterToCartObserver.isUnsubscribed()){
            fromLaterToCartObserver.unsubscribe();
        }
        if (formCartToLaterObserver.isUnsubscribed()){
            formCartToLaterObserver.unsubscribe();
        }
        super.onDestroy();
    }
}
