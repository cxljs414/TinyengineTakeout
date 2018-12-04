package com.taoQlegoupeisongduanandroid.delivery.bean;

import com.google.gson.Gson;

/**
 * Created by changxing on 2016/10/3.
 */
public class OrderStatisticsEntity {


    /**
     * num : 0
     * fee : 0
     */

    private TakeoutEntity takeout;
    /**
     * num : 0
     * fee : 0
     */

    private ErranderEntity errander;
    /**
     * num : 0
     * fee : 0
     */

    private TotalEntity total;
    /**
     * start : 2016-10-02 00:00
     * end : 2016-10-02 23:59
     */

    private TimeEntity time;

    public static OrderStatisticsEntity objectFromData(String str) {

        return new Gson().fromJson(str, OrderStatisticsEntity.class);
    }

    public TakeoutEntity getTakeout() {
        return takeout;
    }

    public void setTakeout(TakeoutEntity takeout) {
        this.takeout = takeout;
    }

    public ErranderEntity getErrander() {
        return errander;
    }

    public void setErrander(ErranderEntity errander) {
        this.errander = errander;
    }

    public TotalEntity getTotal() {
        return total;
    }

    public void setTotal(TotalEntity total) {
        this.total = total;
    }

    public TimeEntity getTime() {
        return time;
    }

    public void setTime(TimeEntity time) {
        this.time = time;
    }

    public static class TakeoutEntity {
        private int num;
        private float fee;

        public static TakeoutEntity objectFromData(String str) {

            return new Gson().fromJson(str, TakeoutEntity.class);
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public float getFee() {
            return fee;
        }

        public void setFee(int fee) {
            this.fee = fee;
        }
    }

    public static class ErranderEntity {
        private int num;
        private float fee;

        public static ErranderEntity objectFromData(String str) {

            return new Gson().fromJson(str, ErranderEntity.class);
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public float getFee() {
            return fee;
        }

        public void setFee(int fee) {
            this.fee = fee;
        }
    }

    public static class TotalEntity {
        private int num;
        private float fee;

        public static TotalEntity objectFromData(String str) {

            return new Gson().fromJson(str, TotalEntity.class);
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public float getFee() {
            return fee;
        }

        public void setFee(int fee) {
            this.fee = fee;
        }
    }

    public static class TimeEntity {
        private String start;
        private String end;

        public static TimeEntity objectFromData(String str) {

            return new Gson().fromJson(str, TimeEntity.class);
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }
    }
}
