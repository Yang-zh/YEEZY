package com.fangzhich.yeezy.product.data.net;

import com.fangzhich.yeezy.product.data.entity.BannerImageEntity;
import com.fangzhich.yeezy.product.data.entity.ProductEntity;
import com.fangzhich.yeezy.product.data.entity.ProductItemEntity;
import com.fangzhich.yeezy.base.data.net.HttpResult;
import com.fangzhich.yeezy.product.data.entity.PopularProductEntity;
import com.fangzhich.yeezy.product.data.entity.ReviewEntity;

import java.util.ArrayList;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Single;

/**
 * ProductService
 * Created by Khorium on 2016/9/18.
 */
interface ProductService {
        @FormUrlEncoded
        @POST("index.php?route=api/product")
        Single<HttpResult<ArrayList<ProductItemEntity>>> getProducts(
                @Field("page") int page,
                @Field("limit") int limit,
                @Field("category_id") int category_id,
                @Field("timestamp") String timestamp,
                @Field("signature") String signature,
                @Field("apiKey") String apiKey,
                @Field("equipment_id") String imei);

        @FormUrlEncoded
        @POST("index.php?route=api/product/getProduct")
        Single<HttpResult<ProductEntity>> getProduct(
                @Field("product_id") int product_id,
                @Field("timestamp") String timestamp,
                @Field("signature") String signature,
                @Field("apiKey") String apiKey,
                @Field("equipment_id") String imei);

        @FormUrlEncoded
        @POST("index.php?route=api/product/getReview")
        Single<HttpResult<ArrayList<ReviewEntity>>> getReviews(
                @Field("page") int page,
                @Field("limit") int limit,
                @Field("product_id") int product_id,
                @Field("timestamp") String timestamp,
                @Field("signature") String signature,
                @Field("apiKey") String apiKey,
                @Field("equipment_id") String imei);

        @FormUrlEncoded
        @POST("index.php?route=api/product/getPopularProducts")
        Single<HttpResult<ArrayList<PopularProductEntity>>> getPopularProducts(
                @Field("page") int page,
                @Field("limit") int limit,
                @Field("timestamp") String timestamp,
                @Field("signature") String signature,
                @Field("apiKey") String apiKey,
                @Field("equipment_id") String imei);

        @FormUrlEncoded
        @POST("index.php?route=api/banner")
        Single<HttpResult<ArrayList<BannerImageEntity>>> getBannerImages(
                @Field("width") String width,
                @Field("height") String height,
                @Field("timestamp") String timestamp,
                @Field("signature") String signature,
                @Field("apiKey") String apiKey,
                @Field("equipment_id") String imei);
}