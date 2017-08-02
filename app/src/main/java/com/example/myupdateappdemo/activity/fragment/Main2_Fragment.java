package com.example.myupdateappdemo.activity.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myupdateappdemo.R;
import com.example.myupdateappdemo.activity.Constants.HttpConstants;
import com.example.myupdateappdemo.activity.adapter.AdsAdapter;
import com.example.myupdateappdemo.activity.base.BaseFragment;
import com.example.myupdateappdemo.activity.model.GetAds;

import java.util.ArrayList;

/**
 * Created by 亮亮 on 2017/7/27.
 */

public class Main2_Fragment extends BaseFragment implements View.OnClickListener {
    private Button btn;
    private ViewPager vp;
    private ArrayList<ImageView> imageViewLists = new ArrayList<>();
    private AdsAdapter adsadapter;
    private int currentPosition=0;

    @Override
    public void initData() {
        GetAds getAds = new GetAds() {
            @Override
            public void showAds(final ArrayList<String> guidepic) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (String str : guidepic) {
                            ImageView imageView = new ImageView(getActivity());
                            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            Glide.with(getActivity()).load(str).placeholder(R.mipmap.ic_launcher).into(imageView);
                            imageViewLists.add(imageView);
                            adsadapter = new AdsAdapter(getActivity(), imageViewLists);
                            vp.setAdapter(adsadapter);
                            vp.setCurrentItem(200);
                            adsadapter.startPlay(vp);
                            adsadapter.controlVP(vp);
                        }
                    }
                });
            }
        };
        getAds.getAds(HttpConstants.guide);

    }

    @Override
    public View initView(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.main_fragment, null, false);
//        btn= (Button) v.findViewById(R.id.bt);
        vp = (ViewPager) v.findViewById(R.id.vp);
        initEvent();
        return v;
    }

    private void initEvent() {

//        btn.setOnClickListener(this);
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
}
