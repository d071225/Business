package com.yisheng.ui.login;

import android.content.Intent;
import android.os.Handler;

import com.yisheng.business.BaseActivity;
import com.yisheng.business.MainActivity;
import com.yisheng.business.MyApplacation;
import com.yisheng.business.R;
import com.yisheng.utils.L;
import com.yisheng.utils.SPUtils;


public class SplashActivity extends BaseActivity {
	private final int SPLASH_DISPLAY_LENGHT = 1000; // 延迟1秒

	private boolean isFirstLogin = false;


	@Override
	protected void initData() {

	}

	@Override
	protected void initView() {
		setContentView(R.layout.splash);
//		ConnectionConstant.DEBUG=false;
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				L.e("token值====》"+SPUtils.get(MyApplacation.getContext(), "token", ""));
				Intent intent=null;
				if (SPUtils.get(MyApplacation.getContext(), "token", "")==null|SPUtils.get(MyApplacation.getContext(), "token", "").equals("")) {
					intent = new Intent(SplashActivity.this,
							LoginActivity.class);
				} else {
						intent = new Intent(SplashActivity.this,
						MainActivity.class);
				}
				startActivity(intent);
				finish();
			}
		}, SPLASH_DISPLAY_LENGHT);
	}

}
