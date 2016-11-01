package com.fangzhich.sneakerlab;

import android.app.Application;

import com.blankj.utilcode.utils.PhoneUtils;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.fangzhich.sneakerlab.util.Const;
import com.fangzhich.sneakerlab.util.Installation;
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

    private FirebaseAnalytics mFirebaseAnalytics;

    private static final String TWITTER_KEY = "wKgCh1BtodC7yY41kvy7DvRjM";
    private static final String TWITTER_SECRET = "BHZHexsHvYY5T2Vhunsq27KZU9016yrYVCNfBJMxSzLs5ZyX7c";

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

        //Facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        //Firebase
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //twitter
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig), new TweetComposer());
        //IMEI
        Const.IMEI = Installation.id(this);
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
