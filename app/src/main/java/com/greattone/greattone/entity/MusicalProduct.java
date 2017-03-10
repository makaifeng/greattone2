package com.greattone.greattone.entity;

/**
 * 乐器商城的乐器产品
 * Created by Administrator on 2017/2/16.
 */


public class MusicalProduct {
    String id;
    String title;
    String subtitle;
    String type;
    String model;
    String titlepic;
    String city;
    String newstext;
    String colour;
    String thumbnail;
    String timestamp;
    String telephone;
    String shardurl;
    String shardpic;
    int money;
    int freight;
    int comms;//评价数
    int postage;//是否包邮

    public String getShardurl() {
        return shardurl;
    }

    public void setShardurl(String shardurl) {
        this.shardurl = shardurl;
    }

    public String getShardpic() {
        return shardpic;
    }

    public void setShardpic(String shardpic) {
        this.shardpic = shardpic;
    }

    public int getComms() {
        return comms;
    }

    public void setComms(int comms) {
        this.comms = comms;
    }

    public int getPostage() {
        return postage;
    }

    public void setPostage(int postage) {
        this.postage = postage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitlepic() {
        return titlepic;
    }

    public void setTitlepic(String titlepic) {
        this.titlepic = titlepic;
    }


    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNewstext() {
        return newstext;
    }

    public void setNewstext(String newstext) {
        this.newstext = newstext;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }


    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getFreight() {
        return freight;
    }

    public void setFreight(int freight) {
        this.freight = freight;
    }
}
