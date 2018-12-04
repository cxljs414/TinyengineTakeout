package com.taoQlegoupeisongduanandroid.delivery.bean;

import java.util.List;

/**
 * Created by changxing on 2016/8/1.
 */
public class WorkStatusBean {
    /**
     * alias : IBB844RyBXqssxeQrTEbMY4BCr7r8Yb4
     * tags : ["12345678","hvbCf3Mkzd2O2N2"]
     * work_status_cn : 接单中
     */

    private String alias;
    private String work_status_cn;
    private List<String> tags;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getWork_status_cn() {
        return work_status_cn;
    }

    public void setWork_status_cn(String work_status_cn) {
        this.work_status_cn = work_status_cn;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }


}
