package com.p_v.flexiblecalendar.entity;

import java.util.Calendar;

/**
 * @author p-v
 */
public interface Event {
    int getColor();
    String getEventTitle();
    String getEventContent();
    Calendar getCalendar();
}
