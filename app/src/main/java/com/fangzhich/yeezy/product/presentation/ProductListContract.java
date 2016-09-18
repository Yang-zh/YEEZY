package com.fangzhich.yeezy.product.presentation;

import com.fangzhich.yeezy.base.presentation.BasePresenter;
import com.fangzhich.yeezy.base.presentation.BaseView;
import com.fangzhich.yeezy.product.data.entity.ProductItemEntity;

import java.util.ArrayList;

/**
 * ProductListContract
 * Created by Khorium on 2016/9/18.
 */
public class ProductListContract {
    public interface Presenter extends BasePresenter {
        void getProductList();
        void getProductList(int page, int limit);
        void getProductList(int page, int limit, int category_id);
        void getProductListMore(int page, int limit, int category_id);
    }
    public interface View extends BaseView<Presenter> {
        void onLoadDataSuccess(ArrayList<ProductItemEntity> productList);
        void onLoadError(Throwable throwable);
        void onLoadMoreSuccess(ArrayList<ProductItemEntity> productList);
    }
}
