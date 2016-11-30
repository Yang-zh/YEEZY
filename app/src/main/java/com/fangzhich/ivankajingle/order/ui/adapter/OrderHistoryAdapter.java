package com.fangzhich.ivankajingle.order.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangzhich.ivankajingle.R;
import com.fangzhich.ivankajingle.base.ui.recyclerview.BaseRecyclerViewAdapter;
import com.fangzhich.ivankajingle.base.ui.recyclerview.LinearLayoutItemDecoration;
import com.fangzhich.ivankajingle.base.widget.CustomDialog;
import com.fangzhich.ivankajingle.main.ui.SupportActivity;
import com.fangzhich.ivankajingle.order.data.entity.OrderItemEntity;
import com.fangzhich.ivankajingle.order.data.net.OrderApi;
import com.fangzhich.ivankajingle.order.presentation.OrderListContract;
import com.fangzhich.ivankajingle.order.presentation.OrderListPresenter;
import com.fangzhich.ivankajingle.order.ui.OrderDetailActivity;
import com.fangzhich.ivankajingle.order.ui.OrderHistoryActivity;
import com.fangzhich.ivankajingle.util.TagFormatUtil;
import com.fangzhich.ivankajingle.util.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.SingleSubscriber;
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
        if (listener!=null) {
            listener.noData();
        }
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
    protected void onBindHolder(final ViewHolder holder, final int position) {
        final OrderItemEntity order = mData.get(position);

        //just enjoy callback hell
        switch (order.order_status) {
            case "Processing":
                holder.orderOperation.setVisibility(View.VISIBLE);
                holder.btCancel.setVisibility(View.VISIBLE);
                holder.btCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new CustomDialog().initPopup(v.getContext(), R.layout.dialog_cancel, new CustomDialog.Listener() {
                            @Override
                            public void onInit(final PopupWindow dialog, View content) {
                                content.findViewById(R.id.bt_yes).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        OrderApi.cancelOrder(order.order_id, new SingleSubscriber<Object>() {
                                            @Override
                                            public void onSuccess(Object value) {
                                                ToastUtil.toast("Cancel order success");
                                                dialog.dismiss();
                                                order.order_status = "Canceled";
                                                notifyItemChanged(position);
                                            }

                                            @Override
                                            public void onError(Throwable error) {
                                                dialog.dismiss();
                                                ToastUtil.toast(error.getMessage());
                                            }
                                        });
                                        dialog.dismiss();
                                    }
                                });
                                content.findViewById(R.id.bt_no).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                            }

                            @Override
                            public void onDismiss(PopupWindow dialog, View content) {

                            }
                        }).showPopup(holder.itemView, Gravity.CENTER);
                    }
                });
                break;
            case "Shipping":
                holder.orderOperation.setVisibility(View.VISIBLE);
                holder.btConfirmReceive.setVisibility(View.VISIBLE);
                holder.btConfirmReceive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {new CustomDialog().initPopup(v.getContext(), R.layout.dialog_confirm, new CustomDialog.Listener() {
                        @Override
                        public void onInit(final PopupWindow dialog, View content) {
                            v.findViewById(R.id.bt_yes).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                OrderApi.confirmOrder(order.order_id, new SingleSubscriber<Object>() {
                                    @Override
                                    public void onSuccess(Object value) {
                                        ToastUtil.toast("Confirm order success");
                                        dialog.dismiss();
                                        order.order_status = "Completed";
                                        notifyItemChanged(position);
                                    }

                                    @Override
                                    public void onError(Throwable error) {
                                        ToastUtil.toast(error.getMessage());
                                        dialog.dismiss();
                                    }
                                });
                                dialog.dismiss();
                                }
                            });
                            v.findViewById(R.id.bt_no).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                        }

                        @Override
                        public void onDismiss(PopupWindow dialog, View content) {

                        }
                    }).showPopup(holder.itemView, Gravity.CENTER);
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
                        .placeholder(R.mipmap.product_image_placeholder)
                        .fitCenter()
                        .crossFade()
                        .into(holder.ivProductImage);
                holder.productName.setText(product.name);
                holder.quantity.setText(product.quantity);
                holder.price.setText(TagFormatUtil.from(itemView.getResources().getString(R.string.priceFormat))
                        .with("price",product.price)
                        .format());
                for (OrderItemEntity.Product.Option option:product.option) {
                    switch (option.name) {
                        case "Size":
                            holder.sizeIs.setVisibility(View.VISIBLE);
                            holder.size.setVisibility(View.VISIBLE);
                            holder.size.setText(option.value);
                            break;
                        case "size":
                            holder.sizeIs.setVisibility(View.VISIBLE);
                            holder.size.setVisibility(View.VISIBLE);
                            holder.size.setText(option.value);
                            break;
                        case "Color":
                            holder.colorIs.setVisibility(View.VISIBLE);
                            holder.color.setVisibility(View.VISIBLE);
                            holder.color.setText(option.value);
                            break;
                        case "color":
                            holder.colorIs.setVisibility(View.VISIBLE);
                            holder.color.setVisibility(View.VISIBLE);
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
        holder.recyclerView.addItemDecoration(new LinearLayoutItemDecoration(holder.recyclerView.getContext(),LinearLayoutItemDecoration.VERTICAL_LIST,0));
        productAdapter.loadData();
        productAdapter.notifyDataSetChanged();

        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),OrderDetailActivity.class);
                intent.putExtra("order_id",order.order_id);
                int requestCode;
                switch (order.order_status) {
                    case "Processing":
                        requestCode = OrderHistoryActivity.IS_CANCELED;
                        ((Activity)v.getContext()).startActivityForResult(intent,requestCode);
                        break;
                    case "Shipping":
                        requestCode = OrderHistoryActivity.IS_CONFIRMED;
                        ((Activity)v.getContext()).startActivityForResult(intent,requestCode);
                        break;
                    case "Completed":
                        requestCode = OrderHistoryActivity.IS_REVIEWED;
                        ((Activity)v.getContext()).startActivityForResult(intent,requestCode);
                        break;
                    case "Canceled":
                        v.getContext().startActivity(intent);
                        break;
                }
            }
        });
    }

    public void notifyOrderStatusChanged(String order_id,int status) {
        for (int i=0;i<mData.size();i++) {
            OrderItemEntity entity = mData.get(i);
            if (entity.order_id.equals(order_id)) {
                switch (status) {
                    case OrderHistoryActivity.CANCELED:
                        entity.order_status = "Canceled";
                        break;
                    case OrderHistoryActivity.CONFIRMED:
                        entity.order_status = "Completed";
                        break;
                    case OrderHistoryActivity.REVIEWED:
                        entity.order_status = "Completed";
                        break;
                }
            }
        }
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
        @BindView(R.id.color_is)
        TextView colorIs;
        @BindView(R.id.color)
        TextView color;
        @BindView(R.id.size_is)
        TextView sizeIs;
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
