package com.greattone.greattone.entity;

import java.io.Serializable;

public class UserInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5993714339551053306L;
	String userid;// "userid": 1
	String username;// "username": "jimmy"
	String userfen;// "userfen": "804"
	String usermoney;// "usermoney": "1110.02"
	String userdate;// "userdate": 0
	String groupname;// "groupname": "普通会员"
	String havemsg;// "havemsg": "0"
	int groupid;// "groupid": "1",
	String token;// "token": "hImDCeNsSQ5NfZ1JQBgZ",
	String commentnum;// "commentnum": "13"
	String favanum;// "favanum": "3",
	String email;// "email": "123456@qq.com",
	String userpic;// "userpic":"http://localhost:8888/e/data/images/nouserpic.gif"
	String registertime;// "registertime": "1449913901",
	String money;// "money": "1110.02",
	String zgroupid;// "zgroupid": "0",
	String checked;// "checked": "1",
	String truename;// "truename": "asdasd",
	String oicq;// "oicq": "asd",
	String msn;// "msn": "",
	String mycall;// "mycall": "",
	String phone;// "phone": "asd",
	String addres;// 详细地址
	String address;// 省
	String zip;// "zip": "",
	String spacestyleid;// "spacestyleid": "1",
	String homepage;// "homepage": "",
	String saytext;// "saytext": "ECMS之家，想到即可做到！",
	String company;// "company": "",
	String fax;// "fax": "",
	String spacename;// "spacename": "",
	String spacegg;// "spacegg": "",
	String viewstats;// "viewstats": "1",
	String regip;// "regip": "192.168.1.101",
	String lasttime;// "lasttime": "1452535056",
	String lastip;// "lastip": "::1",
	String loginnum;// "loginnum": "122",
	String regipport;// "regipport": "64756",
	String lastipport;// "lastipport": "63463",
	String feednum;// "lastipport": "63463",
	String follownum;// "lastipport": "63463",
