package com.fangzhich.yeezy.ui.fragment;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.utils.SizeUtils;
import com.fangzhich.yeezy.R;
import com.fangzhich.yeezy.net.Bean.NotificationEntity;
import com.fangzhich.yeezy.ui.adapter.LinearLayoutItemDecoration;
import com.fangzhich.yeezy.ui.adapter.NotificationListAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * NotificationListFragment
 * Created by Khorium on 2016/9/8.
 */
public class NotificationListFragment extends BaseFragment {


    @BindView(R.id.recyclerView_notificationList)
    RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private NotificationListAdapter adapter;

    ArrayList<NotificationEntity> notifications = new ArrayList<>();
    @Override
    public int setContentLayout() {
        return R.layout.fragment_notification_list;
    }

    @Override
    protected void initContentView() {
        manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        adapter = new NotificationListAdapter();
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new LinearLayoutItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        for (int i=0;i<20;i++) {
            notifications.add(new NotificationEntity());
        }
        adapter.setData(notifications);
        adapter.notifyDataSetChanged();
    }
}
