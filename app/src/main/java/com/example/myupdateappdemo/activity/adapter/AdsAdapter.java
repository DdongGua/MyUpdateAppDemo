package com.example.myupdateappdemo.activity.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myupdateappdemo.activity.app.MySingleThreadPool;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 亮亮 on 2017/7/29.
 */

public class AdsAdapter extends PagerAdapter {

    private Activity act;
    private ArrayList<ImageView> list;
    Timer timer;
    int count = 0;
    private int currentPostion;
    private float oldX;
    private float newX;
    private int LEFT = 100;
    private int RIGHT = 101;
    private int direction = 0;

    public AdsAdapter(Activity act, ArrayList<ImageView> list) {
        this.act = act;
        this.list = list;

    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE / 5;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = list.get(position % (list.size()));
        if (imageView.getParent() != null) {
            ViewPager vp = (ViewPager) imageView.getParent();
            vp.removeView(imageView);
        }
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    public void startPlay(final ViewPager vp) {
        currentPostion = vp.getCurrentItem();
        MySingleThreadPool.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        act.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (direction == LEFT) {
                                    vp.setCurrentItem(currentPostion++);
                                } else if (direction == RIGHT) {
                                    vp.setCurrentItem(++currentPostion);
                                } else {
                                    vp.setCurrentItem(currentPostion++);
                                }
                            }
                        });
                    }
                }, 1000, 1000);
            }
        });

    }

    public void controlVP(final ViewPager vp) {
        oldX = 0;
        newX = 0;
        vp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    oldX = motionEvent.getX();
                    if (timer != null) {
                        timer.cancel();
                        timer = null;

                    }
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    newX = motionEvent.getX();
                    if (newX - oldX < 0) {
                        direction = RIGHT;
                    } else if (newX - oldX > 0) {
                        direction = LEFT;
                    }
                    startPlay(vp);
                }
                return false;
            }
        });
    }
}
