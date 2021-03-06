package com.fangzhich.sneakerlab.product.widget;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.recyclerview.BaseRecyclerViewAdapter;
import com.fangzhich.sneakerlab.product.data.entity.ProductEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * OptionAdapter
 * Created by Khorium on 2016/10/13.
 */

class OptionAdapter extends BaseRecyclerViewAdapter<ProductEntity.Option.ProductOption, RecyclerView.ViewHolder> {

    public SizeDialog.OptionType type;

    private List<ProductEntity.Option.ProductOption> options;

    OptionAdapter(List<ProductEntity.Option.ProductOption> options, SizeDialog.OptionType type) {
        this.options = options;
        this.type = type;
    }

    @Override
    public void loadData() {
        mData = options;
        for (int i=0;i<mData.size();i++) {
            chooseStatus.add(false);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(parent.getContext(), R.layout.item_option, null);
        return new RecyclerView.ViewHolder(itemView) {};
    }

    @Override
    protected void onBindHolder(RecyclerView.ViewHolder holder, final int position) {
        View itemView = holder.itemView;
        ((TextView) itemView.findViewById(R.id.text)).setText(mData.get(position).name);
        CardView card = ((CardView) itemView.findViewById(R.id.cardItem));
        TextView text = ((TextView) itemView.findViewById(R.id.text));
        card.setCardBackgroundColor(itemView.getResources().getColor(getColor(position)));
        text.setTextColor(itemView.getResources().getColor(getTextColor(position)));
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChooseStatus(position);
                if(onOptionChooseListener!=null) {
                    onOptionChooseListener.onOptionChoose(type,position,chooseStatus.get(position));
                }
            }
        });
    }

    private ArrayList<Boolean> chooseStatus = new ArrayList<>();
    private int oldPosition;

    private int getColor(int position) {
        return chooseStatus.get(position) ? R.color.colorAccent : R.color.product_option_background;
    }

    private int getTextColor(int position) {
        return chooseStatus.get(position) ? R.color.textColorWhite : R.color.tab_layout_unSelect_color;
    }

    private synchronized void setChooseStatus(int position) {
        if (chooseStatus.get(position)) {
            chooseStatus.set(position,false);
        } else {
            chooseStatus.set(oldPosition,false);
            notifyItemChanged(oldPosition);
            chooseStatus.set(position,true);
            oldPosition = position;
        }
        notifyItemChanged(position);
    }

    private OnOptionChooseListener onOptionChooseListener;

    interface OnOptionChooseListener {
        void onOptionChoose(SizeDialog.OptionType type, int position, boolean isChoose);
    }

    void setOnOptionChooseListener(OnOptionChooseListener listener) {
        this.onOptionChooseListener = listener;
    }
}
