package com.fangzhich.sneakerlab.main.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
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
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.fangzhich.sneakerlab.BuildConfig;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.data.event.RxBus;
import com.fangzhich.sneakerlab.base.data.log.GLogManager;
import com.fangzhich.sneakerlab.base.service.MyFirebaseInstanceIDService;
import com.fangzhich.sneakerlab.base.service.MyFirebaseMessagingService;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.cart.ui.DialogManager;
import com.fangzhich.sneakerlab.main.data.entity.CategoryEntity;
import com.fangzhich.sneakerlab.main.data.event.UserInfoRefreshEvent;
import com.fangzhich.sneakerlab.main.data.net.MainApi;
import com.fangzhich.sneakerlab.product.ui.ProductDetailActivity;
import com.fangzhich.sneakerlab.product.ui.ProductListFragment;
import com.fangzhich.sneakerlab.order.ui.OrderHistoryActivity;
import com.fangzhich.sneakerlab.product.ui.ProductRecommendListFragment;
import com.fangzhich.sneakerlab.user.ui.LoginActivity;
import com.fangzhich.sneakerlab.user.ui.NotificationActivity;
import com.fangzhich.sneakerlab.user.ui.PersonalCenterActivity;
import com.fangzhich.sneakerlab.user.ui.SplashActivity;
import com.fangzhich.sneakerlab.util.Const;
import com.fangzhich.sneakerlab.util.ToastUtil;
import com.fangzhich.sneakerlab.util.MyUtil;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.util.ArrayList;

import butterknife.BindView;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import timber.log.Timber;

public class MainActivity extends BaseActivity {

    public static final int NOTIFY_DEFAULT = 100;
    public static final int NOTIFY_CART = 101;
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
    ActionBarDrawerToggle drawerToggle;


    @BindView(R.id.tabLayout_main)
    TabLayout tabLayout;
    @BindView(R.id.viewPager_main)
    ViewPager viewPager;
    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> fragmentTitles = new ArrayList<>();
    FragmentPagerAdapter adapter;

    @BindView(R.id.debug)
    TextView debug;

    @Override
    public int setContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initContentView() {
        //check if show debug notice
        if (BuildConfig.BASE_URL.equals("http://api.fangzhich.com")) {
            debug.setVisibility(View.VISIBLE);
        }

        if (getIntent().getBooleanExtra("fromSplash", false)) {
            Intent intent = new Intent(this, ProductDetailActivity.class);
            intent.putExtra("product_id", "79");
            startActivity(intent);
        }
        getNotification();
        initActionBarAndDrawer();
        initViewPager();
    }

