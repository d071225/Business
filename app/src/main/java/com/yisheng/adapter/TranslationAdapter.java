package com.yisheng.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yisheng.business.R;
import com.yisheng.domain.QueryRecentlyBean;
import com.yisheng.utils.DateUtils;
import com.yisheng.utils.MoneyUtils;

import java.util.List;

/**
 * Created by DY on 2016/9/13.
 */
public class TranslationAdapter extends MyAdapter<QueryRecentlyBean.QueryRecentlyList> {

    public TranslationAdapter(List<QueryRecentlyBean.QueryRecentlyList> list, Context context) {
        super(list, context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.translation_listview_item,null);
            holder=new ViewHolder();
            holder.time= (TextView) convertView.findViewById(R.id.translation_item_time);
            holder.money= (TextView) convertView.findViewById(R.id.translation_item_money);
            holder.no= (TextView) convertView.findViewById(R.id.translation_item_no);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.time.setText(DateUtils.getDateToString2(DateUtils.getStringDayToDate(list.get(position).dateTime)));
        holder.money.setText("+"+MoneyUtils.FenToYuan(list.get(position).amt)+"元");
        holder.no.setText(list.get(position).count+"笔");
        return convertView;
    }
    public static class ViewHolder{
        public TextView time;
        public TextView money;
        public TextView no;
    }
}
