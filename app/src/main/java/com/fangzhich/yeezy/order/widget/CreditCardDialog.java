package com.fangzhich.yeezy.order.widget;

import android.content.Context;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ShoppingCartActivity
 * Created by Khorium on 2016/9/1.
 */
public class CreditCardDialog {

    @BindView(R.id.et_credit_card_number)
    EditText etCreditCardNumber;
    @BindView(R.id.et_security_code)
    EditText etSecurityCode;
    @BindView(R.id.et_expiry_date_number)
    EditText etExpiryDateNumber;
    @BindView(R.id.et_billing_postal_code)
    EditText etBillingPostalCode;
    @OnClick(R.id.bt_save_info)
    void savePaymentInfo() {
        mPopupWindow.dismiss();
    }
    private PopupWindow mPopupWindow;

    @BindView(R.id.bt_cancel)
    LinearLayout btCancel;
    private Context mContext;
    private View mContentView;

    public CreditCardDialog initPopup(DialogManager dialogManager, Context context) {
        mContext = context;
        View mPopupContent = View.inflate(context, R.layout.dialog_payment_info, null);
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
