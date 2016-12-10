package com.fangzhich.sneakerlab.cart.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.fangzhich.sneakerlab.App;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.base.ui.recyclerview.BaseRecyclerViewAdapter;
import com.fangzhich.sneakerlab.user.data.entity.CreditCardEntity;
import com.fangzhich.sneakerlab.user.data.net.UserApi;
import com.fangzhich.sneakerlab.util.ToastUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.SingleSubscriber;
import timber.log.Timber;

/**
 * ThirdPartyPaymentMethodActivity
 * Created by Khorium on 2016/12/7.
 */
public class PaymentMethodListActivity extends BaseActivity {

    public static final int CHOOSE_PAYMENT_METHOD = 1002;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_payment_method_list)
    RecyclerView rvPaymentMethodList;

    @BindView(R.id.payment_method_3rd)
    TextView paymentMethod3rd;
    @OnClick(R.id.payment_method_3rd_layout)
    void openPaymentMethod3rdList() {
        Intent intent = new Intent(this,PaymentMethodList3rdActivity.class);
        startActivityForResult(intent,PaymentMethodList3rdActivity.CHOOSE_3rd_PAYMENT_METHOD);
    }

    private PaymentManager mPaymentManger;
    private BaseRecyclerViewAdapter paymentMethodAdapter;

    @Override
    public int setContentLayout() {
        return R.layout.activity_list_payment_method;
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
        paymentMethodAdapter = new BaseRecyclerViewAdapter<CreditCardEntity,ViewHolder>() {
            @Override
            public void loadData() {
                UserApi.getCreditCardList(new SingleSubscriber<ArrayList<CreditCardEntity>>() {
                    @Override
                    public void onSuccess(ArrayList<CreditCardEntity> value) {
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
                View itemView = View.inflate(parent.getContext(),R.layout.item_payment_method,null);
                return new ViewHolder(itemView);
            }

            @Override
            protected void onBindHolder(final ViewHolder holder, int position) {
                final CreditCardEntity card = mData.get(position);
                holder.cardType.setText("CreditCard");
                holder.cardNumber.setText("****"+ card.card_number.substring(card.card_number.length()-3,card.card_number.length()));
                holder.cardDate.setText(card.card_month+"/20"+card.card_year);
                holder.btEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PaymentMethodListActivity.this, EditPaymentMethodActivity.class);
                        intent.putExtra("card", card);
                        startActivityForResult(intent,EditPaymentMethodActivity.CHOOSE_PAYMENT_METHOD);
                    }
                });
                holder.btDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UserApi.deleteCreditCard(card.credit_id, new SingleSubscriber<Object>() {
                            @Override
                            public void onSuccess(Object value) {
                                removeItem(holder.getAdapterPosition());
                            }

                            @Override
                            public void onError(Throwable error) {
                                Timber.e(error);
                                ToastUtil.toast(error.getMessage());
                            }
                        });
                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setResult(RESULT_OK);
                        onBackPressed();
                    }
                });
            }
        };
        rvPaymentMethodList.setLayoutManager(new LinearLayoutManager(this));
        rvPaymentMethodList.setAdapter(paymentMethodAdapter);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_type)
        TextView cardType;
        @BindView(R.id.card_number)
        TextView cardNumber;
        @BindView(R.id.card_date)
        TextView cardDate;
        @BindView(R.id.bt_edit)
        TextView btEdit;
        @BindView(R.id.bt_delete)
        TextView btDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode!=RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case PaymentMethodList3rdActivity.CHOOSE_3rd_PAYMENT_METHOD:
                mPaymentManger.isUsingPaypal = true;
                setResult(RESULT_OK);
                onBackPressed();
                break;
            case EditPaymentMethodActivity.EDIT_PAYMENT_METHOD:
                mPaymentManger.isUsingPaypal = false;
                paymentMethodAdapter.loadData();
                break;
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
