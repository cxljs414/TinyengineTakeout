package com.taoQlegoupeisongduanandroid.delivery.bean;

import com.google.gson.Gson;

/**
 * Created by changxing on 2016/7/28.
 */
public class Cash {

    /**
     * getcash_id : 15
     */

    private String getcash_id;

    public static Cash objectFromData(String str) {

        return new Gson().fromJson(str, Cash.class);
    }

    public String getGetcash_id() {
        return getcash_id;
    }

    public void setGetcash_id(String getcash_id) {
        this.getcash_id = getcash_id;
    }
}
