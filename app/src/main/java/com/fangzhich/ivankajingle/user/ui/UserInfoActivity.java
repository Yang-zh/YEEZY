package com.fangzhich.ivankajingle.user.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ConvertUtils;
import com.fangzhich.ivankajingle.R;
import com.fangzhich.ivankajingle.base.ui.BaseActivity;
import com.fangzhich.ivankajingle.base.widget.CustomDialog;
import com.fangzhich.ivankajingle.base.widget.spinner.NiceSpinner;
import com.fangzhich.ivankajingle.main.ui.MainActivity;
import com.fangzhich.ivankajingle.user.data.entity.PersonalInfoEntity;
import com.fangzhich.ivankajingle.user.presentation.contract.PersonalInfoContract;
import com.fangzhich.ivankajingle.user.presentation.contract.PersonalInfoPresenter;
import com.fangzhich.ivankajingle.util.Const;
import com.fangzhich.ivankajingle.util.ToastUtil;
import com.fangzhich.ivankajingle.util.TagFormatUtil;

import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * UserInfoActivity
 * Created by Khorium on 2016/9/1.
 */
public class UserInfoActivity extends BaseActivity implements PersonalInfoContract.View{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_layout)
    RelativeLayout titleLayout;

    private PersonalInfoContract.Presenter mPresenter;

    @BindView(R.id.tv_birthday)
    TextView tvBirthday;

    @OnClick(R.id.bt_birthday)
    void changeBirthday() {
        new CustomDialog()
                .initPopup(this, R.layout.dialog_date_picker, new CustomDialog.Listener() {
                    DatePicker picker;
                    CardView btConfirm;

                    @Override
                    public void onInit(final PopupWindow dialog, View content) {
                        final Calendar calendar = Calendar.getInstance();
                        picker = (DatePicker) content.findViewById(R.id.datePicker);
                        picker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), new DatePicker.OnDateChangedListener() {
                            @Override
                            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            }
                        });
                        btConfirm = (CardView) content.findViewById(R.id.bt_save_date);
                        btConfirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                tvBirthday.setText(TagFormatUtil
                                        .from(getResources().getString(R.string.Date))
                                        .with("year",picker.getYear())
                                        .with("month",picker.getMonth())
                                        .with("day",picker.getDayOfMonth())
                                        .format());
                                dialog.dismiss();
                            }
                        });
                    }

                    @Override
                    public void onDismiss(final PopupWindow dialog, View content) {

                    }
                })
                .showPopup(getWindow().getDecorView(), Gravity.BOTTOM);
    }

    @BindView(R.id.spinner_sex)
    NiceSpinner sexSpinner;

    @OnClick(R.id.bt_next)
    void next() {
        mPresenter.editPersonalInfo(Const.getUserInfo().user_info.firstname,
                Const.getUserInfo().user_info.lastname,
                Const.getUserInfo().user_info.telephone,
                String.valueOf(sexSpinner.getSelectedIndex()+1),
                String.valueOf(Calendar.getInstance().get(Calendar.YEAR)-Integer.valueOf(tvBirthday.getText().toString().split("-")[0])));
        setResult(RegisterActivity.SUCCESS);
        if (getIntent().getBooleanExtra("isFirstRegister",false)) {
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("isFirstRegister",false);
            startActivity(intent);
        }
        finish();
    }


    @Override
    public int setContentLayout() {
        setPresenter(new PersonalInfoPresenter(this));
        return R.layout.activity_userinfo;
    }

    @Override
    protected void initContentView() {
        initToolbar();
        initSpinner();
    }

    private void initToolbar() {
        boolean isFirstRegister = getIntent().getBooleanExtra("isFirstRegister",false);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            if (!isFirstRegister) {
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
            } else {
                ((Toolbar.LayoutParams)titleLayout.getLayoutParams()).setMargins(ConvertUtils.dp2px(this,60),0,0,0);
            }
        }
        title.setText(R.string.Personalize);
    }

    private void initSpinner() {
        sexSpinner.attachDataSource(new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.genders))));
    }

    @Override
    protected void loadData() {
        mPresenter.getPersonalInfo();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        switch (item.getItemId()) {
            case R.id.skip:
                Intent intent = new Intent(this,MainActivity.class);
                intent.putExtra("isFirstRegister",false);
                startActivity(intent);
                setResult(RegisterActivity.SUCCESS);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getIntent().getBooleanExtra("isFirstRegister",false)) {
            getMenuInflater().inflate(R.menu.menu_skip, menu);
        }
        return true;
    }

    @Override
    public void onGetInfoSuccess(PersonalInfoEntity entity) {
        tvBirthday.setText(TagFormatUtil
                .from(getResources().getString(R.string.Date))
                .with("year",Calendar.getInstance().get(Calendar.YEAR)-entity.age)
                .with("month",1)
                .with("day",1)
                .format());
        sexSpinner.setSelectedIndex(entity.sex );
    }

    @Override
    public void onGetInfoFailed(Throwable throwable) {
        Timber.e(throwable);
//        ToastUtil.toast(throwable.getMessage());
    }

    @Override
    public void onEditInfoSuccess() {
        ToastUtil.toast("Change PersonalInfo Success!");
    }

    @Override
    public void onEditInfoFailed(Throwable throwable) {
        Timber.e(throwable);
        ToastUtil.toast(throwable.getMessage());
    }

    @Override
    public void setPresenter(PersonalInfoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onBackPressed() {
        if (getIntent().getBooleanExtra("isFirstRegister",false)) {
            return;
        }
        super.onBackPressed();
    }
}
