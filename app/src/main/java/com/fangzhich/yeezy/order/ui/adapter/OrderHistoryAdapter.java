package com.fangzhich.yeezy.order.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.base.ui.recyclerview.BaseRecyclerViewAdapter;
import com.fangzhich.yeezy.order.presentation.OrderListContract;
import com.fangzhich.yeezy.order.presentation.OrderListPresenter;
import com.fangzhich.yeezy.product.data.entity.ReviewEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * OrderHistoryAdapter
 * Created by Khorium on 2016/9/18.
 */
class OrderHistoryAdapter extends BaseRecyclerViewAdapter<ReviewEntity,OrderHistoryAdapter.ViewHolder> implements OrderListContract.View{

    //todo wait for api
    private final int mProduct_id;
    private OrderListContract.Presenter mPresenter;
    private ArrayList<ReviewEntity> mReviews = new ArrayList<>();
//    private int mTotalPage = -1;

    public OrderHistoryAdapter(int product_id) {
        mProduct_id = product_id;
        setPresenter(new OrderListPresenter(this));
        loadData();
    }

    @Override
    public void setPresenter(OrderListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public ArrayList<ReviewEntity> loadData() {
        if (mPresenter!=null) {
            mPresenter.getOrderList(mProduct_id);
        }
//        mTotalPage = 0;
        return mReviews;
    }

//    @Override
//    public void onLoadReviewListSuccess(ArrayList<ReviewEntity> reviewList) {
//        mReviews = reviewList;
//        notifyDataSetChanged();
//    }
    @Override
    public void onLoadOrderListSuccess(ArrayList<ReviewEntity> reviewList) {

    }
//    @Override
//    public void onLoadReviewListFailed(Throwable throwable) {
//        Timber.e(throwable.getMessage());
//    }
//
    @Override
    public void onLoadOrderListFailed(Throwable throwable) {

    }

    @Override
    public void loadMore() {
//        mPresenter.getProductReviewList(++mTotalPage,20,mProduct_id);
        notifyDataSetChanged();
    }

//    @Override
//    public void onLoadMoreReviewListSuccess(ArrayList<ReviewEntity> reviewList) {
//        int positionStart = mReviews.size() + 1;
//        mReviews.addAll(reviewList);
//        notifyItemRangeChanged(positionStart, mReviews.size());
//    }
//
    @Override
    public void onLoadOrderListMoreSuccess(ArrayList<ReviewEntity> reviewList) {

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
