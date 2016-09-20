package com.fangzhich.yeezy.product.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blankj.utilcode.utils.SizeUtils;
import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.base.ui.BaseFragment;
import com.fangzhich.yeezy.base.ui.recyclerview.OnScrollLoadMoreHelper;
import com.fangzhich.yeezy.product.ui.adapter.ProductListAdapter;
import com.fangzhich.yeezy.base.ui.recyclerview.GridSpaceItemDecoration;

import butterknife.BindView;
import timber.log.Timber;

/**
 *
 * Created by Khorium on 2016/8/30.
 */
public class ProductListFragment extends BaseFragment {

    @BindView(R.id.recyclerView_productList)
    RecyclerView recyclerView;
    private final static int SPAN_COUNT = 2;

    ProductListAdapter adapter;
    GridLayoutManager manager;

    int categoryId;

    @Override
    public int setContentLayout() {
        return R.layout.fragment_productlist;
    }

    @Override
    protected void initContentView() {
        categoryId = getArguments().getInt("category_id");

        manager = new GridLayoutManager(getActivity(),SPAN_COUNT);
        adapter = new ProductListAdapter(categoryId);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new GridSpaceItemDecoration(SizeUtils.dp2px(getContext(),8),SPAN_COUNT));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new OnScrollLoadMoreHelper(adapter));
    }
}