//	String level;// "lastipport": "63463",
	Level level;// "lastipport": "63463",
	String address1;// 市区
	String address2;// 地区
	String music_star;// 达人类型
	String wznum;// wznum=0
	String distance;// 距离
	String sex;// 性别
	String chusheng;// 生日
	String putong_shenfen;//普通身份类型
	String teacher_type;//老师类型
	String classroom_type;//教室类型
	String photo;//个人照片
	String photos;//琴行照片(4张)
	String photoID;//手持身份证照片
	String shenfenzheng;//身份证号码
	String biye;//毕业学院
	String aihao;//
	String measure;//
	String telephone;//
	String postnum;//
	String plnum;//
	String shareurl;//分享链接
	String fuzeren;//负责人
	int sign;//是否完善资料
	int cked;//是否认证
	int verification;//是否认证
	int isfeed;//是否关注
	int isfans;//是否关注
	int isfriend;//是否是好友
	int isstudent;//是否是他的学生

	public String getShareurl() {
		return shareurl;
	}

	public void setShareurl(String shareurl) {
		this.shareurl = shareurl;
	}

	public int getCked() {
		return cked;
	}

	public void setCked(int cked) {
		this.cked = cked;
	}

	public int getIsstudent() {
		return isstudent;
	}

	public void setIsstudent(int isstudent) {
		this.isstudent = isstudent;
	}

	public String getFuzeren() {
		return fuzeren;
	}

	public void setFuzeren(String fuzeren) {
		this.fuzeren = fuzeren;
	}

	public String getPlnum() {
		return plnum;
	}

	public void setPlnum(String plnum) {
		this.plnum = plnum;
	}

	public String getPostnum() {
		return postnum;
	}

	public void setPostnum(String postnum) {
		this.postnum = postnum;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAihao() {
		return aihao;
	}

	public void setAihao(String aihao) {
		this.aihao = aihao;
	}

	public int getSign() {
		return sign;
	}

	public void setSign(int sign) {
		this.sign = sign;
	}

	public String getBiye() {
		return biye;
	}

	public void setBiye(String biye) {
		this.biye = biye;
	}

	public String getShenfenzheng() {
		return shenfenzheng;
	}

	public void setShenfenzheng(String shenfenzheng) {
		this.shenfenzheng = shenfenzheng;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getPhotoID() {
		return photoID;
	}

	public void setPhotoID(String photoID) {
		this.photoID = photoID;
	}

	public String getPutong_shenfen() {
		return putong_shenfen;
	}

	public void setPutong_shenfen(String putong_shenfen) {
		this.putong_shenfen = putong_shenfen;
	}

	public String getTeacher_type() {
		return teacher_type;
	}

	public void setTeacher_type(String teacher_type) {
		this.teacher_type = teacher_type;
	}

	public String getClassroom_type() {
		return classroom_type;
	}

	public void setClassroom_type(String classroom_type) {
		this.classroom_type = classroom_type;
	}

	public String getAddres() {
		return addres;
	}

	public void setAddres(String addres) {
		this.addres = addres;
	}
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public int getIsfeed() {
		return isfeed;
	}

	public void setIsfeed(int isfeed) {
		this.isfeed = isfeed;
	}

	public int getIsfriend() {
		return isfriend;
	}

	public void setIsfriend(int isfriend) {
		this.isfriend = isfriend;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getChusheng() {
		return chusheng;
	}

	public void setChusheng(String chusheng) {
		this.chusheng = chusheng;
	}

	public String getMusic_star() {
		return music_star;
	}

	public void setMusic_star(String music_star) {
		this.music_star = music_star;
	}

	public String getWznum() {
		return wznum;
	}

	public void setWznum(String wznum) {
		this.wznum = wznum;
	}

	public int getVerification() {
		return verification;
	}

	public void setVerification(int verification) {
		this.verification = verification;
	}

	public String getFeednum() {
		return feednum;
	}

	public void setFeednum(String feednum) {
		this.feednum = feednum;
	}

	public String getFollownum() {
		return follownum;
	}

	public void setFollownum(String follownum) {
		this.follownum = follownum;
	}


	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

//	public String getLevel() {
//		return level;
//	}
//
//	public void setLevel(String level) {
//		this.level = level;
//	}

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getZgroupid() {
		return zgroupid;
	}

	public void setZgroupid(String zgroupid) {
		this.zgroupid = zgroupid;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getOicq() {
		return oicq;
	}

	public void setOicq(String oicq) {
		this.oicq = oicq;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getMycall() {
		return mycall;
	}

	public void setMycall(String mycall) {
		this.mycall = mycall;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getSpacestyleid() {
		return spacestyleid;
	}

	public void setSpacestyleid(String spacestyleid) {
		this.spacestyleid = spacestyleid;
	}

	public String getHomepage() {
		return homepage;
	}

	public int getIsfans() {
		return isfans;
	}

	public void setIsfans(int isfans) {
		this.isfans = isfans;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getSaytext() {
		return saytext;
	}

	public void setSaytext(String saytext) {
		this.saytext = saytext;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getSpacename() {
		return spacename;
	}

	public void setSpacename(String spacename) {
		this.spacename = spacename;
	}

	public String getSpacegg() {
		return spacegg;
	}

	public void setSpacegg(String spacegg) {
		this.spacegg = spacegg;
	}

	public String getViewstats() {
		return viewstats;
	}

	public void setViewstats(String viewstats) {
		this.viewstats = viewstats;
	}

	public String getRegip() {
		return regip;
	}

	public void setRegip(String regip) {
		this.regip = regip;
	}

	public String getLasttime() {
		return lasttime;
	}

	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}

	public String getLastip() {
		return lastip;
	}

	public void setLastip(String lastip) {
		this.lastip = lastip;
	}

	public String getLoginnum() {
		return loginnum;
	}

	public void setLoginnum(String loginnum) {
		this.loginnum = loginnum;
	}

	public String getRegipport() {
		return regipport;
	}

	public void setRegipport(String regipport) {
		this.regipport = regipport;
	}

	public String getLastipport() {
		return lastipport;
	}

	public void setLastipport(String lastipport) {
		this.lastipport = lastipport;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserfen() {
		return userfen;
	}

	public void setUserfen(String userfen) {
		this.userfen = userfen;
	}

	public String getUsermoney() {
		return usermoney;
	}

	public void setUsermoney(String usermoney) {
		this.usermoney = usermoney;
	}

	public String getUserdate() {
		return userdate;
	}

	public void setUserdate(String userdate) {
		this.userdate = userdate;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getHavemsg() {
		return havemsg;
	}

	public void setHavemsg(String havemsg) {
		this.havemsg = havemsg;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCommentnum() {
		return commentnum;
	}

	public void setCommentnum(String commentnum) {
		this.commentnum = commentnum;
	}

	public String getFavanum() {
		return favanum;
	}

	public void setFavanum(String favanum) {
		this.favanum = favanum;
	}

	public String getRegistertime() {
		return registertime;
	}

	public void setRegistertime(String registertime) {
		this.registertime = registertime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserpic() {
		return userpic;
	}

	public void setUserpic(String userpic) {
		this.userpic = userpic;
	}

	public String getMeasure() {
		return measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}

}
