package com.fangzhich.yeezy;

import android.app.Application;

import com.blankj.utilcode.utils.PhoneUtils;
import com.fangzhich.yeezy.util.LogUtils;
import com.squareup.leakcanary.LeakCanary;

/**
 * BaseApplication
 * Created by Khorium on 2016/8/30.
 */
public class YEEZY extends Application {

    public static String IMEI;

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        LogUtils.init(getApplicationContext());
        IMEI = PhoneUtils.getPhoneIMEI(getApplicationContext());
    }
}
