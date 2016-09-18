package com.fangzhich.yeezy.user.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.utils.SPUtils;
import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.base.ui.BaseActivity;
import com.fangzhich.yeezy.base.widget.CustomDialog;
import com.fangzhich.yeezy.base.widget.spinner.NiceSpinner;
import com.fangzhich.yeezy.util.LogUtils;
import com.fangzhich.yeezy.util.TagFormatUtil;

import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * UserInfoActivity
 * Created by Khorium on 2016/9/1.
 */
public class UserInfoActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;

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
                .showPopup(getWindow().getDecorView());
    }

    @BindView(R.id.spinner_sex)
    NiceSpinner sexSpinner;
    SPUtils utils;

    @OnClick(R.id.bt_facebook)
    void next() {
        //todo
    }


    @Override
    public int setContentLayout() {
        return R.layout.activity_userinfo;
    }

    @Override
    protected void initContentView() {
        initToolbar();
        initSpinner();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        title.setText(R.string.Personalize);
    }

    private void initSpinner() {
        sexSpinner.attachDataSource(new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.genders))));
    }

    @Override
    protected void loadData() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        switch (item.getItemId()) {
            case R.id.skip:
                LogUtils.getInstance().toastInfo("Skip");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_skip, menu);
        return true;
    }
}
