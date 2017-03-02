package com.example.mlen.mytwitch.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mlen.mytwitch.R;
import com.example.mlen.mytwitch.api.GetStreamsAsyncTask;
import com.example.mlen.mytwitch.fragments.ChannelListFragment;
import com.example.mlen.mytwitch.fragments.PlayerFragment;
import com.example.mlen.mytwitch.model.StreamsRequest;
import com.example.mlen.mytwitch.recycler.StreamsRecyclerAdapter;
import com.example.mlen.mytwitch.recycler.StreamsRecyclerViewClickListener;

/**
 *
 * Get the channels
 * Make them into POJOs
 * Put the list into an Adapter
 * Display stuff on the screen
 * onClick, open up PlayerActivity
 */
public class ChannelActivity extends MyTwitchNavigationActivity implements StreamsRecyclerViewClickListener {
    private final String TAG = this.getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CoordinatorLayout contentWrapper = (CoordinatorLayout)findViewById(R.id.content_wrapper);
        getLayoutInflater().inflate(R.layout.channel, contentWrapper);
        changeLayoutBasedOnOrientation(getResources().getConfiguration().orientation);
    }

    @Override
    public void onChannelSelected(String channelName) {
        PlayerFragment playerFrag = (PlayerFragment)
                getFragmentManager().findFragmentById(R.id.player_fragment);

        // If the layout has both Player and Channel fragments, directly play the new channel
        if (playerFrag != null) {
            playerFrag.getURLAndPlay(channelName);

        // Otherwise, start up a new intent
        } else {
            Intent intent = new Intent(this, PlayerActivity.class);
            intent.putExtra("channelName", channelName);
            this.startActivity(intent);
        }
        Log.d(TAG, "Channel Selected " + channelName);
    }

    /**
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        changeLayoutBasedOnOrientation(newConfig.orientation);
    }

    /**
     *
     * When the app rotates, change the layout from horizontal to vertical
     * @param orientation
     */
    protected void changeLayoutBasedOnOrientation(int orientation) {

        LinearLayout layout = (LinearLayout) findViewById(R.id.channel_main);

        ChannelListFragment listFrag = (ChannelListFragment)
                getFragmentManager().findFragmentById(R.id.channel_fragment);

        PlayerFragment playerFrag = (PlayerFragment)
                getFragmentManager().findFragmentById(R.id.player_fragment);
        View channelList = listFrag.getView();

        // Checks the orientation of the screen
        int limit = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, getResources().getDisplayMetrics());

        if (playerFrag == null) {
            limit = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layout.setOrientation(LinearLayout.HORIZONTAL);
            channelList.setLayoutParams(new LinearLayout.LayoutParams(limit, ViewGroup.LayoutParams.MATCH_PARENT));
            ViewGroup parentView = ((ViewGroup) channelList.getParent());
            parentView.removeView(channelList);
            parentView.addView(channelList, 0);
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT){
            layout.setOrientation(LinearLayout.VERTICAL);
            channelList.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, limit));
            ViewGroup parentView = ((ViewGroup) channelList.getParent());
            parentView.removeView(channelList);
            parentView.addView(channelList);
        }
    }
}
