package com.taoQlegoupeisongduanandroid.delivery.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by changxing on 2016/8/3.
 */
public class OrderDetailBean {


    /**
     * id : 3320
     * uniacid : 1
     * acid : 1
     * sid : 1
     * uid : 51
     * groupid : 1
     * order_type : 1
     * is_pay : 1
     * ordersn : 20170212104423664692
     * code : 8693
     * openid : o3nuitwCSxxGvgKz9lVJ7avG4zCA
     * username : 哈哈
     * sex : 先生
     * mobile : 13356784567
     * address : 国际大都会
     * number :
     * location_x : 37.791001
     * location_y : 112.572056
     * note :
     * price : 38.00
     * num : 1
     * delivery_day : 2017-02-12
     * delivery_time : 11:00~11:30
     * pay_type : delivery
     * addtime : 1486867463
     * paytime : 1486867463
     * delivery_handle_type : wechat
     * delivery_success_location_x :
     * delivery_success_location_y :
     * delivery_assign_time : 1486872462
     * delivery_instore_time : 0
     * delivery_success_time : 0
     * status : 4
     * delivery_status : 7
     * delivery_type : 2
     * is_comment : 0
     * print_nums : 1
     * delivery_fee : 0.00
     * pack_fee : 0.00
     * serve_fee :
     * discount_fee : 8
     * total_fee : 38
     * final_fee : 30.00
     * vip_free_delivery_fee : 0
     * invoice :
     * data : a:1:{i:7;a:3:{s:8:"goods_id";s:1:"7";s:5:"title";s:15:"野山菌炖鸡";s:7:"options";a:1:{i:0;a:7:{s:9:"option_id";s:1:"0";s:4:"name";s:0:"";s:3:"num";i:1;s:9:"price_num";i:1;s:12:"discount_num";i:0;s:10:"bargain_id";i:0;s:11:"price_total";s:2:"38";}}}}
     * is_remind : 0
     * deliveryer_id : 80
     * is_refund : 0
     * person_num : 1
     * table_id : 0
     * table_cid : 0
     * reserve_type :
     * reserve_time :
     * transaction_id :
     * box_price : 0
     * deliveryingtime : 0
     * deliveryinstoretime : 0
     * deliverysuccesstime : 0
     * serial_sn : 7
     * handletime : 1486867463
     * endtime : 0
     * refund_status : 0
     * out_trade_no :
     * store_final_fee : 30
     * store_discount_fee : 0
     * plateform_discount_fee : 0
     * plateform_serve_rate : 0
     * plateform_serve_fee : 0
     * plateform_delivery_fee : 0.00
     * plateform_deliveryer_fee : 0
     * refund_fee : 0
     * stat_year : 2017
     * stat_month : 201702
     * stat_day : 20170212
     * order_type_cn : 外卖
     * status_cn : 配送中
     * pay_type_cn : 货到付款
     * pay_type_class : delivery-pay
     * deliveryer_transfer_status : 1
     * deliveryer_transfer_reason : ["电动车坏了\r","老婆怀孕了\r","我不想干了\r","订单费用太低"]
     * addtime_cn : 2017-02-12 10:44
     * paytime_cn : 2017-02-12 10:44
     * deliveryingtime_cn : 2017-02-12 12:07
     * deliveryinstoretime_cn : 1970-01-01 08:00
     * deliverysuccesstime_cn : 1970-01-01 08:00
     * store : {"title":"祥云餐馆","address":"山西省太原市小店区","telephone":"0351-4626233","location_x":"37.788871","location_y":"112.559928"}
     * deliveryer : {"title":"常兴","mobile":"18803415450","age":"25","sex":"男","location_x":"39.76069","location_y":"116.49544"}
     * store2deliveryer_distance : 405.94
     * store2user_distance : 1.09
     * goods : [{"id":"6027","oid":"3320","uniacid":"1","sid":"1","uid":"51","goods_id":"7","goods_cid":"2","option_id":"0","goods_num":"1","goods_discount_num":"0","goods_title":"野山菌炖鸡","goods_unit_price":"38","goods_price":"38.00","goods_original_price":"38.00","bargain_id":"0","total_update_status":"1","print_label":"0","status":"4","addtime":"1486867463","goods_category_title":"特色炖菜","stat_year":"2017","stat_month":"201702","stat_week":"201706","stat_day":"20170212"}]
     * activityed : [{"id":"3512","uniacid":"1","sid":"1","oid":"3320","type":"discount","name":"满减优惠","icon":"jian_b.png","note":"-￥8","fee":"8","store_discount_fee":"0","plateform_discount_fee":"0"},{"id":"3513","uniacid":"1","sid":"1","oid":"3320","type":"grant","name":"满赠优惠","icon":"zeng_b.png","note":"西瓜一个","fee":"0","store_discount_fee":"0","plateform_discount_fee":"0"}]
     */

