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
    public static void login(String email,
                             String password,
                             SingleSubscriber<UserInfoEntity> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        params.put("timestamp", timestamp);

        String signature = getSignature(params);

        createService(UserService.class)
                .login(email,password,
                        timestamp,signature,API_KEY, Const.IMEI)
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
    public static void register(String firstname,
                                String lastname,
                                String email,
                                String password,
                                SingleSubscriber<RegisterEntity> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String, String> params = new HashMap<>();
        params.put("firstname", firstname);
        params.put("lastname", lastname);
        params.put("email", email);
        params.put("password", password);
        params.put("timestamp", timestamp);

        String signature = getSignature(params);

        createService(UserService.class)
                .register(firstname,lastname,email,password,
                        timestamp,signature,API_KEY, Const.IMEI)
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
        params.put("email",email);
        params.put("token",token);
        params.put("timestamp",timestamp);

        String signature = getSignature(params);

        createService(UserService.class)
                .signOut(email,token,
                        timestamp,signature,API_KEY,Const.IMEI)
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
        params.put("email",email);
        params.put("token",token);
        params.put("timestamp",timestamp);

        String signature = getSignature(params);

        createService(UserService.class)
                .getPersonalInfo(email,token,
                        timestamp,signature,API_KEY,Const.IMEI)
                .map(new HttpResultFunc<PersonalInfoEntity>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }
    public static void editPersonalInfo(String firstname,
                                        String lastname,
                                        String phone,
                                        String sex,
                                        String age,
                                        SingleSubscriber<Object> singleSubscriber) {

        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("firstname",firstname);
        params.put("lastname",lastname);
        params.put("phone",phone);
        params.put("sex",sex);
        params.put("age",age);
        params.put("email",email);
        params.put("token",token);
        params.put("timestamp",timestamp);

        String signature = getSignature(params);

        createService(UserService.class)
                .editPersonalInfo(firstname, lastname, phone, sex, age,
                        email, token,
                        timestamp, signature, API_KEY, Const.IMEI)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    public static void editPassword(String old_password,
                                    String new_password,
                                    SingleSubscriber<Object> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("old_password",old_password);
        params.put("new_password",new_password);
        params.put("email",email);
        params.put("token",token);
        params.put("timestamp",timestamp);

        String signature = getSignature(params);

        createService(UserService.class)
                .editPassword(old_password, new_password,
                        email, token,
                        timestamp, signature, API_KEY,Const.IMEI)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }
}


