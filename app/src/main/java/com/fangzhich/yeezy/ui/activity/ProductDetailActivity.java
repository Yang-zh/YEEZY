package com.fangzhich.yeezy.ui.activity;

import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
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
import com.fangzhich.yeezy.ui.fragment.ProductDescriptionFragment;
import com.fangzhich.yeezy.ui.fragment.ProductListFragment;
import com.fangzhich.yeezy.ui.fragment.ProductOverViewFragment;
import com.fangzhich.yeezy.ui.fragment.ProductRatingFragment;
import com.fangzhich.yeezy.ui.fragment.ProductShippingInfoFragment;
import com.fangzhich.yeezy.util.LogUtils;
import com.fangzhich.yeezy.util.MyUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProductDetail Activity
 * Created by Khorium on 2016/8/31.
 */
public class ProductDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;


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
    @BindView(R.id.popup_background)
    View popupBackground;
    @OnClick(R.id.popup_background)
    void dismiss(){
        mPopupWindow.dismiss();
    }
    @OnClick(R.id.bt_buy)
    void buy() {
//        popupBackground.setVisibility(View.VISIBLE);
        mPopupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }


    @Override
    public int setContentLayout() {
        return R.layout.activity_product_detail;
    }

    @Override
    protected void initContentView() {
        String id = getIntent().getStringExtra("product_id");
        initToolbar();
        initViewPager();
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

    private void initViewPager() {
        fragments.add(new ProductOverViewFragment());
        fragments.add(new ProductListFragment());
        fragments.add(new ProductDescriptionFragment());
        fragments.add(new ProductRatingFragment());
        fragments.add(new ProductShippingInfoFragment());
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
        MyUtils.dynamicSetTabLayoutMode(tabLayout,this);
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
                LogUtils.getInstance().logTestError("SizeDialog", String.valueOf(value));
            }
        });
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        mPopupWindow.setAnimationStyle(R.style.Dialog);
    }

    @Override
    protected void loadData() {

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
