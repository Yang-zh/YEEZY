package com.fangzhich.yeezy.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.ui.fragment.NotificationListFragment;
import com.fangzhich.yeezy.util.MyUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * NotificationActivity
 * Created by Khorium on 2016/9/1.
 */
public class NotificationActivity extends BaseActivity{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.tabLayout_notification)
    TabLayout tabLayout;
    @BindView(R.id.viewPager_notification)
    ViewPager viewPager;
    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> fragmentTitles = new ArrayList<>();
    FragmentPagerAdapter adapter;

    @Override
    public int setContentLayout() {
        return R.layout.activity_notification;
    }

    @Override
    protected void initContentView() {
        initToolbar();
        initViewPager();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        title.setText(R.string.Notify);
    }

    private void initViewPager() {
        fragments.add(new NotificationListFragment());
        fragments.add(new NotificationListFragment());
        fragments.add(new NotificationListFragment());
        fragments.add(new NotificationListFragment());
        fragmentTitles.add(getResources().getString(R.string.All));
        fragmentTitles.add(getResources().getString(R.string.Deals));
        fragmentTitles.add(getResources().getString(R.string.YourOrders));
        fragmentTitles.add(getResources().getString(R.string.Other));
        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return fragmentTitles.get(position);
            }
        };

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        tabLayout.setupWithViewPager(viewPager);
        MyUtils.dynamicSetTabLayoutMode(tabLayout,this);
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
