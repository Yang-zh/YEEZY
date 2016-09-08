package com.fangzhich.yeezy.ui.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.blankj.utilcode.utils.SPUtils;
import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.util.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * CurrencyActivity
 * Created by Khorium on 2016/9/1.
 */
public class CurrencyActivity extends BaseActivity{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.spinner_Currency)
    Spinner languageSpinner;
    ArrayAdapter<CharSequence> adapter;
    String[] currencies;
    SPUtils utils;

    @OnClick(R.id.bt_Currency)
    void chooseCurrency() {
        if (utils!=null&&currencies!=null&&languageSpinner!=null) {
            utils.putInt("currency", languageSpinner.getSelectedItemPosition());
            LogUtils.getInstance().toastInfo("Change Language Success");
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
        utils = new SPUtils(CurrencyActivity.this,"YEEZY");
        currencies = getResources().getStringArray(R.array.currencies);

        adapter = ArrayAdapter.createFromResource(this,R.array.currencies,android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);
        languageSpinner.setPrompt("Currency");
        languageSpinner.setSelection(utils.getInt("currency"));
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                LogUtils.getInstance().toastInfo("Please Choose Currency");
            }
        });
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
