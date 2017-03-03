package com.example.mlen.mytwitch.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.mlen.mytwitch.R;
import com.example.mlen.mytwitch.model.StreamRequest;
import com.example.mlen.mytwitch.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Cannot use extras because they don't get passed through the broadcast
//        if (intent.getExtras() != null) {
            String userName = "doublelift"; //intent.getExtras().getString("userName");
            try {
                String responseText = Utils.executeGet("https://api.twitch.tv/kraken/streams/" + userName);

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();

                StreamRequest requestData = gson.fromJson(responseText, StreamRequest.class);
                Log.d(TAG, "Hello");
                if (requestData.getStream() == null) {
                    sendLiveNotification(1, "Stream not open", "Stream is not yet live");
                } else {
                    sendLiveNotification(1, "Stream is live", userName + " is now live!");
                }
            } catch (Exception e) {

            }
//        }

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
