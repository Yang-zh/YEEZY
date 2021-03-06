package com.fangzhich.sneakerlab.base.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * 模板Activity
 * Created by Khorium on 2016/8/30.
 */
public abstract class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentLayout());
        ButterKnife.bind(this);
        initContentView();
        loadData();
    }

    /**
     * @return Activity's Layout
     */
    public abstract int setContentLayout();

    /**
     * init ContentView
     */
    protected abstract void initContentView();

    /**
     * load Activity's Data
     */
    protected void loadData(){}

}
