package com.fangzhich.sneakerlab.order.presentation;

import com.fangzhich.sneakerlab.order.data.entity.OrderItemEntity;
import com.fangzhich.sneakerlab.order.data.net.OrderApi;

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
    public void getOrderList() {
        getOrderList("1","20");
    }

    @Override
    public void getOrderList(String page, String limit) {
        OrderApi.getOrderList(page, limit, new SingleSubscriber<ArrayList<OrderItemEntity>>() {
            @Override
            public void onSuccess(ArrayList<OrderItemEntity> value) {
                mView.onLoadOrderListSuccess(value);
            }

            @Override
            public void onError(Throwable error) {
                mView.onLoadOrderListFailed(error);
            }
        });
    }
}
