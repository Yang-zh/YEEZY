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
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

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

        if (remoteMessage.getNotification() != null) {
            Timber.d(remoteMessage.getNotification().getBody());
        }
        sendNotification(remoteMessage.getNotification(),MainActivity.NOTIFY_DEFAULT);
    }

    private void sendCartNotification(RemoteMessage.Notification notification) {
        sendNotification(notification,MainActivity.NOTIFY_CART);
    }

    private void sendNotification(RemoteMessage.Notification notification,int requestCode) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 100 /* Request code */, intent,
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
