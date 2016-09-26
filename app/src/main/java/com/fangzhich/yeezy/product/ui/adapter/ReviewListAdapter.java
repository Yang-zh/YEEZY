package com.fangzhich.yeezy.product.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.base.ui.recyclerview.BaseRecyclerViewAdapter;
import com.fangzhich.yeezy.product.data.entity.ReviewEntity;
import com.fangzhich.yeezy.product.presentation.ProductReviewListContract;
import com.fangzhich.yeezy.product.presentation.ProductReviewListPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * OrderHistoryAdapter
 * Created by Khorium on 2016/9/18.
 */
public class ReviewListAdapter extends BaseRecyclerViewAdapter<ReviewEntity,ReviewListAdapter.ViewHolder> implements ProductReviewListContract.View{

    private final int mProduct_id;
    private ProductReviewListContract.Presenter mPresenter;
    private ArrayList<ReviewEntity> mReviews = new ArrayList<>();
    private int mTotalPage = -1;

    public ReviewListAdapter(int product_id) {
        mProduct_id = product_id;
        setPresenter(new ProductReviewListPresenter(this));
        loadData();
    }

    @Override
    public void setPresenter(ProductReviewListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public ArrayList<ReviewEntity> loadData() {
        if (mPresenter!=null) {
            mPresenter.getProductReviewList(mProduct_id);
        }
        mTotalPage = 0;
        return mReviews;
    }

    @Override
    public void onLoadReviewListSuccess(ArrayList<ReviewEntity> reviewList) {
        mReviews = reviewList;
        notifyDataSetChanged();
    }

    @Override
    public void onLoadReviewListFailed(Throwable throwable) {
        Timber.e(throwable.getMessage());
    }

    @Override
    public void loadMore() {
        mPresenter.getProductReviewList(++mTotalPage,20,mProduct_id);
        notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreReviewListSuccess(ArrayList<ReviewEntity> reviewList) {
        int positionStart = mReviews.size() + 1;
        mReviews.addAll(reviewList);
        notifyItemRangeChanged(positionStart, mReviews.size());
    }

    @Override
    public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.item_review, null));
    }

    @Override
    protected void onBindHolder(ViewHolder holder, int position) {
        ReviewEntity review = mReviews.get(position);
        holder.comment.setText(review.text);
        holder.ratingBar.setNumStars(Integer.parseInt(review.rating));
        holder.author.setText(review.author);
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rating_bar)
        RatingBar ratingBar;
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
