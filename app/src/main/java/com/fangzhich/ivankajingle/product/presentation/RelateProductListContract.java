package com.fangzhich.ivankajingle.product.presentation;

import com.fangzhich.ivankajingle.base.presentation.BasePresenter;
import com.fangzhich.ivankajingle.base.presentation.BaseView;
import com.fangzhich.ivankajingle.product.data.entity.PopularProductEntity;

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
