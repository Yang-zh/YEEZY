package com.fangzhich.yeezy.product.ui;

import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bigkoo.snappingstepper.SnappingStepper;
import com.bigkoo.snappingstepper.listener.SnappingStepperValueChangeListener;
import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.base.ui.BaseActivity;
import com.fangzhich.yeezy.product.data.entity.ProductEntity;
import com.fangzhich.yeezy.product.presentation.ProductDetailContract;
import com.fangzhich.yeezy.product.presentation.ProductDetailPresenter;
import com.fangzhich.yeezy.util.ToastUtil;
import com.fangzhich.yeezy.util.MyUtil;

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

    private int productId;

    @BindView(R.id.tabLayout_productDetail)
    TabLayout tabLayout;
    @BindView(R.id.viewPager_productDetail)
    ViewPager viewPager;
    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> fragmentTitles = new ArrayList<>();
    FragmentPagerAdapter adapter;


    PopupWindow mPopupWindow;

    @BindView(R.id.tv_price)
    TextView price;
    @BindView(R.id.tv_price_original)
    TextView priceOriginal;

    @OnClick(R.id.popup_background)
    void dismiss(){
        mPopupWindow.dismiss();
    }
    @OnClick(R.id.bt_buy)
    void buy() {
        mPopupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
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
        productId = getIntent().getIntExtra("product_id", 0);
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
        View mPopupContent = View.inflate(this, R.layout.dialog_choose_size, null);
        mPopupWindow = new PopupWindow(mPopupContent, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        View close = mPopupContent.findViewById(R.id.bt_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        SnappingStepper stepper = (SnappingStepper) mPopupContent.findViewById(R.id.quantity_view);
        stepper.setMinValue(1);
        stepper.setMaxValue(99);
        stepper.setOnValueChangeListener(new SnappingStepperValueChangeListener() {
            @Override
            public void onValueChange(View view, int value) {
                ToastUtil.logTestError("SizeDialog", String.valueOf(value));
            }
        });
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        mPopupWindow.setAnimationStyle(R.style.Dialog);
    }

    @Override
    protected void loadData() {
        mPresenter.getProductOverview(productId);
    }

    @Override
    public void onGetProductDetailSuccess(ProductEntity product) {
        initViewPager(product);
        price.setText(product.price);
        priceOriginal.setText(getResources().getString(R.string.nulll));
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