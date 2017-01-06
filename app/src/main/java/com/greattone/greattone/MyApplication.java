package com.greattone.greattone;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.dodola.rocoofix.RocooFix;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.greattone.greattone.util.ActivityUtil;
import com.greattone.greattone.util.CityUtil;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.util.UpdateObjectToOSSUtil;

import cn.jpush.android.api.JPushInterface;

//import com.baidu.mapapi.SDKInitializer;

public class MyApplication extends Application {
	private static MyApplication myApplication;
@Override
public void onCreate() {
	super.onCreate();
	myApplication=this;
	if (!ActivityUtil.isVirtual(this)) {
	ImageLoaderUtil.getInstance().initImageLoader(this);//初始化 图片加载
	CityUtil.initCity(this);//初始化 城市数据
    JPushInterface.init(this);     		// 初始化 JPush
    JPushInterface.setDebugMode(false); 	// 设置开启日志,发布时请关闭日志
		UpdateObjectToOSSUtil.getInstance().init(this);//初始化 图片上传到阿里云
	}
	//  Facebook 初始化
	FacebookSdk.sdkInitialize(getApplicationContext());
	AppEventsLogger.activateApp(this);
	
	
//	 CrashHandler handler = CrashHandler.getInstance();  //异常捕获
//	    handler.init(this); 
}

public static MyApplication getInstance() {
	return myApplication;
}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(base);
		RocooFix.init(this);
	}
}
