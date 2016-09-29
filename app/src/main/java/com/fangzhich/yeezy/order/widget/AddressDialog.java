package com.fangzhich.yeezy.order.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.base.widget.DialogManager;
import com.fangzhich.yeezy.base.widget.spinner.NiceSpinner;
import com.fangzhich.yeezy.order.ui.OrderConfirmedActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ShoppingCartActivity
 * Created by Khorium on 2016/9/1.
 */
public class AddressDialog {

    @BindView(R.id.et_address_bay)
    EditText etAddressBay;
    @BindView(R.id.et_address_street)
    EditText etAddressStreet;
    @BindView(R.id.et_address_unit)
    EditText etAddressUnit;
    @BindView(R.id.spinner_country)
    NiceSpinner spinnerCountry;
    @BindView(R.id.et_address_state)
    EditText etAddressState;
    @BindView(R.id.et_address_city)
    EditText etAddressCity;
    @BindView(R.id.et_address_code)
    EditText etAddressCode;
    @BindView(R.id.et_address_phone)
    EditText etAddressPhone;
    @OnClick(R.id.bt_place_order)
    void placeOrder() {
        Intent intent = new Intent(mContext, OrderConfirmedActivity.class);
        mContext.startActivity(intent);
    }

    private PopupWindow mPopupWindow;

    @BindView(R.id.bt_cancel)
    LinearLayout btCancel;
    private Context mContext;
    private View mContentView;

    public AddressDialog initPopup(DialogManager dialogManager, Context context) {
        mContext = context;
        View mPopupContent = View.inflate(context, R.layout.dialog_address, null);
        ButterKnife.bind(this, mPopupContent);

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
        mContentView = contentView;
        mPopupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
    }
    public boolean isShowing() {
        if (mPopupWindow==null) {
            return false;
        }
        return mPopupWindow.isShowing();
    }

    public void dismiss() {
        mPopupWindow.dismiss();
    }
}
