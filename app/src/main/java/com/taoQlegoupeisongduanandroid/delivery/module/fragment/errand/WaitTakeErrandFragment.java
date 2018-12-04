package com.taoQlegoupeisongduanandroid.delivery.module.fragment.errand;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taoQlegoupeisongduanandroid.delivery.R;
import com.taoQlegoupeisongduanandroid.delivery.adapter.CommRecyclerAdapter;
import com.taoQlegoupeisongduanandroid.delivery.adapter.DividerItemDecoration;
import com.taoQlegoupeisongduanandroid.delivery.adapter.ViewHolder;
import com.taoQlegoupeisongduanandroid.delivery.app.App;
import com.taoQlegoupeisongduanandroid.delivery.bean.CommBean;
import com.taoQlegoupeisongduanandroid.delivery.bean.ErrandEntity;
import com.taoQlegoupeisongduanandroid.delivery.module.fragment.BaseFragment;
import com.taoQlegoupeisongduanandroid.delivery.module.init.ErrandOrderDetailActivity;
import com.taoQlegoupeisongduanandroid.delivery.service.NetChangeListener;
import com.taoQlegoupeisongduanandroid.delivery.utils.customview.LoadMoreRecyclerView;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.CommonUtils;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.SP;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.TS;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

import static com.taoQlegoupeisongduanandroid.delivery.module.init.ErrandOrderDetailActivity.GETTHING_ACTION;

/**
 * 跑腿-待取货
 * Created by changxing on 2016/7/29.
 */
