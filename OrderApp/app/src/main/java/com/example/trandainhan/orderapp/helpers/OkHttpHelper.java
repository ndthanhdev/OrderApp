package com.example.trandainhan.orderapp.helpers;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by duyth on 5/10/2017.
 */

public class OkHttpHelper {

    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    public static String get(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;
        response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String post(String url, Object src) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MEDIA_TYPE, GsonHelper.toJson(src));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
