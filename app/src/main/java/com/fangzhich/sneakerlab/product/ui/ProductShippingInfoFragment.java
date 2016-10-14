package com.fangzhich.sneakerlab.product.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseFragment;
import com.fangzhich.sneakerlab.base.ui.recyclerview.BaseRecyclerViewAdapter;
import com.fangzhich.sneakerlab.product.data.entity.ProductEntity;
import com.fangzhich.sneakerlab.product.data.entity.ShippingInfo;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * ShippingInfo
 * Created by Khorium on 2016/8/31.
 */
public class ProductShippingInfoFragment extends BaseFragment {
    @BindView(R.id.rv_shipping)
    RecyclerView rvShipping;

    BaseRecyclerViewAdapter adapter;
    private ProductEntity product;

    @Override
    public int setContentLayout() {
        return R.layout.fragment_product_shippinginfo;
    }

    @Override
    protected void initContentView() {
        product = getArguments().getParcelable("mProduct");

        rvShipping.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BaseRecyclerViewAdapter<ShippingInfo,RecyclerView.ViewHolder>() {
            @Override
            public void loadData() {
                ArrayList<ShippingInfo> infos = new ArrayList<>();
                infos.add(new ShippingInfo(getResources().getString(R.string.EstimatedShipping),product.shipping_info.EstimatedShipping));
                infos.add(new ShippingInfo(getResources().getString(R.string.Availability),product.shipping_info.Availability));
                infos.add(new ShippingInfo(getResources().getString(R.string.EstimatedArrival),product.shipping_info.EstimatedArrival));
                infos.add(new ShippingInfo(getResources().getString(R.string.ShipsFrom),product.shipping_info.ShipsFrom));
                infos.add(new ShippingInfo(getResources().getString(R.string.ReturnPolicy),product.shipping_info.ReturnPolicy));
                mData = infos;
            }

            @Override
            public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
                View itemView = View.inflate(parent.getContext(),R.layout.item_shipping_info,null);
                return new RecyclerView.ViewHolder(itemView) {};
            }

            @Override
            protected void onBindHolder(RecyclerView.ViewHolder holder, int position) {
                View itemView = holder.itemView;
                ((TextView)itemView.findViewById(R.id.name)).setText(mData.get(position).name);
                ((TextView)itemView.findViewById(R.id.content)).setText(mData.get(position).content);
            }
        };
        rvShipping.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        adapter.loadData();
    }
}
