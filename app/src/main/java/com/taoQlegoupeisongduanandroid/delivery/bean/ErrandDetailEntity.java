package com.taoQlegoupeisongduanandroid.delivery.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by changxing on 2016/10/2.
 */
public class ErrandDetailEntity {


    /**
     * id : 372
     * uniacid : 1
     * acid : 1
     * uid : 1727
     * openid : o3nuitws-cow0IwCBnALUQChYEZc
     * code : 6201
     * order_sn : 20170212164409567630
     * order_type : buy
     * order_cid : 5
     * goods_name : 红塔山
     * goods_price : 未填写,请联系顾客沟通
     * goods_weight :
     * buy_username :
     * buy_sex :
     * buy_mobile :
     * buy_address : 用户未指定,您可自由寻找商户购买商品
     * buy_location_x :
     * buy_location_y :
     * accept_username : 灯火阑珊的
     * accept_sex : 先生
     * accept_mobile : 13456565623
     * accept_address : 天安门002
     * accept_location_x : 39.908692
     * accept_location_y : 116.397477
     * distance : 0
     * delivery_time : 立即送达
     * pay_type : credit
     * is_pay : 1
     * addtime : 1486889049
     * paytime : 1486889049
     * delivery_status : 2
     * deliveryer_id : 80
     * delivery_assign_time : 1486890178
     * delivery_instore_time : 0
     * delivery_success_time : 0
     * delivery_success_location_x :
     * delivery_success_location_y :
     * status : 2
     * print_nums : 0
     * delivery_fee : 2
     * delivery_tips : 0
     * total_fee : 2
     * discount_fee : 0
     * final_fee : 2.00
     * deliveryer_fee : 2
     * deliveryer_total_fee : 2
     * note :
     * is_remind : 0
     * is_anonymous : 0
     * anonymous_username : 灯火阑珊的
     * out_trade_no :
     * refund_status : 0
     * refund_out_no :
     * refund_apply_time : 0
     * refund_success_time : 0
     * refund_channel :
     * refund_account :
     * delivery_handle_type : wechat
     * order_type_cn : 随意购
     * status_cn : 正在进行中
     * delivery_status_cn : 待取货
     * pay_type_cn : 余额支付
     * category : {"id":"5","title":"买香烟","thumb":"images/1/2016/09/w4S8g1hmwmALp4j5p1pGKlPhG8HBSH.png"}
     * deliveryer_transfer_status : false
     * deliveryer_transfer_reason : ["电动车坏了\r","老婆怀孕了\r","我不想干了\r","订单费用太低"]
     * addtime_cn : 2017-02-12 16:44
     * buy_address_title : 买
     * accept_address_title : 送
     * paytime_cn : 2017-02-12 16:44
     * delivery_assign_time_cn : 2017-02-12 17:02
     * delivery_instore_time_cn : 1970-01-01 08:00
     * delivery_success_time_cn : 1970-01-01 08:00
     * deliveryer : {"title":"常兴","mobile":"18803415450","age":"25","sex":"男","location_x":"39.76076","location_y":"116.49499"}
     * store2user_distance : 未知
     * store2deliveryer_distance : 未知
     */

    private String id;
    private String uniacid;
    private String acid;
    private String uid;
    private String openid;
    private String code;
    private String order_sn;
    private String order_type;
    private String order_cid;
    private String goods_name;
    private String goods_price;
    private String goods_weight;
    private String buy_username;
    private String buy_sex;
    private String buy_mobile;
    private String buy_address;
    private String buy_location_x;
    private String buy_location_y;
    private String accept_username;
    private String accept_sex;
    private String accept_mobile;
    private String accept_address;
    private String accept_location_x;
    private String accept_location_y;
    private String distance;
    private String delivery_time;
    private String pay_type;
    private String is_pay;
    private String addtime;
    private String paytime;
    private String delivery_status;
    private String deliveryer_id;
    private String delivery_assign_time;
    private String delivery_instore_time;
    private String delivery_success_time;
    private String delivery_success_location_x;
    private String delivery_success_location_y;
    private String status;
    private String print_nums;
    private String delivery_fee;
    private String delivery_tips;
    private String total_fee;
    private String discount_fee;
    private String final_fee;
    private String deliveryer_fee;
    private String deliveryer_total_fee;
    private String note;
    private String is_remind;
    private String is_anonymous;
    private String anonymous_username;
    private String out_trade_no;
    private String refund_status;
    private String refund_out_no;
    private String refund_apply_time;
    private String refund_success_time;
    private String refund_channel;
    private String refund_account;
    private String delivery_handle_type;
    private String order_type_cn;
    private String status_cn;
    private String delivery_status_cn;
    private String pay_type_cn;
    /**
     * id : 5
     * title : 买香烟
     * thumb : images/1/2016/09/w4S8g1hmwmALp4j5p1pGKlPhG8HBSH.png
     */

