package com.greattone.greattone.data;

public class HttpConstants {

	public String ServerUrl;

	public HttpConstants(boolean isDebug) {
		if (isDebug) {// 判断是Debug模式
			ServerUrl = SERVER_URL_DEBUG;
		} else {// 判断是release模式
			ServerUrl = SERVER_URL_RELEASE;
		}
	}

	public final static String SERVER_URL_DEBUG = "http://www.greattone.net"; // DEBUG下的服务器
	public final static String SERVER_URL_RELEASE = "http://www.greattone.net"; // release下的服务器
	/** 登录地址 */
	public final static String LOGIN_URL = "http://www.greattone.net/app/users/login";// 登录地址
	/** 注册地址 */
	public final static String CREATE_USER_URL = "http://www.greattone.net/app/users/CreateUser";// 注册地址
	/** 首页广告地址 */
	public final static String GETBANNER_URL = "http://www.greattone.net/app/minformation/getbanner"; // 首页广告地址
	/** 老师集合地址 */
	public final static String TEACHER_INDEX_URL = "http://www.greattone.net/app/teacher/index"; // 老师集合地址
	/** 个人中心地址 */
	public final static String PERSONAL_CENTRE_URL = "http://www.greattone.net/app/tarento/personalcentre"; // 个人中心地址
	/** 我的知音地址 */
	public final static String CONTACTS_URL = "http://www.greattone.net/app/centre/contacts"; // 我的知音地址
	/** 我的好友地址 */
	public final static String CONFIDANT_URL = "http://www.greattone.net/app/centre/confidant"; // 我的好友地址
	/** 我的粉丝地址 */
	public final static String CONTACTME_URL = "http://www.greattone.net/app/centre/contactme"; // 我的粉丝地址
	/** 单个老师详情地址 */
	public final static String TEACHER_INFO_URL = "http://www.greattone.net/app/teacher/info"; // 单个老师详情地址
	/** 教室集合地址 */
	public final static String CLASSROOM_INDEX_URL = "http://www.greattone.net/app/classroom/index"; // 教室集合地址
	/** 单个教室详情地址 */
	public final static String CLASSROOM_INFO_URL = "http://www.greattone.net/app/classroom/info"; // 单个教室详情地址
	/** 音乐名人集合地址 */
	public final static String TARENTO_INDEX_URL = "http://www.greattone.net/app/tarento/index"; // 音乐名人集合地址
	/** 老师的推荐视频地址 */
	public final static String TEACHER_VIDEO_URL = "http://www.greattone.net/app/teacher/video"; // 老师的推荐视频地址
	/** 老师的课程中心地址 */
	public final static String TEACHER_COURSE_URL = "http://www.greattone.net/app/teacher/course"; // 老师的课程中心地址
	/** 关注地址 */
	public final static String TARENTO_ADDATTENTION_URL = "http://www.greattone.net/app/tarento/addattention"; // 关注地址
	/** 音乐名人空间地址 */
	public final static String TARENTO_HOME_URL = "http://www.greattone.net/app/tarento/home"; // 音乐名人空间地址
	/** 音乐名人详情地址 */
	public final static String TARENTO_INFO_URL = "http://www.greattone.net/app/tarento/info"; // 音乐名人详情地址
	/** 活动地址 */
	public final static String TARENTO_GET_ACTIVITY_URL = "http://www.greattone.net/app/tarento/getactivity"; // 活动地址
	/** 活动详情地址 */
	public final static String ACTIVITY_EVENTS_DETAIL_URL = "http://www.greattone.net/app/activity/eventsDetail"; // 活动详情地址
	/** 我的消息的通知数地址 */
	public final static String CHAT_NOTICE_URL = "http://www.greattone.net/app/chat/notice"; // 我的消息的通知数地址
	/** 我的消息地址 */
	public final static String CHAT_MESSAGE_URL = "http://www.greattone.net/app/chat/message"; // 我的消息地址
	/** 修改备注地址 */
	public final static String CENTRE_REMARKS_URL = "http://www.greattone.net/app/centre/remarks"; // 修改备注地址
	/** 老师邀请学生地址 */
	public final static String TEACHER_INVITE_URL = "http://www.greattone.net/app/teacher/invite"; // 老师邀请学生地址
	/** 海选列表地址 */
	public final static String COMPETITION_INDEX_URL = "http://www.greattone.net/app/competition/index"; // 海选列表地址
	/** 海选详情地址 */
	public final static String COMPETITION_EVENTSDETAIL_URL = "http://www.greattone.net/app/competition/eventsDetail"; // 海选详情地址
	/** 获取身份地址 */
	public final static String USERS_GETIDENTITY_URL = "http://www.greattone.net/app/users/getIdentity"; //
	/** 获取普通用户信息地址 */
	public final static String INFO_GETUSER_URL = "http://www.greattone.net/app/info/getuser"; // 获取普通用户信息地址
	/** 获取达人用户信息地址 */
	public final static String INFO_GETPLAYERS_URL = "http://www.greattone.net/app/info/getplayers"; // 获取达人用户信息地址
	/** 编辑普通用户信息地址 */
	public final static String INFO_EDIT_USER_URL = "http://www.greattone.net/app/info/edituser"; // 编辑普通用户信息地址
	/** 编辑达人用户信息地址 */
	public final static String INFO_EDIT_PLAYERS_URL = "http://www.greattone.net/app/info/editplayers"; // 编辑达人用户信息地址
	/** 获取乐器信息地址 */
	public final static String USERS_GET_LABEL_URL = "http://www.greattone.net/app/users/getLabel"; // 获取乐器信息地址
	/** 获取老师用户信息地址 */
	public final static String INFO_GET_TEACHER_URL = "http://www.greattone.net/app/info/getteacher"; // 获取老师用户信息地址
	/** 修改安全密码地址 */
	public final static String CENTRE_EIDTUSER_URL = "http://www.greattone.net/app/centre/eidtuser"; // 修改安全密码地址
	/** 获取机构信息地址 */
	public final static String INFO_GET_AGENCY_URL = "http://www.greattone.net/app/info/getagency"; // 获取机构信息地址
	/** 获取音乐广场信息地址 */
	public final static String BLOG_DYNAMIC_URL = "http://www.greattone.net/app/blog/dynamic"; // 获取音乐广场信息地址
	/** 获取音乐广场信息详情地址 */
	public final static String BLOG_DYNAMIC_DETAIL_URL = "http://www.greattone.net/app/blog/dynamicdetail"; // 获取音乐广场信息详情地址
	/** 帖子评论地址 */
	public final static String BLOG_TRANSMIT_URL = "http://www.greattone.net/app/blog/transmit"; // 帖子转发地址
	/** 帖子评论地址 */
	public final static String COMPETITION_COMMENT_URL = "http://www.greattone.net/app/competition/comment"; // 帖子评论地址
	/** 帖子取消收藏地址 */
	public final static String BLOG_UNCOLLECT_URL = "http://www.greattone.net/app/blog/uncollect"; // 帖子取消收藏地址
	/** 帖子收藏地址 */
	public final static String BLOG_COLLECT_URL = "http://www.greattone.net/app/blog/collect"; // 帖子收藏地址
	/** 讨论区首页地址 */
	public final static String FORUM_INDEX_URL = "http://www.greattone.net/app/forum/index"; // 讨论区首页地址
	/** 讨论区地址 */
	public final static String FORUM_BBSLIST_URL = "http://www.greattone.net/app/forum/bbslist"; // 讨论区地址
	/** 获取我的发帖地址 */
	public final static String CENTRE_MESSAGE_URL = "http://www.greattone.net/app/centre/message"; // 获取我的发帖地址
	/** 获取我的收藏地址 */
	public final static String CENTRE_GET_BLOG_URL = "http://www.greattone.net/app/centre/getblog"; // 获取我的收藏地址
	/** 收入记录地址 */
	public final static String CENTRE_STATISTICS_URL = "http://www.greattone.net/app/centre/statistics"; // 收入记录地址
	/** 提现记录地址 */
	public final static String CENTRE_TX_RECORD_URL = "http://www.greattone.net/app/centre/txrecord"; // 提现记录地址
	/** 提现到支付宝地址 */
	public final static String CENTRE_TX_ALIPAY_URL = "http://www.greattone.net/app/centre/txalipay"; // 提现到支付宝地址
	/** 提现到银行卡地址 */
	public final static String CENTRE_TX_BANK_URL = "http://www.greattone.net/app/centre/txbank"; // 提现到银行卡地址
	/** 发送验证码地址 */
	public final static String USERS_SEND_MCODE_URL = "http://www.greattone.net/app/users/sendMcode"; // 发送验证码地址
	/** 我的提问地址 */
	public final static String CENTRE_MY_FAQS_URL = "http://www.greattone.net/app/centre/myFaqs"; // 我的提问地址
	/** 我的回答地址 */
	public final static String CENTRE_OBTAIN_QUIZ_URL = "http://www.greattone.net/app/centre/obtainquiz"; // 我的回答地址
	/** QA列表地址 */
	public final static String CENTRE_QA_ORDER_URL = "http://www.greattone.net/app/centre/qaorder"; // QA列表地址
	/** QA删除地址 */
	public final static String CENTRE_DEL_QA_URL = "http://www.greattone.net/app/centre/delqa"; // QA删除地址
	/** QA订单详情地址 */
	public final static String CENTRE_FAQS_INFO_URL = "http://www.greattone.net/app/centre/faqsinfo"; // QA订单详情地址
	/** 聊天记录地址 */
	public final static String CHAT_FRIEND_URL = "http://www.greattone.net/app/chat/friendChat"; // 
	/** 名人的关注 */
	public final static String TARENTO_FOCUS_LIST_URL = "http://www.greattone.net/app/tarento/focuslist"; // 
	/** 与名人相似的人 */
	public final static String TARENTO_SIMI_LIST_URL = "http://www.greattone.net/app/tarento/similist"; // 
	/** 名人的粉丝 */
	public final static String TARENTO_FANS_LIST_URL = "http://www.greattone.net/app/tarento/fanslist"; // 
	/** 获取专访列表 */
	public final static String INFORMATION_EXCLUSIVE_URL = "http://www.greattone.net/app/minformation/exclusive"; // 
	/** 修改头像 */
	public final static String CENTRE_EDIT_PHOTO_URL = "http://www.greattone.net/app/centre/editphoto"; // 
	/** 我的教室 */
	public final static String CLASSROOM_MY_ROOM_URL = "http://www.greattone.net/app/classroom/myroom"; // 
	/** 老师的教室 */
	public final static String CLASSROOM_TEACHER_ROOM_URL = "http://www.greattone.net/app/classroom/teacherroom"; // 
	/** 我的学生 */
	public final static String CLASSROOM_STUDENT_LIST_URL = "http://www.greattone.net/app/classroom/studentlist"; // 
	/** 我的老师 */
	public final static String CLASSROOM_ALL_TEACHERS_URL = "http://www.greattone.net/app/classroom/allteachers"; // 
	/**  */
	public final static String CLASSROOM_ADD_TEACHER_URL = "http://www.greattone.net/app/classroom/addteacher"; // 
	/**  */
	public final static String CLASSROOM_ALL_TEACHER_URL = "http://www.greattone.net/app/classroom/allteacher"; // 
	/**  */
	public final static String CLASSROOM_JIARU_URL = "http://www.greattone.net/app/classroom/jiaru"; // 
	/**  我的活动*/
	public final static String COMPETITION_MY_ACTIVITY_URL = "http://www.greattone.net/app/competition/myActivity"; // 
	/**  我发布的活动*/
	public final static String ACTIVITY_MY_ACTIVITY_URL = "http://www.greattone.net/app/activity/myactivity"; // 
	/**  全部视频*/
	public final static String CENTRE_VIDEO_URL = "http://www.greattone.net/app/centre/video"; // 
	/**  我的推荐视频*/
	public final static String CENTRE_RECVIDEO_URL = "http://www.greattone.net/app/centre/recvideo"; // 
	/**  取消推荐视频*/
	public final static String CENTRE_DELVIDEO_URL = "http://www.greattone.net/app/centre/delvideo"; // 
	/**  推荐视频*/
	public final static String CENTRE_ISRECVIDEO_URL = "http://www.greattone.net/app/centre/isrecvideo"; // 
	/**  我的课程*/
	public final static String CENTRE_MANAGE_URL = "http://www.greattone.net/app/centre/manage"; // 
	/**  课程详情*/
	public final static String CLASSROOM_COURSE_INFO_URL = "http://www.greattone.net/app/classroom/courseinfo"; // 
	/**  删除课程*/
	public final static String CENTRE_DEL_COURSES_URL = "http://www.greattone.net/app/centre/delcourses"; // 
	/**  我的课程详情*/
	public final static String CENTRE_COURSE_INFO_URL = "http://www.greattone.net/app/centre/courseinfo"; // 
	/**  我的租赁的教室*/
	public final static String CENTRE_GET_ROOM_URL = "http://www.greattone.net/app/centre/getroom"; // 
	/**  删除我的租赁的教室*/
	public final static String CENTRE_DEL_ROOMS_URL = "http://www.greattone.net/app/centre/delrooms"; // 
	/**  我的租赁的教室的详情*/
	public final static String CENTRE_ROOM_INFO_URL = "http://www.greattone.net/app/centre/roominfo"; // 
	/**教室邀请de学生*/
	public final static String CLASSROOM_INVITE_STUDENT_URL = "http://www.greattone.net/app/classroom/invitestudent"; // 
	/**老师邀请de学生*/
	public final static String TEACHER_INVITE_STUDENT_URL = "http://www.greattone.net/app/teacher/invitestudent"; // 
	/**教室的等待de学生*/
	public final static String CLASSROOM_DENG_STUDENT_URL = "http://www.greattone.net/app/classroom/dengstudent"; // 
	/**老师的等待学生*/
	public final static String TEACHER_DENG_STUDENT_URL = "http://www.greattone.net/app/teacher/dengstudent"; // 
	/**我的学生*/
	public final static String CENTRE_STUDENT_URL = "http://www.greattone.net/app/centre/student"; // 
	/**删除我的学生*/
	public final static String CENTRE_DEL_STUDENT_URL = "http://www.greattone.net/app/centre/delstdent"; // 
	/**申请学生*/
	public final static String CENTRE_SCONTROL_URL = "http://www.greattone.net/app/centre/scontrol"; // 
	/**推荐学生*/
	public final static String CENTRE_REC_STUDENT_URL = "http://www.greattone.net/app/centre/recstuden"; // 
	/**老师取消邀请学生*/
	public final static String TEACHER_CANCEL_INVITE_URL = "http://www.greattone.net/app/teacher/cancelinvite"; // 
	/**教室取消邀请学生*/
	public final static String CLASSROOM_CANCEL_INVITE_URL = "http://www.greattone.net/app/classroom/cancelinvite"; // 
	/**教室邀请学生*/
	public final static String CLASSROOM_INVITE_URL = "http://www.greattone.net/app/classroom/invite"; // 
	/**教师列表*/
	public final static String CENTRE_TEACHER_URL = "http://www.greattone.net/app/centre/teacher"; // 
	/**新增老师*/
	public final static String CLASSROOM_INVITE_TEACHER_URL = "http://www.greattone.net/app/classroom/inviteteacher"; // 
	/**等待de老师*/
	public final static String CLASSROOM_DENG_TEACHER_URL = "http://www.greattone.net/app/classroom/dengteacher"; // 

}
