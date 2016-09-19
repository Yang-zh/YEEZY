package com.fangzhich.yeezy.base.ui.recyclerview;

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
        if (SPAN_COUNT == 2) {
//            setItemOffsets in GridLayout
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
        }
    }
}

