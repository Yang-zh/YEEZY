package com.fangzhich.yeezy.product.ui;

import android.text.Html;
import android.webkit.WebView;
import android.widget.TextView;

import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.base.ui.BaseFragment;
import com.fangzhich.yeezy.product.data.entity.ProductEntity;

import butterknife.BindView;

/**
 * Description
 * Created by Khorium on 2016/8/31.
 */
public class ProductDescriptionFragment extends BaseFragment {

    @BindView(R.id.tv_productDescription)
    WebView productDescription;
    private ProductEntity mProduct;

    @Override
    public int setContentLayout() {
        return R.layout.fragment_product_description;
    }

    @Override
    protected void initContentView() {
        mProduct = getArguments().getParcelable("mProduct");
        if (mProduct!=null) {
            productDescription.loadData("<html><body>"+Html.fromHtml(mProduct.description)+"</body></html>", "text/html", "utf-8");
//            productDescription.setText(Html.fromHtml(mProduct.description));
        }
    }

    @Override
    protected void loadData() {

    }
}
