package com.fangzhich.sneakerlab.cart.data.net;

import com.fangzhich.sneakerlab.base.data.net.HttpResult;
import com.fangzhich.sneakerlab.cart.data.entity.CheckOutInfoEntity;
import com.fangzhich.sneakerlab.user.data.entity.AddressEntity;
import com.fangzhich.sneakerlab.cart.data.entity.CartEntity;
import com.fangzhich.sneakerlab.order.data.entity.ConfirmOrderEntity;
import com.fangzhich.sneakerlab.cart.data.entity.CountryEntity;
import com.fangzhich.sneakerlab.cart.data.entity.DistrictEntity;

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

        @FormUrlEncoded
        @POST("index.php?route=api/cartback/removeCartback")
        Single<HttpResult<Object>> removeItemFromLaterCart(
                @Field("cart_back_id") String cart_back_id,
                @Field("email") String email,
                @Field("token") String token,
                @Field("timestamp") String timestamp,
                @Field("signature") String signature,
                @Field("apiKey") String apiKey,
                @Field("equipment_id") String imei);

        @FormUrlEncoded
        @POST("index.php?route=api/cartback/moveToCart")
        Single<HttpResult<CartEntity.Cart>> moveItemFromLaterToCart(
                @Field("cart_back_id") String cart_back_id,
                @Field("email") String email,
                @Field("token") String token,
                @Field("timestamp") String timestamp,
                @Field("signature") String signature,
                @Field("apiKey") String apiKey,
                @Field("equipment_id") String imei);

        @FormUrlEncoded
        @POST("index.php?route=api/cartback/moveOutCart")
        Single<HttpResult<CartEntity.CartBack>> moveItemFromCartToLater(
                @Field("cart_id") String cart_id,
                @Field("email") String email,
                @Field("token") String token,
                @Field("timestamp") String timestamp,
                @Field("signature") String signature,
                @Field("apiKey") String apiKey,
                @Field("equipment_id") String imei);

        @FormUrlEncoded
        @POST("index.php?route=api/common/country")
        Single<HttpResult<ArrayList<CountryEntity>>> getCountries(
                @Field("timestamp") String timestamp,
                @Field("signature") String signature,
                @Field("apiKey") String apiKey,
                @Field("equipment_id") String imei);

        @FormUrlEncoded
        @POST("index.php?route=api/common/zone")
        Single<HttpResult<ArrayList<DistrictEntity>>> getDistricts(
                @Field("country_id") String country_Id,
                @Field("timestamp") String timestamp,
                @Field("signature") String signature,
                @Field("apiKey") String apiKey,
                @Field("equipment_id") String imei);

        @FormUrlEncoded
        @POST("index.php?route=api/checkout")
        Single<HttpResult<CheckOutInfoEntity>> checkOut(
                @Field("email") String email,
                @Field("token") String token,
                @Field("timestamp") String timestamp,
                @Field("signature") String signature,
                @Field("apiKey") String apiKey,
                @Field("equipment_id") String imei);

        @FormUrlEncoded
        @POST("index.php?route=api/placeOrder")
        Single<HttpResult<ConfirmOrderEntity>> placeOrder(
                @Field("email") String email,
                @Field("token") String token,
                @Field("timestamp") String timestamp,
                @Field("signature") String signature,
                @Field("apiKey") String apiKey,
                @Field("equipment_id") String imei);
}
