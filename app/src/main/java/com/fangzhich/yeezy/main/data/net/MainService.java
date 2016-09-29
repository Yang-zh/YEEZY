package com.fangzhich.yeezy.main.data.net;

import com.fangzhich.yeezy.base.data.net.HttpResult;
import com.fangzhich.yeezy.main.data.entity.CategoryEntity;

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
            @Field("page") String page,
            @Field("limit") String limit,
            @Field("parent_id") String parent_id,
            @Field("timestamp") String timestamp,
            @Field("signature") String signature,
            @Field("apiKey") String apiKey,
            @Field("equipment_id") String imei);
}