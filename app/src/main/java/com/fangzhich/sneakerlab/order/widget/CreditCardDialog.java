package com.fangzhich.sneakerlab.order.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.widget.DialogManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ShoppingCartActivity
 * Created by Khorium on 2016/9/1.
 */
public class CreditCardDialog {

    @OnClick(R.id.bt_cancel)
    void cancel() {
        mPopupWindow.dismiss();
    }

    @OnClick(R.id.bt_back)
    void back() {
        mPopupWindow.setOnDismissListener(null);
        mPopupWindow.dismiss();
        manager.reShowShoppingCartDialog();
    }


    @OnClick(R.id.bt_save_info)
    void savePaymentInfo() {
        final View progressBar =  View.inflate(mPopupContent.getContext(),R.layout.progress_bar,null);
        final WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        windowManager.addView(progressBar, params);
        progressBar.findViewById(R.id.progress_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                windowManager.removeViewImmediate(progressBar);
            }
        });
//        mPopupWindow.setOnDismissListener(null);
//        mPopupWindow.dismiss();
//        manager.reShowShoppingCartDialog();
    }

    @BindView(R.id.et_credit_card_number)
    EditText etCreditCardNumber;
    @BindView(R.id.et_security_code)
    EditText etSecurityCode;
    @BindView(R.id.et_expiry_date_number)
    EditText etExpiryDateNumber;
    @BindView(R.id.et_billing_postal_code)
    EditText etBillingPostalCode;

    private PopupWindow mPopupWindow;

    private Context mContext;
    private View mContentView;
    View mPopupContent;

    private DialogManager manager;

    public CreditCardDialog initPopup(DialogManager dialogManager, Context context) {
        manager = dialogManager;
        mContext = context;
        mPopupContent = View.inflate(context, R.layout.dialog_payment_info, null);
        ButterKnife.bind(this, mPopupContent);

        mPopupWindow = new PopupWindow(mPopupContent, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        mPopupWindow.setAnimationStyle(R.style.Dialog);

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

    public void hide() {
        if (mPopupWindow!=null) {
            mPopupWindow.getContentView().setVisibility(View.GONE);
        }
    }

    public void show() {
        if (mPopupWindow!=null) {
            mPopupWindow.getContentView().setVisibility(View.VISIBLE);
        }
    }
}
