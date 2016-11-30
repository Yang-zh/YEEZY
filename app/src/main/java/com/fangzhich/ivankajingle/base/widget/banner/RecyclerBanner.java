package com.fangzhich.ivankajingle.base.widget.banner;

import android.content.Context;
import android.view.View;

import java.util.List;

/**
 * RecyclerBanner
 * Created by Khorium on 2016/9/27.
 */

public class RecyclerBanner extends View{

    public RecyclerBanner(Context context) {
        super(context);
    }

    class CircleManager<T> {

        private List<T> images;

        CircleManager(List<T> images) {
            this.images = images;
        }
    }
}
