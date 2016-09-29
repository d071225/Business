package com.yisheng.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yisheng.business.BaseActivity;
import com.yisheng.business.MyApplacation;
import com.yisheng.business.R;
import com.yisheng.domain.QueryDetailBean;
import com.yisheng.http.ConnectionConstant;
import com.yisheng.utils.DateUtils;
import com.yisheng.utils.L;
import com.yisheng.utils.MoneyUtils;
import com.yisheng.utils.SPUtils;
import com.yisheng.utils.TranslationStatusUtils;
import com.yisheng.utils.TranslationWayUtils;
import com.yisheng.view.IsHaseData;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by DY on 2016/9/14.
 */
public class TranslationActivity extends BaseActivity{

    private ImageView back;
    private TextView title;
    private String prdordno;
    private TextView money;
    private TextView status;
    private TextView pay_no;
    private TextView pay_path;
    private TextView pay_time;
    private IsHaseData isHaseData;
    private LinearLayout detail_ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setContentView(R.layout.translation_detail);
        getInentData();
        back =(ImageView)findViewById(R.id.back);
        title =(TextView)findViewById(R.id.title);
        title.setText("交易详情");
        money =(TextView)findViewById(R.id.translation_detail_money);
        status =(TextView)findViewById(R.id.translation_detail_status);
        pay_no =(TextView)findViewById(R.id.translation_detail_pay_no);
        pay_path =(TextView)findViewById(R.id.translation_detail_pay_path);
        pay_time =(TextView)findViewById(R.id.translation_detail_pay_time);
        isHaseData = (IsHaseData) findViewById(R.id.data_status);
        detail_ll = (LinearLayout) findViewById(R.id.translation_detail_ll);
        loadingDialog.show();
        getDataForService();
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }

    private void getInentData() {
        Intent intent = getIntent();
        prdordno = intent.getStringExtra("prdordno");

    }

    public void getDataForService(){
        L.e("获取订单号===》" + prdordno);
        RequestParams params=new RequestParams(ConnectionConstant.QUERYDETAILURL);
//        params.addBodyParameter("merchant_id", "16090800000605");
        params.addBodyParameter("merchant_id", (String) SPUtils.get(MyApplacation.getContext(), "custId", ""));
        params.addBodyParameter("order_no", prdordno);
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                L.e("查询成功===》" + result);
                loadingDialog.dismiss();
                Gson gson = new Gson();
                QueryDetailBean queryDetailBean = gson.fromJson(result, QueryDetailBean.class);
                if (queryDetailBean.code.equals("0000")){
                    QueryDetailBean.QueryOrder queryOrder=queryDetailBean.body.order;
                    money.setText("+" + MoneyUtils.FenToYuan(queryOrder.ordamt) + "元");
                    TranslationStatusUtils.numToString(status, queryOrder.ordstatus);
                    pay_no.setText(queryOrder.thirdPartyOrdNo);
                    pay_path.setText(TranslationWayUtils.numToString(queryOrder.payChannel));
                    pay_time.setText(DateUtils.getMyDateToString(DateUtils.getStringToDate(queryOrder.ordtime)));
                    isHaseData.initChildView(IsHaseData.TASK_COMPLETED, detail_ll);
                }else{
                    isHaseData.initChildView(IsHaseData.TASK_EMPTY,detail_ll);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                L.e("查询失败===》");
                isHaseData.initChildView(IsHaseData.TASK_FAILED, detail_ll);
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
