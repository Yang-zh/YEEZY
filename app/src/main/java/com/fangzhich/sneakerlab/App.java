package com.fangzhich.sneakerlab;

import android.app.Application;

import com.blankj.utilcode.utils.PhoneUtils;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.fangzhich.sneakerlab.util.Const;
import com.fangzhich.sneakerlab.util.ToastUtil;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * BaseApplication
 * Created by Khorium on 2016/8/30.
 */
public class App extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.

    @Override
    public void onCreate() {
        super.onCreate();

        Const.init(this);

        //LeakCanary
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);
        }

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
        }

        //ToastUtil
        ToastUtil.init(getApplicationContext(), BuildConfig.DEBUG);

        //IMEI
        Const.IMEI = PhoneUtils.getPhoneIMEI(getApplicationContext());
     }


//    public void addActivity(Activity act) {
//        allActivities.add(act);
//    }
//
//    public void removeActivity(Activity act) {
//        allActivities.remove(act);
//    }
//
//    public void exitApp() {
//        synchronized (allActivities) {
//            for (Activity act : allActivities) {
//                act.finish();
//            }
//        }
//        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(0);
//    }
}
