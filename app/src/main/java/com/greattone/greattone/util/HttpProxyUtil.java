package com.greattone.greattone.util;

import android.content.Context;
import android.provider.MediaStore;

import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.data.ClassId;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.util.HttpUtil.ErrorResponseListener;
import com.greattone.greattone.util.HttpUtil.ResponseListener;

import java.io.File;
import java.util.HashMap;

public class HttpProxyUtil {
	/**全部学员*/
	public static void getStudent(Context context,String userid, ResponseListener responseListener,
			ErrorResponseListener errorResponseListener) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "jiaoshi/getStudent");
		map.put("userid",userid );
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		((BaseActivity) context).	addRequest(HttpUtil.httpConnectionByPost(context, map,
				responseListener, errorResponseListener));
	}
	/**关注*/
	public static void addattention(Context context,String followid, ResponseListener responseListener,
			ErrorResponseListener errorResponseListener) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "user/doFeed");
		map.put("followid",followid );
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		((BaseActivity) context).	addRequest(HttpUtil.httpConnectionByPost(context, map,
				responseListener, errorResponseListener));
	}
	/**上传视频的图片*/
	public static void updatePictureByByte(Context context,String filepass,String classid,String picPath,Boolean isShowProgress, ResponseListener responseListener,
			ErrorResponseListener errorResponseListener) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "post/upfile");
		map.put("uploadkey", "e7627f53d4712552f8d82c30267d9bb4");
		map.put("filepass", filepass);
		map.put("classid", classid);
		map.put("open", "1");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		HashMap<String, byte[]> bytes = new HashMap<String, byte[]>();
		bytes.put("file", BitmapUtil.getVideoPicBytes(picPath,
				MediaStore.Images.Thumbnails.FULL_SCREEN_KIND));
		HttpUtil.httpConnectionByPostBytes(context, map,bytes,"png",isShowProgress,
				responseListener, errorResponseListener);
	}
	/**上传压缩图片*/
	public static void updatePictureByCompress(Context context,String filepass,String classid,String picPath, ResponseListener responseListener,
			ErrorResponseListener errorResponseListener) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "post/upfile");
		map.put("uploadkey", "e7627f53d4712552f8d82c30267d9bb4");
		map.put("filepass", filepass);
		map.put("classid", classid);
		map.put("open", "1");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		HashMap<String, byte[]> bytes = new HashMap<String, byte[]>();
		bytes.put("file", BitmapUtil.getBytesFromFile(picPath));
		String[] suffix=picPath.split("\\.");
		HttpUtil.httpConnectionByPostBytes(context, map,bytes,suffix[suffix.length - 1],false,
				responseListener, errorResponseListener);
	}
	/**上传压缩图片*/
	public static void updatePictureByCompress2(Context context,String filepass,String classid,String picPath, ResponseListener responseListener,
			ErrorResponseListener errorResponseListener) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "post/upfile");
		map.put("uploadkey", "e7627f53d4712552f8d82c30267d9bb4");
		map.put("filepass", filepass);
		map.put("classid", classid);
		map.put("open", "1");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		HashMap<String, File> file = new HashMap<String, File>();
		file.put("file", BitmapUtil.getFileFromBitmapThumb(context,picPath));
		HttpUtil.httpConnectionByPostFile(context, map,file,false,
				responseListener, errorResponseListener);
	}
	/**上传图片*/
	public static void updatePicture(Context context,String filepass,String classid,String picPath, ResponseListener responseListener,
			ErrorResponseListener errorResponseListener) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "post/upfile");
		map.put("uploadkey", "e7627f53d4712552f8d82c30267d9bb4");
		map.put("filepass", filepass);
		map.put("classid", classid);
		map.put("open", "1");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		HashMap<String, File> files = new HashMap<String, File>();
		files.put("file", new File(picPath));
	HttpUtil.httpConnectionByPostFile(context, map,files,
				responseListener, errorResponseListener);
	}
	/**上传视频*/
	public static void updateVideo(Context context,String filepass,String classid,String videoPath, ResponseListener responseListener,
			ErrorResponseListener errorResponseListener) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "post/upfile");
		map.put("uploadkey", "e7627f53d4712552f8d82c30267d9bb4");
		map.put("filepass", filepass);
		map.put("classid", classid);
		map.put("open", "1");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		HashMap<String, File> files = new HashMap<String, File>();
		files.put("file", new File(videoPath));
		HttpUtil.httpConnectionByPostFile(context, map,files,
				responseListener, errorResponseListener);
	}
	/**获取用户详情*/
	public static void getUserInfo(Context context,String userid,String extra ,ResponseListener responseListener,
			ErrorResponseListener errorResponseListener) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "user/getuserInfo");
		map.put("userid",userid);
		map.put("extra", extra);
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		((BaseActivity) context).	addRequest(HttpUtil.httpConnectionByPost(context, map,
				responseListener, errorResponseListener));
	}
	/**获取知音动态*/
	public static void getFriendsBlogs(Context context,String extra ,int pageSize,int pageIndex,ResponseListener responseListener,
			ErrorResponseListener errorResponseListener) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "zhiyin/list");
		map.put("extra",extra);
		map.put("pageSize", pageSize + "");
		map.put("pageIndex", pageIndex + "");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		((BaseActivity) context).	addRequest(HttpUtil.httpConnectionByPost(context, map,
				responseListener, errorResponseListener));
	}
	/**获取讨论区
	 * @param userid */
	public static void getTLList(Context context,String userid ,String extra, int pageSize,int pageIndex,ResponseListener responseListener,
			ErrorResponseListener errorResponseListener) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "info/tllist");
		map.put("userid",userid);
		map.put("extra",extra);
		map.put("pageSize", pageSize + "");
		map.put("pageIndex", pageIndex + "");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		((BaseActivity) context).	addRequest(HttpUtil.httpConnectionByPost(context, map,
				responseListener, errorResponseListener));
	}
	/** 获取音乐广场数据*/
	public static void getBlogs(Context context,String extra ,String query ,String keyboard ,int pageSize,int pageIndex,ResponseListener responseListener,
			ErrorResponseListener errorResponseListener) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "info/list");
		map.put("pageSize", pageSize + "");
		map.put("pageIndex", pageIndex + "");
		map.put("query", query);
		map.put("keyboard", keyboard);
		map.put("extra",extra);
			map.put("ismember", "1");
			map.put("classid",  ClassId.音乐广场_ID + "");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		((BaseActivity) context).	addRequest(HttpUtil.httpConnectionByPost(context, map,
				responseListener, errorResponseListener));
	}
	/**是否是好友 */
	public static void isFriend(Context context,String userid ,ResponseListener responseListener,
			ErrorResponseListener errorResponseListener) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "user/isfriend");
		map.put("userid", userid);
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		((BaseActivity) context).	addRequest(HttpUtil.httpConnectionByPost(context, map,
				responseListener, errorResponseListener));
	}
	/**获取签约信息*/
	public static void getSignData(Context context,ResponseListener responseListener,
			ErrorResponseListener errorResponseListener) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "qianyue/getData");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		((BaseActivity) context).	addRequest(HttpUtil.httpConnectionByPost(context, map,
				responseListener, errorResponseListener));
	}
	/**获取签约的订单信息*/
	public static void getSignOrderData(Context context,String packageid,ResponseListener responseListener,
			ErrorResponseListener errorResponseListener) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "qianyue/getOrderData");
		map.put("packageid", packageid);
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		((BaseActivity) context).	addRequest(HttpUtil.httpConnectionByPost(context, map,
				responseListener, errorResponseListener));
	}
	/**获取新闻和资讯的点赞数*/
	public static void getLikes(Context context,String id,ResponseListener responseListener,
			ErrorResponseListener errorResponseListener) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "news/getlike");
		map.put("id", id);
		((BaseActivity) context).	addRequest(HttpUtil.httpConnectionByPost(context, map,
				responseListener, errorResponseListener));
	}
	/**
	 * 获取课表的某条的详细详细
	 * @param context
	 * @param id
	 * @param responseListener
	 * @param errorResponseListener
	 */
	public static void getTimeTableDetailDate(Context context,String id,ResponseListener responseListener,
										 ErrorResponseListener errorResponseListener) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "kebiao/getDetail");
		map.put("id", id);//
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		((BaseActivity) context).	addRequest(HttpUtil.httpConnectionByPost(context, map,
				responseListener, errorResponseListener));
	}
	/**
	 * 获取某人课表的某天的数据
	 * @param context
	 * @param date  日期  格式为2016-10-01
	 * @param responseListener
	 * @param errorResponseListener
     */
	public static void getCourseDateList(Context context,String userid,String date,ResponseListener responseListener,
								ErrorResponseListener errorResponseListener) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "kebiao/getDateList");
		map.put("date", date);// 日期  格式为2016-10-01
		map.put("userid", userid);//用户id
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		((BaseActivity) context).	addRequest(HttpUtil.httpConnectionByPost(context, map,
				responseListener, errorResponseListener));
	}
	/**
	 * 获取某人课表的某月的数据
	 * @param context
	 * @param month  月份  格式为2016-10-01 必须带日期
	 * @param responseListener
	 * @param errorResponseListener
     */
	public static void getCourseMonthList(Context context,String userid,String month,ResponseListener responseListener,
								ErrorResponseListener errorResponseListener) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "kebiao/getMonthList");
		map.put("month", month);//月份  格式为2016-10-01 必须带日期
		map.put("userid", userid);//用户id
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		((BaseActivity) context).	addRequest(HttpUtil.httpConnectionByPost(context, map,
				responseListener, errorResponseListener));
	}
	/**
	 * 课程表的发布
     */
	public static void postCourse(Context context,String couname,String location,String classtime,String starttime,String stoptime,String repeat,String stuname,String remarks,ResponseListener responseListener,
								ErrorResponseListener errorResponseListener) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "kebiao/post");
		map.put("couname", couname);//课程名称
		map.put("location", location);//上课地点
		map.put("classtime", classtime);//开始日期  格式2016-11-01
		map.put("starttime", starttime);//开始时间  	格式08:15
		map.put("stoptime", stoptime);//结束时间   	格式08:15
		map.put("repeat", repeat);//重复周期
		map.put("stuname", stuname);//学生姓名
		map.put("remarks", remarks);//备注信息
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());

		((BaseActivity) context).	addRequest(HttpUtil.httpConnectionByPost(context, map,
				responseListener, errorResponseListener));
	}
	/**
	 * 课程表的修改
     */
	public static void editCourse(Context context,String id,String couname,String location,String classtime,String starttime,String stoptime,String stuname,String remarks,ResponseListener responseListener,
								ErrorResponseListener errorResponseListener) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "kebiao/editor");
		map.put("id", id);//id
		map.put("couname", couname);//课程名称
		map.put("location", location);//上课地点
		map.put("classtime", classtime);//开始日期  格式2016-11-01
		map.put("starttime", starttime);//开始时间  	格式08:15
		map.put("stoptime", stoptime);//结束时间   	格式08:15
		map.put("stuname", stuname);//学生姓名
		map.put("remarks", remarks);//备注信息
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		((BaseActivity) context).	addRequest(HttpUtil.httpConnectionByPost(context, map,
				responseListener, errorResponseListener));
	}
	/**
	 * 课程表的删除
     */
	public static void deleteCourse(Context context,String id,ResponseListener responseListener,
								ErrorResponseListener errorResponseListener) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "kebiao/delete");
		map.put("id", id);
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		((BaseActivity) context).addRequest(HttpUtil.httpConnectionByPost(context, map,
				responseListener, errorResponseListener));
	}
	/**
	 * 课程表的删除
     */
	public static void getPackages(Context context,ResponseListener responseListener,
								ErrorResponseListener errorResponseListener) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "qianyue/getPackages");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		((BaseActivity) context).addRequest(HttpUtil.httpConnectionByPost(context, map,
				responseListener, errorResponseListener));
	}
	/**
	 * 检查用户名是否存在
     */
	public static void checkuser(Context context,String name,ResponseListener responseListener,
								ErrorResponseListener errorResponseListener) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "user/checkuser");
		map.put("username", name);
		((BaseActivity) context).addRequest(HttpUtil.httpConnectionByPost(context, map,
				responseListener, errorResponseListener));
	}
	/**
	 * 检查用户名是否存在
     */
	public static void checkphone(Context context,String phone,ResponseListener responseListener,
								ErrorResponseListener errorResponseListener) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "user/checkphone");
		map.put("phone", phone);
		((BaseActivity) context).addRequest(HttpUtil.httpConnectionByPost(context, map,
				responseListener, errorResponseListener));
	}
	/**
	 * 检查用户名是否存在
     */
	public static void checkcode(Context context,String phone,String code,ResponseListener responseListener,
								ErrorResponseListener errorResponseListener) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "user/checkcode");
		map.put("phone", phone);
		map.put("smscode", code);
		((BaseActivity) context).addRequest(HttpUtil.httpConnectionByPost(context, map,
				responseListener, errorResponseListener));
	}
	/**
	 * 获取热修复的信息
     */
	public static void getHotFixData(Context context,String hotFixVersionCode,ResponseListener responseListener,
								ErrorResponseListener errorResponseListener) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "extend/getFixData");
		map.put("hotFixVersionCode", hotFixVersionCode);
		((BaseActivity) context).addRequest(HttpUtil.httpConnectionByPost(context, map,
				responseListener, errorResponseListener));
	}
	/**
	 *邀请
     */
	public static void invite(Context context,String userid,ResponseListener responseListener,
								ErrorResponseListener errorResponseListener) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "tongxunlu/invite");
		map.put("userid", userid);
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		((BaseActivity) context).addRequest(HttpUtil.httpConnectionByPost(context, map,
				responseListener, errorResponseListener));
	}
	/**
	 * 取消邀请
     */
	public static void uninvite(Context context,String userid,ResponseListener responseListener,
								ErrorResponseListener errorResponseListener) {
		HashMap<String, String> map = new HashMap<>();
		map.put("api", "tongxunlu/uninvite");
		map.put("userid", userid);
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		((BaseActivity) context).addRequest(HttpUtil.httpConnectionByPost(context, map,
				responseListener, errorResponseListener));
	}
	/**
	 * 添加关系
     */
	public static void addguanxi(Context context,String userid,int type,ResponseListener responseListener,
								ErrorResponseListener errorResponseListener) {
		HashMap<String, String> map = new HashMap<>();
		map.put("api", "tongxunlu/addguanxi");
		map.put("userid", userid);
		map.put("type", type+"");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		((BaseActivity) context).addRequest(HttpUtil.httpConnectionByPost(context, map,
				responseListener, errorResponseListener));
	}
	/**
	 * 解除关系
     */
	public static void unguanxi(Context context,String userid,ResponseListener responseListener,
								ErrorResponseListener errorResponseListener) {
		HashMap<String, String> map = new HashMap<>();
		map.put("api", "tongxunlu/unguanxi");
		map.put("userid", userid);
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		((BaseActivity) context).addRequest(HttpUtil.httpConnectionByPost(context, map,
				responseListener, errorResponseListener));
	}
	/**
	 * 拒绝添加关系
     */
	public static void rejectguanxi(Context context,String userid,int type,ResponseListener responseListener,
								ErrorResponseListener errorResponseListener) {
		HashMap<String, String> map = new HashMap<>();
		map.put("api", "tongxunlu/rejectguanxi");
		map.put("userid", userid);
		map.put("type", type+"");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		((BaseActivity) context).addRequest(HttpUtil.httpConnectionByPost(context, map,
				responseListener, errorResponseListener));
	}
	/**
	 * 同意添加关系
     */
	public static void agreeadd(Context context,String userid,int type,ResponseListener responseListener,
								ErrorResponseListener errorResponseListener) {
		HashMap<String, String> map = new HashMap<>();
		map.put("api", "tongxunlu/agreeguanxi");
		map.put("userid", userid);
		map.put("type", type+"");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		((BaseActivity) context).addRequest(HttpUtil.httpConnectionByPost(context, map,
				responseListener, errorResponseListener));
	}

}
