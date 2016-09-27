package com.fangzhich.yeezy.user.data.net;

import com.fangzhich.yeezy.user.data.entity.PersonalInfoEntity;
import com.fangzhich.yeezy.user.data.entity.UserInfoEntity;
import com.fangzhich.yeezy.user.data.entity.RegisterEntity;
import com.fangzhich.yeezy.base.data.net.HttpResult;

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
}
