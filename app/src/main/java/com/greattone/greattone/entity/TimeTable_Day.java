package com.greattone.greattone.entity;

import java.io.Serializable;

/**
 * 课表每日
 * Created by Administrator on 2016/11/2.
 */
public class TimeTable_Day implements Serializable {
    String id;
    String tearid;
    String ddid;
    String couname;
    String starttime;
    String stoptime;
    String classtime;
    String location;
    String tearname;
    String stuname;
    String remarks;
    int state;

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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getClasstime() {
        return classtime;
    }

    public void setClasstime(String classtime) {
        this.classtime = classtime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTearname() {
        return tearname;
    }

    public void setTearname(String tearname) {
        this.tearname = tearname;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
