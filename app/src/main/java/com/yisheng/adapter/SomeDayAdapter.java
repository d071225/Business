package com.yisheng.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yisheng.business.R;
import com.yisheng.domain.QueryAllBean;
import com.yisheng.utils.DateUtils;
import com.yisheng.utils.MoneyUtils;
import com.yisheng.utils.TranslationStatusUtils;
import com.yisheng.utils.TranslationWayUtils;

import java.util.List;

/**
 * Created by DY on 2016/9/13.
 */
public class SomeDayAdapter extends MyAdapter<QueryAllBean.QueryAllList> {

    public SomeDayAdapter(List<QueryAllBean.QueryAllList> list, Context context) {
        super(list, context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.hone_listview_item,null);
            holder=new ViewHolder();
            holder.time= (TextView) convertView.findViewById(R.id.home_item_time);
            holder.money= (TextView) convertView.findViewById(R.id.home_item_money);
            holder.state= (TextView) convertView.findViewById(R.id.home_item_state);
            holder.path= (TextView) convertView.findViewById(R.id.home_item_path);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.time.setText(DateUtils.getMyDateToString(DateUtils.getStringToDate(list.get(position).ordtime)));
//        holder.time.setText(DateUtils.getMyDateToString(Long.parseLong(list.get(position).ordtime)));
//        L.e("当前时间戳===》"+DateUtils.timeStamp());
        holder.money.setText("+"+MoneyUtils.FenToYuan(list.get(position).ordamt)+"元");
        TranslationStatusUtils.numToString( holder.state,list.get(position).ordstatus);
        holder.path.setText("("+TranslationWayUtils.numToString(list.get(position).payChannel)+")");
        return convertView;
    }
    public static class ViewHolder{
        public TextView time;
        public TextView money;
        public TextView state;
        public TextView path;
    }
}
