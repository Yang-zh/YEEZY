package com.fangzhich.sneakerlab.cart.data.net;

import com.fangzhich.sneakerlab.base.data.net.BaseApi;
import com.fangzhich.sneakerlab.cart.data.entity.CartEntity;
import com.fangzhich.sneakerlab.order.data.entity.CountryEntity;
import com.fangzhich.sneakerlab.order.data.entity.DistrictEntity;
import com.fangzhich.sneakerlab.util.Const;

import java.util.ArrayList;
import java.util.HashMap;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.fangzhich.sneakerlab.util.Const.Obj.gson;

/**
 * CartApi
 * Created by Khorium on 2016/9/21.
 */

public class CartApi extends BaseApi {


    /**
     * CartList request
     *
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void getCartList(SingleSubscriber<CartEntity> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("email", email);
        params.put("token", token);
        params.put("timestamp", timestamp);

        String signature = getSignature(params);

        createService(CartService.class)
                .getCartList(email,token,timestamp,signature,API_KEY, Const.IMEI)
                .map(new HttpResultFunc<CartEntity>())
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
    public static void addItemToCart(String product_id, String quantity, ArrayList<Integer> option, String recurring_id, SingleSubscriber<Object> singleSubscriber) {
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

    public static void getCountries(SingleSubscriber<ArrayList<CountryEntity>> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("timestamp", timestamp);

        String signature = getSignature(params);

        createService(CartService.class)
                .getCountries(timestamp,signature,API_KEY, Const.IMEI)
                .map(new HttpResultFunc<ArrayList<CountryEntity>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    public static void getDistricts(String country_id, SingleSubscriber<ArrayList<DistrictEntity>> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("country_id", country_id);
        params.put("timestamp", timestamp);

        String signature = getSignature(params);

        createService(CartService.class)
                .getDistricts(country_id,timestamp,signature,API_KEY, Const.IMEI)
                .map(new HttpResultFunc<ArrayList<DistrictEntity>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }
}
