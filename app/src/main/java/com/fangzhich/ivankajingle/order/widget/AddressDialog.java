package com.fangzhich.ivankajingle.order.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.fangzhich.ivankajingle.R;
import com.fangzhich.ivankajingle.cart.ui.DialogManager;
import com.fangzhich.ivankajingle.base.widget.ProgressBar;
import com.fangzhich.ivankajingle.base.widget.spinner.NiceSpinner;
import com.fangzhich.ivankajingle.cart.data.entity.CartEntity;
import com.fangzhich.ivankajingle.cart.data.net.CartApi;
import com.fangzhich.ivankajingle.order.data.entity.CountryEntity;
import com.fangzhich.ivankajingle.order.data.entity.DistrictEntity;
import com.fangzhich.ivankajingle.user.data.entity.UserInfoEntity;
import com.fangzhich.ivankajingle.user.data.net.UserApi;
import com.fangzhich.ivankajingle.util.Const;
import com.fangzhich.ivankajingle.util.ToastUtil;

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

    private ArrayList<CountryEntity> countryList;
    private ArrayList<DistrictEntity> districtList;
    private CartEntity.Address address;

    @OnClick(R.id.bt_cancel)
    void cancel() {
        mPopupWindow.setOnDismissListener(null);
        mPopupWindow.dismiss();
        manager.closeAll();
    }

//    @OnClick(R.id.bt_back)
//    void back() {
//        mPopupWindow.dismiss();
//    }

    @OnClick(R.id.bt_save_info)
    void saveInfo() {
        String fullname = etAddressBay.getText().toString();
        if (TextUtils.isEmpty(fullname)) {
            ToastUtil.toast("fullname shouldn't be null");
            return;
        }

        String address = etAddressStreet.getText().toString();
        if (TextUtils.isEmpty(address)) {
            ToastUtil.toast("address shouldn't be null");
            return;
        }

        String suite = etAddressUnit.getText().toString();
        if (TextUtils.isEmpty(suite)) {
            ToastUtil.toast("suite shouldn't be null");
            return;
        }

        if (spinnerCountry.getSelectedIndex()<0) {
            ToastUtil.toast("please choose country");
            return;
        }

        String country = null;
        if (countryList!=null && countryList.size()!=0) {
            country = countryList.get(spinnerCountry.getSelectedIndex()).country_id;
        }

        String state = null;
        if (districtList!=null && districtList.size()!=0) {
            state = districtList.get(spinnerState.getSelectedIndex()).zone_id;
        }


        String city = etAddressCity.getText().toString();
        if (TextUtils.isEmpty(city)) {
            ToastUtil.toast("city shouldn't be null");
            return;
        }

        String code = etAddressCode.getText().toString();
        if (TextUtils.isEmpty(code)) {
            ToastUtil.toast("code shouldn't be null");
            return;
        }

        String phone = etAddressPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.toast("phone shouldn't be null");
            return;
        }

        final String fullAddress = city+" "+address;

        final ProgressBar progressBar = ProgressBar.getInstance();
        progressBar.init(mContext, new ProgressBar.Callback() {
            @Override
            public void onProgressBarClick(View v) {

            }
        }).show();

        if (this.address==null) {
            UserApi.addAddress(fullname, phone, address, suite, city, code, country, state, new SingleSubscriber<String>() {
                @Override
                public void onSuccess(String value) {
                    progressBar.cancel();
                    ToastUtil.toast("save address info success");
                    UserInfoEntity.ShippingAddress shippingAddress = new UserInfoEntity.ShippingAddress();
                    shippingAddress.address_id = value;
                    Const.getUserInfo().shipping_address = shippingAddress;
                    mPopupWindow.dismiss();
                    manager.saveAddress(value,fullAddress);
                }

                @Override
                public void onError(Throwable error) {
                    progressBar.cancel();
                    ToastUtil.toast(error.getMessage());
                }
            });
        } else {
            UserApi.editAddress(this.address.address_id,fullname, phone, address, suite, city, code, country, state, new SingleSubscriber<Object>() {
                @Override
                public void onSuccess(Object value) {
                    progressBar.cancel();
                    ToastUtil.toast("save address info success");
                    mPopupWindow.dismiss();
                    manager.saveAddress(AddressDialog.this.address.address_id,fullAddress);
                }

                @Override
                public void onError(Throwable error) {
                    progressBar.cancel();
                    ToastUtil.toast(error.getMessage());
                }
            });
        }
    }

    @BindView(R.id.et_address_bay)
    EditText etAddressBay;
    @BindView(R.id.et_address_street)
    EditText etAddressStreet;
    @BindView(R.id.et_address_unit)
    EditText etAddressUnit;
    @BindView(R.id.spinner_country)
    NiceSpinner spinnerCountry;
    @BindView(R.id.spinner_state)
    NiceSpinner spinnerState;
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
                manager.reShowShoppingCartDialog();
            }
        });

        spinnerCountry.setClickable(false);
        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadDistrictForPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerState.setClickable(false);
            }
        });
        spinnerState.setClickable(false);

        loadData();

        return this;
    }

    private void loadData() {
        CartApi.getCountries(new SingleSubscriber<ArrayList<CountryEntity>>() {
            @Override
            public void onSuccess(ArrayList<CountryEntity> value) {
                countryList = value;
                spinnerCountry.setClickable(true);
                ArrayList<String> list = new ArrayList<>();
                for (CountryEntity entity:value) {
                    list.add(entity.name);
                }
                spinnerCountry.attachDataSource(list);
                if (address!=null) {
                    for (int i=0;i<countryList.size();i++) {
                        if (countryList.get(i).country_id.equals(address.country_id)) {
                            spinnerCountry.setSelectedIndex(i);
                        }
                    }
                }
                loadDistrictForPosition(spinnerCountry.getSelectedIndex());
                Timber.d("load countries success");
            }

            @Override
            public void onError(Throwable error) {
                Timber.e(error.getMessage());
            }
        });
    }

    private void loadDistrictForPosition(int position) {
        Timber.d(countryList.get(position).country_id);
        CartApi.getDistricts(countryList.get(position).country_id, new SingleSubscriber<ArrayList<DistrictEntity>>() {
            @Override
            public void onSuccess(ArrayList<DistrictEntity> value) {
                districtList = value;
                ArrayList<String> list = new ArrayList<>();
                for (DistrictEntity entity:value) {
                    list.add(entity.name);
                }
                spinnerState.setClickable(true);
                spinnerState.attachDataSource(list);

                if (address!=null) {
                    for (int i=0;i<districtList.size();i++) {
                        if (districtList.get(i).zone_id.equals(address.zone_id)) {
                            spinnerState.setSelectedIndex(i);
                        }
                    }
                }
                Timber.d("load districts success");
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
        return mPopupWindow != null && mPopupWindow.isShowing();
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

    public AddressDialog withAddress(CartEntity.Address address) {
        this.address = address;
        if (address!=null) {
            etAddressBay.setText(address.fullname);
            etAddressStreet.setText(address.address_1);
            etAddressUnit.setText(address.suite);
            etAddressCity.setText(address.city);
            etAddressCode.setText(address.postcode);
            etAddressPhone.setText(address.phone);
        }
        return this;
    }

}