    private void getNotification() {
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Timber.d("Key: " + key + " Value: " + value);
            }
        }
    }

    private void initActionBarAndDrawer() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
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

        View headLayout = navigationView.getHeaderView(0);
        headImage = (ImageView) headLayout.findViewById(R.id.headImage);
        userName = (TextView) headLayout.findViewById(R.id.userName);
        headLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Const.isLogin()) {
                    startActivity(new Intent(MainActivity.this, PersonalCenterActivity.class));
                } else {
                    GLogManager.loginEntryClick(MainActivity.this);
                    startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), LoginActivity.IS_LOGIN);
                }

            }
        });

        navigationView.setItemIconTintList(null);
        navigationView.getChildAt(0).setVerticalScrollBarEnabled(false);
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
                        if (!Const.isLogin()) {
                            startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), LoginActivity.IS_LOGIN);
                        } else {
                            new DialogManager(MainActivity.this, getWindow().getDecorView()).startShoppingCartDialog();
                        }
                        break;
                    case R.id.history:
                        if (!Const.isLogin()) {
                            startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), LoginActivity.IS_LOGIN);
                        } else {
                            startActivity(new Intent(MainActivity.this, OrderHistoryActivity.class));
                        }
                        break;
                    case R.id.contact:
                        startActivity(new Intent(MainActivity.this, ContactActivity.class));
                        break;
                    case R.id.support:
                        startActivity(new Intent(MainActivity.this, SupportActivity.class));
                        break;
                    case R.id.about:
                        startActivity(new Intent(MainActivity.this, AboutActivity.class));
                        break;
                    case R.id.return_policy:
                        startActivity(new Intent(MainActivity.this, ReturnPolicyActivity.class));
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
                if (categoryList == null || categoryList.size() <= 0) {
                    Timber.e("no categories");
                    return;
                }
                for (CategoryEntity category : categoryList) {
                    Bundle args = new Bundle();
                    args.putString("category_id", category.category_id);

                    if (category.name.equals("Recommended")) {
                        ProductRecommendListFragment fragment = new ProductRecommendListFragment();
                        fragment.setArguments(args);
                        fragments.add(fragment);
                    } else {
                        ProductListFragment fragment = new ProductListFragment();
                        fragment.setArguments(args);
                        fragments.add(fragment);
                    }

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
                viewPager.setOffscreenPageLimit(0);
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
                MyUtil.dynamicSetTabLayoutMode(tabLayout, MainActivity.this);
            }

            @Override
            public void onError(Throwable error) {
                ToastUtil.toast(error.getMessage());
                error.printStackTrace();
                Timber.e(error.getMessage());
            }
        });
    }


    @Override
    protected void loadData() {
        refreshUserInfo();
        rxBus = RxBus.getDefault()
                .toObservable(UserInfoRefreshEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<UserInfoRefreshEvent>() {
                    @Override
                    public void call(UserInfoRefreshEvent userInfoRefreshEvent) {
                        refreshUserInfo();
                    }
                });

        //firebase token initialize
        final String token = FirebaseInstanceId.getInstance().getToken();
        if (!TextUtils.isEmpty(token)) {
            MainApi.refreshFireBaseToken(token, new SingleSubscriber<Object>() {
                @Override
                public void onSuccess(Object value) {
                    Const.setFireBaseMessageToken(token);
                    Timber.e("refresh firebaseMessageToken success");
                    ToastUtil.toast("refresh firebaseMessageToken success");
                }

                @Override
                public void onError(Throwable error) {
                    Timber.e(error);
                }
            });
        } else {
            Timber.e("firebase token is empty");
        }
//        ToastUtil.toast(FirebaseInstanceId.getInstance().getToken()+"----"+FirebaseInstanceId.getInstance().getCreationTime());
//        Log.e("firebasetest",FirebaseInstanceId.getInstance().getToken()+"----"+FirebaseInstanceId.getInstance().getCreationTime());
//        Log.e("firebasetest",Const.fireBaseMessageToken);
    }

    private void refreshUserInfo() {
        if (Const.isLogin()) {
            if (Const.getUserInfo().user_info.facebook_id!=null) {
                Glide.with(this)
                        .load(Const.getUserInfo().user_info.avatarimage)
                        .asBitmap()
                        .placeholder(R.mipmap.head_image_place_holder)
                        .fitCenter()
                        .listener(new RequestListener<String, Bitmap>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                headImage.setImageBitmap(resource);
                                return false;
                            }
                        })
                        .into(headImage);
            } else {
                Timber.d(Const.getUserInfo().user_info.avatar);
                Glide.with(this)
                        .load(Const.getUserInfo().user_info.avatar)
                        .asBitmap()
                        .placeholder(R.mipmap.head_image_place_holder)
                        .fitCenter()
                        .listener(new RequestListener<String, Bitmap>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                headImage.setImageBitmap(resource);
                                return false;
                            }
                        })
                        .into(headImage);
            }
            userName.setText(Const.getUserInfo().user_info.firstname + " " + Const.getUserInfo().user_info.lastname);
        } else {
            userName.setText(R.string.SignIn);
            headImage.setImageResource(R.mipmap.head_image_place_holder);
        }
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
            case R.id.search:
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        switch (requestCode) {
            case LoginActivity.IS_LOGIN:
                if (resultCode == SplashActivity.SUCCESS) {
                    refreshUserInfo();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    Subscription rxBus;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (rxBus!=null && !rxBus.isUnsubscribed()) {
            rxBus.unsubscribe();
        }
    }
}
