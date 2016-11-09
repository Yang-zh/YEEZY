package com.fangzhich.sneakerlab.base.widget;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.View;

import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.cart.data.net.CartApi;
import com.fangzhich.sneakerlab.util.ToastUtil;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;
import rx.SingleSubscriber;

/**
 * PickerView
 * Created by Khorium on 2016/11/9.
 */

public class PickerView {

    private Context mContext;
    private AlertDialog mDialog;
    private NumberPickerView mPickerView;

    public PickerView(Context context) {
        this.mContext = context;
    }

    public void initPickerView(int layoutRes, int minValue, int maxValue, int currentValue, View.OnClickListener onConfirmListener) {

        View numberView = View.inflate(mContext, layoutRes,null);
        mDialog = new AlertDialog.Builder(mContext, R.style.alertDialog).create();
        mDialog.setView(numberView);
        mPickerView = (NumberPickerView) numberView.findViewById(R.id.picker);
        String[] values = new String[99];
        for (int i=1;i<100;i++) {
            values[i-1] = String.valueOf(i);
        }
        mPickerView.setDisplayedValues(values);
        mPickerView.setMinValue(minValue);
        mPickerView.setMaxValue(maxValue);
        mPickerView.setValue(currentValue);
        CardView btConfirm = (CardView) numberView.findViewById(R.id.bt_confirm);
        btConfirm.setOnClickListener(onConfirmListener);
    }

    public void setClickable(boolean clickable) {
        mPickerView.setClickable(clickable);
    }

    public int getValue() {
        return mPickerView.getValue();
    }

    public void show() {
        if (mDialog!=null) {
            mDialog.show();
        }
    }

    public void dismiss() {
        if (mDialog!=null) {
            mDialog.dismiss();
        }
    }
}
