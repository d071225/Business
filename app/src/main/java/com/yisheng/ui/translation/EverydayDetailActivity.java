package com.yisheng.ui.translation;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yisheng.adapter.SomeDayAdapter;
import com.yisheng.business.BaseActivity;
import com.yisheng.business.MyApplacation;
import com.yisheng.business.R;
import com.yisheng.domain.QueryAllBean;
import com.yisheng.http.ConnectionConstant;
import com.yisheng.ui.home.TranslationActivity;
import com.yisheng.utils.L;
import com.yisheng.utils.SPUtils;
import com.yisheng.utils.T;
import com.yisheng.utils.TranslationStatusUtils;
import com.yisheng.utils.TranslationWayUtils;
import com.yisheng.view.IsHaseData;
import com.yisheng.view.recycler.RefreshListView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DY on 2016/9/14.
 */
public class EverydayDetailActivity extends BaseActivity{
    private RefreshListView listView;
    private SomeDayAdapter adapter;
    private ImageView back;
    private TextView title;
    private List<QueryAllBean.QueryAllList> dataList;
    private String after_time;
    private String before_time;
    private String order_status;
    private String pay_channel;
    private IsHaseData isHaseData;
    private LinearLayout everyday_ll;
    private String dateTime;
    private int pageNo=1;
    private String news;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_everyday_detail);
        getIntentData();
        dataList=new ArrayList<QueryAllBean.QueryAllList>();
        isHaseData = (IsHaseData) findViewById(R.id.data_status);
        everyday_ll = (LinearLayout) findViewById(R.id.everyday_ll);
        back =(ImageView)findViewById(R.id.back);
        title =(TextView)findViewById(R.id.title);
        title.setText("交易查询");
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        listView = (RefreshListView)findViewById(R.id.everyday_lv);
        loadingDialog.show();
        getDataForService();

        adapter = new SomeDayAdapter(dataList, getApplicationContext());
        listView.setAdapter(adapter);
        listviewClick();
    }

    private void listviewClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(EverydayDetailActivity.this, TranslationActivity.class);
                intent.putExtra("prdordno",dataList.get(position).prdordno);
                L.e("第三方订单===》"+dataList.get(position).thirdPartyOrdNo);
                startActivity(intent);
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
                        getDataForService();
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
                        getDataForService();
                    }
                }, 1000);

            }
        });
    }

    private void getIntentData() {
        Intent intent = getIntent();
        dateTime = intent.getStringExtra("dateTime");
        news = intent.getStringExtra("news");
        if (dateTime!=null){
            after_time = intent.getStringExtra("dateTime");
            before_time = intent.getStringExtra("dateTime");
        }else{
            after_time = intent.getStringExtra("after_time");
            before_time = intent.getStringExtra("before_time");
        }
        order_status = intent.getStringExtra("order_status");
        pay_channel = intent.getStringExtra("pay_channel");
    }
    public void getDataForService(){
        L.e("支付渠道:"+pay_channel+"支付状态："+order_status+"结束时间："+before_time+"开始时间："+after_time);
        RequestParams params=new RequestParams(ConnectionConstant.QUERYALLURL);
        params.addBodyParameter("merchant_id", (String) SPUtils.get(MyApplacation.getContext(), "custId", ""));
        params.addBodyParameter("after_time", after_time);
        params.addBodyParameter("before_time", before_time);
        params.addBodyParameter("order_status", TranslationStatusUtils.stringToNum(order_status));
        params.addBodyParameter("pay_channel", TranslationWayUtils.stringToNum(pay_channel));
        params.addBodyParameter("pageSize", "10");
        params.addBodyParameter("page", pageNo+"");
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                L.e("查询成功===》" + result);
                loadingDialog.dismiss();
                Gson gson=new Gson();
                QueryAllBean queryAllBean = gson.fromJson(result, QueryAllBean.class);
                if (queryAllBean.code.equals("0000")){
                    isHaseData.initChildView(IsHaseData.TASK_COMPLETED, everyday_ll);
                    dataList.addAll(queryAllBean.body.orderList);
                }else {

                    if (pageNo >1){
                        T.showShort(MyApplacation.getContext(), "没有数据了");
                    }else{
                        isHaseData.initChildView(IsHaseData.TASK_EMPTY, everyday_ll);
                    }
                }
                adapter.notifyDataSetChanged();
                listView.onRefreshComplete(true);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                L.e("查询失败===》");
                isHaseData.initChildView(IsHaseData.TASK_FAILED, everyday_ll);
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

}
