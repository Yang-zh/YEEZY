package com.fangzhich.yeezy.product.presentation;

import com.fangzhich.yeezy.base.presentation.BaseView;
import com.fangzhich.yeezy.product.data.entity.ProductEntity;

/**
 * ProductOverviewContract
 * Created by Khorium on 2016/9/18.
 */
public class ProductOverviewContract {
    public interface Presenter {
        void getProductOverview(int product_id);
    }
    public interface View extends BaseView<Presenter> {
        void onGetProductOverviewSuccess(ProductEntity product);
        void onGetProductOverviewFailed(Throwable throwable);
    }
}
