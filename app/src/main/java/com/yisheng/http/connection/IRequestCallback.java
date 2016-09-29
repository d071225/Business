package com.yisheng.http.connection;

/******************************************************************
 * @文件名称 : IRequestCallback.java
 * @文件描述 : 返回请求数据
 ******************************************************************/

public interface IRequestCallback 
{
	
	/**
	 * 接收网络层返回的数据的回调接口函数
	 * @param data 需要解析处理的数据
	 */
	public void onReceiveData(String jsonStr);
}
