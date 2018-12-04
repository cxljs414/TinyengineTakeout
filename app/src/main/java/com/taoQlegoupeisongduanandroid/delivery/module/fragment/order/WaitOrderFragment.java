package com.taoQlegoupeisongduanandroid.delivery.module.fragment.order;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.taoQlegoupeisongduanandroid.delivery.R;
import com.taoQlegoupeisongduanandroid.delivery.adapter.CommRecyclerAdapter;
import com.taoQlegoupeisongduanandroid.delivery.adapter.DividerItemDecoration;
import com.taoQlegoupeisongduanandroid.delivery.adapter.ViewHolder;
import com.taoQlegoupeisongduanandroid.delivery.app.App;
import com.taoQlegoupeisongduanandroid.delivery.bean.GrapOrderBean;
import com.taoQlegoupeisongduanandroid.delivery.bean.OrderBean;
import com.taoQlegoupeisongduanandroid.delivery.module.fragment.BaseFragment;
import com.taoQlegoupeisongduanandroid.delivery.module.init.OrderDetailActivity;
import com.taoQlegoupeisongduanandroid.delivery.service.NetChangeListener;
import com.taoQlegoupeisongduanandroid.delivery.utils.customview.LoadMoreRecyclerView;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.CommonUtils;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.LogUtil;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.SP;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.TS;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * Created by changxing on 2016/7/29.
 */
