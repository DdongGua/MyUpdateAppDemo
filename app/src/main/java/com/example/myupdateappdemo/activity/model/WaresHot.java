package com.example.myupdateappdemo.activity.model;

import com.example.myupdateappdemo.activity.Constants.HttpConstants;
import com.example.myupdateappdemo.activity.httputils.BaseCallBack;
import com.example.myupdateappdemo.activity.httputils.OkHttpUtils;

import java.util.HashMap;

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
}
