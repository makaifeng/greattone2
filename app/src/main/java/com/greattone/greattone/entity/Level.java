package com.greattone.greattone.entity;

import java.io.Serializable;

public class Level implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = -732868673118476285L;
String name;
String pic;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getPic() {
	return pic;
}
public void setPic(String pic) {
	this.pic = pic;
}
}
