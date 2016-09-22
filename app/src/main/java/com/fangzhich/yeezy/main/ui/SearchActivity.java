package com.fangzhich.yeezy.main.ui;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.base.ui.BaseActivity;

import butterknife.BindView;

/**
 * Search
 * Created by Khorium on 2016/9/1.
 */
public class SearchActivity extends BaseActivity {

    @BindView(R.id.editText_search)
    EditText searchInput;
    @BindView(R.id.bt_cancel)
    TextView CancelButton;

    @Override
    public int setContentLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected void initContentView() {
        CancelButton.setOnClickListener(v -> onBackPressed());
    }

    @Override
    protected void loadData() {

    }
}
