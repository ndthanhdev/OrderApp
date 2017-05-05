package com.example.trandainhan.orderapp.tools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by duyth on 5/5/2017.
 */

public class GsonHelper {

    static Gson gson;

    public Gson getGson(){
        if (gson==null)
            gson = new Gson();
        return gson;
    }
}
