package com.greattone.greattone.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer.Result;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.greattone.greattone.R;
import com.greattone.greattone.Listener.ActivityBackListener;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.util.ShareUtil;
import com.greattone.greattone.util.ShareUtil.shareCallback;
import com.umeng.analytics.social.UMSocialService;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * 分享
 *
 */
public class SharePopWindow {
	static SharePopWindow sharePopWindow;
	private PopupWindow mPopupWindow;
	private Context context;
//	private TextView tv_share_to_weixin;
//	private TextView tv_share_to_weixin_pyq;
//	private TextView tv_share_to_qq;
//	private TextView tv_share_to_sina;
	private TextView tv_share_cancel;
	shareCallback callback;
	UMSocialService mController;
	String title;
	String imagePath;
	String content;
	String targetUrl;
	View popupView;
	// private String sharePlatform=SHARE_PLATFORM_ALL;
	public static String SHARE_PLATFORM_ALL = "0";
	public static String SHARE_PLATFORM_WEIXIN = "1";
	public static String SHARE_PLATFORM_WEIXIN_PYQ = "2";
	public static String SHARE_PLATFORM_QQ = "3";
	public static String SHARE_PLATFORM_SINA = "4";
	
//	cn.sharesdk.framework.Platform.ShareParams sp;
	CallbackManager callbackManager;
boolean isFaceBookShare;
private GridView gv_share;
	public SharePopWindow(Context context) {
		init(context);
	}

	public static SharePopWindow build(Context context) {
		sharePopWindow = new SharePopWindow(context);
		sharePopWindow.context = context;
		sharePopWindow.callback = new shareCallback() {

			@Override
			public void shareOk() {
				
			}

			@Override
			public void shareError() {

			}
		};
		return sharePopWindow;
	}

	public static SharePopWindow build(Context context, shareCallback callback) {
		sharePopWindow = new SharePopWindow(context);
		sharePopWindow.context = context;
		sharePopWindow.callback = callback;
		return sharePopWindow;
	}

	public SharePopWindow setTitle(String title) {
		sharePopWindow.title = TextUtils.isEmpty(title)?"好琴声":title;
		return sharePopWindow;
	}

	public SharePopWindow setIconPath(String iconPath) {
		sharePopWindow.imagePath = iconPath;
		return sharePopWindow;
	}

	public SharePopWindow setContent(String content) {
		sharePopWindow.content = TextUtils.isEmpty(content)?"我正在使用好琴声,赶快来吧!!!":content;
		return sharePopWindow;
	}

//	public SharePopWindow setSharePlatform(String... sharePlatform) {
//		// 微信好友
//		tv_share_to_weixin.setVisibility(View.GONE);
//		// 微信朋友圈
//		tv_share_to_weixin_pyq.setVisibility(View.GONE);
//		// QQ好友
//		tv_share_to_qq.setVisibility(View.GONE);
//		// 新浪微博
//		tv_share_to_sina.setVisibility(View.GONE);
//		// sharePopWindow.sharePlatform = sharePlatform;
//	List<String> list=new ArrayList<String>();
//	for (String string : sharePlatform) {
//		list.add(string);
//	}
//		if (list.contains(SHARE_PLATFORM_QQ)) {
//			tv_share_to_qq.setVisibility(View.VISIBLE);
//		}
//		if (list.contains(SHARE_PLATFORM_WEIXIN)) {
//			tv_share_to_weixin.setVisibility(View.VISIBLE);
//		}
//		if (list.contains(SHARE_PLATFORM_WEIXIN_PYQ)) {
//			tv_share_to_weixin_pyq.setVisibility(View.VISIBLE);
//		}
//		if (list.contains(SHARE_PLATFORM_SINA)) {
//			tv_share_to_sina.setVisibility(View.VISIBLE);
//		}
//		return sharePopWindow;
//	}

	public SharePopWindow setTOargetUrl(String targetUrl) {
		sharePopWindow.targetUrl = targetUrl;
		return sharePopWindow;
	}



