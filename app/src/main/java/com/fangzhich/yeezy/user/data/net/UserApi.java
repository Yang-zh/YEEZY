package com.fangzhich.yeezy.user.data.net;


import com.fangzhich.yeezy.base.data.net.BaseApi;
import com.fangzhich.yeezy.user.data.entity.PersonalInfoEntity;
import com.fangzhich.yeezy.user.data.entity.UserInfoEntity;
import com.fangzhich.yeezy.user.data.entity.RegisterEntity;
import com.fangzhich.yeezy.util.Const;

import java.util.HashMap;

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
    public static void login(String email,String password,SingleSubscriber<UserInfoEntity> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        params.put("timestamp", timestamp);

        String signature = getSignature(params);

        createService(UserService.class)
                .login(email,password,timestamp,signature,API_KEY, Const.IMEI)
                .map(new HttpResultFunc<UserInfoEntity>())
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

        HashMap<String, String> params = new HashMap<>();
        params.put("firstname", firstname);
        params.put("lastname", lastname);
        params.put("email", email);
        params.put("password", password);
        params.put("timestamp", timestamp);

        String signature = getSignature(params);

        createService(UserService.class)
                .register(firstname,lastname,email,password,timestamp,signature,API_KEY, Const.IMEI)
                .map(new HttpResultFunc<RegisterEntity>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    /**
     * Sign out request
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void signOut(SingleSubscriber<Object> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("email",Const.User.email);
        params.put("token",Const.User.token);
        params.put("timestamp",timestamp);

        String signature = getSignature(params);

        createService(UserService.class)
                .signOut(Const.User.email,Const.User.token,timestamp,signature,API_KEY,Const.IMEI)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    /**
     * Personal info request
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void getPersonalInfo(SingleSubscriber<PersonalInfoEntity> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("email",Const.User.email);
        params.put("token",Const.User.token);
        params.put("timestamp",timestamp);

        String signature = getSignature(params);

        createService(UserService.class)
                .getPersonalInfo(Const.User.email,Const.User.token,timestamp,signature,API_KEY,Const.IMEI)
                .map(new HttpResultFunc<PersonalInfoEntity>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }
}


