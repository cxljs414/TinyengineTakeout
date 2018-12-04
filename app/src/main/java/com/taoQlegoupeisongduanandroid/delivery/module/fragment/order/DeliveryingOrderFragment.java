package com.taoQlegoupeisongduanandroid.delivery.module.fragment.order;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.taoQlegoupeisongduanandroid.delivery.R;
import com.taoQlegoupeisongduanandroid.delivery.adapter.CommRecyclerAdapter;
import com.taoQlegoupeisongduanandroid.delivery.adapter.DividerItemDecoration;
import com.taoQlegoupeisongduanandroid.delivery.adapter.ViewHolder;
import com.taoQlegoupeisongduanandroid.delivery.app.App;
import com.taoQlegoupeisongduanandroid.delivery.bean.CommBean;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * Created by changxing on 2016/7/29.
 */
public class DeliveryingOrderFragment extends BaseFragment implements
        NetChangeListener,SwipeRefreshLayout.OnRefreshListener,LoadMoreRecyclerView.LoadMoreListener
{

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

    @BindView(R.id.deliveryingorder_recycleView)LoadMoreRecyclerView recyclerView;
    @BindView(R.id.nodata)LinearLayout noData;
    private CommRecyclerAdapter<OrderBean.ListEntity> adapter;
    private List<OrderBean.ListEntity> mDatas = new ArrayList<>();
    private static WaitTakeOrderFragment instance;

    public static WaitTakeOrderFragment getInstance(){
        if(instance == null)instance = new WaitTakeOrderFragment();
        return instance;
    }

    @Override
    protected View initContentView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_deliveryingorder,null);
        ButterKnife.bind(this,view);
        setNetListener(this);
        setRefreshEnable(true);
        setRefreshListener(this);
        //getOrderList(TYPE_REFRESH,TRADE_TYPE4,min_id+"");
        return view;
    }

    /**
     * 获取明细列表
     * @param type  load 加载更多   /   refresh 刷新
     * @param status   可选值(3:待抢订单,7: 待取货, 4: 配送中(待送达), 5: 配送成功, 6: 配送失败)
     * @param id  最小或者最大id(根据type来确定是最小id还是最大id)
     */
    private void getOrderList(final String type, String status, String id) {
        //i="+API_UNIACID+"&c=entry&do=dyorder&op=list&m=we7_wmall
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
                        LogUtil.d(orderBean.getList().size()+"deliveryorder");
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
                mContext,mDatas,R.layout.list_item_waittakeorder) {
            @Override
            public void convert(ViewHolder holder, final OrderBean.ListEntity entity, final int position) {
                holder.setText(R.id.orderid,"#"+entity.getSerial_sn());
                if(entity.getNote() != null && !entity.getNote().equals("")){
                    holder.setVisible(R.id.notelayout,View.VISIBLE);
                    holder.setText(R.id.note,entity.getNote());
                }else{
                    holder.setVisible(R.id.notelayout,View.GONE);
                }
                holder.setText(R.id.itemwaittakeorder_getkm,"-"+entity.getStore2deliveryer_distance()+"km-");
                holder.setText(R.id.itemwaittakeorder_sendkm,"-"+entity.getStore2user_distance()+"km-");
                holder.setText(R.id.itemwaittakeorder_fee,"￥"+entity.getDeliveryer_fee()+"");
                holder.setText(R.id.itemwaittakeorder_getaddress,entity.getStore().getTitle()+"\n"+entity.getStore().getAddress());
                holder.setText(R.id.itemwaittakeorder_sendaddress,entity.getAddress());
                holder.setText(R.id.itemwaittakeorder_time,entity.getAddtime_cn());
                holder.setText(R.id.itemwaittakeorder_deliverytime,entity.getDelivery_time());

                holder.setVisible(R.id.itemwaittakeorder_custommobilecontain,View.GONE);
                holder.setVisible(R.id.itemwaittakeorder_shopmobilecontain,View.VISIBLE);
                holder.setText(R.id.itemwaittakeorder_shopmobiletile,"客");
                holder.setText(R.id.itemwaittakeorder_shopmobile,entity.getMobile());

                Button button = holder.getView(R.id.itemwaittakeorder_get);
                button.setText("确认送达");
                holder.setOnClickListener(R.id.itemwaittakeorder_root, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, OrderDetailActivity.class);
                        intent.putExtra("id",entity.getId());
                        intent.putExtra("getkm",entity.getStore2deliveryer_distance());
                        intent.putExtra("sendkm",entity.getStore2user_distance());
                        startActivityForResult(intent,REQUEST_CODE);
                    }
                });

                holder.setOnClickListener(R.id.itemwaittakeorder_shopmobilecontain, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommonUtils.telphoneCall(mContext,entity.getMobile());
                    }
                });

                holder.setOnClickListener(R.id.itemwaittakeorder_get, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommonUtils.showDialog(mContext,"确认该订单已送达？",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //i="+API_UNIACID+"&c=entry&do=dyorder&op=success&m=we7_wmall
                                LinkedHashMap<String,String> map = new LinkedHashMap<>();
                                map.put("i", App.API_UNIACID);
                                map.put("c","entry");
                                map.put("m","we7_wmall");
                                map.put("do","dyorder");
                                map.put("op","success");
                                map.put("token", (String) SP.get(mContext,"token",""));
                                map.put("id",entity.getId());
                                toSubscribe(apiManager.getApiService().sureSended(map).map(new HttpResultFunc<CommBean>()),
                                        new Subscriber<CommBean>() {
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
                                            public void onNext(CommBean commBean) {
                                                hideRefresh();
                                                TS.show(mContext,"确认送达 成功");
                                                mDatas.remove(position);
                                                recyclerView.setAdapter(adapter);
                                                if(mDatas.size() == 0){
                                                    recyclerView.setVisibility(View.GONE);
                                                    noData.setVisibility(View.VISIBLE);
                                                }
                                            }
                                        });
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            getOrderList(TYPE_REFRESH,TRADE_TYPE4,"-1");
        }
    }

    @Override
    public void onLoadMore() {
        getOrderList(TYPE_LOADMORE,TRADE_TYPE4,min_id+"");
    }

    @Override
    public void onNetChange(boolean available) {
        if(available)
            getOrderList(TYPE_REFRESH,TRADE_TYPE4,"-1");
    }

    @Override
    public void onRefresh() {
        getOrderList(TYPE_REFRESH,TRADE_TYPE4,"-1");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d("配送中，刷新");
        getOrderList(TYPE_REFRESH,TRADE_TYPE4,min_id+"");
    }
}
