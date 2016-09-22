package com.fangzhich.yeezy.order.data.net;

import com.fangzhich.yeezy.base.data.net.HttpResult;
import com.fangzhich.yeezy.order.data.entity.CartEntity;
import com.fangzhich.yeezy.product.data.entity.BannerImageEntity;
import com.fangzhich.yeezy.product.data.entity.PopularProductEntity;
import com.fangzhich.yeezy.product.data.entity.ProductEntity;
import com.fangzhich.yeezy.product.data.entity.ProductItemEntity;
import com.fangzhich.yeezy.product.data.entity.ReviewEntity;

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
        Single<HttpResult<ArrayList<CartEntity>>> getCartList(
                @Field("email") String email,
                @Field("token") String token,
                @Field("timestamp") String timestamp,
                @Field("signature") String signature,
                @Field("apiKey") String apiKey,
                @Field("equipment_id") String imei);

        @FormUrlEncoded
        @POST("index.php?route=api/card/add")
        Single<HttpResult> addCart(
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
        Single<HttpResult> editCart(
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
        Single<HttpResult> removeCart(
                @Field("cart_id") String cart_id,
                @Field("email") String email,
                @Field("token") String token,
                @Field("timestamp") String timestamp,
                @Field("signature") String signature,
                @Field("apiKey") String apiKey,
                @Field("equipment_id") String imei);
}
