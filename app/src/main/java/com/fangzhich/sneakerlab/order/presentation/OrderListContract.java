package com.fangzhich.sneakerlab.order.presentation;

import com.fangzhich.sneakerlab.base.presentation.BasePresenter;
import com.fangzhich.sneakerlab.base.presentation.BaseView;
import com.fangzhich.sneakerlab.order.data.entity.OrderItemEntity;

import java.util.ArrayList;

/**
 * OrderListContract
 * Created by Khorium on 2016/9/21.
 */

public class OrderListContract {
    public interface Presenter extends BasePresenter{
        void getOrderList();
        void getOrderList(String page, String limit);
        void getOrderListMore(String page, String limit);
    }
    public interface View extends BaseView<OrderListContract.Presenter> {
        void onLoadOrderListSuccess(ArrayList<OrderItemEntity> reviewList);
        void onLoadOrderListFailed(Throwable throwable);
        void onLoadOrderListMoreSuccess(ArrayList<OrderItemEntity> reviewList);
    }
}
