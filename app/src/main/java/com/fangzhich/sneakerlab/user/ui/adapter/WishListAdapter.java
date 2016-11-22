package com.fangzhich.sneakerlab.user.ui.adapter;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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

public class WishListAdapter extends BaseRecyclerViewAdapter<WishEntity, RecyclerView.ViewHolder> {

    private final static int NO_DATA = 1;
    private final static int DEFAULT = 2;

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
//                WishEntity entity = new WishEntity();
//                entity.isNull = true;
//                mData = new ArrayList<>();
//                mData.add(0,entity);
//                notifyDataSetChanged();
                if (listener!=null) {
                    listener.noData();
                }
                Timber.e(error.getMessage());
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case NO_DATA:
                itemView = View.inflate(parent.getContext(), R.layout.view_collection_empty, null);
                return new EmptyViewHolder(itemView);
            default:
                itemView = View.inflate(parent.getContext(), R.layout.item_product, null);
                return new ViewHolder(itemView);
        }
    }

    @Override
    protected void onBindHolder(final RecyclerView.ViewHolder holder, final int position) {

//        switch (getItemViewType(position)) {
//            case NO_DATA:
//                holder.itemView.findViewById(R.id.continue_shopping).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        loadData();
//                    }
//                });
//                break;
//            default:
                ViewHolder defaultHolder = (ViewHolder) holder;

                WishEntity wish =  mData.get(position);

                if (wish.name==null) {
                    return;
                }

                Glide.with(holder.itemView.getContext())
                        .load(wish.image)
                        .placeholder(R.mipmap.product_image_placeholder)
                        .fitCenter()
                        .crossFade()
                        .into(defaultHolder.productImage);
                defaultHolder.productName.setText(wish.name);
                defaultHolder.productPrice.setText(String.valueOf(TagFormatUtil
                        .from(holder.itemView.getContext().getResources().getString(R.string.priceFormat))
                        .with("price",wish.price)
                        .format()));
//        if (productItem.original_price>productItem.special_price) {
//            holder.productOriginalPrice.setText(String.valueOf(TagFormatUtil
//                    .from(holder.itemView.getContext().getResources().getString(R.string.priceFormat))
//                    .with("price",productItem.original_price)
//                    .format()));
//        }
        defaultHolder.promotion.setVisibility(wish.promotion==1?View.VISIBLE:View.GONE);
        defaultHolder.discount.setText("-"+wish.discount);
        defaultHolder.discount.setVisibility(TextUtils.isEmpty(wish.discount) || wish.discount.equals("0")?View.GONE:View.VISIBLE);
                defaultHolder.productOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                defaultHolder.productSellVolume.setText(R.string.nulll);
                defaultHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Timber.d("On Item %d Click",position);
                        Intent intent = new Intent(v.getContext(),ProductDetailActivity.class);
                        intent.putExtra("product_id", mData.get(holder.getAdapterPosition()).product_id);
                        v.getContext().startActivity(intent);
                    }
                });
//                break;
//        }
    }

    @Override
    public int getItemViewType(int position) {
//        if (mData.get(position).isNull) {
//            return NO_DATA;
//        } else {
            return DEFAULT;
//        }
    }

    protected class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
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
        @BindView(R.id.promotion)
        ImageView promotion;
        @BindView(R.id.product_discount)
        TextView discount;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
