package com.fangzhich.sneakerlab.user.ui.adapter;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.recyclerview.BaseRecyclerViewAdapter;
import com.fangzhich.sneakerlab.product.data.entity.ProductItemEntity;
import com.fangzhich.sneakerlab.product.ui.ProductDetailActivity;
import com.fangzhich.sneakerlab.user.data.entity.WishEntity;
import com.fangzhich.sneakerlab.user.data.net.UserApi;
import com.fangzhich.sneakerlab.util.TagFormatUtil;
import com.fangzhich.sneakerlab.util.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.SingleSubscriber;
import timber.log.Timber;

/**
 * WishListAdapter
 * Created by Khorium on 2016/10/8.
 */

public class WishListAdapter extends BaseRecyclerViewAdapter<WishEntity, WishListAdapter.ViewHolder> {

    public WishListAdapter() {
        loadData();
    }

    @Override
    public void loadData() {
        UserApi.getWishList(new SingleSubscriber<ArrayList<WishEntity>>() {
            @Override
            public void onSuccess(ArrayList<WishEntity> value) {
                mData = value;
                notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable error) {
                Timber.e(error.getMessage());
                ToastUtil.toast(error.getMessage());
            }
        });
    }

    @Override
    public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(parent.getContext(), R.layout.item_product, null);
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindHolder(final ViewHolder holder, final int position) {

        WishEntity wish =  mData.get(position);

        Glide.with(holder.itemView.getContext())
                .load(wish.image)
                .placeholder(R.mipmap.product_image_placeholder)
                .fitCenter()
                .crossFade()
                .into(holder.productImage);
        holder.productName.setText(wish.name);
        holder.productPrice.setText(String.valueOf(TagFormatUtil
                .from(holder.itemView.getContext().getResources().getString(R.string.priceFormat))
                .with("price",wish.price)
                .format()));
//        if (productItem.original_price>productItem.special_price) {
//            holder.productOriginalPrice.setText(String.valueOf(TagFormatUtil
//                    .from(holder.itemView.getContext().getResources().getString(R.string.priceFormat))
//                    .with("price",productItem.original_price)
//                    .format()));
//        }
        holder.productOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.productSellVolume.setText(R.string.nulll);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("On Item %d Click",position);
                Intent intent = new Intent(v.getContext(),ProductDetailActivity.class);
                intent.putExtra("product_id", mData.get(holder.getAdapterPosition()).product_id);
                v.getContext().startActivity(intent);
            }
        });
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_productImage)
        ImageView productImage;
        @BindView(R.id.tv_productName)
        TextView productName;
        @BindView(R.id.tv_productPrice)
        TextView productPrice;
        @BindView(R.id.tv_productOriginalPrice)
        TextView productOriginalPrice;
        @BindView(R.id.tv_productSellVolume)
        TextView productSellVolume;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
