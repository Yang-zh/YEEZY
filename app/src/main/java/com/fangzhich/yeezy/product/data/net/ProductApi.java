package com.fangzhich.yeezy.product.data.net;

import com.fangzhich.yeezy.base.data.net.BaseApi;
import com.fangzhich.yeezy.product.data.entity.BannerImageEntity;
import com.fangzhich.yeezy.product.data.entity.ProductEntity;
import com.fangzhich.yeezy.product.data.entity.ProductItemEntity;
import com.fangzhich.yeezy.product.data.entity.RecommendProductEntity;
import com.fangzhich.yeezy.product.data.entity.ReviewEntity;
import com.fangzhich.yeezy.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * ProductApi
 * Created by Khorium on 2016/9/18.
 */
public class ProductApi extends BaseApi {

    /**
     * Products request
     *
     * @param page page
     * @param limit limit per page
     * @param category_id product's category_id
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void getProducts(int page, int limit, int category_id, SingleSubscriber<ArrayList<ProductItemEntity>> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put("limit", String.valueOf(limit));
        params.put("category_id", String.valueOf(category_id));
        params.put("timestamp", timestamp);

        String signature = getSignature(params);

        Timber.d("sendProductListRequest");
        createService(ProductService.class)
                .getProducts(page,limit,category_id,timestamp,signature,API_KEY, Constants.IMEI)
                .map(new HttpResultFunc<ArrayList<ProductItemEntity>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    /**
     * Product request
     *
     * @param product_id product_id of product
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void getProduct(int product_id, SingleSubscriber<ProductEntity> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("product_id", String.valueOf(product_id));
        params.put("timestamp", String.valueOf(timestamp));

        String signature = getSignature(params);

        createService(ProductService.class)
                .getProduct(product_id,timestamp,signature,API_KEY, Constants.IMEI)
                .map(new HttpResultFunc<ProductEntity>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    /**
     * Product request
     *
     * @param page page
     * @param limit limit per page
     * @param product_id product_id of Reviews belong to
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void getReviews(int page, int limit, int product_id, SingleSubscriber<ArrayList<ReviewEntity>> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put("limit", String.valueOf(limit));
        params.put("product_id", String.valueOf(product_id));
        params.put("timestamp", String.valueOf(timestamp));

        String signature = getSignature(params);

        createService(ProductService.class)
                .getReviews(page,limit,product_id,timestamp,signature,API_KEY, Constants.IMEI)
                .map(new HttpResultFunc<ArrayList<ReviewEntity>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    /**
     * Product request
     *
     * @param page page
     * @param limit limit per page
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void getPopularProducts(int page, int limit, SingleSubscriber<ArrayList<RecommendProductEntity>> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put("limit", String.valueOf(limit));
        params.put("timestamp", String.valueOf(timestamp));

        String signature = getSignature(params);

        createService(ProductService.class)
                .getPopularProducts(page,limit,timestamp,signature,API_KEY, Constants.IMEI)
                .map(new HttpResultFunc<ArrayList<RecommendProductEntity>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    /**
     * Product request
     *
     * @param width banner image's width
     * @param height banner image's height
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void getBannerImages(String width, String height, SingleSubscriber<ArrayList<BannerImageEntity>> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("width", String.valueOf(width));
        params.put("height", String.valueOf(height));
        params.put("timestamp", String.valueOf(timestamp));

        String signature = getSignature(params);

        createService(ProductService.class)
                .getBannerImages(width,height,timestamp,signature,API_KEY, Constants.IMEI)
                .map(new HttpResultFunc<ArrayList<BannerImageEntity>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }
}
