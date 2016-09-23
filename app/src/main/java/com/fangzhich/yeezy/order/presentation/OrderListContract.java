package com.fangzhich.yeezy.order.presentation;

import com.fangzhich.yeezy.base.presentation.BasePresenter;
import com.fangzhich.yeezy.base.presentation.BaseView;
import com.fangzhich.yeezy.product.data.entity.ReviewEntity;

import java.util.ArrayList;

/**
 * OrderListContract
 * Created by Khorium on 2016/9/21.
 */

public class OrderListContract {
    public interface Presenter extends BasePresenter{
        void getOrderList(int product_id);
        void getOrderList(int page, int limit, int product_id);
    }
    public interface View extends BaseView<OrderListContract.Presenter> {
        void onLoadOrderListSuccess(ArrayList<ReviewEntity> reviewList);
        void onLoadOrderListFailed(Throwable throwable);
        void onLoadOrderListMoreSuccess(ArrayList<ReviewEntity> reviewList);
    }
}
