package com.fangzhich.yeezy.user.presentation.contract;

import com.fangzhich.yeezy.user.data.entity.PersonalInfoEntity;
import com.fangzhich.yeezy.user.data.net.UserApi;
import com.fangzhich.yeezy.util.Const;

import rx.SingleSubscriber;

/**
 * PersonalInfoPresenter
 * Created by Khorium on 2016/9/27.
 */

public class PersonalInfoPresenter implements PersonalInfoContract.Presenter {

    PersonalInfoContract.View mView;

    public PersonalInfoPresenter(PersonalInfoContract.View view) {
        mView = view;
    }

    @Override
    public void getPersonalInfo() {
        UserApi.getPersonalInfo(new SingleSubscriber<PersonalInfoEntity>() {
            @Override
            public void onSuccess(PersonalInfoEntity value) {
                mView.onGetInfoSuccess(value);
            }

            @Override
            public void onError(Throwable error) {
                mView.onGetInfoFailed(error);
            }
        });
    }

    @Override
    public void editPersonalInfo(String firstname, String lastname, String phone, String sex, String age) {
        UserApi.editPersonalInfo(firstname, lastname, phone, sex, age, new SingleSubscriber<Object>() {
            @Override
            public void onSuccess(Object value) {
                mView.onEditInfoSuccess();
            }

            @Override
            public void onError(Throwable error) {
                mView.onEditInfoFailed(error);
            }
        });
    }
}