	@SuppressLint("InflateParams")
	void init(Context context) {
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		popupView = layoutInflater.inflate(R.layout.popwindow_share, null);

		mPopupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		mPopupWindow.setTouchable(true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable(context
				.getResources(), (Bitmap) null));

		mPopupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				backgroundAlpha(1f);
			}
		});
		gv_share = (GridView) popupView
				.findViewById(R.id.gv_share);
		gv_share.setAdapter(new MyAdapter());
		gv_share.setOnItemClickListener(itemClickListener);
//		// 分享到微信好友
//		tv_share_to_weixin = (TextView) popupView
//				.findViewById(R.id.tv_share_to_weixin);
//		tv_share_to_weixin.setOnClickListener(lis);
//		// 分享到微信朋友圈
//		tv_share_to_weixin_pyq = (TextView) popupView
//				.findViewById(R.id.tv_share_to_weixin_pyq);
//		tv_share_to_weixin_pyq.setOnClickListener(lis);
//		// 分享到QQ好友
//		tv_share_to_qq = (TextView) popupView.findViewById(R.id.tv_share_to_qq);
//		tv_share_to_qq.setOnClickListener(lis);
//		// 分享到新浪微博
//		tv_share_to_sina = (TextView) popupView
//				.findViewById(R.id.tv_share_to_sina);
//		tv_share_to_sina.setOnClickListener(lis);
		// 取消分享
		tv_share_cancel = (TextView) popupView
				.findViewById(R.id.tv_share_cancel);
		tv_share_cancel.setOnClickListener(lis);

	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
