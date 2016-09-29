package com.yisheng.domain;

/**
 * Created by DY on 2016/9/18.
 */
public class QueryDetailBean {
    public String message;
    public String code;
    public QueryDetailData body;
    public class QueryDetailData{
        public QueryOrder order;
    }
    public class QueryOrder{
        /**
         *  "prdordno": "20160913000000015",
         "prdordtype": "02",
         "biztype": null,
         "ordstatus": "00",
         "ordamt": "10000",
         "payordno": null,
         "price": null,
         "goodsName": "收款",
         "goodsNameShort": "收款",
         "custId": "16090800000605",
         "prddate": "20160913",
         "prdtime": "100607",
         "ordtime": "20160913100607",
         "modifyTime": null,
         "custName": null,
         "aliMerchantId": "20160908072339027935",
         "merchantOrderNo": "2016091310020184690",
         "callbackUrl": "http://mpay.qianjingpay.com/801019.tran11",
         "tradeDesc": "收款",
         "payChannel": null
         */
        public String ordamt;
        public String prdordno;
        public String ordtime;
        public String price;
        public String ordstatus;
        public String payChannel;
        public String thirdPartyOrdNo;
    }
}

