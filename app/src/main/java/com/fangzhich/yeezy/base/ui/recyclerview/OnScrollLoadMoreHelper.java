package com.fangzhich.yeezy.base.ui.recyclerview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 *
 * Created by Khorium on 2016/9/18.
 */
public class OnScrollLoadMoreHelper extends RecyclerView.OnScrollListener{

    private BaseRecyclerViewAdapter mAdapter;

    public OnScrollLoadMoreHelper(BaseRecyclerViewAdapter adapter){
        mAdapter = adapter;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int lastVisibleItemPosition = ((LinearLayoutManager)layoutManager).findLastVisibleItemPosition();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();

        if (visibleItemCount>0 && newState == RecyclerView.SCROLL_STATE_IDLE
                && lastVisibleItemPosition >= totalItemCount-1) {
            mAdapter.loadMore();
        }
    }
}
