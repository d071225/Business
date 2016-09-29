package com.yisheng.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yisheng.business.MyApplacation;
import com.yisheng.business.R;
import com.yisheng.domain.UpdateBean;
import com.yisheng.http.ConnectionConstant;
import com.yisheng.ui.login.ForgetPasswordActivity;
import com.yisheng.ui.login.LoginActivity;
import com.yisheng.ui.translation.EverydayDetailActivity;
import com.yisheng.utils.ActivityUitls;
import com.yisheng.utils.AppUpdate;
import com.yisheng.utils.AppUtils;
import com.yisheng.utils.Dialoguitls;
import com.yisheng.utils.L;
import com.yisheng.utils.SPUtils;
import com.yisheng.utils.T;
import com.yisheng.view.LoadingDialog;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

@SuppressLint({ "NewApi", "ValidFragment", "ResourceAsColor" })
public class MyFragment extends Fragment implements View.OnClickListener{

	View rootView;

	TextView title;
	Button loginOutBtn;

	private ImageView back;
	private ImageView imgUserDefault;
	private RelativeLayout mine_change_password;
	private RelativeLayout mine_mgs_center;
	private RelativeLayout mine_suggestion;
	private RelativeLayout mine_check_update;
	private Button login_out;
	private TextView mine_new_messge;
	private TextView custom_id;
	private LoadingDialog loadingDialog;
	private Dialoguitls dialoguitls;

	@Override
	public void onClick(View v) {
			Intent intent=null;
			switch (v.getId()){
				case R.id.login_out:
					dialoguitls.showOutDialog("确定要退出应用吗？");
					dialoguitls.setOnClickOutLogin(new Dialoguitls.OutLogin() {
						@Override
						public void out() {
							outListener.out();
							SPUtils.remove(MyApplacation.getContext(), "token");
							ActivityUitls.RemoveAllActivity();
							startActivity(new Intent(getActivity(), LoginActivity.class));
						}
					});
					break;
				case R.id.mine_change_password:
					intent=new Intent(getActivity(), ForgetPasswordActivity.class);
					intent.putExtra("change_psw","修改密码");
					startActivity(intent);
					break;
				case R.id.mine_suggestion:
					T.showShort(MyApplacation.getContext(), "暂未开放");
//					startActivity(new Intent(getActivity(), SuggestionActivity.class));
					break;
				case R.id.mine_mgs_center:
					T.showShort(MyApplacation.getContext(), "暂未开放");
//					startActivity(new Intent(getActivity(), NewsCenterActivity.class));
					break;
				case R.id.mine_new_messge:
					intent=new Intent(getActivity(), EverydayDetailActivity.class);
					intent.putExtra("news","news");
					startActivity(intent);
					break;
				case R.id.mine_check_update:
					getUpdateForService();
					break;
			}
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
					String versionName = AppUtils.getVersionName(getActivity());
					if (versionName.equals(version)){
						dialoguitls.showDialog("已是最新版本，无需更新");
						dialoguitls.setOnClickOutLogin(new Dialoguitls.OutLogin() {
							@Override
							public void out() {

							}
						});
					}else{
						AppUpdate appUpdate = new AppUpdate(getActivity(),
								version, url, msg,
								android);
						appUpdate.checkUpdate();
					}
//					SPUtils.clear(MyApplacation.getContext());
//					startActivity(new Intent(SetPasswordActivity.this, LoginActivity.class));
//				} else {
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

	OutListener outListener;
	public interface OutListener {
		void out();
	}
	public void setOutListener(OutListener outListener){
		this.outListener=outListener;
	}
	public interface TranslogListener {
		void trans();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_my, container, false);
		InitView();
		return rootView;
	}

	public void InitView() {
		title = (TextView) rootView.findViewById(R.id.title);
		mine_new_messge = (TextView) rootView.findViewById(R.id.mine_new_messge);
		title.setText("我的");
		custom_id = (TextView) rootView.findViewById(R.id.mine_custom_id);
		custom_id.setText((String) SPUtils.get(MyApplacation.getContext(), "custName", ""));
		back=(ImageView)rootView.findViewById(R.id.back);
		back.setVisibility(View.GONE);
		loginOutBtn = (Button) rootView.findViewById(R.id.login_out);
		loginOutBtn.setOnClickListener(this);
		mine_change_password = (RelativeLayout) rootView.findViewById(R.id.mine_change_password);
		mine_check_update = (RelativeLayout) rootView.findViewById(R.id.mine_check_update);
		mine_mgs_center = (RelativeLayout) rootView.findViewById(R.id.mine_mgs_center);
		mine_suggestion = (RelativeLayout) rootView.findViewById(R.id.mine_suggestion);
		login_out = (Button) rootView.findViewById(R.id.login_out);
		login_out.setOnClickListener(this);
		mine_change_password.setOnClickListener(this);
		mine_check_update.setOnClickListener(this);
		mine_mgs_center.setOnClickListener(this);
		mine_suggestion.setOnClickListener(this);
		mine_new_messge.setOnClickListener(this);
		loadingDialog = new LoadingDialog(getActivity());
		dialoguitls = new Dialoguitls(getActivity());
	}


	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}


	
}