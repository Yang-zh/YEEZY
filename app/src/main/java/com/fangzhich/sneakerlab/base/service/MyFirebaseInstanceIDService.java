package com.fangzhich.sneakerlab.base.service;

import com.fangzhich.sneakerlab.util.Const;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import timber.log.Timber;

/**
 * MyFirebaseInstanceIDService
 * Created by Khorium on 2016/10/9.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {
        Timber.d(FirebaseInstanceId.getInstance().getToken());
        Const.fireBaseMessageToken = FirebaseInstanceId.getInstance().getToken();
    }
}
