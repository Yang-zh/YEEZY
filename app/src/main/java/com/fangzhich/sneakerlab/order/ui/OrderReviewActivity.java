package com.fangzhich.sneakerlab.order.ui;

import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.base.ui.recyclerview.BaseRecyclerViewAdapter;
import com.fangzhich.sneakerlab.base.ui.recyclerview.LinearLayoutItemDecoration;
import com.fangzhich.sneakerlab.order.data.entity.OrderEntity;
import com.fangzhich.sneakerlab.order.data.net.OrderApi;
import com.fangzhich.sneakerlab.user.data.net.UserApi;
import com.fangzhich.sneakerlab.util.ToastUtil;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.SingleSubscriber;

/**
 * OrderReviewActivity
 * Created by Khorium on 2016/10/10.
 */

public class OrderReviewActivity extends BaseActivity {

    public static final int IS_REVIEWED = 100;

    OrderEntity order;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_order_review)
    RecyclerView recyclerView;

    @Override
    public int setContentLayout() {
        return R.layout.activity_order_review;
    }

    @Override
    protected void initContentView() {
        order = getIntent().getParcelableExtra("order");
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
        title.setText(R.string.Review);
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new LinearLayoutItemDecoration(this,LinearLayoutItemDecoration.VERTICAL_LIST,R.drawable.item_decoration_linearlayout));
        recyclerView.setAdapter(new BaseRecyclerViewAdapter<OrderEntity.Product,ViewHolder>() {

            @Override
            public void loadData() {
                for (OrderEntity.Product product: order.product) {
                    if (product.review_status.equals("0")) {
                        mData.add(product);
                    }
                }
            }

            @Override
            public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
                View itemView = View.inflate(OrderReviewActivity.this,R.layout.item_review_in_order,null);
                return new ViewHolder(itemView);
            }

            @Override
            protected void onBindHolder(final ViewHolder holder, final int position) {
                final OrderEntity.Product product = mData.get(position);
                View itemView = holder.itemView;
                Glide.with(itemView.getContext())
                        .load(product.image)
                        .placeholder(R.mipmap.product_image_placeholder)
                        .fitCenter()
                        .crossFade()
                        .into(holder.productImage);
                holder.productName.setText(product.name);
                holder.btSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.productReview.getText().length()<5) {
                            ToastUtil.toast("At least 5 Characters!");
                            return;
                        }
                        OrderApi.addReview(product.order_product_id, String.valueOf(holder.ratingBar.getRating()), holder.productReview.getText().toString(), new SingleSubscriber<Object>() {
                            @Override
                            public void onSuccess(Object value) {
                                ToastUtil.toast("Reviewed!");
                                mData.remove(position);
                                notifyItemRemoved(position);
//                                final AlertDialog dialog = new AlertDialog.Builder(OrderReviewActivity.this).create();
//                                dialog.show();
//                                Window window = dialog.getWindow();
//                                if (window==null) {
//                                    return;
//                                }
//                                View view = View.inflate(OrderReviewActivity.this,R.layout.dialog_review,null);
//                                window.setContentView(view);
//                                Handler handler = new Handler();
//                                handler.postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        dialog.dismiss();
//                                    }
//                                },2000);
                            }

                            @Override
                            public void onError(Throwable error) {
                                ToastUtil.toast(error.getMessage());
                            }
                        });
                    }
                });
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.product_image)
        ImageView productImage;
        @BindView(R.id.product_name)
        TextView productName;
        @BindView(R.id.rating_bar)
        SimpleRatingBar ratingBar;
        @BindView(R.id.product_review)
        EditText productReview;
        @BindView(R.id.bt_submit)
        CardView btSubmit;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
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
