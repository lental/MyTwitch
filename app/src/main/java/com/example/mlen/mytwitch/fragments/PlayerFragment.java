package com.example.mlen.mytwitch.fragments;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.mlen.mytwitch.R;
import com.example.mlen.mytwitch.api.GetAccessTokenAsyncTask;
import com.example.mlen.mytwitch.ima.IMAController;
import com.example.mlen.mytwitch.ima.IMAUtils;
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
 * Created by mlen on 3/1/17.
 */

public class PlayerFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();
    private SimpleExoPlayer player;
    private IMAController imaController;
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();

    RelativeLayout wrapper;
    SimpleExoPlayerView simpleExoPlayerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View inflated = inflater.inflate(R.layout.player_fragment, container, false);
        wrapper = (RelativeLayout) inflated.findViewById(R.id.player_wrapper);
        simpleExoPlayerView = (SimpleExoPlayerView) wrapper.findViewById(R.id.player_view);
        return inflated;
    }

    @Override
    public void onResume() {
        super.onResume();
        player.setPlayWhenReady(true);
        if (imaController != null) {
            imaController.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        player.setPlayWhenReady(false);
        if (imaController != null) {
            imaController.onPause();
        }
    }
    @Override
    public void onDestroy() {
        super.onPause();
        player.setPlayWhenReady(false);
        if (imaController != null) {
            imaController.onDestroy();
        }
        player.release();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        player = createReallySimpleExoPlayer();
        simpleExoPlayerView.requestFocus();
        simpleExoPlayerView.setPlayer(player);

        imaController = new IMAController(getActivity(), player, wrapper);
        String url = null;
        String channelName = null;
        if (getActivity().getIntent().getExtras() != null) {
            Bundle extras = getActivity().getIntent().getExtras();
            url = extras.getString("url");
            channelName = extras.getString("channelName");

            String adTag = IMAUtils.generateAdTag("https://pubads.g.doubleclick.net/gampad/live/ads?iu=[iu]&ius_szs=300x250&sz=640x480&gdfp_req=1&env=vp&output=xml_vast3&url=[url]&correlator=[timestamp]&cust_params=[cust_params]&unviewed_position_start=1&eid=567890291&sdkv=h.3.158.1&sdki=3c0d&scor=1584445166772916&adk=2615417281&osd=2&frm=0&sdr=1&afvsz=200x200%2C250x250%2C300x250%2C336x280%2C450x50%2C468x60%2C480x70&ciu_szs=300x60%2C300x250&ged=ve4_td9_tt2_pd9_la9000_er238.80.394.380_vi0.0.926.735_vp100_eb24171", channelName);
            imaController.requestAds(adTag);
        }

        if (url != null) {
            playVideo(url);
        } else if (channelName != null) {
            getURLAndPlay(channelName);
        }

    }

    private SimpleExoPlayer createReallySimpleExoPlayer() {
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveVideoTrackSelection.Factory(BANDWIDTH_METER);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        SimpleExoPlayer newPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, new DefaultLoadControl(),
                null, SimpleExoPlayer.EXTENSION_RENDERER_MODE_ON);
        newPlayer.setPlayWhenReady(true);
        return newPlayer;
    }

    public void getURLAndPlay(String name) {
        GetAccessTokenAsyncTask task = new GetAccessTokenAsyncTask(new GetAccessTokenAsyncTask.GetAccessTokenCallback() {
            @Override
            public void onAccessToken(TwitchAccessToken token) {
                if (token != null) {
                    playVideo(Utils.generateURL(token.getChannelName(), token));
                }
            }
        });
        task.execute(name);
    }

    private void playVideo(String url) {
        Uri uri = Uri.parse(url);
        DataSource.Factory dataFactory = new DefaultDataSourceFactory(getActivity(), BANDWIDTH_METER,
                new DefaultHttpDataSourceFactory("myTwitchApp", BANDWIDTH_METER));

        MediaSource source = new HlsMediaSource(uri, dataFactory, new Handler(), null);
        player.prepare(source);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Log.d(TAG, "PlayerFragment Finalized");
    }
}
