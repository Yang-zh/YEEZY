package com.fangzhich.yeezy.product.presentation;

import com.fangzhich.yeezy.base.presentation.BasePresenter;
import com.fangzhich.yeezy.base.presentation.BaseView;
import com.fangzhich.yeezy.product.data.entity.ProductEntity;

/**
 * ProductDetailContract
 * Created by Khorium on 2016/9/18.
 */
public class ProductDetailContract {
    public interface Presenter extends BasePresenter{
        void getProductDetail(String product_id);
    }
    public interface View extends BaseView<Presenter> {
        void onGetProductDetailSuccess(ProductEntity product);
        void onGetProductDetailFailed(Throwable throwable);
    }
}
