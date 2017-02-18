package com.example.mlen.mytwitch.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mlen.mytwitch.R;
import com.example.mlen.mytwitch.api.GetStreamsAsyncTask;
import com.example.mlen.mytwitch.model.StreamsRequest;
import com.example.mlen.mytwitch.recycler.StreamsRecyclerAdapter;

/**
 *
 * Get the channels
 * Make them into POJOs
 * Put the list into an Adapter
 * Display stuff on the screen
 * onClick, open up PlayerActivity
 */
public class ChannelActivity extends MyTwitchNavigationActivity {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CoordinatorLayout contentWrapper = (CoordinatorLayout)findViewById(R.id.content_wrapper);
        getLayoutInflater().inflate(R.layout.channel, contentWrapper);
//        setContentView(R.layout.channel);
        GetStreamsAsyncTask task = new GetStreamsAsyncTask(new GetStreamsAsyncTask.GetStreamsCallback() {
            @Override
            public void onStreams(StreamsRequest streams) {
                recyclerView.setAdapter(new StreamsRecyclerAdapter(ChannelActivity.this, streams.getStreams()));
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_wrapper);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        task.execute();

    }
}
