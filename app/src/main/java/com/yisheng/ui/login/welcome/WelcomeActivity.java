package com.yisheng.ui.login.welcome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.yisheng.business.R;
import com.yisheng.ui.login.LoginActivity;

import java.util.ArrayList;

public class WelcomeActivity extends Activity {

	private ViewPager mViewPager;
	Context mContext;
	private ImageView mPage0;
	private ImageView mPage1;
	private ImageView mPage2;
	private ImageView mPage3;
	private ImageView mPage4;
	private ImageView mPage5;
	private ImageView mPage6;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        mViewPager = (ViewPager)findViewById(R.id.whatsnew_viewpager);
        mContext=WelcomeActivity.this;
        mViewPager.setOnClickListener(new VipagerOnClick());
        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());   
        mPage0 = (ImageView)findViewById(R.id.page0);
        mPage1 = (ImageView)findViewById(R.id.page1);
        mPage2 = (ImageView)findViewById(R.id.page2);
//        mPage3 = (ImageView)findViewById(R.id.page3);
//        mPage4 = (ImageView)findViewById(R.id.page4);
//        mPage5 = (ImageView)findViewById(R.id.page5);
//        mPage6 = (ImageView)findViewById(R.id.page6);
               
        LayoutInflater mLi = LayoutInflater.from(this);
        View view1 = mLi.inflate(R.layout.welcome_one, null);
        View view2 = mLi.inflate(R.layout.welcome_two, null);
        View view3 = mLi.inflate(R.layout.welcome_three, null);
        final ArrayList<View> views = new ArrayList<View>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        final ArrayList<String> titles = new ArrayList<String>();
        titles.add("tab1");
        titles.add("tab2");
        titles.add("tab3");
//        titles.add("tab4");
//        titles.add("tab5");
//        titles.add("tab6");
//        titles.add("tab7");
        
        MyPagerAdapter mPagerAdapter = new MyPagerAdapter(views,titles,WelcomeActivity.this,new VipagerOnClick());
		mViewPager.setAdapter(mPagerAdapter);
		
    }    
    

    public class MyOnPageChangeListener implements OnPageChangeListener {
    	
    	
		public void onPageSelected(int page) {
			
			switch (page) {
			case 0:				
				mPage0.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				mPage1.setImageDrawable(getResources().getDrawable(R.drawable.page));
				mPage2.setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
			case 1:
				mPage1.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				mPage0.setImageDrawable(getResources().getDrawable(R.drawable.page));
				mPage2.setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
			case 2:
				mPage2.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				mPage1.setImageDrawable(getResources().getDrawable(R.drawable.page));
				mPage0.setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
			case 3:
				mPage3.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				mPage4.setImageDrawable(getResources().getDrawable(R.drawable.page));
				mPage2.setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
			case 4:
				mPage4.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				mPage3.setImageDrawable(getResources().getDrawable(R.drawable.page));
				mPage5.setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
			case 5:
				mPage5.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				mPage4.setImageDrawable(getResources().getDrawable(R.drawable.page));
				mPage6.setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
			case 6:
				mPage6.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				mPage5.setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
			}
		}
		
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageScrollStateChanged(int arg0) {
		}
	}

    public class VipagerOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.startBtn:
					Intent mainIntent = new Intent(mContext,
							LoginActivity.class);
					startActivity(mainIntent);
					finish();
				break;
			default:
				break;
			}
		}
    	
    }
    
    
    
}
