package com.taoQlegoupeisongduanandroid.delivery.adapter;

import android.content.Context;
import android.support.v4.util.CircularArray;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.taoQlegoupeisongduanandroid.delivery.adapter.BaseLoadingAdapter;
import com.taoQlegoupeisongduanandroid.delivery.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Created by changxing on 2016/7/29.
 */
public abstract class CommRecyclerAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    private List<T> items;
    private int layoutId;
    private Context mContext;
    public CommRecyclerAdapter(Context mContext, List<T> ts, int layoutId) {
        super();
        this.mContext = mContext;
        this.items = ts;
        this.layoutId = layoutId;
    }

    public abstract void  convert(ViewHolder holder,T t,int position);

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.get(mContext,parent,layoutId);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert( holder,items.get(position),position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
