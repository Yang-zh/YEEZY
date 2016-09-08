package com.fangzhich.yeezy.net.service;

import com.fangzhich.yeezy.net.Bean.TestPicEntity;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * test
 * Created by Khorium on 2016/8/30.
 */
public interface PicService {

    @GET("/shots")
    Observable<ArrayList<TestPicEntity>> getPics(@Query("date") String date);
}
