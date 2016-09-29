package com.yisheng.ui.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yisheng.adapter.TranslationAdapter;
import com.yisheng.business.MyApplacation;
import com.yisheng.business.R;
import com.yisheng.domain.QueryRecentlyBean;
import com.yisheng.http.ConnectionConstant;
import com.yisheng.ui.home.TranslationActivity;
import com.yisheng.ui.translation.EverydayDetailActivity;
import com.yisheng.utils.DateUtils;
import com.yisheng.utils.L;
import com.yisheng.utils.SPUtils;
import com.yisheng.utils.T;
import com.yisheng.utils.TranslationStatusUtils;
import com.yisheng.utils.TranslationWayUtils;
import com.yisheng.view.LoadingDialog;
import com.yisheng.view.recycler.RefreshListView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@SuppressLint({"NewApi", "ValidFragment"})
public class TranslationFragment extends Fragment implements View.OnClickListener {

    /**
     * 当前页数
     */
    private int pageNo = 1;
    /**
     * 上下文
     */
    private View rootView;
    private ImageView back;

    private TextView title;

    private int width;
    /**
     * listView
     */
    private int mYear;

    private int mMonth;

    private int mDay;


    private TextView start_date;
    private TextView end_date;
    private RefreshListView listView;
    private TranslationAdapter adapter;
    private LinearLayout pay_path;
    private LinearLayout pay_state;
    private PopupWindow popupWindow;
    private TextView pay_path_tv;
    private TextView pay_state_tv;
    private EditText pay_no;
    private Button btn_query;
    private List<QueryRecentlyBean.QueryRecentlyList> dataList;
    private String payState;
    private String payPath;
    private String before_time;
    private String after_time;
    private LoadingDialog loadingDialog;

    public View onCreateView(final LayoutInflater inflater, android.view.ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.cash_flow, container, false);
        dataList=new ArrayList<QueryRecentlyBean.QueryRecentlyList>();
        listView = (RefreshListView) rootView.findViewById(R.id.translation_lv);
        View headView=View.inflate(getActivity(),R.layout.translation_listview_headview,null);
        listView.addHeaderView(headView);

