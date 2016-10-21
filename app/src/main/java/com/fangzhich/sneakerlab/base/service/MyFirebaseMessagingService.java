package com.fangzhich.sneakerlab.base.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.fangzhich.sneakerlab.R;
import com.fangzhich.sneakerlab.main.ui.MainActivity;
import com.fangzhich.sneakerlab.main.ui.SupportActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Set;

import timber.log.Timber;

/**
 * MyFirebaseMessagingService
 * Created by Khorium on 2016/10/9.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Timber.d(remoteMessage.getFrom());

        switch (remoteMessage.getFrom()) {
            case "cart":
                sendCartNotification(remoteMessage.getNotification());
            break;
        }
        if (remoteMessage.getData().size()>0) {
            Set<Map.Entry<String, String>> entrySet = remoteMessage.getData().entrySet();
            for (Map.Entry entry: entrySet) {
                Timber.d("remoteMessage data: "+entry.getKey()+" - "+entry.getValue());
                if (entry.getKey().equals("support")) {
                    Timber.d("key support find, send support notification");
                    sendSupportNotification(remoteMessage.getNotification());
                    return;
                }
            }
        }
//        if (remoteMessage.getNotification().getTag()!=null && remoteMessage.getNotification().getTag().contains("support")) {
//            sendSupportNotification(remoteMessage.getNotification());
//        }

        if (remoteMessage.getNotification() != null) {
            Timber.d(remoteMessage.getNotification().getBody());
        }
        Timber.d("no special notification type find, send default notification");
        sendNotification(remoteMessage.getNotification(),MainActivity.class,MainActivity.NOTIFY_DEFAULT);
    }

    private void sendSupportNotification(RemoteMessage.Notification notification) {
        sendNotification(notification, SupportActivity.class,SupportActivity.NOTIFY_SUPPORT);
    }

    private void sendCartNotification(RemoteMessage.Notification notification) {
        sendNotification(notification,MainActivity.class,MainActivity.NOTIFY_CART);
    }

    private void sendNotification(RemoteMessage.Notification notification,Class activity,int requestCode) {
        Intent intent = new Intent(this, activity);
        intent.putExtra("text",notification.getBody());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
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
