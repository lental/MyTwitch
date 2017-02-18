package com.example.mlen.mytwitch.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mlen.mytwitch.R;
import com.example.mlen.mytwitch.api.GetAccessTokenAsyncTask;
import com.example.mlen.mytwitch.model.TwitchAccessToken;
import com.example.mlen.mytwitch.utils.Utils;

public class MainActivity extends MyTwitchNavigationActivity{

    TwitchAccessToken storedToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CoordinatorLayout contentWrapper = (CoordinatorLayout)findViewById(R.id.content_wrapper);
        getLayoutInflater().inflate(R.layout.content_main, contentWrapper);


        Button btnGetToken = (Button)findViewById(R.id.btn_get_access_token);
        btnGetToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetAccessTokenAsyncTask task = new GetAccessTokenAsyncTask(new GetAccessTokenAsyncTask.GetAccessTokenCallback() {
                    @Override
                    public void onAccessToken(TwitchAccessToken token) {
                        setTokenText(token);
                        storedToken = token;
                    }
                });
                task.execute("lenkid");
            }
        });

        Button btnGenerateUrl = (Button)findViewById(R.id.btn_generate_url);
        btnGenerateUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (storedToken == null) {
                    setUrlText("Token not yet acquired");
                } else {
                    setUrlText(Utils.generateURL("lenkid", storedToken));
                }
            }
        });

        Button btnPlay = (Button)findViewById(R.id.btn_play);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new intent with url as extra
                TextView txtGenerateUrl = (TextView)findViewById(R.id.txt_generate_url);
                Intent playerIntent = new Intent(MainActivity.this, PlayerActivity.class);
                playerIntent.putExtra("url", txtGenerateUrl.getText());
                startActivity(playerIntent);
            }
        });

    }

    public void setTokenText(TwitchAccessToken token) {
        TextView txtGetToken = (TextView)findViewById(R.id.txt_get_access_token);
        txtGetToken.setText(token.toString());
    }

    public void setUrlText(String text) {
        TextView txtGenerateUrl = (TextView)findViewById(R.id.txt_generate_url);
        txtGenerateUrl.setText(text);
    }
}