        InitView();
        return rootView;
    }

    ;
    public void getDataForService(){
        RequestParams params=new RequestParams(ConnectionConstant.QUERYRECENTLYURL);
        L.e("支付渠道:" + payState + "支付状态：" + payPath + "开始时间：" + before_time + "结束时间：" + after_time);
        params.addBodyParameter("merchant_id", (String) SPUtils.get(MyApplacation.getContext(), "custId", ""));
//        params.addBodyParameter("merchant_id", "16090800000605");
        params.addBodyParameter("after_time", after_time);
        params.addBodyParameter("before_time", before_time);
        params.addBodyParameter("order_status", TranslationStatusUtils.stringToNum(payState));
        params.addBodyParameter("pay_channel", TranslationWayUtils.stringToNum(payPath));
        params.addBodyParameter("pageSize", 10+"");
        params.addBodyParameter("page", pageNo+"");
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                L.e("查询成功===》"+result);
                loadingDialog.dismiss();
                Gson gson=new Gson();
                QueryRecentlyBean queryRecentlyBean = gson.fromJson(result, QueryRecentlyBean.class);
                if (queryRecentlyBean.code.equals("0000")){
                    dataList.addAll(queryRecentlyBean.body.orderList);
                }else if(queryRecentlyBean.code.equals("512")){
                    if (pageNo>1){
                        T.showShort(MyApplacation.getContext(), "没有数据了");
                    }else{
                        T.showShort(MyApplacation.getContext(), "没有交易记录");
                    }
                }
                adapter.notifyDataSetChanged();
                listView.onRefreshComplete(true);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                L.e("查询失败===》");
                T.showLong(MyApplacation.getContext(), "网络连接失败");
                loadingDialog.dismiss();
                listView.onRefreshComplete(true);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                loadingDialog.dismiss();
                listView.onRefreshComplete(true);
            }

            @Override
            public void onFinished() {
                loadingDialog.dismiss();
                listView.onRefreshComplete(true);
            }
        });
    }


    public void InitView() {
        back = (ImageView) rootView.findViewById(R.id.back);
        back.setVisibility(View.GONE);
        title = (TextView) rootView.findViewById(R.id.title);
        title.setText("交易查询");
        start_date = (TextView) rootView.findViewById(R.id.start_date);
        end_date = (TextView) rootView.findViewById(R.id.end_date);
        pay_path_tv = (TextView) rootView.findViewById(R.id.pay_path_tv);
        pay_state_tv = (TextView) rootView.findViewById(R.id.pay_state_tv);
        pay_no = (EditText) rootView.findViewById(R.id.pay_no);
        pay_path = (LinearLayout) rootView.findViewById(R.id.pay_path);
        pay_state = (LinearLayout) rootView.findViewById(R.id.pay_state);
        btn_query = (Button) rootView.findViewById(R.id.btn_query);
        btn_query.setOnClickListener(this);
        start_date.setOnClickListener(this);
        end_date.setOnClickListener(this);
        pay_path.setOnClickListener(this);
        pay_state.setOnClickListener(this);
//        getDataForService();
        adapter = new TranslationAdapter(dataList, getActivity());
        listView.setAdapter(adapter);
        loadingDialog = new LoadingDialog(getActivity());
        listviewClick();
        setDateTime();
    }

    private void listviewClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), EverydayDetailActivity.class);
                intent.putExtra("dateTime", dataList.get(position).dateTime);
                intent.putExtra("pay_channel",payPath);
                intent.putExtra("order_status",payState);
                startActivity(intent);
            }
        });
        listView.setOnRefreshListener(new RefreshListView.OnRefreshListener() {

            @Override
            public void onRefresh() {

                MyApplacation.getmHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageNo = 1;
                        dataList.clear();
                        getDataForService();
                        adapter.notifyDataSetChanged();
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore() {
                // TODO Auto-generated method stub
                MyApplacation.getmHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageNo++;
                        getDataForService();
                        adapter.notifyDataSetChanged();
                    }
                }, 1000);

            }
        });
    }


    private void setDateTime() {

        final Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);

        mMonth = c.get(Calendar.MONTH);

        mDay = c.get(Calendar.DAY_OF_MONTH);
        createDisplay();

    }

    /**
     * 初始日期
     */

    private void createDisplay() {
        before_time = getDate();
        after_time = getDate();
        start_date.setText(mYear + "年"
                + ((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)) + "月"
                + ((mDay < 10) ? "0" + mDay : mDay) + "日");
        end_date.setText(mYear + "年"
                + ((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)) + "月"
                + ((mDay < 10) ? "0" + mDay : mDay) + "日");

    }

    /**
     * 更新日期
     */

    private void updateDisplay() {
        if (rv == 0) {
            after_time = getDate();
            start_date.setText(mYear + "年"
                    + ((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
                    + "月" + ((mDay < 10) ? "0" + mDay : mDay) + "日");
        } else {
            before_time = getDate();
            end_date.setText(mYear + "年"
                    + ((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
                    + "月" + ((mDay < 10) ? "0" + mDay : mDay) + "日");
        }

    }
    public String getDate(){
        return mYear+""+((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))+""+((mDay < 10) ? "0" + mDay : mDay);
    }

    int rv = 0;

    //
    public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO 日期选择完成事件，取消时不会触发
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDisplay();
//	       MyLog.d("Debug", "year=" + year + ",monthOfYear=" + monthOfYear + ",dayOfMonth=" + dayOfMonth);
        }

        private String getValue() {
            return "" + mYear + mMonth + mDay;
        }

    }

    //
    private void showDatePickerFragemnt() {
        DialogFragment fragment = new DatePickerFragment();
        fragment.show(getFragmentManager(), "datePicker");
    }

    protected void showPopwindow(View v, final String[] s, final int type) {
        // TODO Auto-generated method stub
        View view=View.inflate(getActivity(), R.layout.translation_dialog_item, null);
        ListView lv=(ListView) view.findViewById(R.id.dilog_lv);
        lv.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.more_popupwindow_listview_item,s));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(type==1){
                    payState = s[position];
                    pay_state_tv.setText(s[position]);
                }else{
                    payPath = s[position];
                    pay_path_tv.setText(s[position]);
                }
                dismissPopupWindow();
            }
        });
        popupWindow = new PopupWindow(view, v.getWidth(),
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
//        popupWindow.showAsDropDown(v, DensityUtils.dp2px(getActivity(),10), -v.getHeight());
        popupWindow.showAsDropDown(v);
    }
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
		case R.id.btn_query:
            if (pay_no.getText().toString().trim().equals("")){
                L.e("开始时间===》"+start_date.getText().toString()+"结束时间===》"+end_date.getText().toString());
                if (DateUtils.DateCompare(start_date.getText().toString(),
                        end_date.getText().toString())) {
                    if(DateUtils.DateMonthCompare(start_date.getText().toString(),
                            end_date.getText().toString())){
                        dataList.clear();
                        loadingDialog.show();
                        getDataForService();
                    }else{
                        T.showLong(MyApplacation.getContext(), "只能查询一个月内的交易记录，请重新选择查询日期");
                    }

//                    Intent intent=new Intent(getActivity(), EverydayDetailActivity.class);
//                    intent.putExtra("pay_channel",payPath);
//                    intent.putExtra("order_status",payState);
//                    intent.putExtra("before_time",before_time);
//                    intent.putExtra("after_time", after_time);
//            L.e("支付渠道:"+payPath+"支付状态："+payState+"结束时间："+before_time+"开始时间："+after_time);
//                    startActivity(intent);
                }else{
                    T.showShort(MyApplacation.getContext(), "结束时间要大于起始时间");
                }
            }else{
                Intent intent=new Intent(getActivity(), TranslationActivity.class);
                intent.putExtra("prdordno",pay_no.getText().toString().trim());
                startActivity(intent);
            }
            break;
		case R.id.pay_path:
            String[] path={"支付宝","微信","全部"};
            showPopwindow(pay_path,path,2);
            break;
		case R.id.pay_state:
            String[] state={"支付成功","支付失败","全部"};
            showPopwindow(pay_state,state,1);
            break;
            case R.id.start_date:
                rv = 0;
//			((Activity) mContext).showDialog(DATE_DIALOG_ID);
                showDatePickerFragemnt();
                break;
            case R.id.end_date:
                rv = 1;
                showDatePickerFragemnt();
//			((Activity) mContext).showDialog(DATE_DIALOG_ID);
                break;
            default:
                break;
        }
    }

    private void dismissPopupWindow() {
        if(popupWindow!=null){
            popupWindow.dismiss();
        }
    }

}
