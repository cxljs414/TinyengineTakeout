package com.taoQlegoupeisongduanandroid.delivery.utils.http;

import android.app.Activity;
import android.content.Context;

import com.lidroid.xutils.exception.HttpException;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.TS;

import rx.Subscriber;

/**
 * Created by changxing on 2016/7/27.
 */
public class RequestSubscriber<T> extends Subscriber<T> {
    OnRefreshListener listener;
    Context mContext;
    public RequestSubscriber(Context mContext,OnRefreshListener listener) {
        this.listener = listener;
        this.mContext = mContext;
    }

    @Override
    public void onStart() {
        super.onStart();
        listener.onRefreshing();
    }

    @Override
    public void onCompleted() {
        listener.onRereshStop();
    }

    @Override
    public void onError(Throwable e) {
        if(e instanceof RuntimeException){
            TS.show(mContext,e.getMessage());
        }else{
            TS.show(mContext,"请求失败");
        }
        listener.onRereshStop();
    }

    @Override
    public void onNext(T t) {
        listener.onRereshStop();
    }
}
