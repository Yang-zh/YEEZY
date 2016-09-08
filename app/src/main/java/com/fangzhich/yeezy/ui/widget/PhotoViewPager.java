package com.fangzhich.yeezy.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * solve photoView's bug
 * Created by Khorium on 2016/9/5.
 */
public class PhotoViewPager extends ViewPager {
    public PhotoViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PhotoViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            //uncomment if you really want to see these errors
            //e.printStackTrace();
            return false;
        }
    }
}
