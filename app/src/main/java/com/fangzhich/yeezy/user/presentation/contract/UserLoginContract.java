package com.fangzhich.yeezy.user.presentation.contract;

import com.fangzhich.yeezy.base.presentation.BasePresenter;
import com.fangzhich.yeezy.base.presentation.BaseView;

/**
 * UserLoginContract
 * Created by Khorium on 2016/9/14.
 */
public interface UserLoginContract {
    interface Presenter extends BasePresenter {
        void login(String email,String password);
        void loginByFaceBook();
        void forgetPassword();
    }

    interface View extends BaseView<Presenter> {
        void onLoginSuccess();
        void onLoginFailed();
    }
}
