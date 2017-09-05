package com.example.myupdateappdemo.activity.model;

import com.example.myupdateappdemo.activity.Constants.HttpConstants;
import com.example.myupdateappdemo.activity.bean.WaresBean;
import com.example.myupdateappdemo.activity.http.WaresComAPI;
import com.example.myupdateappdemo.activity.httputils.BaseCallBack;
import com.example.myupdateappdemo.activity.httputils.OkHttpUtils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 亮亮 on 2017/8/1.
 */

public class WaresHot {
    public  static void getWaresHot(BaseCallBack callBack){
        HashMap<String, String> map = new HashMap<>();
        map.put("curPage","0");
        map.put("pageSize","10");
        OkHttpUtils instance = OkHttpUtils.getInstance();
        instance.get(HttpConstants.API.WARES_HOT,map,callBack);
    }
    public static void postWaresHot(BaseCallBack callBack){
        HashMap<String, String> map = new HashMap<>();
        map.put("curPage","0");
        map.put("pageSize","10");
        OkHttpUtils instance = OkHttpUtils.getInstance();
        instance.postData(HttpConstants.API.WARES_HOT,map,callBack);
    }
    public static void getWaresAPI(Callback<WaresBean> callback){
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(HttpConstants.API.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                //GsonConverterFactory将返回结果转化为javaBean
                build();
        WaresComAPI war = retrofit.create(WaresComAPI.class);
        Call<WaresBean> call=war.getWaresHostAPI("wares");
        call.enqueue(callback);

    }
    public static void getWaresAPI2(Callback<WaresBean> callback) {
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(HttpConstants.API.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        WaresComAPI war = retrofit.create(WaresComAPI.class);
        Call<WaresBean> call = war.getWaresHostAPI();
        call.enqueue(callback);
    }
    public static void getWaresAPI21(Callback<WaresBean> callback){
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(HttpConstants.API.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        WaresComAPI war = retrofit.create(WaresComAPI.class);
        Call<WaresBean> call=war.getWaresHostAPI21("0","10");
        call.enqueue(callback);

    }
    public static void getWaresAPI22(Callback<WaresBean> callback){
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(HttpConstants.API.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        WaresComAPI war = retrofit.create(WaresComAPI.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("curPage","0");
        map.put("pageSize","10");
        Call<WaresBean> call=war.getWaresHostAPI22(map);
        call.enqueue(callback);

    }
}
