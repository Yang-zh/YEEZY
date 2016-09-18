package com.fangzhich.yeezy.main.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.SPUtils;
import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.base.ui.BaseActivity;
import com.fangzhich.yeezy.main.data.net.MainApi;
import com.fangzhich.yeezy.main.data.net.Bean.CategoryEntity;
import com.fangzhich.yeezy.product.ui.ProductListFragment;
import com.fangzhich.yeezy.order.ui.HistoryActivity;
import com.fangzhich.yeezy.user.ui.NotificationActivity;
import com.fangzhich.yeezy.user.ui.UserInfoActivity;
import com.fangzhich.yeezy.order.widget.ShoppingCartDialog;
import com.fangzhich.yeezy.user.ui.RegisterActivity;
import com.fangzhich.yeezy.util.LogUtils;
import com.fangzhich.yeezy.util.MyUtils;

import java.util.ArrayList;

import butterknife.BindView;
import rx.SingleSubscriber;

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
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.OpenDrawer, R.string.CloseDrawer) {
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
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });
        userInfoButton = (RelativeLayout) navigationView.getHeaderView(0).findViewById(R.id.bt_userInfo);
        userInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserInfoActivity.class));
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
                        startActivity(new Intent(MainActivity.this, NotificationActivity.class));
                        break;
                    case R.id.shoppingCart:
                        ShoppingCartDialog dialog = new ShoppingCartDialog();
                        dialog.initPopup(MainActivity.this).showPopup(getWindow().getDecorView());
                        break;
                    case R.id.history:
                        startActivity(new Intent(MainActivity.this, HistoryActivity.class));
                        break;
                    case R.id.currency:
                        startActivity(new Intent(MainActivity.this, CurrencyActivity.class));
                        break;
                    case R.id.about:
                        startActivity(new Intent(MainActivity.this, AboutActivity.class));
                        break;
                    case R.id.contact:
                        startActivity(new Intent(MainActivity.this, ContactActivity.class));
                        break;
                    case R.id.support:
                        startActivity(new Intent(MainActivity.this, SupportActivity.class));
                        break;
                    case R.id.settings:
//                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                        SPUtils spUtils = new SPUtils(MainActivity.this,"YEEZY");
                        spUtils.putBoolean("isLogin",false);
                        LogUtils.toastInfo("sign out success");
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void initViewPager() {
        MainApi.getCategories(new SingleSubscriber<ArrayList<CategoryEntity>>() {
            @Override
            public void onSuccess(ArrayList<CategoryEntity> categoryList) {
                for (CategoryEntity category : categoryList) {
                    ProductListFragment fragment = new ProductListFragment();
                    Bundle args = new Bundle();

                    args.putInt("category_id", Integer.parseInt(category.category_id));
                    fragment.setArguments(args);

                    fragments.add(fragment);
                    fragmentTitles.add(category.name);
                }
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
                viewPager.setOffscreenPageLimit(1);
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
                MyUtils.dynamicSetTabLayoutMode(tabLayout, MainActivity.this);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable error) {
                LogUtils.toastInfo(error.getMessage());
                LogUtils.logTestError("getCategoriesError", error.getMessage());
            }
        });
    }


    @Override
    protected void loadData() {
        headImage.setImageResource(R.mipmap.headshot_true);
        userName.setText(R.string.Username);
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.filter:
                LogUtils.toastInfo("Filter");
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
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
