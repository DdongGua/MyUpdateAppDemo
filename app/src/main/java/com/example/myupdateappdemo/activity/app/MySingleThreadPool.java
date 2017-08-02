package com.example.myupdateappdemo.activity.app;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 亮亮 on 2017/7/29.
 */

public class MySingleThreadPool {
    private static MySingleThreadPool myPool = new MySingleThreadPool();
    private static ExecutorService threadPool;

    private MySingleThreadPool() {
        threadPool = Executors.newSingleThreadExecutor();
    }

    public static ExecutorService getInstance() {
        return threadPool;
    }
}
