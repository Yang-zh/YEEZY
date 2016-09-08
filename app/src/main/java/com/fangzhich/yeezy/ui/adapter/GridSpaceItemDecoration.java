package com.fangzhich.yeezy.ui.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * ItemDecoration
 * Created by Khorium on 2016/8/31.
 */
public class GridSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int SPAN_COUNT;

    public GridSpaceItemDecoration(int space,int SPAN_COUNT) {
        this.space = space;
        this.SPAN_COUNT = SPAN_COUNT;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //设置GridLayout中Item的间距
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = outRect.left = space;
            outRect.bottom = outRect.right = space / 2;
        } else if (parent.getChildAdapterPosition(view) == 1) {
            outRect.top = outRect.right = space;
            outRect.bottom = outRect.left = space / 2;
        } else if (parent.getChildAdapterPosition(view) % 2 == 0) {
            outRect.left = space;
            outRect.top = outRect.bottom = outRect.right = space / 2;
        } else if (parent.getChildAdapterPosition(view) % 2 == 1) {
            outRect.right = space;
            outRect.top = outRect.bottom = outRect.left = space / 2;
        }
//        int position = parent.getChildAdapterPosition(view);
//        outRect.set(space / 2, 0, space / 2, 0);
//        if (position >= SPAN_COUNT) {
//            outRect.top = space;
//        } else {
//            outRect.top = 0;
//        }
    }
}

