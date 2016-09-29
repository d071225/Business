package com.yisheng.http.connection;

/******************************************************************
 * @文件名称 : SysRecObserver.java
 * @文件描述 : 广播观察者接口
 ******************************************************************/

public interface SysRecObserver
{
	/**   
	* 当被观察的对象发生变化时，这个方法会被调用。   
	*/
	void update(Object arg);
}
