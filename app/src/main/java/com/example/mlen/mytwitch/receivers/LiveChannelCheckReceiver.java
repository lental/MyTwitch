package com.example.mlen.mytwitch.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.mlen.mytwitch.services.LiveChannelCheckService;

/**
 * Created by mlen on 3/2/17.
 */

public class LiveChannelCheckReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent liveCheck = new Intent(context, LiveChannelCheckService.class);
        context.startService(liveCheck);
    }
}
