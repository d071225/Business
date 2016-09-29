package com.yisheng.http.connection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.yisheng.http.threadpool.TaskHandle;
import com.yisheng.http.threadpool.TaskQueue;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class BitmapTask extends ConnectionTask{

	/**
	 * 订购列表图标请求队列线程数
	 */
	private static final int MAX_ICON_REQ_COUNT = 5;

	/**
	 * 订购列表图标请求队列
	 */
	private static TaskQueue bitmapQueue;

	/**
	 * 请求图片构造方法
	 * @param handler     JSON或bitmap回调接口   
	 * @param httpUrl     连接地址
	 */
	public BitmapTask(IHttpCallback callback, String httpUrl,Context context)
	{
		super(callback,context);
		this.httpUrl = httpUrl;
		if (bitmapQueue == null)
		{
			bitmapQueue = new TaskQueue(MAX_ICON_REQ_COUNT);
		}
	}

	public static void cancelAllTask()
	{
		if (bitmapQueue != null)
		{
			bitmapQueue.terminateAllThread();
		}
	}
	
	public void doPost() throws Exception, Error {
		URL myFileUrl = new URL(httpUrl);
		Bitmap bitmap = null;
		try {
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
			((BitmapRequestCallback) httpCallback).onReceiveData(bitmap);
		} catch (IOException e) {
			((BitmapRequestCallback) httpCallback).onReceiveData(null);
		}
	}

	/**
	 * 发送请求
	 */
	@Override
	public TaskHandle sendTaskReq()
	{
		TaskHandle handle= null;
		if (bitmapQueue != null)
		{
			handle = bitmapQueue.addTask(this);
		}
		return handle;
	}

}
