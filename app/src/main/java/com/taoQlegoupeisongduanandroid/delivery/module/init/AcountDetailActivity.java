package com.taoQlegoupeisongduanandroid.delivery.module.init;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.taoQlegoupeisongduanandroid.delivery.R;
import com.taoQlegoupeisongduanandroid.delivery.adapter.MyFragmentPagerAdapter;
import com.taoQlegoupeisongduanandroid.delivery.app.App;
import com.taoQlegoupeisongduanandroid.delivery.module.base.BaseActivity;
import com.taoQlegoupeisongduanandroid.delivery.module.fragment.mine.AllDetailFragment;
import com.taoQlegoupeisongduanandroid.delivery.module.fragment.mine.DeliveryCostFragment;
import com.taoQlegoupeisongduanandroid.delivery.module.fragment.mine.GetCashFragment;
import com.taoQlegoupeisongduanandroid.delivery.module.fragment.mine.OtherDetailFragment;
import com.taoQlegoupeisongduanandroid.delivery.service.NetChangeListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AcountDetailActivity extends BaseActivity implements NetChangeListener{
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private ArrayList<String> titleList = new ArrayList<>();
    @BindView(R.id.acountdetail_tablayout)TabLayout tabLayout;
    @BindView(R.id.acountdetail_viewpager)ViewPager viewPager;
    @Override
    protected void initConentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_acount_detail);
        ButterKnife.bind(this);
        App.getInstance().addHomeTop(this);
        setToolBar(R.string.title_activity_acountdetail);
        setNetListener(this);
        //添加tab
        TabLayout.Tab tab1 = tabLayout.newTab();
        tab1.setText("全部");
        tabLayout.addTab(tab1);
        TabLayout.Tab tab2 = tabLayout.newTab();
        tab2.setText("配送费入账");
        tabLayout.addTab(tab2);
        TabLayout.Tab tab3 = tabLayout.newTab();
        tab3.setText("申请提现");
        tabLayout.addTab(tab3);
        TabLayout.Tab tab4 = tabLayout.newTab();
        tab4.setText("其他变动");
        tabLayout.addTab(tab4);
        //设置viewpager
        fragmentList.add(new AllDetailFragment());
        fragmentList.add(new DeliveryCostFragment());
        fragmentList.add(new GetCashFragment());
        fragmentList.add(new OtherDetailFragment());
        titleList.add("全部");
        titleList.add("配送费入账");
        titleList.add("申请提现");
        titleList.add("其他变动");
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),titleList,fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);

        //将tablayout和viewpager关联起来
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onNetChange(boolean available) {

    }
}
