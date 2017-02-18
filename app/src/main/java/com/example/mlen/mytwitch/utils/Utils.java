package com.example.mlen.mytwitch.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.mlen.mytwitch.model.TwitchAccessToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mlen on 2/17/17.
 */

public class Utils {

    private static OkHttpClient client = new OkHttpClient();

    public static String executeGet(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Client-ID", "kd1unb4b3q4t58fwlpcbzcbnm76a8fp")
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String generateURL(String channelName, TwitchAccessToken token) {
        StringBuilder builder = new StringBuilder();
        builder.append("http://usher.twitch.tv/api/channel/hls/" + channelName + ".m3u8?");
        try {
            builder.append("token=" + URLEncoder.encode(token.getToken(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        builder.append("&sig=" + token.getSig());

        return builder.toString();
    }

}
