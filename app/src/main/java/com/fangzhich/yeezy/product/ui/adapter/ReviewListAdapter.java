package com.fangzhich.yeezy.product.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.product.data.entity.ReviewEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ReviewListAdapter
 * Created by Khorium on 2016/9/18.
 */
public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ViewHolder> {

    private ArrayList<ReviewEntity> mReviews = new ArrayList<>();

    public ReviewListAdapter() {
        loadData();

    }

    private void loadData() {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.item_review, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
