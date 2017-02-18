package com.example.mlen.mytwitch.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;

import com.example.mlen.mytwitch.R;
import com.example.mlen.mytwitch.api.GetAccessTokenAsyncTask;
import com.example.mlen.mytwitch.model.TwitchAccessToken;
import com.example.mlen.mytwitch.utils.Utils;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

/**
 * Created by mlen on 2/17/17.
 */

public class PlayerActivity extends MyTwitchNavigationActivity {
    private SimpleExoPlayer player;
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    String channelName = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CoordinatorLayout contentWrapper = (CoordinatorLayout)findViewById(R.id.content_wrapper);
        getLayoutInflater().inflate(R.layout.player, contentWrapper);

        player = createReallySimpleExoPlayer();
        SimpleExoPlayerView simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.player_view);
        simpleExoPlayerView.requestFocus();
        simpleExoPlayerView.setPlayer(player);

        String url = null;
        if (getIntent().getExtras() != null) {
            Bundle extras = getIntent().getExtras();
            url = extras.getString("url");
            channelName = extras.getString("channelName");
        }

        if (url != null) {
            playVideo(url);
        } else if (channelName != null) {
            GetAccessTokenAsyncTask task = new GetAccessTokenAsyncTask(new GetAccessTokenAsyncTask.GetAccessTokenCallback() {
                @Override
                public void onAccessToken(TwitchAccessToken token) {
                    if (token != null) {
                        playVideo(Utils.generateURL(channelName, token));
                    }
                }
            });
            task.execute(channelName);
        }
    }
    private SimpleExoPlayer createReallySimpleExoPlayer() {
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveVideoTrackSelection.Factory(BANDWIDTH_METER);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        SimpleExoPlayer newPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, new DefaultLoadControl(),
                null, SimpleExoPlayer.EXTENSION_RENDERER_MODE_ON);
        newPlayer.setPlayWhenReady(true);
        return newPlayer;
    }

    private void playVideo(String url) {
        Uri uri = Uri.parse(url);
        DataSource.Factory dataFactory = new DefaultDataSourceFactory(this, BANDWIDTH_METER,
                new DefaultHttpDataSourceFactory("myTwitchApp", BANDWIDTH_METER));

        MediaSource source = new HlsMediaSource(uri, dataFactory, new Handler(), null);
        player.prepare(source);
    }
}
