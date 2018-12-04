package com.taoQlegoupeisongduanandroid.delivery.module.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taoQlegoupeisongduanandroid.delivery.R;
import com.taoQlegoupeisongduanandroid.delivery.bean.HttpResult;
import com.taoQlegoupeisongduanandroid.delivery.service.NetChangeListener;
import com.taoQlegoupeisongduanandroid.delivery.utils.http.ApiManager;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.CommonUtils;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.TS;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by changxing on 2016/7/27.
 */
public abstract class BaseFragment extends Fragment {
    protected Context mContext;
    private NetChangeListener netListener;
    protected Subscription subscription;
    protected ApiManager apiManager ;
    private boolean IsRefreshing = false;
    private NetReceiver receiver;

    @BindView(R.id.fragment_refresh)SwipeRefreshLayout refresh;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        apiManager = new ApiManager();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = initContentView(inflater);
        ButterKnife.bind(this,view);
        //首先判断是否有网络，如果没有显示没有网络
        if(!CommonUtils.isNetworkAvailable(mContext)){
            TS.show(mContext,"网络连接不可用");
        }
        return view;
    }

    protected abstract View initContentView(LayoutInflater inflater);

    public void setNetListener(NetChangeListener netListener) {
        this.netListener = netListener;
        receiver = new NetReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mContext.registerReceiver(receiver,filter);
    }

    public void setRefreshListener(SwipeRefreshLayout.OnRefreshListener listener){
        refresh.setOnRefreshListener(listener);
    }

    protected void unSubscribe(){
        if(subscription != null && subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }
    /**
     * 显示进度条
     */
    protected void showRefresh(){
        refresh.setRefreshing(true);
        IsRefreshing = true;
    }

    protected  boolean isRefreshing(){
        return IsRefreshing;
    }

    protected void setRefreshEnable(boolean enable){
        refresh.setEnabled(enable);
    }

    /**
     * 隐藏进度条
     */
    protected void hideRefresh(){
        refresh.setRefreshing(false);
        IsRefreshing = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideRefresh();
        unSubscribe();
        if(receiver != null)
            mContext.unregisterReceiver(receiver);
    }

    protected  <T> void toSubscribe(Observable<T> o, Subscriber<T> s){
        subscription =  o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     * @param <T>   Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    public class HttpResultFunc<T> implements Func1<HttpResult<T>,T> {

        @Override
        public T call(HttpResult<T> tHttpResult) {
            if(tHttpResult.getMessage().getResultCode() != 0){
                throw new RuntimeException(tHttpResult.getMessage().getResultMessage());
            }
            return tHttpResult.getMessage().getData();
        }
    }

    public class NetReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            /**
             * 监听网络变化
             */
            if(intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")){
                if(CommonUtils.isNetworkAvailable(context)){
                    //如果从无网络变成有网络
                    netListener.onNetChange(true);
                }else{
                    TS.show(mContext,"网络连接不可用");
                    netListener.onNetChange(false);
                }
            }
        }
    }
}
