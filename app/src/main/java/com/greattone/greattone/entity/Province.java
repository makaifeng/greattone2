package com.greattone.greattone.entity;

import java.util.List;

public class Province {
	String name;
	List<City> cityList;
//	List<City> city;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public List<City> getCity() {
//		return city;
//	}
//
//	public void setCity(List<City> city) {
//		this.city = city;
//	}




	@Override
	public String toString() {
		StringBuffer buffer=new StringBuffer();
		buffer.append("{Province:"+getName()+"[");
		for (int i = 0; i < getCityList().size(); i++) {
			buffer.append("{city:"+getCityList().get(i).getName()+"[");
			for (int j = 0; j < getCityList().get(i).getAreaList().size(); j++) {
				buffer.append("{district:"+getCityList().get(i).getAreaList().get(j)+"},");
			}
			buffer.append("],");
		}
		buffer.append("]}");
		return buffer.toString();
	}

	public List<City> getCityList() {
		return cityList;
	}

	public void setCityList(List<City> cityList) {
		this.cityList = cityList;
	}
}
