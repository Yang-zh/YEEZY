package com.fangzhich.sneakerlab.user.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.recyclerview.BaseRecyclerViewAdapter;
import com.fangzhich.sneakerlab.user.data.entity.WishEntity;
import com.fangzhich.sneakerlab.user.data.net.UserApi;
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
        View itemView = View.inflate(parent.getContext(), R.layout.item_wish_list, null);
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindHolder(ViewHolder holder, int position) {
       WishEntity wish =  mData.get(position);
        holder.picBig.setMinimumHeight(holder.picBig.getWidth());
        holder.pic1.setMinimumHeight(holder.pic1.getWidth());
        holder.pic2.setMinimumHeight(holder.pic2.getWidth());
        holder.plusOne.setMinimumHeight(holder.plusOne.getWidth());
       Glide.with(holder.itemView.getContext())
               .load(wish.image)
               .fitCenter()
               .crossFade()
               .into(holder.picBig);
//        if (wish.images==null) {
//            return;
//        }
//        if (wish.images.size()>=1) {
//            Glide.with(holder.itemView.getContext())
//                    .load(wish.images.get(0))
//                    .fitCenter()
//                    .crossFade()
//                    .into(holder.picBig);
//        }
//        if (wish.images.size()>2) {
//            Glide.with(holder.itemView.getContext())
//                    .load(wish.images.get(1))
//                    .fitCenter()
//                    .crossFade()
//                    .into(holder.pic1);
//        }
//        if (wish.images.size()>=3){
//            Glide.with(holder.itemView.getContext())
//                    .load(wish.images.get(2))
//                    .fitCenter()
//                    .crossFade()
//                    .into(holder.pic2);
//        }
        holder.plusOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.toast("plus one");
            }
        });
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.picBig)
        ImageView picBig;
        @BindView(R.id.pic1)
        ImageView pic1;
        @BindView(R.id.pic2)
        ImageView pic2;
        @BindView(R.id.plusOne)
        TextView plusOne;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
