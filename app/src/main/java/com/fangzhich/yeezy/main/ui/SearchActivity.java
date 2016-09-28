package com.fangzhich.yeezy.main.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.base.ui.BaseActivity;
import com.fangzhich.yeezy.base.ui.recyclerview.BaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Search
 * Created by Khorium on 2016/9/1.
 */
public class SearchActivity extends BaseActivity {

    @BindView(R.id.editText_search)
    EditText searchInput;
    @OnClick(R.id.bt_cancel)
    void cancel() {
        onBackPressed();
    }
    @BindView(R.id.popular_search)
    TextView popularSearch;

    @BindView(R.id.rv_popularSearches)
    RecyclerView recyclerView;

    BaseRecyclerViewAdapter<String,ViewHolder> baseRecyclerViewAdapter;

    @Override
    public int setContentLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected void initContentView() {

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        final List<String> data = Arrays.asList("Adidas","Yeezy","Nike");

        baseRecyclerViewAdapter = new BaseRecyclerViewAdapter<String,ViewHolder>() {

            @Override
            public void loadData() {
                mData = data;
            }

            @Override
            public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
                View itemView = View.inflate(parent.getContext(),R.layout.item_search,null);
                return new ViewHolder(itemView){};
            }

            @Override
            protected void onBindHolder(ViewHolder holder, int position) {
                View itemView = holder.itemView;
                ((TextView) itemView.findViewById(R.id.search_text)).setText(mData.get(position));
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // todo search text of position
                    }
                });
            }
        };

        recyclerView.setAdapter(baseRecyclerViewAdapter);

    }

    @Override
    protected void loadData() {
        baseRecyclerViewAdapter.loadData();
    }
}
