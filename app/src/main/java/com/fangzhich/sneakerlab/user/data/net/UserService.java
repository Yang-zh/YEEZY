package com.fangzhich.sneakerlab.user.data.net;

import android.support.v4.view.ViewCompat;

import com.fangzhich.sneakerlab.base.data.net.HttpResult;
import com.fangzhich.sneakerlab.main.data.entity.MessageEntity;
import com.fangzhich.sneakerlab.main.data.entity.NotificationEntity;
import com.fangzhich.sneakerlab.user.data.entity.AddressEntity;
import com.fangzhich.sneakerlab.user.data.entity.AvatarEntity;
import com.fangzhich.sneakerlab.user.data.entity.CreditCardEntity;
import com.fangzhich.sneakerlab.user.data.entity.PersonalInfoEntity;
import com.fangzhich.sneakerlab.user.data.entity.RegisterEntity;
import com.fangzhich.sneakerlab.user.data.entity.UserInfoEntity;
import com.fangzhich.sneakerlab.user.data.entity.WishEntity;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
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
            @Field("firebase_token") String firebase_token,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);

    @FormUrlEncoded
    @POST("index.php?route=api/facebook")
    Single<HttpResult<UserInfoEntity>> loginByFacebook(
            @Field("accesstoken") String accesstoken,
            @Field("facebookId") String facebookId,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("firstname") String firstname,
            @Field("middlename") String middlename,
            @Field("lastname") String lastname,
            @Field("avatarimage") String avatarimage,
            @Field("firebase_token") String firebase_token,
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
    @POST("index.php?route=api/customer/forgetPwd")
    Single<HttpResult<Object>> forgetPassword(
            @Field("email") String email,
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
    Single<HttpResult<List>> editPersonalInfo(
            @Field("firstname") String firstname,
            @Field("lastname") String lastname,
            @Field("phone") String phone,
            @Field("sex") String sex,
            @Field("age") String age,
            @Field("birthday") String birthday,
            @Field("email") String email,
            @Field("token") String token,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);

    @FormUrlEncoded
    @POST("index.php?route=api/customer/editEmail")
    Single<HttpResult<Object>> editEmail(
            @Field("email_new") String newEmail,
            @Field("email") String email,
            @Field("token") String token,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);

    @Multipart
    @POST(" index.php?route=api/customer/editAvatar")
    Single<HttpResult<AvatarEntity>> editAvatar(
            @Part MultipartBody.Part avatar,
            @Part MultipartBody.Part email,
            @Part MultipartBody.Part token,
            @Part MultipartBody.Part timestamp,
            @Part MultipartBody.Part signature,
            @Part MultipartBody.Part apiKey,
            @Part MultipartBody.Part imei);

    @FormUrlEncoded
    @POST("index.php?route=api/address/show")
    Single<HttpResult<AddressEntity>> getAddress(
            @Field("address_id") String address_id,
            @Field("email") String email,
            @Field("token") String token,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);

    @FormUrlEncoded
    @POST("index.php?route=api/address")
    Single<HttpResult<ArrayList<AddressEntity>>> getAddressList(
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
            @Field("set_default") String set_default,
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
            @Field("set_default") String set_default,
            @Field("email") String email,
            @Field("token") String token,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);
    @FormUrlEncoded
    @POST("index.php?route=api/address/setDefault")
    Single<HttpResult<Object>> setDefaultAddress(
            @Field("address_id") String address_id,
            @Field("email") String email,
            @Field("token") String token,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);

    @FormUrlEncoded
    @POST("index.php?route=api/address/delete")
    Single<HttpResult<Object>> deleteAddress(
            @Field("address_id") String address_id,
            @Field("email") String email,
            @Field("token") String token,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);

    @FormUrlEncoded
    @POST("index.php?route=api/credit/show")
    Single<HttpResult<CreditCardEntity>> getCreditCard(
            @Field("credit_id") String credit_id,
            @Field("email") String email,
            @Field("token") String token,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);

    @FormUrlEncoded
    @POST("index.php?route=api/credit")
    Single<HttpResult<ArrayList<CreditCardEntity>>> getCreditCardList(
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
            @Field("set_default") String set_default,
            @Field("email") String email,
            @Field("token") String token,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);

    @FormUrlEncoded
    @POST("index.php?route=api/credit/edit")
    Single<HttpResult<Object>> editCreditCard(
            @Field("credit_id") String credit_id,
            @Field("card_number") String card_number,
            @Field("card_month") String card_month,
            @Field("card_year") String card_year,
            @Field("card_cvv") String card_cvv,
            @Field("zip_code") String zip_code,
            @Field("set_default") String set_default,
            @Field("email") String email,
            @Field("token") String token,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);

    @FormUrlEncoded
    @POST("index.php?route=api/credit/delete")
    Single<HttpResult<Object>> deleteCreditCard(
            @Field("credit_id") String credit_id,
            @Field("email") String email,
            @Field("token") String token,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);
    @FormUrlEncoded
    @POST("index.php?route=api/credit/setDefault")
    Single<HttpResult<Object>> setDefaultCreditCard(
            @Field("credit_id") String credit_id,
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

    @FormUrlEncoded
    @POST("index.php?route=api/share/add")
    Single<HttpResult<Object>> share(
            @Field("product_id") String product_id,
            @Field("channel") String channel,
            @Field("text") String text,
            @Field("email") String email,
            @Field("token") String token,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);

    @FormUrlEncoded
    @POST("index.php?route=api/push")
    Single<HttpResult<ArrayList<NotificationEntity>>> getNotificationList(
            @Field("email") String email,
            @Field("token") String token,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);

    @FormUrlEncoded
    @POST("index.php?route=api/message/add")
    Single<HttpResult<Object>> requestSupport(
            @Field("type") String type,
            @Field("fullname") String fullname,
            @Field("text") String text,
            @Field("equipment_token") String equipment_token,
            @Field("email") String email,
            @Field("token") String token,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);

    @FormUrlEncoded
    @POST("index.php?route=api/message")
    Single<HttpResult<ArrayList<MessageEntity>>> getSupportMessageList(
            @Field("equipment_token") String equipment_token,
            @Field("email") String email,
            @Field("token") String token,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);
}
