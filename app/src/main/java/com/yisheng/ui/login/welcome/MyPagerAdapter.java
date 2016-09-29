package com.yisheng.ui.login.welcome;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.yisheng.business.R;

import java.util.ArrayList;

public class MyPagerAdapter extends PagerAdapter {

	
	private ArrayList<View> views;
	private ArrayList<String> titles;
	private Button startBtn;
	private Button loginBtn;
	private Context mContext;
	
	OnClickListener VipagerOnClick;
	
	public MyPagerAdapter(ArrayList<View> views,ArrayList<String> titles,Context mContext
			,OnClickListener VipagerOnClick){
		this.VipagerOnClick=VipagerOnClick;
		this.mContext=mContext;
		this.views = views;
		this.titles = titles;
	}
	
	@Override
	public int getCount() {
		return this.views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	
	public void destroyItem(View container, int position, Object object) {
		((ViewPager)container).removeView(views.get(position));
	}
	
	public Object instantiateItem(View container, int position) {
		((ViewPager)container).addView(views.get(position));
		if(position==2){
		startBtn=(Button)views.get(position).findViewById(R.id.startBtn);
		startBtn.setOnClickListener(VipagerOnClick);
		}
		return views.get(position);
	}

}
