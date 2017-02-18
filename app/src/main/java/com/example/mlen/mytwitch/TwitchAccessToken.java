package com.example.mlen.mytwitch;

/**
 * Created by mlen on 2/17/17.
 */

public class TwitchAccessToken {
    String token;
    String sig;

    public TwitchAccessToken(){

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }
}