public class WaitTakeErrandFragment extends BaseFragment implements
        NetChangeListener,SwipeRefreshLayout.OnRefreshListener,LoadMoreRecyclerView.LoadMoreListener
{

    private static final String TYPE_REFRESH = "refresh";
    private static final String TYPE_LOADMORE = "load";
    private static final String TRADE_TYPE1 = "1";//待抢订单
    private static final String TRADE_TYPE2 = "2";//待取货
    private static final String TRADE_TYPE3 = "3";//配送中
    private static final String TRADE_TYPE4 = "4";//配送成功
    private static final int REQUEST_CODE = 1;
    private int max_id = -1;
    private int min_id = -1;

    @BindView(R.id.waittakeorder_recycleView)LoadMoreRecyclerView recyclerView;
    @BindView(R.id.nodata)LinearLayout noData;
    private CommRecyclerAdapter<ErrandEntity.ListEntity> adapter;
    private List<ErrandEntity.ListEntity> mDatas = new ArrayList<>();



    private static WaitTakeErrandFragment instance;

    public static WaitTakeErrandFragment getInstance(){
        if(instance == null)instance = new WaitTakeErrandFragment();
        return instance;
    }

    @Override
    protected View initContentView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_waittakeorder,null);
        ButterKnife.bind(this,view);
        setNetListener(this);
        setRefreshEnable(true);
        setRefreshListener(this);

        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(ErrandOrderDetailActivity.TRANSFER_ACTION);
        myIntentFilter.addAction(ErrandOrderDetailActivity.GETTHING_ACTION);


        //注册广播
        getActivity().registerReceiver(broadcastReceiver, myIntentFilter);
        getOrderList(TYPE_REFRESH,TRADE_TYPE2,min_id+"");
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
        map.put("do","dyorder-errander");
        map.put("op","list");
        map.put("token", (String) SP.get(mContext,"token",""));
        map.put("status",status);
        map.put("type",type);
        map.put("id",id);
        toSubscribe(apiManager.getApiService().getErrandOrderList(map).map(new HttpResultFunc<ErrandEntity>()),
                new Subscriber<ErrandEntity>() {

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
                    public void onNext(ErrandEntity orderBean) {
                        hideRefresh();
                        max_id = orderBean.getMax_id();
                        min_id = orderBean.getMin_id();
                        if(type.equals(TYPE_LOADMORE)){
                            int startIndex = mDatas.size();
                            mDatas.addAll(orderBean.getList());
                            recyclerView.setAdapter(adapter);
                        }else {
                            mDatas.clear();
                            mDatas.addAll(orderBean.getList());

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
        adapter = new CommRecyclerAdapter<ErrandEntity.ListEntity>(
                mContext,mDatas,R.layout.list_item_waittakeorder) {
            @Override
            public void convert(ViewHolder holder, final ErrandEntity.ListEntity entity, final int position) {
                holder.setText(R.id.orderid,"#"+entity.getId());
                if(entity.getNote() != null && !entity.getNote().equals("")){
                    holder.setVisible(R.id.notelayout,View.VISIBLE);
                    holder.setText(R.id.note,entity.getNote());
                }else{
                    holder.setVisible(R.id.notelayout,View.GONE);
                }
                holder.setText(R.id.itemwaittakeorder_getkm,"-"+entity.getStore2user_distance()+"km-");
                holder.setText(R.id.itemwaittakeorder_sendkm,"-"+entity.getStore2deliveryer_distance()+"km-");
                holder.setText(R.id.itemwaittakeorder_fee,"￥"+entity.getDeliveryer_total_fee()+"");
                holder.setText(R.id.accetp_title,entity.getBuy_address_title()+":");
                holder.setText(R.id.send_title,entity.getAccept_address_title()+":");
                holder.setText(R.id.itemwaittakeorder_getaddress,entity.getBuy_address());
                holder.setText(R.id.itemwaittakeorder_sendaddress,entity.getAccept_address());
                holder.setText(R.id.itemwaittakeorder_time,entity.getAddtime_cn());
                holder.setText(R.id.itemwaittakeorder_deliverytime,entity.getDelivery_time());
                holder.setText(R.id.itemwaittakeorder_shopmobile,entity.getAccept_mobile());

                holder.setVisible(R.id.goodlayout,View.VISIBLE);
                holder.setText(R.id.itemwaitorder_goodname,entity.getGoods_name());


                holder.setText(R.id.order_typetv,entity.getOrder_type_cn());

                holder.setText(R.id.order_classtv,entity.getTitle());
                holder.setVisible(R.id.order_classtv,View.VISIBLE);
                TextView goodtitle = holder.getView(R.id.goods_title);
                TextView orderType = holder.getView(R.id.order_typetv);
                orderType.setVisibility(View.VISIBLE);
                //显示取货联系人手机号，客改成取，颜色蓝色
                holder.setVisible(R.id.itemwaittakeorder_shopmobilecontain,View.GONE);
                holder.setVisible(R.id.itemwaittakeorder_custommobilecontain,View.VISIBLE);
                if(entity.getOrder_type_cn().equals("随意购")){
                    orderType.setTextColor(getResources().getColor(R.color.syg_color));
                    orderType.setBackgroundResource(R.drawable.background_round_syg);
                    goodtitle.setTextColor(getResources().getColor(R.color.syg_color));
                    //随意购直接是收货人的手机号,accept_mobile
                    holder.setText(R.id.itemwaittakeorder_custommobiletile,"收");
                    holder.setText(R.id.itemwaittakeorder_custommobile,entity.getAccept_mobile());
                    holder.setOnClickListener(R.id.itemwaittakeorder_custommobilecontain, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CommonUtils.telphoneCall(mContext,entity.getAccept_mobile());
                        }
                    });

                }else if(entity.getOrder_type_cn().equals("快速送")){
                    orderType.setTextColor(getResources().getColor(R.color.kss_color));
                    orderType.setBackgroundResource(R.drawable.background_round_kss);
                    goodtitle.setTextColor(getResources().getColor(R.color.kss_color));

                    holder.setText(R.id.itemwaittakeorder_custommobiletile,"取");
                    holder.setText(R.id.itemwaittakeorder_custommobile,entity.getBuy_mobile());

                    holder.setOnClickListener(R.id.itemwaittakeorder_custommobilecontain, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CommonUtils.telphoneCall(mContext,entity.getBuy_mobile());
                        }
                    });

                }else if(entity.getOrder_type_cn().equals("快速取")){
                    orderType.setTextColor(getResources().getColor(R.color.ksq_color));
                    orderType.setBackgroundResource(R.drawable.background_round_ksq);
                    goodtitle.setTextColor(getResources().getColor(R.color.ksq_color));

                    //显示取货联系人手机号，客改成取，颜色蓝色
                    holder.setText(R.id.itemwaittakeorder_custommobiletile,"取");
                    holder.setText(R.id.itemwaittakeorder_custommobile,entity.getBuy_mobile());
                    holder.setOnClickListener(R.id.itemwaittakeorder_custommobilecontain, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CommonUtils.telphoneCall(mContext,entity.getBuy_mobile());
                        }
                    });
                }
                holder.setOnClickListener(R.id.itemwaittakeorder_root, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ErrandOrderDetailActivity.class);
                        intent.putExtra("id",entity.getId());
                        intent.putExtra("getkm",entity.getStore2deliveryer_distance());
                        intent.putExtra("sendkm",entity.getStore2user_distance());
                        startActivity(intent);
                    }
                });


                holder.setText(R.id.itemwaittakeorder_get,"我已取货");
                holder.setOnClickListener(R.id.itemwaittakeorder_get, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommonUtils.showDialog(mContext,"确认已取货？",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //i="+API_UNIACID+"&c=entry&do=dyorder-errander&op=instore&m=we7_wmall
                                LinkedHashMap<String,String> map = new LinkedHashMap<>();
                                map.put("i", App.API_UNIACID);
                                map.put("c","entry");
                                map.put("m","we7_wmall");
                                map.put("do","dyorder-errander");
                                map.put("op","instore");
                                map.put("token", (String) SP.get(mContext,"token",""));
                                map.put("id",entity.getId());
                                toSubscribe(apiManager.getApiService().getErrandnstoreOrderDetail(map).map(new HttpResultFunc<CommBean>()),
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
                                                TS.show(mContext,"确认取货成功");
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
        recyclerView.setAutoLoadMoreEnable(false);
        recyclerView.setLoadMoreListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public void onLoadMore() {
        getOrderList(TYPE_LOADMORE,TRADE_TYPE2,min_id+"");
    }

    @Override
    public void onNetChange(boolean available) {
        if(available)
            getOrderList(TYPE_REFRESH,TRADE_TYPE2,"-1");
    }

    @Override
    public void onRefresh() {
        getOrderList(TYPE_REFRESH,TRADE_TYPE2,"-1");
    }
    private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ErrandOrderDetailActivity.TRANSFER_ACTION)||action.equals(GETTHING_ACTION)) {
                RefreshData();
            }
        }
    };
    public void RefreshData(){
        getOrderList(TYPE_REFRESH,TRADE_TYPE2,"-1");
    }
}
