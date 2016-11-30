package com.fangzhich.ivankajingle.product.presentation;

import com.fangzhich.ivankajingle.base.presentation.BasePresenter;
import com.fangzhich.ivankajingle.base.presentation.BaseView;
import com.fangzhich.ivankajingle.product.data.entity.ProductEntity;

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
