package com.fangzhich.yeezy.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blankj.utilcode.utils.SizeUtils;
import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.ui.adapter.ProductListAdapter;
import com.fangzhich.yeezy.data.net.Bean.ProductEntity;
import com.fangzhich.yeezy.ui.adapter.GridSpaceItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;

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

    ArrayList<ProductEntity> products = new ArrayList<>();

    @Override
    public int setContentLayout() {
        return R.layout.fragment_productlist;
    }

    @Override
    protected void initContentView() {
        manager = new GridLayoutManager(getActivity(),SPAN_COUNT);
        adapter = new ProductListAdapter();
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new GridSpaceItemDecoration(SizeUtils.dp2px(getContext(),8),SPAN_COUNT));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
//        Api.getPic(10 ,new Subscriber<ArrayList<TestPicEntity>>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onNext(ArrayList<TestPicEntity> testPicEntities) {
//                LogUtils.getInstance().logTestError("productList","load data accomplished");
//                products = testPicEntities;
//                adapter.setData(products);
//                adapter.notifyDataSetChanged();
//            }
//        });
        for (int i=0;i<20;i++) {
            products.add(new ProductEntity());
        }
        adapter.setData(products);
        adapter.notifyDataSetChanged();
    }
}
