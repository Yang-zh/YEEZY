package com.fangzhich.sneakerlab.order.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.cart.ui.DialogManager;
import com.fangzhich.sneakerlab.base.widget.ProgressBar;
import com.fangzhich.sneakerlab.cart.data.entity.CartEntity;
import com.fangzhich.sneakerlab.cart.ui.ShoppingCartDialog;
import com.fangzhich.sneakerlab.user.data.entity.UserInfoEntity;
import com.fangzhich.sneakerlab.user.data.net.UserApi;
import com.fangzhich.sneakerlab.util.Const;
import com.fangzhich.sneakerlab.util.ToastUtil;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.SingleSubscriber;

/**
 * ShoppingCartActivity
 * Created by Khorium on 2016/9/1.
 */
public class CreditCardDialog {

    private CartEntity.Payment card;

    @OnClick(R.id.bt_cancel)
    void cancel() {
        mPopupWindow.setOnDismissListener(null);
        mPopupWindow.dismiss();
        manager.closeAll();
    }
//    @OnClick(R.id.bt_back)
//    void back() {
//        mPopupWindow.setOnDismissListener(null);
//        mPopupWindow.dismiss();
//        manager.reShowShoppingCartDialog();
//    }


    @OnClick(R.id.bt_save_info)
    void savePaymentInfo() {

        final String cardNumber = etCreditCardNumber.getText().toString();
        if (TextUtils.isEmpty(cardNumber)) {
            ToastUtil.toast("Card number should not be null");
            return;
        }

        final String securityCode = etSecurityCode.getText().toString();
        if (TextUtils.isEmpty(securityCode)) {
            ToastUtil.toast("Security code should not be null");
            return;
        }


        String expiryDate = etExpiryDateNumber.getText().toString();
        if (TextUtils.isEmpty(expiryDate)) {
            ToastUtil.toast("Expiry date should not be null");
            return;
        }

        Pattern pattern = Pattern.compile("[0-9]{2}/[0-9]{2}");
        if (!pattern.matcher(expiryDate).matches()) {
            ToastUtil.toast("please enter expiry date like 00/00");
            return;
        }

        String[] date = expiryDate.split("/");
        final String month = date[0];
        final String year = date[1];

        String zipPostalCode = etBillingPostalCode.getText().toString();
        if (TextUtils.isEmpty(zipPostalCode)) {
            ToastUtil.toast("Zip/PostalCode should be null");
        }

        final ProgressBar progressBar = ProgressBar.getInstance();
        progressBar.init(mContext, new ProgressBar.Callback() {
            @Override
            public void onProgressBarClick(View v) {

            }
        }).show();

        if (this.card==null) {
            UserApi.addCreditCard(cardNumber, month, year, securityCode, zipPostalCode, new SingleSubscriber<String>() {
                @Override
                public void onSuccess(String value) {
                    progressBar.cancel();
                    ToastUtil.toast("save credit card info success");
                    mPopupWindow.dismiss();
                    manager.saveCreditCard("CreditCard",cardNumber,year,month,securityCode);
                }

                @Override
                public void onError(Throwable error) {
                    progressBar.cancel();
                    ToastUtil.toast(error.getMessage());
                }
            });
        } else {
            UserApi.editCreditCard(cardNumber, month, year, securityCode, zipPostalCode, new SingleSubscriber<Object>() {
                @Override
                public void onSuccess(Object value) {
                    progressBar.cancel();
                    ToastUtil.toast("save credit card info success");
                    mPopupWindow.dismiss();
                    manager.saveCreditCard("CreditCard",cardNumber,year,month,securityCode);
                }

                @Override
                public void onError(Throwable error) {
                    progressBar.cancel();
                    ToastUtil.toast(error.getMessage());
                }
            });
        }
        mPopupWindow.dismiss();
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
    private View mPopupContent;

    private DialogManager manager;

    public CreditCardDialog initPopup(DialogManager dialogManager, Context context) {
        manager = dialogManager;
        mContext = context;
        mPopupContent = View.inflate(context, R.layout.dialog_payment_info, null);
        ButterKnife.bind(this, mPopupContent);

        mPopupWindow = new PopupWindow(mPopupContent, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        mPopupWindow.setAnimationStyle(R.style.Dialog);

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                manager.reShowShoppingCartDialog();
            }
        });

        return this;
    }

    public void showPopup(View contentView) {
        mContentView = contentView;
        mPopupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
    }

    public boolean isShowing() {
        if (mPopupWindow == null) {
            return false;
        }
        return mPopupWindow.isShowing();
    }

    public void dismiss() {
        mPopupWindow.dismiss();
    }

    public void hide() {
        if (mPopupWindow != null) {
            mPopupWindow.getContentView().setVisibility(View.GONE);
        }
    }

    public void show() {
        if (mPopupWindow != null) {
            mPopupWindow.getContentView().setVisibility(View.VISIBLE);
        }
    }

    public CreditCardDialog withCreditCard(CartEntity.Payment card) {
        this.card = card;
        if (card!=null) {
            etCreditCardNumber.setText(card.card_number);
            etSecurityCode.setText(card.card_cvv);
            etExpiryDateNumber.setText(card.card_month+"/"+card.card_year);
            etBillingPostalCode.setText(card.zip_code);
        }
        return this;
    }
}
