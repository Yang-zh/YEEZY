package com.fangzhich.sneakerlab.order.ui.adapter;

import android.content.Intent;
import android.net.LinkAddress;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.recyclerview.BaseRecyclerViewAdapter;
import com.fangzhich.sneakerlab.base.ui.recyclerview.LinearLayoutItemDecoration;
import com.fangzhich.sneakerlab.main.ui.ContactActivity;
import com.fangzhich.sneakerlab.main.ui.SupportActivity;
import com.fangzhich.sneakerlab.order.data.entity.OrderItemEntity;
import com.fangzhich.sneakerlab.order.presentation.OrderListContract;
import com.fangzhich.sneakerlab.order.presentation.OrderListPresenter;
import com.fangzhich.sneakerlab.order.ui.OrderDetailActivity;
import com.fangzhich.sneakerlab.order.ui.OrderReviewActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * OrderHistoryAdapter
 * Created by Khorium on 2016/9/18.
 */
public class OrderHistoryAdapter extends BaseRecyclerViewAdapter<OrderItemEntity, OrderHistoryAdapter.ViewHolder> implements OrderListContract.View {

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
        mPresenter.getOrderListMore(String.valueOf(++mTotalPage), "20");
    }

    @Override
    public void onLoadOrderListMoreSuccess(ArrayList<OrderItemEntity> orderList) {
        int positionStart = mData.size() + 1;
        mData.addAll(orderList);
        notifyItemRangeChanged(positionStart,mData.size());
    }

    @Override
    public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.item_order_list, null));
    }

    @Override
    protected void onBindHolder(ViewHolder holder, int position) {
        final OrderItemEntity order = mData.get(position);

        switch (order.order_status) {
            case "Processing":
                holder.orderOperation.setVisibility(View.VISIBLE);
                holder.btCancel.setVisibility(View.VISIBLE);
                holder.btCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //todo
                    }
                });
                break;
            case "Shipping":
                holder.orderOperation.setVisibility(View.VISIBLE);
                holder.btConfirmReceive.setVisibility(View.VISIBLE);
                holder.btConfirmReceive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //todo
                    }
                });
                break;
            case "Completed":
                holder.status.setTextColor(holder.itemView.getResources().getColor(R.color.text_color_gray));
                holder.orderOperation.setVisibility(View.VISIBLE);
                holder.btReview.setVisibility(View.VISIBLE);
                holder.btReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.getContext().startActivity(new Intent(v.getContext(), SupportActivity.class));
                    }
                });
                holder.btRefund.setVisibility(View.VISIBLE);
                holder.btRefund.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.getContext().startActivity(new Intent(v.getContext(), SupportActivity.class));
                    }
                });
                break;
            case "Canceled":
                holder.status.setTextColor(holder.itemView.getResources().getColor(R.color.text_color_gray));
                break;
        }

        holder.status.setText(order.order_status);
        holder.orderDate.setText(order.date_added);

        final BaseRecyclerViewAdapter<OrderItemEntity.Product,ProductViewHolder> productAdapter = new BaseRecyclerViewAdapter<OrderItemEntity.Product, ProductViewHolder>() {
            @Override
            public void loadData() {
                mData = order.product;
            }

            @Override
            public ProductViewHolder onCreateHolder(ViewGroup parent, int viewType) {
                return new ProductViewHolder(View.inflate(parent.getContext(),R.layout.item_order_list_product_list,null)) {};
            }

            @Override
            protected void onBindHolder(ProductViewHolder holder, int position) {
                View itemView = holder.itemView;

                OrderItemEntity.Product product = mData.get(position);

                Glide.with(itemView.getContext())
                        .load(product.image)
                        .fitCenter()
                        .crossFade()
                        .into(holder.ivProductImage);
                holder.productName.setText(product.name);
                holder.quantity.setText(product.quantity);
                holder.price.setText(product.price);
                for (OrderItemEntity.Product.Option option:product.option) {
                    switch (option.name) {
                        case "Size":
                            holder.size.setText(option.value);
                            break;
                        case "size":
                            holder.size.setText(option.value);
                            break;
                        case "Color":
                            holder.color.setText(option.value);
                            break;
                        case "color":
                            holder.color.setText(option.value);
                            break;
                        default:
                            break;
                    }
                }
            }
        };

        holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder.recyclerView.getContext()));
        holder.recyclerView.setAdapter(productAdapter);
        holder.recyclerView.addItemDecoration(new LinearLayoutItemDecoration(holder.recyclerView.getContext(),LinearLayoutItemDecoration.VERTICAL_LIST));
        productAdapter.loadData();
        productAdapter.notifyDataSetChanged();

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

        @BindView(R.id.rv_order_list_product)
        RecyclerView recyclerView;

        @BindView(R.id.bt_detail)
        CardView detail;

        @BindView(R.id.bt_cancel)
        TextView btCancel;

        @BindView(R.id.bt_refund)
        TextView btRefund;

        @BindView(R.id.bt_confirm_receive)
        CardView btConfirmReceive;

        @BindView(R.id.bt_review)
        CardView btReview;

        @BindView(R.id.order_operation)
        LinearLayout orderOperation;


        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder{

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

        ProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
