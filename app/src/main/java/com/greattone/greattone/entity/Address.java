package com.greattone.greattone.entity;

import java.util.List;

public class Address {
String name;
List<City> cityList;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public List<City> getCityList() {
	return cityList;
}
public void setCityList(List<City> cityList) {
	this.cityList = cityList;
}

}
