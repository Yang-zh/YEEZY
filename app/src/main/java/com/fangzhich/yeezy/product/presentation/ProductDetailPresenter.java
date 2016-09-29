package com.fangzhich.yeezy.product.presentation;

import com.fangzhich.yeezy.product.data.entity.ProductEntity;
import com.fangzhich.yeezy.product.data.net.ProductApi;

import rx.SingleSubscriber;

/**
 * ProductDetailPresenter
 * Created by Khorium on 2016/9/18.
 */
public class ProductDetailPresenter implements ProductDetailContract.Presenter {

    private ProductDetailContract.View mView;

    public ProductDetailPresenter(ProductDetailContract.View view) {
        mView = view;
    }

    @Override
    public void getProductDetail(String product_id) {
        ProductApi.getProduct(product_id, new SingleSubscriber<ProductEntity>() {
            @Override
            public void onSuccess(ProductEntity product) {
                mView.onGetProductDetailSuccess(product);
            }

            @Override
            public void onError(Throwable throwable) {
                mView.onGetProductDetailFailed(throwable);
            }
        });
    }
}
