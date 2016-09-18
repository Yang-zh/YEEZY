package com.fangzhich.yeezy.user.presentation.presenter;

import com.fangzhich.yeezy.user.data.entity.RegisterEntity;
import com.fangzhich.yeezy.user.data.net.UserApi;
import com.fangzhich.yeezy.user.presentation.contract.UserRegisterContract;

import rx.SingleSubscriber;

/**
 * UserRegisterPresenter
 * Created by Khorium on 2016/9/14.
 */
public class UserRegisterPresenter implements UserRegisterContract.Presenter {

    UserRegisterContract.View mView;

    public UserRegisterPresenter(UserRegisterContract.View view){
        mView = view;
    }

    @Override
    public void register(String firstname,String lastname,String email,String password) {
        UserApi.register(firstname, lastname, email, password, new SingleSubscriber<RegisterEntity>() {
            @Override
            public void onSuccess(RegisterEntity value) {
                mView.onRegisterSuccess(value);
            }

            @Override
            public void onError(Throwable throwable) {
                mView.onRegisterFailed(throwable);
            }
        });
    }
}
