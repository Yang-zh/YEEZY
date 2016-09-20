package com.fangzhich.yeezy.product.ui.adapter;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.base.ui.recyclerview.BaseRecyclerViewAdapter;
import com.fangzhich.yeezy.product.presentation.ProductListContract;
import com.fangzhich.yeezy.product.presentation.ProductListPresenter;
import com.fangzhich.yeezy.product.ui.ProductDetailActivity;
import com.fangzhich.yeezy.product.data.entity.ProductItemEntity;
import com.fangzhich.yeezy.util.ToastUtil;

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

    private ArrayList<ProductItemEntity> mProductList = new ArrayList<>();
    private int totalPage;
    private int categoryId;

    public ProductListAdapter(int category_id) {
        this.categoryId = category_id;
        setPresenter(new ProductListPresenter(this));
        loadData();
    }
    @Override
    public void setPresenter(ProductListContract.Presenter presenter) {
        mPresenter = (ProductListPresenter) presenter;
    }

    @Override
    public ArrayList<ProductItemEntity> loadData() {
        mPresenter.getProductList(0,20,categoryId);
        totalPage = 1;
        return mProductList;
    }

    @Override
    public void onLoadDataSuccess(ArrayList<ProductItemEntity> productList) {
        mProductList = productList;
        notifyDataSetChanged();
    }

    @Override
    public void loadMore() {
        mPresenter.getProductListMore(++totalPage,20,categoryId);
        notifyDataSetChanged();
    }

    @Override
    public void onLoadError(Throwable throwable) {
        ToastUtil.toast(throwable.getMessage());
        Timber.e(throwable.getMessage());
    }

    @Override
    public void onLoadMoreSuccess(ArrayList<ProductItemEntity> productList) {
        int positionStart = mProductList.size() + 1;
        mProductList.addAll(productList);
        notifyItemRangeChanged(positionStart, productList.size());
    }

    @Override
    public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.item_product, null));
    }

    @Override
    protected void onBindHolder(final ViewHolder holder, final int position) {
        ProductItemEntity productItem = mProductList.get(position);

        Glide.with(holder.itemView.getContext())
                .load(productItem.image)
                .centerCrop()
                .crossFade()
                .into(holder.productImage);
        holder.productName.setText(productItem.name);
        holder.productPrice.setText(productItem.price);
        holder.productOriginalPrice.setText(R.string.nulll);
        holder.productOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.productSellVolume.setText(R.string.nulll);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("On Item " + position + " Click");
                Intent intent = new Intent(v.getContext(),ProductDetailActivity.class);
                intent.putExtra("product_id", Integer.parseInt(mProductList.get(holder.getAdapterPosition()).product_id));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
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
