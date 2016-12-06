package com.fangzhich.sneakerlab.product.presentation;

import com.fangzhich.sneakerlab.product.data.entity.ReviewEntity;
import com.fangzhich.sneakerlab.product.data.net.ProductApi;

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
    public void getProductReviewList(String product_id, String limit) {
        ProductApi.getReviews("0", limit, product_id, new SingleSubscriber<ArrayList<ReviewEntity>>() {
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
    public void getProductReviewList(String page, String limit, String product_id) {
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
