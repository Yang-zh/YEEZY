package com.fangzhich.ivankajingle.main.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.fangzhich.ivankajingle.R;
import com.fangzhich.ivankajingle.base.ui.BaseActivity;
import com.fangzhich.ivankajingle.main.data.entity.MessageEntity;
import com.fangzhich.ivankajingle.user.data.net.UserApi;
import com.fangzhich.ivankajingle.util.ToastUtil;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import butterknife.OnClick;
import rx.SingleSubscriber;
import timber.log.Timber;

/**
 * SupportActivity
 * Created by Khorium on 2016/9/1.
 */
public class SupportActivity extends BaseActivity {

    public static int NOTIFY_SUPPORT = 101;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.support_intro)
    TextView supportIntro;

    @BindView(R.id.rv_support_record_list)
    RecyclerView rvSupportRecordList;
    private SupportListAdapter adapter;

    @BindView(R.id.message)
    EditText message;
    @OnClick(R.id.bt_send)
    void sendMessage() {
        Timber.d(FirebaseInstanceId.getInstance().getToken());
        if (!TextUtils.isEmpty(message.getText())) {
            UserApi.sendSupportMessage("0",message.getText().toString(), new SingleSubscriber<Object>() {
                @Override
                public void onSuccess(Object value) {
                    MessageEntity entity = new MessageEntity(message.getText().toString(),0);
                    adapter.addItem(entity);
                    message.setText("");
                }

                @Override
                public void onError(Throwable error) {
                    ToastUtil.toast(error.getMessage());
                    Timber.e(error);
                }
            });
        }
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_support;
    }

    @Override
    protected void initContentView() {
        initToolbar();
        initRecyclerView();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        title.setText(R.string.CustomerSupport);
    }

    private void initRecyclerView() {
        rvSupportRecordList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SupportListAdapter();
        rvSupportRecordList.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        adapter.loadData();
        getNotification();
    }

    private void getNotification() {
        if (getIntent().getExtras() == null) {
            return;
        }
        for (String key : getIntent().getExtras().keySet()) {
            Object value = getIntent().getExtras().get(key);
            Timber.d("Key: " + key + " Value: " + value);
            final String text = (String) value;
            if (key.equals("text") && !TextUtils.isEmpty(text)) {
                UserApi.sendSupportMessage("1",text, new SingleSubscriber<Object>() {
                    @Override
                    public void onSuccess(Object value) {
                        Timber.d("text is "+text);
                        MessageEntity entity = new MessageEntity(text,1);
                        adapter.addItem(entity);
                    }

                    @Override
                    public void onError(Throwable error) {
                        ToastUtil.toast(error.getMessage());
                        Timber.e(error);
                    }
                });
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
