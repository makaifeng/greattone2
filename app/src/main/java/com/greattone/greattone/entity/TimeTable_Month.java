package com.greattone.greattone.entity;

/**
 * 课表每月信息
 * Created by Administrator on 2016/11/1.
 */
public class TimeTable_Month {
    String id;
    String tearid;
    String ddid;
    String couname;
    String stuname;
    String starttime;
    String stoptime;
    String state;

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTearid() {
        return tearid;
    }

    public void setTearid(String tearid) {
        this.tearid = tearid;
    }

    public String getDdid() {
        return ddid;
    }

    public void setDdid(String ddid) {
        this.ddid = ddid;
    }

    public String getCouname() {
        return couname;
    }

    public void setCouname(String couname) {
        this.couname = couname;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getStoptime() {
        return stoptime;
    }

    public void setStoptime(String stoptime) {
        this.stoptime = stoptime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
