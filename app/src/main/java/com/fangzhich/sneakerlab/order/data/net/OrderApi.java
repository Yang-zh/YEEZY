package com.fangzhich.sneakerlab.order.data.net;

import com.fangzhich.sneakerlab.base.data.net.BaseApi;
import com.fangzhich.sneakerlab.order.data.entity.ConfirmOrderEntity;
import com.fangzhich.sneakerlab.order.data.entity.OrderEntity;
import com.fangzhich.sneakerlab.order.data.entity.OrderItemEntity;
import com.fangzhich.sneakerlab.util.Const;

import java.util.ArrayList;
import java.util.HashMap;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * OrderApi
 * Created by Khorium on 2016/9/29.
 */

public class OrderApi extends BaseApi {

    /**
     * Get order list request
     * @param page page
     * @param limit limit
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void getOrderList(String page, String limit, SingleSubscriber<ArrayList<OrderItemEntity>> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("page", page);
        params.put("limit", limit);
        params.put("email", email);
        params.put("token", token);
        params.put("timestamp", timestamp);

        String signature = getSignature(params);

        createService(OrderService.class)
                .getOrderlist(page,limit,email,token,timestamp,signature,API_KEY, Const.IMEI)
                .map(new HttpResultFunc<ArrayList<OrderItemEntity>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    /**
     * Get order detail request
     * @param order_id order id
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void getOrder(String order_id, SingleSubscriber<OrderEntity> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("order_id", order_id);
        params.put("email", email);
        params.put("token", token);
        params.put("timestamp", timestamp);

        String signature = getSignature(params);

        createService(OrderService.class)
                .getOrder(order_id,email,token,timestamp,signature,API_KEY, Const.IMEI)
                .map(new HttpResultFunc<OrderEntity>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    /**
     * Confirm order request
     * @param order_id order id
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void confirmOrder(String order_id, SingleSubscriber<Object> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("order_id", order_id);
        params.put("order_status_id", "5");
        params.put("email", email);
        params.put("token", token);
        params.put("timestamp", timestamp);

        String signature = getSignature(params);

        createService(OrderService.class)
                .changeOrderStatus(order_id,"5",email,token,timestamp,signature,API_KEY, Const.IMEI)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    /**
     * Cancel order request
     * @param order_id order id
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void cancelOrder(String order_id, SingleSubscriber<Object> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("order_id", order_id);
        params.put("order_status_id", "7");
        params.put("email", email);
        params.put("token", token);
        params.put("timestamp", timestamp);

        String signature = getSignature(params);

        createService(OrderService.class)
                .changeOrderStatus(order_id,"7",email,token,timestamp,signature,API_KEY, Const.IMEI)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    public static void checkOut(String address_id,String card_number,String card_month,String card_year,String card_cvv, SingleSubscriber<ConfirmOrderEntity> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("address_id", address_id);
        params.put("card_number", card_number);
        params.put("card_month", card_month);
        params.put("card_year", card_year);
        params.put("card_cvv", card_cvv);
        params.put("email", email);
        params.put("token", token);
        params.put("timestamp", timestamp);

        String signature = getSignature(params);

        createService(OrderService.class)
                .checkOut(address_id,card_number,card_month,card_year,card_cvv,email,token,timestamp,signature,API_KEY, Const.IMEI)
                .map(new HttpResultFunc<ConfirmOrderEntity>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    public static void addReview(String product_id,String rating,String text,SingleSubscriber<Object> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("product_id", product_id);
        params.put("rating", rating);
        params.put("text", text);
        params.put("email", email);
        params.put("token", token);
        params.put("timestamp", timestamp);

        String signature = getSignature(params);

        createService(OrderService.class)
                .addReview(product_id,rating,text,email,token,timestamp,signature,API_KEY, Const.IMEI)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }
}
