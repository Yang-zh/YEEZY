package com.fangzhich.sneakerlab.user.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.base.ui.recyclerview.GridSpaceItemDecoration;
import com.fangzhich.sneakerlab.base.ui.recyclerview.OnScrollLoadMoreHelper;
import com.fangzhich.sneakerlab.cart.ui.DialogManager;
import com.fangzhich.sneakerlab.main.ui.MainActivity;
import com.fangzhich.sneakerlab.main.ui.SettingActivity;
import com.fangzhich.sneakerlab.order.ui.OrderHistoryActivity;
import com.fangzhich.sneakerlab.product.ui.adapter.RelateProductListAdapter;
import com.fangzhich.sneakerlab.product.ui.adapter.ReviewListAdapter;
import com.fangzhich.sneakerlab.user.ui.adapter.WishListAdapter;
import com.fangzhich.sneakerlab.util.Const;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * PersonalCenterActivity
 * Created by Khorium on 2016/9/28.
 */

public class PersonalCenterActivity extends BaseActivity {

    //title
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;

    //head
    @BindView(R.id.headImage)
    ImageView headImage;
    @BindView(R.id.userName)
    TextView userName;
    @OnClick(R.id.edit_info)
    void editInfo() {
        startActivity(new Intent(this, UserEditInfoActivity.class));
    }

    //list
    @OnClick(R.id.shoppingCart)
    void shoppingCart() {
        new DialogManager(this, getWindow().getDecorView()).startShoppingCartDialog();
    }
    @OnClick(R.id.orderHistory)
    void orderHistory() {
        startActivity(new Intent(this, OrderHistoryActivity.class));
    }
    @OnClick(R.id.wishList)
    void wishlist() {
        startActivity(new Intent(this, WishListActivity.class));
    }


    //content
    @BindView(R.id.rv_related)
    RecyclerView recyclerView;
    private RelateProductListAdapter adapter;

    private boolean isInit;

    //no data view
    @BindView(R.id.no_data_notice)
    RelativeLayout noData;
    @BindView(R.id.content_view)
    RelativeLayout contentView;
    @BindView(R.id.continue_shopping)
    TextView continueShopping;


    @Override
    public int setContentLayout() {
        return R.layout.activity_person_center;
    }

    @Override
    protected void initContentView() {
        initToolbar();
        initTitleAndList();
        initRecyclerView();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        title.setText(getResources().getString(R.string.MyLab));
    }

    private void initTitleAndList() {
        userName.setText(Const.getUserInfo().user_info.firstname + " " + Const.getUserInfo().user_info.lastname);
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new RelateProductListAdapter();
        recyclerView.addItemDecoration(new GridSpaceItemDecoration(SizeUtils.dp2px(this, 8), 2));
        recyclerView.addOnScrollListener(new OnScrollLoadMoreHelper(adapter));
        recyclerView.setAdapter(adapter);
        adapter.setOnAdapterStatusChangeListener(new ReviewListAdapter.OnAdapterStatusChangeListener() {
            @Override
            public void noData() {
                recyclerView.setVisibility(View.GONE);
                noData.setVisibility(View.VISIBLE);
            }
        });
        continueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void loadData() {
        isInit = true;
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isInit) {
            adapter.loadData();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_person_center, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.settings:
                startActivity(new Intent(this, SettingActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
