package com.yisheng.http.connection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.yisheng.http.threadpool.TaskHandle;
import com.yisheng.http.threadpool.TaskObject;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/******************************************************************
 * @文件名称 : ConnectionTask.java
 * @文件描述 : 网络连接抽象基类
 ******************************************************************/

public abstract class ConnectionTask implements TaskObject, SysRecObserver {
	/**
	 * 代理地址
	 */
	public static String hostUrl = "10.0.0.172";

	/**
	 * 代理端口
	 */
	public static int hostPort = 80;

	/**
	 * 发送请求
	 */
	public abstract TaskHandle sendTaskReq();

	/**
	 * post请求定义
	 */
	public static final int POST = 0;

	/**
	 * get请求定义
	 */
	public static final int GET = 1;

	/**
	 * 是否已经超时
	 */
	protected boolean isTimeOut = false;

	/**
	 * 是否已经被取消的标志
	 */
	protected boolean canceled = false;

	/**
	 * 是否已被暂停的标志
	 */
	protected boolean paused = false;

	/**
	 * 是否需要往上层抛出消息
	 */
	private boolean isNeedSendMess = true;

	public void setNeedSendMess(boolean isNeedSendMess) {
		this.isNeedSendMess = isNeedSendMess;
	}

	/**
	 * 请求类型，默认为POST
	 */
	protected int requestType = POST;

	/**
	 * 超时定时器
	 */
	protected Timer timer;

	/**
	 * 超时定时器任务
	 */
	protected TimerTask timerTask;

	/**
	 * 超时时间  60
	 */
	protected int timeout = 1000 * 1000;

	/**
	 * url地址
	 */
	protected String httpUrl;

	/**
	 * 返回码
	 */
	protected int responseCode;

	/**
	 * 上下文
	 */
	private Context mContext;

	public List<NameValuePair> paramsters = null;

	/**
	 * 网络响应监听接口
	 */
	protected IHttpCallback httpCallback;

	protected DefaultHttpClient client = new DefaultHttpClient();

	protected HttpRequestBase request = null;

	/**
	 * 默认构造方法
	 */
	public ConnectionTask(IHttpCallback httpCallback, Context context) {
		this.httpCallback = httpCallback;
		this.mContext = context;
	}

	/**
	 * 判断用户是否已删除下载任务
	 * 
	 * @return 是否删除标志
	 */
	public boolean isCanceled() {
		return canceled;
	}

	/**
	 * 暂停连接任务
	 */
	public void pausedConnect() {
		paused = true;
	}

	/**
	 * 判断用户是否已暂停下载
	 * 
	 * @return 是否暂停下载标志
	 */
	public boolean isPaused() {
		return paused;
	}

