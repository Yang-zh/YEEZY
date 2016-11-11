package com.fangzhich.sneakerlab.base.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * FooterBehavior
 * Created by Khorium on 2016/11/11.
 */


public class FooterBehavior extends CoordinatorLayout.Behavior<View> {

    private float targetY = -1;

    private static final String TAG = "FooterBehavior";

    public FooterBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child,
                                       View directTargetChild, View target, int nestedScrollAxes) {
        if (targetY == -1) {
            targetY = target.getY();
        }
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target,
                                  int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        float scrooY = targetY - Math.abs(target.getY());
        float scaleY = scrooY / targetY;
        child.setTranslationY(child.getHeight() * scaleY);
    }
}

