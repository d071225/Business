package com.yisheng.domain;

/**
 * Created by DY on 2016/9/18.
 */
public class QuerySomeDayBean {
    public String message;
    public String code;
    public QuerySomeData body;
    public class QuerySomeData{
        public QuerySomeOrder order;
    }
    public class QuerySomeOrder{
        /**
         *  "custId": "16090800000605",
         "dateTime": "20160912",
         "count": "103",
         "amt": "609610"
         */
        public String dateTime;
        public String count;
        public String amt;
    }
}

