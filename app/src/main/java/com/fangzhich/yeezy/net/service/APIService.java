package com.fangzhich.yeezy.net.service;

import com.fangzhich.yeezy.net.Bean.HttpResult;
import com.fangzhich.yeezy.net.Bean.LoginEntity;
import com.fangzhich.yeezy.net.Bean.RegisterEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * API
 * Created by Khorium on 2016/9/8.
 */
public interface APIService {

    @FormUrlEncoded
    @POST("index.php?route=api/login")
    Observable<HttpResult<LoginEntity>> login(
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String equipment_id,
            @Field("timestamp") String timestamp,
            @Field("email") String email,
            @Field("password") String password,
            @Field("signature") String signature);

    @FormUrlEncoded
    @POST("index.php?route=api/register")
    Observable<HttpResult<RegisterEntity>> register(
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String equipment_id,
            @Field("timestamp") String timestamp,
            @Field("firstname") String firstname,
            @Field("lastname") String lastname,
            @Field("email") String email,
            @Field("password") String password,
            @Field("signature") String signature);
}