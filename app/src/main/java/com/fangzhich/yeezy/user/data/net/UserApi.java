package com.fangzhich.yeezy.user.data.net;


import com.fangzhich.yeezy.YEEZY;
import com.fangzhich.yeezy.base.data.BaseApi;
import com.fangzhich.yeezy.data.net.Bean.LoginEntity;
import com.fangzhich.yeezy.data.net.Bean.RegisterEntity;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * NetClient
 * Created by Khorium on 2016/8/30.
 */
public class UserApi extends BaseApi{

    /**
     * login Request
     *
     * @param email email
     * @param password password
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void login(String email,String password,SingleSubscriber<LoginEntity> singleSubscriber) {
        String timestamp = getTimeStamp();
        String signature = getSignature(email,password,timestamp);

        UserApi.createClientAuthorizedService(UserService.class)
                .login(email,password,timestamp,signature,API_KEY,YEEZY.IMEI)
                .map(new HttpResultFunc<LoginEntity>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    /**
     * Register request
     *
     * @param firstname firstName
     * @param lastname lastName
     * @param email email
     * @param password password
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void register(String firstname, String lastname, String email, String password, SingleSubscriber<RegisterEntity> singleSubscriber) {
        String timestamp = getTimeStamp();
        String signature = getSignature(firstname,lastname,email,password,timestamp);

        UserApi.createClientAuthorizedService(UserService.class)
                .register(firstname,lastname,email,password,timestamp,signature,API_KEY,YEEZY.IMEI)
                .map(new HttpResultFunc<RegisterEntity>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }}
