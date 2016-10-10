package com.fangzhich.sneakerlab.base.service;

import com.fangzhich.sneakerlab.util.Const;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * MyFirebaseInstanceIDService
 * Created by Khorium on 2016/10/9.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        Const.fireBaseMessageToken = FirebaseInstanceId.getInstance().getToken();
    }
}
