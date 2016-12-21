package com.greattone.greattone.entity;

import com.greattone.greattone.util.Textutil;

public class District {
	String name;//简体名字
//	String zipcode;
	String traName;//繁体名字
	public String getTraName() {
		return traName;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		try {
			this.traName = Textutil.Sim2Tra(name).toString();
		} catch (Exception e) {
			this.traName=name;
			e.printStackTrace();
		}
	}

//	public String getZipcode() {
//		return zipcode;
//	}
//
//	public void setZipcode(String zipcode) {
//		this.zipcode = zipcode;
//	}
}
