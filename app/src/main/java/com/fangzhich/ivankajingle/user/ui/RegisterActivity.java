package com.fangzhich.ivankajingle.user.ui;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.ConstUtils;
import com.blankj.utilcode.utils.RegularUtils;
import com.fangzhich.ivankajingle.R;
import com.fangzhich.ivankajingle.base.ui.BaseActivity;
import com.fangzhich.ivankajingle.user.data.entity.RegisterEntity;
import com.fangzhich.ivankajingle.user.data.entity.UserInfoEntity;
import com.fangzhich.ivankajingle.user.data.net.UserApi;
import com.fangzhich.ivankajingle.user.presentation.contract.UserRegisterContract;
import com.fangzhich.ivankajingle.user.presentation.presenter.UserRegisterPresenter;
import com.fangzhich.ivankajingle.util.Const;
import com.fangzhich.ivankajingle.util.ToastUtil;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.METValidator;
import com.rengwuxian.materialedittext.validation.RegexpValidator;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import rx.SingleSubscriber;
import timber.log.Timber;

/**
 * RegisterActivity
 * Created by Khorium on 2016/9/9.
 */
public class RegisterActivity extends BaseActivity implements UserRegisterContract.View{

    public static final int IS_REGISTER = 200;
    public static final int SUCCESS = 201;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.first_name)
    MaterialEditText firstName;
    @OnTextChanged(R.id.first_name)
    void onFirstNameChanged(CharSequence s) {
        isFirstNameCorrect = s.length()>0;
        checkInput();
    }
    @OnFocusChange(R.id.first_name)
    void onFirstNameFocusChanged(boolean focused) {
        if (firstName.getText()!=null && !focused) {
            firstName.validate();
        }
    }

    @BindView(R.id.last_name)
    MaterialEditText lastName;
    @OnTextChanged(R.id.last_name)
    void onLastNameChanged(CharSequence s) {
        isLastNameCorrect = s.length()>0;
        checkInput();
    }
    @OnFocusChange(R.id.last_name)
    void onLastNameFocusChanged(boolean focused) {
        if (lastName.getText()!=null && !focused) {
            lastName.validate();
        }
    }

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

    @BindView(R.id.password)
    MaterialEditText password;
    @OnTextChanged(R.id.password)
    void onPasswordChanged(CharSequence s) {
        isPasswordCorrect = s.length()>=6;
        checkInput();
    }
    @OnFocusChange(R.id.password)
    void onPasswordFocusChanged(boolean focused) {
        if (password.getText()!=null && !focused) {
            password.validate();
        }
    }
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

    private boolean isFirstNameCorrect;
    private boolean isLastNameCorrect;
    private boolean isEmailCorrect;
    private boolean isPasswordCorrect;
    private void checkInput() {
        if (isFirstNameCorrect && isLastNameCorrect && isEmailCorrect && isPasswordCorrect) {
            btCreateAccount.setImageResource(R.mipmap.bt_create_account_clickable);
        } else {
            btCreateAccount.setImageResource(R.mipmap.bt_create_account_unclickable);
        }
    }

    private void validateInput() {
        firstName.validate();
        lastName.validate();
        email.validate();
        password.validate();
    }

    @BindView(R.id.bt_create_account)
    ImageView btCreateAccount;
    @OnClick(R.id.bt_create_account)
    void createAccount() {
        checkInput();
        if (isFirstNameCorrect && isLastNameCorrect && isEmailCorrect && isPasswordCorrect) {
            btCreateAccount.setClickable(false);
            mPresenter.register(
                    firstName.getText().toString(),
                    lastName.getText().toString(),
                    email.getText().toString(),
                    password.getText().toString());
        } else {
            validateInput();
        }
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected void initContentView() {
        setPresenter(new UserRegisterPresenter(this));
        initToolbar();
        initEditText();
    }

    private void initEditText() {

        firstName.setAutoValidate(true);
        firstName.addValidator(new METValidator(getString(R.string.NameNotValid)) {
            @Override
            public boolean isValid(@NonNull CharSequence text, boolean isEmpty) {
                return text.length()>0;
            }
        });
        firstName.setValidateOnFocusLost(false);

        lastName.setAutoValidate(true);
        lastName.addValidator(new METValidator(getString(R.string.NameNotValid)) {
            @Override
            public boolean isValid(@NonNull CharSequence text, boolean isEmpty) {
                return text.length()>0;
            }
        });
        lastName.setValidateOnFocusLost(false);

        email.setAutoValidate(true);
        email.addValidator(new RegexpValidator(getString(R.string.InValidEmail), ConstUtils.REGEX_EMAIL));
        email.setValidateOnFocusLost(false);

        password.setTypeface(Typeface.SANS_SERIF);
        password.setAutoValidate(true);
        password.addValidator(new METValidator(getString(R.string.PasswordTooShort)) {
            @Override
            public boolean isValid(@NonNull CharSequence text, boolean isEmpty) {
                return text.length()>=6;
            }
        });
        password.setValidateOnFocusLost(false);
    }


    UserRegisterPresenter mPresenter;

    @Override
    public void setPresenter(UserRegisterContract.Presenter presenter) {
        mPresenter = (UserRegisterPresenter) presenter;
    }

    @Override
    public void onRegisterSuccess(final RegisterEntity entity) {
        ToastUtil.toast(Const.User.REGISTER_SUCCESS);
        UserApi.login(email.getText().toString(), password.getText().toString(), new SingleSubscriber<UserInfoEntity>() {
            @Override
            public void onSuccess(UserInfoEntity value) {
                Timber.d(value.toString());
                Const.setLogin(true,value);
                setResult(SUCCESS);
                finish();
            }

            @Override
            public void onError(Throwable error) {
                Timber.e(error);
                btCreateAccount.setClickable(true);
                setResult(SUCCESS);
                finish();
                ToastUtil.toastLong("sorry,register success but try login failed,please login by yourself");
            }
        });
    }

    @Override
    public void onRegisterFailed(Throwable throwable) {
        Timber.e(throwable);
        ToastUtil.toast(Const.User.REGISTER_FAILED);
        btCreateAccount.setClickable(true);
    }


    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        title.setText(R.string.CreateAccount);
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
