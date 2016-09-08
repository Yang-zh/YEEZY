package com.fangzhich.yeezy.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * BaseFragment
 * Created by Khorium on 2016/8/30.
 */
public abstract class BaseFragment extends Fragment {

    View mContentView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mContentView = inflater.inflate(setContentLayout(), container, false);
        ButterKnife.bind(this, mContentView);
        initContentView();
        loadData();
        return mContentView;
    }

    /**
     * @return Fragment's Layout
     */
    public abstract int setContentLayout();

    /**
     * init ContentView
     */
    protected abstract void initContentView();

    /**
     * load Fragment's Data
     */
    protected abstract void loadData();
}
