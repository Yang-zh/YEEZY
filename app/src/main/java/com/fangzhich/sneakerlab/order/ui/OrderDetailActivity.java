package com.fangzhich.sneakerlab.order.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.base.ui.recyclerview.BaseRecyclerViewAdapter;
import com.fangzhich.sneakerlab.base.ui.recyclerview.LinearLayoutItemDecoration;
import com.fangzhich.sneakerlab.main.ui.ContactActivity;
import com.fangzhich.sneakerlab.main.ui.SupportActivity;
import com.fangzhich.sneakerlab.order.data.entity.OrderEntity;
import com.fangzhich.sneakerlab.order.data.entity.OrderItemEntity;
import com.fangzhich.sneakerlab.order.data.net.OrderApi;
import com.fangzhich.sneakerlab.order.ui.adapter.OrderHistoryAdapter;
import com.fangzhich.sneakerlab.util.TagFormatUtil;
import com.fangzhich.sneakerlab.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.shippingAddressDetail1)
    TextView shippingAddressDetail1;
    @BindView(R.id.shippingAddressDetail2)
    TextView shippingAddressDetail2;
    @BindView(R.id.shippingAddressDetail3)
    TextView shippingAddressDetail3;
    @BindView(R.id.shippingAddressDetail4)
    TextView shippingAddressDetail4;
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

    @BindView(R.id.bt_cancel)
    TextView btCancel;
    @OnClick(R.id.bt_cancel)
    void cancel() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        Window window = dialog.getWindow();
        if (window==null) {
            return;
        }
        View view = View.inflate(this,R.layout.dialog_cancel,null);
        view.findViewById(R.id.bt_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.bt_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @BindView(R.id.bt_refund)
    TextView btRefund;
    @OnClick(R.id.bt_refund)
    void refund() {
        startActivity(new Intent(this, RefundActivity.class));
    }

    @BindView(R.id.bt_contact)
    CardView btContact;
    @OnClick(R.id.bt_contact)
    void contact() {
        startActivity(new Intent(this, ContactActivity.class));
    }

    @BindView(R.id.bt_confirm_receive)
    CardView btConfirmReceive;
    @OnClick(R.id.bt_confirm_receive)
    void confirm() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        Window window = dialog.getWindow();
        if (window==null) {
            return;
        }
        View view = View.inflate(this,R.layout.dialog_confirm,null);
        view.findViewById(R.id.bt_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.bt_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        window.setContentView(view);

    }

    @BindView(R.id.bt_review)
    CardView btReview;
    @OnClick(R.id.bt_review)
    void review() {
        Intent intent = new Intent(this, OrderReviewActivity.class);
        startActivity(intent);
    }

    @BindView(R.id.order_operation)
    LinearLayout orderOperation;


    private String order_id;
    private OrderEntity order;

    BaseRecyclerViewAdapter<OrderEntity.Product,ProductViewHolder> productAdapter;

    @Override
    public int setContentLayout() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initContentView() {
        order_id = getIntent().getStringExtra("order_id");
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
        title.setText(R.string.OrderDetail);
    }

    private void initRecyclerView() {
        productAdapter = new BaseRecyclerViewAdapter<OrderEntity.Product, ProductViewHolder>() {

            @Override
            public void loadData() {
                mData = order.product;
            }

            @Override
            public ProductViewHolder onCreateHolder(ViewGroup parent, int viewType) {
                return new ProductViewHolder(View.inflate(parent.getContext(), R.layout.item_order_list_product_list,null)) {};
            }

            @Override
            protected void onBindHolder(ProductViewHolder holder, int position) {
                View itemView = holder.itemView;

                OrderEntity.Product product = mData.get(position);
                Glide.with(itemView.getContext())
                        .load(product.image)
                        .fitCenter()
                        .crossFade()
                        .into(holder.ivProductImage);
                holder.productName.setText(product.name);
                holder.quantity.setText(product.quantity);
                holder.price.setText(product.price);
                for (OrderEntity.Product.Option option:product.option) {
                    switch (option.name) {
                        case "Size":
                            holder.size.setText(option.value);
                            break;
                        case "size":
                            holder.size.setText(option.value);
                            break;
                        case "Color":
                            holder.color.setText(option.value);
                            break;
                        case "color":
                            holder.color.setText(option.value);
                            break;
                        default:
                            break;
                    }
                }
            }
        };
        rvOrderDetailList.setLayoutManager(new LinearLayoutManager(this));
        rvOrderDetailList.addItemDecoration(new LinearLayoutItemDecoration(this,LinearLayoutItemDecoration.VERTICAL_LIST));
        rvOrderDetailList.setAdapter(productAdapter);
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

        productAdapter.loadData();
        productAdapter.notifyDataSetChanged();
        switch (order.order_status) {
            case "Processing":
                orderOperation.setVisibility(View.VISIBLE);
                btCancel.setVisibility(View.VISIBLE);
                break;
            case "Shipping":
                orderOperation.setVisibility(View.VISIBLE);
                btConfirmReceive.setVisibility(View.VISIBLE);
                break;
            case "Completed":
                orderOperation.setVisibility(View.VISIBLE);
                btReview.setVisibility(View.VISIBLE);
                btRefund.setVisibility(View.VISIBLE);
                break;
            case "Canceled":
                break;
        }

        orderPlaced.setText(TagFormatUtil.from(getResources().getString(R.string.OrderPlaced))
                        .with("content",order.date_added)
                        .format());
        orderId.setText(TagFormatUtil.from(getResources().getString(R.string.OrderId))
                        .with("content",order.order_id)
                        .format());
        shippingAddressDetail1.setText(order.shipping.suite+", "+order.shipping.address_1);
        shippingAddressDetail2.setText(order.shipping.city);
        shippingAddressDetail3.setText(order.shipping.zone_code+", "+order.shipping.postcode);
        shippingAddressDetail4.setText(order.shipping.country);
        paymentMethodDetail1.setText(getResources().getString(R.string.CreditCard));
        paymentMethodDetail2.setText(TagFormatUtil.from(getResources().getString(R.string.numberFormat))
                        .with("number",order.payment.card_number)
                        .format());
        paymentMethodDetail3.setText(TagFormatUtil.from(getResources().getString(R.string.codeFormat))
                        .with("code",order.payment.zip_code)
                        .format());
        itemTotalDetail.setText(TagFormatUtil.from(getResources().getString(R.string.priceFormat))
                .with("price",order.price)
                .format());
        shippingDetail.setText(TagFormatUtil.from(getResources().getString(R.string.priceFormat))
                .with("price",order.service_charge)
                .format());
        taxDetail.setText("");
        orderTotalDetail.setText(TagFormatUtil.from(getResources().getString(R.string.priceFormat))
                .with("price",order.total)
                .format());
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_productImage)
        ImageView ivProductImage;
        @BindView(R.id.productName)
        TextView productName;
        @BindView(R.id.quantity)
        TextView quantity;
        @BindView(R.id.color)
        TextView color;
        @BindView(R.id.size)
        TextView size;
        @BindView(R.id.price)
        TextView price;

        ProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