//			case R.id.tv_share_to_weixin:
//				WeiXinShareContent();
//				break;
//			case R.id.tv_share_to_weixin_pyq:
//				FacebookContent();
////				CircleShareContent();
//				break;
//			case R.id.tv_share_to_qq:
//			QQShareContent();
//				break;
//			case R.id.tv_share_to_sina:
//				setShareData();
//				startShare(mController, SHARE_MEDIA.SINA);
//				break;
			case R.id.tv_share_cancel:
				if (callback!=null) 	callback.shareError();
				break;

			default:
				break;
			}
			cancel();
		}
	};
	OnItemClickListener itemClickListener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			try {
				switch (position) {
                case 0:
                WeiXinShareContent();
                break;
            case 1:
                CircleShareContent();
                break;
            case 2:
                QQShareContent();
                break;
            case 3:
                try {
                    FacebookContent();
                } catch (Exception e) {
                    Toast.makeText(context,"分享失败",Toast.LENGTH_LONG).show();
                }
                break;

                default:
                    break;
                }
			} catch (Exception e) {
				e.printStackTrace();
			}
			cancel();
		}
	};
	/*
	 * 显示界面
	 */
	public void show() {
		mPopupWindow.showAtLocation(popupView, Gravity.BOTTOM, 0, 0);
		backgroundAlpha(0.5f);
		ShareUtil.initShareData();
		isFaceBookShare=false;
		((BaseActivity)context).setActivityBack(activityBackListener);
	}


	ActivityBackListener activityBackListener=new ActivityBackListener() {
		
		@Override
		public void activityBack(int requestCode, int resultCode, Intent data) {
			if (!	isFaceBookShare) {
				UMShareAPI.get( context ).onActivityResult( requestCode, resultCode, data);
			}else {
				 callbackManager.onActivityResult(requestCode, resultCode, data);
			}
		}
	};

	/**
	 * 设置Facebook分享内容
	 */
	protected void FacebookContent() {
		isFaceBookShare=true;
		 callbackManager = CallbackManager.Factory.create();
		ShareDialog		shareDialog=new ShareDialog((BaseActivity)context);
		
		shareDialog.registerCallback(callbackManager, facebookCallback);
		if (ShareDialog.canShow(ShareLinkContent.class)) {
			ShareLinkContent linkContent = new ShareLinkContent.Builder()
	        .setContentUrl(Uri.parse(targetUrl))
			.setContentTitle(title)
			.setContentDescription(content)
			.setImageUrl(Uri.parse(imagePath))
	        .build();
		    shareDialog.show(linkContent);
		}
//		new ShareAction((BaseActivity) context)
//		.setPlatform(SHARE_MEDIA.FACEBOOK)
//		.setCallback(umShareListener)
//		.withText(content)
//		.withTitle(title)
//		.withTargetUrl(targetUrl)
//		.withMedia(new UMImage(context, imagePath))
//		.share();
	}
	/**
	 * 设置QQ分享内容
	 */
	protected void QQShareContent() {
		new ShareAction((Activity) context)
		.setPlatform(SHARE_MEDIA.QQ)
		.setCallback(umShareListener)
		.withText(content)
		.withTitle(title)
		.withTargetUrl(targetUrl)
		.withMedia(new UMImage(context, imagePath))
		.share();
	}
	/**
	 * 设置微信分享内容
	 */
	private void WeiXinShareContent() {
		new ShareAction((Activity) context)
		.setPlatform(SHARE_MEDIA.WEIXIN)
		.setCallback(umShareListener)
		.withText(content)
		.withTitle(title)
		.withTargetUrl(targetUrl)
		.withMedia(new UMImage(context, imagePath))
		.share();
//		 cn.sharesdk.wechat.friends.Wechat.ShareParams sp = new Wechat.ShareParams();
//		 sp.title=title;
//		 sp.text=content;
//		 sp.imageUrl=imagePath;
//		 sp.url=targetUrl;
//		 sp.shareType=Platform.SHARE_WEBPAGE;
//		sp.setTitle(title);
//		sp.setText(content);
//		sp.setImageUrl(imagePath);
////		sp.setImagePath(imagePath);
////		sp.setImageData(imageData);
//		sp.setUrl(targetUrl);
//		sp.setShareType(Platform.SHARE_WEBPAGE);
//		
//		Platform wx = ShareSDK.getPlatform (Wechat.NAME);
//		wx. setPlatformActionListener (paListener); // 设置分享事件回调
//		// 执行图文分享
//		wx.share(sp);
//		MyProgressDialog.show(context);
//		api=WXAPIFactory.createWXAPI(context, "wx383932e506d270a1", true);
//		api.registerApp("wx383932e506d270a1");
//		ImageLoaderUtil.getInstance().loadImage(imagePath, new SimpleImageLoadingListener(){
//			 public void onLoadingComplete(String imageUri, android.view.View view, android.graphics.Bitmap loadedImage) {
//				 MyProgressDialog.Cancel();
//				 WXImageObject imageObject=new WXImageObject(loadedImage);
//				 WXMediaMessage message=new WXMediaMessage();
//				 message.mediaObject=imageObject;
//				 Bitmap thumbBmp=Bitmap.createScaledBitmap(loadedImage, 200, 200, true);
//				 loadedImage.recycle();
//				 message.thumbData=com.greattone.greattone.wxpay.Util.bmpToByteArray(thumbBmp, true);
//				 
//				 SendMessageToWX.Req req=new SendMessageToWX.Req();
//				 req.transaction=buildTramsaction("img");
//				 req.message=message;
//				 req.scene=SendMessageToWX.Req.WXSceneSession;
//				 api.sendReq(req);
//				 
//	            };
//		});
	}
	  protected String buildTramsaction(String type) {
		     return (type == null) ? String.valueOf(System.currentTimeMillis())   
			            :type + System.currentTimeMillis();   
	}

	/**
	 * 设置微信朋友圈分享内容
	 */
	private void CircleShareContent() {
		new ShareAction((Activity) context)
		.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
		.setCallback(umShareListener)
		.withText(content)
		.withTitle(title)
		.withTargetUrl(targetUrl)
		.withMedia(new UMImage(context, imagePath))
		.share();
//		cn.sharesdk.wechat.moments.WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
//		sp.setTitle(title);
//		sp.setText(content);
//		sp.setImageUrl(imagePath);
//		sp.setUrl(targetUrl);
//		sp.setShareType(Platform.SHARE_WEBPAGE);
//		Platform wx = ShareSDK.getPlatform (WechatMoments.NAME);
//		wx. setPlatformActionListener (paListener); // 设置分享事件回调
//		// 执行图文分享
//		wx.share(sp);
	}

