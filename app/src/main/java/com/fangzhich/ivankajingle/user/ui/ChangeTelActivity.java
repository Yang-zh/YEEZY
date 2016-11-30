package com.fangzhich.ivankajingle.user.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.fangzhich.ivankajingle.R;
import com.fangzhich.ivankajingle.base.ui.BaseActivity;
import com.fangzhich.ivankajingle.user.data.entity.UserInfoEntity;
import com.fangzhich.ivankajingle.user.data.net.UserApi;
import com.fangzhich.ivankajingle.util.Const;
import com.fangzhich.ivankajingle.util.ToastUtil;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.METValidator;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import rx.SingleSubscriber;
import timber.log.Timber;

/**
 * ChangePasswordActivity
 * Created by Khorium on 2016/11/9.
 */
public class ChangeTelActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private boolean isTelCorrect;
    @BindView(R.id.tel)
    MaterialEditText tel;
    @OnTextChanged(R.id.tel)
    void onTelFocusChanged(CharSequence s) {
        isTelCorrect = s.length()>=6;
        checkInput();
    }
    @OnFocusChange(R.id.tel)
    void onTelFocusChanged(boolean focused) {
        if (tel.getText()!=null && !focused) {
            tel.validate();
        }
    }

    private void checkInput() {

    }

    private void validateInput() {
        tel.validate();
    }

    @BindView(R.id.bt_submit)
    CardView submit;
    @OnClick(R.id.bt_submit)
    void submit() {
        if (isTelCorrect) {
            submit.setClickable(false);
            UserInfoEntity.UserInfo userInfo = Const.getUserInfo().user_info;
            UserApi.editPersonalInfo(userInfo.firstname, userInfo.lastname, tel.getText().toString(),String.valueOf(userInfo.sex),String.valueOf(userInfo.age), new SingleSubscriber<List>() {
                @Override
                public void onSuccess(List value) {
                    ToastUtil.toast("Change tel success!");
                    Const.refreshTel(tel.getText().toString());
                    setResult(UserEditInfoActivity.CHANGE_SUCCESS);
                    finish();
                }

                @Override
                public void onError(Throwable error) {
                    ToastUtil.toast(error.getMessage());
                    submit.setClickable(true);
                    Timber.e(error);
                }
            });
        } else {
            validateInput();
        }
//        String newPassword = this.newPassword.getText().toString();
//
//        if (newPassword.length()<6) {
//            ToastUtil.toast("New password must longer than 6");
//            return;
//        }
//
//        UserApi.editPassword(oldPassword.getText().toString(), this.newPassword.getText().toString(), new SingleSubscriber<Object>() {
//            @Override
//            public void onSuccess(Object value) {
//                ToastUtil.toast("Change password success!");
//                finish();
//            }
//
//            @Override
//            public void onError(Throwable error) {
//                ToastUtil.toast(error.getMessage());
//                Timber.e(error);
//            }
//        });
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_change_tel;
    }

    @Override
    protected void initContentView() {
        initToolbar();
        initEditText();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        title.setText(R.string.Tel);
    }

    private void initEditText() {
        tel.setText(Const.getUserInfo().user_info.telephone);
        tel.setAutoValidate(true);
        tel.addValidator(new METValidator(getString(R.string.SeemNotValidTel)) {
            @Override
            public boolean isValid(@NonNull CharSequence text, boolean isEmpty) {
                return text.length()>=6;
            }
        });
        tel.setValidateOnFocusLost(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
