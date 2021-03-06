package com.fangzhich.sneakerlab.user.presentation.contract;

import com.fangzhich.sneakerlab.base.presentation.BasePresenter;
import com.fangzhich.sneakerlab.base.presentation.BaseView;
import com.fangzhich.sneakerlab.user.data.entity.PersonalInfoEntity;

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
