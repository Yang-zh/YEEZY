package com.fangzhich.yeezy.data.net.service;

import com.fangzhich.yeezy.data.net.Bean.CategoryEntity;
import com.fangzhich.yeezy.data.net.Bean.ProductEntity;
import com.fangzhich.yeezy.data.net.Bean.ProductItemEntity;
import com.fangzhich.yeezy.data.net.framework.HttpResult;
import com.fangzhich.yeezy.data.net.Bean.LoginEntity;
import com.fangzhich.yeezy.data.net.Bean.RegisterEntity;

import java.util.ArrayList;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Single;

/**
 * API
 * Created by Khorium on 2016/9/8.
 */
public interface APIService {

    @FormUrlEncoded
    @POST("index.php?route=api/login")
    Single<HttpResult<LoginEntity>> login(
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
    @POST("index.php?route=api/category")
    Single<HttpResult<ArrayList<CategoryEntity>>> getCategories(
            @Field("page") int page,
            @Field("limit") int limit,
            @Field("parent_id") int parent_id,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);

    @FormUrlEncoded
    @POST("index.php?route=api/product")
    Single<HttpResult<ArrayList<ProductItemEntity>>> getProducts(
            @Field("page") int page,
            @Field("limit") int limit,
            @Field("category_id") int category_id,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("imei") String imei);

    @FormUrlEncoded
    @POST("index.php?route=api/product/getProduct")
    Single<HttpResult<ProductEntity>> getProduct(
            @Field("product_id") int category_id,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("imei") String imei);
}