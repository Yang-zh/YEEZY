package com.fangzhich.sneakerlab.user.ui;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.ConvertUtils;
import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseActivity;
import com.fangzhich.sneakerlab.base.ui.recyclerview.BaseRecyclerViewAdapter;
import com.fangzhich.sneakerlab.base.ui.recyclerview.GridSpaceItemDecoration;
import com.fangzhich.sneakerlab.main.ui.MainActivity;
import com.fangzhich.sneakerlab.user.data.entity.SupportItemEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * WelcomeActivity
 * Created by Khorium on 2016/9/12.
 */
public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rv_suggest)
    RecyclerView rvSuggest;
    BaseRecyclerViewAdapter adapter;
    @OnClick(R.id.bt_next)
    void next() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initContentView() {
        initToolbar();
        initRecyclerView();
    }

    private void initRecyclerView() {
        rvSuggest.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new BaseRecyclerViewAdapter<SupportItemEntity,RecyclerView.ViewHolder>() {

            @Override
            public void loadData() {
                ArrayList<SupportItemEntity> images = new ArrayList<>();
                for (int i=0; i<20;i++) {
                    SupportItemEntity entity = new SupportItemEntity();
                    entity.imageRes = R.mipmap.item_welcome_placeholder;
                    entity.isCheck = false;
                    images.add(entity);
                }
                mData = images;
            }

            @Override
            public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
                View itemView = View.inflate(parent.getContext(),R.layout.item_suggest_list,null);
                return new RecyclerView.ViewHolder(itemView) {};
            }

            @Override
            protected void onBindHolder(final RecyclerView.ViewHolder holder, final int position) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View view = holder.itemView.findViewById(R.id.check);
                        if (mData.get(position).isCheck) {
                            v.setBackgroundDrawable(getResources().getDrawable(R.mipmap.item_welcome_bg));
                            view.setVisibility(View.INVISIBLE);
                            mData.get(position).isCheck = false;
                        } else {
                            v.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.welcome_item_check_background)));
                            view.setVisibility(View.VISIBLE);
                            mData.get(position).isCheck = true;
                        }
                    }
                });
                ImageView view = (ImageView) holder.itemView.findViewById(R.id.image);
                view.setImageResource(mData.get(position).imageRes);
                setIsCheck(holder.itemView,holder.getAdapterPosition());
            }

            void setIsCheck(View view, int position) {
                if (mData.get(position).isCheck) {
                    view.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.welcome_item_check_background)));
                    view.findViewById(R.id.check).setVisibility(View.VISIBLE);
                    mData.get(position).isCheck = true;
                } else {
                    view.setBackgroundDrawable(getResources().getDrawable(R.mipmap.item_welcome_bg));
                    view.findViewById(R.id.check).setVisibility(View.INVISIBLE);
                    mData.get(position).isCheck = false;
                }
            }
        };
        rvSuggest.setAdapter(adapter);
        rvSuggest.addItemDecoration(new GridSpaceItemDecoration(ConvertUtils.dp2px(this,15),2));
    }

    private void initToolbar() {
        boolean isFirstRegister = getIntent().getBooleanExtra("isFirstRegister",false);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            if (!isFirstRegister) {
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
        title.setText(R.string.Welcome);
    }

    @Override
    protected void loadData() {
        adapter.loadData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        switch (item.getItemId()) {
            case R.id.skip:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getIntent().getBooleanExtra("isFirstRegister",false)) {
            getMenuInflater().inflate(R.menu.menu_skip, menu);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (getIntent().getBooleanExtra("isFirstRegister",false)) {
            return;
        }
        super.onBackPressed();
    }
}
