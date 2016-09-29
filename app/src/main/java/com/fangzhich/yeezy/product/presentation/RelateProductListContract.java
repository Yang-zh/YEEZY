package com.fangzhich.yeezy.product.presentation;

import com.fangzhich.yeezy.base.presentation.BasePresenter;
import com.fangzhich.yeezy.base.presentation.BaseView;
import com.fangzhich.yeezy.product.data.entity.PopularProductEntity;
import com.fangzhich.yeezy.product.data.entity.ProductItemEntity;

import java.util.ArrayList;

/**
 * ProductListContract
 * Created by Khorium on 2016/9/18.
 */
public class RelateProductListContract {
    public interface Presenter extends BasePresenter {
        void getPopularProductList();
        void getPopularProductList(String page, String limit);
        void getPopularProductListMore(String page, String limit);
    }
    public interface View extends BaseView<Presenter> {
        void onLoadDataSuccess(ArrayList<PopularProductEntity> popularProductList);
        void onLoadError(Throwable throwable);
        void onLoadMoreSuccess(ArrayList<PopularProductEntity> popularProductList);
    }
}
