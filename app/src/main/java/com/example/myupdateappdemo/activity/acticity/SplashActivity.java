package com.example.myupdateappdemo.activity.acticity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.myupdateappdemo.R;
import com.example.myupdateappdemo.activity.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 亮亮 on 2017/7/25.
 */

public class SplashActivity extends BaseActivity {
    private static final int STARTCOUNT = 10;
    private static final int COUNT = 11;
    private Thread thread;
    private Timer timer;
    int time = 0;
    int count = 0;
    private Handler hander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case STARTCOUNT:
                    hander.sendEmptyMessageDelayed(STARTCOUNT, 1000);
                    count += 1000;
                    if (count >=time) {
                        hander.removeMessages(STARTCOUNT);
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    }
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        useTimer(3000);
//        useWhile(3000);
        useHandler(3000);

    }

    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
    }

    //方法一
    public void useHandler(int time) {
        this.time=time;
        hander.sendEmptyMessage(STARTCOUNT);

    }

    //方法二
    private void useWhile(final int time) {
        //睡眠1秒
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (count < time) {

                    //睡眠1秒
                    SystemClock.sleep(1000);
                    count += 1000;

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (count != 4000) {
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        }
                    }
                });

            }
        });
        thread.start();
    }

    //方法三
    void useTimer(final int time) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                //设置定时器
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (count < time) {
                            count += 1000;
                        } else {
                            //停止
                            timer.cancel();
                            timer.purge();
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        }
                    }
                }, 0, 1000);
            }
        }).start();
    }

    public void skip(View view) {
        //避免点击跳转与自动跳转冲突
        hander.removeMessages(STARTCOUNT);
//        timer.cancel();
//        timer.purge();
//        count = 4000;
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
    }

}
