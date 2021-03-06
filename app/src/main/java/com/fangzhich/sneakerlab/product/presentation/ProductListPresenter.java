package com.fangzhich.sneakerlab.product.presentation;

import com.fangzhich.sneakerlab.product.data.entity.ProductItemEntity;
import com.fangzhich.sneakerlab.product.data.net.ProductApi;

import java.util.ArrayList;

import rx.SingleSubscriber;

/**
 * ProductListPresenter
 * Created by Khorium on 2016/9/18.
 */
public class ProductListPresenter implements ProductListContract.Presenter {

    private final ProductListContract.View mView;

    public ProductListPresenter(ProductListContract.View view) {
        mView = view;
    }

    @Override
    public void getProductList() {
        getProductList("0","20");
    }

    @Override
    public void getProductList(String page, String limit) {
        getProductList(page,limit,"0");
    }

    @Override
    public void getProductList(String page, String limit, String category_id) {
        ProductApi.getProducts(page,limit,category_id,new SingleSubscriber<ArrayList<ProductItemEntity>>() {
            @Override
            public void onSuccess(ArrayList<ProductItemEntity> productList) {
                mView.onLoadDataSuccess(productList);
            }

            @Override
            public void onError(Throwable throwable) {
                mView.onLoadError(throwable);
            }
        });
    }

    @Override
    public void getProductListMore(String page, String limit, String category_id) {
        ProductApi.getProducts(page,limit,category_id,new SingleSubscriber<ArrayList<ProductItemEntity>>() {
            @Override
            public void onSuccess(ArrayList<ProductItemEntity> productList) {
                mView.onLoadMoreSuccess(productList);
            }

            @Override
            public void onError(Throwable throwable) {
                mView.onLoadMoreError(throwable);
            }
        });
    }
}
