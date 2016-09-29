package com.yisheng.http.connection;

import android.content.Context;

import com.yisheng.http.threadpool.TaskHandle;
import com.yisheng.http.threadpool.TaskQueue;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class BaseRequestTask extends ConnectionTask
{
	/**
	 * 最大任务请求数
	 */
	private static final int maxCount = 3;

	/**
	 * 连接队列
	 */
	private static TaskQueue requestQueue;

	/**
	 * 连接服务器构造方法
	 * @param handler    JSON或bitmap回调接口   
	 * @param httpUrl    连接地址
	 */
	public BaseRequestTask(IHttpCallback callback, String httpUrl, Context context)
	{
		super(callback, context);
		this.httpUrl = httpUrl;
		if (requestQueue == null)
		{
			requestQueue = new TaskQueue(maxCount);
		}
	}

	/**
	 * 读网络数据
	 * @throws IOException             IO异常类
	 * @throws InterruptedException    中断异常类
	 */
	@Override
	protected void readResponseData(HttpResponse response) throws Exception,
			IOException, InterruptedException, UnsupportedEncodingException,
			JSONException
	{
		super.readResponseData(response);
		HttpEntity entity = response.getEntity();
		String jsonStr = EntityUtils.toString(entity);
		((IRequestCallback) httpCallback).onReceiveData(jsonStr);
	}

	public  void doPost() throws Exception, Error
	{
		try
		{
			request = new HttpPost(httpUrl);
            if (paramsters != null)
			{
				HttpEntity entity = new UrlEncodedFormEntity(paramsters,
						"UTF-8");
				((HttpPost) request).setEntity(entity);
			}		
				client.getParams().setIntParameter(
						HttpConnectionParams.SO_TIMEOUT, timeout);
				client.getParams().setIntParameter(
						HttpConnectionParams.CONNECTION_TIMEOUT, timeout);
			HttpResponse response = client.execute(request);
			responseCode = response.getStatusLine().getStatusCode();
			switch (responseCode)
			{
			case HttpStatus.SC_OK:
				readResponseData(response);
				break;
			case HttpStatus.SC_PARTIAL_CONTENT:
				readResponseData(response);
				break;
			case HttpStatus.SC_BAD_REQUEST:
				readErrorResponse(response);
				break;
			case HttpStatus.SC_SERVICE_UNAVAILABLE:
				throw new InterruptedException("Connection busy");
			case HttpStatus.SC_BAD_GATEWAY:
			case HttpStatus.SC_FORBIDDEN:
			case HttpStatus.SC_INTERNAL_SERVER_ERROR:
				throw new InterruptedException("Connection bad request");
			default:
				throw new IOException("Connection response error:"
						+ responseCode);
			}

			if (canceled || paused || isTimeOut)
			{
				throw new InterruptedException();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			setConnError(e.getMessage());
		}
		finally
		{
			clearNet();
		}
	}

	
	
	/**
	 * 发送请求
	 */
	@Override
	public TaskHandle sendTaskReq()
	{

		TaskHandle handle = null;
		if (requestQueue != null)
		{
			handle = requestQueue.addTask(this);
		}
		return handle;
	}
}

