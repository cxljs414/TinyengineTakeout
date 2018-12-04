package com.taoQlegoupeisongduanandroid.delivery.module.fragment.mine;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.taoQlegoupeisongduanandroid.delivery.R;
import com.taoQlegoupeisongduanandroid.delivery.adapter.CommRecyclerAdapter;
import com.taoQlegoupeisongduanandroid.delivery.adapter.ViewHolder;
import com.taoQlegoupeisongduanandroid.delivery.app.App;
import com.taoQlegoupeisongduanandroid.delivery.bean.AllDetailBean;
import com.taoQlegoupeisongduanandroid.delivery.module.fragment.BaseFragment;
import com.taoQlegoupeisongduanandroid.delivery.module.init.CashDetailActivity;
import com.taoQlegoupeisongduanandroid.delivery.module.init.OrderDetailActivity;
import com.taoQlegoupeisongduanandroid.delivery.service.NetChangeListener;
import com.taoQlegoupeisongduanandroid.delivery.utils.customview.LoadMoreRecyclerView;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.SP;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.TS;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * 全部
 * Created by changxing on 2016/7/27.
 */
public class AllDetailFragment extends BaseFragment implements
        NetChangeListener,SwipeRefreshLayout.OnRefreshListener,LoadMoreRecyclerView.LoadMoreListener{

    private static final String TYPE_REFRESH = "refresh";
    private static final String TYPE_LOADMORE = "load";
    private static final String TRADE_TYPE0 = "0";//0全部
    private static final String TRADE_TYPE1 = "1";//配送费入账
    private static final String TRADE_TYPE2 = "2";//申请提现
    private static final String TRADE_TYPE3 = "3";//其他变动

    private int max_id = -1;
    private int min_id = -1;

    @BindView(R.id.alldetail_recycleView)LoadMoreRecyclerView recyclerView;
    @BindView(R.id.nodata)LinearLayout noData;
    private CommRecyclerAdapter<AllDetailBean.ListEntity> adapter;
    private List<AllDetailBean.ListEntity> mDatas = new ArrayList<>();
    @Override
    protected View initContentView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_alldetail,null);
        ButterKnife.bind(this,view);
        setNetListener(this);
        setRefreshEnable(true);
        setRefreshListener(this);
        getAllDetail(TYPE_REFRESH,TRADE_TYPE0,min_id+"");
        return view;
    }

    /**
     * 获取明细列表
     * @param type  load 加载更多   /   refresh 刷新
     * @param tradeType   交易类型,（０：　不限，　１：　配送费入账，　２：　申请提现，　３：其他变动）
     * @param id  最小或者最大id(根据type来确定是最小id还是最大id)
     */
    private void getAllDetail(final String type, final String tradeType, String id) {
        //i="+API_UNIACID+"&c=entry&do=dytrade&op=inout&m=we7_wmall
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        map.put("i", App.API_UNIACID);
        map.put("c","entry");
        map.put("m","we7_wmall");
        map.put("do","dytrade");
        map.put("op","inout");
        map.put("token", (String) SP.get(mContext,"token",""));
        map.put("type",type);
        map.put("trade_type",tradeType);
        map.put("id",id);

        toSubscribe(apiManager.getApiService().getAllDetail(map).map(new HttpResultFunc<AllDetailBean>()),
                new Subscriber<AllDetailBean>() {

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
                    public void onNext(AllDetailBean allDetailBean) {
                        hideRefresh();
                        max_id = allDetailBean.getMax_id();
                        min_id = allDetailBean.getMin_id();
                        if(type.equals(TYPE_LOADMORE)){
                            if(allDetailBean.getList().size() == 0){
                                recyclerView.notifyMoreFinish(false);
                                TS.show(mContext,"没有更多了");
                            }else{
                                recyclerView.notifyMoreFinish(true);
                            }
                            int startIndex = mDatas.size();
                            for(AllDetailBean.ListEntity entity : allDetailBean.getList()){
                                mDatas.add(entity);
                            }
                            adapter.notifyItemRangeInserted(startIndex, allDetailBean.getList().size());
                        }else {
                            mDatas.clear();
                            for(AllDetailBean.ListEntity entity : allDetailBean.getList()){
                                mDatas.add(entity);
                            }

                            if(adapter  == null){
                                initView();
                            }else{
                                adapter.notifyItemRangeInserted(0, allDetailBean.getList().size());
                            }
                            if(allDetailBean.getMore() == 0){
                                recyclerView.setAutoLoadMoreEnable(false);
                            }

                        }
                    }
                });

    }

    private void initView() {
        if(mDatas.size() > 0 ) {
            recyclerView.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);
        } else{
            recyclerView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new CommRecyclerAdapter<AllDetailBean.ListEntity>(
                mContext,mDatas,R.layout.list_item_alldetail) {
            @Override
            public void convert(ViewHolder holder, final AllDetailBean.ListEntity entity, final int position) {
                holder.setText(R.id.alldetailitem_tradetype,entity.getTrade_type_cn());
                holder.setText(R.id.alldetailitem_fee,entity.getFee());
                holder.setText(R.id.alldetailitem_addtime,entity.getAddtime_cn());
                holder.setText(R.id.alldetailitem_amount,"￥"+entity.getAmount());
                holder.setOnClickListener(R.id.alldetailitem_root, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(entity.getTrade_type().equals(TRADE_TYPE2)){//交易详情
                            Intent intent = new Intent(mContext, CashDetailActivity.class);
                            intent.putExtra("cashdetailId",entity.getExtra());
                            startActivity(intent);
                        }else if(entity.getTrade_type().equals(TRADE_TYPE1)){
                            Intent intent = new Intent(mContext, OrderDetailActivity.class);
                            intent.putExtra("id",entity.getExtra());
                            startActivity(intent);
                        }

                    }
                });
            }
        };
        recyclerView.setAutoLoadMoreEnable(true);
        recyclerView.setLoadMoreListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onRefresh() {
        getAllDetail(TYPE_REFRESH,TRADE_TYPE0,"-1");
    }

    @Override
    public void onNetChange(boolean available) {
        if(available)
            getAllDetail(TYPE_REFRESH,TRADE_TYPE0,"-1");
    }


    @Override
    public void onLoadMore() {
        getAllDetail(TYPE_LOADMORE,TRADE_TYPE0,min_id+"");
    }
}
