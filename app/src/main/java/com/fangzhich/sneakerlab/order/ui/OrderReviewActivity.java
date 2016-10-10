package com.fangzhich.sneakerlab.order.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * OrderReviewActivity
 * Created by Khorium on 2016/10/10.
 */

public class OrderReviewActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rating_bar)
    RatingBar ratingBar;
    @BindView(R.id.et_evaluation)
    EditText etEvaluation;
    @OnClick(R.id.bt_submit)
    void submit() {
        ToastUtil.toast("Success");
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_order_review;
    }

    @Override
    protected void initContentView() {
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
        title.setText(R.string.SignIn);
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
