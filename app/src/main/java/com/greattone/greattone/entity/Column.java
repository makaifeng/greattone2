package com.greattone.greattone.entity;

public class Column {
String name;
int  classid;
public Column() {
	super();
}
public Column(String name, int classid) {
	super();
	this.name = name;
	this.classid = classid;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public int getClassid() {
	return classid;
}
public void setClassid(int classid) {
	this.classid = classid;
}

}
