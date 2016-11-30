package com.fangzhich.ivankajingle.product.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blankj.utilcode.utils.SizeUtils;
import com.fangzhich.ivankajingle.R;
import com.fangzhich.ivankajingle.base.ui.BaseFragment;
import com.fangzhich.ivankajingle.base.ui.recyclerview.GridSpaceItemDecoration;
import com.fangzhich.ivankajingle.base.ui.recyclerview.OnScrollLoadMoreHelper;
import com.fangzhich.ivankajingle.product.ui.adapter.RelateProductListAdapter;

import butterknife.BindView;

/**
 *
 * Created by Khorium on 2016/8/30.
 */
public class ProductRelateListFragment extends BaseFragment {

    @BindView(R.id.recyclerView_productList)
    RecyclerView recyclerView;
    private final static int SPAN_COUNT = 2;

    RelateProductListAdapter adapter;
    GridLayoutManager manager;

    @Override
    public int setContentLayout() {
        return R.layout.fragment_productlist;
    }

    @Override
    protected void initContentView() {
        manager = new GridLayoutManager(getActivity(),SPAN_COUNT);
        adapter = new RelateProductListAdapter();
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new GridSpaceItemDecoration(SizeUtils.dp2px(getContext(),8),SPAN_COUNT));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new OnScrollLoadMoreHelper(adapter));
    }
}
