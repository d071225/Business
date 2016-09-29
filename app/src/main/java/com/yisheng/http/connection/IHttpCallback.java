package com.yisheng.http.connection;

/******************************************************************
 * @文件名称 : IHttpCallback.java
 * @文件描述 : 网络回调接口
 ******************************************************************/

public interface IHttpCallback 
{

	/**
	 * 其他错误回调函数
	 * @param code     状态码
	 * @param message  错误消息
	 */
	public void onError(int code, String message);
	
	/**
	 * 超时处理
	 * @param code    状态码
	 * @param message 异常信息
	 */
	public void onTimeOut(int code, String message);
	
	/**
	 * 网络不可用 
	 * @param code    状态码
	 * @param message 异常信息 
	 */
	public void onConnError(int code, String message);
}
