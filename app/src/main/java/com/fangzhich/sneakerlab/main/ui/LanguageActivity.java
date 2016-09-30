package com.fangzhich.sneakerlab.main.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.blankj.utilcode.utils.SPUtils;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.base.widget.spinner.NiceSpinner;
import com.fangzhich.sneakerlab.util.ToastUtil;

import java.util.Arrays;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * LanguageActivity
 * Created by Khorium on 2016/9/1.
 */
public class LanguageActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.spinner_language)
    NiceSpinner languageSpinner;
    SPUtils utils;

    @OnClick(R.id.bt_language)
    void chooseCurrency() {
        if (utils!=null&&languageSpinner!=null) {
            utils.putInt("language", languageSpinner.getSelectedIndex());
            ToastUtil.toast("Change Language Success");
        }
        onBackPressed();
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_language;
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
        title.setText(R.string.LanguageSetting);
    }

    private void initSpinner() {
        utils = new SPUtils(LanguageActivity.this,"APP");
        languageSpinner.attachDataSource(new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.languages))));
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
