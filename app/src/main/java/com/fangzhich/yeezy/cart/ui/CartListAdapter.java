package com.fangzhich.yeezy.cart.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.base.ui.recyclerview.BaseRecyclerViewAdapter;
import com.fangzhich.yeezy.cart.data.entity.CartItemEntity;
import com.fangzhich.yeezy.util.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * CartListAdapter
 * Created by Khorium on 2016/9/23.
 */
class CartListAdapter extends BaseRecyclerViewAdapter<CartItemEntity, CartListAdapter.ViewHolder> {

    private CartManager cartManager = new CartManager();

    private ArrayList<CartItemEntity> mCartList;

    @Override
    public ArrayList<CartItemEntity> loadData() {
        return null;
    }

    @Override
    public void loadMore() {
        cartManager.getCartList(new CartManager.CartListCallBack() {
            @Override
            public void onSuccess(ArrayList<CartItemEntity> cartList) {
                mCartList = cartList;
                notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable throwable) {
                ToastUtil.toast(throwable.getMessage());
                Timber.e(throwable.getMessage());
            }
        });
    }

    @Override
    public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(parent.getContext(), R.layout.item_product_in_bill, null);
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindHolder(ViewHolder holder, int position) {

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
