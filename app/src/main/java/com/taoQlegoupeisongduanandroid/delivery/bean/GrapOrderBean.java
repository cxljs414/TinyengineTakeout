package com.taoQlegoupeisongduanandroid.delivery.bean;

import com.google.gson.Gson;

/**
 * Created by changxing on 2016/8/1.
 */
public class GrapOrderBean {

    /**
     * resultCode :
     */

    private String resultCode;

    public static GrapOrderBean objectFromData(String str) {

        return new Gson().fromJson(str, GrapOrderBean.class);
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
}
