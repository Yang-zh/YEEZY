package com.fangzhich.ivankajingle.user.presentation.contract;

import com.fangzhich.ivankajingle.base.presentation.BasePresenter;
import com.fangzhich.ivankajingle.base.presentation.BaseView;
import com.fangzhich.ivankajingle.user.data.entity.UserInfoEntity;

/**
 * UserLoginContract
 * Created by Khorium on 2016/9/14.
 */
public interface UserLoginContract {
    interface Presenter extends BasePresenter {
        void login(String email,String password);
        void loginByFaceBook();
    }

    interface View extends BaseView<Presenter> {
        void onLoginSuccess(UserInfoEntity entity);
        void onLoginFailed(Throwable throwable);
    }
}
