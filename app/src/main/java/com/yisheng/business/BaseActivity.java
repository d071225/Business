package com.yisheng.business;

import android.app.Activity;
import android.os.Bundle;

import com.yisheng.view.LoadingDialog;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by DY on 2016/9/14.
 */
public abstract class BaseActivity extends Activity{

    protected LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog = new LoadingDialog(this);
        initView();
        initData();
    }

    protected abstract void initData();

    protected abstract void initView();

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }
}
