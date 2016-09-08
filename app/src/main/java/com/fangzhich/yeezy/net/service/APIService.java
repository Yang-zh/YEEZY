package com.fangzhich.yeezy.net.service;

import com.fangzhich.yeezy.net.Bean.LoginResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * API
 * Created by Khorium on 2016/9/8.
 */
public interface APIService {
//    $data = [
//            'token' => '64e1b8d34f425d19e1ee2ea7236d3028',
//            'email' => 'admin@admin.com',
//            'password' => '123456'
//            ];
    @FormUrlEncoded
    @POST("index.php?route=account/login")
    Observable<LoginResult> login(@Field("token") String token, @Field("email") String email, @Field("password") String password);

}
