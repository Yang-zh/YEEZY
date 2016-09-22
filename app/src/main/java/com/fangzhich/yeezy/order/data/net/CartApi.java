package com.fangzhich.yeezy.order.data.net;

import com.fangzhich.yeezy.base.data.net.BaseApi;

/**
 * CartApi
 * Created by Khorium on 2016/9/21.
 */

public class CartApi extends BaseApi {

//
//    /**
//     * CartList request
//     *
//     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
//     */
//    public static void getCartList(SingleSubscriber<ArrayList<CartEntity>> singleSubscriber) {
//        String timestamp = getTimeStamp();
//
//        HashMap<String,String> params = new HashMap<>();
//        params.put("email", email);
//        params.put("token", token);
//        params.put("timestamp", timestamp);
//
//        String signature = getSignature(params);
//
//        createService(CartService.class)
//                .getCartList(email,token,timestamp,signature,API_KEY, Constants.IMEI)
//                .map(new HttpResultFunc<ArrayList<CartEntity>>())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(singleSubscriber);
//    }
//
//    /**
//     * Product request
//     *
//     * @param product_id product_id of product
//     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
//     */
//    public static void getProduct(int product_id, int quantity, CartEntity.Products.Option option, String recurring_id, SingleSubscriber<ProductEntity> singleSubscriber) {
//        String timestamp = getTimeStamp();
//        Gson gson = new Gson();
//
//        HashMap<String,String> params = new HashMap<>();
//        params.put("product_id", String.valueOf(product_id));
//        params.put("quantity", String.valueOf(quantity));
//        params.put("option", gson.toJson(option));
//        params.put("recurring_id", recurring_id);
//        params.put("email", email);
//        params.put("token", token);
//        params.put("timestamp", timestamp);
//
//        String signature = getSignature(params);
//
//        createService(CartService.class)
//                .addCart(product_id,quantity,option.toString(),recurring_id,email,token,timestamp,signature,API_KEY, Constants.IMEI)
//                .map(new HttpResultFunc<ProductEntity>())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(singleSubscriber);
//    }
//
//    /**
//     * Product request
//     *
//     * @param page page
//     * @param limit limit per page
//     * @param product_id product_id of Reviews belong to
//     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
//     */
//    public static void getReviews(int page, int limit, int product_id, SingleSubscriber<ArrayList<ReviewEntity>> singleSubscriber) {
//        String timestamp = getTimeStamp();
//
//        HashMap<String,String> params = new HashMap<>();
//        params.put("page", String.valueOf(page));
//        params.put("limit", String.valueOf(limit));
//        params.put("product_id", String.valueOf(product_id));
//        params.put("timestamp", String.valueOf(timestamp));
//
//        String signature = getSignature(params);
//
//        createService(CartService.class)
//                .editCart(page,limit,product_id,timestamp,signature,API_KEY, Constants.IMEI)
//                .map(new HttpResultFunc<ArrayList<ReviewEntity>>())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(singleSubscriber);
//    }
//
//    /**
//     * Product request
//     *
//     * @param page page
//     * @param limit limit per page
//     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
//     */
//    public static void getPopularProducts(int page, int limit, SingleSubscriber<ArrayList<PopularProductEntity>> singleSubscriber) {
//        String timestamp = getTimeStamp();
//
//        HashMap<String,String> params = new HashMap<>();
//        params.put("page", String.valueOf(page));
//        params.put("limit", String.valueOf(limit));
//        params.put("timestamp", String.valueOf(timestamp));
//
//        String signature = getSignature(params);
//
//        createService(CartService.class)
//                .removeCart(page,limit,timestamp,signature,API_KEY, Constants.IMEI)
//                .map(new HttpResultFunc<ArrayList<PopularProductEntity>>())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(singleSubscriber);
//    }

}
