package com.example.mlen.mytwitch.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mlen.mytwitch.R;
import com.example.mlen.mytwitch.api.GetStreamsAsyncTask;
import com.example.mlen.mytwitch.model.StreamsRequest;
import com.example.mlen.mytwitch.recycler.StreamsRecyclerAdapter;

public class ChannelListFragment extends Fragment {
    RecyclerView recyclerView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        GetStreamsAsyncTask task = new GetStreamsAsyncTask(new GetStreamsAsyncTask.GetStreamsCallback() {
            @Override
            public void onStreams(StreamsRequest streams) {
                recyclerView.setAdapter(new StreamsRecyclerAdapter(getActivity(), streams.getStreams()));
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        task.execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.channel_list_fragment, container, false);
        return recyclerView;
    }
}
