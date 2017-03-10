package com.greattone.greattone;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.greattone.greattone.util.ActivityUtil;
import com.greattone.greattone.util.CityUtil;
import com.greattone.greattone.util.CrashHandler;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.util.UpdateObjectToOSSUtil;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike;

import cn.jpush.android.api.JPushInterface;

//import com.baidu.mapapi.SDKInitializer;

public class MyApplication extends Application {
	private static MyApplication myApplication;
	ApplicationLike tinkerApplicationLike;
@Override
public void onCreate() {
	super.onCreate();
	myApplication=this;
	if (BuildConfig.TINKER_ENABLE) {
		// 我们可以从这里获得Tinker加载过程的信息
		tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();

		// 初始化TinkerPatch SDK, 更多配置可参照API章节中的,初始化SDK
		TinkerPatch.init(tinkerApplicationLike)
				.reflectPatchLibrary()
				.setPatchRollbackOnScreenOff(true)
				.setPatchRestartOnSrceenOff(true);

		// 每隔3个小时去访问后台时候有更新,通过handler实现轮训的效果
		new FetchPatchHandler().fetchPatchWithInterval(3);
	}
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
	
	
	 CrashHandler handler = CrashHandler.getInstance();  //异常捕获
	    handler.init(this);
}

public static MyApplication getInstance() {
	return myApplication;
}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(base);
	}
}
