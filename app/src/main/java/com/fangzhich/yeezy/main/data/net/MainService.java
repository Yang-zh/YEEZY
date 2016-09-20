package com.fangzhich.yeezy.main.data.net;

import com.fangzhich.yeezy.main.data.net.entity.CategoryEntity;
import com.fangzhich.yeezy.base.data.net.HttpResult;

import java.util.ArrayList;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Single;

/**
 * MainAPI
 * Created by Khorium on 2016/9/8.
 */
interface MainService {
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
}