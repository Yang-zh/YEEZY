package com.fangzhich.sneakerlab.product.ui;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseFragment;
import com.fangzhich.sneakerlab.base.ui.recyclerview.LinearLayoutItemDecoration;
import com.fangzhich.sneakerlab.product.data.entity.ProductEntity;
import com.fangzhich.sneakerlab.product.ui.adapter.ReviewListAdapter;
import com.fangzhich.sneakerlab.util.TagFormatUtil;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import butterknife.BindView;

/**
 * Rating
 * Created by Khorium on 2016/8/31.
 */
public class ProductRatingFragment extends BaseFragment {
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
    @BindView(R.id.rv_comments)
    RecyclerView rvComments;

    ProductEntity product;

    @Override
    public int setContentLayout() {
        return R.layout.fragment_product_rating;
    }

    @Override
    protected void initContentView() {
        product = getArguments().getParcelable("mProduct");
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        ReviewListAdapter adapter = new ReviewListAdapter(product.product_id);
        rvComments.setLayoutManager(manager);
        rvComments.addItemDecoration(new LinearLayoutItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        rvComments.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        Glide.with(getContext())
                .load(product.images==null?null:product.images.get(0))
                .placeholder(R.mipmap.product_image_placeholder)
                .fitCenter()
                .crossFade()
                .into(productImage);
        productName.setText(product.name);
        sellNumber.setText(TagFormatUtil.from(getResources().getString(R.string.SellNumberFormat))
                .with("number", product.quantity)
                .format());
        saveNumber.setText(TagFormatUtil.from(getResources().getString(R.string.SaveNumberFormat))
                .with("number", product.points)
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
}
