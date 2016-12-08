package com.fangzhich.sneakerlab.cart.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.fangzhich.sneakerlab.App;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.data.event.RxBus;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.base.widget.ProgressBar;
import com.fangzhich.sneakerlab.base.widget.spinner.NiceSpinner;
import com.fangzhich.sneakerlab.cart.data.event.GuideFlowFinishEvent;
import com.fangzhich.sneakerlab.cart.data.net.CartApi;
import com.fangzhich.sneakerlab.order.data.entity.CountryEntity;
import com.fangzhich.sneakerlab.order.data.entity.DistrictEntity;
import com.fangzhich.sneakerlab.user.data.entity.UserInfoEntity;
import com.fangzhich.sneakerlab.user.data.net.UserApi;
import com.fangzhich.sneakerlab.util.Const;
import com.fangzhich.sneakerlab.util.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import timber.log.Timber;

/**
 * ShippingInfoActivity
 * Created by Khorium on 2016/12/7.
 */
public class EditShippingAddressActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
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

    private ArrayList<CountryEntity> countryList;
    private ArrayList<DistrictEntity> districtList;

    private PaymentManager mPaymentManger;

    @Override
    public int setContentLayout() {
        return R.layout.activity_edit_shipping_info;
    }

    @Override
    protected void initContentView() {
        mPaymentManger = ((App) getApplication()).mPaymentManager;

        initToolbar();

        RxBus.getDefault()
                .toObservable(GuideFlowFinishEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<GuideFlowFinishEvent>() {
                    @Override
                    public void call(GuideFlowFinishEvent guideFlowFinishEvent) {
                        onBackPressed();
                    }
                });
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void loadData() {
        spinnerCountry.setClickable(false);
        spinnerState.setClickable(false);

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

        CartApi.getCountries(new SingleSubscriber<ArrayList<CountryEntity>>() {
            @Override
            public void onSuccess(ArrayList<CountryEntity> value) {
                countryList = value;
                spinnerCountry.setClickable(true);
                ArrayList<String> list = new ArrayList<>();
                for (CountryEntity entity : value) {
                    list.add(entity.name);
                }
                spinnerCountry.attachDataSource(list);
//                if (address != null) {
//                    for (int i = 0; i < countryList.size(); i++) {
//                        if (countryList.get(i).country_id.equals(address.country_id)) {
//                            spinnerCountry.setSelectedIndex(i);
//                            loadDistrictForPosition(i);
//                        }
//                    }
//                }
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
                for (DistrictEntity entity : value) {
                    list.add(entity.name);
                }
                spinnerState.setClickable(true);
                spinnerState.attachDataSource(list);

//                if (address != null) {
//                    for (int i = 0; i < districtList.size(); i++) {
//                        if (districtList.get(i).zone_id.equals(address.zone_id)) {
//                            spinnerState.setSelectedIndex(i);
//                        }
//                    }
//                }
                Timber.d("load districts success");
            }

            @Override
            public void onError(Throwable error) {
                Timber.e(error.getMessage());
            }
        });
    }

    @BindView(R.id.bt_save_info)
    CardView btSaveInfo;

    @OnClick(R.id.bt_save_info)
    void saveShippingAddress() {

        String fullname = etAddressBay.getText().toString();
        if (TextUtils.isEmpty(fullname)) {
            ToastUtil.toast("Input fullname please");
            return;
        }

        String address = etAddressStreet.getText().toString();
        if (TextUtils.isEmpty(address)) {
            ToastUtil.toast("Input street address please");
            return;
        }

        String suite = etAddressUnit.getText().toString();

        if (spinnerCountry.getSelectedIndex()<0) {
            ToastUtil.toast("Choose country please");
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
            ToastUtil.toast("Input city please");
            return;
        }

        String code = etAddressCode.getText().toString();
        if (TextUtils.isEmpty(code)) {
            ToastUtil.toast("Input Zip/PostalCode please");
            return;
        }

        String phone = etAddressPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.toast("Input phone please");
            return;
        }
        if (phone.length()<13||phone.length()>16) {
            ToastUtil.toast("Not a valid phone number");
        }

        final ProgressBar progressBar = ProgressBar.getInstance();
        progressBar.init(this, new ProgressBar.Callback() {
            @Override
            public void onProgressBarClick(View v) {

            }
        }).show();

        if (Const.getUserInfo().shipping_address==null) {
            UserApi.addAddress(fullname, phone, address, suite, city, code, country, state, new SingleSubscriber<String>() {
                @Override
                public void onSuccess(String value) {
                    progressBar.cancel();
                    UserInfoEntity.ShippingAddress shippingAddress = new UserInfoEntity.ShippingAddress();
                    shippingAddress.address_id = value;
                    Const.getUserInfo().shipping_address = shippingAddress;
                }

                @Override
                public void onError(Throwable error) {
                    progressBar.cancel();
                    ToastUtil.toast(error.getMessage());
                }
            });
        } else {
            UserApi.editAddress(Const.getUserInfo().shipping_address.address_id,fullname, phone, address, suite, city, code, country, state, new SingleSubscriber<Object>() {
                @Override
                public void onSuccess(Object value) {
                    progressBar.cancel();
                }

                @Override
                public void onError(Throwable error) {
                    progressBar.cancel();
                    ToastUtil.toast(error.getMessage());
                }
            });
        }

        if (mPaymentManger.isFirstPaying) {
            mPaymentManger.startEditPaymentInfoActivity(this);
        } else {
            onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
