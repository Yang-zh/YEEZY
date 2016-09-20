package com.fangzhich.yeezy;

import android.app.Activity;
import android.app.Application;

import com.blankj.utilcode.utils.PhoneUtils;
import com.fangzhich.yeezy.util.Constants;
import com.fangzhich.yeezy.util.ToastUtil;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;

import java.util.HashSet;

import timber.log.Timber;

/**
 * BaseApplication
 * Created by Khorium on 2016/8/30.
 */
public class App extends Application {

    private static App instance;

    private final  HashSet<Activity> allActivities = new HashSet<>();

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        //LeakCanary
        LeakCanary.install(this);

        if (BuildConfig.DEBUG) {
            //Logger
            Logger.init().hideThreadInfo();

            //Timber with Logger
            Timber.plant(new Timber.DebugTree() {
                @Override
                protected void log(int priority, String tag, String message, Throwable t) {
                    Logger.t(0).log(priority, tag, message, t);
                }
            });

            //StrictMode
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

        //ToastUtil
        ToastUtil.init(getApplicationContext(), BuildConfig.DEBUG);

        //IMEI
        Constants.IMEI = PhoneUtils.getPhoneIMEI(getApplicationContext());

    }


    public void addActivity(Activity act) {
        allActivities.add(act);
    }

    public void removeActivity(Activity act) {
        allActivities.remove(act);
    }

    public void exitApp() {
        synchronized (allActivities) {
            for (Activity act : allActivities) {
                act.finish();
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
