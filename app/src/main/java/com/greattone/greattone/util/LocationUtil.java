package com.greattone.greattone.util;


import android.content.Context;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
//import com.baidu.location.BDLocationListener;
//import com.baidu.location.LocationClient;
//import com.baidu.location.LocationClientOption;
//import com.baidu.location.LocationClientOption.LocationMode;

public class LocationUtil {
	static LocationUtil location;
	private Context context;
	
	//百度定位
//	private LocationClient mLocClient;
	

	
//	/**
//	 * //加载百度定位
//	 * @param context
//	 * @param bdLocationListener
//	 */
//	public LocationUtil(Context context, BDLocationListener bdLocationListener) {
//		this.context = context;
//		initBaiduLocation(bdLocationListener);
//	}
	/**
	 * //加载高德定位
	 * @param context
	 * @param bdLocationListener
	 */
	public LocationUtil(Context context,AMapLocationListener aMapLocationListener) {
		this.context = context;
		initGaodeLocation(aMapLocationListener);
	}


//	/**
//	 * 开始百度定位
//	 */
//	public static void start(Context context,
//			BDLocationListener bdLocationListener) {
//		location = new LocationUtil(context, bdLocationListener);
//
//	}
	
	/**
	 * 开始高德定位
	 */
	public static void start(Context context,
			AMapLocationListener aMapLocationListener) {
		location = new LocationUtil(context,  aMapLocationListener);
		
	}

	/**
	 * 停止定位
	 */
	public static void stop() {
		if (location != null) {
//			if (location.mLocClient != null) {
//			location.mLocClient.stop();
//			}
			if (location.mlocationClient != null) {
				location.mlocationClient.onDestroy();
				location.mlocationClient = null;
			}
		}
	}
	
	//高德定位
	//声明mLocationOption对象
	public AMapLocationClientOption mLocationOption = null;
	private AMapLocationClient mlocationClient;
	
	/**
	 * 加载高德定位模块
	 */
	private void initGaodeLocation(AMapLocationListener aMapLocationListener) {
		mlocationClient = new AMapLocationClient(context);
		//设置定位监听W
		mlocationClient.setLocationListener(aMapLocationListener);
		//初始化定位参数
		mLocationOption = new AMapLocationClientOption();
		//设置为单次定位
		mLocationOption.setOnceLocation(true);
		//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
		mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
		//设置定位间隔,单位毫秒,默认为2000ms
		mLocationOption.setInterval(2000);
		//设置定位参数
		mlocationClient.setLocationOption(mLocationOption);
		// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
		// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
		// 在定位结束后，在合适的生命周期调用onDestroy()方法
		// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
		//启动定位
		mlocationClient.startLocation();
	}
//	/**
//	 * 加载百度定位模块
//	 * 
//	 * @param bdLocationListener
//	 */
//	private void initBaiduLocation(BDLocationListener bdLocationListener) {
//		// 定位初始化
//		mLocClient = new LocationClient(context);
//		mLocClient.registerLocationListener(bdLocationListener);
//		LocationClientOption option = new LocationClientOption();
//		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
//		option.setOpenGps(true);// 打开gps
//		option.setCoorType("bd09ll"); // 设置坐标类型
//		option.setScanSpan(50 * 1000);// 设置发起定位请求的间隔时间为50s
//		option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
//		mLocClient.setLocOption(option);//
//		mLocClient.start();// 开始定位
//	}

}
