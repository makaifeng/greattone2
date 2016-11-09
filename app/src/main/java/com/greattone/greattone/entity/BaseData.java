package com.greattone.greattone.entity;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;

public class BaseData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5993714339551053306L;
	String identity;// 身份
	String video;
	int fans;// 粉丝数
	int isvip;// 是否是Vip
	String level;// 等级
	String name;// 名字
	String pic;// 图片地址
	String title;// 标题
	String url;// 跳转路径
	int uid;
	String address;
	String uname;
	String mobile;
	String distance;
	String check;
	int friend;
	int iffocus;
	int iscomment;
	int isfans;
	int isfriend;
	int quality;
	int serve;
	int setting;
	int star;
	List<Pic> list;
	List<Pic> piclist;
	int rid;
	String latitude;
	String longitude;
	int hourNum;
	String mtkprice;
	String price;// 价格
	int teacher_count;// 老师数
	int room_count;// 教室数
	int focusMeNum;// 关注我的数量
	int id;
	int focus;
	String time;
	String remaining;
	int issign;
	String hobby;
	int roomid;
	String cname;
	String city;
	int iscommet;
	String icon;
	String username;
	int groupid;
	String token;
	int isIdent;
	int blogId;
	int comment;
	int hits;
	int commnum;//
	String desc;// 描述
	String content;// 帖子内容
	String descr;// 帖子描述
	long ctime;// 发帖时间
	int isreprint;// 是否是转载
	String music;
	String photo;// 帖子照片
	String reprintContent;//
	int reprintId;// 转载id
	String reprintUid;// 转载的用户id
	String reprintUname;// 转载的用户名
	int reprnum;//
	String shareLink;// 分享链接
	String staff;//
	int type;// 帖子的类型 1视频 2音乐 3图片 4文本
	String videoid;//
	long btime;// 开始时间
	long endtime;// 结束时间
	String venue;// 活动举办地
	int cid;
	String contenturl;//
	String countApply;//
	long etime;// 结束时间
	int hot;//
	int isapply;//
	String issuer;// 发布人
	String lat;//
	String lng;//
	int num;//
	String share;// 分享链接
	String summarizeurl;// 总结网址
	String head;// 消息头像
	String funame;// 消息的好友名
	String fuid;// 消息的好友id
	long createtime;// 消息的创建时间
	int news;// 消息数
	int commentnum;// 我的消息的
	int msgcount;// 我的消息的
	int noticecount;// 我的息的
	List<agencyPic> agencyPicList;
	String area;// 市辖区
	String manager;// 教室负责人
	String roomsArea;//
	String gender;// 性别
	String nickname;// 昵称
	String email;// 邮箱
	String province;//
	String birthday;// 生日
	String idCard;//
	String school;//
	String idPic;//
	String personPic;//
	String phone;//
	String roomArea;//
	String realname;//
	String applyId;//
	String collect;//

	List<Recommment> recomment;
	
	  private String attention;

	public String getAttention() {
		return attention;
	}

	public void setAttention(String attention) {
		this.attention = attention;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getCollect() {
		return collect;
	}

	public void setCollect(String collect) {
		this.collect = collect;
	}


	public List<Pic> getPiclist() {
		return piclist;
	}

	public void setPiclist(String pic) {
		this.piclist = JSON.parseArray(pic,Pic.class);
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getRoomArea() {
		return roomArea;
	}

	public void setRoomArea(String roomArea) {
		this.roomArea = roomArea;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdPic() {
		return idPic;
	}

	public void setIdPic(String idPic) {
		this.idPic = idPic;
	}

	public String getPersonPic() {
		return personPic;
	}

	public void setPersonPic(String personPic) {
		this.personPic = personPic;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}


	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getRoomsArea() {
		return roomsArea;
	}

	public void setRoomsArea(String roomsArea) {
		this.roomsArea = roomsArea;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<agencyPic> getAgencyPicList() {
		return agencyPicList;
	}

	public void setAgencyPicList(List<agencyPic> agencyPicList) {
		this.agencyPicList = agencyPicList;
	}

	public int getCommentnum() {
		return commentnum;
	}

	public void setCommentnum(int commentnum) {
		this.commentnum = commentnum;
	}

	public int getMsgcount() {
		return msgcount;
	}

	public void setMsgcount(int msgcount) {
		this.msgcount = msgcount;
	}

	public int getNoticecount() {
		return noticecount;
	}

	public void setNoticecount(int noticecount) {
		this.noticecount = noticecount;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getFuname() {
		return funame;
	}

	public void setFuname(String funame) {
		this.funame = funame;
	}

	public String getFuid() {
		return fuid;
	}

	public void setFuid(String fuid) {
		this.fuid = fuid;
	}

	public long getCreatetime() {
		return createtime;
	}

	public void setCreatetime(long createtime) {
		this.createtime = createtime;
	}

	public int getNews() {
		return news;
	}

	public void setNews(int news) {
		this.news = news;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getContenturl() {
		return contenturl;
	}

	public void setContenturl(String contenturl) {
		this.contenturl = contenturl;
	}

	public String getCountApply() {
		return countApply;
	}

	public void setCountApply(String countApply) {
		this.countApply = countApply;
	}

	public long getEtime() {
		return etime;
	}

	public void setEtime(long etime) {
		this.etime = etime;
	}

	public int getHot() {
		return hot;
	}

	public void setHot(int hot) {
		this.hot = hot;
	}

	public int getIsapply() {
		return isapply;
	}

	public void setIsapply(int isapply) {
		this.isapply = isapply;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public int getNum() {
		return num;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getShare() {
		return share;
	}

	public void setShare(String share) {
		this.share = share;
	}

	public String getSummarizeurl() {
		return summarizeurl;
	}

	public void setSummarizeurl(String summarizeurl) {
		this.summarizeurl = summarizeurl;
	}

	public long getBtime() {
		return btime;
	}

	public void setBtime(long btime) {
		this.btime = btime;
	}

	public long getEndtime() {
		return endtime;
	}

	public void setEndtime(long endtime) {
		this.endtime = endtime;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public int getCommnum() {
		return commnum;
	}

	public void setCommnum(int commnum) {
		this.commnum = commnum;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public long getCtime() {
		return ctime;
	}

	public void setCtime(long ctime) {
		this.ctime = ctime;
	}

	public int getIsreprint() {
		return isreprint;
	}

	public void setIsreprint(int isreprint) {
		this.isreprint = isreprint;
	}

	public String getMusic() {
		return music;
	}

	public void setMusic(String music) {
		this.music = music;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getReprintContent() {
		return reprintContent;
	}

	public void setReprintContent(String reprintContent) {
		this.reprintContent = reprintContent;
	}

	public int getReprintId() {
		return reprintId;
	}

	public void setReprintId(int reprintId) {
		this.reprintId = reprintId;
	}

	public String getReprintUid() {
		return reprintUid;
	}

	public void setReprintUid(String reprintUid) {
		this.reprintUid = reprintUid;
	}

	public String getReprintUname() {
		return reprintUname;
	}

	public void setReprintUname(String reprintUname) {
		this.reprintUname = reprintUname;
	}

	public int getReprnum() {
		return reprnum;
	}

	public void setReprnum(int reprnum) {
		this.reprnum = reprnum;
	}

	public String getShareLink() {
		return shareLink;
	}

	public void setShareLink(String shareLink) {
		this.shareLink = shareLink;
	}

	public String getStaff() {
		return staff;
	}

	public void setStaff(String staff) {
		this.staff = staff;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getVideoid() {
		return videoid;
	}

	public void setVideoid(String videoid) {
		this.videoid = videoid;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public int getFans() {
		return fans;
	}

	public void setFans(int fans) {
		this.fans = fans;
	}

	public int getIsvip() {
		return isvip;
	}

	public void setIsvip(int isvip) {
		this.isvip = isvip;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public int getFriend() {
		return friend;
	}

	public void setFriend(int friend) {
		this.friend = friend;
	}

	public int getIffocus() {
		return iffocus;
	}

	public void setIffocus(int iffocus) {
		this.iffocus = iffocus;
	}

	public int getIscomment() {
		return iscomment;
	}

	public void setIscomment(int iscomment) {
		this.iscomment = iscomment;
	}

	public int getIsfans() {
		return isfans;
	}

	public void setIsfans(int isfans) {
		this.isfans = isfans;
	}

	public int getIsfriend() {
		return isfriend;
	}

	public void setIsfriend(int isfriend) {
		this.isfriend = isfriend;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	public int getServe() {
		return serve;
	}

	public void setServe(int serve) {
		this.serve = serve;
	}

	public int getSetting() {
		return setting;
	}

	public void setSetting(int setting) {
		this.setting = setting;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public List<Pic> getList() {
		return list;
	}

	public void setList(List<Pic> list) {
		this.list = list;
	}

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public int getHourNum() {
		return hourNum;
	}

	public void setHourNum(int hourNum) {
		this.hourNum = hourNum;
	}

	public String getMtkprice() {
		return mtkprice;
	}

	public void setMtkprice(String mtkprice) {
		this.mtkprice = mtkprice;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getTeacher_count() {
		return teacher_count;
	}

	public void setTeacher_count(int teacher_count) {
		this.teacher_count = teacher_count;
	}

	public int getRoom_count() {
		return room_count;
	}

	public void setRoom_count(int room_count) {
		this.room_count = room_count;
	}

	public int getFocusMeNum() {
		return focusMeNum;
	}

	public void setFocusMeNum(int focusMeNum) {
		this.focusMeNum = focusMeNum;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFocus() {
		return focus;
	}

	public void setFocus(int focus) {
		this.focus = focus;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getRemaining() {
		return remaining;
	}

	public void setRemaining(String remaining) {
		this.remaining = remaining;
	}

	public int getIssign() {
		return issign;
	}

	public void setIssign(int issign) {
		this.issign = issign;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public int getRoomid() {
		return roomid;
	}

	public void setRoomid(int roomid) {
		this.roomid = roomid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getIscommet() {
		return iscommet;
	}

	public void setIscommet(int iscommet) {
		this.iscommet = iscommet;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getIsIdent() {
		return isIdent;
	}

	public void setIsIdent(int isIdent) {
		this.isIdent = isIdent;
	}

	public int getBlogId() {
		return blogId;
	}

	public void setBlogId(int blogId) {
		this.blogId = blogId;
	}

	public int getComment() {
		return comment;
	}

	public void setComment(int comment) {
		this.comment = comment;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}


	public class agencyPic {
		String agencyPic;

		public String getAgencyPic() {
			return agencyPic;
		}

		public void setAgencyPic(String agencyPic) {
			this.agencyPic = agencyPic;
		}

	}
	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public List<Recommment> getRecomment() {
		return recomment;
	}

	public void setRecomment(List<Recommment> recomment) {
		this.recomment = recomment;
	}
}
