package com.yisheng.ui.login;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yisheng.business.BaseActivity;
import com.yisheng.business.MainActivity;
import com.yisheng.business.MyApplacation;
import com.yisheng.business.R;
import com.yisheng.domain.LoginBean;
import com.yisheng.domain.UpdateBean;
import com.yisheng.http.ConnectionConstant;
import com.yisheng.utils.ActivityUitls;
import com.yisheng.utils.AppUpdate;
import com.yisheng.utils.Dialoguitls;
import com.yisheng.utils.ExampleUtil;
import com.yisheng.utils.L;
import com.yisheng.utils.SPUtils;
import com.yisheng.utils.T;
import com.yisheng.view.LoadingDialog;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class LoginActivity extends BaseActivity implements OnClickListener {


	private Button login;
	private TextView forgetPassword;
	private EditText edit_username;
	private EditText edit_pwd;
	private Dialoguitls dialoguitls;
	private LoadingDialog loadingDialog;
	private String username;

	@Override
	protected void initData() {

	}

	@Override
	protected void initView() {
		ActivityUitls.addActivity(this);
		setContentView(R.layout.new_login);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		InitView();
	}

	public void getDataForService(){
		username = edit_username.getText().toString().trim();
		RequestParams params=new RequestParams(ConnectionConstant.LOGINURL);
//		params.addBodyParameter("custMobile", edit_username.getText().toString().trim());
//		params.addBodyParameter("custPwd", edit_username.getText().toString().trim());
		params.addBodyParameter("REQ_MESSAGE", "{\"REQ_HEAD\": {},\"REQ_BODY\": {\"custMobile\":" + username + "," + "\"custPwd\":" + edit_pwd.getText().toString().trim() + "}}");
		x.http().post(params, new Callback.CommonCallback<String>() {

			@Override
			public void onSuccess(String result) {
				L.e("查询成功===》" + result);
				loadingDialog.dismiss();
				Gson gson = new Gson();
				LoginBean loginBean = gson.fromJson(result, LoginBean.class);
				LoginBean.LoginData loginData = loginBean.REP_BODY;
				if (loginData.RSPCOD.equals("000000")) {
					String custId = loginData.custId;
					String custName = loginData.custName;
					String RSPMSG = loginData.RSPMSG;
					String token = loginData.token;
					SPUtils.put(MyApplacation.getContext(), "token", token);
					SPUtils.put(MyApplacation.getContext(), "custId", custId);
					SPUtils.put(MyApplacation.getContext(), "custName", custName);
					SPUtils.put(MyApplacation.getContext(), "username", username);
					startActivity(new Intent(LoginActivity.this, MainActivity.class));
					finish();
					if (custId != null) {
						//调用JPush API设置Alias
						mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, custId));
					}
					T.showShort(MyApplacation.getContext(), "登录成功");
				} else if (loginData.RSPCOD.equals("000206")) {
					dialoguitls.showDialog("用户不存在");
				} else {
					dialoguitls.showDialog("用户或密码不正确");
				}
//				dialoguitls.alertDialog.setCancelable(false);
				dialoguitls.setOnClickOutLogin(new Dialoguitls.OutLogin() {
					@Override
					public void out() {

					}
				});
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				L.e("查询失败===》");
				T.showShort(MyApplacation.getContext(), "服务器连接失败");
				loadingDialog.dismiss();
			}

			@Override
			public void onCancelled(CancelledException cex) {
				loadingDialog.dismiss();
			}

			@Override
			public void onFinished() {
				loadingDialog.dismiss();
			}
		});
	}
	@Override
	protected void onResume() {
		super.onResume();
		prepareUpdate();
	}

	public void prepareUpdate(){
//		ShowDialog(mContext);
//		List<NameValuePair> parameter = RequestEntityFactory
//				.getInstance().UpdateEntity();
//		StartNetRequest(parameter,
//				ConnectionConstant.UPDATEREQUEST, loginHandler,
//				mContext);
	}
	public void InitView() {
		forgetPassword = (TextView) findViewById(R.id.forget_password);
		forgetPassword.setOnClickListener(this);
		login = (Button) findViewById(R.id.login);
		login.setOnClickListener(this);
		edit_username = (EditText) findViewById(R.id.edit_username);
		edit_pwd = (EditText) findViewById(R.id.edit_pwd);
		dialoguitls = new Dialoguitls(LoginActivity.this);
		//网络加载提示对话框
		loadingDialog =new LoadingDialog(this);
		loadingDialog.setCanceledOnTouchOutside(false);
		edit_username.setText((String) SPUtils.get(MyApplacation.getContext(), "username", ""));
		getUpdateForService();
	}

	public void getUpdateForService(){
		RequestParams params=new RequestParams(ConnectionConstant.CHECKUPDATEURL);
		x.http().post(params, new Callback.CommonCallback<String>() {

			@Override
			public void onSuccess(String result) {
				L.e("查询成功===》" + result);
				loadingDialog.dismiss();
				Gson gson = new Gson();
				UpdateBean updateBean = gson.fromJson(result, UpdateBean.class);
				UpdateBean.UpdateData updateData = updateBean.body;
				if (updateBean.code.equals("0000")) {
					String android = updateData.android;
					String msg = updateData.msg;
					String url = updateData.url;
					String version = updateData.version;
						AppUpdate appUpdate = new AppUpdate(LoginActivity.this,
								version, url, msg,
								android);
						appUpdate.checkUpdate();
				}
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				L.e("查询失败===》");
				T.showShort(MyApplacation.getContext(), "连接服务器失败");
				loadingDialog.dismiss();
			}

			@Override
			public void onCancelled(CancelledException cex) {
				loadingDialog.dismiss();
			}

			@Override
			public void onFinished() {
				loadingDialog.dismiss();
			}
		});
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
			case R.id.login:
				if(edit_username.getText().toString().trim().equals("")|edit_pwd.getText().toString().trim().equals("")){
					T.showShort(MyApplacation.getContext(),"用户名或密码不能为空");
				}else{
					loadingDialog.show();
					getDataForService();
				}
//				startActivity(new Intent(LoginActivity.this, MainActivity.class));
				break;
			case R.id.forget_password:
				startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
				break;
		default:
			break;
		}
	}
	private static final int MSG_SET_ALIAS = 1001;
	private static final int MSG_SET_TAGS = 1002;
	private static final String TAG = "JPush";

	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case MSG_SET_ALIAS:
					Log.d(TAG, "Set alias in handler.");
					JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, null, mAliasCallback);
					break;

//                case MSG_SET_TAGS:
//                    Log.d(TAG, "Set tags in handler.");
//                    JPushInterface.setAliasAndTags(getApplicationContext(), null, (Set<String>) msg.obj, mTagsCallback);
//                    break;

				default:
					Log.i(TAG, "Unhandled msg - " + msg.what);
			}
		}
	};
	private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

		@Override
		public void gotResult(int code, String alias, Set<String> tags) {
			String logs ;
			switch (code) {
				case 0:
					logs = "Set tag and alias success";
					Log.i(TAG, logs);
					break;

				case 6002:
					logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
					Log.i(TAG, logs);
					if (ExampleUtil.isConnected(getApplicationContext())) {
						mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
					} else {
						Log.i(TAG, "No network");
					}
					break;

				default:
					logs = "Failed with errorCode = " + code;
					Log.e(TAG, logs);
			}

//			ExampleUtil.showToast(logs, getApplicationContext());
		}

	};
}
