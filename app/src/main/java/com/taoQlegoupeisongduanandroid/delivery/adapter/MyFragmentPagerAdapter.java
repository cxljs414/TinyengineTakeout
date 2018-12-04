package com.taoQlegoupeisongduanandroid.delivery.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;


public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragmentsList;
    private ArrayList<String> titleList = new ArrayList<>();
    public MyFragmentPagerAdapter(FragmentManager fm,ArrayList<String> titleList, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragmentsList = fragments;
        this.titleList = titleList;
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }

    @Override
    public Fragment getItem(int arg0) {
        return fragmentsList.get(arg0);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

}
