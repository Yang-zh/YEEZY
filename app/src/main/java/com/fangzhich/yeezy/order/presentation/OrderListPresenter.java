package com.fangzhich.yeezy.order.presentation;

import com.fangzhich.yeezy.product.data.entity.ReviewEntity;
import com.fangzhich.yeezy.product.data.net.ProductApi;

import java.util.ArrayList;

import rx.SingleSubscriber;

/**
 * OrderListPresenter
 * Created by Khorium on 2016/9/21.
 */

public class OrderListPresenter implements OrderListContract.Presenter {

    private final OrderListContract.View mView;

    public OrderListPresenter(OrderListContract.View view) {
        mView = view;
    }

    @Override
    public void getOrderList(int product_id) {

    }

    @Override
    public void getOrderList(int page, int limit, int product_id) {

    }
}
