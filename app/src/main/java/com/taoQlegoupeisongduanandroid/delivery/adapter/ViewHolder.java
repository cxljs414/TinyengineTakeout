package com.taoQlegoupeisongduanandroid.delivery.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder extends RecyclerView.ViewHolder{

	private SparseArray<View> mViews;
	private View mConvertView;
	private Context mContext;
	public ViewHolder(Context context, ViewGroup parent, View itemView) {
		super(itemView);
		this.mViews=new SparseArray<View>();
		this.mContext = context;
		mConvertView = itemView;
	}
	public static ViewHolder get(Context context, ViewGroup parent, int layoutId){
		View itemView = LayoutInflater.from(context).inflate(layoutId,parent,false);
		return new ViewHolder(context,parent,itemView);
	}

	public <T extends View> T getView(int viewId){
		
		View view=mViews.get(viewId);
		if(view==null){
			view =mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
			
		}
		return (T) view;
	}
	public ViewHolder setOnClickListener(int viewId, View.OnClickListener listener){
		View view = getView(viewId);
		view.setOnClickListener(listener);
		return this;
	}

	public ViewHolder setVisible(int viewId,int avisible){
		View view = getView(viewId);
		view.setVisibility(avisible);
		return this;
	}
	
	public ViewHolder setText(int viewId,String text){
		
		TextView tv=getView(viewId);
		tv.setText(text);
		return this;
	}
	public ViewHolder setImageResource(int viewId ,int resId){
		ImageView tv=getView(viewId);
		tv.setImageResource(resId);
		return this;
	}
	public ViewHolder setImageBitmap(int viewId ,Bitmap bm){
		ImageView tv=getView(viewId);
		tv.setImageBitmap(bm);
		return this;
	}
	public ViewHolder setImageUrl(int viewId , String url){
		ImageView view=getView(viewId);
		
		//自己写的图片加载类 用来加载本地或者网络图片
		//ImageLoader.getInstance().load(view,url);
		return this;
	}
	public ViewHolder setImageButtoBacground(int viewId , int resId ){
		ImageButton view=getView(viewId);
		view.setBackgroundResource(resId);
		return this;
	}

	public View getConvertView(){

		return mConvertView;
	}


}
