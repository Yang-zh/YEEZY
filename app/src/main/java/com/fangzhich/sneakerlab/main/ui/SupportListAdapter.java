package com.fangzhich.sneakerlab.main.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.recyclerview.BaseRecyclerViewAdapter;
import com.fangzhich.sneakerlab.main.data.entity.TalkItemEntity;

import butterknife.BindView;

/**
 * SupportListAdapter
 * Created by Khorium on 2016/10/17.
 */
public class SupportListAdapter extends BaseRecyclerViewAdapter<TalkItemEntity, RecyclerView.ViewHolder> {



    private final static int TYPE_USER = 0;
    private final static int TYPE_SERVICE = 1;

    @Override
    public void loadData() {
        // todo
    }

    @Override
    public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case TYPE_USER:
                itemView = View.inflate(parent.getContext(),R.layout.item_support_user,null);
                break;
            case TYPE_SERVICE:
                itemView = View.inflate(parent.getContext(),R.layout.item_support_user,null);
                break;
            default:
                itemView = null;
        }

        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
