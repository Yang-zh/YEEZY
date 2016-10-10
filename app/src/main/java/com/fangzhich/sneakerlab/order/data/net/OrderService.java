package com.fangzhich.sneakerlab.order.data.net;

import com.fangzhich.sneakerlab.base.data.net.HttpResult;
import com.fangzhich.sneakerlab.order.data.entity.ConfirmOrderEntity;
import com.fangzhich.sneakerlab.order.data.entity.OrderEntity;
import com.fangzhich.sneakerlab.order.data.entity.OrderItemEntity;

import java.util.ArrayList;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Single;

/**
 * OrderService
 * Created by Khorium on 2016/9/29.
 */

public interface OrderService {

    @FormUrlEncoded
    @POST("index.php?route=api/order/getOrders")
    Single<HttpResult<ArrayList<OrderItemEntity>>> getOrderlist(
            @Field("page") String page,
            @Field("limit") String limit,
            @Field("email") String email,
            @Field("token") String token,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);

    @FormUrlEncoded
    @POST("index.php?route=api/order/getOrder")
    Single<HttpResult<OrderEntity>> getOrder(
            @Field("order_id") String order_id,
            @Field("email") String email,
            @Field("token") String token,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);

    @FormUrlEncoded
    @POST("index.php?route=api/checkout")
    Single<HttpResult<ConfirmOrderEntity>> checkOut(
            @Field("address_id") String address_id,
            @Field("card_number") String card_number,
            @Field("card_month") String card_month,
            @Field("card_year") String card_year,
            @Field("card_cvv") String card_cvv,
            @Field("email") String email,
            @Field("token") String token,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);

}
