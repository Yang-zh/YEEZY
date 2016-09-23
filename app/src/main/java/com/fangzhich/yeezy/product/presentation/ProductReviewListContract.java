package com.fangzhich.yeezy.product.presentation;

import com.fangzhich.yeezy.base.presentation.BasePresenter;
import com.fangzhich.yeezy.base.presentation.BaseView;
import com.fangzhich.yeezy.product.data.entity.ProductEntity;
import com.fangzhich.yeezy.product.data.entity.ReviewEntity;
import com.squareup.haha.guava.collect.ArrayListMultimap;

import java.util.ArrayList;

/**
 * OrderListContract
 * Created by Khorium on 2016/9/21.
 */

public class ProductReviewListContract {
    public interface Presenter extends BasePresenter{
        void getProductReviewList(int product_id);
        void getProductReviewList(int page, int limit, int product_id);
    }
    public interface View extends BaseView<ProductReviewListContract.Presenter> {
        void onLoadReviewListSuccess(ArrayList<ReviewEntity> reviewList);
        void onLoadReviewListFailed(Throwable throwable);
        void onLoadMoreReviewListSuccess(ArrayList<ReviewEntity> reviewList);
    }
}
