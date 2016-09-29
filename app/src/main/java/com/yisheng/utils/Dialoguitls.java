package com.yisheng.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yisheng.business.MyApplacation;
import com.yisheng.business.R;

/**
 * Created by DY on 2016/9/21.
 */
public class Dialoguitls {

    public AlertDialog alertDialog;
    private AlertDialog.Builder builder;
    private Activity context;
    public Dialoguitls(Activity context){
        this.context=context;
    }
    public void showDialog(String message){
        builder = new AlertDialog.Builder(context,R.style.MyDialogStyleTop);
        alertDialog = builder.create();
        View view=View.inflate(MyApplacation.getContext(), R.layout.dialog_login,null);
        TextView tv= (TextView) view.findViewById(R.id.messge);
        tv.setText(message);
        Button btn_ok= (Button) view.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outLogin.out();
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(view, 0, 0, 0, 0);
        alertDialog.show();
//        alertDialog.getWindow().setLayout(300,200);
    }
    public void showOutDialog(String message){
        builder = new AlertDialog.Builder(context,R.style.MyDialogStyleTop);
        alertDialog = builder.create();
        View view=View.inflate(MyApplacation.getContext(), R.layout.dialog_out_login,null);
        TextView tv= (TextView) view.findViewById(R.id.messge);
        tv.setText(message);
        Button btn_ok= (Button) view.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outLogin.out();
                alertDialog.dismiss();
            }
        });
        Button btn_cancle= (Button) view.findViewById(R.id.btn_cancle);
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(view, 0, 0, 0, 0);
        alertDialog.show();
//        alertDialog.getWindow().setLayout(300,200);
    }
    public void dismissDialog(){
        if (alertDialog!=null){
            alertDialog.dismiss();
        }
    }
    OutLogin outLogin;
    public void setOnClickOutLogin(OutLogin outLogin){
        this.outLogin=outLogin;
    }
    public interface OutLogin{
        void out();
    }
}
