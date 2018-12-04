package com.taoQlegoupeisongduanandroid.delivery.module.init;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taoQlegoupeisongduanandroid.delivery.R;
import com.taoQlegoupeisongduanandroid.delivery.adapter.MyFragmentPagerAdapter;
import com.taoQlegoupeisongduanandroid.delivery.module.base.BaseActivity;
import com.taoQlegoupeisongduanandroid.delivery.module.fragment.errand.ErrandFragment;
import com.taoQlegoupeisongduanandroid.delivery.module.fragment.mine.MineFragment;
import com.taoQlegoupeisongduanandroid.delivery.module.fragment.order.OrderFragment;
import com.taoQlegoupeisongduanandroid.delivery.service.LocationService;
import com.taoQlegoupeisongduanandroid.delivery.service.NetChangeListener;
import com.taoQlegoupeisongduanandroid.delivery.utils.customview.NoScrollViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements NetChangeListener{
//    @BindView(R.id.home_btn_order)Button orderbt;
//    @BindView(R.id.home_btn_errand)Button errandbt;
//    @BindView(R.id.home_btn_me)Button minebt;
    @BindView(R.id.home_viewpager)NoScrollViewPager viewPager;

    private Button[] mTabs;
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private ArrayList<String> titleList = new ArrayList<>();
    private LocationService locationService;
    private LocationService.MyIBinder myIBinder;
    private OrderFragment orderFragment;
    private ErrandFragment errandFragment;
    private MineFragment mineFragment;
    private String extra;

    // 当前fragment的index
    private int currentTabIndex;
    private int index;

    private boolean isExit; // 是否退出
    @Override
    protected void initConentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setToolBar(R.string.title_activity_home);
        setNetListener(this);
        netTost = true;
        hideToolBar();
        //开启定位服务
        startLocationService();
        //orderbt.setSelected(true);
        initView();
        initViewpager();
    }
    /**
     * 初始化组件
     */
    private void initView() {

        mTabs = new Button[3];
        mTabs[0] = (Button) findViewById(R.id.home_btn_order);
        mTabs[1] = (Button) findViewById(R.id.home_btn_errand);
        mTabs[2] = (Button) findViewById(R.id.home_btn_me);

        // 把第一个tab设为选中状态
        mTabs[0].setSelected(true);

    }
    private void initViewpager() {
        orderFragment = new OrderFragment();
        errandFragment = new ErrandFragment();
        mineFragment = MineFragment.getInstance();
        fragmentList.add(orderFragment);
        fragmentList.add(errandFragment);
        fragmentList.add(mineFragment);
        titleList.add("订单");
        titleList.add("跑腿");
        titleList.add("我的");
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),titleList,fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setNoScroll(true);
        if(getIntent().hasExtra("extra")){
            String msg = getIntent().getStringExtra("extra");
            JSONObject jsonObject = JSON.parseObject(msg);
            if(jsonObject.containsKey("redirect_type") && jsonObject.containsKey("redirect_extra")){
                optionRedirect(jsonObject);
            }
        }
    }
    /**
     * button点击事件
     *
     * @param view
     */
    public void onTabClicked(View view) {
        if (view.getId() == R.id.home_btn_order) {
            index = 0;
        } else if (view.getId() == R.id.home_btn_errand) {
            index = 1;
        } else if (view.getId() == R.id.home_btn_me) {
            index = 2;
        }

//        if (currentTabIndex != index) {
//            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
//            trx.hide(fragments[currentTabIndex]);
//            if (!fragments[index].isAdded()) {
//                trx.add(R.id.fragment_container, fragments[index]);
//            }
//            trx.show(fragments[index]).commit();
//        }

        viewPager.setCurrentItem(index);
        mTabs[currentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }
    private void startLocationService() {
        Intent intent = new Intent(mContext, LocationService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        startService(intent);
    }



    @Override
    public void onNetChange(boolean available) {

    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myIBinder = (LocationService.MyIBinder) service;
            locationService = myIBinder.getService();
            locationService.setHandle(handler);
            locationService.startLocation();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            if(data == null) return;
            if(msg.what == 1){
                //TS.show(mContext,data.getString("msg"));
                Log.i("Location",data.getString("msg"));
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void optionRedirect(JSONObject jsonObject) {
        String redirect_type = jsonObject.getString("redirect_type");
        String redirect_extra = jsonObject.getString("redirect_extra");
        if(redirect_type.equals("errander")){
            mTabs[currentTabIndex].setSelected(false);
            currentTabIndex=1;
            // 把当前tab设为选中状态
            mTabs[currentTabIndex].setSelected(true);
            viewPager.setCurrentItem(1);
            if(redirect_extra.equals("1")){
                //跳转到跑腿订单-待抢订单
                errandFragment.redirect(1);
            }else if(redirect_extra.equals("2")){
                //跳转到跑腿订单-取货订单列表
                errandFragment.redirect(2);
            }
        }else if(redirect_type.equals("takeout")){
            mTabs[currentTabIndex].setSelected(false);
            currentTabIndex=0;
            mTabs[currentTabIndex].setSelected(true);
            viewPager.setCurrentItem(0);
            if(redirect_extra.equals("3")){
                //跳转到外卖订单-待抢订单
                orderFragment.redirect(3);
            }else if(redirect_extra.equals("7")){
                //跳转到外卖订单-取货订单列表
                orderFragment.redirect(7);
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);//设置新的intent
        if(getIntent().hasExtra("extra")){
            String msg = getIntent().getStringExtra("extra");
            JSONObject jsonObject = JSON.parseObject(msg);
            if(jsonObject.containsKey("redirect_type") && jsonObject.containsKey("redirect_extra")){
                optionRedirect(jsonObject);
            }
        }
    }
    /**
     * 判断是否退出程序
     */
    public void exit() {

        if (!isExit) {
            isExit = true;
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
    /**
     * 在2秒以内点击则更改Boolean值
     */
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
}
