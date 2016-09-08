package com.fangzhich.yeezy.ui.activity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fangzhich.yeezy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ShoppingCartActivity
 * Created by Khorium on 2016/9/1.
 */
public class ShoppingCartDialog {

    PopupWindow mPopupWindow;

    @BindView(R.id.bt_cancel)
    LinearLayout btCancel;
    @BindView(R.id.dialog_content_title)
    TextView dialogContentTitle;
    @BindView(R.id.aboveLine)
    View aboveLine;
    @BindView(R.id.credit_card_text)
    TextView creditCardText;
    @BindView(R.id.et_credit_card_number)
    EditText etCreditCardNumber;
    @BindView(R.id.security_code)
    TextView securityCode;
    @BindView(R.id.et_security_code)
    EditText etSecurityCode;
    @BindView(R.id.security_code_detail)
    TextView securityCodeDetail;
    @BindView(R.id.expiry_date)
    TextView expiryDate;
    @BindView(R.id.et_expiry_date_number)
    EditText etExpiryDateNumber;
    @BindView(R.id.billing_postal_code)
    TextView billingPostalCode;
    @BindView(R.id.et_billing_postal_code)
    EditText etBillingPostalCode;
    @BindView(R.id.billing_postal_code_detail)
    TextView billingPostalCodeDetail;
    @BindView(R.id.bt_save_info)
    CardView btSaveInfo;
    @BindView(R.id.secure_payment)
    ImageView securePayment;
    @BindView(R.id.secure_payment_detail)
    TextView securePaymentDetail;
    @BindView(R.id.secure_payment_info)
    TextView securePaymentInfo;
    @BindView(R.id.info_content)
    RelativeLayout infoContent;
    @BindView(R.id.dialog_content)
    RelativeLayout dialogContent;

    public ShoppingCartDialog initPopup(Context context) {
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
