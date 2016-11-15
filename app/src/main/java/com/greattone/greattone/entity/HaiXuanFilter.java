package com.greattone.greattone.entity;

import java.util.List;

public class HaiXuanFilter {
List<String> area;
String group;
String RMB;
String NTC;

	public String getRMB() {
		return RMB;
	}

	public void setRMB(String RMB) {
		this.RMB = RMB;
	}

	public String getNTC() {
		return NTC;
	}

	public void setNTC(String NTC) {
		this.NTC = NTC;
	}

	public List<String> getArea() {
	return area;
}
public void setArea(List<String> area) {
	this.area = area;
}
public String getGroup() {
	return group;
}
public void setGroup(String group) {
	this.group = group;
}

}
