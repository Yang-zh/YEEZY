package com.fangzhich.sneakerlab.user.data.net;

import com.fangzhich.sneakerlab.user.data.entity.CreditCardEntity;
import com.fangzhich.sneakerlab.user.data.entity.PersonalInfoEntity;
import com.fangzhich.sneakerlab.user.data.entity.UserInfoEntity;
import com.fangzhich.sneakerlab.user.data.entity.RegisterEntity;
import com.fangzhich.sneakerlab.base.data.net.HttpResult;
import com.fangzhich.sneakerlab.user.data.entity.WishEntity;

import java.util.ArrayList;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Single;

/**
 * UserService
 * Created by Khorium on 2016/9/14.
 */
interface UserService {

    @FormUrlEncoded
    @POST("index.php?route=api/login")
    Single<HttpResult<UserInfoEntity>> login(
            @Field("email") String email,
            @Field("password") String password,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);

    @FormUrlEncoded
    @POST("index.php?route=api/register")
    Single<HttpResult<RegisterEntity>> register(
            @Field("firstname") String firstname,
            @Field("lastname") String lastname,
            @Field("email") String email,
            @Field("password") String password,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);

    @FormUrlEncoded
    @POST("index.php?route=api/signout")
    Single<HttpResult<Object>> signOut(
            @Field("email") String email,
            @Field("token") String token,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);

    @FormUrlEncoded
    @POST("index.php?route=api/customer")
    Single<HttpResult<PersonalInfoEntity>> getPersonalInfo(
            @Field("email") String email,
            @Field("token") String token,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);

    @FormUrlEncoded
    @POST("index.php?route=api/customer/edit")
    Single<HttpResult<Object>> editPersonalInfo(
            @Field("firstname") String firstname,
            @Field("lastname") String lastname,
            @Field("phone") String phone,
            @Field("sex") String sex,
            @Field("age") String age,
            @Field("email") String email,
            @Field("token") String token,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);

    @FormUrlEncoded
    @POST("index.php?route=api/customer/editPwd")
    Single<HttpResult<Object>> editPassword(
            @Field("old_password") String old_password,
            @Field("new_password") String new_password,
            @Field("email") String email,
            @Field("token") String token,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);

    @FormUrlEncoded
    @POST("index.php?route=api/address/add")
    Single<HttpResult<String>> addAddress(
            @Field("fullname") String fullname,
            @Field("phone") String phone,
            @Field("address") String address,
            @Field("suite") String suite,
            @Field("city") String city,
            @Field("postcode") String postcode,
            @Field("country_id") String country_id,
            @Field("zone_id") String zone_id,
            @Field("email") String email,
            @Field("token") String token,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);

    @FormUrlEncoded
    @POST("index.php?route=api/address/edit")
    Single<HttpResult<Object>> editAddress(
            @Field("address_id") String address_id,
            @Field("fullname") String fullname,
            @Field("phone") String phone,
            @Field("address") String address,
            @Field("suite") String suite,
            @Field("city") String city,
            @Field("postcode") String postcode,
            @Field("country_id") String country_id,
            @Field("zone_id") String zone_id,
            @Field("email") String email,
            @Field("token") String token,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);

    @FormUrlEncoded
    @POST("index.php?route=api/credit")
    Single<HttpResult<CreditCardEntity>> getCreditCard(
            @Field("email") String email,
            @Field("token") String token,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);

    @FormUrlEncoded
    @POST("index.php?route=api/credit/add")
    Single<HttpResult<String>> addCreditCard(
            @Field("card_number") String card_number,
            @Field("card_month") String card_month,
            @Field("card_year") String card_year,
            @Field("card_cvv") String card_cvv,
            @Field("zip_code") String zip_code,
            @Field("email") String email,
            @Field("token") String token,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);

    @FormUrlEncoded
    @POST("index.php?route=api/credit/edit")
    Single<HttpResult<Object>> editCreditCard(
            @Field("card_number") String card_number,
            @Field("card_month") String card_month,
            @Field("card_year") String card_year,
            @Field("card_cvv") String card_cvv,
            @Field("zip_code") String zip_code,
            @Field("email") String email,
            @Field("token") String token,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);

    @FormUrlEncoded
    @POST("index.php?route=api/wishlist")
    Single<HttpResult<ArrayList<WishEntity>>> getWishList(
            @Field("email") String email,
            @Field("token") String token,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);

    @FormUrlEncoded
    @POST("index.php?route=api/wishlist/add")
    Single<HttpResult<Object>> addWish(
            @Field("product_id") String product_id,
            @Field("email") String email,
            @Field("token") String token,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);

    @FormUrlEncoded
    @POST("index.php?route=api/wishlist/delete")
    Single<HttpResult<Object>> deleteWish(
            @Field("product_id") String product_id,
            @Field("email") String email,
            @Field("token") String token,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);
}
