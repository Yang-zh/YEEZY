package com.fangzhich.ivankajingle.cart.ui;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangzhich.ivankajingle.R;
import com.fangzhich.ivankajingle.base.ui.recyclerview.BaseRecyclerViewAdapter;
import com.fangzhich.ivankajingle.base.widget.PickerView;
import com.fangzhich.ivankajingle.cart.data.entity.CartEntity;
import com.fangzhich.ivankajingle.cart.data.net.CartApi;
import com.fangzhich.ivankajingle.util.TagFormatUtil;
import com.fangzhich.ivankajingle.util.ToastUtil;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import butterknife.BindView;
import butterknife.ButterKnife;
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

        void noData();
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

                    if (onLoadDataListener!=null) {
                        onLoadDataListener.noData();
                    }

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
                final PickerView pickerView = new PickerView(v.getContext());
                pickerView.initPickerView(R.layout.dialog_number_view, 1, 99, Integer.valueOf(holder.quantityDetail.getText().toString()), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pickerView.setClickable(false);
                        final int quantity = pickerView.getValue();
                        CartApi.editItemInCart(cartItem.cart_id, String.valueOf(quantity), new SingleSubscriber<Object>() {
                            @Override
                            public void onSuccess(Object value) {
                                pickerView.dismiss();
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
                pickerView.show();
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
