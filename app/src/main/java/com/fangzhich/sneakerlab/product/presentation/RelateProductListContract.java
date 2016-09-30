package com.fangzhich.sneakerlab.product.presentation;

import com.fangzhich.sneakerlab.base.presentation.BasePresenter;
import com.fangzhich.sneakerlab.base.presentation.BaseView;
import com.fangzhich.sneakerlab.product.data.entity.PopularProductEntity;

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
