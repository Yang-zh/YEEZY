package com.fangzhich.sneakerlab.user.presentation.contract;

import com.fangzhich.sneakerlab.base.presentation.BasePresenter;
import com.fangzhich.sneakerlab.base.presentation.BaseView;
import com.fangzhich.sneakerlab.user.data.entity.RegisterEntity;

/**
 * UserRegisterContract
 * Created by Khorium on 2016/9/14.
 */
public class UserRegisterContract {
    public interface Presenter extends BasePresenter {
        void register(String firstname,String lastname,String email,String password);
    }
    public interface View extends BaseView<Presenter> {
        void onRegisterSuccess(RegisterEntity entity);
        void onRegisterFailed(Throwable throwable);
    }

}
