package com.fangzhich.ivankajingle.base.ui.recyclerview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 *
 * Created by Khorium on 2016/9/18.
 */
public class OnScrollLoadMoreHelper extends RecyclerView.OnScrollListener{

    private BaseRecyclerViewAdapter mAdapter;
    private boolean isLoadData;

    public OnScrollLoadMoreHelper(BaseRecyclerViewAdapter adapter){
        mAdapter = adapter;
        mAdapter.setOnLoadFinishListener(new BaseRecyclerViewAdapter.OnLoadFinishListener() {
            @Override
            public void onLoadFinish() {
                isLoadData = false;
            }
        });
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int lastVisibleItemPosition = ((LinearLayoutManager)layoutManager).findLastVisibleItemPosition();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();

        if (visibleItemCount>0 && newState == RecyclerView.SCROLL_STATE_IDLE
                && lastVisibleItemPosition >= totalItemCount-1 &&!isLoadData) {
            isLoadData = true;
            mAdapter.loadMore(new BaseRecyclerViewAdapter.OnLoadFinishListener() {
                @Override
                public void onLoadFinish() {
                    setLoadDataStatus(false);
                }
            });
        }
    }

    public void setLoadDataStatus(boolean isLoadData){
        this.isLoadData = isLoadData;
    }
}
