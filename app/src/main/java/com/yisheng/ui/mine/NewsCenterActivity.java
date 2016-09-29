package com.yisheng.ui.mine;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yisheng.adapter.NewsAdapter;
import com.yisheng.business.BaseActivity;
import com.yisheng.business.R;
import com.yisheng.view.recycler.RefreshListView;

import java.util.ArrayList;

/**
 * Created by DY on 2016/9/14.
 */
public class NewsCenterActivity extends BaseActivity implements View.OnClickListener{

    private RefreshListView listView;
    private NewsAdapter adapter;
    private ImageView back;
    private TextView title;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_mine_news_center);
        back =(ImageView)findViewById(R.id.back);
        title =(TextView)findViewById(R.id.title);
        title.setText("消息中心");
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        listView = (RefreshListView)findViewById(R.id.mine_lv);
        adapter = new NewsAdapter(getData(), getApplicationContext());
        listView.setAdapter(adapter);
//        listView.setEnabled(false);
        listView.setOnRefreshListener(new RefreshListView.OnRefreshListener() {

            @Override
            public void onRefresh() {
                listView.onRefreshComplete(true);
            }

            @Override
            public void onLoadMore() {
                // TODO Auto-generated method stub
                listView.onRefreshComplete(true);
            }
        });
    }
    private ArrayList<String> getData() {
        ArrayList<String> mArrayList = new ArrayList<String>();
        for (int i=0;i<20;i++){
            mArrayList.add("测试数据"+i);
        }
        return mArrayList;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
}
