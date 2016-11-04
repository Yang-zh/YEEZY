package com.fangzhich.sneakerlab.cart.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.widget.CustomDialog;
import com.fangzhich.sneakerlab.base.widget.ProgressBar;
import com.fangzhich.sneakerlab.cart.data.entity.CartEntity;
import com.fangzhich.sneakerlab.main.ui.ReturnPolicyActivity;
import com.fangzhich.sneakerlab.order.data.entity.ConfirmOrderEntity;
import com.fangzhich.sneakerlab.order.data.net.OrderApi;
import com.fangzhich.sneakerlab.order.ui.OrderConfirmedActivity;
import com.fangzhich.sneakerlab.util.ToastUtil;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.SingleSubscriber;
import rx.Subscription;
import timber.log.Timber;

/**
 * ShoppingCartActivity
 * Created by Khorium on 2016/9/1.
 */
public class ShoppingCartDialog {

    private Context mContext;
    private View mContentView;
    private DialogManager manager;
    private Subscription mSubscription;

    @OnClick(R.id.bt_cancel)
    void cancel() {
        mPopupWindow.dismiss();
    }
    @BindView(R.id.dialog)
    RelativeLayout dialog;

    //----------address dialog-------------
    @BindView(R.id.ship_layout)
    RelativeLayout shipLayout;
    @BindView(R.id.address)
    TextView tv_address;
    @OnClick(R.id.bt_address_edit)
    void editAddress() {
        manager.startAddressDialog(cart==null?null:cart.address);
        manager.hideShoppingCartDialog();
    }
    private String address_id;

    //----------credit card dialog-------------
    @BindView(R.id.bill_layout)
    RelativeLayout billLayout;
    @BindView(R.id.credit_card_type)
    TextView creditCardType;
    @BindView(R.id.credit_cart_number)
    TextView creditCartNumber;
    @OnClick(R.id.bt_card_edit)
    void editCreditCardInfo() {
        manager.startCreditCardDialog(cart==null?null:cart.payment);
        manager.hideShoppingCartDialog();
    }
    private String cardType;
    private String cardNumber;
    private String cardYear;
    private String cardMonth;
    private String cardCvv;

    public CartEntity cart;

    @BindView(R.id.tv_item_total)
    TextView tvItemTotal;
    @BindView(R.id.tv_estimated_shipping)
    TextView tvEstimatedShipping;
    @BindView(R.id.tv_tax)
    TextView tvTax;
    @BindView(R.id.tv_order_total)
    TextView tvOrderTotal;

    @OnClick(R.id.return_policy)
    void showReturnPolicy() {
        mContext.startActivity(new Intent(mContext, ReturnPolicyActivity.class));
    }

    @OnClick(R.id.bt_checkout)
    void checkout() {
        if (TextUtils.isEmpty(address_id)) {
            ToastUtil.toast("please add address info");
            return;
        }
//        TextUtils.isEmpty(cardType) ||
        if (TextUtils.isEmpty(cardNumber)) {
            ToastUtil.toast("Please add credit card info");
            return;
        }

        final ProgressBar progressBar = ProgressBar.getInstance();
        progressBar.init(mContext, new ProgressBar.Callback() {
            @Override
            public void onProgressBarClick(View v) {

            }
        }).show();

        OrderApi.checkOut(new SingleSubscriber<ConfirmOrderEntity>() {
            @Override
            public void onSuccess(ConfirmOrderEntity value) {
                progressBar.cancel();
                Intent intent = new Intent(mContext, OrderConfirmedActivity.class);
                intent.putExtra("cart", cart);
                mContext.startActivity(intent);
                manager.closeAll();
                manager.closeProductDetail();
                checkIfSubscribe();
            }

            @Override
            public void onError(Throwable error) {
                progressBar.cancel();
                manager.showCustomDialog(R.layout.dialog_checkout_fail, new CustomDialog.Listener() {
                    @Override
                    public void onInit(final PopupWindow dialog, View content) {
                        content.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }

                    @Override
                    public void onDismiss(PopupWindow dialog, View content) {

                    }
                });
                Timber.e(error);
                ToastUtil.toast(error.getMessage());
            }
        });
    }

