package com.p_v.flexiblecalendar.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author p-v
 */
public class CalendarEvent implements Event,Serializable {

    private String id;
    private int color;
    private String eventTitle;//标题、单号
    private String eventContent;//备注
    private Calendar calendar;//日期时间
    private String tipContent = "";//提示内容类型
    private int aheadTime = 0;
    private int alarmId;//提醒id

    public CalendarEvent(){

    }

    public CalendarEvent(int color, String eventTitle, String eventContent, Calendar calendar, String tipContent) {
        this.color = color;
        this.eventTitle = eventTitle;
        this.eventContent = eventContent;
        this.calendar = calendar;
        this.tipContent = tipContent;
        this.id = UUID.randomUUID().toString().replace("-","");
        this.alarmId = new Random().nextInt(10000);
    }

    public CalendarEvent(int color) {
        this.color = color;
        this.id = UUID.randomUUID().toString().replace("-","");
    }

    public int getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    public int getAheadTime() {
        return aheadTime;
    }

    public void setAheadTime(int aheadTime) {
        this.aheadTime = aheadTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipContent() {
        return tipContent;
    }

    public void setTipContent(String tipContent) {
        this.tipContent = tipContent;
    }

    public String getEventContent() {
        return eventContent;
    }

    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
    }

    @Override
    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
}
