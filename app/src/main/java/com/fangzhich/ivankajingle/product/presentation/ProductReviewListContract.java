package com.fangzhich.ivankajingle.product.presentation;

import com.fangzhich.ivankajingle.base.presentation.BasePresenter;
import com.fangzhich.ivankajingle.base.presentation.BaseView;
import com.fangzhich.ivankajingle.product.data.entity.ReviewEntity;

import java.util.ArrayList;

/**
 * OrderListContract
 * Created by Khorium on 2016/9/21.
 */

public class ProductReviewListContract {
    public interface Presenter extends BasePresenter{
        void getProductReviewList(String product_id);
        void getProductReviewList(String page, String limit, String product_id);
    }
    public interface View extends BaseView<ProductReviewListContract.Presenter> {
        void onLoadReviewListSuccess(ArrayList<ReviewEntity> reviewList);
        void onLoadReviewListFailed(Throwable throwable);
        void onLoadMoreReviewListSuccess(ArrayList<ReviewEntity> reviewList);
    }
}
