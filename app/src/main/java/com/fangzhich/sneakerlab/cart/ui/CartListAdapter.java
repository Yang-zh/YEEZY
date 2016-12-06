package com.fangzhich.sneakerlab.cart.ui;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.recyclerview.BaseRecyclerViewAdapter;
import com.fangzhich.sneakerlab.base.widget.NumberView;
import com.fangzhich.sneakerlab.cart.data.entity.CartEntity;
import com.fangzhich.sneakerlab.util.TagFormatUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * CartListAdapter
 * Created by Khorium on 2016/9/23.
 */
class CartListAdapter extends BaseRecyclerViewAdapter<CartEntity.Product, CartListAdapter.ViewHolder> {

    private CartManager cartManager = new CartManager();
    private OnCartStatusChangeListener onCartStatusChangeListener;

    interface OnCartStatusChangeListener {
        void loadCartData(CartEntity cart);

        void checkSubscribe();

        void noData();
    }

    public void setOnCartStatusChangeListener(OnCartStatusChangeListener listener) {
        onCartStatusChangeListener = listener;
    }


    @Override
    public void loadData() {
        if (cartManager==null) {
            return;
        }
        cartManager.getCartList(new CartManager.CartListCallBack() {
            @Override
            public void onSuccess(CartEntity cart) {
                mData = cart.products;

                if (onCartStatusChangeListener !=null) {
                    onCartStatusChangeListener.loadCartData(cart);
                }

                notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable throwable) {

                if (onCartStatusChangeListener !=null) {
                    onCartStatusChangeListener.noData();
                }

                Timber.e(throwable);
            }
        });
    }


    @Override
    public void loadMore() {
    }

    @Override
    public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(parent.getContext(), R.layout.item_cart_list, null);
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
        holder.tvProductPrice.setText(TagFormatUtil.from(holder.itemView.getResources().getString(R.string.priceFormat))
                .with("price",cartItem.special_price)
                .format());
        holder.tvProductOriginalPrice.setText(TagFormatUtil.from(holder.itemView.getResources().getString(R.string.priceFormat))
                .with("price",cartItem.original_price)
                .format());
        holder.tvProductOriginalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        for (CartEntity.Product.Option option:cartItem.options) {
            switch (option.name) {
                case "size":
                case "Size":
                    holder.sizeIs.setVisibility(View.VISIBLE);
                    holder.sizeDetail.setVisibility(View.VISIBLE);
                    holder.sizeDetail.setText(option.value);
                    break;
                case "color":
                case "Color":
                    holder.colorIs.setVisibility(View.VISIBLE);
                    holder.colorDetail.setVisibility(View.VISIBLE);
                    holder.colorDetail.setText(option.value);
                    break;
            }
        }
        holder.delete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cartManager.removeCartItem(cartItem.cart_id, new CartManager.RemoveItemCallBack() {
                            @Override
                            public void onSuccess() {
                                mData.remove(holder.getAdapterPosition());
                                onCartStatusChangeListener.checkSubscribe();
                                notifyItemRemoved(holder.getAdapterPosition());
                                loadData();
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                Timber.e(throwable);
                            }
                        });
                    }
                });
        holder.numberView.setGoods_storage(99);
        holder.numberView.setOnAmountChangeListener(new NumberView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                holder.numberView.setClickable(false);
                cartManager.editCartItem(cartItem.cart_id, String.valueOf(amount), new CartManager.EditItemCallBack() {
                    @Override
                    public void onSuccess() {
                        holder.numberView.setClickable(true);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Timber.e(throwable);
                    }
                });
            }
        });

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_productImage)
        ImageView ivProductImage;
        @BindView(R.id.tv_productName)
        TextView tvProductName;
        @BindView(R.id.color_is)
        TextView colorIs;
        @BindView(R.id.color_detail)
        TextView colorDetail;
        @BindView(R.id.size_is)
        TextView sizeIs;
        @BindView(R.id.size_detail)
        TextView sizeDetail;
        @BindView(R.id.tv_productPrice)
        TextView tvProductPrice;
        @BindView(R.id.tv_productOriginalPrice)
        TextView tvProductOriginalPrice;
        @BindView(R.id.delete)
        TextView delete;
        @BindView(R.id.quantity_view)
        NumberView numberView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
