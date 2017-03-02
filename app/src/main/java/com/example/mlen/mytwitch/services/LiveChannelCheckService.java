package com.example.mlen.mytwitch.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.mlen.mytwitch.R;

/**
 * Created by mlen on 3/2/17.
 */

public class LiveChannelCheckService extends IntentService {
    private static String TAG = LiveChannelCheckService.class.getSimpleName();
    public LiveChannelCheckService() {
        super(TAG);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sendLiveNotification(2 , "Check Disabled", "Check is now turned off");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        sendLiveNotification(1, "Keeping Track of channel", "Checking if channel is live...");
    }

    private void sendLiveNotification(int id, String title, String text) {

        Bitmap icon = BitmapFactory.decodeResource(this.getResources(), R.drawable.mail2);

        Notification noti = new Notification.Builder(this)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.mail1)
                .setLargeIcon(icon)
                .build();

        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.notify(id, noti);
    }
}
