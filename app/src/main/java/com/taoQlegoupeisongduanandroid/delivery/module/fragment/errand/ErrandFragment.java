package com.taoQlegoupeisongduanandroid.delivery.module.fragment.errand;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taoQlegoupeisongduanandroid.delivery.R;
import com.taoQlegoupeisongduanandroid.delivery.adapter.MyFragmentPagerAdapter;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.LogUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by changxing on 2016/7/27.
 * 跑腿
 */
public class ErrandFragment extends Fragment {
    @BindView(R.id.order_tablayout)TabLayout tabLayout;
    @BindView(R.id.order_viewpager)ViewPager viewPager;
    @BindView(R.id.toolbar_title)TextView title;
    private View rootView;
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private ArrayList<String> titleList = new ArrayList<>();
    private static ErrandFragment instance;

    WaitErrandFragment waitErrandFragment;
    WaitTakeErrandFragment waitTakeErrandFragment;
    DeliveryingErrandFragment deliveryingErrandFragment;
    SuccessErrandFragment successErrandFragment;
    public static ErrandFragment getInstance(){
        if(instance == null)instance = new ErrandFragment();
        return instance;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_order,null);
        ButterKnife.bind(this,rootView);
        initView(rootView);
        title.setText("跑腿订单");
        return rootView;
    }
    private void initView(View view) {
        //添加tab
        TabLayout.Tab tab1 = tabLayout.newTab();
        tab1.setText("待抢");
        tabLayout.addTab(tab1);
        TabLayout.Tab tab2 = tabLayout.newTab();
        tab2.setText("待取货");
        tabLayout.addTab(tab2);
        TabLayout.Tab tab3 = tabLayout.newTab();
        tab3.setText("配送中");
        tabLayout.addTab(tab3);
        TabLayout.Tab tab4 = tabLayout.newTab();
        tab4.setText("配送成功");
        tabLayout.addTab(tab4);
        //设置viewpager
        fragmentList.add(waitErrandFragment=new WaitErrandFragment());
        fragmentList.add(waitTakeErrandFragment=new WaitTakeErrandFragment());
        fragmentList.add(deliveryingErrandFragment=new DeliveryingErrandFragment());
        fragmentList.add(successErrandFragment=new SuccessErrandFragment());
        titleList.add("待抢");
        titleList.add("待取货");
        titleList.add("配送中");
        titleList.add("配送成功");
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getChildFragmentManager(),titleList,fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                LogUtil.d("pager :"+position+" ");
                switch (position){
                    case 0:
                        waitErrandFragment.onRefresh();
                        break;
                    case 1:
                        waitTakeErrandFragment.onRefresh();
                        break;
                    case 2:
                        deliveryingErrandFragment.onRefresh();
                        break;
                    case 3:
                        successErrandFragment.onRefresh();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //将tablayout和viewpager关联起来
        tabLayout.setupWithViewPager(viewPager);
    }

    public void redirect(int i) {
        if(i == 1){
            viewPager.setCurrentItem(0);
        }else if(i== 2){
            viewPager.setCurrentItem(1);
        }
    }
}
