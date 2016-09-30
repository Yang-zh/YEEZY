package com.fangzhich.sneakerlab.user.presentation.contract;

import com.fangzhich.sneakerlab.base.presentation.BasePresenter;
import com.fangzhich.sneakerlab.base.presentation.BaseView;
import com.fangzhich.sneakerlab.user.data.entity.UserInfoEntity;

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