public class WaitOrderFragment extends BaseFragment implements
        NetChangeListener,SwipeRefreshLayout.OnRefreshListener,LoadMoreRecyclerView.LoadMoreListener{

    private static final String TYPE_REFRESH = "refresh";
    private static final String TYPE_LOADMORE = "load";
    private static final String TRADE_TYPE3 = "3";
    private static final String TRADE_TYPE7 = "7";
    private static final String TRADE_TYPE4 = "4";
    private static final String TRADE_TYPE5 = "5";
    private static final String TRADE_TYPE6 = "6";
    private static final int REQUEST_CODE = 1;

    private int max_id = -1;
    private int min_id = -1;

    @BindView(R.id.waitorder_recycleView)LoadMoreRecyclerView recyclerView;
    @BindView(R.id.nodata)LinearLayout noData;
    private CommRecyclerAdapter<OrderBean.ListEntity> adapter;
    private List<OrderBean.ListEntity> mDatas = new ArrayList<>();
    private OrderPushReciever reciever;


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what== 1){
                getOrderList(TYPE_REFRESH,TRADE_TYPE3,"-1");
            }
            return true;
        }
    });
    Timer timer;

    TimerTask task = new TimerTask(){
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reciever = new OrderPushReciever();
        IntentFilter filter = new IntentFilter("com.jpush.received");
        mContext.registerReceiver(reciever,filter);
    }

    @Override
    protected View initContentView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_waitorder,null);
        ButterKnife.bind(this,view);
        setNetListener(this);
        setRefreshEnable(true);
        setRefreshListener(this);
        getOrderList(TYPE_REFRESH,TRADE_TYPE3,min_id+"");
        timer = new Timer(true);
        timer.schedule(task,10000, 10000); //延时10000ms后执行，10000ms执行一次
        return view;
    }

    /**
     * 获取明细列表
     * @param type  load 加载更多   /   refresh 刷新
     * @param status   可选值(3:待抢订单,7: 待取货, 4: 配送中(待送达), 5: 配送成功, 6: 配送失败)
     * @param id  最小或者最大id(根据type来确定是最小id还是最大id)
     */
    private void getOrderList(final String type, String status, String id) {
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        map.put("i", App.API_UNIACID);
        map.put("c","entry");
        map.put("m","we7_wmall");
        map.put("do","dyorder");
        map.put("op","list");
        map.put("token", (String) SP.get(mContext,"token",""));
        map.put("status",status);
        map.put("type",type);
        map.put("id",id);
        map.put("aaa","123");
        toSubscribe(apiManager.getApiService().getOrderList(map).map(new HttpResultFunc<OrderBean>()),
                new Subscriber<OrderBean>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        if(type.equals(TYPE_REFRESH))
                            showRefresh();
                    }

                    @Override
                    public void onCompleted() {
                        hideRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideRefresh();
                    }

                    @Override
                    public void onNext(OrderBean orderBean) {

                        LogUtil.d(orderBean.getList().size()+"waitorder");

                        hideRefresh();
                        max_id = orderBean.getMax_id();
                        min_id = orderBean.getMin_id();

                        if(type.equals(TYPE_LOADMORE)){
                            int startIndex = mDatas.size();
                            for(OrderBean.ListEntity entity : orderBean.getList()){
                                mDatas.add(entity);
                            }
                            recyclerView.setAdapter(adapter);
                        }else {
                            mDatas.clear();
                            for(OrderBean.ListEntity entity : orderBean.getList()){
                                mDatas.add(entity);
                            }

                            if(adapter  == null ){
                                initView();
                            }else{
                                recyclerView.setAdapter(adapter);
                            }
                        }

                        if(orderBean.getMore() == 0){
                            recyclerView.setAutoLoadMoreEnable(false);
                        }

                        if(mDatas.size() > 0 ) {
                            recyclerView.setVisibility(View.VISIBLE);
                            noData.setVisibility(View.GONE);
                        } else{
                            recyclerView.setVisibility(View.GONE);
                            noData.setVisibility(View.VISIBLE);
                        }
                    }
                });

    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new CommRecyclerAdapter<OrderBean.ListEntity>(
                mContext,mDatas,R.layout.list_item_waitorder) {
            @Override
            public void convert(ViewHolder holder, final OrderBean.ListEntity entity, final int position) {
                holder.setText(R.id.orderid,"#"+entity.getSerial_sn());
                holder.setText(R.id.itemwaitorder_getkm,"-"+entity.getStore2deliveryer_distance()+"km-");
                holder.setText(R.id.itemwaitorder_sendkm,"-"+entity.getStore2user_distance()+"km-");
                holder.setText(R.id.itemwaitorder_fee,"￥"+entity.getDeliveryer_fee()+"");
                holder.setText(R.id.itemwaitorder_getaddress,entity.getStore().getTitle()+"\n"+entity.getStore().getAddress());
                holder.setText(R.id.itemwaitorder_sendaddress,entity.getAddress());
                holder.setText(R.id.itemwaitorder_time,entity.getAddtime_cn());
                holder.setText(R.id.itemwaitorder_deliverytime,entity.getDelivery_time());
                if(entity.getNote() != null && !entity.getNote().equals("")){
                    holder.setVisible(R.id.notelayout,View.VISIBLE);
                    holder.setText(R.id.note,entity.getNote());
                }else{
                    holder.setVisible(R.id.notelayout,View.GONE);
                }

                holder.setOnClickListener(R.id.itemwaitorder_root, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, OrderDetailActivity.class);
                        intent.putExtra("id",entity.getId());
                        intent.putExtra("getkm",entity.getStore2deliveryer_distance());
                        intent.putExtra("sendkm",entity.getStore2user_distance());
                        startActivityForResult(intent,REQUEST_CODE);
                    }
                });

                holder.setOnClickListener(R.id.itemwaitorder_grab, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isRefreshing())return;
                        CommonUtils.showDialog(mContext, "确认抢单？", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //i="+API_UNIACID+"&c=entry&do=dyorder&op=collect&m=we7_wmall
                                LinkedHashMap<String,String> map = new LinkedHashMap<>();
                                map.put("i", App.API_UNIACID);
                                map.put("c","entry");
                                map.put("m","we7_wmall");
                                map.put("do","dyorder");
                                map.put("op","collect");
                                map.put("token", (String) SP.get(mContext,"token",""));
                                map.put("id",entity.getId());
                                toSubscribe(apiManager.getApiService().getGrapOrder(map).map(new HttpResultFunc<GrapOrderBean>()),
                                        new Subscriber<GrapOrderBean>() {

                                            @Override
                                            public void onStart() {
                                                super.onStart();
                                                showRefresh();
                                            }

                                            @Override
                                            public void onCompleted() {
                                                hideRefresh();
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                hideRefresh();
                                            }

                                            @Override
                                            public void onNext(GrapOrderBean grapOrderBean) {
                                                hideRefresh();
                                                TS.show(mContext,"抢单成功");
                                                mDatas.remove(position);
                                                recyclerView.setAdapter(adapter);
                                                if(mDatas.size() == 0){
                                                    recyclerView.setVisibility(View.GONE);
                                                    noData.setVisibility(View.VISIBLE);
                                                }
                                            }
                                        }
                                );
                            }
                        });

                    }
                });
            }
        };
        recyclerView.setAutoLoadMoreEnable(true);
        recyclerView.setLoadMoreListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public void onRefresh() {
        getOrderList(TYPE_REFRESH,TRADE_TYPE3,"-1");
    }

    @Override
    public void onNetChange(boolean available) {
        if(available)
            getOrderList(TYPE_REFRESH,TRADE_TYPE3,"-1");
    }


    @Override
    public void onLoadMore() {
        getOrderList(TYPE_LOADMORE,TRADE_TYPE3,min_id+"");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                getOrderList(TYPE_REFRESH,TRADE_TYPE3,"-1");
            }
        }
    }

    class OrderPushReciever extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("com.jpush.received")){
                LogUtil.d("订单刷新");
                getOrderList(TYPE_REFRESH,TRADE_TYPE3,"-1");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext.unregisterReceiver(reciever);
    }
}
