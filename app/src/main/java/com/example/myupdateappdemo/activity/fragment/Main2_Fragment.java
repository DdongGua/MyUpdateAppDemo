package com.example.myupdateappdemo.activity.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.example.myupdateappdemo.R;
import com.example.myupdateappdemo.activity.Constants.HttpConstants;
import com.example.myupdateappdemo.activity.adapter.AdsAdapter;
import com.example.myupdateappdemo.activity.base.BaseFragment;
import com.example.myupdateappdemo.activity.bean.ADSBean;
import com.example.myupdateappdemo.activity.event.InforEvent;
import com.example.myupdateappdemo.activity.httputils.BaseCallBack;
import com.example.myupdateappdemo.activity.httputils.OkHttpUtils;
import com.example.myupdateappdemo.activity.model.GetAds;
import com.example.myupdateappdemo.activity.utils.SimpleImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by 亮亮 on 2017/7/27.
 */

public class Main2_Fragment extends BaseFragment implements View.OnClickListener, BaseSliderView.OnSliderClickListener {
    private static final String TAG = "Main2_Fragment";
    private Button btn;
    private ViewPager vp;
    private ArrayList<ImageView> imageViewLists = new ArrayList<>();
    private AdsAdapter adsadapter;
    private int currentPosition = 0;
    private SliderLayout sl;
    private RecyclerView rv;
    private TextView tv_event;


    @Override
    public void initData() {
        GetAds.getAds2(new BaseCallBack() {
            private List<ADSBean.DataBean.BannerBean> banner;

            @Override
            public void onError() {

            }

            @Override
            public void onSuccess(Response response) {
                try {
                    String json = response.body().string();
                    ADSBean adsBean = OkHttpUtils.getGson().fromJson(json, ADSBean.class);
                    banner = adsBean.getData().get(0).getBanner();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initSlider(banner);

                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed() {

            }
        });
        SimpleImageLoader.getInstance().save(getActivity(), "https://n.sinaimg.cn/tech/crawl/20170226/yrbm-fyawhqy2124261.jpg");

    }
    @Subscribe(threadMode = ThreadMode.ASYNC)//EventBus订阅模式
    public void onMessageEvent(InforEvent event) {
        String address = event.getAddress();
        String name = event.getName();
        int age = event.getAge();
        tv_event.setText(address+name+age);
    };

    private void initSlider(List<ADSBean.DataBean.BannerBean> banner) {
        for (ADSBean.DataBean.BannerBean bean : banner) {
            DefaultSliderView dsv = new DefaultSliderView(getActivity());

            dsv.image(HttpConstants.QINIU + bean.getImg())
                    .setScaleType(DefaultSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            Log.e("TAG", HttpConstants.QINIU);
            Log.e("TAG", bean.getImg());
            Bundle bundle = new Bundle();
            bundle.putString("tag", bean.getId());
            dsv.bundle(bundle);
            sl.addSlider(dsv);
            sl.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
            sl.setPresetTransformer(SliderLayout.Transformer.DepthPage);

        }


    }


    @Override
    public View initView(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.main2_fragment, null, false);
        ;
        sl = (SliderLayout) v.findViewById(R.id.slider);
        rv = (RecyclerView) v.findViewById(R.id.rv);
        tv_event = (TextView) v.findViewById(R.id.tv_event);


        return v;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.setAction("Android.intent.action.VIEW");
        Uri uri = Uri.parse("https://item.taobao.com/item.htm?spm=a217h.1721928.1998704851.2.4bc338d2fkfKwX&id=524309999816"); // 商品地址
        intent.setData(uri);
        intent.setClassName("com.taobao.taobao", "com.taobao.tao.detail.activity.DetailActivity");
        startActivity(intent);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            if (adsadapter != null) {
                int currentItem = vp.getCurrentItem();
                Log.e(TAG, "guajuonHiddenChanged: 隐藏 " + currentItem);
                adsadapter.stopLoop();
            }
        } else {
            if (adsadapter != null) {
                int currentItem = vp.getCurrentItem();
                Log.e(TAG, "guajuonHiddenChanged: 显示 " + currentItem);
                adsadapter.startPlay(vp);

            }
        }

    }

    public void onSliderClick(BaseSliderView slider) {
        Bundle bundle = slider.getBundle();
        String tag = bundle.getString("tag");
        Log.e(TAG, "onSliderClick: " + tag);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}
