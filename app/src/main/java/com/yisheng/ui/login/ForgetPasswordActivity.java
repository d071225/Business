package com.yisheng.ui.login;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yisheng.business.BaseActivity;
import com.yisheng.business.MyApplacation;
import com.yisheng.business.R;
import com.yisheng.domain.SmsBean;
import com.yisheng.http.ConnectionConstant;
import com.yisheng.utils.ActivityUitls;
import com.yisheng.utils.AppUtils;
import com.yisheng.utils.HandlerValues;
import com.yisheng.utils.L;
import com.yisheng.utils.SPUtils;
import com.yisheng.utils.T;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by DY on 2016/9/12.
 */
public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener{
    private TextView title;
    private ImageView back;

    private EditText telPhone;
    private EditText smscodeEidt;
    private TextView smscodeTxt;
    private Button submit;

    /**
     * Handler
     */
    public Handler loginHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
//                case HandlerValues.NETWORK_TIMEOUT_ERROR:
//                    ToastManager.TimeutToast(mContext);
//                    break;
//                case HandlerValues.NETWORK_ERROR:
//                    ToastManager.NetWorkErrorToast(mContext);
//                    break;
//                case HandlerValues.UNKNOWNERROR:
//                case HandlerValues.DATAFORMATEERROR:
//                case HandlerValues.HTTPERROR:
//                    ToastManager.ErrorRequestToast(mContext);
//                    break;
                case HandlerValues.TIMEOK:
                    if (timer != null) {
                        timer.cancel();
                    }
                    if (changeTask != null) {
                        changeTask.cancel();
                    }
                    smscodeTxt.setText("重新发送验证码");
                    smscodeTxt.setClickable(true);
                    smscodeTxt.setBackgroundResource(R.drawable.text_sms_bg);
                    break;
                case HandlerValues.REFLASHTIME:
                    String timerString = (String) msg.obj;
                    smscodeTxt.setClickable(false);
                    smscodeTxt.setBackgroundResource(R.drawable.text_sms_bg_wait);
                    smscodeTxt.setText(timerString);
                    break;
//                case HandlerValues.FAIL:
//                    excuteResult = (ExcuteResult) msg.obj;
//                    ToastManager.ShowToast(mContext, excuteResult.getResultMsg());
//                    break;
//                case HandlerValues.REGISTSUCCESS:
//                    excuteResult = (ExcuteResult) msg.obj;
//                    ToastManager.ShowToast(mContext, excuteResult.getResultMsg());
//                    setResult(777);
//                    finish();
//                    break;
//                case HandlerValues.SMSSUCCESS:
//                    ToastManager.ShowToast(mContext, "验证码已发送");
//                    break;
                default:
                    break;
            }
        }
    };
    private Timer timer;
    private TimerTask changeTask;
    private String change_psw;
    private String phoneNum;
    private String merchant_id;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ActivityUitls.addActivity(this);
        setContentView(R.layout.activity_forget_password);
        InitView();
    }

    public void InitView(){
        back=(ImageView)findViewById(R.id.back);
        title=(TextView)findViewById(R.id.title);
        title.setText("忘记密码");
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        telPhone = (EditText) findViewById(R.id.phone);
        telPhone.setText((String) SPUtils.get(MyApplacation.getContext(), "username", ""));
//        telPhone.addTextChangedListener(textWatcher);
        smscodeEidt = (EditText) findViewById(R.id.edt_smscode);
        submit = (Button) findViewById(R.id.next);
        smscodeTxt = (TextView) findViewById(R.id.txt_smscode);
//		smscodeTxt.setTextColor(mContext.getResources().getColor(
//				R.color.shape_price_stroke));
        smscodeTxt.setBackgroundResource(R.drawable.text_sms_bg_wait);
        smscodeTxt.setOnClickListener(ForgetPasswordActivity.this);
        smscodeTxt.setBackgroundResource(R.drawable.text_sms_bg);
        submit.setOnClickListener(this);
        Intent intent = getIntent();
        change_psw = intent.getStringExtra("change_psw");
        if (change_psw!=null){
            title.setText("修改密码");
        }
    }
    public void sendSms() {
        startLiveTimer();
        getDataForService();
    }

    /**
     * 启动定时器,轮询请求消息
     */
    public void startLiveTimer() {

        timer = new Timer();
        // 构造一个定时器任务对象

        changeTask = new TimerTask() {
            int totaltime = 60;

            @Override
            public void run() {
                if (totaltime > 0) {
                    String timerString ="重新发送"
                            + totaltime;
                    totaltime--;
                    loginHandler.sendMessage(loginHandler.obtainMessage(
                            HandlerValues.REFLASHTIME, timerString));
                } else {
                    loginHandler.sendEmptyMessage(HandlerValues.TIMEOK);
                }
            }
        };
        // 启动定时器,延迟一秒后启动
        timer.schedule(changeTask, 1000, 1000);
    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // 手机号码11位
            if (s == null || s.length() < 11) {
                smscodeTxt.setOnClickListener(null);
                // smscodeTxt.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.next_bg));
//				smscodeTxt.setTextColor(mContext.getResources().getColor(
//						R.color.shape_price_stroke));
                smscodeTxt.setBackgroundResource(R.drawable.text_sms_bg_wait);
            } else {
                if (AppUtils.isMobileNO(s.toString())) {
                    smscodeTxt.setOnClickListener(ForgetPasswordActivity.this);
                    // smscodeTxt.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.reserver_btn));
//					smscodeTxt.setTextColor(mContext.getResources().getColor(
//							R.color.red));
                    smscodeTxt.setBackgroundResource(R.drawable.text_sms_bg);
                } else {
                    smscodeTxt.setOnClickListener(null);
                    // smscodeTxt.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.next_bg));
//					smscodeTxt.setTextColor(mContext.getResources().getColor(
//							R.color.shape_price_stroke));
                    smscodeTxt.setBackgroundResource(R.drawable.text_sms_bg_wait);
                }
            }
        }
    };
    public void getDataForService(){
        RequestParams params=new RequestParams(ConnectionConstant.CUSTSMSURL);
        params.addBodyParameter("mobile", telPhone.getText().toString().toString());
//        params.addBodyParameter("merchant_id", (String) SPUtils.get(MyApplacation.getContext(), "custId", ""));
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                L.e("查询成功===》" + result);
                loadingDialog.dismiss();
                Gson gson = new Gson();
                SmsBean smsBean = gson.fromJson(result, SmsBean.class);
                if (smsBean.code.equals("0000")){
//                    headview_money.setText(MoneyUtils.FenToYuan(querySomeOrder.amt) + "元");
//                    headview_no.setText(querySomeOrder.count + "笔");
                    T.showShort(MyApplacation.getContext(),"短信发送成功");
                    merchant_id = smsBean.body.merchant_id;
                }else{
                    T.showShort(MyApplacation.getContext(),smsBean.body.error);
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
    public void getSmsDataForService(){
        final String mobile=telPhone.getText().toString().toString();
        RequestParams params=new RequestParams(ConnectionConstant.SMSCHECKURL);
        params.addBodyParameter("mobile", mobile);
        L.e("merchant_id===>"+merchant_id);
        if (change_psw!=null){
            params.addBodyParameter("merchant_id", (String) SPUtils.get(MyApplacation.getContext(), "custId", ""));
        }else{
            params.addBodyParameter("merchant_id", merchant_id);
        }
//        params.addBodyParameter("merchant_id", "16090800000603");
        params.addBodyParameter("captcha", smscodeEidt.getText().toString().trim());
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                L.e("查询成功===》" + result);
                loadingDialog.dismiss();
                Gson gson = new Gson();
                SmsBean smsBean = gson.fromJson(result, SmsBean.class);
                if (smsBean.code.equals("0000")){
                    Intent intent=new Intent(ForgetPasswordActivity.this,SetPasswordActivity.class);
                    intent.putExtra("mobile",mobile);
                    intent.putExtra("change_psw","修改密码");
                    startActivity(intent);
                }else {
                    T.showShort(MyApplacation.getContext(), smsBean.body.error);
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
        phoneNum = telPhone.getText().toString().toString();
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.txt_smscode:
                if(phoneNum.equals("")){
                    T.showShort(MyApplacation.getContext(),"手机号不能为空");
                }else if(phoneNum.length()<11){
                    T.showShort(MyApplacation.getContext(),"手机号不能小于11位");
                }else if(!AppUtils.isMobileNO(phoneNum)){
                    T.showShort(MyApplacation.getContext(),"请输入正确的手机号");
                }else{
                    sendSms();
                }
                break;
            case R.id.next:
                if(phoneNum.equals("")){
                    T.showShort(MyApplacation.getContext(),"手机号不能为空");
                }else if(phoneNum.length()<11){
                    T.showShort(MyApplacation.getContext(),"手机号不能小于11位");
                }else if(!AppUtils.isMobileNO(phoneNum)){
                    T.showShort(MyApplacation.getContext(),"请输入正确的手机号");
                }else if(smscodeEidt.getText().toString().toString().equals("")){
                    T.showShort(MyApplacation.getContext(),"验证码不能为空");
                }else{
                    getSmsDataForService();
                }
//                startActivity(new Intent(this,SetPasswordActivity.class));
                break;
        }
    }
}
