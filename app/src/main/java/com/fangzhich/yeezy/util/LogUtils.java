package com.fangzhich.yeezy.util;

import android.content.Context;
import android.support.compat.BuildConfig;
import android.util.Log;
import android.widget.Toast;

/**
 *
 * Created by Khorium on 2016/8/30.
 */
public class LogUtils {

    private static Context context;

    private LogUtils() {}

    private static class SingletonHolder {
        private static LogUtils INSTANCE = new LogUtils();
    }

    public static LogUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static void init(Context context){
        LogUtils.context = context;
    }

    private static Toast toast;

    public static void toastInfo(String message) {
        if (toast == null) {
            if (context==null) {
                logTestError("LogUtils","LogUtils needs initialization");
                return;
            }
            toast = Toast.makeText(context,message,Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }


    public static void logTestInfo(String tag, String message) {
        if (BuildConfig.DEBUG){
            Log.i(tag,message);
        }
    }

    public static void logInfo(String tag, String message) {
            Log.i(tag,message);
    }


    public static void logTestError(String tag, String message) {
        if (BuildConfig.DEBUG){
            Log.e(tag,message);
        }
    }

    public void logError(String tag, String message) {
        Log.e(tag,message);
    }
}
