package com.example.myupdateappdemo.activity.http;

import android.app.Activity;
import android.text.TextUtils;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by 亮亮 on 2017/8/10.
 */

public class OkHttpHelper {
    private static OkHttpHelper okHttpHelper = new OkHttpHelper();
    public static OkHttpClient okHttpClient;

    private OkHttpHelper() {
        okHttpClient = new OkHttpClient();
    }

    public static OkHttpHelper getInstance() {
        return okHttpHelper;
    }

    public void getStream(String url, Callback callback) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);


    }
}
