package com.example.mlen.mytwitch.ima;

import android.util.Log;

import java.net.URLEncoder;

/**
 * Created by mlen on 2/19/17.
 */

public class IMAUtils {
    private static final String TAG = IMAUtils.class.getSimpleName();

    // To get cust params working here, I need to parcelize the model, so PlayerActivity can get all info
    public static String generateAdTag(String base, String channelName) {
        StringBuilder builder = new StringBuilder(base);

        try {
            replaceStringInBuilder(builder, "[iu]", URLEncoder.encode("/3576121/twitch/channels/" + channelName, "utf-8"));
            replaceStringInBuilder(builder, "[url]", URLEncoder.encode("https://www.twitch.tv/" + channelName, "utf-8"));
            replaceStringInBuilder(builder, "[cust_params]", "partner%3Dtrue%26game%3Dmetro%3A_last_light%26chan%3D" + channelName + "%26embed%3Dfalse%26platform%3Dapp%26playerType%3Dsite%26mature%3Dfalse%26pos%3D1%26timebreak%3D30%26adblock%3Dfalse%26sdk%3Dhtml5%26kuid%3Drkengex45");

        } catch (Exception e) {
            Log.e(TAG, "Unable to generate Ad tag", e);
        }

        return builder.toString();
    }

    private static void replaceStringInBuilder(StringBuilder builder, String src, String repl) {

        int replI = builder.indexOf(src);
        builder.replace(replI, replI + src.length(), repl);
    }
}
