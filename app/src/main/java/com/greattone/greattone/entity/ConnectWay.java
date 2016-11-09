package com.greattone.greattone.entity;

import java.io.Serializable;

public class ConnectWay implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 8724023507231236234L;
String id;
String title;
String titleurl;
String timestamp;
String classid;
String classname;
String address;
String newstime;
String phone;
String mail;
String url;
String php_name;
String photo;

public String getPhp_name() {
	return php_name;
}
public void setPhp_name(String php_name) {
	this.php_name = php_name;
}
public String getPhoto() {
	return photo;
}
public void setPhoto(String photo) {
	this.photo = photo;
}
public String getMail() {
	return mail;
}
public void setMail(String mail) {
	this.mail = mail;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
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
public String getTitleurl() {
	return titleurl;
}
public void setTitleurl(String titleurl) {
	this.titleurl = titleurl;
}
public String getTimestamp() {
	return timestamp;
}
public void setTimestamp(String timestamp) {
	this.timestamp = timestamp;
}
public String getClassid() {
	return classid;
}
public void setClassid(String classid) {
	this.classid = classid;
}
public String getClassname() {
	return classname;
}
public void setClassname(String classname) {
	this.classname = classname;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getNewstime() {
	return newstime;
}
public void setNewstime(String newstime) {
	this.newstime = newstime;
}
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
}
