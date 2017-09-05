package com.example.myupdateappdemo.activity.http;

import android.os.Handler;
import android.os.Looper;

import com.example.myupdateappdemo.activity.Constants.HttpConstants;
import com.example.myupdateappdemo.activity.bean.WaresBean;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.HTTP;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by 亮亮 on 2017/8/7.
 */

public class HttpHelper {
    private static volatile HttpHelper httpHelper;
    private static Retrofit retrofit;
    private final APIS apis;

    private HttpHelper() {
         retrofit = new Retrofit.Builder().
                baseUrl(HttpConstants.API.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                build();
        apis = retrofit.create(APIS.class);


    }

    public static HttpHelper getInstance() {
        if (httpHelper == null) {
            synchronized (HttpHelper.class) {
                if (httpHelper == null) {
                    httpHelper = new HttpHelper();
                }
            }
        }
        return httpHelper;
    }

    public void getWareHot3(MyTask runnable) {
        Observable<WaresBean> wares = apis.getWare("wares");
        wares.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WaresBean>() {
                    @Override
                    public void call(WaresBean waresBean) {
                        Handler handler = new Handler(Looper.getMainLooper());
                        runnable.setWaresBean(waresBean);
                        handler.post(runnable);
                    }
                });

    }
}
