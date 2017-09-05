package com.example.myupdateappdemo.activity.model;

import android.text.TextUtils;

import com.example.myupdateappdemo.activity.Constants.HttpConstants;
import com.example.myupdateappdemo.activity.bean.GuideBean;
import com.example.myupdateappdemo.activity.httputils.BaseCallBack;
import com.example.myupdateappdemo.activity.httputils.OkHttpUtils;
import com.google.gson.Gson;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by 亮亮 on 2017/7/28.
 */

public abstract class GetAds {

    private Call call;

    public  void getAds(String url) {
        OkHttpUtils.getInstance().get(url, null, new BaseCallBack() {
            @Override
            public void onError() {

            }

            @Override
            public void onSuccess(Response response) {
                if (response.isSuccessful()) {
                    try {
                        String json = response.body().string();
                        if (TextUtils.isEmpty(json)) {
                            //做没有数据的处理
                        } else {
                            Gson gson = new Gson();
                            GuideBean guideBean = gson.fromJson(json, GuideBean.class);
                            ArrayList<String> guidepic = (ArrayList<String>) guideBean.getData().getGuidepic();
                            showAds(guidepic);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException("广告轮播下载出现异常");
                    }

                }
            }

            @Override
            public void onFailed() {

            }
        });
    }
    public static  void getAds2(BaseCallBack callBack){
        OkHttpUtils.getInstance().postData(HttpConstants.ADS,null,callBack);

    }



    public abstract void showAds(ArrayList<String> guidepic);

}
