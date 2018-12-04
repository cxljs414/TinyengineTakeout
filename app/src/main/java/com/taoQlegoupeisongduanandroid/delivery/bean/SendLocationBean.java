package com.taoQlegoupeisongduanandroid.delivery.bean;

import com.google.gson.Gson;

/**
 * Created by changxing on 2016/8/1.
 */
public class SendLocationBean {

    /**
     * resultCode :
     */

    private String resultCode;

    public static SendLocationBean objectFromData(String str) {

        return new Gson().fromJson(str, SendLocationBean.class);
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
}
