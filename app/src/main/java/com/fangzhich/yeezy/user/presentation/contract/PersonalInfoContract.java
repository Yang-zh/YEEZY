package com.fangzhich.yeezy.user.presentation.contract;

import com.fangzhich.yeezy.base.presentation.BasePresenter;
import com.fangzhich.yeezy.base.presentation.BaseView;
import com.fangzhich.yeezy.user.data.entity.PersonalInfoEntity;
import com.fangzhich.yeezy.user.data.entity.UserInfoEntity;

/**
 * PersonalInfoContract
 * Created by Khorium on 2016/9/14.
 */
public interface PersonalInfoContract {
    interface Presenter extends BasePresenter {
        void getPersonalInfo();
        void editPersonalInfo(String firstname, String lastname, String phone, String sex,String age);
    }

    interface View extends BaseView<Presenter> {
        void onGetInfoSuccess(PersonalInfoEntity entity);
        void onGetInfoFailed(Throwable throwable);
        void onEditInfoSuccess();
        void onEditInfoFailed(Throwable throwable);
    }
}
