package com.yisheng.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yisheng.adapter.SomeDayAdapter;
import com.yisheng.business.MyApplacation;
import com.yisheng.business.R;
import com.yisheng.domain.QueryAllBean;
import com.yisheng.domain.QuerySomeDayBean;
import com.yisheng.http.ConnectionConstant;
import com.yisheng.ui.home.TranslationActivity;
import com.yisheng.utils.L;
import com.yisheng.utils.MoneyUtils;
import com.yisheng.utils.SPUtils;
import com.yisheng.utils.T;
import com.yisheng.view.BadgeView;
import com.yisheng.view.LoadingDialog;
import com.yisheng.view.recycler.RefreshListView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@SuppressLint({ "NewApi", "ValidFragment", "ResourceAsColor" })
public class PlanetFragment extends Fragment implements View.OnClickListener{

	private ImageView back;
	private RefreshListView listView;
	private SomeDayAdapter adapter;
	private ImageView home_iv;
	private LinearLayout home_new_messge;
	private View rootView;
	private TextView headview_money;
	private TextView headview_no;
	private List<QueryAllBean.QueryAllList> dataList;
	private LoadingDialog loadingDialog;
	private int pageNo=1;
	private int count=10;
	private int countNo=0;
	private TextView custom_name;
	private LinearLayout headview_ll;
	private static final int CURRENTDAYDELAY=1;
	private static final int CURRENTDAYRECYCLE=2;
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
					case CURRENTDAYDELAY:
						handler.sendEmptyMessageDelayed(CURRENTDAYRECYCLE,3*1000*60);
//						handler.sendEmptyMessageDelayed(CURRENTDAYRECYCLE,1000);
						break;
					case CURRENTDAYRECYCLE:
//						++countNo;
//						headview_no.setText(countNo + "笔");
						loadingDialog.show();
						getDataForService(0);
						pageNo = 1;
						dataList.clear();
						getDataListForService();
						break;

