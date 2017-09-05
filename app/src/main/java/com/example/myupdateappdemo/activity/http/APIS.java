package com.example.myupdateappdemo.activity.http;

import com.example.myupdateappdemo.activity.bean.WaresBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by 亮亮 on 2017/8/7.
 */

public interface APIS {
    @GET("{wares}/hot?curPage=0&pageSize=10")
    Observable<WaresBean> getWare(@Path("wares") String wares);
}

