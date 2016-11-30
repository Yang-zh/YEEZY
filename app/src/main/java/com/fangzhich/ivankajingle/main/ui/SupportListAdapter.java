package com.fangzhich.ivankajingle.main.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fangzhich.ivankajingle.R;
import com.fangzhich.ivankajingle.base.data.net.FireBaseInitializeException;
import com.fangzhich.ivankajingle.base.ui.recyclerview.BaseRecyclerViewAdapter;
import com.fangzhich.ivankajingle.main.data.entity.MessageEntity;
import com.fangzhich.ivankajingle.user.data.net.UserApi;
import com.fangzhich.ivankajingle.util.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.SingleSubscriber;
import timber.log.Timber;

/**
 * SupportListAdapter
 * Created by Khorium on 2016/10/17.
 */
public class SupportListAdapter extends BaseRecyclerViewAdapter<MessageEntity, SupportListAdapter.ViewHolder> {



    private final static int TYPE_USER = 0;
    private final static int TYPE_SERVICE = 1;

    @Override
    public void loadData() {
        try {
            UserApi.getSupportMessageList(new SingleSubscriber<ArrayList<MessageEntity>>() {
                @Override
                public void onSuccess(ArrayList<MessageEntity> value) {
                    mData = value==null?new ArrayList<MessageEntity>():value;
                    notifyDataSetChanged();
                }

                @Override
                public void onError(Throwable error) {
                    mData = new ArrayList<>();
                    ToastUtil.toast(error.getMessage());
                    Timber.e(error);
                }
            });
        } catch (FireBaseInitializeException e) {
            ToastUtil.toast(e.getMessage());
            mData = new ArrayList<>();
        }
    }

    @Override
    public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case TYPE_USER:
                itemView = View.inflate(parent.getContext(),R.layout.item_support_user,null);
                break;
            case TYPE_SERVICE:
                itemView = View.inflate(parent.getContext(),R.layout.item_support_service,null);
                break;
            default:
                itemView = null;
        }

        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindHolder(ViewHolder holder, int position) {
        holder.text.setText(mData.get(position).text);
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).type;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.headImage)
        ImageView headImage;
        @BindView(R.id.text)
        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
