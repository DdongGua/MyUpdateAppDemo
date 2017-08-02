package com.example.myupdateappdemo.activity.acticity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myupdateappdemo.R;
import com.example.myupdateappdemo.activity.Constants.HttpConstants;
import com.example.myupdateappdemo.activity.adapter.GuideAdapter;
import com.example.myupdateappdemo.activity.base.BaseActivity;
import com.example.myupdateappdemo.activity.bean.GuideBean;
import com.example.myupdateappdemo.activity.httputils.BaseCallBack;
import com.example.myupdateappdemo.activity.httputils.OkHttpUtils;
import com.example.myupdateappdemo.activity.model.progress.ProgressImageView;
import com.example.myupdateappdemo.activity.model.progress.ProgressModelLoader;
import com.example.myupdateappdemo.activity.utils.SpUtils;
import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by 亮亮 on 2017/7/24.
 */

public class GuideActivity extends BaseActivity implements View.OnClickListener {
    private ArrayList<String> guidepic;
    private ArrayList<ProgressImageView> lists = new ArrayList<>();
    private ViewPager vp;
    private LinearLayout ll;
    private Button bt;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        if ((boolean) SpUtils.getInstance(this, "config").getSp("isfirst", false)) {
            startActivity(new Intent(GuideActivity.this, SplashActivity.class));
        }
        initData();
        initView();
        initEvent();
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
    }

    private void initEvent() {
        bt.setOnClickListener(this);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                resetCirlceIndicater();
                ImageView iv = (ImageView) ll.getChildAt(position);
                iv.setImageResource(R.drawable.seleted);
                if (position == guidepic.size() - 1) {
                    ll.setVisibility(View.INVISIBLE);
                    bt.setVisibility(View.VISIBLE);
                    Animation animation = AnimationUtils.loadAnimation(GuideActivity.this, R.anim.enter_from_button);
                    animation.setInterpolator(new OvershootInterpolator());
                    bt.setAnimation(animation);
                }
            }

            private void resetCirlceIndicater() {
                for (int i = 0; i < ll.getChildCount(); i++) {
                    ((ImageView) ll.getChildAt(i)).setImageResource(R.drawable.normal);
                }
                bt.setVisibility(View.GONE);
                ll.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        vp = (ViewPager) findViewById(R.id.vp);
        ll = (LinearLayout) findViewById(R.id.ll);
        bt = (Button) findViewById(R.id.bt);
        bt.setVisibility(View.GONE);
        vp.setOffscreenPageLimit(0);
    }

    private void initData() {
        final Request req = new Request.Builder()
                .get().url(HttpConstants.guide).
                        build();
        OkHttpUtils.getInstance().get(HttpConstants.guide, null, new BaseCallBack() {
            @Override
            public void onError() {

            }

            @Override
            public void onSuccess(okhttp3.Response response) {
                String json = null;
                try {
                    json = response.body().string();
                    Gson gson = new Gson();
                    GuideBean guideBean = gson.fromJson(json, GuideBean.class);
                    if (guideBean != null) {
                        if (200 == guideBean.getStatus()) {
                            guidepic = (ArrayList<String>) guideBean.getData().getGuidepic();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateFace();

                                }
                            });

                        }


                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed() {

            }
        });


    }

    private void updateFace() {
        for (String str : guidepic) {
            ProgressImageView progressImageView = (ProgressImageView) LayoutInflater.from(GuideActivity.this).inflate(R.layout.progressimageview, null, false);
            ImageView imageView = progressImageView.getImageView();
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(GuideActivity.this).
                    using(new ProgressModelLoader(new ProgressHandler(GuideActivity.this, progressImageView))).
                    load(str).
                    diskCacheStrategy(DiskCacheStrategy.NONE).
                    placeholder(R.drawable.loading).into(imageView);
            lists.add(progressImageView);

            //底部小圆点
            ImageView iv = new ImageView(GuideActivity.this);
            iv.setImageResource(R.drawable.normal);
            LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(50, 50);
            layoutparams.setMargins(10, 0, 10, 0);
            iv.setLayoutParams(layoutparams);
            ll.addView(iv);


        }
        ImageView firstiv = (ImageView) ll.getChildAt(0);
        firstiv.setImageResource(R.drawable.seleted);
        GuideAdapter guideadapter = new GuideAdapter(GuideActivity.this, lists);
        vp.setAdapter(guideadapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt:
                SpUtils.getInstance(this, "config").putSP("isfirst", true);
                startActivity(new Intent(GuideActivity.this, SplashActivity.class));
                break;
            default:
                break;
        }
    }

    private static class ProgressHandler extends Handler {

        private final WeakReference<Activity> mActivity;
        private final ProgressImageView mProgressImageView;

        public ProgressHandler(Activity activity, ProgressImageView progressImageView) {
            super(Looper.getMainLooper());
            mActivity = new WeakReference<>(activity);
            mProgressImageView = progressImageView;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final Activity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case 1:
                        int percent = msg.arg1 * 100 / msg.arg2;
                        mProgressImageView.setProgress(percent);
                        if (percent >= 100) {
                            mProgressImageView.hideTextView();
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
