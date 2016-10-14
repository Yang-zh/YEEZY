package com.fangzhich.sneakerlab.user.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.blankj.utilcode.utils.SizeUtils;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.base.ui.recyclerview.GridSpaceItemDecoration;
import com.fangzhich.sneakerlab.base.ui.recyclerview.OnScrollLoadMoreHelper;
import com.fangzhich.sneakerlab.user.ui.adapter.WishListAdapter;
import com.fangzhich.sneakerlab.util.Const;

import butterknife.BindView;

/**
 * PersonalCenterActivity
 * Created by Khorium on 2016/9/28.
 */

public class PersonalCenterActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rv_wish_list)
    RecyclerView recyclerView;
    private WishListAdapter adapter;


    @Override
    public int setContentLayout() {
        return R.layout.activity_person_center;
    }

    @Override
    protected void initContentView() {
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
        title.setText(Const.getUserInfo().user_info.firstname+" "+Const.getUserInfo().user_info.lastname);
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new WishListAdapter();
        recyclerView.addItemDecoration(new GridSpaceItemDecoration(SizeUtils.dp2px(this,8),2));
        recyclerView.addOnScrollListener(new OnScrollLoadMoreHelper(adapter));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
