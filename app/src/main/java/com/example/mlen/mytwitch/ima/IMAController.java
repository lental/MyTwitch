package com.example.mlen.mytwitch.ima;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.ads.interactivemedia.v3.api.AdDisplayContainer;
import com.google.ads.interactivemedia.v3.api.AdErrorEvent;
import com.google.ads.interactivemedia.v3.api.AdEvent;
import com.google.ads.interactivemedia.v3.api.AdsLoader;
import com.google.ads.interactivemedia.v3.api.AdsManager;
import com.google.ads.interactivemedia.v3.api.AdsManagerLoadedEvent;
import com.google.ads.interactivemedia.v3.api.AdsRequest;
import com.google.ads.interactivemedia.v3.api.ImaSdkFactory;
import com.google.ads.interactivemedia.v3.api.player.ContentProgressProvider;
import com.google.ads.interactivemedia.v3.api.player.VideoProgressUpdate;
import com.google.android.exoplayer2.SimpleExoPlayer;

/**
 * Created by mlen on 2/19/17.
 */

public class IMAController implements AdErrorEvent.AdErrorListener, AdEvent.AdEventListener {
    private final String TAG = this.getClass().getSimpleName();
    // The video player.
    private SimpleExoPlayer mVideoPlayer;

    // The container for the ad's UI.
    private ViewGroup mAdUiContainer;

    // Factory class for creating SDK objects.
    private ImaSdkFactory mSdkFactory;

    // The AdsLoader instance exposes the requestAds method.
    private AdsLoader mAdsLoader;

    // AdsManager exposes methods to control ad playback and listen to ad events.
    private AdsManager mAdsManager;

    // Whether an ad is displayed.
    private boolean mIsAdDisplayed;

    public IMAController(Context c, SimpleExoPlayer exo, ViewGroup container) {
        this.mVideoPlayer = exo;
        this.mAdUiContainer = container;

        // Create an AdsLoader.
        mSdkFactory = ImaSdkFactory.getInstance();
        mAdsLoader = mSdkFactory.createAdsLoader(c);
        // Add listeners for when ads are loaded and for errors.
        mAdsLoader.addAdErrorListener(this);
        mAdsLoader.addAdsLoadedListener(new AdsLoader.AdsLoadedListener() {
            @Override
            public void onAdsManagerLoaded(AdsManagerLoadedEvent adsManagerLoadedEvent) {
                // Ads were successfully loaded, so get the AdsManager instance. AdsManager has
                // events for ad playback and errors.
                mAdsManager = adsManagerLoadedEvent.getAdsManager();

                // Attach event and error event listeners.
                mAdsManager.addAdErrorListener(IMAController.this);
                mAdsManager.addAdEventListener(IMAController.this);
                mAdsManager.init();
            }
        });

        // Add listener for when the content video finishes.
        // POSTROLLS
//        mVideoPlayer.addVideoCompletedListener(new OnVideoCompletedListener() {
//            @Override
//            public void onVideoCompleted() {
//                // Handle completed event for playing post-rolls.
//                if (mAdsLoader != null) {
//                    mAdsLoader.contentComplete();
//                }
//            }
//        });
    }

    /**
     * Request video ads from the given VAST ad tag.
     * @param adTagUrl URL of the ad's VAST XML
     */
    public void requestAds(String adTagUrl) {
        AdDisplayContainer adDisplayContainer = mSdkFactory.createAdDisplayContainer();
        adDisplayContainer.setAdContainer(mAdUiContainer);

        // Create the ads request.
        AdsRequest request = mSdkFactory.createAdsRequest();
        request.setAdTagUrl(adTagUrl);
        request.setAdDisplayContainer(adDisplayContainer);

        //Midrolls, maybe?
        request.setContentProgressProvider(new ContentProgressProvider() {
            @Override
            public VideoProgressUpdate getContentProgress() {
                if (mIsAdDisplayed || mVideoPlayer == null || mVideoPlayer.getDuration() <= 0) {
                    return VideoProgressUpdate.VIDEO_TIME_NOT_READY;
                }
                return new VideoProgressUpdate(mVideoPlayer.getCurrentPosition(),
                        mVideoPlayer.getDuration());
            }
        });

        // Request the ad. After the ad is loaded, onAdsManagerLoaded() will be called.
        mAdsLoader.requestAds(request);
    }

    @Override
    public void onAdEvent(AdEvent adEvent) {
        Log.i(TAG, "Event: " + adEvent.getType());

        // These are the suggested event types to handle. For full list of all ad event
        // types, see the documentation for AdEvent.AdEventType.
        switch (adEvent.getType()) {
            case LOADED:
                // AdEventType.LOADED will be fired when ads are ready to be played.
                // AdsManager.start() begins ad playback. This method is ignored for VMAP or
                // ad rules playlists, as the SDK will automatically start executing the
                // playlist.
                mAdsManager.start();
                break;
            case CONTENT_PAUSE_REQUESTED:
                // AdEventType.CONTENT_PAUSE_REQUESTED is fired immediately before a video
                // ad is played.
                mIsAdDisplayed = true;
                // We can either pause or mute
                mVideoPlayer.setPlayWhenReady(false);
                //  mVideoPlayer.setVolume(0);
                break;
            case CONTENT_RESUME_REQUESTED:
                // AdEventType.CONTENT_RESUME_REQUESTED is fired when the ad is completed
                // and you should start playing your content.
                mIsAdDisplayed = false;

                // We can either pause or mute
                mVideoPlayer.setPlayWhenReady(true);
                // mVideoPlayer.setVolume(1);
                break;
            case ALL_ADS_COMPLETED:
                if (mAdsManager != null) {
                    mAdsManager.destroy();
                    mAdsManager = null;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onAdError(AdErrorEvent adErrorEvent) {
        Log.e(TAG, "Ad Error: " + adErrorEvent.getError().getMessage());
        mVideoPlayer.setPlayWhenReady(true);
    }

    public void onPause(){
        if (mAdsManager != null && mIsAdDisplayed) {
            mAdsManager.pause();
        }
    }
    public void onResume(){
        if (mAdsManager != null && mIsAdDisplayed) {
            mAdsManager.resume();
        }
    }

}
