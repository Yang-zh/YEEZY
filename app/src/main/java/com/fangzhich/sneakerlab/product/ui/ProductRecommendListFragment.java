package com.fangzhich.sneakerlab.product.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.blankj.utilcode.utils.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseFragment;
import com.fangzhich.sneakerlab.base.ui.recyclerview.GridSpaceItemDecoration;
import com.fangzhich.sneakerlab.base.ui.recyclerview.OnScrollLoadMoreHelper;
import com.fangzhich.sneakerlab.product.ui.adapter.ProductListAdapter;

import butterknife.BindView;

/**
 *
 * Created by Khorium on 2016/8/30.
 */
public class ProductRecommendListFragment extends BaseFragment {

    @BindView(R.id.banner)
    ImageView banner;
    @BindView(R.id.recyclerView_productList)
    RecyclerView recyclerView;
    private final static int SPAN_COUNT = 2;

    ProductListAdapter adapter;
    GridLayoutManager manager;

    String categoryId;

    @Override
    public int setContentLayout() {
        return R.layout.fragment_productlist_recommend;
    }

    @Override
    protected void initContentView() {
        categoryId = getArguments().getString("category_id");
        Glide.with(this)
                .load(R.drawable.banner_black_friday)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(banner);


        manager = new GridLayoutManager(getActivity(),SPAN_COUNT);
        adapter = new ProductListAdapter(categoryId);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new GridSpaceItemDecoration(SizeUtils.dp2px(getContext(),8),SPAN_COUNT));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new OnScrollLoadMoreHelper(adapter));
    }
}