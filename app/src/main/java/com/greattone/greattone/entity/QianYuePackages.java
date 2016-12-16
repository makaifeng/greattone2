package com.greattone.greattone.entity;

/**
 * Created by Administrator on 2016/12/15.
 */
public class QianYuePackages {
    String name;
    String content;
    String price_now;
    String unit;
    String price_old;
    int isshow_old;
    String remark;
    String packageid;

    public String getPackageid() {
        return packageid;
    }

    public void setPackageid(String packageid) {
        this.packageid = packageid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrice_now() {
        return price_now;
    }

    public void setPrice_now(String price_now) {
        this.price_now = price_now;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPrice_old() {
        return price_old;
    }

    public void setPrice_old(String price_old) {
        this.price_old = price_old;
    }

    public int getIsshow_old() {
        return isshow_old;
    }

    public void setIsshow_old(int isshow_old) {
        this.isshow_old = isshow_old;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
