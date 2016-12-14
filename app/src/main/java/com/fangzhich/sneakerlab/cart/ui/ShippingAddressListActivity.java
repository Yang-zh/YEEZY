package com.fangzhich.sneakerlab.cart.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fangzhich.sneakerlab.App;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.base.ui.recyclerview.BaseRecyclerViewAdapter;
import com.fangzhich.sneakerlab.cart.data.entity.CheckOutInfoEntity;
import com.fangzhich.sneakerlab.user.data.entity.AddressEntity;
import com.fangzhich.sneakerlab.user.data.net.UserApi;
import com.fangzhich.sneakerlab.util.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.SingleSubscriber;
import timber.log.Timber;

/**
 * ShippingAddressListActivity
 * Created by Khorium on 2016/12/7.
 */
public class ShippingAddressListActivity extends BaseActivity {

    public static final int CHOOSE_SHIPPING_ADDRESS = 1001;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_shipping_address_list)
    RecyclerView rvShippingAddressList;

    @OnClick(R.id.bt_add_address)
    void btAddAddress() {
        startActivityForResult(new Intent(this,EditShippingAddressActivity.class),EditShippingAddressActivity.ADD_ADDRESS);
    }

    private PaymentManager mPaymentManger;
    private BaseRecyclerViewAdapter shippingAddressAdapter;

    @Override
    public int setContentLayout() {
        return R.layout.activity_list_shipping_address;
    }

    @Override
    protected void initContentView() {
        mPaymentManger = ((App) getApplication()).mPaymentManager;

        initToolbar();

        initRecyclerView();
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

    private void initRecyclerView() {
        shippingAddressAdapter = new BaseRecyclerViewAdapter<AddressEntity, ViewHolder>() {
            @Override
            public void loadData() {
                UserApi.getAddressList(new SingleSubscriber<ArrayList<AddressEntity>>() {
                    @Override
                    public void onSuccess(ArrayList<AddressEntity> value) {
                        mData = value;
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable error) {
                        Timber.e(error);
                        ToastUtil.toast(error.getMessage());
                    }
                });
            }

            @Override
            public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
                View itemView = View.inflate(parent.getContext(), R.layout.item_shipping_address_list, null);
                return new ViewHolder(itemView);
            }

            @Override
            protected void onBindHolder(final ViewHolder holder, int position) {
                final AddressEntity address = mData.get(position);
                holder.fullname.setText(address.fullname);
                holder.street.setText(address.address_1);
                holder.cityAndState.setText(address.city + address.suite);
                holder.country.setText(address.country);
                holder.btDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UserApi.deleteAddress(address.address_id, new SingleSubscriber<Object>() {
                            @Override
                            public void onSuccess(Object value) {
                                removeItem(holder.getAdapterPosition());
                            }

                            @Override
                            public void onError(Throwable error) {
                                ToastUtil.toast(error.getMessage());
                                Timber.e(error);
                            }
                        });
                    }
                });
                holder.btEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ShippingAddressListActivity.this, EditShippingAddressActivity.class);
                        intent.putExtra("address", address);
                        startActivityForResult(intent, EditShippingAddressActivity.EDIT_ADDRESS);
                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent data = new Intent();
                        CheckOutInfoEntity.Address address2 = new CheckOutInfoEntity.Address(address);
                        data.putExtra("address", address2);
                        setResult(RESULT_OK,data);
                        onBackPressed();
                    }
                });
            }
        };
        rvShippingAddressList.setLayoutManager(new LinearLayoutManager(this));
        rvShippingAddressList.setAdapter(shippingAddressAdapter);
    }

    @Override
    protected void loadData() {
        shippingAddressAdapter.loadData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case EditShippingAddressActivity.EDIT_ADDRESS:
                shippingAddressAdapter.loadData();
            case EditShippingAddressActivity.ADD_ADDRESS:
                shippingAddressAdapter.loadData();
                break;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.icon_check)
        ImageView check;
        @BindView(R.id.fullname)
        TextView fullname;
        @BindView(R.id.street)
        TextView street;
        @BindView(R.id.cityAndState)
        TextView cityAndState;
        @BindView(R.id.country)
        TextView country;
        @BindView(R.id.bt_delete)
        ImageView btDelete;
        @BindView(R.id.bt_edit)
        ImageView btEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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
