package com.fangzhich.sneakerlab.user.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.ConstUtils;
import com.blankj.utilcode.utils.RegularUtils;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.main.ui.MainActivity;
import com.fangzhich.sneakerlab.user.data.entity.UserInfoEntity;
import com.fangzhich.sneakerlab.user.presentation.contract.UserLoginContract;
import com.fangzhich.sneakerlab.user.presentation.presenter.UserLoginPresenter;
import com.fangzhich.sneakerlab.util.Const;
import com.fangzhich.sneakerlab.util.ToastUtil;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.METValidator;
import com.rengwuxian.materialedittext.validation.RegexpValidator;

import org.androidannotations.annotations.AfterTextChange;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import timber.log.Timber;

import static com.fangzhich.sneakerlab.user.ui.RegisterActivity.SUCCESS;

/**
 * LoginActivity
 * Created by Khorium on 2016/9/12.
 */
public class LoginActivity extends BaseActivity implements UserLoginContract.View {

    public static final int IS_LOGIN = 100;

    //input check
    private boolean isEmailCorrect = false;
    private boolean isPasswordCorrect = false;

    @BindView(R.id.bt_sign_in)
    ImageView bt_sign_in;
    @OnClick(R.id.bt_sign_in)
    void signIn() {
        checkEmailAndPassword();
        if (isEmailCorrect && isPasswordCorrect) {
            mPresenter.login(email.getText().toString(), password.getText().toString());
        } else {
            validateEmailAndPassword();
        }
    }

    private void checkEmailAndPassword() {
        bt_sign_in.setImageResource(isEmailCorrect && isPasswordCorrect?R.mipmap.bt_sign_in_clickable:R.mipmap.bt_sign_in_unclickable);
    }

    private void validateEmailAndPassword() {
//        if (!isEmailCorrect) {
//            email.validate();
//        }
        if (!isPasswordCorrect) {
            password.validate();
        }
    }

    //email
    @BindView(R.id.email)
    MaterialEditText email;
    @OnTextChanged(R.id.email)
    void onEmailChanged(CharSequence s) {
        isEmailCorrect = RegularUtils.isEmail(s.toString());
        checkEmailAndPassword();
    }

//    @OnFocusChange(R.id.email)
//    void onEmailFocusChanged(boolean focused) {
//        if (email.getText()!=null && !focused) {
//            if (isEmailCorrect) {
//                email.setError(null);
//            } else {
//                email.setError("Not a valid email address");
//            }
//        }
//    }

    //password
    @BindView(R.id.password)
    MaterialEditText password;
    @OnTextChanged(R.id.password)
    void onPasswordChanged(CharSequence s) {
        isPasswordCorrect = s.length() >= 6;
        checkEmailAndPassword();
    }

//    @OnFocusChange(R.id.password)
//    void onPasswordFocusChanged(boolean focused) {
//        if (password.getText()!=null && !focused) {
//            if (isPasswordCorrect) {
//                password.setError(null);
//            } else {
//                password.setError("password too short");
//            }
//        }
//    }

    //password visible check
    @OnCheckedChanged(R.id.cb_password)
    void isChecked(boolean isChecked) {
        if (isChecked) {
            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            password.setTypeface(Typeface.SANS_SERIF);
        }
    }

    @OnClick(R.id.bt_close)
    void close() {
        finish();
    }

    @OnClick(R.id.bt_create_account)
    void createAccount() {
        startActivityForResult(new Intent(this, RegisterActivity.class), RegisterActivity.IS_REGISTER);
    }

    @OnClick(R.id.bt_forget_password)
    void forgetPassword() {
        startActivity(new Intent(this, ForgetPasswordActivity.class));
    }

    private FacebookLoginManager facebookLoginManager;

    @OnClick(R.id.bt_facebook)
    void loginByFacebook() {
        facebookLoginManager.loginByFacebook(this, new FacebookLoginManager.FacebookLoginCallBack() {
            @Override
            public void onLoginSuccess(UserInfoEntity value) {
                LoginActivity.this.onLoginSuccess(value);
            }

            @Override
            public void onLoginFailed(Throwable error) {
                LoginActivity.this.onLoginFailed(error);
            }
        });
    }


    @Override
    public int setContentLayout() {
        return R.layout.activity_sign_in;
    }

    @Override
    protected void initContentView() {
        setPresenter(new UserLoginPresenter(this));

        facebookLoginManager = new FacebookLoginManager();
        facebookLoginManager.initFaceBookLoginCallBackManager();

        email.setAutoValidate(true);
//        email.addValidator(new RegexpValidator(getString(R.string.UnValidEmail),ConstUtils.REGEX_EMAIL));
        email.setValidateOnFocusLost(false);

        password.setTypeface(Typeface.SANS_SERIF);
        password.setAutoValidate(true);
        password.addValidator(new METValidator(getString(R.string.EmailOrPasswordNotNull)) {
            @Override
            public boolean isValid(@NonNull CharSequence text, boolean isEmpty) {
                return text.length()>0;
            }
        });
        password.setValidateOnFocusLost(false);
    }

    UserLoginPresenter mPresenter;

    @Override
    public void setPresenter(UserLoginContract.Presenter presenter) {
        mPresenter = (UserLoginPresenter) presenter;
    }

    @Override
    public void onLoginSuccess(UserInfoEntity entity) {
        ToastUtil.toast(Const.User.LOGIN_SUCCESS);
        Const.setLogin(true, entity);
        setResult(SplashActivity.SUCCESS);
        finish();
    }

    @Override
    public void onLoginFailed(Throwable throwable) {
        Timber.e(throwable.getMessage());
        bt_sign_in.setClickable(true);
        password.setError(getString(R.string.EmailOrPasswordIncorrect));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            setResult(SplashActivity.FAILED);
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RegisterActivity.IS_REGISTER: {
                if (resultCode == SUCCESS) {
                    finish();
                }
            }
        }
        facebookLoginManager.onActivityResult(requestCode, resultCode, data);
    }
}
