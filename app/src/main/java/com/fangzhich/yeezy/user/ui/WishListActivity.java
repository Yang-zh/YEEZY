package com.fangzhich.yeezy.user.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.base.ui.BaseActivity;
import com.fangzhich.yeezy.main.data.net.MainApi;
import com.fangzhich.yeezy.main.data.net.entity.CategoryEntity;
import com.fangzhich.yeezy.main.ui.MainActivity;
import com.fangzhich.yeezy.product.ui.ProductListFragment;
import com.fangzhich.yeezy.util.MyUtil;
import com.fangzhich.yeezy.util.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import rx.SingleSubscriber;
import timber.log.Timber;

/**
 * WishListActivity
 * Created by Khorium on 2016/9/28.
 */

public class WishListActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;


    @Override
    public int setContentLayout() {
        return R.layout.activity_wish_list;
    }

    @Override
    protected void initContentView() {
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        title.setText(R.string.Cerf);
    }
}
