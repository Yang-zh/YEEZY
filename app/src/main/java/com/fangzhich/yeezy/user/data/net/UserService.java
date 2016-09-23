package com.fangzhich.yeezy.user.data.net;

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
}
