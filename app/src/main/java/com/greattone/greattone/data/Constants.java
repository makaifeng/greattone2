package com.greattone.greattone.data;



public class Constants  {
	public static boolean isDebug =true; // 判断是否是Debug模式 true是debug
	public final static String PICTURE_NAME = "icon.png";// 图片名
	//好琴声3.0
	public final static String WX_APPID = "wx383932e506d270a1";//微信
	public final static String WX_APPSECRET = "36147743f899a693aca730ff79b1e79c";//微信AppSecret
	//好琴声
	public final static String WX_APPID2= "wx1ca72dd2170d5611";//微信AppID
	public final static String WX_APPSECRET2 = "779b1efc5db36aaecb8e884756fcad64";//微信AppSecret

	//修复版本
	public static final String hotFixVersionCode="1";
	public final static int MAXPAGE =9; // 发送图片最大张数
	// 用于swith判断
		public final static int CONNECTIONS_SUCCESSFUL = 0; // 网络请求连接成功
		public final static int CONNECTIONS_FAILED = 1; // 网络请求连接失败
//		public final static int PHOTOGRAPH = 2; // 请求系统拍照功能
//		public final static int ALBUM = 3; // 请求系统相册功能
		public final static int CUSTOM_ALBUM = 5; //自定义相册
		public final static int TIMER = 101; // 用于计时
		public final static int TIMER_STOP = 102; // 用于计时停止
		// 其他
		public final static String PREFERENCES_NAME_USER = "greattone_user_sp";
		public final static String NO_CONNECTION_ERROR = "com.android.volley.NoConnectionError: java.io.EOFException";
		

		/**
		 * 发送广播的参数
		 */
		public  static class  Broadcast{
			public static final String CURRENT_CITY_OK="current_city_ok";
			public static final String CURRENT_CITY_FAIL="current_city_fail";
		}
		
		/**
		 * 已选择的图片路径
		 */
		public static final String EXTRA_SELECTED_PICTURES = "com.ns.mutiphotochoser.extra.SELECTED_PICTURES";
	    /**
	     * 要选择的图片数量key
	     */
	    public static final String EXTRA_PHOTO_LIMIT = "com.ns.mutiphotochoser.extra.PHOTO_LIMIT";

	    /**
	     * 选择返回结果保存key
	     */
	    public static final String EXTRA_PHOTO_PATHS = "com.ns.mutiphotochoser.extra.PHOTO_PATHS";
}
