package com.fangzhich.ivankajingle.product.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blankj.utilcode.utils.SizeUtils;
import com.fangzhich.ivankajingle.R;
import com.fangzhich.ivankajingle.base.ui.BaseFragment;
import com.fangzhich.ivankajingle.base.ui.recyclerview.GridSpaceItemDecoration;
import com.fangzhich.ivankajingle.base.ui.recyclerview.OnScrollLoadMoreHelper;
import com.fangzhich.ivankajingle.product.ui.adapter.ProductListAdapter;

import butterknife.BindView;

/**
 * Created by Khorium on 2016/8/30.
 */
public class ProductListFragment extends BaseFragment {

    @BindView(R.id.recyclerView_productList)
    RecyclerView recyclerView;
    private final static int SPAN_COUNT = 2;

    ProductListAdapter adapter;
    GridLayoutManager manager;

    String categoryId;

    @Override
    public int setContentLayout() {
        return R.layout.fragment_productlist;
    }

    @Override
    protected void initContentView() {
        categoryId = getArguments().getString("category_id");

        manager = new GridLayoutManager(getActivity(), SPAN_COUNT);
        adapter = new ProductListAdapter(categoryId);
//        adapter.setOnStartActivityListener(new ProductListAdapter.OnStartActivityListener() {
//            @Override
//            public Bundle beforeIntentSend(Intent intent, View itemView) {
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    Transition transition = new ChangeImageTransform();
//                    transition.setDuration(2000);
//                    getActivity().getWindow().setExitTransition(transition);
//                    ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(), Pair.create((View)itemView.findViewById(R.id.iv_productImage) , "product_image_big"));
//                    startActivity(intent,activityOptions.toBundle());
//                    getActivity().overridePendingTransition(0,0);
//                    return null;
//                } else {
//                    return null;
//                }
//            }
//        });
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new GridSpaceItemDecoration(SizeUtils.dp2px(getContext(), 8), SPAN_COUNT));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new OnScrollLoadMoreHelper(adapter));
    }
}