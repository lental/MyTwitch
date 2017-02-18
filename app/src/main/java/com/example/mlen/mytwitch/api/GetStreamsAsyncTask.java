package com.example.mlen.mytwitch.api;

import android.os.AsyncTask;

import com.example.mlen.mytwitch.activity.ChannelActivity;
import com.example.mlen.mytwitch.model.StreamsRequest;
import com.example.mlen.mytwitch.recycler.StreamsRecyclerAdapter;
import com.example.mlen.mytwitch.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

/**
 * Created by mlen on 2/17/17.
 */

public class GetStreamsAsyncTask extends AsyncTask<String, Void, StreamsRequest> {

    public interface GetStreamsCallback {
        void onStreams(StreamsRequest streams);
    }

    GetStreamsCallback callback;
    public GetStreamsAsyncTask(GetStreamsCallback callback) {
        this.callback = callback;
    }
    @Override
    protected StreamsRequest doInBackground(String... params) {
        try {
            String responseText  = Utils.executeGet("https://api.twitch.tv/kraken/streams?limit=25&hls=true&offset=0&avc_profile=High&avc_level=4.1");

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

            StreamsRequest requestData = gson.fromJson(responseText, StreamsRequest.class);
            return requestData;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(StreamsRequest s) {
        super.onPostExecute(s);
        callback.onStreams(s);
    }
}
