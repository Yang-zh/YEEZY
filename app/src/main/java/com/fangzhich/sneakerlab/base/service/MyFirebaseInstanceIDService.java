package com.fangzhich.sneakerlab.base.service;

import com.fangzhich.sneakerlab.main.data.net.MainApi;
import com.fangzhich.sneakerlab.util.Const;
import com.fangzhich.sneakerlab.util.ToastUtil;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

import rx.SingleSubscriber;
import timber.log.Timber;

/**
 * MyFirebaseInstanceIDService
 * Created by Khorium on 2016/10/9.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {
        Timber.d(FirebaseInstanceId.getInstance().getToken());
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        MainApi.refreshFireBaseToken(FirebaseInstanceId.getInstance().getToken(), new SingleSubscriber<Object>() {
            @Override
            public void onSuccess(Object value) {
                Const.setFireBaseMessageToken(FirebaseInstanceId.getInstance().getToken());
                Timber.e("refresh firebaseMessageToken success");
                ToastUtil.toast("refresh firebaseMessageToken success");
            }

            @Override
            public void onError(Throwable error) {
                Timber.e(error);
            }
        });
    }
}
