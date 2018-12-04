package com.taoQlegoupeisongduanandroid.delivery.module.init;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.taoQlegoupeisongduanandroid.delivery.R;
import com.taoQlegoupeisongduanandroid.delivery.app.App;
import com.taoQlegoupeisongduanandroid.delivery.bean.CashDetail;
import com.taoQlegoupeisongduanandroid.delivery.module.base.BaseActivity;
import com.taoQlegoupeisongduanandroid.delivery.service.NetChangeListener;
import com.taoQlegoupeisongduanandroid.delivery.utils.http.RequestSubscriber;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.SP;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.TS;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 交易详情页面
 */
public class CashDetailActivity extends BaseActivity implements NetChangeListener{

    @BindView(R.id.cashdetail_cash)TextView cash;
    @BindView(R.id.cashdetail_curstate)TextView curstate;
    @BindView(R.id.cashdetail_oricash)TextView oricash;//提现金额
    @BindView(R.id.cashdetail_fee)TextView fee; //手续费
    @BindView(R.id.cashdetail_person)TextView person; //提现人
    @BindView(R.id.cashdetail_starttime)TextView startTime;//提现时间
    @BindView(R.id.cashdetail_endtime)TextView endTime;
    @BindView(R.id.cashdetail_realcash)TextView realcash;//实际到账
    @BindView(R.id.cashdetail_orderno)TextView orderNo;
    @BindView(R.id.cashdetail_endtimetitlelayout)RelativeLayout endTimeLayout;

    String id = "";
    @Override
    protected void initConentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cashdetail);
        ButterKnife.bind(this);
        App.getInstance().addHomeTop(this);
        setToolBar(R.string.title_activity_cashdetail);
        setNetListener(this);
        id= getIntent().getStringExtra("cashdetailId");
        getCashDetail();
    }

    /**
     * 获取交易详情
     */
    private void getCashDetail() {
        //i="+API_UNIACID+"&c=entry&do=dytrade&op=getcash_detail&m=we7_wmall
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        map.put("i",App.API_UNIACID);
        map.put("c","entry");
        map.put("do","dytrade");
        map.put("m","we7_wmall");
        map.put("op","getcash_detail");
        map.put("token", (String) SP.get(mContext,"token",""));
        map.put("id",id);
        toSubscribe(apiManager.getApiService().getCashDetail(map).map(new HttpResultFunc<CashDetail>()),
                new RequestSubscriber<CashDetail>(this,this){
                    @Override
                    public void onNext(CashDetail cashDetail) {
                        initData(cashDetail);
                    }
                });

    }

    /**
     * 初始化页面
     * @param cashDetail
     */
    private void initData(CashDetail cashDetail) {

        cash.setText("￥"+cashDetail.getFinal_fee());
        oricash.setText("￥"+cashDetail.getGet_fee());
        fee.setText("￥"+cashDetail.getTake_fee());
        curstate.setText(cashDetail.getStatus_cn());
        startTime.setText(cashDetail.getAddtime_cn());
        endTime.setText(cashDetail.getStatus().equals("1")?cashDetail.getEndtime_cn():"");
        realcash.setText("￥"+cashDetail.getFinal_fee());
        orderNo.setText(cashDetail.getTrade_no());
        person.setText(cashDetail.getTitle());
        if(cashDetail.getStatus().equals("1")){
            endTimeLayout.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.cashdetail_commit)
    void onClick(View view){
        if(isRefreshing())return;
        TS.show(mContext,"查看详情");
    }

    @Override
    public void onNetChange(boolean available) {

    }
}
