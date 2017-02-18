package com.example.mlen.mytwitch.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by mlen on 2/17/17.
 */

public class StreamsRequest {
    public Stream[] streams;

    public Stream[] getStreams() {
        return streams;
    }

    public void setStreams(Stream[] streams) {
        this.streams = streams;
    }
}
