package com.fangzhich.yeezy.user.presentation.presenter;


import com.fangzhich.yeezy.user.data.entity.LoginEntity;
import com.fangzhich.yeezy.user.data.net.UserApi;
import com.fangzhich.yeezy.user.presentation.contract.UserLoginContract;

import rx.SingleSubscriber;

/**
 *
 * Created by Khorium on 2016/9/13.
 */
public class UserLoginPresenter implements UserLoginContract.Presenter {

    UserLoginContract.View mView;

    public UserLoginPresenter(UserLoginContract.View view) {
        mView = view;
    }

    public void login(String email, String password) {
        UserApi.login(email, password, new SingleSubscriber<LoginEntity>() {
            @Override
            public void onSuccess(LoginEntity entity) {
                mView.onLoginSuccess(entity);
            }

            @Override
            public void onError(Throwable throwable) {
                mView.onLoginFailed(throwable);
            }
        });
    }

    @Override
    public void loginByFaceBook() {

    }

    @Override
    public void forgetPassword() {

    }
}
