package com.fangzhich.sneakerlab;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.fangzhich.sneakerlab.cart.ui.PaymentManager;
import com.fangzhich.sneakerlab.util.Const;
import com.fangzhich.sneakerlab.util.Installation;
import com.fangzhich.sneakerlab.util.ToastUtil;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.orhanobut.logger.Logger;
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

    //PaymentManager for multi activities usage.
    public PaymentManager mPaymentManager;

    private static FirebaseAnalytics mFirebaseAnalytics;

    private static final String TWITTER_KEY = "wKgCh1BtodC7yY41kvy7DvRjM";
    private static final String TWITTER_SECRET = "BHZHexsHvYY5T2Vhunsq27KZU9016yrYVCNfBJMxSzLs5ZyX7c";

    @Override
    public void onCreate() {
        super.onCreate();

        Const.init(this);

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

        Timber.e("firebase initialize");
        //Firebase
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //twitter
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig), new TweetComposer());
        //IMEI
        Const.IMEI = Installation.id(this);

     }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
