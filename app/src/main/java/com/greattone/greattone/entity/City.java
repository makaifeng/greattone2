package com.greattone.greattone.entity;

import java.util.List;


public class City {
	String name;
	List<String> areaList;
//	List<District> areaList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<String> areaList) {
		this.areaList = areaList;
	}


}
