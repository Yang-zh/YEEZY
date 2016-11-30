package com.fangzhich.ivankajingle.user.ui;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.widget.TextView;

import com.fangzhich.ivankajingle.R;
import com.fangzhich.ivankajingle.base.ui.BaseActivity;
import com.fangzhich.ivankajingle.user.data.net.UserApi;
import com.fangzhich.ivankajingle.util.ToastUtil;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.METValidator;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import rx.SingleSubscriber;
import timber.log.Timber;

/**
 * ChangePasswordActivity
 * Created by Khorium on 2016/11/9.
 */
public class ChangePasswordActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private boolean isOldPasswordCorrect;
    private boolean isNewPasswordCorrect;

    @BindView(R.id.old_password)
    MaterialEditText oldPassword;
    @OnTextChanged(R.id.old_password)
    void onOldPasswordChanged(CharSequence s) {
        isOldPasswordCorrect = s.length()>=6;
        checkInput();
    }
    @OnFocusChange(R.id.old_password)
    void onOldPasswordFocusChanged(boolean focused) {
        if (oldPassword.getText()!=null && !focused) {
            oldPassword.validate();
        }
    }
    //password visible check
    @OnCheckedChanged(R.id.cb_old_password)
    void isCBOldChecked(boolean isChecked) {
        if (isChecked) {
            oldPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            oldPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            oldPassword.setTypeface(Typeface.SANS_SERIF);
        }
    }


    @BindView(R.id.new_password)
    MaterialEditText newPassword;
    @OnTextChanged(R.id.new_password)
    void onNewPasswordChanged(CharSequence s) {
        isNewPasswordCorrect = s.length()>=6;
        checkInput();
    }
    @OnFocusChange(R.id.new_password)
    void onNewPasswordFocusChanged(boolean focused) {
        if (newPassword.getText()!=null && !focused) {
            newPassword.validate();
        }
    }
    //password visible check
    @OnCheckedChanged(R.id.cb_new_password)
    void isCBNewChecked(boolean isChecked) {
        if (isChecked) {
            newPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            newPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            newPassword.setTypeface(Typeface.SANS_SERIF);
        }
    }

    private void checkInput() {
    }

    private void validateInput() {
        oldPassword.invalidate();
        newPassword.invalidate();
    }

    @BindView(R.id.bt_change_password)
    CardView submit;
    @OnClick(R.id.bt_change_password)
    void changePassword() {
        if (isNewPasswordCorrect && isOldPasswordCorrect) {
            submit.setClickable(false);
            UserApi.editPassword(oldPassword.getText().toString(), this.newPassword.getText().toString(), new SingleSubscriber<Object>() {
                @Override
                public void onSuccess(Object value) {
                    ToastUtil.toast("Change password success!");
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
        return R.layout.activity_change_password;
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
        title.setText(R.string.ChangePassword);
    }

    private void initEditText() {
        oldPassword.setTypeface(Typeface.SANS_SERIF);
        oldPassword.setAutoValidate(true);
        oldPassword.addValidator(new METValidator(getString(R.string.PasswordTooShort)) {
            @Override
            public boolean isValid(@NonNull CharSequence text, boolean isEmpty) {
                return text.length()>=6;
            }
        });
        oldPassword.setValidateOnFocusLost(false);

        newPassword.setTypeface(Typeface.SANS_SERIF);
        newPassword.setAutoValidate(true);
        newPassword.addValidator(new METValidator(getString(R.string.PasswordTooShort)) {
            @Override
            public boolean isValid(@NonNull CharSequence text, boolean isEmpty) {
                return text.length()>=6;
            }
        });
        newPassword.setValidateOnFocusLost(false);
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
