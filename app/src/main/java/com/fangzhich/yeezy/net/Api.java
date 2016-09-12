package com.fangzhich.yeezy.net;


import com.blankj.utilcode.utils.EncryptUtils;
import com.blankj.utilcode.utils.TimeUtils;
import com.fangzhich.yeezy.net.Bean.HttpResult;
import com.fangzhich.yeezy.net.Bean.LoginResult;
import com.fangzhich.yeezy.net.framework.HttpException;
import com.fangzhich.yeezy.net.framework.OauthServiceGenerator;
import com.fangzhich.yeezy.net.service.APIService;
import com.fangzhich.yeezy.util.LogUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 *
 * Created by Khorium on 2016/8/30.
 */
public class Api {

    public final static String BASE_URL = "http://192.168.0.105/";

    public final static String APP_KEY = "NDk4ZWNhOGNiZGExY2Y5MjhmYzdhYTljMzU2OTQwMDg";

    private static final String API_KEY = "ok9FUxp8YADCQyPmjfPthKcE9tkKHgoRXvFtzaPQAM9GVsIFZkpPZzfvc3I4bVywJxnIhkOwiF94EvpgsANpwbxtuTN0aVL18ro1oNtiMBqbPaiT4mLuljWc76jcip4ZijUsFHRnaQIdSfTKHJueVEdCaARwAf3XLZYcRWS5hafhAnSMujmkgdZcuOfpUGy0K8sUeTswEUiVBiBq8qzMzp8GpfKRdjJmgxEsjBOpsKVDEWbY4vaOW5lomG14KtxP";

    public final static int TIME_OUT = 5;

    public static String IMEI = "";

    private Api() {
    }

    private static class SingletonHolder {
        private final static Api INSTANCE = new Api();
    }

    public static Api getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static <S> APIService createClientAuthorizedService(Class<S> apiService) {
        return OauthServiceGenerator
                .createService(APIService.class);
    }

    public static void login(String timestamp, String email,String password , Subscriber<LoginResult> subscriber) {
        Api.createClientAuthorizedService(APIService.class)
                .login(API_KEY,IMEI,timestamp,email,password,getSignature(getLoginMap(APP_KEY,API_KEY,IMEI,timestamp,email,password)))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private static Map<String,String> getLoginMap(String appKey,String apiKey, String imei, String timestamp, String email, String password) {
        HashMap<String,String> params = new HashMap<>();
        params.put("appKey",appKey);
        params.put("apiKey",apiKey);
        params.put("equipment_id",imei);
        params.put("timestamp",timestamp);
        params.put("email",email);
        params.put("password",password);
        return params;
    }

    private static String getTimeStamp() {
        return String.valueOf(TimeUtils.getCurTimeMills());
    }

    private static String getSignature(Map<String,String> params) {
        List<String> list = new ArrayList<>(params.keySet());

        Collections.sort(list);

        StringBuilder builder = new StringBuilder();

        for (String key:list) {
            builder.append(key).append(params.get(key));
        }

        String origin = builder.toString();

        String MD5 = EncryptUtils.encryptMD5ToString(origin);

        String SHA1 = EncryptUtils.encryptSHA1ToString(MD5);

        LogUtils.getInstance().logTestError("network",SHA1);

        return SHA1;
    }

//    private static class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
//
//        @Override
//        public T call(HttpResult<T> httpResult) {
//            if (httpResult.code != 200) {
//                throw new HttpException(httpResult.msg);
//            }
//            return httpResult.data;
//        }
//    }

}