    private CategoryEntity category;
    private String deliveryer_transfer_status;
    private String addtime_cn;
    private String buy_address_title;
    private String accept_address_title;
    private String paytime_cn;
    private String delivery_assign_time_cn;
    private String delivery_instore_time_cn;
    private String delivery_success_time_cn;
    /**
     * title : 常兴
     * mobile : 18803415450
     * age : 25
     * sex : 男
     * location_x : 39.76076
     * location_y : 116.49499
     */

    private DeliveryerEntity deliveryer;

    private Store store;
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    private String store2user_distance;
    private String store2deliveryer_distance;
    private List<String> deliveryer_transfer_reason;
    private String  verification_code;

    public String getVerification_code() {
        return verification_code;
    }

    public void setVerification_code(String verification_code) {
        this.verification_code = verification_code;
    }
    public static ErrandDetailEntity objectFromData(String str) {
        return new Gson().fromJson(str, ErrandDetailEntity.class);
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getOrder_cid() {
        return order_cid;
    }

    public void setOrder_cid(String order_cid) {
        this.order_cid = order_cid;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getGoods_weight() {
        return goods_weight;
    }

    public void setGoods_weight(String goods_weight) {
        this.goods_weight = goods_weight;
    }

    public String getBuy_username() {
        return buy_username;
    }

    public void setBuy_username(String buy_username) {
        this.buy_username = buy_username;
    }

    public String getBuy_sex() {
        return buy_sex;
    }

    public void setBuy_sex(String buy_sex) {
        this.buy_sex = buy_sex;
    }

    public String getBuy_mobile() {
        return buy_mobile;
    }

    public void setBuy_mobile(String buy_mobile) {
        this.buy_mobile = buy_mobile;
    }

    public String getBuy_address() {
        return buy_address;
    }

    public void setBuy_address(String buy_address) {
        this.buy_address = buy_address;
    }

    public String getBuy_location_x() {
        return buy_location_x;
    }

    public void setBuy_location_x(String buy_location_x) {
        this.buy_location_x = buy_location_x;
    }

    public String getBuy_location_y() {
        return buy_location_y;
    }

    public void setBuy_location_y(String buy_location_y) {
        this.buy_location_y = buy_location_y;
    }

    public String getAccept_username() {
        return accept_username;
    }

    public void setAccept_username(String accept_username) {
        this.accept_username = accept_username;
    }

    public String getAccept_sex() {
        return accept_sex;
    }

    public void setAccept_sex(String accept_sex) {
        this.accept_sex = accept_sex;
    }

    public String getAccept_mobile() {
        return accept_mobile;
    }

    public void setAccept_mobile(String accept_mobile) {
        this.accept_mobile = accept_mobile;
    }

    public String getAccept_address() {
        return accept_address;
    }

    public void setAccept_address(String accept_address) {
        this.accept_address = accept_address;
    }

    public String getAccept_location_x() {
        return accept_location_x;
    }

    public void setAccept_location_x(String accept_location_x) {
        this.accept_location_x = accept_location_x;
    }

    public String getAccept_location_y() {
        return accept_location_y;
    }

    public void setAccept_location_y(String accept_location_y) {
        this.accept_location_y = accept_location_y;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
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

    public String getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(String is_pay) {
        this.is_pay = is_pay;
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

    public String getDelivery_status() {
        return delivery_status;
    }

    public void setDelivery_status(String delivery_status) {
        this.delivery_status = delivery_status;
    }

    public String getDeliveryer_id() {
        return deliveryer_id;
    }

    public void setDeliveryer_id(String deliveryer_id) {
        this.deliveryer_id = deliveryer_id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getDelivery_tips() {
        return delivery_tips;
    }

    public void setDelivery_tips(String delivery_tips) {
        this.delivery_tips = delivery_tips;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getDiscount_fee() {
        return discount_fee;
    }

    public void setDiscount_fee(String discount_fee) {
        this.discount_fee = discount_fee;
    }

    public String getFinal_fee() {
        return final_fee;
    }

    public void setFinal_fee(String final_fee) {
        this.final_fee = final_fee;
    }

    public String getDeliveryer_fee() {
        return deliveryer_fee;
    }

    public void setDeliveryer_fee(String deliveryer_fee) {
        this.deliveryer_fee = deliveryer_fee;
    }

    public String getDeliveryer_total_fee() {
        return deliveryer_total_fee;
    }

    public void setDeliveryer_total_fee(String deliveryer_total_fee) {
        this.deliveryer_total_fee = deliveryer_total_fee;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getIs_remind() {
        return is_remind;
    }

    public void setIs_remind(String is_remind) {
        this.is_remind = is_remind;
    }

    public String getIs_anonymous() {
        return is_anonymous;
    }

    public void setIs_anonymous(String is_anonymous) {
        this.is_anonymous = is_anonymous;
    }

    public String getAnonymous_username() {
        return anonymous_username;
    }

    public void setAnonymous_username(String anonymous_username) {
        this.anonymous_username = anonymous_username;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getRefund_status() {
        return refund_status;
    }

    public void setRefund_status(String refund_status) {
        this.refund_status = refund_status;
    }

    public String getRefund_out_no() {
        return refund_out_no;
    }

    public void setRefund_out_no(String refund_out_no) {
        this.refund_out_no = refund_out_no;
    }

    public String getRefund_apply_time() {
        return refund_apply_time;
    }

    public void setRefund_apply_time(String refund_apply_time) {
        this.refund_apply_time = refund_apply_time;
    }

    public String getRefund_success_time() {
        return refund_success_time;
    }

    public void setRefund_success_time(String refund_success_time) {
        this.refund_success_time = refund_success_time;
    }

    public String getRefund_channel() {
        return refund_channel;
    }

    public void setRefund_channel(String refund_channel) {
        this.refund_channel = refund_channel;
    }

    public String getRefund_account() {
        return refund_account;
    }

    public void setRefund_account(String refund_account) {
        this.refund_account = refund_account;
    }

    public String getDelivery_handle_type() {
        return delivery_handle_type;
    }

    public void setDelivery_handle_type(String delivery_handle_type) {
        this.delivery_handle_type = delivery_handle_type;
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

    public String getDelivery_status_cn() {
        return delivery_status_cn;
    }

    public void setDelivery_status_cn(String delivery_status_cn) {
        this.delivery_status_cn = delivery_status_cn;
    }

    public String getPay_type_cn() {
        return pay_type_cn;
    }

    public void setPay_type_cn(String pay_type_cn) {
        this.pay_type_cn = pay_type_cn;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
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

    public String getBuy_address_title() {
        return buy_address_title;
    }

    public void setBuy_address_title(String buy_address_title) {
        this.buy_address_title = buy_address_title;
    }

    public String getAccept_address_title() {
        return accept_address_title;
    }

    public void setAccept_address_title(String accept_address_title) {
        this.accept_address_title = accept_address_title;
    }

    public String getPaytime_cn() {
        return paytime_cn;
    }

    public void setPaytime_cn(String paytime_cn) {
        this.paytime_cn = paytime_cn;
    }

    public String getDelivery_assign_time_cn() {
        return delivery_assign_time_cn;
    }

    public void setDelivery_assign_time_cn(String delivery_assign_time_cn) {
        this.delivery_assign_time_cn = delivery_assign_time_cn;
    }

    public String getDelivery_instore_time_cn() {
        return delivery_instore_time_cn;
    }

    public void setDelivery_instore_time_cn(String delivery_instore_time_cn) {
        this.delivery_instore_time_cn = delivery_instore_time_cn;
    }

    public String getDelivery_success_time_cn() {
        return delivery_success_time_cn;
    }

    public void setDelivery_success_time_cn(String delivery_success_time_cn) {
        this.delivery_success_time_cn = delivery_success_time_cn;
    }

    public DeliveryerEntity getDeliveryer() {
        return deliveryer;
    }

    public void setDeliveryer(DeliveryerEntity deliveryer) {
        this.deliveryer = deliveryer;
    }

    public String getStore2user_distance() {
        return store2user_distance;
    }

    public void setStore2user_distance(String store2user_distance) {
        this.store2user_distance = store2user_distance;
    }

    public String getStore2deliveryer_distance() {
        return store2deliveryer_distance;
    }

    public void setStore2deliveryer_distance(String store2deliveryer_distance) {
        this.store2deliveryer_distance = store2deliveryer_distance;
    }

    public List<String> getDeliveryer_transfer_reason() {
        return deliveryer_transfer_reason;
    }

    public void setDeliveryer_transfer_reason(List<String> deliveryer_transfer_reason) {
        this.deliveryer_transfer_reason = deliveryer_transfer_reason;
    }

    public static class CategoryEntity {
        private String id;
        private String title;
        private String thumb;

        public static CategoryEntity objectFromData(String str) {

            return new Gson().fromJson(str, CategoryEntity.class);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
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
    public static class Store{
//        "title": "祥云餐馆", //门店名称
//                "address": "山西省太原市小店区", //门店地址
//                "telephone": "13453222285", //门店电话
//                "location_x": "37.753365",
//                "location_y": "112.576638"

        private String title;
        private String address;
        private String telephone;
        private String location_x;
        private String location_y;
        public static Store objectFromData(String str) {
            return new Gson().fromJson(str, Store.class);
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

}
