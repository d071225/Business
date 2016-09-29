package com.yisheng.domain;

import java.util.List;

/**
 * Created by DY on 2016/9/18.
 */
public class QueryRecentlyBean {
    public String message;
    public String code;
    public QueryRecentlyData body;
    public class QueryRecentlyData{
        public List<QueryRecentlyList> orderList;
    }
    public class QueryRecentlyList{
        /**
         * "custId": "16090800000605",
         "dateTime": "20160913",
         "count": "15",
         "amt": "680000"
         */
        public String dateTime;
        public String amt;
        public String count;
    }
}

