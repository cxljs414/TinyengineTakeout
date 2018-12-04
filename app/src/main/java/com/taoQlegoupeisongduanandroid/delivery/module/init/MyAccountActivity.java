package com.taoQlegoupeisongduanandroid.delivery.module.init;

import android.os.Bundle;

import com.taoQlegoupeisongduanandroid.delivery.R;
import com.taoQlegoupeisongduanandroid.delivery.module.base.BaseActivity;

import butterknife.ButterKnife;

public class MyAccountActivity extends BaseActivity {

    @Override
    protected void initConentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_account);
        ButterKnife.bind(this);
        setToolBar(R.string.title_activity_myaccount);
    }
}
