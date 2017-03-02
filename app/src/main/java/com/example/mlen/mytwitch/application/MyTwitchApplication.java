package com.example.mlen.mytwitch.application;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.mlen.mytwitch.receivers.LiveChannelCheckReceiver;
import com.example.mlen.mytwitch.services.LiveChannelCheckService;

/**
 * Created by mlen on 3/2/17.
 */

public class MyTwitchApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Intent intent = new Intent(this, LiveChannelCheckReceiver.class);

        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, LiveChannelCheckReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, 1, 10000, pIntent);
    }
}
