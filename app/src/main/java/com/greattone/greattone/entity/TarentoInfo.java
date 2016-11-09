package com.greattone.greattone.entity;

import java.util.List;

public class TarentoInfo {
	private BaseData activity;
	private String desc;
	private List<BaseData> fans;
	private List<BaseData> focus;
	private String isvip;
	private List<BaseData> simi;
	public BaseData getActivity() {
		return activity;
	}
	public void setActivity(BaseData activity) {
		this.activity = activity;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public List<BaseData> getFans() {
		return fans;
	}
	public void setFans(List<BaseData> fans) {
		this.fans = fans;
	}
	public List<BaseData> getFocus() {
		return focus;
	}
	public void setFocus(List<BaseData> focus) {
		this.focus = focus;
	}
	public String getIsvip() {
		return isvip;
	}
	public void setIsvip(String isvip) {
		this.isvip = isvip;
	}
	public List<BaseData> getSimi() {
		return simi;
	}
	public void setSimi(List<BaseData> simi) {
		this.simi = simi;
	}
	
}
