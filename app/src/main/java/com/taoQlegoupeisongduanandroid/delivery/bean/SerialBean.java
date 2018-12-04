package com.taoQlegoupeisongduanandroid.delivery.bean;

import com.google.gson.Gson;

/**
 * Created by changxing on 2016/10/23.
 */
public class SerialBean {


    /**
     * url :
     * uniacid : 1
     * serial_sn : 12345678
     */

    private String url;
    private String uniacid;
    private String serial_sn;
    private String port;

    public static SerialBean objectFromData(String str) {

        return new Gson().fromJson(str, SerialBean.class);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUniacid() {
        return uniacid;
    }

    public void setUniacid(String uniacid) {
        this.uniacid = uniacid;
    }

    public String getSerial_sn() {
        return serial_sn;
    }

    public void setSerial_sn(String serial_sn) {
        this.serial_sn = serial_sn;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
