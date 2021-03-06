package com.fangzhich.sneakerlab.base.ui.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.fangzhich.sneakerlab.main.data.entity.MessageEntity;
import com.fangzhich.sneakerlab.product.data.entity.ProductEntity;
import com.fangzhich.sneakerlab.product.ui.adapter.ReviewListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * BaseRecyclerViewAdapter
 * Created by Khorium on 2016/9/18.
 */
public abstract class BaseRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected List<T> mData = new ArrayList<>();

    protected BaseRecyclerViewAdapter() {
    }

    public abstract void loadData();

    public void loadMore() {

    }

    public void loadMore(OnLoadFinishListener listener) {
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateHolder(parent, viewType);
    }

    public abstract VH onCreateHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(VH holder, int position) {
        onBindHolder(holder, position);
    }

    protected abstract void onBindHolder(VH holder, int position);

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    public List<T> getData() {
        return mData;
    }

    public void addItem(T entity) {
        mData.add(entity);
        notifyItemInserted(mData.size());
    }

    protected OnLoadFinishListener onLoadFinishListener;
    public void setOnLoadFinishListener(OnLoadFinishListener listener) {
        this.onLoadFinishListener = listener;
    }
    public interface OnLoadFinishListener {
        void onLoadFinish();
    }

    protected OnAdapterStatusChangeListener listener;
    public void setOnAdapterStatusChangeListener(OnAdapterStatusChangeListener listener) {
        this.listener = listener;
    }

    public interface OnAdapterStatusChangeListener {
        void noData();
    }
}
