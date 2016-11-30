package com.fangzhich.ivankajingle.user.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fangzhich.ivankajingle.R;
import com.fangzhich.ivankajingle.base.ui.BaseFragment;
import com.fangzhich.ivankajingle.base.ui.recyclerview.LinearLayoutItemDecoration;
import com.fangzhich.ivankajingle.user.ui.adapter.NotificationListAdapter;

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
