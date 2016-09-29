package com.fangzhich.yeezy.product.ui;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.base.ui.BaseFragment;
import com.fangzhich.yeezy.base.ui.recyclerview.LinearLayoutItemDecoration;
import com.fangzhich.yeezy.product.data.entity.ProductEntity;
import com.fangzhich.yeezy.product.ui.adapter.ReviewListAdapter;
import com.fangzhich.yeezy.util.TagFormatUtil;

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
    RatingBar ratingBar;
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
        ratingBar.setNumStars(product.rating);
        ratingNumber.setText(TagFormatUtil.from(getResources().getString(R.string.RatingNumberFormat))
                .with("number", product.reviews)
                .format());

    }
}
