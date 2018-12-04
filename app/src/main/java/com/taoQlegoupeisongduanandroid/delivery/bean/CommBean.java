package com.taoQlegoupeisongduanandroid.delivery.bean;

import com.google.gson.Gson;

/**
 * Created by changxing on 2016/8/3.
 */
public class CommBean {

    /**
     * resultCode :
     */

    private String resultCode;

    public static CommBean objectFromData(String str) {

        return new Gson().fromJson(str, CommBean.class);
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
}
