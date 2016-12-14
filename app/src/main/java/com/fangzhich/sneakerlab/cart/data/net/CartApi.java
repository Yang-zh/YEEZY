package com.fangzhich.sneakerlab.cart.data.net;

import com.fangzhich.sneakerlab.base.data.net.BaseApi;
import com.fangzhich.sneakerlab.cart.data.entity.CartEntity;
import com.fangzhich.sneakerlab.cart.data.entity.CheckOutInfoEntity;
import com.fangzhich.sneakerlab.cart.data.entity.CountryEntity;
import com.fangzhich.sneakerlab.cart.data.entity.DistrictEntity;
import com.fangzhich.sneakerlab.cart.data.entity.PlaceOrderEntity;
import com.fangzhich.sneakerlab.util.Const;

import java.util.ArrayList;
import java.util.HashMap;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

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
    public static void addItemToCart(String product_id, String quantity, HashMap<String,String> option, String recurring_id, SingleSubscriber<Object> singleSubscriber) {
        String timestamp = getTimeStamp();

        Timber.e(gson.toJson(option));

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

    /**
     * Remove Item From Later List in Cart request
     *
     * @param cart_back_id item's id in Cart
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void removeItemFromLaterCart(String cart_back_id, SingleSubscriber<Object> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("cart_back_id", cart_back_id);
        params.put("email", email);
        params.put("token", token);
        params.put("timestamp", timestamp);

        String signature = getSignature(params);

        createService(CartService.class)
                .removeItemFromLaterCart(cart_back_id,email,token,timestamp,signature,API_KEY, Const.IMEI)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    /**
     * Move Item From Later List to Cart List request
     *
     * @param cart_back_id item's id in Cart
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void moveItemFromLaterToCart(String cart_back_id, SingleSubscriber<CartEntity.Cart> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("cart_back_id", cart_back_id);
        params.put("email", email);
        params.put("token", token);
        params.put("timestamp", timestamp);

        String signature = getSignature(params);

        createService(CartService.class)
                .moveItemFromLaterToCart(cart_back_id,email,token,timestamp,signature,API_KEY, Const.IMEI)
                .map(new HttpResultFunc<CartEntity.Cart>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    /**
     * Move Item From Cart List to Later List request
     *
     * @param cart_id item's id in Cart
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void moveItemFromCartToLater(String cart_id, SingleSubscriber<CartEntity.CartBack> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("cart_id", cart_id);
        params.put("email", email);
        params.put("token", token);
        params.put("timestamp", timestamp);

        String signature = getSignature(params);

        createService(CartService.class)
                .moveItemFromCartToLater(cart_id,email,token,timestamp,signature,API_KEY, Const.IMEI)
                .map(new HttpResultFunc<CartEntity.CartBack>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    public static void checkOut(SingleSubscriber<CheckOutInfoEntity> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("email", email);
        params.put("token", token);
        params.put("timestamp", timestamp);

        String signature = getSignature(params);

        createService(CartService.class)
                .checkOut(email,token,timestamp,signature,API_KEY, Const.IMEI)
                .map(new HttpResultFunc<CheckOutInfoEntity>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    public static void placeOrder(String payment, String address_id, String credit_id, String client_data, SingleSubscriber<PlaceOrderEntity> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("payment", payment);
        params.put("address_id", address_id);
        params.put("email", email);
        params.put("token", token);
        params.put("timestamp", timestamp);

        String signature = getSignature(params);

        createService(CartService.class)
                .placeOrder(payment,address_id,credit_id,client_data,email,token,timestamp,signature,API_KEY, Const.IMEI)
                .map(new HttpResultFunc<PlaceOrderEntity>())
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