    private String id;
    private String uniacid;
    private String acid;
    private String sid;
    private String uid;
    private String groupid;
    private String order_type;
    private String is_pay;
    private String ordersn;
    private String code;
    private String openid;
    private String username;
    private String sex;
    private String mobile;
    private String address;
    private String number;
    private String location_x;
    private String location_y;
    private String note;
    private String price;
    private String num;
    private String delivery_day;
    private String delivery_time;
    private String pay_type;
    private String addtime;
    private String paytime;
    private String delivery_handle_type;
    private String delivery_success_location_x;
    private String delivery_success_location_y;
    private String delivery_assign_time;
    private String delivery_instore_time;
    private String delivery_success_time;
    private String status;
    private String delivery_status;
    private String delivery_type;
    private String is_comment;
    private String print_nums;
    private String delivery_fee;
    private String pack_fee;
    private String serve_fee;
    private String discount_fee;
    private String total_fee;
    private String final_fee;
    private String vip_free_delivery_fee;
    private String invoice;
    private String data;
    private String is_remind;
    private String deliveryer_id;
    private String is_refund;
    private String person_num;
    private String table_id;
    private String table_cid;
    private String reserve_type;
    private String reserve_time;
    private String transaction_id;
    private String box_price;
    private String deliveryingtime;
    private String deliveryinstoretime;
    private String deliverysuccesstime;
    private String serial_sn;
    private String handletime;
    private String endtime;
    private String refund_status;
    private String out_trade_no;
    private String store_final_fee;
    private String store_discount_fee;
    private String plateform_discount_fee;
    private String plateform_serve_rate;
    private String plateform_serve_fee;
    private String plateform_delivery_fee;
    private String plateform_deliveryer_fee;
    private String refund_fee;
    private String stat_year;
    private String stat_month;
    private String stat_day;
    private String order_type_cn;
    private String status_cn;
    private String pay_type_cn;
    private String pay_type_class;
    private String deliveryer_transfer_status;
    private String addtime_cn;
    private String paytime_cn;
    private String deliveryingtime_cn;
    private String deliveryinstoretime_cn;
    private String deliverysuccesstime_cn;
    private String deliveryer_fee;

    public String getDeliveryer_fee() {
        return deliveryer_fee;
    }

    public void setDeliveryer_fee(String deliveryer_fee) {
        this.deliveryer_fee = deliveryer_fee;
    }

    /**
     * title : 祥云餐馆
     * address : 山西省太原市小店区
     * telephone : 0351-4626233
     * location_x : 37.788871
     * location_y : 112.559928
     */

    private StoreEntity store;
    /**
     * title : 常兴
     * mobile : 18803415450
     * age : 25
     * sex : 男
     * location_x : 39.76069
     * location_y : 116.49544
     */

