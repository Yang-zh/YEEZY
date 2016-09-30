package com.fangzhich.sneakerlab.order.ui.adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.recyclerview.BaseRecyclerViewAdapter;
import com.fangzhich.sneakerlab.order.data.entity.OrderItemEntity;
import com.fangzhich.sneakerlab.order.presentation.OrderListContract;
import com.fangzhich.sneakerlab.order.presentation.OrderListPresenter;
import com.fangzhich.sneakerlab.order.ui.OrderDetailActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * OrderHistoryAdapter
 * Created by Khorium on 2016/9/18.
 */
class OrderHistoryAdapter extends BaseRecyclerViewAdapter<OrderItemEntity, OrderHistoryAdapter.ViewHolder> implements OrderListContract.View {

    private OrderListContract.Presenter mPresenter;
    private int mTotalPage = 0;

    public OrderHistoryAdapter() {
        setPresenter(new OrderListPresenter(this));
        loadData();
    }

    @Override
    public void setPresenter(OrderListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public void loadData() {
        if (mPresenter != null) {
            mPresenter.getOrderList();
        }
        mTotalPage = 1;
    }

    @Override
    public void onLoadOrderListSuccess(ArrayList<OrderItemEntity> orderList) {
        mData = orderList;
        notifyDataSetChanged();
    }

    @Override
    public void onLoadOrderListFailed(Throwable throwable) {
        Timber.e(throwable.getMessage());
    }

    @Override
    public void loadMore() {
        mPresenter.getOrderList(String.valueOf(++mTotalPage), "20");
        notifyDataSetChanged();
    }

    @Override
    public void onLoadOrderListMoreSuccess(ArrayList<OrderItemEntity> orderList) {
        // todo un reach
    }

    @Override
    public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.item_order_list, null));
    }

    @Override
    protected void onBindHolder(ViewHolder holder, int position) {
        final OrderItemEntity order = mData.get(position);
        holder.status.setText(order.status);
        holder.orderDate.setText(order.date_added);
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),OrderDetailActivity.class);
                intent.putExtra("order_id",order.order_id);
                v.getContext().startActivity(intent);
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.orderDate)
        TextView orderDate;
        @BindView(R.id.iv_productImage)
        ImageView ivProductImage;
        @BindView(R.id.productName)
        TextView productName;
        @BindView(R.id.quantity)
        TextView quantity;
        @BindView(R.id.color)
        TextView color;
        @BindView(R.id.size)
        TextView size;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.bt_detail)
        CardView detail;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
