package com.taoQlegoupeisongduanandroid.delivery.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by changxing on 2016/8/2.
 */
public class OrderBean {


    /**
     * list : [{"id":"1431","addtime":"1470137644","status":"3","username":"灯火阑珊的","mobile":"13453222285","address":"康乐宝宝幼儿园西南231米002","location_x":"37.78611901980707","location_y":"112.55380399999993","delivery_status":"3","delivery_type":"2","delivery_time":"20:00~20:30","sid":"1","num":"1","final_fee":"30","deliveryer_fee":3,"addtime_cn":"08-02 19:34","store":{"title":"祥云餐馆","telephone":"13453222285","address":"山西省太原市小店区","location_x":"37.753365","location_y":"112.576638"},"store2deliveryer_distance":2.03,"store2user_distance":4.16}]
     * max_id : 1431
     * min_id : 1431
     */

    private int max_id;
    private int min_id;
    private int more;
    /**
     * id : 1431
     * addtime : 1470137644
     * status : 3
     * username : 灯火阑珊的
     * mobile : 13453222285
     * address : 康乐宝宝幼儿园西南231米002
     * location_x : 37.78611901980707
     * location_y : 112.55380399999993
     * delivery_status : 3
     * delivery_type : 2
     * delivery_time : 20:00~20:30
     * sid : 1
     * num : 1
     * final_fee : 30
     * deliveryer_fee : 3
     * addtime_cn : 08-02 19:34
     * store : {"title":"祥云餐馆","telephone":"13453222285","address":"山西省太原市小店区","location_x":"37.753365","location_y":"112.576638"}
     * store2deliveryer_distance : 2.03
     * store2user_distance : 4.16
     */

    private List<ListEntity> list;

    public static OrderBean objectFromData(String str) {

        return new Gson().fromJson(str, OrderBean.class);
    }

    public int getMax_id() {
        return max_id;
    }

    public void setMax_id(int max_id) {
        this.max_id = max_id;
    }

    public int getMin_id() {
        return min_id;
    }

    public void setMin_id(int min_id) {
        this.min_id = min_id;
    }

    public int getMore() {
        return more;
    }

    public void setMore(int more) {
        this.more = more;
    }

    public List<ListEntity> getList() {
        return list;
    }

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public static class ListEntity {
        private String id;
        private String ordersn;//平台订单号
        private String serial_sn;//商户订单号
        private String addtime;
        private String status;
        private String username;
        private String mobile;
        private String address;
        private String location_x;
        private String location_y;
        private String delivery_status;
        private String delivery_type;
        private String delivery_time;
        private String sid;
        private String num;
        private String final_fee;
        private double deliveryer_fee;
        private String addtime_cn;
        private String note;
        /**
         * title : 祥云餐馆
         * telephone : 13453222285
         * address : 山西省太原市小店区
         * location_x : 37.753365
         * location_y : 112.576638
         */

        private StoreEntity store;
        private String store2deliveryer_distance;
        private String store2user_distance;

        public static ListEntity objectFromData(String str) {

            return new Gson().fromJson(str, ListEntity.class);
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getOrdersn() {
            return ordersn;
        }

        public void setOrdersn(String ordersn) {
            this.ordersn = ordersn;
        }

        public String getSerial_sn() {
            return serial_sn;
        }

        public void setSerial_sn(String serial_sn) {
            this.serial_sn = serial_sn;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLocation_x() {
            return location_x;
        }

        public void setLocation_x(String location_x) {
            this.location_x = location_x;
        }

        public String getLocation_y() {
            return location_y;
        }

        public void setLocation_y(String location_y) {
            this.location_y = location_y;
        }

        public String getDelivery_status() {
            return delivery_status;
        }

        public void setDelivery_status(String delivery_status) {
            this.delivery_status = delivery_status;
        }

        public String getDelivery_type() {
            return delivery_type;
        }

        public void setDelivery_type(String delivery_type) {
            this.delivery_type = delivery_type;
        }

        public String getDelivery_time() {
            return delivery_time;
        }

        public void setDelivery_time(String delivery_time) {
            this.delivery_time = delivery_time;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getFinal_fee() {
            return final_fee;
        }

        public void setFinal_fee(String final_fee) {
            this.final_fee = final_fee;
        }

        public double getDeliveryer_fee() {
            return deliveryer_fee;
        }

        public void setDeliveryer_fee(double deliveryer_fee) {
            this.deliveryer_fee = deliveryer_fee;
        }

        public String getAddtime_cn() {
            return addtime_cn;
        }

        public void setAddtime_cn(String addtime_cn) {
            this.addtime_cn = addtime_cn;
        }

        public StoreEntity getStore() {
            return store;
        }

        public void setStore(StoreEntity store) {
            this.store = store;
        }

        public String getStore2deliveryer_distance() {
            return store2deliveryer_distance;
        }

        public void setStore2deliveryer_distance(String store2deliveryer_distance) {
            this.store2deliveryer_distance = store2deliveryer_distance;
        }

        public String getStore2user_distance() {
            return store2user_distance;
        }

        public void setStore2user_distance(String store2user_distance) {
            this.store2user_distance = store2user_distance;
        }

        public static class StoreEntity {
            private String title;
            private String telephone;
            private String address;
            private String location_x;
            private String location_y;

            public static StoreEntity objectFromData(String str) {

                return new Gson().fromJson(str, StoreEntity.class);
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getLocation_x() {
                return location_x;
            }

            public void setLocation_x(String location_x) {
                this.location_x = location_x;
            }

            public String getLocation_y() {
                return location_y;
            }

            public void setLocation_y(String location_y) {
                this.location_y = location_y;
            }
        }
    }
}
