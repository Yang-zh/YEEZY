package com.fangzhich.yeezy.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.fangzhich.yeezy.App;
import com.fangzhich.yeezy.R;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import timber.log.Timber;

/**
 * CrashHandler
 * Created by Khorium on 2016/9/19.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private Context mContext;

    private static Thread.UncaughtExceptionHandler mDefaultHandler;

    private final String TAG = CrashHandler.class.getSimpleName();

    public CrashHandler(Context context) {
        mContext = context;
    }

    public static void init(CrashHandler crashHandler) {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Timber.e(TAG, e.toString());
        Timber.e(TAG, collectCrashDeviceInfo());
        Timber.e(TAG, getCrashInfo(e));
        mDefaultHandler.uncaughtException(t,e);
        ToastUtil.toast(mContext.getResources().getString(R.string.ApplicationCrash));
        App.getInstance().exitApp();
    }

    public String getCrashInfo(Throwable ex) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        ex.setStackTrace(ex.getStackTrace());
        ex.printStackTrace(printWriter);
        return result.toString();
    }

    public String collectCrashDeviceInfo() {
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            String versionName = pi.versionName;
            String model = android.os.Build.MODEL;
            String androidVersion = android.os.Build.VERSION.RELEASE;
            String manufacturer = android.os.Build.MANUFACTURER;
            return versionName + "  " + model + "  " + androidVersion + "  " + manufacturer;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
