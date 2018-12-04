package com.taoQlegoupeisongduanandroid.delivery.utils.customview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.p_v.flexiblecalendar.FlexibleCalendarView;
import com.p_v.flexiblecalendar.entity.SelectedDateItem;
import com.p_v.flexiblecalendar.view.BaseCellView;
import com.p_v.flexiblecalendar.view.SquareCellView;
import com.taoQlegoupeisongduanandroid.delivery.R;

/**
 * Created by changxing on 2016/8/8.
 */
public class CalendarPopupWindow extends PopupWindow {
    private View rootView; // 总的布局
    FlexibleCalendarView calendarView;
    private  Context mContext;
    private TextView curMonth;
    private ImageView pre,next;
    public CalendarPopupWindow(Context context) {
        super(context);
        this.mContext = context;
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setBackgroundDrawable(new BitmapDrawable());// 这样设置才能点击屏幕外dismiss窗口
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.timepopwindow_anim_style);

        LayoutInflater mLayoutInflater = LayoutInflater.from(context);
        rootView = mLayoutInflater.inflate(R.layout.popup_calendar, null);
        calendarView = (FlexibleCalendarView) rootView.findViewById(R.id.calendar_view);
        curMonth = (TextView) rootView.findViewById(R.id.toptxt);
        pre = (ImageView) rootView.findViewById(R.id.leftimage);
        next = (ImageView) rootView.findViewById(R.id.rightimage);
        initCalendarView();

        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.moveToPreviousMonth();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.moveToNextMonth();
            }
        });
        setContentView(rootView);
    }

    /**
     * 初始化日历控件
     */
    private void initCalendarView() {

        calendarView.setCalendarView(new FlexibleCalendarView.CalendarView() {
            @Override
            public BaseCellView getCellView(int position, View convertView, ViewGroup parent, @BaseCellView.CellType int cellType) {
                BaseCellView cellView = (BaseCellView) convertView;
                if (cellView == null) {
                    LayoutInflater inflater = LayoutInflater.from(mContext);
                    cellView = (BaseCellView) inflater.inflate(R.layout.calendar1_date_cell_view, null);
                    cellView.setTextColor(Color.BLACK);
                }
                if (cellType == BaseCellView.OUTSIDE_MONTH) {
                    cellView.setTextColor(Color.GRAY);
                }
                return cellView;
            }

            @Override
            public BaseCellView getWeekdayCellView(int position, View convertView, ViewGroup parent) {
                BaseCellView cellView = (BaseCellView) convertView;
                if (cellView == null) {
                    LayoutInflater inflater = LayoutInflater.from(mContext);
                    cellView = (SquareCellView) inflater.inflate(R.layout.calendar1_week_cell_view, null);
                    cellView.setTextColor(Color.BLACK);
                }
                return cellView;
            }

            @Override
            public String getDayOfWeekDisplayValue(int dayOfWeek, String defaultValue) {
                return String.valueOf(defaultValue.charAt(0));
            }
        });
        calendarView.setShowDatesOutsideMonth(false);
        calendarView.setOnMonthChangeListener(new FlexibleCalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month, @FlexibleCalendarView.Direction int direction) {
                curMonth.setText(year+"年"+(month+1)+"月");
            }
        });
    }

    public void setOnDateClickListener(FlexibleCalendarView.OnDateClickListener onDateClickListener) {
        calendarView.setOnDateClickListener(onDateClickListener);
    }

    public void nextDay(){
        calendarView.moveToNextDate();
    }

    public void preDay(){
        calendarView.moveToPreviousDate();
    }

    public SelectedDateItem getCurDate(){
        return calendarView.getSelectedDateItem();
    }
}
