package com.fangzhich.sneakerlab.order.ui;

import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.order.data.entity.OrderEntity;
import com.fangzhich.sneakerlab.order.data.net.OrderApi;
import com.fangzhich.sneakerlab.util.ToastUtil;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import butterknife.BindView;
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
    @BindView(R.id.rating_bar)
    SimpleRatingBar ratingBar;
    @BindView(R.id.et_evaluation)
    EditText etEvaluation;
    @OnClick(R.id.bt_submit)
    void submit() {
        OrderApi.addReview(order.order_id, String.valueOf(ratingBar.getRating()), etEvaluation.getText().toString(), new SingleSubscriber<Object>() {
            @Override
            public void onSuccess(Object value) {
                final AlertDialog dialog = new AlertDialog.Builder(OrderReviewActivity.this).create();
                dialog.show();
                Window window = dialog.getWindow();
                if (window==null) {
                    return;
                }
                View view = View.inflate(OrderReviewActivity.this,R.layout.dialog_review,null);
                window.setContentView(view);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        onBackPressed();
                    }
                },2000);
            }

            @Override
            public void onError(Throwable error) {
                ToastUtil.toast(error.getMessage());
            }
        });
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_order_review;
    }

    @Override
    protected void initContentView() {
        order = getIntent().getParcelableExtra("order");
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
        title.setText(R.string.Review);
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
