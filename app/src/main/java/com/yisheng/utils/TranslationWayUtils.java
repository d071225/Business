package com.yisheng.utils;

/**
 * Created by DY on 2016/9/19.
 */
public class TranslationWayUtils {
    public static String numToString(String status){
        if(status==null){
            return "交易渠道为空";
        }
        if(status.equals("wx")){
            status="微信";
        }else if(status.equals("ali")){
            status="支付宝";
        }else{
            status="交易渠道未知";
        }
        return status;
    }
    public static String stringToNum(String status){
        if(status==null){
            return null;
        }
        if(status.equals("微信")){
            status="wx";
        }else if(status.equals("支付宝")){
            status="ali";
        }else{
            status=null;
        }
        return status;
    }
}
