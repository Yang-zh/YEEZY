package com.fangzhich.yeezy;

import android.app.Application;
import android.os.StrictMode;
import android.support.compat.*;

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
        initDebugAbout();
        initContextAbout();
        initAppComponent();
    }

    private void initDebugAbout() {
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);

//            StrictMode.setThreadPolicy(
//                    new StrictMode.ThreadPolicy.Builder()
//                            .detectAll()
//                            .penaltyDialog() // 弹出违规提示对话框
//                            .penaltyLog() // 在logcat中打印违规异常信息
//                            .build());
//            StrictMode.setVmPolicy(
//                    new StrictMode.VmPolicy.Builder()
//                            .detectAll()
//                            .penaltyLog()
//                            .build());
        }
    }

    private void initContextAbout() {
        LogUtils.init(getApplicationContext());
        IMEI = PhoneUtils.getPhoneIMEI(getApplicationContext());
    }

    private void initAppComponent() {

    }
}
