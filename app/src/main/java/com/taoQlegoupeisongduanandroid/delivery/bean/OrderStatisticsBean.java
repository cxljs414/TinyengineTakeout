package com.taoQlegoupeisongduanandroid.delivery.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by changxing on 2016/8/8.
 */
public class OrderStatisticsBean {


    /**
     * num : 0
     * fee : 0
     */

    private PlateformEntity plateform;
    /**
     * title : 祥云餐馆
     * num : 0
     */

    private List<StoreEntity> store;

    public static OrderStatisticsBean objectFromData(String str) {

        return new Gson().fromJson(str, OrderStatisticsBean.class);
    }

    public PlateformEntity getPlateform() {
        return plateform;
    }

    public void setPlateform(PlateformEntity plateform) {
        this.plateform = plateform;
    }

    public List<StoreEntity> getStore() {
        return store;
    }

    public void setStore(List<StoreEntity> store) {
        this.store = store;
    }

    public static class PlateformEntity {
        private int num;
        private int fee;

        public static PlateformEntity objectFromData(String str) {

            return new Gson().fromJson(str, PlateformEntity.class);
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getFee() {
            return fee;
        }

        public void setFee(int fee) {
            this.fee = fee;
        }
    }

    public static class StoreEntity {
        private String title;
        private int num;

        public static StoreEntity objectFromData(String str) {

            return new Gson().fromJson(str, StoreEntity.class);
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
