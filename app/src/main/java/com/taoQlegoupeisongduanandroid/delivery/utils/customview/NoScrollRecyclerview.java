package com.taoQlegoupeisongduanandroid.delivery.utils.customview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.ListView;

public class NoScrollRecyclerview extends RecyclerView {

	public NoScrollRecyclerview(Context context) {
		super(context);
	}

	public NoScrollRecyclerview(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoScrollRecyclerview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);  
	}  

}  