package com.yisheng.utils;

/**
 * Created by DY on 2016/9/19.
 */
public class MoneyUtils {
    public static String FenToYuan(String money){
        if (money==null){
            return "金额为空";
        }
        Float moneyFloat = Float.parseFloat(money);
        return StringToFloat.parseMoney(moneyFloat/100+"");
    }
}
