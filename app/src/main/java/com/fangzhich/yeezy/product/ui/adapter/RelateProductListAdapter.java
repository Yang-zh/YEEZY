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
import com.fangzhich.yeezy.product.data.entity.PopularProductEntity;
import com.fangzhich.yeezy.product.data.entity.ProductItemEntity;
import com.fangzhich.yeezy.product.presentation.ProductListContract;
import com.fangzhich.yeezy.product.presentation.ProductListPresenter;
import com.fangzhich.yeezy.product.presentation.RelateProductListContract;
import com.fangzhich.yeezy.product.presentation.RelateProductListPresenter;
import com.fangzhich.yeezy.product.ui.ProductDetailActivity;
import com.fangzhich.yeezy.util.ToastUtil;

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

    //后台API页码从0开始，因此初始为-1而不是0
    private int totalPage = -1;

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
            mPresenter.getPopularProductList(0,20);
            totalPage = 0;
        }
    }

    @Override
    public void onLoadDataSuccess(ArrayList<PopularProductEntity> popularProductList) {
        mData = popularProductList;
        notifyDataSetChanged();

    }

    @Override
    public void onLoadError(Throwable throwable) {
        ToastUtil.toast(throwable.getMessage());
        Timber.e(throwable.getMessage());
    }

    @Override
    public void loadMore() {
        mPresenter.getPopularProductListMore(++totalPage,20);
        notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreSuccess(ArrayList<PopularProductEntity> popularProductList) {
        int positionStart = mData.size() + 1;
        mData.addAll(popularProductList);
        notifyItemRangeChanged(positionStart, popularProductList.size());

    }

    @Override
    public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.item_product, null));
    }

    @Override
    protected void onBindHolder(final ViewHolder holder, final int position) {
        PopularProductEntity productItem = mData.get(position);

        Glide.with(holder.itemView.getContext())
                .load(productItem.images.get(0))
                .centerCrop()
                .fitCenter()
                .crossFade()
                .into(holder.productImage);
        holder.productName.setText(productItem.name);
        holder.productPrice.setText(productItem.price);
        holder.productOriginalPrice.setText(R.string.nulll);
        holder.productOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.productSellVolume.setText(R.string.nulll);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Timber.d("On Item %d Click",position);
                Intent intent = new Intent(view.getContext(),ProductDetailActivity.class);
                intent.putExtra("product_id", Integer.parseInt(mData.get(holder.getAdapterPosition()).product_id));
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

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
