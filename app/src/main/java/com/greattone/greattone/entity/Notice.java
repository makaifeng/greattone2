package com.greattone.greattone.entity;

import java.io.Serializable;

public class Notice implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 9126800364217300807L;
String newstime;
String classid;
String id;
String classname;
String smalltext;
String timestamp;
String title;
String intro;
public String getIntro() {
	return intro;
}
public void setIntro(String intro) {
	this.intro = intro;
}
public String getNewstime() {
	return newstime;
}
public void setNewstime(String newstime) {
	this.newstime = newstime;
}
public String getClassid() {
	return classid;
}
public void setClassid(String classid) {
	this.classid = classid;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getClassname() {
	return classname;
}
public void setClassname(String classname) {
	this.classname = classname;
}
public String getSmalltext() {
	return smalltext;
}
public void setSmalltext(String smalltext) {
	this.smalltext = smalltext;
}
public String getTimestamp() {
	return timestamp;
}
public void setTimestamp(String timestamp) {
	this.timestamp = timestamp;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}

}
