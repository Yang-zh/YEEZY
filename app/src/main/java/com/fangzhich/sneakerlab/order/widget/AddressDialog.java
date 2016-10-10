package com.fangzhich.sneakerlab.order.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.widget.DialogManager;
import com.fangzhich.sneakerlab.base.widget.spinner.NiceSpinner;
import com.fangzhich.sneakerlab.cart.data.net.CartApi;
import com.fangzhich.sneakerlab.order.data.entity.CountryEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.SingleSubscriber;
import timber.log.Timber;

/**
 * ShoppingCartActivity
 * Created by Khorium on 2016/9/1.
 */
public class AddressDialog {

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
    void saveInfo() {
        mPopupWindow.setOnDismissListener(null);
        mPopupWindow.dismiss();
        manager.reShowShoppingCartDialog();
    }

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

    private PopupWindow mPopupWindow;

    private Context mContext;
    private View mContentView;

    private DialogManager manager;

    public AddressDialog initPopup(DialogManager dialogManager, Context context) {
        manager = dialogManager;
        mContext = context;
        View mPopupContent = View.inflate(context, R.layout.dialog_address, null);
        ButterKnife.bind(this, mPopupContent);

        mPopupWindow = new PopupWindow(mPopupContent, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        mPopupWindow.setAnimationStyle(R.style.Dialog);

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                manager.closeAll();
            }
        });

        spinnerCountry.setClickable(false);

        loadData();

        return this;
    }

    private void loadData() {
        CartApi.getCountries(new SingleSubscriber<ArrayList<CountryEntity>>() {
            @Override
            public void onSuccess(ArrayList<CountryEntity> value) {
                spinnerCountry.setClickable(false);
                spinnerCountry.attachDataSource(value);
            }

            @Override
            public void onError(Throwable error) {
                Timber.e(error.getMessage());
            }
        });
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
