package com.greattone.greattone.entity;

import java.io.Serializable;
import java.util.List;

public class Blog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3787251944830499374L;
	List<Pic> allphoto;
	int classid;
	String classname;// classname=视频
	String diggdown;// diggdown=(null)
	String diggtop;// diggtop=2
	int id;// id=101
	String isurl;// isurl=0
	String newstime;// newstime=2016-01-12
	String notimg;// notimg=(null)
	String onclick;// onclick=40
	String oneTitlePic;// oneTitlePic=False
	String plnum;// plnum=0
	String smalltext;// smalltext=123
	String tbname;// tbname=photo
	String threeTitlePic;// threeTitlePic=(null)
	String thumbnail;// thumbnail=http://app.umecn.com//img/uploads/thumbs/20160112/120x90_ba2d8354803a8c6633bd6a5565b12ab1.jpg
	String timestamp;// timestamp=1452576444
	String title;// title=【藤缠楼】国外9岁的摇滚吉他手_标清
	String titlepic;// titlepic=http://app.umecn.com/d/file/2016-01-12/ba2d8354803a8c6633bd6a5565b12ab1.jpg
	String titleurl;// titleurl=http://app.umecn.com//guangchang/shipin/2016-01-12/101.html
	String twoTitlePic;// twoTitlePic=(null)
	String username;// username=天道酬勤
	String shipin;// shipin=/d/file/2016-01-12/5977b706bd5b5272f97093cb4504e8ea.mp4
	String ttid;//"ttid": "0", 
	String totaldown;// "totaldown": "0", 
	String newspath;// "newspath": "2016-01-12",
	String filename;// "filename": "101"
	int userid;//  "userid": "80"
	String firsttitle;//  "firsttitle": "0",
	int isgood;//  "isgood": "0",
	int ispic;//  "ispic": "0",
	int istop;//  "istop": "0",
	int isqf;//  "isqf": "0",
	int ismember;//  "ismember": "0",
	String truetime;//   "truetime": "1452576568", 
	String lastdotime;//     "lastdotime": "1452576568",
	int havehtml;//     "havehtml": "1", 
	int groupid;//      "groupid": "0",
	int userfen;//      "userfen": "0",
	String titlefont;//      "titlefont": "", 
	int stb;//     "stb": "1",
	int fstb;//     "fstb": "1",
	int restb;//     "restb": "1",
	String keyboard;//      "keyboard": "", 
	String picurl;//      "picurl": "", 
	String guanjianzi;//    "guanjianzi": "", 
	String zuozhe;//    "zuozhe": "123",
	String laiyuan;//  "laiyuan": "456",
	String yinyue;//  "yinyue": "",
	String keyid;//  "keyid": "",
	int dokey;//     "dokey": "1",
	int newstempid;//     "newstempid": "10",
	int closepl;//     "closepl": "0",
	String infotags;//  "infotags": "",
	String filesize;//  "filesize": "",
	String picsize;//  "picsize": "",
	String picfbl;//  "picfbl": "",
	String picfrom;//  "picfrom": "",
	String morepic;//  "morepic": null,
	int num;//  "num": 0,
	String width;//  "width": "",
	String height;//  "height": "",
	String newstext;//  "newstext": "123",
	String havefava;//    "havefava": null,
	String morepicTotle;//    "morepicTotle": "1",
	String picsay;//    "picsay": null
	String onlinepath;//    "onlinepath": ""
	String flashurl;//    "flashurl": ""
	String downpath;//    "downpath": null
	String resendnum;//转载数
	String userInfo;
	String hai_id;
	String dizhi;//活动举办地址
	String hai_video;//
	String hai_photo;//
	String hai_name;//
	String hai_petition;//
	String huodong_1;//
	String huodong_2;//
	String ad;//
	int isend;//活动是否结束
	public int getIsend() {
		return isend;
	}
	public void setIsend(int isend) {
		this.isend = isend;
	}
	public String getAd() {
		return ad;
	}
	public void setAd(String ad) {
		this.ad = ad;
	}
	public String getHuodong_1() {
		return huodong_1;
	}
	public void setHuodong_1(String huodong_1) {
		this.huodong_1 = huodong_1;
	}
	public String getHuodong_2() {
		return huodong_2;
	}
	public void setHuodong_2(String huodong_2) {
		this.huodong_2 = huodong_2;
	}
	int isfava;//
	int canvote;//是否是投票视频
	
	public String getHai_name() {
		return hai_name;
	}
	public void setHai_name(String hai_name) {
		this.hai_name = hai_name;
	}
	public String getHai_petition() {
		return hai_petition;
	}
	public void setHai_petition(String hai_petition) {
		this.hai_petition = hai_petition;
	}
	public String getHai_video() {
		return hai_video;
	}
	public void setHai_video(String hai_video) {
		this.hai_video = hai_video;
	}
	public String getHai_photo() {
		return hai_photo;
	}
	public void setHai_photo(String hai_photo) {
		this.hai_photo = hai_photo;
	}
	public String getHai_id() {
		return hai_id;
	}
	public void setHai_id(String hai_id) {
		this.hai_id = hai_id;
	}
	public int getCanvote() {
		return canvote;
	}
	public void setCanvote(int canvote) {
		this.canvote = canvote;
	}
	public String getDizhi() {
		return dizhi;
	}
	public void setDizhi(String dizhi) {
		this.dizhi = dizhi;
	}
	public int getIsfava() {
		return isfava;
	}
	public void setIsfava(int isfava) {
		this.isfava = isfava;
	}
	public String getResendnum() {
		return resendnum;
	}
	public void setResendnum(String resendnum) {
		this.resendnum = resendnum;
	}
	public String getTtid() {
		return ttid;
	}
	public void setTtid(String ttid) {
		this.ttid = ttid;
	}
	public String getTotaldown() {
		return totaldown;
	}
	public void setTotaldown(String totaldown) {
		this.totaldown = totaldown;
	}
	public String getNewspath() {
		return newspath;
	}
	public void setNewspath(String newspath) {
		this.newspath = newspath;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getFirsttitle() {
		return firsttitle;
	}
	public void setFirsttitle(String firsttitle) {
		this.firsttitle = firsttitle;
	}
	public int getIsgood() {
		return isgood;
	}
	public void setIsgood(int isgood) {
		this.isgood = isgood;
	}
	public int getIspic() {
		return ispic;
	}
	public void setIspic(int ispic) {
		this.ispic = ispic;
	}
	public int getIstop() {
		return istop;
	}
	public void setIstop(int istop) {
		this.istop = istop;
	}
	public int getIsqf() {
		return isqf;
	}
	public void setIsqf(int isqf) {
		this.isqf = isqf;
	}
	public int getIsmember() {
		return ismember;
	}
	public void setIsmember(int ismember) {
		this.ismember = ismember;
	}
	public String getTruetime() {
		return truetime;
	}
	public void setTruetime(String truetime) {
		this.truetime = truetime;
	}
	public String getLastdotime() {
		return lastdotime;
	}
	public void setLastdotime(String lastdotime) {
		this.lastdotime = lastdotime;
	}
	public int getHavehtml() {
		return havehtml;
	}
	public void setHavehtml(int havehtml) {
		this.havehtml = havehtml;
	}
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
	public int getUserfen() {
		return userfen;
	}
	public void setUserfen(int userfen) {
		this.userfen = userfen;
	}
	public String getTitlefont() {
		return titlefont;
	}
	public void setTitlefont(String titlefont) {
		this.titlefont = titlefont;
	}
	public int getStb() {
		return stb;
	}
	public void setStb(int stb) {
		this.stb = stb;
	}
	public int getFstb() {
		return fstb;
	}
	public void setFstb(int fstb) {
		this.fstb = fstb;
	}
	public int getRestb() {
		return restb;
	}
	public void setRestb(int restb) {
		this.restb = restb;
	}
	public String getKeyboard() {
		return keyboard;
	}
	public void setKeyboard(String keyboard) {
		this.keyboard = keyboard;
	}
	public String getPicurl() {
		return picurl;
	}
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	public String getGuanjianzi() {
		return guanjianzi;
	}
	public void setGuanjianzi(String guanjianzi) {
		this.guanjianzi = guanjianzi;
	}
	public String getZuozhe() {
		return zuozhe;
	}
	public void setZuozhe(String zuozhe) {
		this.zuozhe = zuozhe;
	}
	public String getLaiyuan() {
		return laiyuan;
	}
	public void setLaiyuan(String laiyuan) {
		this.laiyuan = laiyuan;
	}
	public String getYinyue() {
		return yinyue;
	}
	public void setYinyue(String yinyue) {
		this.yinyue = yinyue;
	}
	public String getKeyid() {
		return keyid;
	}
	public void setKeyid(String keyid) {
		this.keyid = keyid;
	}
	public int getDokey() {
		return dokey;
	}
	public void setDokey(int dokey) {
		this.dokey = dokey;
	}
	public int getNewstempid() {
		return newstempid;
	}
	public void setNewstempid(int newstempid) {
		this.newstempid = newstempid;
	}
	public int getClosepl() {
		return closepl;
	}
	public void setClosepl(int closepl) {
		this.closepl = closepl;
	}
	public String getInfotags() {
		return infotags;
	}
	public void setInfotags(String infotags) {
		this.infotags = infotags;
	}
	public String getFilesize() {
		return filesize;
	}
	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}
	public String getPicsize() {
		return picsize;
	}
	public void setPicsize(String picsize) {
		this.picsize = picsize;
	}
	public String getPicfbl() {
		return picfbl;
	}
	public void setPicfbl(String picfbl) {
		this.picfbl = picfbl;
	}
	public String getPicfrom() {
		return picfrom;
	}
	public void setPicfrom(String picfrom) {
		this.picfrom = picfrom;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getNewstext() {
		return newstext;
	}
	public void setNewstext(String newstext) {
		this.newstext = newstext;
	}
	public String getHavefava() {
		return havefava;
	}
	public void setHavefava(String havefava) {
		this.havefava = havefava;
	}
	public String getMorepicTotle() {
		return morepicTotle;
	}
	public void setMorepicTotle(String morepicTotle) {
		this.morepicTotle = morepicTotle;
	}
	public String getPicsay() {
		return picsay;
	}
	public void setPicsay(String picsay) {
		this.picsay = picsay;
	}
	public String getOnlinepath() {
		return onlinepath;
	}
	public void setOnlinepath(String onlinepath) {
		this.onlinepath = onlinepath;
	}
	public String getFlashurl() {
		return flashurl;
	}
	public void setFlashurl(String flashurl) {
		this.flashurl = flashurl;
	}
	public String getDownpath() {
		return downpath;
	}
	public void setDownpath(String downpath) {
		this.downpath = downpath;
	}
	public String getShipin() {
		return shipin;
	}
	public void setShipin(String shipin) {
		this.shipin = shipin;
	}
	public int getClassid() {
		return classid;
	}
	public void setClassid(int classid) {
		this.classid = classid;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public String getDiggdown() {
		return diggdown;
	}
	public void setDiggdown(String diggdown) {
		this.diggdown = diggdown;
	}
	public String getDiggtop() {
		return diggtop;
	}
	public void setDiggtop(String diggtop) {
		this.diggtop = diggtop;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIsurl() {
		return isurl;
	}
	public void setIsurl(String isurl) {
		this.isurl = isurl;
	}
	public String getNewstime() {
		return newstime;
	}
	public void setNewstime(String newstime) {
		this.newstime = newstime;
	}
	public String getNotimg() {
		return notimg;
	}
	public void setNotimg(String notimg) {
		this.notimg = notimg;
	}
	public String getOnclick() {
		return onclick;
	}
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
	public String getOneTitlePic() {
		return oneTitlePic;
	}
	public void setOneTitlePic(String oneTitlePic) {
		this.oneTitlePic = oneTitlePic;
	}
	public String getPlnum() {
		return plnum;
	}
	public void setPlnum(String plnum) {
		this.plnum = plnum;
	}
	public String getSmalltext() {
		return smalltext;
	}
	public void setSmalltext(String smalltext) {
		this.smalltext = smalltext;
	}
	public String getTbname() {
		return tbname;
	}
	public void setTbname(String tbname) {
		this.tbname = tbname;
	}
	public String getThreeTitlePic() {
		return threeTitlePic;
	}
	public void setThreeTitlePic(String threeTitlePic) {
		this.threeTitlePic = threeTitlePic;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitlepic() {
		return titlepic;
	}
	public void setTitlepic(String titlepic) {
		this.titlepic = titlepic;
	}
	public String getTitleurl() {
		return titleurl;
	}
	public void setTitleurl(String titleurl) {
		this.titleurl = titleurl;
	}
	public String getTwoTitlePic() {
		return twoTitlePic;
	}
	public void setTwoTitlePic(String twoTitlePic) {
		this.twoTitlePic = twoTitlePic;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<Pic> getAllphoto() {
		return allphoto;
	}
	public void setAllphoto(List<Pic> allphoto) {
		this.allphoto = allphoto;
	}
	public String getMorepic() {
		return morepic;
	}
	public void setMorepic(String morepic) {
		this.morepic = morepic;
	}
	public String getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}
	
}
