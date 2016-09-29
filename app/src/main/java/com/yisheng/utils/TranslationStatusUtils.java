package com.yisheng.utils;

import android.graphics.Color;
import android.widget.TextView;

/**
 * Created by DY on 2016/9/19.
 */
public class TranslationStatusUtils {
    public static void numToString(TextView tv,String status){
        if(status.equals("01")){
            tv.setTextColor(Color.GREEN);
            tv.setText("交易成功");
//        }else if(status.equals("02")){
//            tv.setTextColor(Color.RED);
//            tv.setText("交易失败");
//        }else if(status.equals("00")){
//            tv.setTextColor(Color.RED);
//            tv.setText("未支付");
//        }else if(status.equals("03")){
//            tv.setTextColor(Color.RED);
//            tv.setText("退款审核中");
//        }else if(status.equals("04")){
//            tv.setTextColor(Color.RED);
//            tv.setText("退款处理中");
//        }else if(status.equals("05")){
//            tv.setTextColor(Color.RED);
//            tv.setText("退款成功");
//        }else if(status.equals("06")){
//            tv.setTextColor(Color.RED);
//            tv.setText("退款失败");
//        }else if(status.equals("07")){
//            tv.setTextColor(Color.RED);
//            tv.setText("撤销审核中");
//        }else if(status.equals("08")){
//            tv.setTextColor(Color.RED);
//            tv.setText("同意撤销");
//        }else if(status.equals("09")){
//            tv.setTextColor(Color.RED);
//            tv.setText("撤销成功");
//        }else if(status.equals("10")){
//            tv.setTextColor(Color.RED);
//            tv.setText("撤销失败");
//        }else if(status.equals("11")){
//            tv.setTextColor(Color.RED);
//            tv.setText("订单作废");
//        }else if(status.equals("21")){
//            tv.setTextColor(Color.RED);
//            tv.setText("支付处理中");
//        }else if(status.equals("99")){
//            tv.setTextColor(Color.RED);
//            tv.setText("超时");
        }else{
            tv.setTextColor(Color.RED);
            tv.setText("交易失败");
        }
    }
    public static String stringToNum(String status){
        if (status==null){
            return null;
        }
        if(status.equals("支付成功")){
            status="01";
        }else if(status.equals("支付失败")){
            status="02";
        }else{
            status=null;
        }
        return status;

    }
}
