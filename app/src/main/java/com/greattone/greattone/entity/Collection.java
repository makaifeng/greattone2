package com.greattone.greattone.entity;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;

public class Collection implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1282429891272168153L;
	String cid;
	int classid;
	String cname;
	String favaid;
	String favatime;
	int id;
	String tbname;
	String title;
	String userid;
 String titleurl;//http:\/\/hao.franzsandner.com\/guangchang\/shipin\/2016-02-02\/280.html",
 String username;//\u8d75\u7586\u6606",
 String thumbnail;//",
 String titlepic;//",
 String notimg;//http:\/\/app.umecn.come\/data\/images\/notimg.gif",
 String newstime;//2016-02-02",
 String timestamp;//1454392264",
 String smalltext;//",
 String classname;//\u89c6\u9891",
 String onclick;//0",
 String plnum;//0",
 List<Pic> morepic;//[],
 UserInfo userinfo;
	
		public List<Pic> getMorepic() {
		return morepic;
	}
	public void setMorepic(List<Pic> morepic) {
		this.morepic = morepic;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getTitleurl() {
		return titleurl;
	}
	public void setTitleurl(String titleurl) {
		this.titleurl = titleurl;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getTitlepic() {
		return titlepic;
	}
	public void setTitlepic(String titlepic) {
		this.titlepic = titlepic;
	}
	public String getNotimg() {
		return notimg;
	}
	public void setNotimg(String notimg) {
		this.notimg = notimg;
	}
	public String getNewstime() {
		return newstime;
	}
	public void setNewstime(String newstime) {
		this.newstime = newstime;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getSmalltext() {
		return smalltext;
	}
	public void setSmalltext(String smalltext) {
		this.smalltext = smalltext;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public String getOnclick() {
		return onclick;
	}
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
	public String getPlnum() {
		return plnum;
	}
	public void setPlnum(String plnum) {
		this.plnum = plnum;
	}
	public UserInfo getUserinfo() {
		return userinfo;
	}
	public void setUserinfo(String userinfo) {
		if (userinfo.startsWith("{")) {
			this.userinfo=JSON.parseObject(userinfo, UserInfo.class);
		}else {
			this.userinfo = new  UserInfo();
			
		}
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getFavaid() {
		return favaid;
	}
	public void setFavaid(String favaid) {
		this.favaid = favaid;
	}
	public String getFavatime() {
		return favatime;
	}
	public void setFavatime(String favatime) {
		this.favatime = favatime;
	}
	public int getClassid() {
		return classid;
	}
	public void setClassid(int classid) {
		this.classid = classid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTbname() {
		return tbname;
	}
	public void setTbname(String tbname) {
		this.tbname = tbname;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
