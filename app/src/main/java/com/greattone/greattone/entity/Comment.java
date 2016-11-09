package com.greattone.greattone.entity;

public class Comment {
	int id;
	String classid;
	int plid;
	int plstep;
	int pluserid;
	String plusername;
	int zcnum;
	String userpic;
	String replys;
	String saytext;
	String saytime;
	public int getPlid() {
		return plid;
	}
	public void setPlid(int plid) {
		this.plid = plid;
	}
	public int getPlstep() {
		return plstep;
	}
	public void setPlstep(int plstep) {
		this.plstep = plstep;
	}
	public String getPlusername() {
		return plusername;
	}
	public void setPlusername(String plusername) {
		this.plusername = plusername;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getClassid() {
		return classid;
	}
	public void setClassid(String classid) {
		this.classid = classid;
	}
	public int getZcnum() {
		return zcnum;
	}
	public void setZcnum(int zcnum) {
		this.zcnum = zcnum;
	}
	public String getUserpic() {
		return userpic;
	}
	public void setUserpic(String userpic) {
		this.userpic = userpic;
	}
	public String getSaytext() {
		return saytext;
	}
	public void setSaytext(String saytext) {
		this.saytext = saytext;
	}
	public String getSaytime() {
		return saytime;
	}
	public void setSaytime(String saytime) {
		this.saytime = saytime;
	}
	public int getPluserid() {
		return pluserid;
	}
	public void setPluserid(int pluserid) {
		this.pluserid = pluserid;
	}
	public String getReplys() {
		return replys;
	}
	public void setReplys(String replys) {
		this.replys = replys;
	}
	
}
