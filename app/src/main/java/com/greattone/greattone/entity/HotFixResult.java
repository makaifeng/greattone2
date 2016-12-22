package com.greattone.greattone.entity;

/**
 * Created by Administrator on 2016/12/22.
 */
public class HotFixResult {
    int newVersionCode;
    String pagerName;
    String url;

    public int getNewVersionCode() {
        return newVersionCode;
    }

    public void setNewVersionCode(int newVersionCode) {
        this.newVersionCode = newVersionCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPagerName() {
        return pagerName;
    }

    public void setPagerName(String pagerName) {
        this.pagerName = pagerName;
    }
}
