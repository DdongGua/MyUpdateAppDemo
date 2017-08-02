package com.example.myupdateappdemo.activity.utils;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

/**
 * Created by 亮亮 on 2017/7/21.
 */

public class PackageUtils {
  public static String getCurrentVersion(Activity activity) throws PackageManager.NameNotFoundException {
      PackageManager pm=activity.getPackageManager();
      PackageInfo packageInfo=pm.getPackageInfo("com.example.myupdateappdemo",0);
      String versionName=packageInfo.versionName;
      if(TextUtils.isEmpty(versionName)){
          return "";
      }
      return versionName;
  }
}
