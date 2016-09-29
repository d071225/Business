package com.yisheng.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by DY on 2016/9/13.
 */
public abstract class MyAdapter<T> extends BaseAdapter{

    protected List<T> list;
    protected Context context;
    public MyAdapter(List<T> list,Context context) {
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
}
