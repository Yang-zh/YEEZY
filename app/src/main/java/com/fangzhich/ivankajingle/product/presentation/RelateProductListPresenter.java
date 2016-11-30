package com.fangzhich.ivankajingle.product.presentation;

import com.fangzhich.ivankajingle.product.data.entity.PopularProductEntity;
import com.fangzhich.ivankajingle.product.data.net.ProductApi;

import java.util.ArrayList;

import rx.SingleSubscriber;

/**
 * ProductListPresenter
 * Created by Khorium on 2016/9/18.
 */
public class RelateProductListPresenter implements RelateProductListContract.Presenter {

    private final RelateProductListContract.View mView;

    public RelateProductListPresenter(RelateProductListContract.View view) {
        mView = view;
    }

    @Override
    public void getPopularProductList() {
        getPopularProductList("0","20");
    }

    @Override
    public void getPopularProductList(String page, String limit) {
        ProductApi.getPopularProducts(page,limit,new SingleSubscriber<ArrayList<PopularProductEntity>>() {
            @Override
            public void onSuccess(ArrayList<PopularProductEntity> productList) {
                mView.onLoadDataSuccess(productList);
            }

            @Override
            public void onError(Throwable throwable) {
                mView.onLoadError(throwable);
            }
        });
    }

    @Override
    public void getPopularProductListMore(String page, String limit) {
        ProductApi.getPopularProducts(page,limit,new SingleSubscriber<ArrayList<PopularProductEntity>>() {
            @Override
            public void onSuccess(ArrayList<PopularProductEntity> productList) {
                mView.onLoadMoreSuccess(productList);
            }

            @Override
            public void onError(Throwable throwable) {
                mView.onLoadError(throwable);
            }
        });
    }
}
