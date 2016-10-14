package com.fangzhich.sneakerlab.util;

import android.content.Context;
import android.widget.Toast;

import timber.log.Timber;

/**
 *
 * Created by Khorium on 2016/8/30.
 */
public class ToastUtil {

    private static Context context;

    private ToastUtil() {}

    public static void init(Context context, boolean isDebug){
        ToastUtil.context = context;
    }

    private static Toast toast;

    public static void toast(String message) {
        if (toast == null) {
            if (context==null) {
                Timber.e("ToastUtil needs initialization");
                return;
            }
            toast = Toast.makeText(context,message,Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    public static void toastLong(String message) {
        if (toast == null) {
            if (context==null) {
                Timber.e("ToastUtil needs initialization");
                return;
            }
            toast = Toast.makeText(context,message,Toast.LENGTH_LONG);
        } else {
            toast.setText(message);
        }
        toast.show();
    }
}
