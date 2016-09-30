package com.fangzhich.sneakerlab.product.presentation;

import com.fangzhich.sneakerlab.base.presentation.BasePresenter;
import com.fangzhich.sneakerlab.base.presentation.BaseView;
import com.fangzhich.sneakerlab.product.data.entity.ProductItemEntity;

import java.util.ArrayList;

/**
 * ProductListContract
 * Created by Khorium on 2016/9/18.
 */
public class ProductListContract {
    public interface Presenter extends BasePresenter {
        void getProductList();
        void getProductList(String page, String limit);
        void getProductList(String page, String limit, String category_id);
        void getProductListMore(String page, String limit, String category_id);
    }
    public interface View extends BaseView<Presenter> {
        void onLoadDataSuccess(ArrayList<ProductItemEntity> productList);
        void onLoadError(Throwable throwable);
        void onLoadMoreSuccess(ArrayList<ProductItemEntity> productList);
    }
}
