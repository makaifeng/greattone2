package com.greattone.greattone.util;

import android.app.Activity;
import android.content.Context;

import com.greattone.greattone.data.Constants;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.Log;

/**
 * 分享
 *
 */
public class ShareUtil {
	static shareCallback callback=new shareCallback() {
		
		@Override
		public void shareOk() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void shareError() {
			// TODO Auto-generated method stub
			
		}
	};
	static Context context;
	String title;
	String imagePath;
	String content;
	String targetUrl;
	static	ShareUtil shareUtil=new ShareUtil();
	public ShareUtil() {
		initShareData();
	}
	public static void open(Context context, String title, String imagePath,
			String content, String targetUrl,UMShareListener umShareListener) {
		ShareUtil.context=context;
		final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[] {
				SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ,SHARE_MEDIA.FACEBOOK };
		new ShareAction((Activity) context).setDisplayList(displaylist)
				.withText(content).withTitle(title).withTargetUrl(targetUrl)
				.withMedia(new UMImage(context, imagePath))
				.setListenerList(umShareListener).open();

	}
	public static void initShareData() {
		// 微信 appid appsecret
		PlatformConfig.setWeixin(Constants.WX_APPID2,
				Constants.WX_APPSECRET2);
//		PlatformConfig.setWeixin("wx1ca72dd2170d5611",
//				"36147743f899a693aca730ff79b1e79c");
		// QQ和Qzone appid appkey
		PlatformConfig.setQQZone("1105050699", "ZCL3BsIAfo4lPhWw");
		Log.LOG=false;
	}
	
	

	public interface shareCallback {
		/**
		 * 分享成功
		 * 
		 * @param v
		 * @param position
		 */
		public void shareOk();

		/**
		 * 分享成功
		 * 
		 * @param v
		 * @param position
		 */
		public void shareError();
	}
}
