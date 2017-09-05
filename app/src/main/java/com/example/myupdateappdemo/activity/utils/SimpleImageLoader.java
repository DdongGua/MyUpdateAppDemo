package com.example.myupdateappdemo.activity.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.widget.Toast;

import com.example.myupdateappdemo.activity.app.App;
import com.example.myupdateappdemo.activity.http.OkHttpHelper;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * Created by 亮亮 on 2017/8/10.
 */

public class SimpleImageLoader {
    private static SimpleImageLoader simpleImageLoader = new SimpleImageLoader();
    private LruCache<String, Bitmap> mLrucache;
    private DiskLruCache mDiskLruCache;

    private SimpleImageLoader() {
        //一般定义为android虚拟机的内存的1/8
        int i = (int) ((Runtime.getRuntime().maxMemory() / 8));
        //初始化内存缓存
        mLrucache = new LruCache<String, Bitmap>(i) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
        String s = Environment.getExternalStorageDirectory() + "/howto";
        File file = new File(s);
        //初始化本地缓存
        try {
            mDiskLruCache = DiskLruCache.open(file, App.versionCode, 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //md5加密
    public String md5(String url) {
        return "hehe";
    }

    public static SimpleImageLoader getInstance() {
        return simpleImageLoader;
    }

    //缓存图片
    public void save(final Activity act, final String url) {
        OkHttpHelper.getInstance().getStream(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(act, "图片缓存失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[] bytes = response.body().bytes();
                saveToMemory(act,url,bytes);
                saveToDisk(url,bytes);
            }
        });

    }

    //存到本地
    public void saveToDisk(String url, byte[] bytes) throws IOException {
        DiskLruCache.Editor edit = mDiskLruCache.edit(md5(url));
        OutputStream out = edit.newOutputStream(0);
        out.write(bytes);
        edit.commit();

    }

    public void saveToMemory(final Activity act, String url, byte[] bytes) throws IOException {
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        if (mLrucache.get(md5(url)) == null) {
            mLrucache.put(md5(url), bitmap);
            act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(act, "图片缓存成功", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(act, "图片已缓存", Toast.LENGTH_SHORT).show();
                }
            });

        }


    }

    public Bitmap getBitmapFromDisk(String url) throws IOException {
        Bitmap bitmap = null;
        DiskLruCache.Snapshot snapshot = mDiskLruCache.get(md5(url));
        if (snapshot != null) {
            InputStream inputStream = snapshot.getInputStream(0);
            bitmap = BitmapFactory.decodeStream(inputStream);


        }
        return bitmap;
    }

    //取图片
    public Bitmap getBitmapFromMemory(String url) {
        Bitmap bitmap;
        String s = md5(url);
        if (mLrucache != null && mLrucache.size() > 0) {
            bitmap = mLrucache.get(s);
        } else {
            bitmap = null;
        }
        return bitmap;
    }

    public boolean removeFromMemory(String url) {
        Bitmap remove = null;
        String s = md5(url);
        if (mLrucache != null && mLrucache.size() > 0) {
            remove = mLrucache.remove(s);
        }
        if (remove != null) {
            return true;
        } else {
            return false;
        }
    }

    public void removeFromDisk(String url) throws IOException {
        String s = md5(url);
        if (mDiskLruCache != null) {
            mDiskLruCache.remove(s);
        }
    }

    public void deleteDiskCache() throws IOException {
        long size = mDiskLruCache.size();
        mDiskLruCache.delete();
    }
}
