package com.fangzhich.yeezy.product.presentation;

import com.fangzhich.yeezy.product.data.entity.ProductEntity;
import com.fangzhich.yeezy.product.data.net.ProductApi;

import rx.SingleSubscriber;

/**
 * ProductOverviewPresenter
 * Created by Khorium on 2016/9/18.
 */
public class ProductOverviewPresenter implements ProductOverviewContract.Presenter {

    ProductOverviewContract.View mView;

    public ProductOverviewPresenter(ProductOverviewContract.View view) {
        mView = view;
    }

    @Override
    public void getProductOverview(int product_id) {
        ProductApi.getProduct(product_id, new SingleSubscriber<ProductEntity>() {
            @Override
            public void onSuccess(ProductEntity product) {
                mView.onGetProductOverviewSuccess(product);
            }

            @Override
            public void onError(Throwable throwable) {
                mView.onGetProductOverviewFailed(throwable);
            }
        });
    }
}
