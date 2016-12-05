package com.fangzhich.ivankajingle.util;

import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.utils.SPUtils;
import com.fangzhich.ivankajingle.base.data.event.RxBus;
import com.fangzhich.ivankajingle.base.data.net.BaseApi;
import com.fangzhich.ivankajingle.main.data.event.UserInfoRefreshEvent;
import com.fangzhich.ivankajingle.user.data.entity.UserInfoEntity;
import com.fangzhich.ivankajingle.user.data.entity.WishEntity;
import com.fangzhich.ivankajingle.user.data.net.UserApi;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.ArrayList;

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

    private static String fireBaseMessageToken;

    public static class Obj {
        public static final SPUtils AppSp = new SPUtils(mContext,SP.APP);
        public static final Gson gson = new Gson();
        public static UserInfoEntity userInfo;
        public static ArrayList<WishEntity> wishList;
    }

    public static class SP {
        static final String APP = "APP";
        public static final String USER_INFO = "UserInfo";
        public static final String IS_LOGIN = "IsLogin";
        public static final String IS_HAVE_TOKEN = "IsHavaFirebaseToken";
        public static final String FIREBASE_TOKEN = "FirebaseToken";
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

    public static void setLogin(boolean login,UserInfoEntity entity) {
        if (login) {
            setUserInfo(entity);
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
            BaseApi.refreshLoginInfo();
        }
        Obj.AppSp.putBoolean(SP.IS_LOGIN,login);
        RxBus.getDefault().post(new UserInfoRefreshEvent());
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

    public static void setFireBaseMessageToken(String token) {
        //save login status
        Const.Obj.AppSp.putBoolean(Const.SP.IS_HAVE_TOKEN,true);
        //save userInfo in SharedPreference
        Const.Obj.AppSp.putString(Const.SP.FIREBASE_TOKEN,token);
        //save userInfo in RAM
        Const.fireBaseMessageToken = token;
    }


    public static String getFireBaseMessageToken() {
        if (TextUtils.isEmpty(fireBaseMessageToken)) {
            return fireBaseMessageToken;
        }

        fireBaseMessageToken = Obj.AppSp.getString(SP.FIREBASE_TOKEN);

        return fireBaseMessageToken;
    }

    public static void addRemoteMessage(RemoteMessage remoteMessage) {
        //todo
    }

    public static void refreshAvatar(String avatar) {
        Obj.userInfo.user_info.avatar = avatar;
        setUserInfo(Obj.userInfo);
        RxBus.getDefault().post(new UserInfoRefreshEvent());
    }

    public static void refreshEmail(String newEmail) {
        Obj.userInfo.user_info.email = newEmail;
        setUserInfo(Obj.userInfo);
        BaseApi.refreshLoginInfo();
    }

    public static void refreshAge(int age) {
        Obj.userInfo.user_info.age = age;
        setUserInfo(Obj.userInfo);
    }

    public static void refreshTel(String tel) {
        Obj.userInfo.user_info.telephone = tel;
        setUserInfo(Obj.userInfo);
    }

    public static void refreshName(String firstName, String lastName) {
        Obj.userInfo.user_info.firstname = firstName;
        Obj.userInfo.user_info.lastname = lastName;
        setUserInfo(Obj.userInfo);
    }

    public static void refreshSex(int gender) {
        Obj.userInfo.user_info.sex = gender;
        setUserInfo(Obj.userInfo);
    }
}

