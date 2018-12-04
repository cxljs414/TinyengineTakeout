package com.taoQlegoupeisongduanandroid.delivery.bean;

import com.google.gson.Gson;

/**
 * Created by changxing on 2016/8/1.
 */
public class UpdateBean {

    /**
     * version : 2
     * downloadUrl : http://1.023wx.cn/addons/we7_wmall/app/takeout1.0.apk
     */

    private String version;
    private String downloadUrl;

    public static UpdateBean objectFromData(String str) {

        return new Gson().fromJson(str, UpdateBean.class);
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
