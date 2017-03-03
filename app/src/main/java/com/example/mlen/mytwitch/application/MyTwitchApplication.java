package com.example.mlen.mytwitch.application;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.mlen.mytwitch.receivers.LiveChannelCheckReceiver;

/**
 * Created by mlen on 3/2/17.
 */

public class MyTwitchApplication extends Application {
    public static final String LIVE_CHECK_PREFS = "live_check_prefs";
    public static final String LIVE_CHECK_PREFS_CHANNEL_NAME = "live_check_channel_name";
    public static final String LIVE_CHECK_PREFS_IS_LIVE = "live_check_is_live";
    @Override
    public void onCreate() {
        super.onCreate();

        Intent intent = new Intent(this, LiveChannelCheckReceiver.class);
        // putExtra does not work on broadcast
        //intent.putExtra("userName","doublelift");

        SharedPreferences livePrefs = getSharedPreferences(LIVE_CHECK_PREFS, 0);

        if (livePrefs.getString(LIVE_CHECK_PREFS_CHANNEL_NAME, null) == null) {
            livePrefs.edit().putString(LIVE_CHECK_PREFS_CHANNEL_NAME, "timthetatman").apply();
        }

        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, LiveChannelCheckReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, 1, 5000, pIntent);
    }
}
