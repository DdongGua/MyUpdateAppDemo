package com.example.myupdateappdemo.activity.http;

import com.example.myupdateappdemo.activity.Constants.HttpConstants;
import com.example.myupdateappdemo.activity.bean.WaresBean;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 亮亮 on 2017/8/4.
 */

public class RetrofitUtil {//retrofit工具类
    private static RetrofitUtil retrofitUtil;
    private static Retrofit retrofit;
    private final WaresComAPI waresComAPI;

    private RetrofitUtil(){
        waresComAPI = retrofit.create(WaresComAPI.class);
    }
    //只要类被调用，就会执行此方法
    static {//静态代码块
        //去初始化retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(HttpConstants.API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitUtil=new RetrofitUtil();
    }
    public void getWaresHot1(Callback<WaresBean> callback){
        Call<WaresBean> call = waresComAPI.getWaresHostAPI();
        call.enqueue(callback);
    }
    public void getWaresHot2(Callback<WaresBean> callback){
        Call<WaresBean> call = waresComAPI.getWaresHostAPI("wares");
        call.enqueue(callback);
    }
    public void getWaresHot3(Callback<WaresBean> callback){
        Call<WaresBean> call = waresComAPI.getWaresHostAPI21("0","10");
        call.enqueue(callback);
    }
    public void getWaresHot4(Callback<WaresBean> callback){
        HashMap<String,String> map=new HashMap<>();
        map.put("curPage","0");
        map.put("pageSize","10");
        Call<WaresBean> call = waresComAPI.getWaresHostAPI22(map);
        call.enqueue(callback);
    }
    public static RetrofitUtil getInstance(){
        return retrofitUtil;
    }
}
