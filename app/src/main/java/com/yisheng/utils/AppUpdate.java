package com.yisheng.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 * 
 * 版本更新
 *
 */
public class AppUpdate {
	private Context context;
	protected String versionName;
	protected String downloadUrl;
	protected String changeLog;
	protected String forceUpdate;
	private ProgressDialog pBar;
	public AppUpdate(Context context,String versionName,String downloadUrl,String changeLog,String forceUpdate) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.versionName=versionName;
		this.downloadUrl=downloadUrl;
		this.changeLog=changeLog;
		this.forceUpdate=forceUpdate;
	}
	public void checkUpdate(){
			// 自动检查有没有新版本 如果有新版本就提示更新  
			new Thread() {
				public void run() {
					try {
						handler1.sendEmptyMessage(0);
					} catch (Exception e) {
						e.printStackTrace();
					}
				};
			}.start();
	   }
	@SuppressLint("HandlerLeak")
	private Handler handler1 = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:				
			   // 如果有更新就提示
			    if (isNeedUpdate()) {   //在下面的代码段
				showUpdateDialog();  //下面的代码段
			  }
			break;
			case 1:
			  pBar.cancel();
			  update();
			 break;
		}
		};
	};
		private void showUpdateDialog() {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setIcon(android.R.drawable.ic_dialog_info);
			builder.setTitle("请升级APP至版本" + versionName);
			builder.setMessage(changeLog);
			builder.setCancelable(false);

			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (Environment.getExternalStorageState().equals(
							Environment.MEDIA_MOUNTED)) {
						downFile(downloadUrl);     //在下面的代码段
					} else {
						Toast.makeText(context, "SD卡不可用，请插入SD卡",
								Toast.LENGTH_SHORT).show();
					}
				}
			});
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(forceUpdate.equals("1")){
//						context.finish();
						System.exit(0);
					}
				}

			});
			builder.create().show();
		}

		private boolean isNeedUpdate() {
			
			String v = versionName; // 最新版本的版本号
			if (v.equals(getVersion())) {
				return false;
			} else {
				return true;
			}
		}

		// 获取当前版本的版本号
		public String getVersion() {
			try {
				PackageManager packageManager = context.getPackageManager();
				PackageInfo packageInfo = packageManager.getPackageInfo(
						context.getPackageName(), 0);
				return packageInfo.versionName;
			} catch (NameNotFoundException e) {
				e.printStackTrace();
				return "版本号未知";
			}
		}
		void downFile(final String url) { 
			pBar = new ProgressDialog(context);    //进度条，在下载的时候实时更新进度，提高用户友好度
			pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pBar.setTitle("正在下载");
			pBar.setMessage("请稍候...");
			if(forceUpdate.equals("1")){
				pBar.setCancelable(false);
			}
			pBar.setProgress(0);
			pBar.show();
			new Thread() {
				public void run() {        
					HttpClient client = new DefaultHttpClient();
					HttpGet get = new HttpGet(url);
					HttpResponse response;
					try {
						response = client.execute(get);
						HttpEntity entity = response.getEntity();
						int length = (int) entity.getContentLength();   //获取文件大小
	                                        pBar.setMax(100);                            //设置进度条的总长度
						InputStream is = entity.getContent();
						FileOutputStream fileOutputStream = null;
						if (is != null) {
							File file = new File(
									Environment.getExternalStorageDirectory(),
									"yssf.apk");
							fileOutputStream = new FileOutputStream(file);
							byte[] buf = new byte[10];   //这个是缓冲区，即一次读取10个比特，我弄的小了点，因为在本地，所以数值太大一 下就下载完了，看不出progressbar的效果。
							int ch = -1;
							int process = 0;
							while ((ch = is.read(buf)) != -1) {       
								fileOutputStream.write(buf, 0, ch);
								process += ch;
								pBar.setProgress((int)process*100/length);       //这里就是关键的实时更新进度了！
							}

						}
						fileOutputStream.flush();
						if (fileOutputStream != null) {
							fileOutputStream.close();
						}
//						down();
						handler1.sendEmptyMessage(1);
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}.start();
		}

		void down() {
			
			handler1.post(new Runnable() {
				public void run() {
					pBar.cancel();
					update();
				}
			});
		}
	//安装文件，一般固定写法
		void update() {                    
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
			intent.setDataAndType(Uri.fromFile(new File(Environment
					.getExternalStorageDirectory(), "yssf.apk")),
					"application/vnd.android.package-archive");
			context.startActivity(intent);
		}
	}
