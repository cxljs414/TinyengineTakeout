package com.taoQlegoupeisongduanandroid.delivery.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by changxing on 2016/7/29.
 */
public class AllDetailBean {


    /**
     * list : [{"id":"39","uniacid":"1","deliveryer_id":"10","trade_type":"2","extra":"27","fee":"-88.20","amount":"98337.60","addtime":"1469693190","remark":"2016-07-28 16:06:30申请提现,提现金额88.2元, 手续费1.76元, 实际到账86.44元","addtime_cn":"2016-07-28 16:06","trade_type_cn":"申请提现"},{"id":"38","uniacid":"1","deliveryer_id":"10","trade_type":"2","extra":"26","fee":"-88.20","amount":"98425.80","addtime":"1469692328","remark":"2016-07-28 15:52:08申请提现,提现金额88.2元, 手续费1.76元, 实际到账86.44元","addtime_cn":"2016-07-28 15:52","trade_type_cn":"申请提现"}]
     * max_id : 39
     * min_id : 38
     */

    private int max_id;
    private int min_id;
    private int more;
    /**
     * id : 39
     * uniacid : 1
     * deliveryer_id : 10
     * trade_type : 2
     * extra : 27
     * fee : -88.20
     * amount : 98337.60
     * addtime : 1469693190
     * remark : 2016-07-28 16:06:30申请提现,提现金额88.2元, 手续费1.76元, 实际到账86.44元
     * addtime_cn : 2016-07-28 16:06
     * trade_type_cn : 申请提现
     */

    private List<ListEntity> list;

    public static AllDetailBean objectFromData(String str) {

        return new Gson().fromJson(str, AllDetailBean.class);
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

    public List<ListEntity> getList() {
        return list;
    }

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public int getMore() {
        return more;
    }

    public void setMore(int more) {
        this.more = more;
    }

    public static class ListEntity {
        private String id;
        private String uniacid;
        private String deliveryer_id;
        private String trade_type;
        private String extra;
        private String fee;
        private String amount;
        private String addtime;
        private String remark;
        private String addtime_cn;
        private String trade_type_cn;

        public static ListEntity objectFromData(String str) {

            return new Gson().fromJson(str, ListEntity.class);
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

        public String getDeliveryer_id() {
            return deliveryer_id;
        }

        public void setDeliveryer_id(String deliveryer_id) {
            this.deliveryer_id = deliveryer_id;
        }

        public String getTrade_type() {
            return trade_type;
        }

        public void setTrade_type(String trade_type) {
            this.trade_type = trade_type;
        }

        public String getExtra() {
            return extra;
        }

        public void setExtra(String extra) {
            this.extra = extra;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getAddtime_cn() {
            return addtime_cn;
        }

        public void setAddtime_cn(String addtime_cn) {
            this.addtime_cn = addtime_cn;
        }

        public String getTrade_type_cn() {
            return trade_type_cn;
        }

        public void setTrade_type_cn(String trade_type_cn) {
            this.trade_type_cn = trade_type_cn;
        }
    }
}
