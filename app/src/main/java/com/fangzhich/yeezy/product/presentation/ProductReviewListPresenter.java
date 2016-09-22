package com.fangzhich.yeezy.product.presentation;

import com.fangzhich.yeezy.product.data.entity.ReviewEntity;
import com.fangzhich.yeezy.product.data.net.ProductApi;

import java.util.ArrayList;

import rx.SingleSubscriber;

/**
 * OrderListPresenter
 * Created by Khorium on 2016/9/21.
 */

public class ProductReviewListPresenter implements ProductReviewListContract.Presenter {

    private final ProductReviewListContract.View mView;

    public ProductReviewListPresenter(ProductReviewListContract.View view) {
        mView = view;
    }

    @Override
    public void getProductReviewList(int product_id) {
        ProductApi.getReviews(0, 20, product_id, new SingleSubscriber<ArrayList<ReviewEntity>>() {
            @Override
            public void onSuccess(ArrayList<ReviewEntity> reviewList) {
                mView.onLoadReviewListSuccess(reviewList);
            }

            @Override
            public void onError(Throwable throwable) {
                mView.onLoadReviewListFailed(throwable);
            }
        });
    }

    @Override
    public void getProductReviewList(int page, int limit, int product_id) {
        ProductApi.getReviews(page, limit, product_id, new SingleSubscriber<ArrayList<ReviewEntity>>() {
            @Override
            public void onSuccess(ArrayList<ReviewEntity> reviewList) {
                mView.onLoadMoreReviewListSuccess(reviewList);
            }

            @Override
            public void onError(Throwable throwable) {
                mView.onLoadReviewListFailed(throwable);
            }
        });
    }
}
