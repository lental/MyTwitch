package com.example.mlen.mytwitch.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.mlen.mytwitch.R;
import com.example.mlen.mytwitch.application.MyTwitchApplication;
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

        SharedPreferences livePrefs = getSharedPreferences(MyTwitchApplication.LIVE_CHECK_PREFS, 0);
        String userName = livePrefs.getString(MyTwitchApplication.LIVE_CHECK_PREFS_CHANNEL_NAME, null);
        if (userName == null) {
            Log.e(TAG, "No Username in preferences to check");
        }
        try {
            String responseText = Utils.executeGet("https://api.twitch.tv/kraken/streams/" + userName);

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

            StreamRequest requestData = gson.fromJson(responseText, StreamRequest.class);

            boolean isLive = livePrefs.getBoolean(MyTwitchApplication.LIVE_CHECK_PREFS_IS_LIVE, false);
            if (requestData.getStream() == null) {
                // Not live at the moment.  If we saved live then switch
                if (isLive) {
                    sendLiveNotification(1, "Stream has stopped", userName + " Has now stopped streaming");
                    livePrefs.edit().putBoolean(MyTwitchApplication.LIVE_CHECK_PREFS_IS_LIVE, false).apply();
                }
            } else {
                // Live.  If we don't have memory of being live, set it to true
                if (!isLive) {
                    sendLiveNotification(1, "Stream is live", userName + " is now live!");
                    livePrefs.edit().putBoolean(MyTwitchApplication.LIVE_CHECK_PREFS_IS_LIVE, true).apply();
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "Exception when handling stream check", e);
        }

    }

    private void sendLiveNotification(int id, String title, String text) {

        Bitmap icon = BitmapFactory.decodeResource(this.getResources(), R.mipmap.logo);

        Notification noti = new Notification.Builder(this)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.logo_white)
                .setLargeIcon(icon)
                .build();

        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.notify(id, noti);
    }
}
