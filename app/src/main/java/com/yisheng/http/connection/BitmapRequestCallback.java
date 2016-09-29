package com.yisheng.http.connection;

import android.graphics.Bitmap;


public interface BitmapRequestCallback 
{
	
	/**
	 * 接收网络层返回的数据的回调接口函数
	 * @param data 需要解析处理的数据
	 */
	public void onReceiveData(Bitmap bitmap);
}
