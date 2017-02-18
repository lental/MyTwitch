package com.example.mlen.mytwitch.model;

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


    @Override
    public String toString() {
        return "TwitchAccessToken{" +
                "token='" + token + '\'' +
                ", sig='" + sig + '\'' +
                '}';
    }
}
