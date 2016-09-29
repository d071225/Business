package com.yisheng.business;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.yisheng.utils.L;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by DY on 2016/9/18.
 */
public class MyApplacation extends Application {

    private static Context context;
    private static Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);//Xutils初始化
        L.isDebug=false;
        context = getApplicationContext();
        mHandler =new Handler();
        L.e("初始化数据");
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);

    }
    public static Context getContext() {
        return context;
    }
    public static Handler getmHandler() {
        return mHandler;
    }
}
