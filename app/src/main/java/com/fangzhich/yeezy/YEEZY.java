package com.fangzhich.yeezy;

import android.app.Application;

import com.fangzhich.yeezy.util.LogUtils;

/**
 * BaseApplication
 * Created by Khorium on 2016/8/30.
 */
public class YEEZY extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.init(getApplicationContext());
    }
}
