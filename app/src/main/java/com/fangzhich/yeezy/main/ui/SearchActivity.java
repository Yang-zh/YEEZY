package com.fangzhich.yeezy.main.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.base.ui.BaseActivity;
import com.fangzhich.yeezy.base.ui.recyclerview.BaseRecyclerViewAdapter;

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
    RecyclerView rvPopularSearch;
    @BindView(R.id.rv_searchResult)
    RecyclerView rvSearchResult;

    BaseRecyclerViewAdapter<String,ViewHolder> popularSearchAdapter;
    BaseRecyclerViewAdapter<String,ViewHolder> searchResultAdapter;

    @Override
    public int setContentLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected void initContentView() {
        initPopularSearch();
        initSearchResult();

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s==null || s.length()==0) {
                    popularSearch.setVisibility(View.VISIBLE);
                    rvPopularSearch.setVisibility(View.VISIBLE);
                } else {
                    popularSearch.setVisibility(View.GONE);
                    rvPopularSearch.setVisibility(View.GONE);
                    search(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initPopularSearch() {
        rvPopularSearch.setLayoutManager(new GridLayoutManager(this,2));

        final List<String> data = Arrays.asList("Adidas","Yeezy","Nike");

        popularSearchAdapter = new BaseRecyclerViewAdapter<String,ViewHolder>() {

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
                final View itemView = holder.itemView;
                ((TextView) itemView.findViewById(R.id.search_text)).setText(mData.get(position));
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence searchText = ((TextView)v.findViewById(R.id.search_text)).getText();
                        searchInput.setText(searchText);
                    }
                });
            }
        };

        rvPopularSearch.setAdapter(popularSearchAdapter);
    }

    private void initSearchResult() {
        rvSearchResult.setLayoutManager(new LinearLayoutManager(this));

        searchResultAdapter = new BaseRecyclerViewAdapter<String, ViewHolder>() {
            @Override
            public void loadData() {
                mData = Arrays.asList("Adidas","Yeezy","Nike");
            }

            @Override
            public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
                View itemView = View.inflate(parent.getContext(),R.layout.item_search_result,null);
                return new ViewHolder(itemView) {};
            }

            @Override
            protected void onBindHolder(ViewHolder holder, int position) {
                View itemView = holder.itemView;
                ((TextView)itemView.findViewById(R.id.text)).setText(mData.get(position));
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence searchText = ((TextView)v.findViewById(R.id.text)).getText();
                        //todo
                    }
                });
            }
        };
    }

    private void search(String s) {

    }

    @Override
    protected void loadData() {
        popularSearchAdapter.loadData();
        searchResultAdapter.loadData();
    }
}
