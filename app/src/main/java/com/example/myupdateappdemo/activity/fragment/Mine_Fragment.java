package com.example.myupdateappdemo.activity.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.myupdateappdemo.R;
import com.example.myupdateappdemo.activity.base.BaseFragment;
import com.example.myupdateappdemo.activity.bean.WaresBean;
import com.example.myupdateappdemo.activity.event.InforEvent;
import com.example.myupdateappdemo.activity.http.RetrofitUtil;
import com.example.myupdateappdemo.activity.http.WaresComAPI;
import com.example.myupdateappdemo.activity.model.WaresHot;
import com.example.myupdateappdemo.activity.utils.SimpleImageLoader;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 亮亮 on 2017/7/27.
 */

public class Mine_Fragment  extends BaseFragment {

    private Button btn;
    private ImageView mimg;

    @Override
    public void initData() {
//        WaresHot.getWaresAPI22(new Callback<WaresBean>() {
//            @Override
//            public void onResponse(Call<WaresBean> call, Response<WaresBean> response) {
//                WaresBean body = response.body();
//                Log.e("2222222222",body.getCopyright());
//            }
//
//            @Override
//            public void onFailure(Call<WaresBean> call, Throwable t) {
//
//            }
//        });
        RetrofitUtil.getInstance().getWaresHot4(new Callback<WaresBean>() {
            @Override
            public void onResponse(Call<WaresBean> call, Response<WaresBean> response) {
                WaresBean body = response.body();
                Log.e("2222222222",body.getCopyright());
            }

            @Override
            public void onFailure(Call<WaresBean> call, Throwable t) {

            }
        });
//        Bitmap bitmap = SimpleImageLoader.getInstance().getBitmapFromMemory("https://n.sinaimg.cn/tech/crawl/20170226/yrbm-fyawhqy2124261.jpg");
//        if(bitmap!=null){
//            mimg.setImageBitmap(bitmap);
//        }
        try {
            Bitmap bitmapFromDisk = SimpleImageLoader.getInstance().getBitmapFromDisk("https://n.sinaimg.cn/tech/crawl/20170226/yrbm-fyawhqy2124261.jpg");
            if(bitmapFromDisk!=null){
                mimg.setImageBitmap(bitmapFromDisk);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View initView(LayoutInflater inflater) {
        View v=inflater.inflate(R.layout.mine_fragment,null,false);
        btn = (Button) v.findViewById(R.id.btn);
        mimg = (ImageView) v.findViewById(R.id.mimg);
        return v;
    }

}
