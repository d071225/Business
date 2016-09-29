package com.yisheng.ui.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yisheng.business.R;

/**
 * Created by DY on 2016/4/29.
 */
public class SuggestionActivity extends Activity implements View.OnClickListener{
    private ImageView back;
    private TextView title;
    private ImageView right;
    private Context mContext;
//    /**
//     * Handler
//     */
//    public Handler suggestionHandler = new Handler() {
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
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
//                case HandlerValues.FAIL:
//                    ExcuteResult excuteResult = (ExcuteResult) msg.obj;
//                    ToastManager.ShowToast(mContext, excuteResult.getMsg());
//                    break;
//                case HandlerValues.SUGGESTIONSUCCESS:
//                    ToastManager.ShowToast(mContext, "查询成功");
//                    finish();
//                    break;
//                default:
//                    break;
//            }
//        }
//    };
    private EditText suggestionInfo;
    private TextView suggestionLimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggestion);
        mContext=getApplicationContext();
        InitView();
    }
    public void InitView(){
        back= (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        right= (ImageView) findViewById(R.id.right);
        right.setOnClickListener(this);
        title = (TextView) findViewById(R.id.title);
        title.setText("意见反馈");
        suggestionLimit = (TextView) findViewById(R.id.suggestion_limit);
        suggestionInfo = (EditText) findViewById(R.id.suggestion_info);
        suggestionInfo.addTextChangedListener(editListener);
    }
//    public void InitData(){
//        List<NameValuePair> parameter = RequestEntityFactory
//                .getInstance().Suggestion(StaticValues.memberId,suggestionInfo.getText().toString().trim());
//        StartNetRequest(parameter, ConnectionConstant.SUGGESTIONREQUEST,
//                suggestionHandler, mContext);
//    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent i=null;
        switch (id) {
            case R.id.back:
                finish();
                break;
            case R.id.right:
                if (suggestionInfo.getText().toString().equals("")){
//                    ToastManager.ShowToast(mContext, "反馈意见不能为空");
                }else{
//                    InitData();
                }
                break;
        }
    }
    TextWatcher editListener=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(suggestionInfo.length()!=0){
                suggestionLimit.setText(suggestionInfo.length()+"/250");
            }else{
                suggestionLimit.setText("限定字数250字");
            }
        }
    };

}