    @BindView(R.id.rv_shoppingCart_list)
    RecyclerView recyclerView;
    @BindView(R.id.no_data_notice)
    RelativeLayout noDataNotice;
    @BindView(R.id.continue_shopping)
    TextView continueShopping;

    private CartListAdapter adapter = new CartListAdapter();

    private PopupWindow mPopupWindow;

    private CartManager cartManager = new CartManager();

    private boolean isAddingToCart;

    private View mPopupContent;


    public ShoppingCartDialog initPopup(final DialogManager manager, Context context) {
        this.manager = manager;
        mContext = context;
        mPopupContent = View.inflate(context, R.layout.dialog_shoppingcart, null);
        ButterKnife.bind(this, mPopupContent);

        mPopupWindow = new PopupWindow(mPopupContent, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setAnimationStyle(R.style.Dialog);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                manager.closeAll();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        return this;
    }

    public ShoppingCartDialog addToCart(String product_id, String quantity, HashMap<String,String> option, String recurring_id) {

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
        return this;
    }

    private void loadData() {
        recyclerView.setAdapter(adapter);
        adapter.loadData();
        adapter.setOnLoadDataListener(new CartListAdapter.OnLoadDataListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void loadCartData(CartEntity cart) {
                ShoppingCartDialog.this.cart = cart;

                if (cart.products==null || cart.products.size()==0) {

                }

                if (cart.address!=null) {
                    address_id = cart.address.address_id;
                    tv_address.setText(cart.address.city+" "+cart.address.address_1);
                }
                if (cart.payment!=null) {
                    cardNumber = cart.payment.card_number;
                    cardMonth = cart.payment.card_month;
                    cardYear = cart.payment.card_year;
                    cardCvv = cart.payment.card_cvv;
                    creditCartNumber.setText(cardNumber.length()>4?"****"+cardNumber.substring(cardNumber.length()-4):cardNumber);
                }

                if (cart.shiping!=null) {
                    tvEstimatedShipping.setText(cart.shiping.text);
                } else {
                    tvEstimatedShipping.setText(mContext.getString(R.string.not_sure));
                }

                for (CartEntity.Totals total: cart.totals) {
                    switch (total.title) {
                        case "Sub-Total": {
                            tvItemTotal.setText(total.text);
                            break;
                        }
                        case "Tax": {
                            tvTax.setText(total.text);
                            break;
                        }
                        case "Total": {
                            tvOrderTotal.setText(total.text);
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
                dialog.setVisibility(View.GONE);
                noDataNotice.setVisibility(View.VISIBLE);
                continueShopping.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        manager.closeAll();
                    }
                });
            }
        });

    }

    private void checkIfSubscribe() {
        if (adapter.getData()!=null&&adapter.getData().size()>=0) {
            FirebaseMessaging.getInstance().subscribeToTopic("cart");
        } else {
            FirebaseMessaging.getInstance().unsubscribeFromTopic("cart");
        }
    }

    public void showPopup(View contentView) {
        mContentView = contentView;
        if (!isAddingToCart) {
            loadData();
        }
        mPopupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
    }

    public boolean isShowing() {
        return mPopupWindow != null && mPopupWindow.isShowing();
    }

    public void dismiss() {
        if (mSubscription!=null && mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
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

    public void saveAddress(String id, String address) {
//        address_id = id;
//        tv_address.setText(address);
        loadData();
    }

    public void saveCreditCard(String type,String number,String year,String month,String cvv) {
        cardType = type;
        cardYear = year;
        cardMonth = month;
        cardCvv = cvv;
        cardNumber = number;
        creditCartNumber.setText(number.length()>4?"****"+number.substring(number.length()-4):number);
    }

}