	protected void doTask() throws Exception, Error {
		if (canceled || paused) {
			throw new InterruptedException();
		}
		try {
			if (isNeedProxy(mContext)) {
				HttpHost proxy = new HttpHost(hostUrl, hostPort);
				client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
						proxy);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		doPost();
	}

	/**
	 * SD 卡监听接口
	 * 
	 * @param arg
	 *            系统通知的action此类需要监听sd的插拔
	 */
	public void update(Object arg) {
		clearNet();
	}

	/**
	 * 运行联网任务
	 */
	public void runTask() {
		try {
			doTask();
		} catch (SecurityException se) {
			hanlderException(se);
		} catch (InterruptedIOException e) {
			handlerInterruptedIOException(e);
		} catch (InterruptedException e) {
			handlerInterruptedException(e);
		} catch (SocketException e) {
			// 无网络时会抛出该异常
			hanlderException(e);
		} catch (UnsupportedEncodingException e) {
			setConnError(e.getMessage());
		} catch (JSONException e) {
			setConnError(e.getMessage());
		} catch (IOException e) {
			// 服务器响应异常会抛出该异常
			hanlderException(e);
		} catch (Exception e) {
			// 其他异常
			hanlderException(e);
		} catch (Error e) {

			if (!isTimeOut) {
				setError(e.toString());
			} else {
				setConnError(e.toString());
			}
		} finally {
			clearNet();
		}
	}

	public abstract void doPost() throws Exception, Error;

	/**
	 * 服务器参数错误处理
	 * 
	 * @param response
	 * @throws RuntimeException
	 * @throws IOException
	 */
	protected void readErrorResponse(HttpResponse response)
			throws InterruptedException, JSONException, Exception {
		if (paused || canceled) {
			return;
		}
		// 请求失败
		setError(response.getEntity().getContent().toString());
	}

	/**
	 * 读网络数据
	 * 
	 * @param response
	 * @throws JSONException
	 * @throws Exception
	 *             异常类
	 */
	protected void readResponseData(HttpResponse response)
			throws JSONException, Exception {
		if (paused || canceled) {
			return;
		}
	}

	/**
	 * 异常处理
	 * 
	 * @param exception
	 *            异常对象
	 */
	private void hanlderException(Exception exception) {
		setConnError(exception.toString());
	}

	/**
	 * 处理InterruptedIOException异常
	 * 
	 * @param e
	 *            异常对象
	 */
	protected void handlerInterruptedIOException(Exception e) {
		// 在connetionProcess()方法执行中或读写流中或打开连接时，打断会抛此异常
		if (isTimeOut) {
			// do nothing
		} else {
			hanlderException(e);
		}
	}

	/**
	 * 关闭连接
	 */
	protected void clearNet() {

		try {
			if (request != null) {
				request.abort();
			}
		} catch (Exception e) {

		} finally {

		}
	}

	/**
	 * 无网络错误处理
	 * 
	 * @param responseCode
	 *            状态码
	 * @param exception
	 *            异常信息
	 */
	protected void setConnError(String exception) {
		if (canceled || paused) {
			return;
		}
		if (httpCallback != null && isNeedSendMess) {
			httpCallback.onConnError(responseCode, exception);
		}
	}

	/**
	 * 超时处理
	 * 
	 * @param responseCode
	 *            状态码
	 * @param exception
	 *            异常信息
	 */
	protected void setTimeOut(String exception) {
		if (canceled || paused) {
			return;
		}
		if (httpCallback != null && isNeedSendMess) {
			httpCallback.onTimeOut(responseCode, exception);
		}
	}

	/**
	 * 设置错误回调
	 */
	protected void setError(String exception) {
		if (httpCallback != null && isNeedSendMess) {
			httpCallback.onError(responseCode, exception);
		}
	}

	/**
	 * 设置请求类型
	 * 
	 * @param requestType
	 *            请求类型
	 */
	public void setRequestType(int requestType) {
		this.requestType = requestType;
	}

	/**
	 * 取消任务
	 */
	public void cancelTask() {
		canceled = true;
	}

	/**
	 * 任务取消的回调接口方法
	 */
	public void onCancelTask() {
		canceled = true;
		clearNet();
	}

	/**
	 * 设置定时器对象
	 * 
	 * @param timer
	 */
	public void setTimer(Timer timer) {
		if (timer != null) {
			this.timer = timer;
		}
	}

	/**
	 * 任务请求响应回调接口
	 * 
	 * @param code
	 *            响应通知码
	 */
	public void onTaskResponse(int code) {
		switch (code) {
		case TaskObject.RESPONSE_TIMEOUT_ONRUN:
		case TaskObject.RESPONSE_TIMEOUT_RUNNING:
			isTimeOut = true;
			setTimeOut("TIMEOUT");
			clearNet();
			break;
		case TaskObject.RESPONSE_SUCCESS:
			clearNet();
		default:
			break;
		}
	}

	/**
	 * 处理中断异常
	 * 
	 * @param exception
	 *            中断异常
	 */
	protected void handlerInterruptedException(InterruptedException e) {
		// 暂停取消时的打断异常
		if (canceled || paused) {
			hanlderException(e);
		} else {
			setError(e.getMessage());
		}
	}

	/**
	 * 设置任务的超时定时器任务对象
	 * 
	 * @param timeoutTask
	 *            定时器任务对象
	 */
	public void setTimeoutTask(TimerTask timeoutTask) {
		this.timerTask = timeoutTask;
	}

	/**
	 * 启动超时定时器
	 */
	public void startTimeoutTimer() {
		if (timer != null) {
			timer.schedule(timerTask, timeout);
		}
	}

	/**
	 * 停止超时定时器
	 */
	public void stopTimeoutTimer() {
		if (timerTask != null) {
			timerTask.cancel();
		}
	}

	/**
	 * 判断当前链接是否是用代理
	 * 
	 * @return true 使用 false不使用
	 */
	public static boolean isNeedProxy(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null) {
			String type = activeNetInfo.getTypeName();
			// WIFI 返回不使用
			if (type.equalsIgnoreCase("WIFI")) {
				return false;
			}
			// 移动网络 判断当前链接类型
			else 
				if (type.equalsIgnoreCase("MOBILE"))
				{
				if(null!=android.net.Proxy.getDefaultHost()&&-1!=android.net.Proxy.getDefaultPort())
				{
					hostUrl =android.net.Proxy.getDefaultHost();
					hostPort=android.net.Proxy.getDefaultPort();
					return true;
				}
				else
				{
					return false;
				}
//				String mobileProxyIp = "";
//				String mobileProxyPort = "";
//				Cursor c = context.getContentResolver().query(
//						Uri.parse("content://telephony/carriers/preferapn"),
//						null, null, null, null);
//				if (c != null) {
//					c.moveToFirst();
//					mobileProxyIp = c.getString(9);
//					mobileProxyPort = c.getString(10);
//					c.close();
//					// IP跟端口不为空
//					if ((null != mobileProxyIp && 0 < mobileProxyIp.length())
//							&& (null != mobileProxyPort && 0 < mobileProxyPort
//									.length())) {
//						hostUrl = mobileProxyIp;
//						try {
//							hostPort = Integer.parseInt(mobileProxyPort);
//						} catch (Exception e) {
//							
//							return false;
//						}
//						return true;
//					} else {
//						return false;
//					}
//				}
			}
		}
		return false;
	}

}