//	PlatformActionListener paListener=new PlatformActionListener() {
//		
//		@Override
//		public void onError(Platform arg0, int arg1, Throwable arg2) {
//			((BaseActivity)context).toast("分享失败");
//			MyProgressDialog.Cancel();
//			callback.shareError();
//		}
//		
//		@Override
//		public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
//			((BaseActivity)context).toast("分享成功");
//			MyProgressDialog.Cancel();
//			callback.shareOk();
//		}
//		
//		@Override
//		public void onCancel(Platform arg0, int arg1) {
//			((BaseActivity)context).toast("分享取消");
//			MyProgressDialog.Cancel();
//			callback.shareError();
//		}
//	};
	FacebookCallback<Result> facebookCallback=new FacebookCallback<Result>() {

		@Override
		public void onCancel() {
			((BaseActivity)context).toast("分享取消");
			MyProgressDialog.Cancel();
			if (callback!=null) 	callback.shareError();
		}

		@Override
		public void onError(FacebookException arg0) {
			((BaseActivity)context).toast("分享失败");
			MyProgressDialog.Cancel();
			if (callback!=null) 	callback.shareError();
		}

		@Override
		public void onSuccess(Result arg0) {
			((BaseActivity)context).toast("分享成功");
			MyProgressDialog.Cancel();
			if (callback!=null) 	callback.shareOk();
		}
	};
	UMShareListener umShareListener=new UMShareListener() {
		
		@Override
		public void onResult(SHARE_MEDIA arg0) {
			MyProgressDialog.Cancel();
			if (callback!=null) 	callback.shareOk();
	
		}
		
		@Override
		public void onError(SHARE_MEDIA arg0, Throwable arg1) {
			MyProgressDialog.Cancel();
			if (callback!=null) 	callback.shareError();
		}
		
		@Override
		public void onCancel(SHARE_MEDIA arg0) {
			MyProgressDialog.Cancel();
			if (callback!=null) 	callback.shareError();
		}
	};


	/*
	 * 隐藏界面
	 */
	public void cancel() {
		mPopupWindow.dismiss();
	}

	/**
	 * 设置添加屏幕的背景透明度
	 * 
	 * @param bgAlpha
	 */
	public void backgroundAlpha(float bgAlpha) {
		WindowManager.LayoutParams lp = ((Activity) context).getWindow()
				.getAttributes();
		lp.alpha = bgAlpha; // 0.0-1.0
		((Activity) context).getWindow().setAttributes(lp);
	}

class MyAdapter extends BaseAdapter{

	String []names=new String[]{"微信","朋友圈","QQ","FaceBook"};
	int []resIds=new int[]{R.drawable.umeng_socialize_wechat,R.drawable.umeng_socialize_wxcircle,R.drawable.umeng_socialize_qq_on,R.drawable.umeng_socialize_facebook};

	@Override
	public int getCount() {
		return names.length;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup group) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder(); 
			convertView = LayoutInflater.from(context).inflate(
					R.layout.adapter_share, group, false);
			holder.name = (TextView) convertView.findViewById(R.id.tv_name);//
			holder.icon = (ImageView) convertView.findViewById(R.id.iv_icon);//
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setPosition(position);
		return convertView;
	}

	class ViewHolder {
		TextView name;
		TextView introduction;
		ImageView icon;

		public void setPosition(int position) {
			icon.setImageResource(resIds[position]);
			name.setText(names[position]);
		}
	}


	
}

}
