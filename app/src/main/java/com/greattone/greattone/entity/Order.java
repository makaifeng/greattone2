package com.greattone.greattone.entity;

public class Order {
	  private String ddid;
	  private String ddno;
	  private String ddtime;
	  private String doing;
	  private String laiyuan;
	  private String money="";
	  private String no;
	  private String myorder;
	  private String shoptitle;
	  private String shopzhuang;
	  private String trueid;
	  private String qa_id;//订单编号
	  private String qa_name;//对方的名字
	  private String newstime;//下单时间
	  private String price;//价格
	  private String bitype;//币种  人民币  新台币
	  
	  public String getBitype() {
		return bitype;
	}
	public void setBitype(String bitype) {
		this.bitype = bitype;
	}
	public String getMyorder() {
		return myorder;
	}
	public void setMyorder(String myorder) {
		this.myorder = myorder;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getNewstime() {
		return newstime;
	}
	public void setNewstime(String newstime) {
		this.newstime = newstime;
	}
	public String getQa_name() {
		return qa_name;
	}
	public void setQa_name(String qa_name) {
		this.qa_name = qa_name;
	}
	private String title;//标题
//	  private String title;//标题
	  
	public String getQa_id() {
		return qa_id;
	}
	public void setQa_id(String qa_id) {
		this.qa_id = qa_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDdid() {
		return ddid;
	}
	public void setDdid(String ddid) {
		this.ddid = ddid;
	}
	public String getDdno() {
		return ddno;
	}
	public void setDdno(String ddno) {
		this.ddno = ddno;
	}
	public String getDdtime() {
		return ddtime;
	}
	public void setDdtime(String ddtime) {
		this.ddtime = ddtime;
	}
	public String getDoing() {
		return doing;
	}
	public void setDoing(String doing) {
		this.doing = doing;
	}
	public String getLaiyuan() {
		return laiyuan;
	}
	public void setLaiyuan(String laiyuan) {
		this.laiyuan = laiyuan;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getShoptitle() {
		return shoptitle;
	}
	public void setShoptitle(String shoptitle) {
		this.shoptitle = shoptitle;
	}
	public String getShopzhuang() {
		return shopzhuang;
	}
	public void setShopzhuang(String shopzhuang) {
		this.shopzhuang = shopzhuang;
	}
	public String getTrueid() {
		return trueid;
	}
	public void setTrueid(String trueid) {
		this.trueid = trueid;
	}
}
