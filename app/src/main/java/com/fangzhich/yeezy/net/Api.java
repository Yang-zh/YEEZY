package com.fangzhich.yeezy.net;


import com.blankj.utilcode.utils.EncryptUtils;
import com.fangzhich.yeezy.net.Bean.HttpResult;
import com.fangzhich.yeezy.net.Bean.LoginEntity;
import com.fangzhich.yeezy.net.Bean.RegisterEntity;
import com.fangzhich.yeezy.net.framework.HttpResultException;
import com.fangzhich.yeezy.net.framework.OauthServiceGenerator;
import com.fangzhich.yeezy.net.service.APIService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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

    public static void login(String email,String password , Subscriber<LoginEntity> subscriber) {
        String timestamp = String.valueOf(System.currentTimeMillis());HashMap<String,String> params = new HashMap<>();
        params.put("timestamp",timestamp);

        params.put("email",email);
        params.put("password",password);

        Api.createClientAuthorizedService(APIService.class)
                .login(API_KEY,IMEI,timestamp,email,password,getSignature(params))
                .map(new HttpResultFunc<LoginEntity>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public static void register(String firstname, String lastname, String email, String password, Subscriber<RegisterEntity> subscriber) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        HashMap<String,String> params = new HashMap<>();
        params.put("timestamp",timestamp);

        params.put("firstname",firstname);
        params.put("lastname",lastname);
        params.put("email",email);
        params.put("password",password);

        Api.createClientAuthorizedService(APIService.class)
                .register(API_KEY,IMEI,timestamp,firstname,lastname,email,password,getSignature(params))
                .map(new HttpResultFunc<RegisterEntity>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private static String getSignature(HashMap<String,String> params) {
        params.put("appKey",APP_KEY);
        params.put("apiKey",API_KEY);
        params.put("equipment_id",IMEI);
        List<String> list = new ArrayList<>(params.keySet());

        Collections.sort(list);

        StringBuilder builder = new StringBuilder();

        for (String key:list) {
            builder.append(key).append(params.get(key));
        }

        String origin = builder.toString();

        return EncryptUtils.encryptSHA1ToString(EncryptUtils.encryptMD5ToString(origin));
    }

    protected static class HttpResultFunc<T> implements Func1<HttpResult<T>,T>{
        @Override
        public T call(HttpResult<T> HttpResult) {
            if (HttpResult.status_code!=0) {
                throw new HttpResultException(HttpResult.message);
            }
            return HttpResult.data;
        }
    }
}
