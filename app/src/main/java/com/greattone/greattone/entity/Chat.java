package com.greattone.greattone.entity;

public class Chat {
//	  private ChatContent content;
//	  private String createtime;
//	  private String head;
//	  private String id;
//	  private boolean isShowTime;
//	  private String name;
//	  private String types;
	  
	private String from_userid;
	private String haveread;
	  private int issys;
	  private String msgtime;
	  private String mid;
	  private String msgtext;
	  private String from_username;
	  private String title;
	  private String unreadnum;
	  private String userpic;
	  private String to_username;
	  private String msg_type="1";
	  public String getMsg_type() {
		return msg_type;
	}
	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}
	public String getMsg_route() {
		return msg_route;
	}
	public void setMsg_route(String msg_route) {
		this.msg_route = msg_route;
	}
	private String msg_route;
	  
	  
	public String getFrom_userid() {
		return from_userid;
	}
	public void setFrom_userid(String from_userid) {
		this.from_userid = from_userid;
	}
	public String getHaveread() {
		return haveread;
	}
	public void setHaveread(String haveread) {
		this.haveread = haveread;
	}
	public String getTo_username() {
		return to_username;
	}
	public void setTo_username(String to_username) {
		this.to_username = to_username;
	}
	public int getIssys() {
		return issys;
	}
	public void setIssys(int issys) {
		this.issys = issys;
	}
	public String getMsgtime() {
		return msgtime;
	}
	public void setMsgtime(String msgtime) {
		this.msgtime = msgtime;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getMsgtext() {
		return msgtext;
	}
	public void setMsgtext(String msgtext) {
		this.msgtext = msgtext;
	}
	public String getFrom_username() {
		return from_username;
	}
	public void setFrom_username(String from_username) {
		this.from_username = from_username;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUnreadnum() {
		return unreadnum;
	}
	public void setUnreadnum(String unreadnum) {
		this.unreadnum = unreadnum;
	}
	public String getUserpic() {
		return userpic;
	}
	public void setUserpic(String userpic) {
		this.userpic = userpic;
	}
	  
}
