package com.fangzhich.yeezy.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 *
 * Created by Khorium on 2016/8/30.
 */
public class LogUtils {

    private final static boolean DEBUG = true;
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

    public void toastInfo(String message) {
        if (toast == null) {
            toast = Toast.makeText(context,message,Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    public void logTestInfo(String tag, String message) {
        if (DEBUG){
            Log.i(tag,message);
        }
    }

    public void logInfo(String tag, String message) {
            Log.i(tag,message);
    }

    public void logTestError(String tag, String message) {
        if (DEBUG){
            Log.e(tag,message);
        }
    }

    public void logError(String tag, String message) {
        Log.e(tag,message);
    }
}
