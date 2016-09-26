package com.fangzhich.yeezy.cart.data.net;

import com.fangzhich.yeezy.base.data.net.HttpResult;
import com.fangzhich.yeezy.cart.data.entity.CartEntity;

import java.util.ArrayList;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Single;

/**
 * ProductService
 * Created by Khorium on 2016/9/18.
 */
interface CartService {
        @FormUrlEncoded
        @POST("index.php?route=api/cart")
        Single<HttpResult<CartEntity>> getCartList(
                @Field("email") String email,
                @Field("token") String token,
                @Field("timestamp") String timestamp,
                @Field("signature") String signature,
                @Field("apiKey") String apiKey,
                @Field("equipment_id") String imei);

        @FormUrlEncoded
        @POST("index.php?route=api/cart/add")
        Single<HttpResult<Object>> addItemToCart(
                @Field("product_id") String product_id,
                @Field("quantity") String quantity,
                @Field("option") String options,
                @Field("recurring_id") String recurring_id,
                @Field("email") String email,
                @Field("token") String token,
                @Field("timestamp") String timestamp,
                @Field("signature") String signature,
                @Field("apiKey") String apiKey,
                @Field("equipment_id") String imei);

        @FormUrlEncoded
        @POST("index.php?route=api/cart/edit")
        Single<HttpResult<Object>> editItemInCart(
                @Field("cart_id") String cart_id,
                @Field("quantity") String quantity,
                @Field("email") String email,
                @Field("token") String token,
                @Field("timestamp") String timestamp,
                @Field("signature") String signature,
                @Field("apiKey") String apiKey,
                @Field("equipment_id") String imei);

        @FormUrlEncoded
        @POST("index.php?route=api/cart/remove")
        Single<HttpResult<Object>> removeItemFromCart(
                @Field("cart_id") String cart_id,
                @Field("email") String email,
                @Field("token") String token,
                @Field("timestamp") String timestamp,
                @Field("signature") String signature,
                @Field("apiKey") String apiKey,
                @Field("equipment_id") String imei);
}
