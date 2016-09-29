package com.fangzhich.yeezy.order.data.net;

import com.fangzhich.yeezy.base.data.net.BaseApi;
import com.fangzhich.yeezy.order.data.entity.CountryEntity;
import com.fangzhich.yeezy.order.data.entity.DistrictEntity;
import com.fangzhich.yeezy.util.Const;

import java.util.ArrayList;
import java.util.HashMap;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * OrderApi
 * Created by Khorium on 2016/9/29.
 */

public class OrderApi extends BaseApi {

    public static void getCountries(int page, int limit, int category_id, SingleSubscriber<ArrayList<CountryEntity>> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("timestamp", timestamp);

        String signature = getSignature(params);

        createService(OrderService.class)
                .getCountries(timestamp,signature,API_KEY, Const.IMEI)
                .map(new HttpResultFunc<ArrayList<CountryEntity>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    public static void getDistricts(String country_id, SingleSubscriber<ArrayList<DistrictEntity>> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("country_id", country_id);
        params.put("timestamp", timestamp);

        String signature = getSignature(params);

        createService(OrderService.class)
                .getDistricts(country_id,timestamp,signature,API_KEY, Const.IMEI)
                .map(new HttpResultFunc<ArrayList<DistrictEntity>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }
}
