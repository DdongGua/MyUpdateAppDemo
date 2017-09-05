package com.example.myupdateappdemo.activity.http;

import com.example.myupdateappdemo.activity.bean.WaresBean;

/**
 * Created by 亮亮 on 2017/8/7.
 */

public abstract class MyTask implements Runnable {
    public WaresBean waresBean;

    public WaresBean getWaresBean() {
        return waresBean;
    }

    public void setWaresBean(WaresBean waresBean) {
        this.waresBean = waresBean;
    }
}
