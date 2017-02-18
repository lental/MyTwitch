package com.example.mlen.mytwitch.recycler;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mlen.mytwitch.activity.PlayerActivity;
import com.example.mlen.mytwitch.R;

/**
 * Created by mlen on 2/17/17.
 */

public class StreamsRecyclerView extends LinearLayout implements View.OnClickListener {
    ImageView previewView;
    TextView titleView;
    TextView gameView;
    TextView viewersView;

    String channelName;

    public StreamsRecyclerView(Context context) {
        super(context);
    }

    public StreamsRecyclerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public StreamsRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StreamsRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        titleView = (TextView) this.findViewById(R.id.recycler_title);
        previewView = (ImageView) this.findViewById(R.id.recycler_preivew);
        gameView = (TextView) this.findViewById(R.id.recycler_game);
        viewersView = (TextView) this.findViewById(R.id.recycler_viewers);

        this.setOnClickListener(this);
    }

    public void setTitle(String text) {
        titleView.setText(text);
    }

    public void setGame(String text) {
        gameView.setText("Playing " + text);
    }

    public void setViewers(int viewers) {
        viewersView.setText(viewers + " viewers");
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this.getContext(), PlayerActivity.class);
        intent.putExtra("channelName", channelName);
        this.getContext().startActivity(intent);
    }
}
