package com.fangzhich.sneakerlab.util;

import android.content.Context;

import com.blankj.utilcode.utils.SPUtils;
import com.fangzhich.sneakerlab.base.data.net.BaseApi;
import com.fangzhich.sneakerlab.user.data.entity.UserInfoEntity;
import com.fangzhich.sneakerlab.user.data.net.UserApi;
import com.google.gson.Gson;

import rx.SingleSubscriber;


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

    public static boolean isLogin() {
        return Obj.AppSp.getBoolean(SP.IS_LOGIN);
    }

    public static void setLogin(boolean login) {
        if (login) {
            BaseApi.refreshLoginInfo();
        } else {
            UserApi.signOut(new SingleSubscriber<Object>() {
                @Override
                public void onSuccess(Object value) {
                    ToastUtil.toast("Sign out success!");
                }

                @Override
                public void onError(Throwable error) {
                    ToastUtil.toast(error.getMessage());
                }
            });
        }
        Obj.AppSp.putBoolean(SP.IS_LOGIN,login);
    }


    public static UserInfoEntity getUserInfo() {
        if (Obj.userInfo!=null) {
            return Obj.userInfo;
        }

        String userInfo = Obj.AppSp.getString(SP.USER_INFO);

        Obj.userInfo = Obj.gson.fromJson(userInfo,UserInfoEntity.class);
        return Obj.userInfo;
    }

    public static void setUserInfo(UserInfoEntity entity) {
        //save login status
        Const.Obj.AppSp.putBoolean(Const.SP.IS_LOGIN,true);
        //save userInfo in SharedPreference
        Const.Obj.AppSp.putString(Const.SP.USER_INFO,Const.Obj.gson.toJson(entity));
        //save userInfo in RAM
        Const.Obj.userInfo = entity;
    }
}

