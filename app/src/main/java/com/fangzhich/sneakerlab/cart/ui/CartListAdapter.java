package com.fangzhich.sneakerlab.cart.ui;

import android.animation.ValueAnimator;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.data.event.RxBus;
import com.fangzhich.sneakerlab.base.ui.recyclerview.BaseRecyclerViewAdapter;
import com.fangzhich.sneakerlab.base.widget.NumberView;
import com.fangzhich.sneakerlab.cart.data.entity.CartEntity;
import com.fangzhich.sneakerlab.cart.data.event.CartItemQuantityChangeEvent;
import com.fangzhich.sneakerlab.cart.data.event.CartItemRemoveEvent;
import com.fangzhich.sneakerlab.cart.data.net.CartApi;
import com.fangzhich.sneakerlab.util.TagFormatUtil;
import com.fangzhich.sneakerlab.util.ToastUtil;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.carbswang.android.numberpickerview.library.NumberPickerView;
import rx.SingleSubscriber;
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
                    ToastUtil.toast(throwable.getMessage());
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
        holder.quantityDetail.setText(cartItem.quantity);
        holder.ratingBar.getAnimationBuilder()
                .setRatingTarget(Float.parseFloat(cartItem.rating))
                .setDuration(1000)
                .setRepeatCount(0)
                .setInterpolator(new LinearInterpolator()).start();
        holder.tvCommentCount.setText(TagFormatUtil.from(holder.itemView.getResources().getString(R.string.BracketsFormat))
                .with("content",cartItem.reviews)
                .format());
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
                                loadData();
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
                View numberView = View.inflate(v.getContext(),R.layout.dialog_number_view,null);
                final AlertDialog dialog = new AlertDialog.Builder(v.getContext(),R.style.alertDialog).create();
                dialog.setView(numberView);
                final NumberPickerView picker = (NumberPickerView) numberView.findViewById(R.id.picker);
                String[] values = new String[99];
                for (int i=1;i<100;i++) {
                    values[i-1] = String.valueOf(i);
                }
                picker.setDisplayedValues(values);
                picker.setMinValue(1);
                picker.setMaxValue(99);
                picker.setValue(Integer.valueOf(holder.quantityDetail.getText().toString()));
                CardView btConfirm = (CardView) numberView.findViewById(R.id.bt_confirm);
                btConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        picker.setClickable(false);
                        final int quantity = picker.getValue();
                        CartApi.editItemInCart(cartItem.cart_id, String.valueOf(quantity), new SingleSubscriber<Object>() {
                            @Override
                            public void onSuccess(Object value) {
                                dialog.dismiss();
                                holder.quantityDetail.setText(String.valueOf(quantity));
                                loadData();
                            }

                            @Override
                            public void onError(Throwable error) {
                                ToastUtil.toast(error.getMessage());
                            }
                        });
                    }
                });
                dialog.show();
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
        @BindView(R.id.quantity_detail)
        TextView quantityDetail;
        @BindView(R.id.shipping_detail)
        TextView shippingDetail;
        @BindView(R.id.tv_productPrice)
        TextView tvProductPrice;
        @BindView(R.id.tv_productOriginalPrice)
        TextView tvProductOriginalPrice;
        @BindView(R.id.tv_commentCount)
        TextView tvCommentCount;
        @BindView(R.id.remove)
        TextView remove;
        @BindView(R.id.edit)
        TextView edit;
        @BindView(R.id.rating_bar)
        SimpleRatingBar ratingBar;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
