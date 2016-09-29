package com.yisheng.domain;

/**
 * Created by DY on 2016/9/18.
 */
public class SmsBean {
    public String message;
    public String code;
    public SmsData body;

    public class SmsData{
        /**
         *   "android": "0",
         "ios": "0",
         "msg": "重要版本变更,请更新",
         "url": "http://www.eposbank.com/download/qxt.apk",
         "version": "1.0.0"
         */
        public String error;
        public String merchant_id;
    }
}

