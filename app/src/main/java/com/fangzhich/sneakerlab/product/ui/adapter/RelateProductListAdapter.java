package com.fangzhich.sneakerlab.product.ui.adapter;

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
import com.fangzhich.sneakerlab.product.data.entity.PopularProductEntity;
import com.fangzhich.sneakerlab.product.presentation.RelateProductListContract;
import com.fangzhich.sneakerlab.product.presentation.RelateProductListPresenter;
import com.fangzhich.sneakerlab.product.ui.ProductDetailActivity;
import com.fangzhich.sneakerlab.util.TagFormatUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * productListAdapter
 * Created by Khorium on 2016/8/30.
 */
public class RelateProductListAdapter extends BaseRecyclerViewAdapter<PopularProductEntity,RelateProductListAdapter.ViewHolder> implements RelateProductListContract.View{

    private RelateProductListPresenter mPresenter;

    private int totalPage = 0;

    public RelateProductListAdapter() {
        setPresenter(new RelateProductListPresenter(this));
        loadData();
    }
    @Override
    public void setPresenter(RelateProductListContract.Presenter presenter) {
        mPresenter = (RelateProductListPresenter) presenter;
    }

    @Override
    public void loadData() {
        if (mPresenter!=null) {
            mPresenter.getPopularProductList("1","50");
            totalPage = 1;
        }
    }

    @Override
    public void onLoadDataSuccess(ArrayList<PopularProductEntity> popularProductList) {
        mData = popularProductList;
        notifyDataSetChanged();
        onLoadFinishListener.onLoadFinish();
    }

    @Override
    public void onLoadError(Throwable throwable) {
        Timber.e(throwable);
    }

    @Override
    public void loadMore() {
        mPresenter.getPopularProductListMore(String.valueOf(++totalPage),"20");
        notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreSuccess(ArrayList<PopularProductEntity> popularProductList) {
        int positionStart = mData.size() + 1;
        mData.addAll(popularProductList);
        notifyItemRangeChanged(positionStart, popularProductList.size());
        onLoadFinishListener.onLoadFinish();
    }

    @Override
    public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.item_product, null));
    }

    @Override
    protected void onBindHolder(final ViewHolder holder, final int position) {
        PopularProductEntity productItem = mData.get(position);

        Glide.with(holder.itemView.getContext())
                .load(productItem.image)
                .placeholder(R.mipmap.product_image_placeholder)
                .centerCrop()
                .fitCenter()
                .crossFade()
                .into(holder.productImage);
        holder.productName.setText(productItem.name);
        holder.productPrice.setText(TagFormatUtil
                .from(holder.itemView.getContext().getResources().getString(R.string.priceFormat))
                .with("price",productItem.special_price)
                .format());
        if (Double.valueOf(productItem.original_price)>Double.valueOf(productItem.special_price)) {
            holder.productOriginalPrice.setText(TagFormatUtil
                    .from(holder.itemView.getContext().getResources().getString(R.string.priceFormat))
                    .with("price",productItem.original_price)
                    .format());
        }
        holder.productOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.productSellVolume.setText(R.string.nulll);
//        holder.promotion.setVisibility(productItem.promotion==1?View.VISIBLE:View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Timber.d("On Item %d Click",position);
                Intent intent = new Intent(view.getContext(),ProductDetailActivity.class);
                intent.putExtra("product_id", mData.get(holder.getAdapterPosition()).product_id);
                view.getContext().startActivity(intent);
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

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

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
