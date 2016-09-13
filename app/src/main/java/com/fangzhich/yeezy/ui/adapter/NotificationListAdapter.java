package com.fangzhich.yeezy.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.data.net.Bean.NotificationEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * NotificationListAdapter
 * Created by Khorium on 2016/9/8.
 */
public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.NotificationViewHolder> {

    private ArrayList<NotificationEntity> notifications;

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NotificationViewHolder(View.inflate(parent.getContext(), R.layout.item_notification, null));
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        holder.notificationTime.setText("5 minutes ago");
        holder.notificationDetail.setText("Did you know we added the products you just saved to your profile");
    }

    public void setData(ArrayList<NotificationEntity> notifications) {
        this.notifications = notifications;
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.notification_icon)
        ImageView notificationIcon;
        @BindView(R.id.notification_time)
        TextView notificationTime;
        @BindView(R.id.notification_detail)
        TextView notificationDetail;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
