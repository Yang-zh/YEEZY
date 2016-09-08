package com.fangzhich.yeezy.net;


import android.annotation.SuppressLint;

import com.fangzhich.yeezy.net.Bean.HttpResult;
import com.fangzhich.yeezy.net.Bean.LoginResult;
import com.fangzhich.yeezy.net.Bean.TestPicEntity;
import com.fangzhich.yeezy.net.framework.AccessToken;
import com.fangzhich.yeezy.net.framework.HttpException;
import com.fangzhich.yeezy.net.framework.NetClientConfig;
import com.fangzhich.yeezy.net.framework.OauthServiceGenerator;
import com.fangzhich.yeezy.net.service.APIService;
import com.fangzhich.yeezy.net.service.PicService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 *
 * Created by Khorium on 2016/8/30.
 */
public class Api {

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
                .createService(APIService.class ,NetClientConfig.CLIENT_ACCESS_TOKEN);
    }

    public static void login(String token,String email,String password , Subscriber<LoginResult> subscriber) {
        Api.createClientAuthorizedService(APIService.class)
                .login(token,email,password)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    private static class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

        @Override
        public T call(HttpResult<T> httpResult) {
            if (httpResult.code != 200) {
                throw new HttpException(httpResult.msg);
            }
            return httpResult.data;
        }
    }

}
