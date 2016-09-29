package com.yisheng.ui.login;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yisheng.business.BaseActivity;
import com.yisheng.business.MyApplacation;
import com.yisheng.business.R;
import com.yisheng.domain.QuerySomeDayBean;
import com.yisheng.http.ConnectionConstant;
import com.yisheng.utils.ActivityUitls;
import com.yisheng.utils.Dialoguitls;
import com.yisheng.utils.L;
import com.yisheng.utils.SPUtils;
import com.yisheng.utils.T;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by DY on 2016/9/12.
 */
public class SetPasswordActivity extends BaseActivity implements View.OnClickListener{
    private TextView title;
    private ImageView back;

    private EditText telPhone;
    private EditText smscodeEidt;
    private TextView smscodeTxt;
    private Button submit;
    private EditText new_password;
    private EditText un_new_password;
    private String unNewPsw;
    private String newPsw;
    private String mobile;
    private Dialoguitls dialoguitls;
    private String change_psw;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ActivityUitls.addActivity(this);
        setContentView(R.layout.activity_set_password);
        InitView();
    }

    public void InitView(){
        back=(ImageView)findViewById(R.id.back);
        title=(TextView)findViewById(R.id.title);
        title.setText("设置密码");
        back.setOnClickListener(this);
        new_password = (EditText) findViewById(R.id.new_password);
        un_new_password = (EditText) findViewById(R.id.un_new_password);
        submit = (Button) findViewById(R.id.next);
        submit.setOnClickListener(this);
        Intent intent=getIntent();
        mobile = intent.getStringExtra("mobile");
        change_psw = intent.getStringExtra("change_psw");
        if (change_psw !=null){
            title.setText("修改密码");
        }
        dialoguitls = new Dialoguitls(SetPasswordActivity.this);
    }
    public void getDataForService(){
        RequestParams params=new RequestParams(ConnectionConstant.PSWCHANGEURL);
        params.addBodyParameter("mobile", mobile);
        params.addBodyParameter("new_psw", newPsw);
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                L.e("查询成功===》" + result);
                loadingDialog.dismiss();
                Gson gson = new Gson();
                QuerySomeDayBean querySomeDayBean = gson.fromJson(result, QuerySomeDayBean.class);
                if (querySomeDayBean.code.equals("0000")) {
//                    headview_money.setText(MoneyUtils.FenToYuan(querySomeOrder.amt) + "元");
//                    headview_no.setText(querySomeOrder.count + "笔");
//                    T.showShort(MyApplacation.getContext(), "密码设置成功");
                    dialoguitls.showDialog("密码设置成功");
                    dialoguitls.setOnClickOutLogin(new Dialoguitls.OutLogin() {
                        @Override
                        public void out() {
                            SPUtils.remove(MyApplacation.getContext(), "token");
                            ActivityUitls.RemoveAllActivity();
                            startActivity(new Intent(SetPasswordActivity.this, LoginActivity.class));
                        }
                    });
                } else {
                    dialoguitls.showDialog("密码设置失败，请重试");
                }
                dialoguitls.alertDialog.setCancelable(false);
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
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.next:
                newPsw = new_password.getText().toString().trim();
                unNewPsw = un_new_password.getText().toString().trim();
                if (newPsw ==null| unNewPsw ==null){
                    T.showShort(MyApplacation.getContext(),"密码不能为空");
                }else if (!newPsw.equals(unNewPsw)){
                    T.showShort(MyApplacation.getContext(),"两次密码不一致");
                }else {
                    getDataForService();
                }
                break;
        }
    }
}
