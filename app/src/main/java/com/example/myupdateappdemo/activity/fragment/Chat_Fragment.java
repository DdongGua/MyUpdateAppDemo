package com.example.myupdateappdemo.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;


import com.example.myupdateappdemo.R;
import com.example.myupdateappdemo.activity.base.BaseFragment;
import com.example.myupdateappdemo.activity.bean.WaresBean;
import com.example.myupdateappdemo.activity.http.HttpHelper;
import com.example.myupdateappdemo.activity.http.MyTask;
import com.example.myupdateappdemo.activity.http.RetrofitUtil;


import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;



/**
 * Created by 亮亮 on 2017/7/27.
 */

/*
rxjava
是一个使用了观察序列的，能够实现基于事件的异步处理效果
跨线程操作
handler
asynctask
okhttp /volley /retrofit
runOnUiThread
 */

public class Chat_Fragment extends BaseFragment {

    private static final String TAG = "ChatFragment";
    private Observer<WaresBean> observer;
    private Observable<WaresBean> observable;

    @Override
    public void initData() {
//        initRxJava2();
        HttpHelper.getInstance().getWareHot3(new MyTask() {
            @Override
            public void run() {
                Log.e(TAG, "run: "+this.getWaresBean().getCopyright());
            }
        });

    }
    private void initRxJava2() {
        Observable.just("hehe").
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(a-> Log.e(TAG, "initRxJava2: "+a));


    }

    private void initRxJava() {
        //1、创建一个oberver观察者
        //2、创建一个被观察者实例
        //3、让被观察者去添加订阅者

        //1 创建一个观察者
        observer = new Observer<WaresBean>() {
            @Override
            public void onCompleted() {
                //控制进度条
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(WaresBean s) {
                 //做获取数据的处理
                Log.e(TAG, "onNext: "+s.getCopyright() );
            }
        };
        //2创建一个被观察者
         observable = Observable.create(new Observable.OnSubscribe<WaresBean>(){


              @Override
              public void call(final Subscriber<? super WaresBean> subscriber) {
                  //添加数据
                  getActivity().runOnUiThread(new Runnable() {
                      @Override
                      public void run() {
                          RetrofitUtil.getInstance().getWaresHot1(new retrofit2.Callback<WaresBean>() {
                              @Override
                              public void onResponse(Call<WaresBean> call, Response<WaresBean> response) {
                                  WaresBean body = response.body();
                                  subscriber.onNext(body);

                              }

                              @Override
                              public void onFailure(Call<WaresBean> call, Throwable t) {

                              }
                          });

                      }
                  });
              }
          });
        //3添加订阅者,联网获取数据
        observable.subscribeOn(Schedulers.io());//设置在子线程完成 subscribe 操作
        observable.observeOn(AndroidSchedulers.mainThread());//设置在哪完成observe操作
        observable.subscribe(observer);

            }

    @Override
    public View initView(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.chat_fragment, null, false);
        return v;
    }
}
