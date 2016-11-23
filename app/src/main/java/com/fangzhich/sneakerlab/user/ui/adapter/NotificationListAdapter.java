package com.fangzhich.sneakerlab.user.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.recyclerview.BaseRecyclerViewAdapter;
import com.fangzhich.sneakerlab.main.data.entity.NotificationEntity;
import com.fangzhich.sneakerlab.user.data.net.UserApi;
import com.fangzhich.sneakerlab.util.Const;
import com.fangzhich.sneakerlab.util.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.SingleSubscriber;
import timber.log.Timber;

/**
 * NotificationListAdapter
 * Created by Khorium on 2016/9/8.
 */
public class NotificationListAdapter extends BaseRecyclerViewAdapter<NotificationEntity,NotificationListAdapter.ViewHolder> {

    private ArrayList<NotificationEntity> notifications;

    public void loadData() {
        //todo
        if (Const.isLogin()) {
            UserApi.getNotificationList(new SingleSubscriber<ArrayList<NotificationEntity>>() {
                @Override
                public void onSuccess(ArrayList<NotificationEntity> value) {
                    notifications = value;
                    notifyDataSetChanged();
                }

                @Override
                public void onError(Throwable error) {
//                    ToastUtil.toast(error.getMessage());
                    Timber.e(error);
                }
            });
        }
    }


    @Override
    public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.item_notification, null));
    }

    @Override
    public void onBindHolder(ViewHolder holder, int position) {
        NotificationEntity entity = notifications.get(position);
        holder.notificationTime.setText(entity.data_added);
        holder.notificationDetail.setText(entity.text);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.notification_icon)
        ImageView notificationIcon;
        @BindView(R.id.notification_time)
        TextView notificationTime;
        @BindView(R.id.notification_detail)
        TextView notificationDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
