package com.example.mlen.mytwitch.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.mlen.mytwitch.R;
import com.example.mlen.mytwitch.model.Stream;
import com.example.mlen.mytwitch.utils.DownloadImageTask;

/**
 * Created by mlen on 2/17/17.
 */

public class StreamsRecyclerAdapter extends RecyclerView.Adapter<StreamsRecyclerViewHolder>{
        LayoutInflater inflater;
        Stream[] streams;
        StreamsRecyclerViewClickListener listener;
        public StreamsRecyclerAdapter(Context c, Stream[] streams, StreamsRecyclerViewClickListener listener) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.streams = streams;
            this.listener = listener;
        }
        @Override
        public StreamsRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            StreamsRecyclerViewHolder vh = new StreamsRecyclerViewHolder(inflater.inflate(R.layout.stream_view, null), listener);
            return vh;
        }

        @Override
        public void onBindViewHolder(StreamsRecyclerViewHolder holder, int position) {

            holder.recyclerView.setTitle(streams[position].getChannel().getName());
            holder.recyclerView.setGame(streams[position].getGame());
            holder.recyclerView.setViewers(streams[position].getViewers());
            holder.recyclerView.channelName = streams[position].getChannel().getName();
            new DownloadImageTask(holder.recyclerView.previewView).execute(streams[position].getPreview().getMedium());
        }

        @Override
        public int getItemCount() {
            return streams.length;
        }
}
