package com.fangzhich.sneakerlab.user.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.blankj.utilcode.utils.ConstUtils;
import com.blankj.utilcode.utils.RegularUtils;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.user.data.entity.UserInfoEntity;
import com.fangzhich.sneakerlab.user.data.net.UserApi;
import com.fangzhich.sneakerlab.util.Const;
import com.fangzhich.sneakerlab.util.ToastUtil;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.METValidator;
import com.rengwuxian.materialedittext.validation.RegexpValidator;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import rx.SingleSubscriber;
import timber.log.Timber;

/**
 * ChangePasswordActivity
 * Created by Khorium on 2016/11/9.
 */
public class ChangeNameActivity extends BaseActivity {

    private boolean isFirstNameCorrect;
    private boolean isLastNameCorrect;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.first_name)
    MaterialEditText firstName;
    @OnTextChanged(R.id.first_name)
    void onFirstNameChanged(CharSequence s) {
        isFirstNameCorrect = s.length()>0;
        checkInput();
    }

    @BindView(R.id.last_name)
    MaterialEditText lastName;
    @OnTextChanged(R.id.last_name)
    void onLastNameChanged(CharSequence s) {
        isLastNameCorrect = s.length()>0;
        checkInput();
    }

    private void checkInput() {
    }

    private void validateInput() {
        firstName.validate();
        lastName.validate();
    }

    @BindView(R.id.bt_submit)
    CardView submit;
    @OnClick(R.id.bt_submit)
    void submit() {
        if (isFirstNameCorrect && isLastNameCorrect) {
            submit.setClickable(false);
            UserInfoEntity.UserInfo userInfo = Const.getUserInfo().user_info;
            UserApi.editPersonalInfo(firstName.getText().toString(), this.lastName.getText().toString(), userInfo.phone,String.valueOf(userInfo.sex),String.valueOf(userInfo.age), new SingleSubscriber<List>() {
                @Override
                public void onSuccess(List value) {
                    ToastUtil.toast("Change name success!");
                    finish();
                }

                @Override
                public void onError(Throwable error) {
                    ToastUtil.toast(error.getMessage());
                    submit.setClickable(false);
                    Timber.e(error);
                }
            });
        } else {
            validateInput();
        }

    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_change_name;
    }

    @Override
    protected void initContentView() {
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

    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        title.setText(R.string.Name);
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
