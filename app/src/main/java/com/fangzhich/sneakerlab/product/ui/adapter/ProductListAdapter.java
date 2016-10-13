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
import com.fangzhich.sneakerlab.product.data.entity.ProductItemEntity;
import com.fangzhich.sneakerlab.product.presentation.ProductListContract;
import com.fangzhich.sneakerlab.product.presentation.ProductListPresenter;
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
public class ProductListAdapter extends BaseRecyclerViewAdapter<ProductItemEntity,ProductListAdapter.ViewHolder> implements ProductListContract.View{

    private ProductListPresenter mPresenter;

    //后台API页码从0开始，因此初始为-1而不是0
    private int totalPage = 0;
    private String pageCount = "20";
    private String categoryId;

    public ProductListAdapter(String category_id) {
        this.categoryId = category_id;
        setPresenter(new ProductListPresenter(this));
        loadData();
    }

    @Override
    public void setPresenter(ProductListContract.Presenter presenter) {
        mPresenter = (ProductListPresenter) presenter;
    }

    @Override
    public void loadData() {
        if (mPresenter!=null) {
            mPresenter.getProductList("1",pageCount,categoryId);
        }
        totalPage = 1;
    }

    @Override
    public void onLoadDataSuccess(ArrayList<ProductItemEntity> productList) {
        mData = productList;
        notifyDataSetChanged();
    }

    @Override
    public void onLoadError(Throwable throwable) {

        Timber.e(throwable.getMessage());
    }

    @Override
    public void onLoadMoreError(Throwable throwable) {
        Timber.e(throwable.getMessage());
    }

    @Override
    public void loadMore() {
        mPresenter.getProductListMore(String.valueOf(++totalPage),pageCount,categoryId);
        notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreSuccess(ArrayList<ProductItemEntity> productList) {
        int positionStart = mData.size() + 1;
        mData.addAll(productList);
        notifyItemRangeChanged(positionStart, productList.size());
    }

    @Override
    public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.item_product, null));
    }

    @Override
    protected void onBindHolder(final ViewHolder holder, final int position) {
        ProductItemEntity productItem = mData.get(position);

        Glide.with(holder.itemView.getContext())
                .load(productItem.image)
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

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
