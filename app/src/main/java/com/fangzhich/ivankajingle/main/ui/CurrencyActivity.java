package com.fangzhich.ivankajingle.main.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.blankj.utilcode.utils.SPUtils;
import com.fangzhich.ivankajingle.R;
import com.fangzhich.ivankajingle.base.ui.BaseActivity;
import com.fangzhich.ivankajingle.base.widget.spinner.NiceSpinner;
import com.fangzhich.ivankajingle.util.ToastUtil;

import java.util.Arrays;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * CurrencyActivity
 * Created by Khorium on 2016/9/1.
 */
public class CurrencyActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.spinner_Currency)
    NiceSpinner languageSpinner;
    SPUtils utils;

    @OnClick(R.id.bt_Currency)
    void chooseCurrency() {
        if (utils!=null&&languageSpinner!=null) {
            utils.putInt("currency", languageSpinner.getSelectedIndex());
            ToastUtil.toast("Change Language Success");
        }
        onBackPressed();
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_currency;
    }


    @Override
    protected void initContentView() {
        initToolbar();
        initSpinner();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        title.setText(R.string.Currency);
    }

    private void initSpinner() {
        utils = new SPUtils(CurrencyActivity.this,"APP");
        languageSpinner.attachDataSource(new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.currencies))));
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
        return super.onOptionsItemSelected(item);
    }
}
