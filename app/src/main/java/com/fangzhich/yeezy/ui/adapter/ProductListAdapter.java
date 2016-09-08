package com.fangzhich.yeezy.ui.adapter;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.ui.activity.ProductDetailActivity;
import com.fangzhich.yeezy.net.Bean.ProductEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * productListAdapter
 * Created by Khorium on 2016/8/30.
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder> {

    private ArrayList<ProductEntity> products = new ArrayList<>();

    public void setData(ArrayList<ProductEntity> products) {
        this.products = products;
    }

    @Override
    public ProductListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ProductListViewHolder(View.inflate(parent.getContext(), R.layout.item_product, null));
    }

    @Override
    public void onBindViewHolder(final ProductListViewHolder holder, int position) {
//        Glide.with(holder.itemView.getContext())
//                .load(products.get(position).imageUrl)
//                .centerCrop()
//                .crossFade()
//                .into(holder.productImage);
        holder.productImage.setImageResource(R.mipmap.placeholder_true);
        holder.productName.setText(R.string.ProductName);
        holder.productPrice.setText(R.string.ProductPrice);
        holder.productOriginalPrice.setText(R.string.ProductOriginalPrice);
        holder.productOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.productSellVolume.setText(R.string.ProductSellVolume);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ProductDetailActivity.class);
                intent.putExtra("id",products.get(holder.getAdapterPosition()).id);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class ProductListViewHolder extends RecyclerView.ViewHolder {

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

        public ProductListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
