package com.greattone.greattone.entity;

import java.util.List;

public class Filter {
	List<String> classname;
	List<OrderBy> orderby;
	public List<String> getClassname() {
		return classname;
	}
	public void setClassname(List<String> classname) {
		this.classname = classname;
	}
	public List<OrderBy> getOrderby() {
		return orderby;
	}
	public void setOrderby(List<OrderBy> orderby) {
		this.orderby = orderby;
	}
	
}
