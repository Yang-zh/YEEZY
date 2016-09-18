package com.fangzhich.yeezy.order.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.fangzhich.yeezy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ShoppingCartActivity
 * Created by Khorium on 2016/9/1.
 */
public class CreditCardDialog {

    PopupWindow mPopupWindow;

    @BindView(R.id.bt_cancel)
    LinearLayout btCancel;

    public CreditCardDialog initPopup(Context context) {
        View mPopupContent = View.inflate(context, R.layout.dialog_payment_info, null);
        ButterKnife.bind(this,mPopupContent);

        mPopupWindow = new PopupWindow(mPopupContent, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        mPopupWindow.setAnimationStyle(R.style.Dialog);

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        return this;
    }

    public void showPopup(View contentView) {
        mPopupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
    }
}
