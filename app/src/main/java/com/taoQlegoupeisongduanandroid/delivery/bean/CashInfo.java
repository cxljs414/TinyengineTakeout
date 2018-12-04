package com.taoQlegoupeisongduanandroid.delivery.bean;

import com.google.gson.Gson;

/**
 * Created by changxing on 2016/7/28.
 */
public class CashInfo {


    /**
     * delivery_type : 1
     * credit2 : 105.10
     * rule : {"id":"1","uniacid":"1","mobile_verify_status":"2","agreement":"","delivery_type":"2","plateform_delivery_fee":"0","delivery_fee_type":"2","delivery_fee":"10","get_cash_fee_limit":"1","get_cash_fee_rate":"2","get_cash_fee_min":"0","get_cash_fee_max":"100","card_apply_status":"1"}
     */

    private int delivery_type;
    private String credit2;
    /**
     * id : 1
     * uniacid : 1
     * mobile_verify_status : 2
     * agreement :
     * delivery_type : 2
     * plateform_delivery_fee : 0
     * delivery_fee_type : 2
     * delivery_fee : 10
     * get_cash_fee_limit : 1
     * get_cash_fee_rate : 2
     * get_cash_fee_min : 0
     * get_cash_fee_max : 100
     * card_apply_status : 1
     */

    private RuleEntity rule;

    public static CashInfo objectFromData(String str) {

        return new Gson().fromJson(str, CashInfo.class);
    }

    public int getDelivery_type() {
        return delivery_type;
    }

    public void setDelivery_type(int delivery_type) {
        this.delivery_type = delivery_type;
    }

    public String getCredit2() {
        return credit2;
    }

    public void setCredit2(String credit2) {
        this.credit2 = credit2;
    }

    public RuleEntity getRule() {
        return rule;
    }

    public void setRule(RuleEntity rule) {
        this.rule = rule;
    }

    public static class RuleEntity {
        private String id;
        private String uniacid;
        private String mobile_verify_status;
        private String agreement;
        private String delivery_type;
        private String plateform_delivery_fee;
        private String delivery_fee_type;
        private String delivery_fee;
        private String get_cash_fee_limit;
        private String get_cash_fee_rate;
        private String get_cash_fee_min;
        private String get_cash_fee_max;
        private String card_apply_status;

        public static RuleEntity objectFromData(String str) {

            return new Gson().fromJson(str, RuleEntity.class);
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

        public String getMobile_verify_status() {
            return mobile_verify_status;
        }

        public void setMobile_verify_status(String mobile_verify_status) {
            this.mobile_verify_status = mobile_verify_status;
        }

        public String getAgreement() {
            return agreement;
        }

        public void setAgreement(String agreement) {
            this.agreement = agreement;
        }

        public String getDelivery_type() {
            return delivery_type;
        }

        public void setDelivery_type(String delivery_type) {
            this.delivery_type = delivery_type;
        }

        public String getPlateform_delivery_fee() {
            return plateform_delivery_fee;
        }

        public void setPlateform_delivery_fee(String plateform_delivery_fee) {
            this.plateform_delivery_fee = plateform_delivery_fee;
        }

        public String getDelivery_fee_type() {
            return delivery_fee_type;
        }

        public void setDelivery_fee_type(String delivery_fee_type) {
            this.delivery_fee_type = delivery_fee_type;
        }

        public String getDelivery_fee() {
            return delivery_fee;
        }

        public void setDelivery_fee(String delivery_fee) {
            this.delivery_fee = delivery_fee;
        }

        public String getGet_cash_fee_limit() {
            return get_cash_fee_limit;
        }

        public void setGet_cash_fee_limit(String get_cash_fee_limit) {
            this.get_cash_fee_limit = get_cash_fee_limit;
        }

        public String getGet_cash_fee_rate() {
            return get_cash_fee_rate;
        }

        public void setGet_cash_fee_rate(String get_cash_fee_rate) {
            this.get_cash_fee_rate = get_cash_fee_rate;
        }

        public String getGet_cash_fee_min() {
            return get_cash_fee_min;
        }

        public void setGet_cash_fee_min(String get_cash_fee_min) {
            this.get_cash_fee_min = get_cash_fee_min;
        }

        public String getGet_cash_fee_max() {
            return get_cash_fee_max;
        }

        public void setGet_cash_fee_max(String get_cash_fee_max) {
            this.get_cash_fee_max = get_cash_fee_max;
        }

        public String getCard_apply_status() {
            return card_apply_status;
        }

        public void setCard_apply_status(String card_apply_status) {
            this.card_apply_status = card_apply_status;
        }
    }
}
