package com.greattone.greattone.entity;

/**
 * @author Administrator
 *
 */
public class Sign {
	String ddid;
	String shoptitle;
	String money;
	String   content;
//	public Sign() {
//		super();
//	}
//	public Sign(String ddid, String shoptitle, String money) {
//		this.ddid = ddid;
//		this.shoptitle = shoptitle;
//		this.money = money;
//	}
	public String getDdid() {
		return ddid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setDdid(String ddid) {
		this.ddid = ddid;
	}
	public String getShoptitle() {
		return shoptitle;
	}
	public void setShoptitle(String shoptitle) {
		this.shoptitle = shoptitle;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	
}
