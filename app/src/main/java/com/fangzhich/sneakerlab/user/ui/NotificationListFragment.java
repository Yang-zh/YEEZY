package com.fangzhich.sneakerlab.user.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.base.ui.BaseFragment;
import com.fangzhich.sneakerlab.base.ui.recyclerview.LinearLayoutItemDecoration;
import com.fangzhich.sneakerlab.main.data.entity.NotificationEntity;
import com.fangzhich.sneakerlab.user.ui.adapter.NotificationListAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * NotificationListFragment
 * Created by Khorium on 2016/9/8.
 */
public class NotificationListFragment extends BaseFragment {


    @BindView(R.id.recyclerView_notificationList)
    RecyclerView recyclerView;
    private NotificationListAdapter adapter;

    @Override
    public int setContentLayout() {
        return R.layout.fragment_notification_list;
    }

    @Override
    protected void initContentView() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        adapter = new NotificationListAdapter();
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new LinearLayoutItemDecoration(getActivity(), LinearLayoutManager.VERTICAL,0));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        adapter.loadData();
    }
}
