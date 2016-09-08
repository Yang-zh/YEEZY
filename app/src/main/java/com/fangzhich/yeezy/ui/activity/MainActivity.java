package com.fangzhich.yeezy.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.net.Api;
import com.fangzhich.yeezy.net.Bean.LoginResult;
import com.fangzhich.yeezy.ui.fragment.ProductListFragment;
import com.fangzhich.yeezy.util.LogUtils;
import com.fangzhich.yeezy.util.MyUtils;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import rx.Subscriber;

public class MainActivity extends BaseActivity {


    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigation)
    NavigationView navigationView;

    //head
    ImageView headImage;
    TextView userName;
    RelativeLayout searchButton;
    RelativeLayout userInfoButton;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    ActionBarDrawerToggle drawerToggle;


    @BindView(R.id.tabLayout_main)
    TabLayout tabLayout;
    @BindView(R.id.viewPager_main)
    ViewPager viewPager;
    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> fragmentTitles = new ArrayList<>();
    FragmentPagerAdapter adapter;

    @Override
    public int setContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initContentView() {
        initActionBarAndDrawer();
        initViewPager();
    }

    private void initActionBarAndDrawer() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        title.setText(R.string.app_name);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.OpenDrawer,R.string.CloseDrawer) {
            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        headImage = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.headImage);
        userName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.userName);
        searchButton = (RelativeLayout) navigationView.getHeaderView(0).findViewById(R.id.bt_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
            }
        });
        userInfoButton = (RelativeLayout) navigationView.getHeaderView(0).findViewById(R.id.bt_userInfo);
        userInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,UserInfoActivity.class));
            }
        });

        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homepage:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.notify:
                        startActivity(new Intent(MainActivity.this,NotificationActivity.class));
                        break;
                    case R.id.shoppingCart:
                        ShoppingCartDialog dialog = new ShoppingCartDialog();
                        dialog.initPopup(MainActivity.this).showPopup(getWindow().getDecorView());
                        break;
                    case R.id.history:
                        startActivity(new Intent(MainActivity.this,HistoryActivity.class));
                        break;
                    case R.id.currency:
                        startActivity(new Intent(MainActivity.this,CurrencyActivity.class));
                        break;
                    case R.id.about:
                        startActivity(new Intent(MainActivity.this,AboutActivity.class));
                        break;
                    case R.id.contact:
                        startActivity(new Intent(MainActivity.this,ContactActivity.class));
                        break;
                    case R.id.support:
                        startActivity(new Intent(MainActivity.this,SupportActivity.class));
                        break;
                    case R.id.settings:
                        startActivity(new Intent(MainActivity.this,SettingActivity.class));
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void initViewPager() {
        fragments.add(new ProductListFragment());
        fragments.add(new ProductListFragment());
        fragments.add(new ProductListFragment());
        fragments.add(new ProductListFragment());
        fragments.add(new ProductListFragment());
        fragments.add(new ProductListFragment());
        fragmentTitles.add("Yeezy 350 Boost");
        fragmentTitles.add("Yeezy 750 Boost");
        fragmentTitles.add("Adidas NMD");
        fragmentTitles.add("Yeezy 350 Boost");
        fragmentTitles.add("Yeezy 750 Boost");
        fragmentTitles.add("Adidas NMD");
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
        headImage.setImageResource(R.mipmap.headshot_true);
        userName.setText(R.string.Username);
        adapter.notifyDataSetChanged();
            Api.login("64e1b8d34f425d19e1ee2ea7236d3028", "admin@admin.com", "123456", new Subscriber<LoginResult>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onNext(LoginResult loginResult) {
                    LogUtils.getInstance().logTestError("Login", new Gson().toJson(loginResult));
                }
            });
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.filter:
                LogUtils.getInstance().toastInfo("Filter");
                break;
            case R.id.search:
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

}
