package com.greattone.greattone.data;

import com.amap.api.location.AMapLocation;
import com.greattone.greattone.entity.BaseData;
import com.greattone.greattone.entity.Fava;
import com.greattone.greattone.entity.Filter;
import com.greattone.greattone.entity.ImageData;
import com.greattone.greattone.entity.LoginInfo;
import com.greattone.greattone.entity.Model;
import com.greattone.greattone.entity.UserInfo;
import com.greattone.greattone.entity.Yuepu_type;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Data {
	public static LoginInfo user = new LoginInfo();
	public static UserInfo myinfo= new UserInfo();
	public static List<Model> post_model = new ArrayList<Model>();
	public static Filter filter_star = new Filter();
	public static Filter filter_putong = new Filter();
	public static Filter filter_teacher = new Filter();
	public static Filter filter_classroom = new Filter();
	public static Filter filter_pinpai = new Filter();
	public static Yuepu_type filter_yuepu = new Yuepu_type();

	public static List<Fava> favaList = new ArrayList<Fava>();
	public static AMapLocation myLocation = new AMapLocation("0");
//	public static BDLocation myLocation = new BDLocation();
	public static BaseData userData=new BaseData();
	public static List<ImageData> bannerList = new ArrayList<ImageData>();

	// /**
	// * 是否是虚拟机
	// */
	// public static boolean isVirtual(Context context) {
	// if ("000000000000000".equals(new PhoneInfo(context).getDeviceID())) {
	// return true;
	// }
	// return false;
	// }

	// /**
	// * 隐藏手机号中间4位
	// */
	// public static String changePhone(String phone) {
	// String phoneName = "";
	// if (phone != null) {
	// phoneName = phone.substring(0, 3) + "****" + phone.substring(7, 11);
	// }
	// return phoneName;
	// }

	// }
	// /**
	// * 距离转换
	// *
	// * @param d
	// */
	// public static String changeDistance(double distance) {
	// if (distance < 1000) {
	// String s[] = String.valueOf(distance).split("\\.");
	// return s[0] + "m";
	// } else {
	// double d = distance / 1000;
	// String s[] = String.valueOf(d).split("\\.");
	// if (s[1].substring(0, 1).equals("0")) {
	// return s[0] + "km";
	// } else {
	// return s[0] + "." + s[1].substring(0, 1) + "km";
	// }
	// }
	//
	// }

	// /**
	// * 用intent启动拨打电话
	// *
	// * @param context
	// * @param telephone
	// */
	// public static void CallPhone(Context context, String telephone) {
	// // 用intent启动拨打电话
	// Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
	// + telephone));
	// context.startActivity(intent);
	// }

	// /**
	// * 跳转到Distance
	// *
	// * @param latitude
	// * @param logitude
	// * @return
	// */
	// public static void toDistance(Context context, String nickName,
	// double latitude, double longitude, String portraitPath,
	// float count, String shopName, String shopAddress) {
	// if (!Data.isVirtual(context)) {
	// Intent intent = new Intent(context, DistanceActivity.class);
	// intent.putExtra("artificerLatitude", latitude);
	// intent.putExtra("artificerLongitude", longitude);
	// intent.putExtra("artificerName", nickName);
	// intent.putExtra("portraitPath", portraitPath);
	// intent.putExtra("count", count);
	// intent.putExtra("shopName", shopName);
	// intent.putExtra("shopAddress", shopAddress);
	// context.startActivity(intent);
	// }
	// }

	// /**
	// * 判断输入的数据是否为空
	// *
	// * @param text1
	// * edittext内的数据
	// * @param text2
	// * edittext的名称
	// * @return
	// */
	// public static boolean textIsEmpty(Context context, String text1[],
	// String text2[]) {
	//
	// // 判断姓名是否为空
	// for (int i = 0; i < text1.length; i++) {
	// if (TextUtils.isEmpty(text1[i])) {
	// int a = i;
	// if (i >= text2.length) {
	// a = text2.length - 1;
	// }
	// ((BaseActivity) context).toast(text2[a] + "不能为空！");
	// return true;
	// }
	// }
	// return false;
	// }

	// /**
	// * 判断是否手机号码是否可用
	// *
	// * @param phoneNumber
	// * @return
	// */
	// public static boolean phoneCanUse(Context context, String phoneNumber) {
	// if (phoneNumber.length() != 11) {
	// ((BaseActivity) context).toast("请输入正确的手机号码！");
	// return false;
	// }
	// String s = phoneNumber.substring(0, 2);
	// if (s.equals("13") || s.equals("15") || s.equals("18")
	// || s.equals("25")) {
	// return true;
	// }
	// ((BaseActivity) context).toast("请输入正确的手机号码！");
	// return false;
	// }

	// /**
	// *
	// * @param picturePath
	// * @param imageView
	// * @param options
	// */
	// public static boolean IsLogin(Context context) {
	// if (MyApplication.getInstance().phoneName == "") {
	// Intent intent = new Intent(context, RegisterActivity.class);
	// ((BaseActivity) context).startActivityForResult(intent,
	// Activity.RESULT_OK);
	// return false;
	// }
	// return true;
	//
	// }

	// /***
	// * 通过包名检测系统中是否安装某个应用程序** @param context* @param
	// * packageName：应用程序的包名(QB:com.tencent.mtt)* @return true : 系统中已经安装该应用程序*
	// @return
	// * false : 系统中未安装该应用程序*
	// */
	// public static boolean checkApkExist(Context context, String packageName)
	// {
	// if (packageName == null || "".equals(packageName)) {
	// return false;
	// }
	// try {
	// context.getPackageManager().getApplicationInfo(packageName,
	// PackageManager.GET_UNINSTALLED_PACKAGES);
	// return true;
	// } catch (NameNotFoundException e) {
	// return false;
	// }
	// }
	//
	// /**
	// * 获取版本号
	// *
	// * @return
	// * @throws Exception
	// */
	// public static String getVersionName(Context context) {
	// // 获取packagemanager的实例
	// PackageManager packageManager = context.getPackageManager();
	// // getPackageName()是你当前类的包名，0代表是获取版本信息
	// PackageInfo packInfo = null;
	// try {
	// packInfo = packageManager.getPackageInfo(context.getPackageName(),
	// PackageManager.GET_PERMISSIONS);
	// } catch (NameNotFoundException e) {
	// e.printStackTrace();
	// }
	// String version = packInfo.versionName;
	// return version;
	// }
	//
	// public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
	// int w = bitmap.getWidth();
	// int h = bitmap.getHeight();
	// Matrix matrix = new Matrix();
	// float scaleWidth = ((float) width / w);
	// float scaleHeight = ((float) height / h);
	// matrix.postScale(scaleWidth, scaleHeight);
	// Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
	// return newbmp;
	// }

	// /**
	// * 拍照
	// */
	// public static void setPhotograph(Context context, String file) {
	// Intent intent = new Intent();
	// // 指定开启系统相机的Action
	// intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
	// intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
	// ((Activity) context).startActivityForResult(intent,
	// Constants.PHOTOGRAPH);
	// }
	// public static void closeKeyBoard(Context context) {
	// InputMethodManager manager = (InputMethodManager) context
	// .getSystemService(Context.INPUT_METHOD_SERVICE);
	// if (((Activity) context).getCurrentFocus() != null
	// && ((Activity) context).getCurrentFocus().getWindowToken() != null) {
	// manager.hideSoftInputFromWindow(((Activity) context)
	// .getCurrentFocus().getWindowToken(),
	// InputMethodManager.HIDE_NOT_ALWAYS);
	// }
	// }

	// /**
	// * 获取网络图片地址
	// *
	// */
	// public static String getNetworkImageUri(String imageAdress) {
	// if
	// (null==imageAdress||null==MyApplication.getInstance().parameter.getImageServer())
	// {
	// return "";
	// }
	// return "http://" +
	// MyApplication.getInstance().parameter.getImageServer()+ imageAdress;
	// }

	// /**
	// * 加入的volley网络请求的队列中
	// *
	// * @param request
	// */
	// public static void addRequest(Context context, RequestQueue queue,
	// Request<?> request) {
	// request.setTag(context);
	// queue.add(request);
	// }

	//
	// /**
	// * 获取开始时间
	// *
	// * @param date
	// * @return
	// */
	// public static String getStartDate(Date date) {
	// long s = date.getTime();
	// return changeTimeformatForService(s);
	// }

	// /**
	// * 获取结束时间
	// *
	// * @param date
	// * @return
	// */
	// public static String getEndDate(Date date) {
	// Calendar c = Calendar.getInstance();
	// c.setTime(date);
	// c.add(Calendar.DAY_OF_MONTH, 6);
	// Date d = c.getTime();
	// long s = d.getTime();
	// return changeTimeformatForService(s);
	// }

	// /**
	// * 获取两个时间的相差天数
	// */
	// public static double getDateDistance(long startTime, long endTime) {
	// long date1 = changeTimeToDawn(startTime);
	// long date2 = changeTimeToDawn(endTime);
	// double dayCount = (date2 - date1) / (1000 * 3600 * 24);// 从间隔毫秒变成间隔天数
	// return dayCount;
	// }

	/**
	 * 转换成当前时间的0点
	 */
	public static long changeTimeToDawn(long startTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(startTime);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.SECOND, 1);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	// /**
	// * 改变时间的格式 1555555--------->"/Date(1555555+0800)/"
	// *
	// * @return
	// */
	// public static String changeTimeformatForService(Object time) {
	// return "/Date(" + time.toString() + "+0800)/";
	// }

	// /**
	// * 改变时间的格式 "/Date(1555555+0800)/"--------->1555555
	// *
	// * @return
	// */
	// public static String changeTimeformatFromService(String time) {
	// int start = time.indexOf("(");
	// int end = time.indexOf("+");
	// String str = null;
	// if (start != -1 || end != -1) {
	// str = time.substring(start + 1, end);
	// } else {
	// str = time;
	// }
	// return str;
	// }

	// /**
	// * 获取当前选择是哪天
	// *
	// * @return
	// */
	// public static long getDateTime(int adddate) {
	// Calendar calendar = Calendar.getInstance();
	// calendar.add(Calendar.DAY_OF_MONTH, adddate);
	// return calendar.getTimeInMillis();
	// }

	// /**
	// * 打印信息
	// *
	// * @param msg
	// * 打印的信息
	// */
	// public static void println(String msg) {
	// if (Constants.isDebug) {
	// System.out.println(msg);
	// }
	// }
	// /**
	// * Toast信息
	// *
	// * @param msg
	// * Toast的信息
	// */
	// public static void Toast(Context context,String msg) {
	// if (Constants.isDebug) {
	// ((BaseActivity) context).toast(msg);
	// }
	// }

	/**
	 * 获取星期
	 * 
	 * @param milliseconds
	 */
	public static String getWeekData(long milliseconds) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(milliseconds);
		int weekData = c.get(Calendar.DAY_OF_WEEK);// 表示今天的i天后是星期几
		if (Calendar.MONDAY == weekData) {
			return "周一";
		}
		if (Calendar.TUESDAY == weekData) {
			return "周二";
		}
		if (Calendar.WEDNESDAY == weekData) {
			return "周三";
		}
		if (Calendar.THURSDAY == weekData) {
			return "周四";
		}
		if (Calendar.FRIDAY == weekData) {
			return "周五";
		}
		if (Calendar.SATURDAY == weekData) {
			return "周六";
		}
		if (Calendar.SUNDAY == weekData) {
			return "周日";
		}

		return "周一";

	}

	/**
	 * 获取星期
	 * 
	 * @param time
	 */
	public static String getWeekData(String time) {
		long milliseconds = Long.valueOf(time);
		return getWeekData(milliseconds);
	}

	/**
	 * 改变价格的格式
	 * 
	 * * @param price 转换的价格
	 * 
	 * @param decimalFormat
	 *            转换之后的价格的格式 如12.0是12.000
	 */
	public static String changePrice(double price, String decimalFormat) {
		// 构造方法的字符格式这里如果小数不足2位,会以0补足
		DecimalFormat format = new DecimalFormat(decimalFormat);
		// format 返回的是字符串
		String p = format.format(price);
		return p;
	}

	/**
	 * 将String的格式转换为Utf-8
	 * 
	 * @param s
	 * @return
	 */
	public static String toStringForUtf8(String s) {
		String i = null;
		try {
			i = new String(s.getBytes(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * 过滤价格，去掉小数点后面多余的0
	 */
	public static String fiterPrice(Object price) {
		String mPrice = String.valueOf(price);
		if (mPrice.startsWith("-")) {
			return "0";
		}
		String s[] = mPrice.split("\\.");
		if (s.length == 1) {
			return mPrice;
		}
		char c[] = s[1].toCharArray();
		int size = c.length;
		for (int i = c.length - 1; i >= 0; i--) {
			if (c[i] == '0') {
				size--;
			} else {
				break;
			}
		}
		if (size == 0) {
			mPrice = mPrice.substring(0, s[0].length());
		} else {
			mPrice = mPrice.substring(0, s[0].length() + 1 + size);
		}
		return mPrice;
	}

	/**
	 * 过滤距离，去掉小数点后面多余的0
	 */
	public static String fiterDistance(Object distance) {
		return fiterPrice(distance);
	}
}
