package com.fangzhich.sneakerlab.product.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.recyclerview.BaseRecyclerViewAdapter;
import com.fangzhich.sneakerlab.product.data.entity.ReviewEntity;
import com.fangzhich.sneakerlab.product.presentation.ProductReviewListContract;
import com.fangzhich.sneakerlab.product.presentation.ProductReviewListPresenter;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * OrderHistoryAdapter
 * Created by Khorium on 2016/9/18.
 */
public class ReviewListAdapter extends BaseRecyclerViewAdapter<ReviewEntity,ReviewListAdapter.ViewHolder> implements ProductReviewListContract.View{

    private final String mProduct_id;
    private ProductReviewListContract.Presenter mPresenter;
    private int mTotalPage = -1;

    public ReviewListAdapter(String product_id) {
        mProduct_id = product_id;
        setPresenter(new ProductReviewListPresenter(this));
        loadData();
    }

    @Override
    public void setPresenter(ProductReviewListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public void loadData() {
        if (mPresenter!=null) {
            mPresenter.getProductReviewList(mProduct_id);
        }
        mTotalPage = 0;
    }

    @Override
    public void onLoadReviewListSuccess(ArrayList<ReviewEntity> reviewList) {
        mData = reviewList;
        notifyDataSetChanged();
    }

    @Override
    public void onLoadReviewListFailed(Throwable throwable) {
        if (mData==null || mData.size()==0) {
            if (listener!=null) {
                listener.noData();
            }
            return;
        }
        Timber.e(throwable.getMessage());
    }

    @Override
    public void loadMore() {
        mPresenter.getProductReviewList(String.valueOf(++mTotalPage),"20",mProduct_id);
        notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreReviewListSuccess(ArrayList<ReviewEntity> reviewList) {
        int positionStart = mData.size() + 1;
        mData.addAll(reviewList);
        notifyItemRangeChanged(positionStart, mData.size());
    }

    @Override
    public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.item_review, null));
    }

    @Override
    protected void onBindHolder(ViewHolder holder, int position) {
        ReviewEntity review = mData.get(position);
        holder.comment.setText(review.text.replace("&quot;", "\""));
        holder.ratingBar.getAnimationBuilder()
                .setRatingTarget(review.rating)
                .setDuration(1000)
                .setRepeatCount(0)
                .setInterpolator(new LinearInterpolator())
                .start();
        holder.author.setText(review.author);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rating_bar)
        SimpleRatingBar ratingBar;
        @BindView(R.id.comment)
        TextView comment;
        @BindView(R.id.author)
        TextView author;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
