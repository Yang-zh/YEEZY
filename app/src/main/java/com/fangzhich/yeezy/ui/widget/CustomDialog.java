package com.fangzhich.yeezy.ui.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.fangzhich.yeezy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * CustomDialog
 * Created by Khorium on 2016/9/1.
 */
public class CustomDialog {

    PopupWindow mPopupWindow;
    View mPopupContent;

    public CustomDialog initPopup(Context context, int layout, final Listener listener) {
        mPopupContent = View.inflate(context, layout, null);
        mPopupWindow = new PopupWindow(mPopupContent, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);

        listener.onInit(mPopupWindow, mPopupContent);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                listener.onDismiss(mPopupWindow, mPopupContent);
            }
        });

        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        mPopupWindow.setAnimationStyle(R.style.Dialog);
        return this;
    }

    public void showPopup(View contentView) {
        mPopupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
    }

    public interface Listener{
        void onInit(final PopupWindow dialog, final View content);
        void onDismiss(final PopupWindow dialog, final View content);
    }
}
