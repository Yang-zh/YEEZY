package com.fangzhich.yeezy.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 *
 * Created by Khorium on 2016/8/30.
 */
public class ToastUtil {

    private static Context context;
    private static boolean DEBUG = true;

    private ToastUtil() {}

    private static class SingletonHolder {
        private static ToastUtil INSTANCE = new ToastUtil();
    }

    public static ToastUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static void init(Context context, boolean isDebug){
        ToastUtil.context = context;
        ToastUtil.DEBUG = isDebug;
    }

    private static Toast toast;

    public static void toast(String message) {
        if (toast == null) {
            if (context==null) {
                logTestError("ToastUtil","ToastUtil needs initialization");
                return;
            }
            toast = Toast.makeText(context,message,Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }


    public static void logTestInfo(String tag, String message) {
        if (DEBUG) {
            Log.i(tag,message);
        }
    }

    public static void logInfo(String tag, String message) {
            Log.i(tag,message);
    }


    public static void logTestError(String tag, String message) {
        if (DEBUG) {
            Log.e(tag,message);
        }
    }

    public static void logError(String tag, String message) {
        Log.e(tag,message);
    }
}
