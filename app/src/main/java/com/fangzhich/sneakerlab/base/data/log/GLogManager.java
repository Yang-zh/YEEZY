package com.fangzhich.sneakerlab.base.data.log;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * GLogManager
 * Created by Khorium on 2016/11/24.
 */

public class GLogManager {

    public static void loginEntryClick(Context context) {
        createSelectContent(context, "0001","LOGIN_ENTRY", "DrawerHeaderOfMainActivity");
    }

    private static void createSelectContent(Context context, String itemId, String itemName, String contentType) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, itemId);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, itemName);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, contentType);
        FirebaseAnalytics.getInstance(context).logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }
}
