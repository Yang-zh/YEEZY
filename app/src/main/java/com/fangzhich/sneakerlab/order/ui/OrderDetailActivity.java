package com.fangzhich.sneakerlab.order.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.order.data.entity.OrderEntity;
import com.fangzhich.sneakerlab.order.data.net.OrderApi;
import com.fangzhich.sneakerlab.util.TagFormatUtil;
import com.fangzhich.sneakerlab.util.ToastUtil;

import butterknife.BindView;
import rx.SingleSubscriber;
import timber.log.Timber;

/**
 * OrderDetailActivity
 * Created by Khorium on 2016/9/30.
 */
public class OrderDetailActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.orderPlaced)
    TextView orderPlaced;
    @BindView(R.id.orderId)
    TextView orderId;
    @BindView(R.id.shippingAddressDetail)
    TextView shippingAddressDetail;
    @BindView(R.id.paymentMethodDetail1)
    TextView paymentMethodDetail1;
    @BindView(R.id.paymentMethodDetail2)
    TextView paymentMethodDetail2;
    @BindView(R.id.paymentMethodDetail3)
    TextView paymentMethodDetail3;
    @BindView(R.id.rv_order_detail_list)
    RecyclerView rvOrderDetailList;
    @BindView(R.id.itemTotalDetail)
    TextView itemTotalDetail;
    @BindView(R.id.shippingDetail)
    TextView shippingDetail;
    @BindView(R.id.taxDetail)
    TextView taxDetail;
    @BindView(R.id.orderTotalDetail)
    TextView orderTotalDetail;
    @BindView(R.id.bt_contact)
    CardView btContact;
    @BindView(R.id.bt_confirm_receive)
    CardView btConfirmReceive;
    @BindView(R.id.bt_review)
    CardView btReview;
    @BindView(R.id.order_operation)
    RelativeLayout orderOperation;


    private String order_id;
    private OrderEntity order;

    @Override
    public int setContentLayout() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initContentView() {
        order_id = getIntent().getStringExtra("order_id");
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        title.setText(R.string.OrderDetail);
    }

    @Override
    protected void loadData() {
        if (!TextUtils.isEmpty(order_id)) {
            OrderApi.getOrder(order_id, new SingleSubscriber<OrderEntity>() {
                @Override
                public void onSuccess(OrderEntity value) {
                    order = value;
                    refresh();
                }

                @Override
                public void onError(Throwable error) {
                    Timber.e(error.getMessage());
                    ToastUtil.toast(error.getMessage());
                }
            });
        }
    }

    private void refresh() {
        orderPlaced.setText(TagFormatUtil.from(getResources().getString(R.string.OrderPlaced))
                        .with("content",order.date_added)
                        .format());
        orderId.setText(TagFormatUtil.from(getResources().getString(R.string.OrderId))
                        .with("content",order.order_id)
                        .format());
        shippingAddressDetail.setText(order.shipping.address_format);
        paymentMethodDetail1.setText(getResources().getString(R.string.CreditCard));
        itemTotalDetail.setText(TagFormatUtil.from(getResources().getString(R.string.OrderPlaced))
                .with("price",order.total)
                .format());
        shippingDetail.setText(TagFormatUtil.from(getResources().getString(R.string.OrderPlaced))
                .with("content",order.date_added)
                .format());
    }
}
