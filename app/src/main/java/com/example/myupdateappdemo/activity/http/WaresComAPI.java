package com.example.myupdateappdemo.activity.http;

import com.example.myupdateappdemo.activity.bean.WaresBean;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import static android.R.attr.path;

/**
 * Created by 亮亮 on 2017/8/3.
 */

public interface WaresComAPI {//Retrofit
  @GET("{wares}/hot?curPage=0&pageSize=10")
    //返回值 方法名（参数）
  Call<WaresBean> getWaresHostAPI(@Path("wares") String wares);

  @GET("wares/hot?curPage=0&pageSize=10")
    //返回值 方法名（参数）
  Call<WaresBean> getWaresHostAPI();

  @GET("wares/hot")
  Call<WaresBean> getWaresHostAPI21(@Query("curPage") String page ,@Query("pageSize") String pagesize);

  @GET("wares/hot")
  Call<WaresBean> getWaresHostAPI22(@QueryMap HashMap<String ,String> parm );

}
