package com.yisheng.view;

import android.content.Context;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yisheng.business.R;

/**
 * Created by DY on 2016/9/20.
 */
public class IsHaseData extends RelativeLayout {
    public static final int TASK_COMPLETED = 1;//服务器连接成功，且数据不为空
    public static final int TASK_FAILED = 2;//服务器连接失败
    public static final int TASK_NONETWORK = 3;//无网络
    public static final int TASK_EMPTY = 4;//数据为空
    private View inflate;
    private ImageView iv;
    private TextView tv;
    private View inflate1;

    public IsHaseData(Context context) {
        super(context);
        initView(context);
    }

    public IsHaseData(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public IsHaseData(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initView(Context context) {
//        inflate = View.inflate(context, R.layout.is_have_data, null);
        inflate = LayoutInflater.from(context).inflate(R.layout.is_have_data, this, true);
        iv = (ImageView) inflate.findViewById(R.id.data_iv);
        tv = (TextView) inflate.findViewById(R.id.data_tv);
    }

    public void initChildView(Message msg,View v) {
        if (msg==null){
            showTip_noData();
            return;
        }
        switch (msg.what) {
            case TASK_COMPLETED:  //网络请求成功
                v.setVisibility(VISIBLE);
                this.setVisibility(GONE);
                break;
            case TASK_FAILED:   //网络请求失败
                v.setVisibility(GONE);
                showTip_noServer();
                break;
            case TASK_NONETWORK:
                v.setVisibility(GONE);
                showTip_noConnect();
                break;
            case TASK_EMPTY:
                v.setVisibility(GONE);
                showTip_noData();
                break;
        }
    }
    public void initChildView(int type,View v) {
        if (type==0){
            showTip_noData();
            return;
        }
        switch (type) {
            case TASK_COMPLETED:  //网络请求成功
                v.setVisibility(VISIBLE);
                this.setVisibility(GONE);
                break;
            case TASK_FAILED:   //网络请求失败
                v.setVisibility(GONE);
                showTip_noServer();
                break;
            case TASK_NONETWORK:
                v.setVisibility(GONE);
                showTip_noConnect();
                break;
            case TASK_EMPTY:
                v.setVisibility(GONE);
                showTip_noData();
                break;
        }
    }
    /**
     * 设置样式为无数据样式
     */
    public void showTip_noData(){
        iv.setImageResource(R.drawable.ic_empty_page);
        tv.setText("没有数据");
        this.setVisibility(View.VISIBLE);
    }
    /**
     * 设置样式为无连接样式
     */
    public void showTip_noConnect(){
        iv.setImageResource(R.drawable.ic_error_page);
        tv.setText("无网络连接");
        this.setVisibility(View.VISIBLE);
    }
    /**
     * 设置样式为无服务器样式
     */
    public void showTip_noServer(){
        iv.setImageResource(R.drawable.ic_error_page);
        tv.setText("暂时无法连接服务器，请稍后再试");
        this.setVisibility(View.VISIBLE);
    }

}
