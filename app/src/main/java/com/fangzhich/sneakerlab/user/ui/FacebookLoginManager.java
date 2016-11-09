package com.fangzhich.sneakerlab.user.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.fangzhich.sneakerlab.user.data.entity.UserInfoEntity;
import com.fangzhich.sneakerlab.user.data.net.UserApi;
import com.fangzhich.sneakerlab.util.ToastUtil;

import org.json.JSONException;

import java.util.Arrays;

import rx.SingleSubscriber;
import timber.log.Timber;

/**
 * FacebookLoginManager
 * Created by Khorium on 2016/10/26.
 */
public class FacebookLoginManager {

    private String facebookEmail;
    private String facebookId;
    private Profile profile;
    private CallbackManager callBackManager;
    private FacebookLoginCallBack callBack;


    void initFaceBookLoginCallBackManager() {
        Timber.d("Facebook Callback Manager init");
        callBackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callBackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Timber.d("Facebook login request success");
                //profile request
                sendProfileRequest(loginResult);
            }

            @Override
            public void onCancel() {
                ToastUtil.toast("login canceled");
            }

            @Override
            public void onError(FacebookException error) {
                Timber.e(error.getMessage());
                ToastUtil.toast(error.getMessage());
            }
        });
    }

    private void sendProfileRequest(final LoginResult loginResult) {
        Timber.d("send Facebook profile request");
        Profile.fetchProfileForCurrentAccessToken();
        getProfile(loginResult);
    }

    private void getProfile(LoginResult loginResult) {
        Profile currentProfile = Profile.getCurrentProfile();
        if (currentProfile!=null) {
            profile = currentProfile;
            Timber.d("Facebook profile request success");
            Timber.d("send Facebook email request");
            //email request
            sendEmailRequest(loginResult,currentProfile.getId());

            String builder = currentProfile.getId() +
                    "\n" +
                    currentProfile.getFirstName() +
                    "\n" +
                    currentProfile.getMiddleName() +
                    "\n" +
                    currentProfile.getLastName() +
                    "\n" +
                    currentProfile.getName() +
                    "\n" +
                    currentProfile.getLinkUri() +
                    "\n" +
                    currentProfile.getProfilePictureUri(150, 150);
            Timber.d(builder);
        } else {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendEmailRequest(final LoginResult loginResult, String userId) {
        new GraphRequest(
            AccessToken.getCurrentAccessToken(), userId, null, HttpMethod.GET, new GraphRequest.Callback() {
                public void onCompleted(GraphResponse response) {
                    Timber.d(response.getJSONObject().toString());
                    try {
                        facebookEmail = (String) response.getJSONObject().get("email");
                        facebookId = (String) response.getJSONObject().get("id");
                    } catch (JSONException e) {
                        Timber.e(e);
                    }
                    Timber.d("Facebook email request success");
                    Timber.d("send login by facebook request to own server");
                    requestLogin(loginResult);
                }
            }
    ).executeAsync();
    }

    private void requestLogin(LoginResult loginResult) {
//        if (TextUtils.isEmpty(facebookEmail)) {
//            ToastUtil.toast("Get your facebook email failed");
//            return;
//        }
        if (profile == null) {
            ToastUtil.toast("Get your facebook profile failed");
            return;
        }
        UserApi.loginByFacebook(loginResult.getAccessToken().getToken(),facebookId, facebookEmail, "",profile.getFirstName(), profile.getMiddleName(), profile.getLastName(), profile.getProfilePictureUri(200, 200).toString(), new SingleSubscriber<UserInfoEntity>() {
            @Override
            public void onSuccess(UserInfoEntity value) {
                callBack.onLoginSuccess(value);
            }

            @Override
            public void onError(Throwable error) {
                callBack.onLoginFailed(error);
            }
        });
    }

    void loginByFacebook(Activity activity,FacebookLoginCallBack callback) {
        this.callBack = callback;
        Timber.d("send Facebook login request");
        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("email","public_profile"));
    }

    void onActivityResult(int requestCode, int resultCode, Intent data) {
        callBackManager.onActivityResult(requestCode, resultCode, data);
    }

    interface FacebookLoginCallBack {
        void onLoginSuccess(UserInfoEntity value);
        void onLoginFailed(Throwable error);
    }
}
