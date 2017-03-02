package com.example.mlen.mytwitch.api;

import android.os.AsyncTask;

import com.example.mlen.mytwitch.utils.Utils;
import com.example.mlen.mytwitch.model.TwitchAccessToken;
import com.google.gson.Gson;

import java.io.IOException;

/**
 * Created by mlen on 2/17/17.
 */

public class GetAccessTokenAsyncTask extends AsyncTask<String, Void, TwitchAccessToken> {
    public interface GetAccessTokenCallback {
        void onAccessToken(TwitchAccessToken token);
    }

    GetAccessTokenCallback callback;
    public GetAccessTokenAsyncTask(GetAccessTokenCallback callback) {
        this.callback = callback;
    }
    @Override
    protected TwitchAccessToken doInBackground(String... params) {
        if (params.length == 0) return null;
            String channelName = params[0];

            try {
                String tokenText  = Utils.executeGet("https://api.twitch.tv/api/channels/" + channelName + "/access_token");
                Gson gson = new Gson();
                TwitchAccessToken token = gson.fromJson(tokenText, TwitchAccessToken.class);
                token.setChannelName(channelName);
                return token;
            } catch (IOException e) {
                return null;
        }
    }

    @Override
    protected void onPostExecute(TwitchAccessToken token) {
        super.onPostExecute(token);
        callback.onAccessToken(token);
    }
}
