package com.fangzhich.yeezy.util;

import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.utils.SPUtils;
import com.fangzhich.yeezy.user.data.entity.UserInfoEntity;
import com.google.gson.Gson;

import timber.log.Timber;


/**
 * Const
 * Created by Khorium on 2016/9/14.
 */
public class Const {

    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public static String IMEI;

    public static class Obj {
        public static final SPUtils AppSp = new SPUtils(mContext,SP.APP);
        public static final Gson gson = new Gson();
        public static UserInfoEntity userInfo;
    }

    public static class SP {
        static final String APP = "APP";
        public static final String USER_INFO = "UserInfo";
        public static final String IS_LOGIN = "IsLogin";
    }

    public static class User {
        public static final String LOGIN_SUCCESS = "Login Success!";
        public static final String LOGIN_FAILED = "Login failed";
        public static final String REGISTER_FAILED = "Register Failed";
        public static final String REGISTER_SUCCESS = "Register Success!";
    }

    public static UserInfoEntity getUserInfo() {
        if (Obj.userInfo!=null) {
            return Obj.userInfo;
        }

        String userInfo = Obj.AppSp.getString(SP.USER_INFO);

        Obj.userInfo = Obj.gson.fromJson(userInfo,UserInfoEntity.class);
        return Obj.userInfo;
    }
}

