package com.fangzhich.sneakerlab.product.presentation;

import com.fangzhich.sneakerlab.base.presentation.BasePresenter;
import com.fangzhich.sneakerlab.base.presentation.BaseView;
import com.fangzhich.sneakerlab.product.data.entity.ProductEntity;

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
