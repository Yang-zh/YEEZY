package com.fangzhich.yeezy.cart.data.net;

import com.fangzhich.yeezy.base.data.net.BaseApi;
import com.fangzhich.yeezy.cart.data.entity.CartItemEntity;
import com.fangzhich.yeezy.product.data.entity.ProductEntity;
import com.fangzhich.yeezy.util.Const;

import java.util.ArrayList;
import java.util.HashMap;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.fangzhich.yeezy.util.Const.Obj.gson;

/**
 * CartApi
 * Created by Khorium on 2016/9/21.
 */

public class CartApi extends BaseApi {


    private static final String email = Const.getUserInfo().data.user_info.email;
    private static final String token = Const.getUserInfo().data.token;

    /**
     * CartList request
     *
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void getCartList(SingleSubscriber<ArrayList<CartItemEntity>> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("email", email);
        params.put("token", token);
        params.put("timestamp", timestamp);

        String signature = getSignature(params);

        createService(CartService.class)
                .getCartList(email,token,timestamp,signature,API_KEY, Const.IMEI)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    /**
     * Add Item to Cart request
     *
     * @param product_id product_id of product in this Item
     * @param quantity quantity of product
     * @param option options of product
     * @param recurring_id recurring_id of this item
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void addItemToCart(String product_id, String quantity, CartItemEntity.Products.Option option, String recurring_id, SingleSubscriber<Object> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("product_id", product_id);
        params.put("quantity", quantity);
        params.put("option", gson.toJson(option));
        params.put("recurring_id", recurring_id);
        params.put("email", email);
        params.put("token", token);
        params.put("timestamp", timestamp);

        String signature = getSignature(params);

        createService(CartService.class)
                .addItemToCart(product_id,quantity,gson.toJson(option),recurring_id,email,token,timestamp,signature,API_KEY, Const.IMEI)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    /**
     * Edit Item in Cart request
     *
     * @param cart_id item's id in Cart
     * @param quantity quantity of product
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void editItemInCart(String cart_id, String quantity, SingleSubscriber<Object> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("cart_id", cart_id);
        params.put("quantity", quantity);
        params.put("email", email);
        params.put("token", token);
        params.put("timestamp", timestamp);

        String signature = getSignature(params);

        createService(CartService.class)
                .editItemInCart(cart_id,quantity,email,token,timestamp,signature,API_KEY, Const.IMEI)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    /**
     * Remove Item From Cart request
     *
     * @param cart_id item's id in Cart
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void removeItemFromCart(String cart_id, SingleSubscriber<Object> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("cart_id", cart_id);
        params.put("email", email);
        params.put("token", token);
        params.put("timestamp", timestamp);

        String signature = getSignature(params);

        createService(CartService.class)
                .removeItemFromCart(cart_id,email,token,timestamp,signature,API_KEY, Const.IMEI)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }
}
