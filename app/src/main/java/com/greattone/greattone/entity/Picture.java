package com.greattone.greattone.entity;

import java.io.Serializable;

public class Picture implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3839332990274809184L;
	String picUrl;
	long size;
	int  type; //0 为本地图片 1为网络图片
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	
}
