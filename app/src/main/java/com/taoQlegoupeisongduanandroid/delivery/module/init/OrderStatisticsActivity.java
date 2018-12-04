package com.taoQlegoupeisongduanandroid.delivery.module.init;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.p_v.flexiblecalendar.FlexibleCalendarView;
import com.taoQlegoupeisongduanandroid.delivery.R;
import com.taoQlegoupeisongduanandroid.delivery.adapter.CommRecyclerAdapter;
import com.taoQlegoupeisongduanandroid.delivery.app.App;
import com.taoQlegoupeisongduanandroid.delivery.bean.CashDetail;
import com.taoQlegoupeisongduanandroid.delivery.bean.OrderStatisticsBean;
import com.taoQlegoupeisongduanandroid.delivery.bean.OrderStatisticsEntity;
import com.taoQlegoupeisongduanandroid.delivery.module.base.BaseActivity;
import com.taoQlegoupeisongduanandroid.delivery.service.NetChangeListener;
import com.taoQlegoupeisongduanandroid.delivery.utils.customview.CalendarPopupWindow;
import com.taoQlegoupeisongduanandroid.delivery.utils.http.RequestSubscriber;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.SP;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.TS;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderStatisticsActivity extends BaseActivity implements NetChangeListener, DatePickerDialog.OnDateSetListener {

    private static final  String TYPE_TODAY = "today";
    private static final  String TYPE_WEEK = "week";
    private static final  String TYPE_MONTH = "month";
    private static final  String TYPE_CUSTOM = "custom";

    private OrderStatisticsEntity orderEntity;
    private TextView curTimeTv;
    private Calendar startCar,endcar;

    @BindView(R.id.radiogroup)RadioGroup radioGroup;
    @BindView(R.id.timelayout)LinearLayout timelayout;
    @BindView(R.id.customlayout)LinearLayout customLayout;
    @BindView(R.id.yye)TextView yye;
    @BindView(R.id.sjbt)TextView sjbt;
    @BindView(R.id.yxddl)TextView yxddl;
    @BindView(R.id.wxddl)TextView wxddl;
    @BindView(R.id.statistics_time)TextView statisticsTime;
    @BindView(R.id.contentlayout)LinearLayout contentLayout;
    @BindView(R.id.custom_starttime)TextView startTime;
    @BindView(R.id.custom_endtime)TextView endTime;

    @Override
    protected void initConentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_order_statistics);
        ButterKnife.bind(this);
        App.getInstance().addHomeTop(this);
        setToolBar(R.string.title_activity_orderstatistics);
        setRefreshEnable(false);
        initView();
        getOrderStatistics(TYPE_TODAY,"","");
    }

    /**
     * 获取订单统计
     * @param type 今天：today, 本周：week, 本月：month, 自定义：custom, 当type= custom时， 需要附加start和end参数
     */
    public void getOrderStatistics(final String type, String start, final String end){
        //i="+API_UNIACID+"&c=entry&do=dystat&op=index&m=we7_wmall
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        map.put("i", App.API_UNIACID);
        map.put("c","entry");
        map.put("m","we7_wmall");
        map.put("do","dystat");
        map.put("op","index");
        map.put("token", (String) SP.get(mContext,"token",""));
        map.put("type",type);
        map.put("start",start);
        map.put("end",end);
        toSubscribe(apiManager.getApiService().getOrderStatistics(map).map(new HttpResultFunc<OrderStatisticsEntity>()),
                new RequestSubscriber<OrderStatisticsEntity>(this,this){
                    @Override
                    public void onNext(OrderStatisticsEntity entity) {
                        orderEntity = entity;
                        contentLayout.setVisibility(View.VISIBLE);
                        yye.setText(""+entity.getTakeout().getNum());
                        sjbt.setText("￥"+entity.getTakeout().getFee());
                        yxddl.setText(""+entity.getErrander().getNum());
                        wxddl.setText("￥"+entity.getErrander().getFee());

                        if(!type.equals(TYPE_CUSTOM)){
                            timelayout.setVisibility(View.VISIBLE);
                            customLayout.setVisibility(View.GONE);
                            statisticsTime.setText("统计时间："+entity.getTime().getStart()+" 到 "+entity.getTime().getEnd());
                        }

                    }
                });
    }

    private void initView() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio1:
                        customLayout.setVisibility(View.GONE);
                        getOrderStatistics(TYPE_TODAY,"","");
                        break;
                    case R.id.radio2:
                        customLayout.setVisibility(View.GONE);
                        getOrderStatistics(TYPE_WEEK,"","");
                        break;
                    case R.id.radio3:
                        customLayout.setVisibility(View.GONE);
                        getOrderStatistics(TYPE_MONTH,"","");
                        break;
                    case R.id.radio4:
                        timelayout.setVisibility(View.GONE);
                        customLayout.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    @OnClick({R.id.custom_starttime,R.id.custom_endtime})
    public void onButtonClick(View view){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog ;
        switch (view.getId()){
            case R.id.custom_starttime:
                curTimeTv = startTime;

                dialog = new DatePickerDialog(mContext, this,
                        calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
                break;
            case R.id.custom_endtime:
                curTimeTv = endTime;
                if(startTime.getText().toString().equals("")){
                    TS.show(mContext,"请先选择开始日期！");
                }else{
                    dialog= new DatePickerDialog(mContext, this,
                            calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH));
                    dialog.show();
                }
                break;

        }
    }



    @Override
    public void onNetChange(boolean available) {

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        if(curTimeTv == endTime){
            endcar = Calendar.getInstance();
            endcar.set(year,monthOfYear,dayOfMonth);
            if(endcar.before(startCar)){
                TS.show(mContext,"结束时间不能早于开始时间！");
            }else{
                curTimeTv.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                getOrderStatistics(TYPE_CUSTOM,startTime.getText().toString(),endTime.getText().toString());
            }
        }else{
            curTimeTv.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
            startCar = Calendar.getInstance();
            startCar.set(year,monthOfYear,dayOfMonth);
        }

    }
}
