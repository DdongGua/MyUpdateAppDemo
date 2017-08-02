package com.example.myupdateappdemo.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myupdateappdemo.R;
import com.example.myupdateappdemo.activity.base.BaseFragment;

/**
 * Created by 亮亮 on 2017/7/27.
 */

public class Mine_Fragment  extends BaseFragment {

    @Override
    public void initData() {

    }

    @Override
    public View initView(LayoutInflater inflater) {
        View v=inflater.inflate(R.layout.mine_fragment,null,false);
        return v;
    }
}
