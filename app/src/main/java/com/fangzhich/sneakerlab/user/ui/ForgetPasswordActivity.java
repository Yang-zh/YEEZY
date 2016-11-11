package com.fangzhich.sneakerlab.user.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.blankj.utilcode.utils.ConstUtils;
import com.blankj.utilcode.utils.RegularUtils;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.user.data.net.UserApi;
import com.fangzhich.sneakerlab.util.ToastUtil;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import rx.SingleSubscriber;

/**
 * ForgetPasswordActivity
 * Created by Khorium on 2016/9/27.
 */
public class ForgetPasswordActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;


    private boolean isEmailCorrect;

    @BindView(R.id.et_email)
    MaterialEditText etEmail;
    @OnTextChanged(R.id.et_email)
    void onEmailChanged(CharSequence s) {
        isEmailCorrect = RegularUtils.isEmail(s.toString());
        checkInput();
    }

    private void checkInput() {

    }

    private void validateInput() {
        etEmail.validate();
    }

    @OnClick(R.id.bt_submit)
    void submit() {
        if (!isEmailCorrect) {
            validateInput();
            return;
        }
        UserApi.forgetPassword(etEmail.getText().toString(), new SingleSubscriber<Object>() {
            @Override
            public void onSuccess(Object value) {
                ToastUtil.toast("your new password has been sent to your email");
            }

            @Override
            public void onError(Throwable error) {
                ToastUtil.toast("send email failed, please retry or contact us by service@fangzhich.com");
            }
        });
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void initContentView() {
        initToolbar();
        initEditText();
    }

    private void initEditText() {
        etEmail.setAutoValidate(true);
        etEmail.addValidator(new RegexpValidator(getString(R.string.InValidEmail), ConstUtils.REGEX_EMAIL));
        etEmail.setValidateOnFocusLost(false);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        title.setText(R.string.ResetPassword);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
