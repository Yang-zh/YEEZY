package com.fangzhich.sneakerlab.user.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.blankj.utilcode.utils.ConstUtils;
import com.blankj.utilcode.utils.RegularUtils;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.user.data.net.UserApi;
import com.fangzhich.sneakerlab.util.Const;
import com.fangzhich.sneakerlab.util.ToastUtil;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;

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
public class ChangeEmailActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private boolean isEmailCorrect;

    private String newEmailRequest;

    @BindView(R.id.email)
    MaterialEditText email;
    @OnTextChanged(R.id.email)
    void onEmailChanged(CharSequence s) {
        isEmailCorrect = RegularUtils.isEmail(s.toString());
        checkInput();
    }
    @OnFocusChange(R.id.email)
    void onEmailFocusChanged(boolean focused) {
        if (email.getText()!=null && !focused) {
            email.validate();
        }
    }

    private void checkInput() {

    }

    private void validateInput() {
        email.validate();
    }

    @BindView(R.id.bt_submit)
    CardView submit;
    @OnClick(R.id.bt_submit)
    void submit() {
        if (isEmailCorrect) {
            submit.setClickable(false);
            newEmailRequest = email.getText().toString();
            UserApi.editEmail(newEmailRequest, new SingleSubscriber<Object>() {
                @Override
                public void onSuccess(Object value) {
                    ToastUtil.toast("Change email success!");
                    Const.refreshEmail(newEmailRequest);
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
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_change_email;
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
        title.setText(R.string.Email);
    }

    private void initEditText() {
        email.setText(Const.getUserInfo().user_info.email);
        email.setAutoValidate(true);
        email.addValidator(new RegexpValidator(getString(R.string.InValidEmail), ConstUtils.REGEX_EMAIL));
        email.setValidateOnFocusLost(false);
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
