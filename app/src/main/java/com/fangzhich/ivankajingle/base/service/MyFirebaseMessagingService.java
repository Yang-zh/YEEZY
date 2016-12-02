package com.fangzhich.ivankajingle.base.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.fangzhich.ivankajingle.R;
import com.fangzhich.ivankajingle.main.ui.MainActivity;
import com.fangzhich.ivankajingle.main.ui.SupportActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import timber.log.Timber;

/**
 * MyFirebaseMessagingService
 * Created by Khorium on 2016/10/9.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification()==null) {
            Timber.e("Empty pushMessage received");
            return;
        }

        Timber.d("PushMessage Body: "+remoteMessage.getNotification().getBody());

        Const.addRemoteMessage(remoteMessage);

        if (!remoteMessage.getFrom().equals("all")) {
            Timber.d("no special notification type find, send default notification");
            sendNotification(remoteMessage.getNotification(), MainActivity.class, MainActivity.NOTIFY_DEFAULT);
        }

        Observable.just(remoteMessage.getData())
                .flatMap(new Func1<Map<String, String>, Observable<Map.Entry<String, String>>>() {
                    @Override
                    public Observable<Map.Entry<String, String>> call(Map<String, String> map) {
                        return Observable.from(map.entrySet());
                    }
                })
                .filter(new Func1<Map.Entry<String, String>, Boolean>() {
                    @Override
                    public Boolean call(Map.Entry<String, String> entry) {
                        if (entry.getKey().equals("support")) {
                            Timber.d("key support find, send support notification");
                            sendSupportNotification(remoteMessage.getNotification());
                            return false;
                        }
                        return entry.getKey().equals("code");
                    }
                })
                .subscribe(new Action1<Map.Entry<String, String>>() {
                    @Override
                    public void call(Map.Entry<String, String> entry) {
                        switch (PushMessageCode.innerValueOf(entry.getValue())) {
                            case SUCCESS_ORDER_CODE:
                                break;
                            case SHIPPING_ORDER_CODE:
                                break;
                            case COMPLETE_ORDER_CODE:
                                break;
                            case CANCEL_ORDER_CODE:
                                break;
                            case OPERATION_BLACK_FRIDAY_CODE:
                                break;
                            case OPERATION_NETWORK_MONDAY_CODE:
                                break;
                            case PROMOTIONS_SUMMARY_CODE:
                                break;
                            case PROMOTIONS_ONLY_ONE_CODE:
                                break;
                            case UNDEFINED:
                                Timber.d("no special notification type find, send default notification");
                                sendNotification(remoteMessage.getNotification(), MainActivity.class, MainActivity.NOTIFY_DEFAULT);
                                break;
                        }
                    }
                });
    }

    private void sendSupportNotification(RemoteMessage.Notification notification) {
        sendNotification(notification, SupportActivity.class, SupportActivity.NOTIFY_SUPPORT);
    }

    private void sendCartNotification(RemoteMessage.Notification notification) {
        sendNotification(notification, MainActivity.class, MainActivity.NOTIFY_CART);
    }

    private void sendNotification(RemoteMessage.Notification notification, Class activity, int requestCode) {
        Intent intent = new Intent(this, activity);
        intent.putExtra("text", notification.getBody());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