    private DeliveryerEntity deliveryer;
    private String store2deliveryer_distance;
    private String store2user_distance;
    private List<String> deliveryer_transfer_reason;
    /**
     * id : 6027
     * oid : 3320
     * uniacid : 1
     * sid : 1
     * uid : 51
     * goods_id : 7
     * goods_cid : 2
     * option_id : 0
     * goods_num : 1
     * goods_discount_num : 0
     * goods_title : 野山菌炖鸡
     * goods_unit_price : 38
     * goods_price : 38.00
     * goods_original_price : 38.00
     * bargain_id : 0
     * total_update_status : 1
     * print_label : 0
     * status : 4
     * addtime : 1486867463
     * goods_category_title : 特色炖菜
     * stat_year : 2017
     * stat_month : 201702
     * stat_week : 201706
     * stat_day : 20170212
     */

    private List<GoodsEntity> goods;
    /**
     * id : 3512
     * uniacid : 1
     * sid : 1
     * oid : 3320
     * type : discount
     * name : 满减优惠
     * icon : jian_b.png
     * note : -￥8
     * fee : 8
     * store_discount_fee : 0
     * plateform_discount_fee : 0
     */

    private List<ActivityedEntity> activityed;

    public static OrderDetailBean objectFromData(String str) {

        return new Gson().fromJson(str, OrderDetailBean.class);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUniacid() {
        return uniacid;
    }

    public void setUniacid(String uniacid) {
        this.uniacid = uniacid;
    }

    public String getAcid() {
        return acid;
    }

    public void setAcid(String acid) {
        this.acid = acid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(String is_pay) {
        this.is_pay = is_pay;
    }

    public String getOrdersn() {
        return ordersn;
    }

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getDelivery_day() {
        return delivery_day;
    }

    public void setDelivery_day(String delivery_day) {
        this.delivery_day = delivery_day;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getPaytime() {
        return paytime;
    }

    public void setPaytime(String paytime) {
        this.paytime = paytime;
    }

    public String getDelivery_handle_type() {
        return delivery_handle_type;
    }

    public void setDelivery_handle_type(String delivery_handle_type) {
        this.delivery_handle_type = delivery_handle_type;
    }

    public String getDelivery_success_location_x() {
        return delivery_success_location_x;
    }

    public void setDelivery_success_location_x(String delivery_success_location_x) {
        this.delivery_success_location_x = delivery_success_location_x;
    }

    public String getDelivery_success_location_y() {
        return delivery_success_location_y;
    }

    public void setDelivery_success_location_y(String delivery_success_location_y) {
        this.delivery_success_location_y = delivery_success_location_y;
    }

    public String getDelivery_assign_time() {
        return delivery_assign_time;
    }

    public void setDelivery_assign_time(String delivery_assign_time) {
        this.delivery_assign_time = delivery_assign_time;
    }

    public String getDelivery_instore_time() {
        return delivery_instore_time;
    }

    public void setDelivery_instore_time(String delivery_instore_time) {
        this.delivery_instore_time = delivery_instore_time;
    }

    public String getDelivery_success_time() {
        return delivery_success_time;
    }

    public void setDelivery_success_time(String delivery_success_time) {
        this.delivery_success_time = delivery_success_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getIs_comment() {
        return is_comment;
    }

    public void setIs_comment(String is_comment) {
        this.is_comment = is_comment;
    }

    public String getPrint_nums() {
        return print_nums;
    }

    public void setPrint_nums(String print_nums) {
        this.print_nums = print_nums;
    }

    public String getDelivery_fee() {
        return delivery_fee;
    }

    public void setDelivery_fee(String delivery_fee) {
        this.delivery_fee = delivery_fee;
    }

    public String getPack_fee() {
        return pack_fee;
    }

    public void setPack_fee(String pack_fee) {
        this.pack_fee = pack_fee;
    }

    public String getServe_fee() {
        return serve_fee;
    }

    public void setServe_fee(String serve_fee) {
        this.serve_fee = serve_fee;
    }

    public String getDiscount_fee() {
        return discount_fee;
    }

    public void setDiscount_fee(String discount_fee) {
        this.discount_fee = discount_fee;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getFinal_fee() {
        return final_fee;
    }

    public void setFinal_fee(String final_fee) {
        this.final_fee = final_fee;
    }

    public String getVip_free_delivery_fee() {
        return vip_free_delivery_fee;
    }

    public void setVip_free_delivery_fee(String vip_free_delivery_fee) {
        this.vip_free_delivery_fee = vip_free_delivery_fee;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getIs_remind() {
        return is_remind;
    }

    public void setIs_remind(String is_remind) {
        this.is_remind = is_remind;
    }

    public String getDeliveryer_id() {
        return deliveryer_id;
    }

    public void setDeliveryer_id(String deliveryer_id) {
        this.deliveryer_id = deliveryer_id;
    }

    public String getIs_refund() {
        return is_refund;
    }

    public void setIs_refund(String is_refund) {
        this.is_refund = is_refund;
    }

    public String getPerson_num() {
        return person_num;
    }

    public void setPerson_num(String person_num) {
        this.person_num = person_num;
    }

    public String getTable_id() {
        return table_id;
    }

    public void setTable_id(String table_id) {
        this.table_id = table_id;
    }

    public String getTable_cid() {
        return table_cid;
    }

    public void setTable_cid(String table_cid) {
        this.table_cid = table_cid;
    }

    public String getReserve_type() {
        return reserve_type;
    }

    public void setReserve_type(String reserve_type) {
        this.reserve_type = reserve_type;
    }

    public String getReserve_time() {
        return reserve_time;
    }

    public void setReserve_time(String reserve_time) {
        this.reserve_time = reserve_time;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getBox_price() {
        return box_price;
    }

    public void setBox_price(String box_price) {
        this.box_price = box_price;
    }

    public String getDeliveryingtime() {
        return deliveryingtime;
    }

    public void setDeliveryingtime(String deliveryingtime) {
        this.deliveryingtime = deliveryingtime;
    }

    public String getDeliveryinstoretime() {
        return deliveryinstoretime;
    }

    public void setDeliveryinstoretime(String deliveryinstoretime) {
        this.deliveryinstoretime = deliveryinstoretime;
    }

    public String getDeliverysuccesstime() {
        return deliverysuccesstime;
    }

    public void setDeliverysuccesstime(String deliverysuccesstime) {
        this.deliverysuccesstime = deliverysuccesstime;
    }

    public String getSerial_sn() {
        return serial_sn;
    }

    public void setSerial_sn(String serial_sn) {
        this.serial_sn = serial_sn;
    }

    public String getHandletime() {
        return handletime;
    }

    public void setHandletime(String handletime) {
        this.handletime = handletime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getRefund_status() {
        return refund_status;
    }

    public void setRefund_status(String refund_status) {
        this.refund_status = refund_status;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getStore_final_fee() {
        return store_final_fee;
    }

    public void setStore_final_fee(String store_final_fee) {
        this.store_final_fee = store_final_fee;
    }

    public String getStore_discount_fee() {
        return store_discount_fee;
    }

    public void setStore_discount_fee(String store_discount_fee) {
        this.store_discount_fee = store_discount_fee;
    }

    public String getPlateform_discount_fee() {
        return plateform_discount_fee;
    }

    public void setPlateform_discount_fee(String plateform_discount_fee) {
        this.plateform_discount_fee = plateform_discount_fee;
    }

    public String getPlateform_serve_rate() {
        return plateform_serve_rate;
    }

    public void setPlateform_serve_rate(String plateform_serve_rate) {
        this.plateform_serve_rate = plateform_serve_rate;
    }

    public String getPlateform_serve_fee() {
        return plateform_serve_fee;
    }

    public void setPlateform_serve_fee(String plateform_serve_fee) {
        this.plateform_serve_fee = plateform_serve_fee;
    }

    public String getPlateform_delivery_fee() {
        return plateform_delivery_fee;
    }

    public void setPlateform_delivery_fee(String plateform_delivery_fee) {
        this.plateform_delivery_fee = plateform_delivery_fee;
    }

    public String getPlateform_deliveryer_fee() {
        return plateform_deliveryer_fee;
    }

    public void setPlateform_deliveryer_fee(String plateform_deliveryer_fee) {
        this.plateform_deliveryer_fee = plateform_deliveryer_fee;
    }

    public String getRefund_fee() {
        return refund_fee;
    }

    public void setRefund_fee(String refund_fee) {
        this.refund_fee = refund_fee;
    }

    public String getStat_year() {
        return stat_year;
    }

    public void setStat_year(String stat_year) {
        this.stat_year = stat_year;
    }

    public String getStat_month() {
        return stat_month;
    }

    public void setStat_month(String stat_month) {
        this.stat_month = stat_month;
    }

    public String getStat_day() {
        return stat_day;
    }

    public void setStat_day(String stat_day) {
        this.stat_day = stat_day;
    }

    public String getOrder_type_cn() {
        return order_type_cn;
    }

    public void setOrder_type_cn(String order_type_cn) {
        this.order_type_cn = order_type_cn;
    }

    public String getStatus_cn() {
        return status_cn;
    }

    public void setStatus_cn(String status_cn) {
        this.status_cn = status_cn;
    }

    public String getPay_type_cn() {
        return pay_type_cn;
    }

    public void setPay_type_cn(String pay_type_cn) {
        this.pay_type_cn = pay_type_cn;
    }

    public String getPay_type_class() {
        return pay_type_class;
    }

    public void setPay_type_class(String pay_type_class) {
        this.pay_type_class = pay_type_class;
    }

    public String getDeliveryer_transfer_status() {
        return deliveryer_transfer_status;
    }

    public void setDeliveryer_transfer_status(String deliveryer_transfer_status) {
        this.deliveryer_transfer_status = deliveryer_transfer_status;
    }

    public String getAddtime_cn() {
        return addtime_cn;
    }

    public void setAddtime_cn(String addtime_cn) {
        this.addtime_cn = addtime_cn;
    }

    public String getPaytime_cn() {
        return paytime_cn;
    }

    public void setPaytime_cn(String paytime_cn) {
        this.paytime_cn = paytime_cn;
    }

    public String getDeliveryingtime_cn() {
        return deliveryingtime_cn;
    }

    public void setDeliveryingtime_cn(String deliveryingtime_cn) {
        this.deliveryingtime_cn = deliveryingtime_cn;
    }

    public String getDeliveryinstoretime_cn() {
        return deliveryinstoretime_cn;
    }

    public void setDeliveryinstoretime_cn(String deliveryinstoretime_cn) {
        this.deliveryinstoretime_cn = deliveryinstoretime_cn;
    }

    public String getDeliverysuccesstime_cn() {
        return deliverysuccesstime_cn;
    }

    public void setDeliverysuccesstime_cn(String deliverysuccesstime_cn) {
        this.deliverysuccesstime_cn = deliverysuccesstime_cn;
    }

    public StoreEntity getStore() {
        return store;
    }

    public void setStore(StoreEntity store) {
        this.store = store;
    }

    public DeliveryerEntity getDeliveryer() {
        return deliveryer;
    }

    public void setDeliveryer(DeliveryerEntity deliveryer) {
        this.deliveryer = deliveryer;
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

    public List<String> getDeliveryer_transfer_reason() {
        return deliveryer_transfer_reason;
    }

    public void setDeliveryer_transfer_reason(List<String> deliveryer_transfer_reason) {
        this.deliveryer_transfer_reason = deliveryer_transfer_reason;
    }

    public List<GoodsEntity> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsEntity> goods) {
        this.goods = goods;
    }

    public List<ActivityedEntity> getActivityed() {
        return activityed;
    }

    public void setActivityed(List<ActivityedEntity> activityed) {
        this.activityed = activityed;
    }

    public static class StoreEntity {
        private String title;
        private String address;
        private String telephone;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
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

    public static class DeliveryerEntity {
        private String title;
        private String mobile;
        private String age;
        private String sex;
        private String location_x;
        private String location_y;

        public static DeliveryerEntity objectFromData(String str) {

            return new Gson().fromJson(str, DeliveryerEntity.class);
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
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

    public static class GoodsEntity {
        private String id;
        private String oid;
        private String uniacid;
        private String sid;
        private String uid;
        private String goods_id;
        private String goods_cid;
        private String option_id;
        private String goods_num;
        private String goods_discount_num;
        private String goods_title;
        private String goods_unit_price;
        private String goods_price;
        private String goods_original_price;
        private String bargain_id;
        private String total_update_status;
        private String print_label;
        private String status;
        private String addtime;
        private String goods_category_title;
        private String stat_year;
        private String stat_month;
        private String stat_week;
        private String stat_day;

        public static GoodsEntity objectFromData(String str) {

            return new Gson().fromJson(str, GoodsEntity.class);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }

        public String getUniacid() {
            return uniacid;
        }

        public void setUniacid(String uniacid) {
            this.uniacid = uniacid;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_cid() {
            return goods_cid;
        }

        public void setGoods_cid(String goods_cid) {
            this.goods_cid = goods_cid;
        }

        public String getOption_id() {
            return option_id;
        }

        public void setOption_id(String option_id) {
            this.option_id = option_id;
        }

        public String getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num;
        }

        public String getGoods_discount_num() {
            return goods_discount_num;
        }

        public void setGoods_discount_num(String goods_discount_num) {
            this.goods_discount_num = goods_discount_num;
        }

        public String getGoods_title() {
            return goods_title;
        }

        public void setGoods_title(String goods_title) {
            this.goods_title = goods_title;
        }

        public String getGoods_unit_price() {
            return goods_unit_price;
        }

        public void setGoods_unit_price(String goods_unit_price) {
            this.goods_unit_price = goods_unit_price;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getGoods_original_price() {
            return goods_original_price;
        }

        public void setGoods_original_price(String goods_original_price) {
            this.goods_original_price = goods_original_price;
        }

        public String getBargain_id() {
            return bargain_id;
        }

        public void setBargain_id(String bargain_id) {
            this.bargain_id = bargain_id;
        }

        public String getTotal_update_status() {
            return total_update_status;
        }

        public void setTotal_update_status(String total_update_status) {
            this.total_update_status = total_update_status;
        }

        public String getPrint_label() {
            return print_label;
        }

        public void setPrint_label(String print_label) {
            this.print_label = print_label;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getGoods_category_title() {
            return goods_category_title;
        }

        public void setGoods_category_title(String goods_category_title) {
            this.goods_category_title = goods_category_title;
        }

        public String getStat_year() {
            return stat_year;
        }

        public void setStat_year(String stat_year) {
            this.stat_year = stat_year;
        }

        public String getStat_month() {
            return stat_month;
        }

        public void setStat_month(String stat_month) {
            this.stat_month = stat_month;
        }

        public String getStat_week() {
            return stat_week;
        }

        public void setStat_week(String stat_week) {
            this.stat_week = stat_week;
        }

        public String getStat_day() {
            return stat_day;
        }

        public void setStat_day(String stat_day) {
            this.stat_day = stat_day;
        }
    }

    public static class ActivityedEntity {
        private String id;
        private String uniacid;
        private String sid;
        private String oid;
        private String type;
        private String name;
        private String icon;
        private String note;
        private String fee;
        private String store_discount_fee;
        private String plateform_discount_fee;

        public static ActivityedEntity objectFromData(String str) {

            return new Gson().fromJson(str, ActivityedEntity.class);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUniacid() {
            return uniacid;
        }

        public void setUniacid(String uniacid) {
            this.uniacid = uniacid;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getStore_discount_fee() {
            return store_discount_fee;
        }

        public void setStore_discount_fee(String store_discount_fee) {
            this.store_discount_fee = store_discount_fee;
        }

        public String getPlateform_discount_fee() {
            return plateform_discount_fee;
        }

        public void setPlateform_discount_fee(String plateform_discount_fee) {
            this.plateform_discount_fee = plateform_discount_fee;
        }
    }
}
