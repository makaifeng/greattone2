package com.greattone.greattone.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.view.inputmethod.InputMethodManager;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.dodola.rocoofix.RocooFix;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.data.Constants;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.HotFixResult;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.Vesion;
import com.greattone.greattone.util.HttpUtil.ResponseListener;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ActivityUtil {
//	/**退出登录*/
//	public static void Exit(Context context) {
//		Data.myinfo=new UserInfo();
//		context.startActivity(new Intent(context, LoginActivity.class));
//		finishActivity(Data.activityErrorList);
//	}
	/**
	 * 关闭Activity
	 */
	public static void finishActivity(List<Activity> list ) {
		for (int i = 0; i < list.size(); i++) {
			list.get(i).finish();
		}
		list.clear();
	}
	/**关闭键盘*/
	public static void closeKeyBoard(Context context) {
		InputMethodManager manager = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (((Activity) context).getCurrentFocus() != null
				&& ((Activity) context).getCurrentFocus().getWindowToken() != null) {
			manager.hideSoftInputFromWindow(((Activity) context)
					.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	
//	/***
//	 * 通过包名检测系统中是否安装某个应用程序** @param context* @param
//	 * packageName：应用程序的包名(QB:com.tencent.mtt)* @return true : 系统中已经安装该应用程序* @return
//	 * false : 系统中未安装该应用程序*
//	 */
//	@SuppressWarnings("deprecation")
//	public static boolean checkApkExist(Context context, String packageName) {
//		if (packageName == null || "".equals(packageName)) {
//			return false;
//		}
//		try {
//			context.getPackageManager().getApplicationInfo(packageName,
//					PackageManager.GET_UNINSTALLED_PACKAGES);
//			return true;
//		} catch (NameNotFoundException e) {
//			return false;
//		}
//	}
	/**
	 * 获取版本号
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getVersionName(Context context) {
		// 获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = null;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(),
					PackageManager.GET_PERMISSIONS);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		String version = packInfo.versionName;
		return version;
	}
	/**
	 * 获取版本号
	 *
	 * @return
	 * @throws Exception
	 */
	public static int getVersionCode(Context context) {
		// 获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = null;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(),
					PackageManager.GET_PERMISSIONS);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		int version = packInfo.versionCode;
		return version;
	}

	public static String GetNetworkType(Context context)
	{
	    String strNetworkType = "";
	    
	    try {
			NetworkInfo networkInfo =( (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected())
			{
			    if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
			    {
			        strNetworkType = "WIFI";
			    }
			    else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
			    {
			        String _strSubTypeName = networkInfo.getSubtypeName();
			        
//	            Log.e("cocos2d-x", "Network getSubtypeName : " + _strSubTypeName);
			        
			        // TD-SCDMA   networkType is 17
			        int networkType = networkInfo.getSubtype();
			        switch (networkType) {
			            case TelephonyManager.NETWORK_TYPE_GPRS:
			            case TelephonyManager.NETWORK_TYPE_EDGE:
			            case TelephonyManager.NETWORK_TYPE_CDMA:
			            case TelephonyManager.NETWORK_TYPE_1xRTT:
			            case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
			                strNetworkType = "2G";
			                break;
			            case TelephonyManager.NETWORK_TYPE_UMTS:
			            case TelephonyManager.NETWORK_TYPE_EVDO_0:
			            case TelephonyManager.NETWORK_TYPE_EVDO_A:
			            case TelephonyManager.NETWORK_TYPE_HSDPA:
			            case TelephonyManager.NETWORK_TYPE_HSUPA:
			            case TelephonyManager.NETWORK_TYPE_HSPA:
			            case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
			            case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
			            case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
			                strNetworkType = "3G";
			                break;
			            case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
			                strNetworkType = "4G";
			                break;
			            default:
			                // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
			                if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000"))  {
			                    strNetworkType = "3G";
			                } else{
			                    strNetworkType = _strSubTypeName;
			                }
			                
			                break;
			         }
			         
//	            Log.e("cocos2d-x", "Network getSubtype : " + Integer.valueOf(networkType).toString());
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
//	    Log.e("cocos2d-x", "Network Type : " + strNetworkType);
	    
	    return strNetworkType;
	}
//	/*
//	 * 从服务器中下载APK
//	 */
//	protected void downLoadApk(Context context) {
//		final ProgressDialog pd; // 进度条对话框
//		pd = new ProgressDialog(context);
//		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//		pd.setMessage("正在下载更新");
//		pd.setCancelable(true);// 设置进度条是否可以按退回键取消
//		pd.setCanceledOnTouchOutside(false);
//		pd.show();
//		new Thread() {
//			@Override
//			public void run() {
//				try {
//					// File file =
//					// getFileFromServer("http://app.meiye365.cn/ttm-downloadapp/ttm/app/TianTianMeiSh_V1.0.1.apk",
//					// pd);
//					File file = getFileFromServer(
//							MyApplication.getInstance().parameter
//									.getUpdateURL(),
//							pd);
//					// sleep(3000);
//					installApk(context,file);
//					pd.dismiss(); // 结束掉进度条对话框
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}.start();
//	}
/**
 *
 * @param path
 * @param pd
 * @return
 * @throws Exception
 */
	public static File getFileFromServer(String urlpath,String path, ProgressDialog pd)
			throws Exception {
		// 如果相等的话表示当前的sdcard挂载在手机上并且是可用的
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			URL url = new URL(urlpath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			// 获取到文件的大小
			pd.setMax(conn.getContentLength());
			InputStream is = conn.getInputStream();
			File file = new File(path);
//			File file = new File(Environment.getExternalStorageDirectory()
//					.getPath() + Environment.getDownloadCacheDirectory(),
//					"greattone.apk");
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdir();
			}
			FileOutputStream fos = new FileOutputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);
			byte[] buffer = new byte[1024];
			int len;
			int total = 0;
			while ((len = bis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
				total += len;
				// 获取当前下载量
				pd.setProgress(total);
			}
			fos.close();
			bis.close();
			is.close();
			return file;
		} else {
			return null;
		}

	}

//	// 通过路径安装apk
//	protected void installApk(Context context,File file) {
//		Intent intent = new Intent();
//		// 执行动作
//		intent.setAction(Intent.ACTION_VIEW);
//		// 执行的数据类型
//		intent.setDataAndType(Uri.fromFile(file),
//				"application/vnd.android.package-archive");
//		context.	startActivity(intent);
//	}
//	/**
//	 * 用intent启动拨打电话
//	 *
//	 * @param context
//	 * @param telephone
//	 */
//	public static void CallPhone(Context context, String telephone) {
//		// 用intent启动拨打电话
//		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
//				+ telephone));
//		context.startActivity(intent);
//	}
	
	/**
	 * 是否是虚拟机
	 */
	public static boolean isVirtual(Context context) {
		
		try {
			if ("000000000000000".equals(new PhoneInfo(context).getDeviceID())) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}
	
	
	/** 获取版本号 */
	public  static void getVesion(final BaseActivity activity) {
		MyProgressDialog.show(activity);
		HashMap<String, String> localHashMap = new HashMap<String, String>();
		localHashMap.put("api", "extend/vesion");
//		localHashMap.put("type", "google");
		activity.	addRequest(HttpUtil.httpConnectionByPost(activity, localHashMap,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						MyProgressDialog.Cancel();
						Vesion vesion=JSON.parseObject(message.getData(), Vesion.class);
//						String vesion = JSON.parseObject(message.getData())
//								.getString("vesion");
						examine_version(activity,vesion);
					}

				}, null));
	}
	/**
	 * 版本更新
	 */
	private static void examine_version(final BaseActivity activity,final Vesion vesion) {

		if (ActivityUtil.getVersionName(activity).equals(vesion.getVesion())) {//无版本更新
			if(Constants.hotFixVersionCode.equals(vesion.getFixversion())){//无版本修复
			}else {
				HotFix(activity);
			}
		} else {
			if (getVersionCode(activity)<=vesion.getCode()){
				Builder builer = new Builder(activity);
				builer.setTitle(activity.getResources().getString(R.string.版本升级));
				builer.setMessage(vesion.getDesc());
				builer.setCancelable(false);// 设置进度条是否可以按退回键取消

				// 当点确定按钮时从服务器上下载 新的apk 然后安装 װ

				builer.setPositiveButton(activity.getResources().getString(R.string.确定),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								Uri uri = Uri.parse(vesion.getUrl());
								Intent intent = new Intent(Intent.ACTION_VIEW, uri);
								activity.startActivity(intent);
								// ToMain();
							}
						});
				if (vesion.getActive()!=1) {
				builer.setNegativeButton(activity.getResources().getString(R.string.取消),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
							}
						});
				}
				AlertDialog dialog = builer.create();
				dialog.show();
			}
		}
	}

	/**
	 *热修复
	 */
	private static void HotFix(final BaseActivity activity) {
		final ProgressDialog pd; // 进度条对话框
		pd = new ProgressDialog(activity);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("获取更新信息");
		pd.setCancelable(true);// 设置进度条是否可以按退回键取消
		pd.setCanceledOnTouchOutside(false);
		pd.show();
		HttpProxyUtil.getHotFixData(activity, Constants.hotFixVersionCode, new ResponseListener() {
			@Override
			public void setResponseHandle(Message2 message) {
				if (message.getData()!=null&&message.getData().startsWith("{")){
					final	HotFixResult hotFixResult =JSON.parseObject(message.getData(),HotFixResult.class);
						new Thread() {
							@Override
							public void run() {
								pd.setMessage("下载更新包");
								try {
									String path = Environment.getExternalStorageDirectory()
											.getPath() + Environment.getDownloadCacheDirectory() +
											File.separator + hotFixResult.getPagerName();
									File file = getFileFromServer(hotFixResult.getUrl(), path, pd);
									if (file==null){
										pd.dismiss();
									}else {
									RocooFix.applyPatchRuntime(activity, path);}
								} catch (Exception e) {
									pd.dismiss();
									e.printStackTrace();
								}
							}
						}.start();
				}else {
					pd.dismiss();
				}
			}
		}, new HttpUtil.ErrorResponseListener() {
			@Override
			public void setServerErrorResponseHandle(Message2 message) {
				pd.dismiss();
			}

			@Override
			public void setErrorResponseHandle(VolleyError error) {
				pd.dismiss();
			}
		});
	}

	public static String sHA1(Context context) {
	    try {
	        PackageInfo info = context.getPackageManager().getPackageInfo(
	            context.getPackageName(), PackageManager.GET_SIGNATURES);
	        byte[] cert = info.signatures[0].toByteArray();
	        MessageDigest md = MessageDigest.getInstance("SHA1");
	        byte[] publicKey = md.digest(cert);
	        StringBuffer hexString = new StringBuffer();
	        for (int i = 0; i < publicKey.length; i++) {
	            String appendString = Integer.toHexString(0xFF & publicKey[i])
	                .toUpperCase(Locale.US);
	            if (appendString.length() == 1)
	                hexString.append("0");
	                hexString.append(appendString);
	                hexString.append(":");
	        }
	        String result =hexString.toString();
	        return result.substring(0, result.length()-1);
	    } catch (NameNotFoundException e) {
	        e.printStackTrace();
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
}
