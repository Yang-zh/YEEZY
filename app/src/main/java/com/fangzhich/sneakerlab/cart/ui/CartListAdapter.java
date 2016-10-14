package com.fangzhich.sneakerlab.cart.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.recyclerview.BaseRecyclerViewAdapter;
import com.fangzhich.sneakerlab.cart.data.entity.CartEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * CartListAdapter
 * Created by Khorium on 2016/9/23.
 */
class CartListAdapter extends BaseRecyclerViewAdapter<CartEntity.Product, CartListAdapter.ViewHolder> {

    private CartManager cartManager = new CartManager();
    private OnLoadDataListener onLoadDataListener;

    interface OnLoadDataListener {
        void loadCartData(CartEntity cart);

        void checkSubscribe();
    }

    public void setOnLoadDataListener (OnLoadDataListener listener) {
        onLoadDataListener = listener;
    }


    @Override
    public void loadData() {
        if (cartManager!=null) {
            cartManager.getCartList(new CartManager.CartListCallBack() {
                @Override
                public void onSuccess(CartEntity cart) {
                    mData = cart.products;

                    if (onLoadDataListener!=null) {
                        onLoadDataListener.loadCartData(cart);
                    }

                    notifyDataSetChanged();
                }

                @Override
                public void onError(Throwable throwable) {
                    Timber.e(throwable.getMessage());
                }
            });
        }
    }

    @Override
    public void loadMore() {
    }

    @Override
    public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(parent.getContext(), R.layout.item_product_in_bill, null);
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindHolder(final ViewHolder holder, final int position) {
        final CartEntity.Product cartItem = mData.get(position);
        holder.tvProductName.setText(cartItem.name);
        Glide.with(holder.itemView.getContext())
                .load(cartItem.image)
                .placeholder(R.mipmap.product_image_placeholder)
                .fitCenter()
                .into(holder.ivProductImage);
        holder.shippingDetail.setText(cartItem.shipping);
        holder.tvProductPrice.setText(cartItem.special_price);

        holder.remove.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cartManager.removeCartItem(cartItem.cart_id, new CartManager.RemoveItemCallBack() {
                            @Override
                            public void onSuccess() {
                                mData.remove(holder.getAdapterPosition());
                                onLoadDataListener.checkSubscribe();
                                notifyItemRemoved(holder.getAdapterPosition());
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                Timber.e(throwable.getMessage());
                            }
                        });
                    }
                });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_productImage)
        ImageView ivProductImage;
        @BindView(R.id.tv_productName)
        TextView tvProductName;
        @BindView(R.id.color_detail)
        TextView colorDetail;
        @BindView(R.id.quantity_detail)
        TextView quantityDetail;
        @BindView(R.id.shipping_detail)
        TextView shippingDetail;
        @BindView(R.id.tv_productPrice)
        TextView tvProductPrice;
        @BindView(R.id.tv_productOriginalPrice)
        TextView tvProductOriginalPrice;
        @BindView(R.id.remove)
        TextView remove;
        @BindView(R.id.edit)
        TextView edit;
        @BindView(R.id.rating_bar)
        RatingBar ratingBar;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
