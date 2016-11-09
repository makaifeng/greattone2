package com.greattone.greattone.entity;

import java.io.Serializable;

public class JPushMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String id;
	String username;
	String type;
	String to_username;
	String myExtras;
	String classid;
	String pic;
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTo_username() {
		return to_username;
	}
	public void setTo_username(String to_username) {
		this.to_username = to_username;
	}
	public String getMyExtras() {
		return myExtras;
	}
	public void setMyExtras(String myExtras) {
		this.myExtras = myExtras;
	}
	public String getClassid() {
		return classid;
	}
	public void setClassid(String classid) {
		this.classid = classid;
	}
	
}
