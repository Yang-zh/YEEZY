package com.fangzhich.yeezy;

import android.app.Application;

import com.blankj.utilcode.utils.PhoneUtils;
import com.fangzhich.yeezy.net.Api;
import com.fangzhich.yeezy.util.LogUtils;
import com.squareup.leakcanary.LeakCanary;

/**
 * BaseApplication
 * Created by Khorium on 2016/8/30.
 */
public class YEEZY extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        LogUtils.init(getApplicationContext());
        Api.IMEI = PhoneUtils.getPhoneIMEI(getApplicationContext());
    }
}
