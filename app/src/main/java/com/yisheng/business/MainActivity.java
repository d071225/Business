package com.yisheng.business;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yisheng.ui.fragment.MyFragment;
import com.yisheng.ui.fragment.PlanetFragment;
import com.yisheng.ui.fragment.TranslationFragment;
import com.yisheng.utils.ActivityUitls;

import java.util.Timer;
import java.util.TimerTask;


/**
 * 项目的主Activity，所有的Fragment都嵌入在这里。
 *
 * @author
 */
@SuppressLint("NewApi")
public class MainActivity extends BaseActivity implements OnClickListener,MyFragment.TranslogListener {

    /**
     * 用于展示账户的Fragment
     */
    private PlanetFragment planetFragment;

    /**
     * 用于展示 功能 的Fragment
     */
    private TranslationFragment cashFlowFragment;

    /**
     * 用于展示 我的 的Fragment
     */
    private MyFragment myFragment;

    /**
     * 消息界面布局
     */
    private View messageLayout;

    /**
     * 明细查询
     */
    private View cashFlowLayout;
    /**
     * 设置界面布局
     */
    private View settingLayout;

    private ImageView messageImg,cashFlowImg, myImg;

    private TextView messageTxt,cashFlowTxt, myTxt;

    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager fragmentManager;

    private Context mContext;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ActivityUitls.addActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        // 初始化布局元素
        initViews();
        fragmentManager = getFragmentManager();
        setTabSelection(0);
    }

    /**
     * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
     */
    private void initViews() {
//        new SharedPreferencesUtil().AutoLogin(getApplicationContext());
        messageLayout = findViewById(R.id.message_layout);
//		contactsLayout = findViewById(R.id.contacts_layout);
        settingLayout = findViewById(R.id.setting_layout);
        cashFlowLayout=findViewById(R.id.cash_flow_layout);
        cashFlowImg=(ImageView)findViewById(R.id.img_cash_flow);
        cashFlowTxt=(TextView)findViewById(R.id.txt_cash_flow);
        messageImg = (ImageView) findViewById(R.id.img_message);
//        contactsImg = (ImageView) findViewById(R.id.img_contacts);
        myImg = (ImageView) findViewById(R.id.img_my);
        messageTxt = (TextView) findViewById(R.id.txt_message);
//        contactsTxt = (TextView) findViewById(R.id.txt_contacts);
        myTxt = (TextView) findViewById(R.id.txt_my);
        messageLayout.setOnClickListener(this);
        cashFlowLayout.setOnClickListener(this);
//		contactsLayout.setOnClickListener(this);
        settingLayout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        switch (v.getId()) {
            case R.id.message_layout:
                // 当点击了消息tab时，选中第1个tab
                setTabSelection(0);
                break;
            case R.id.cash_flow_layout:
                setTabSelection(1);
                break;
            case R.id.setting_layout:
                // 当点击了设置tab时，选中第4个tab
                setTabSelection(2);
                break;
            default:
                break;
        }
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index
     *
     */
    private void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                // 当点击了消息tab时，改变控件的图片和文字颜色
                messageImg.setImageResource(R.drawable.shouye_selceted);
                messageTxt.setTextColor(mContext.getResources().getColor(
                        R.color.home_ll_bg_color));
                // messageLayout.setBackgroundResource(R.color.main_press);
                // if(planetFragment!=null){
                // transaction.remove(planetFragment);
                // planetFragment=null;
                // }
                 if (planetFragment == null) {
                // 如果MessageFragment为空，则创建一个并添加到界面上
                planetFragment = new PlanetFragment();
                transaction.add(R.id.content, planetFragment);
//                 transaction.add(R.id.content, planetFragment);
                 } else {
                 // 如果MessageFragment不为空，则直接将它显示出来
                 transaction.show(planetFragment);
                 }
                break;
            case 1:

                // 当点击了联系人tab时，改变控件的图片和文字颜色
                cashFlowImg.setImageResource(R.drawable.query_selected);
                cashFlowTxt.setTextColor(mContext.getResources().getColor(
                        R.color.home_ll_bg_color));
                if(cashFlowFragment==null){
                    cashFlowFragment = new TranslationFragment();
                    transaction.add(R.id.content, cashFlowFragment);
                }else{
                    transaction.show(cashFlowFragment);
                }

                break;

            case 2:
            default:
                // 当点击了设置tab时，改变控件的图片和文字颜色
                myImg.setImageResource(R.drawable.mine_selected);
                myTxt.setTextColor(mContext.getResources().getColor(
                        R.color.home_ll_bg_color));
                // settingLayout.setBackgroundResource(R.color.main_press);
                 if (myFragment == null) {
                 // 如果SettingFragment为空，则创建一个并添加到界面上
                myFragment = new MyFragment();
                     myFragment.setOutListener(new MyFragment.OutListener() {
                         @Override
                         public void out() {
                             finish();
                         }
                     });
                // transaction.add(R.id.content, pushFragment);
                transaction.add(R.id.content, myFragment);
                 } else {
                 // 如果SettingFragment不为空，则直接将它显示出来
                 transaction.show(myFragment);
                 }
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        // messageLayout.setBackgroundResource(R.color.main_normal);
        // contactsLayout.setBackgroundResource(R.color.main_normal);
        // settingLayout.setBackgroundResource(R.color.main_normal);
        messageImg.setImageResource(R.drawable.shouye);
        cashFlowImg.setImageResource(R.drawable.query);
        myImg.setImageResource(R.drawable.mine);
        messageTxt.setTextColor(mContext.getResources().getColor(
                R.color.txt_color));
        cashFlowTxt.setTextColor(mContext.getResources().getColor(
                R.color.txt_color));
        myTxt.setTextColor(mContext.getResources().getColor(R.color.txt_color));
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction
     *            用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (planetFragment != null) {
            transaction.hide(planetFragment);
        }
        if (cashFlowFragment != null) {
            transaction.hide(cashFlowFragment);
        }
//        if (serviceFragment != null) {
//            transaction.hide(serviceFragment);
//        }
        if (myFragment != null) {
            transaction.hide(myFragment);
        }
    }

    /* 点击两次退出应用 */
    private static Boolean isExit = false;
    private static Boolean hasTask = false;
    Timer tExit = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            isExit = false;
            hasTask = true;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit == false) {
                isExit = true;
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                if (!hasTask) {
                    tExit.schedule(task, 2000);
                }
            } else {
                finish();
                System.exit(0);
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
//        MobclickAgent.onPause(this);
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    /**
     * 离开页面计时
     */
    private long exitTime = 0;

    @Override
    public void trans() {
        setTabSelection(1);
    }

}
