package com.fangzhich.yeezy.order.data.net;

import com.fangzhich.yeezy.base.data.net.HttpResult;
import com.fangzhich.yeezy.order.data.entity.CountryEntity;
import com.fangzhich.yeezy.order.data.entity.DistrictEntity;
import com.fangzhich.yeezy.product.data.entity.ProductItemEntity;

import java.util.ArrayList;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Single;

/**
 * OrderService
 * Created by Khorium on 2016/9/29.
 */

public interface OrderService {

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

}
