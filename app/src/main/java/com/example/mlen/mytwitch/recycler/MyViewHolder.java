package com.example.mlen.mytwitch.recycler;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mlen.mytwitch.R;

/**
 * Created by mlen on 2/16/17.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {
    public StreamsRecyclerView recyclerView;
    public MyViewHolder(View itemView) {
        super(itemView);
        recyclerView = (StreamsRecyclerView) itemView.findViewById(R.id.recycler_main);
    }
}


