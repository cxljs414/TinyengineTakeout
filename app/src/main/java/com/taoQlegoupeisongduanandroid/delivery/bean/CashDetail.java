package com.taoQlegoupeisongduanandroid.delivery.bean;

import com.google.gson.Gson;

/**
 * Created by changxing on 2016/7/28.
 */
public class CashDetail {


    /**
     * id : 12
     * uniacid : 1
     * deliveryer_id : 10
     * trade_no : 20160728114508344886
     * get_fee : 100.00
     * take_fee : 2.00
     * final_fee : 98.00
     * status : 2
     * addtime : 1469677508
     * endtime : 0
     * title : 杨慧锋
     * nickname : 灯火阑珊的
     * openid : o3nuitws-cow0IwCBnALUQChYEZc
     * addtime_cn : 2016-07-28 11:45
     * endtime_cn : 1970-01-01 08:00
     * status_cn : 申请中
     */

    private String id;
    private String uniacid;
    private String deliveryer_id;
    private String trade_no;
    private String get_fee;
    private String take_fee;
    private String final_fee;
    private String status;
    private String addtime;
    private String endtime;
    private String title;
    private String nickname;
    private String openid;
    private String addtime_cn;
    private String endtime_cn;
    private String status_cn;

    public static CashDetail objectFromData(String str) {

        return new Gson().fromJson(str, CashDetail.class);
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

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getGet_fee() {
        return get_fee;
    }

    public void setGet_fee(String get_fee) {
        this.get_fee = get_fee;
    }

    public String getTake_fee() {
        return take_fee;
    }

    public void setTake_fee(String take_fee) {
        this.take_fee = take_fee;
    }

    public String getFinal_fee() {
        return final_fee;
    }

    public void setFinal_fee(String final_fee) {
        this.final_fee = final_fee;
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

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAddtime_cn() {
        return addtime_cn;
    }

    public void setAddtime_cn(String addtime_cn) {
        this.addtime_cn = addtime_cn;
    }

    public String getEndtime_cn() {
        return endtime_cn;
    }

    public void setEndtime_cn(String endtime_cn) {
        this.endtime_cn = endtime_cn;
    }

    public String getStatus_cn() {
        return status_cn;
    }

    public void setStatus_cn(String status_cn) {
        this.status_cn = status_cn;
    }
}
