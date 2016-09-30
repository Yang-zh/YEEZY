package com.fangzhich.sneakerlab.product.ui;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.base.widget.DialogManager;
import com.fangzhich.sneakerlab.product.data.entity.ProductEntity;
import com.fangzhich.sneakerlab.product.presentation.ProductDetailContract;
import com.fangzhich.sneakerlab.product.presentation.ProductDetailPresenter;
import com.fangzhich.sneakerlab.util.MyUtil;
import com.fangzhich.sneakerlab.util.TagFormatUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * ProductDetail Activity
 * Created by Khorium on 2016/8/31.
 */
public class ProductDetailActivity extends BaseActivity implements ProductDetailContract.View{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;

    private String productId;

    @BindView(R.id.tabLayout_productDetail)
    TabLayout tabLayout;
    @BindView(R.id.viewPager_productDetail)
    ViewPager viewPager;
    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> fragmentTitles = new ArrayList<>();
    FragmentPagerAdapter adapter;


    DialogManager manager;

    @BindView(R.id.tv_price)
    TextView price;
    @BindView(R.id.tv_price_original)
    TextView priceOriginal;
    private ProductEntity product;

    @OnClick(R.id.bt_buy)
    void buy() {
        if (product!=null) {
            manager.withProductDetailControl(this).startSizeDialog(product);
        }
    }


    @Override
    public int setContentLayout() {
        return R.layout.activity_product_detail;
    }

    private ProductDetailPresenter mPresenter;

    @Override
    public void setPresenter(ProductDetailContract.Presenter presenter) {
        mPresenter = (ProductDetailPresenter) presenter;
    }

    @Override
    protected void initContentView() {
        setPresenter(new ProductDetailPresenter(this));
        productId = getIntent().getStringExtra("product_id");
        initToolbar();
        initBottomBarAndPopup();
        priceOriginal.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        title.setText(R.string.ProductDetail);
    }

    private void initViewPager(ProductEntity product) {
        Bundle args = new Bundle();
        args.putParcelable("mProduct",product);

        ProductOverviewFragment productOverviewFragment = new ProductOverviewFragment();
        productOverviewFragment.setArguments(args);
        ProductRelateListFragment productRelateListFragment = new ProductRelateListFragment();
        productRelateListFragment.setArguments(args);
        ProductDescriptionFragment ProductDesFragment = new ProductDescriptionFragment();
        ProductDesFragment.setArguments(args);
        ProductRatingFragment productRatingFragment = new ProductRatingFragment();
        productRatingFragment.setArguments(args);
        ProductShippingInfoFragment productShippingInfoFragment = new ProductShippingInfoFragment();
        productShippingInfoFragment.setArguments(args);

        fragments.add(productOverviewFragment);
        fragments.add(productRelateListFragment);
        fragments.add(ProductDesFragment);
        fragments.add(productRatingFragment);
        fragments.add(productShippingInfoFragment);
        fragmentTitles.add(getResources().getString(R.string.OverView));
        fragmentTitles.add(getResources().getString(R.string.Related));
        fragmentTitles.add(getResources().getString(R.string.Description));
        fragmentTitles.add(getResources().getString(R.string.Rating));
        fragmentTitles.add(getResources().getString(R.string.ShippingInfo));
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
        MyUtil.dynamicSetTabLayoutMode(tabLayout,this);
    }

    private void initBottomBarAndPopup() {
        priceOriginal.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        manager = new DialogManager(this,getWindow().getDecorView());
    }

    @Override
    protected void loadData() {
        mPresenter.getProductDetail(productId);
    }

    @Override
    public void onGetProductDetailSuccess(ProductEntity product) {
        this.product = product;
        price.setText(TagFormatUtil.from(getResources().getString(R.string.priceFormat))
                .with("price",String.valueOf(product.special_price))
                .format());
        priceOriginal.setText(TagFormatUtil.from(getResources().getString(R.string.priceFormat))
                .with("price",String.valueOf(product.original_price))
                .format());
        initViewPager(product);
    }

    @Override
    public void onGetProductDetailFailed(Throwable throwable) {
        Timber.e(throwable.getMessage());
    }

    public void onTabClick(String tabTitle) {
        switch (tabTitle) {
            case "Overview":
                viewPager.setCurrentItem(0);
                break;
            case "Related":
                viewPager.setCurrentItem(1);
                break;
            case "Description":
                viewPager.setCurrentItem(2);
                break;
            case "Rating":
                viewPager.setCurrentItem(3);
                break;
            case "Shipping":
                viewPager.setCurrentItem(4);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
