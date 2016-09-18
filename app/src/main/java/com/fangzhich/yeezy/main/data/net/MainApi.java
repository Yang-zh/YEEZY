package com.fangzhich.yeezy.main.data.net;


import com.fangzhich.yeezy.YEEZY;
import com.fangzhich.yeezy.base.data.net.BaseApi;
import com.fangzhich.yeezy.main.data.net.Bean.CategoryEntity;

import java.util.ArrayList;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * NetClient
 * Created by Khorium on 2016/8/30.
 */
public class MainApi extends BaseApi{
    /**
     * Categories request
     *
     * @param page page
     * @param limit limit per page
     * @param parent_id category's parent_id
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void getCategories(int page, int limit, int parent_id, SingleSubscriber<ArrayList<CategoryEntity>> singleSubscriber) {
        String timestamp = getTimeStamp();
        String signature = getSignature(page,limit,parent_id,timestamp);
        createClientAuthorizedService(MainService.class)
                .getCategories(page,limit,parent_id,timestamp,signature,API_KEY,YEEZY.IMEI)
                .map(new HttpResultFunc<ArrayList<CategoryEntity>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    public static void getCategories(SingleSubscriber<ArrayList<CategoryEntity>> singleSubscriber) {
        getCategories(1,20,singleSubscriber);
    }

    public static void getCategories(int page, int limit, SingleSubscriber<ArrayList<CategoryEntity>> singleSubscriber) {
        getCategories(page,limit,0,singleSubscriber);
    }
}
