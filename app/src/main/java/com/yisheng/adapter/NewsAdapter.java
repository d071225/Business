package com.yisheng.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yisheng.business.R;

import java.util.List;

/**
 * Created by DY on 2016/9/13.
 */
public class NewsAdapter extends MyAdapter<String> {

    public NewsAdapter(List<String> list, Context context) {
        super(list, context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.news_listview_item,null);
            holder=new ViewHolder();
            holder.time= (TextView) convertView.findViewById(R.id.news_item_time);
            holder.title= (TextView) convertView.findViewById(R.id.news_item_title);
            holder.content= (TextView) convertView.findViewById(R.id.news_item_content);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.time.setText(list.get(position));
        return convertView;
    }
    public static class ViewHolder{
        public TextView time;
        public TextView title;
        public TextView content;
    }
}
