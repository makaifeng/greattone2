package com.greattone.greattone.entity;

public class Video {
	String id;
	String shipin;
	String titlepic;
	String title;
	String onclick;
	String plnum;
UserInfo userInfo;

	public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

	public String getTitlepic() {
	return titlepic;
}

public void setTitlepic(String titlepic) {
	this.titlepic = titlepic;
}

public String getTitle() {
	return title;
}

public void setTitle(String title) {
	this.title = title;
}

public String getOnclick() {
	return onclick;
}

public void setOnclick(String onclick) {
	this.onclick = onclick;
}

public String getPlnum() {
	return plnum;
}

public void setPlnum(String plnum) {
	this.plnum = plnum;
}

public UserInfo getUserInfo() {
	return userInfo;
}

public void setUserInfo(UserInfo userInfo) {
	this.userInfo = userInfo;
}

	public String getShipin() {
		return shipin;
	}

	public void setShipin(String shipin) {
		this.shipin = shipin;
	}
}