					default:
						break;
					}
		}
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_planet, container, false);
		initView();
		return rootView;
	}
	public void initView(){
		dataList=new ArrayList<QueryAllBean.QueryAllList>();
		listView = (RefreshListView) rootView.findViewById(R.id.home_lv);
		home_new_messge = (LinearLayout) rootView.findViewById(R.id.home_new_messge);
		home_iv = (ImageView) rootView.findViewById(R.id.home_iv);
		custom_name = (TextView) rootView.findViewById(R.id.custom_name);
		custom_name.setText((String) SPUtils.get(MyApplacation.getContext(), "custName", ""));
		showBadgeView();
		View headView=View.inflate(getActivity(),R.layout.home_listview_headview,null);
		headview_money = (TextView) headView.findViewById(R.id.headview_money);
//		headview_ll = (LinearLayout) headView.findViewById(R.id.headview_ll);
//		headview_ll.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				L.e("点击了头部布局");
//			}
//		});
		headview_no = (TextView) headView.findViewById(R.id.headview_no);
//		headview_money.setOnClickListener(this);
//		headview_no.setOnClickListener(this);
		loadingDialog = new LoadingDialog(getActivity());
		loadingDialog.show();
		getDataForService(1);
		getDataListForService();
		L.e("获取当前日期===》" + getDateTime() + "保存的商户id==》" + SPUtils.get(MyApplacation.getContext(), "username", ""));
		listView.addHeaderView(headView, null, false);
//		listView.addHeaderView(headView);
		adapter = new SomeDayAdapter(dataList,getActivity());
		listView.setAdapter(adapter);
		listviewClick();
	}

	private void listviewClick() {
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				L.e("item位置" + position);
				if (position > -1) {
					Intent intent = new Intent(getActivity(), TranslationActivity.class);
					intent.putExtra("prdordno", dataList.get(position).prdordno);
					L.e("首页订单号==》" + dataList.get(position).prdordno);
					startActivity(intent);
				}
			}
		});
		listView.setOnRefreshListener(new RefreshListView.OnRefreshListener() {

			@Override
			public void onRefresh() {

				MyApplacation.getmHandler().postDelayed(new Runnable() {
					@Override
					public void run() {
						pageNo = 1;
						dataList.clear();
						handler.removeCallbacksAndMessages(null);
						getDataListForService();
						getDataForService(1);
					}
				}, 1000);
			}

			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				MyApplacation.getmHandler().postDelayed(new Runnable() {
					@Override
					public void run() {
						pageNo++;
						getDataListForService();
					}
				}, 1000);

			}
		});
	}

	public void getDataForService(final int type){
		RequestParams params=new RequestParams(ConnectionConstant.QUERYSOMEDAYURL);
		params.addBodyParameter("merchant_id", (String)SPUtils.get(MyApplacation.getContext(),"custId",""));
//		params.addBodyParameter("merchant_id", "16090800000605");
//        params.addBodyParameter("date_time", "20160912");
        params.addBodyParameter("date_time", getDateTime());
		x.http().post(params, new Callback.CommonCallback<String>() {

			@Override
			public void onSuccess(String result) {
				L.e("查询成功===》" + result);
				loadingDialog.dismiss();
				Gson gson = new Gson();
				QuerySomeDayBean querySomeDayBean = gson.fromJson(result, QuerySomeDayBean.class);
				QuerySomeDayBean.QuerySomeOrder querySomeOrder = querySomeDayBean.body.order;
				if (querySomeDayBean.code.equals("0000")){
					headview_money.setText(MoneyUtils.FenToYuan(querySomeOrder.amt) + "元");
					headview_no.setText(querySomeOrder.count + "笔");
				}else{
					if (type==1){
						T.showShort(MyApplacation.getContext(),"没有交易数据");
					}
				}
				handler.sendEmptyMessage(CURRENTDAYDELAY);
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				L.e("查询失败===》");
				if (type==1){
					T.showShort(MyApplacation.getContext(),"服务器连接失败");
				}
				loadingDialog.dismiss();
				handler.sendEmptyMessage(CURRENTDAYDELAY);
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
	public void getDataListForService(){
		RequestParams params=new RequestParams(ConnectionConstant.QUERYALLURL);
		params.addBodyParameter("merchant_id", (String)SPUtils.get(MyApplacation.getContext(),"custId",""));
//		params.addBodyParameter("merchant_id", "16090800000605");
        params.addBodyParameter("pageSize", count+"");
        params.addBodyParameter("page", pageNo+"");
        params.addBodyParameter("date_time", getDateTime());
//        params.addBodyParameter("date_time", "20160913");
		x.http().post(params, new Callback.CommonCallback<String>() {

			@Override
			public void onSuccess(String result) {
				L.e("查询成功 列表===》" + result);
				Gson gson=new Gson();
				QueryAllBean queryAllBean = gson.fromJson(result, QueryAllBean.class);
				if (queryAllBean.code.equals("0000")){
					dataList.addAll(queryAllBean.body.orderList);
				}else{
					if (pageNo>1){
						T.showShort(MyApplacation.getContext(), "没有数据了");
					}
				}
				adapter.notifyDataSetChanged();
				listView.onRefreshComplete(true);
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				L.e("查询失败===》");
				if (pageNo>1){
					pageNo--;
				}
				listView.onRefreshComplete(true);

			}

			@Override
			public void onCancelled(CancelledException cex) {
				listView.onRefreshComplete(true);
			}

			@Override
			public void onFinished() {
				listView.onRefreshComplete(true);
			}
		});
	}
	private void showBadgeView() {
		BadgeView badgeView=new BadgeView(getActivity(),home_iv);
		badgeView.setText("1");
		badgeView.setTextSize(12);
		badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
		badgeView.show();
	}

	private String getDateTime() {

		final Calendar c = Calendar.getInstance();

		int mYear = c.get(Calendar.YEAR);

		int mMonth = c.get(Calendar.MONTH);

		int mDay = c.get(Calendar.DAY_OF_MONTH);
		return ""+mYear+((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))+((mDay < 10) ? "0" + mDay : mDay);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		handler.removeCallbacksAndMessages(null);
	}

	@Override
	public void onClick(View v) {

	}
}