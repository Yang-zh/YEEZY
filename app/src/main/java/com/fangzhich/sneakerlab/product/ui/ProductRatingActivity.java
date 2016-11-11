package com.fangzhich.sneakerlab.product.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.base.ui.recyclerview.LinearLayoutItemDecoration;
import com.fangzhich.sneakerlab.product.data.entity.ProductEntity;
import com.fangzhich.sneakerlab.product.ui.adapter.ReviewListAdapter;
import com.fangzhich.sneakerlab.util.TagFormatUtil;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import butterknife.BindView;

/**
 * ProductRatingActivity
 * Created by Khorium on 2016/11/11.
 */

public class ProductRatingActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.product_image)
    ImageView productImage;
    @BindView(R.id.product_name)
    TextView productName;
    @BindView(R.id.sell_number)
    TextView sellNumber;
    @BindView(R.id.save_number)
    TextView saveNumber;
    @BindView(R.id.rating_bar)
    SimpleRatingBar ratingBar;
    @BindView(R.id.rating_number)
    TextView ratingNumber;

    @BindView(R.id.no_data_notice)
    TextView noDataNotice;
    @BindView(R.id.rv_comments)
    RecyclerView rvComments;

    ProductEntity product;

    @Override
    public int setContentLayout() {
        return R.layout.activity_product_rating;
    }

    @Override
    protected void initContentView() {
        product = getIntent().getParcelableExtra("mProduct");
        initToolbar();
        initRecyclerView();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        title.setText(R.string.Ratings);
    }


    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        final ReviewListAdapter adapter = new ReviewListAdapter(product.product_id);
        rvComments.setLayoutManager(manager);
        rvComments.addItemDecoration(new LinearLayoutItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvComments.setAdapter(adapter);
        adapter.setOnAdapterStatusChangeListener(new ReviewListAdapter.OnAdapterStatusChangeListener() {
            @Override
            public void noData() {
                rvComments.setVisibility(View.GONE);
                noDataNotice.setVisibility(View.VISIBLE);
            }
        });
        noDataNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvComments.setVisibility(View.VISIBLE);
                noDataNotice.setVisibility(View.GONE);
                adapter.loadData();
            }
        });
    }

    @Override
    protected void loadData() {
        Glide.with(this)
                .load(product.images==null?null:product.images.get(0))
                .placeholder(R.mipmap.product_image_placeholder)
                .fitCenter()
                .crossFade()
                .into(productImage);
        productName.setText(product.name);
        sellNumber.setText(TagFormatUtil.from(getResources().getString(R.string.SellNumberFormat))
                .with("number", product.sales_volume)
                .format());
        saveNumber.setText(TagFormatUtil.from(getResources().getString(R.string.SaveNumberFormat))
                .with("number", product.collections)
                .format());
        ratingBar.getAnimationBuilder()
                .setRatingTarget(product.rating)
                .setDuration(1000)
                .setRepeatCount(0)
                .setInterpolator(new LinearInterpolator())
                .start();
        ratingNumber.setText(TagFormatUtil.from(getResources().getString(R.string.RatingNumberFormat))
                .with("number", product.reviews)
                .format());

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
