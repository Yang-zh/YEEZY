package com.fangzhich.yeezy.ui.fragment;

import android.widget.TextView;

import com.fangzhich.yeezy.R;

import butterknife.BindView;

/**
 * Description
 * Created by Khorium on 2016/8/31.
 */
public class ProductDescriptionFragment extends BaseFragment {

    @BindView(R.id.tv_productDescription)
    TextView productDescription;

    @Override
    public int setContentLayout() {
        return R.layout.fragment_product_description;
    }

    @Override
    protected void initContentView() {
        productDescription.setText("productDescription  productDescription  productDescription  productDescription  productDescription  productDescription  productDescription  productDescription  ");
    }

    @Override
    protected void loadData() {

    }
}
