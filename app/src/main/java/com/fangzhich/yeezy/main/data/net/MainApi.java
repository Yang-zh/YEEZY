package com.fangzhich.yeezy.main.data.net;


import com.fangzhich.yeezy.base.data.net.BaseApi;
import com.fangzhich.yeezy.main.data.net.entity.CategoryEntity;
import com.fangzhich.yeezy.util.Const;

import java.util.ArrayList;
import java.util.HashMap;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * NetClient
 * Created by Khorium on 2016/8/30.
 */
public class MainApi extends BaseApi {
    /**
     * Categories request
     *
     * @param page             page
     * @param limit            limit per page
     * @param parent_id        category's parent_id
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void getCategories(int page, int limit, int parent_id, SingleSubscriber<ArrayList<CategoryEntity>> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put("limit", String.valueOf(limit));
        params.put("parent_id", String.valueOf(parent_id));
        params.put("timestamp", timestamp);

        String signature = getSignature(params);

        createService(MainService.class)
                .getCategories(page, limit, parent_id, timestamp, signature, API_KEY, Const.IMEI)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    public static void getCategories(SingleSubscriber<ArrayList<CategoryEntity>> singleSubscriber) {
        getCategories(1, 20, singleSubscriber);
    }

    public static void getCategories(int page, int limit, SingleSubscriber<ArrayList<CategoryEntity>> singleSubscriber) {
        getCategories(page, limit, 0, singleSubscriber);
    }
}
