package com.greattone.greattone.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.widget.MyRoundImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

@SuppressLint("InflateParams")
public class MyHintDialog {
//	android.content.DialogInterface.OnClickListener onClickListener;
//	android.content.DialogInterface.OnClickListener onClickListener2;
//	String positiveButton;
//	String neutralButton;
	
	private static Dialog dialog;
/**
 * 投票弹框
 * @param context
 * @param titleicon 广告图
 * @param url 广告跳转
 * @param message  提示信息
 * @param isOK  成功或失败
 * @return
 */
	@SuppressWarnings("deprecation")
	public static Dialog showVoteHintDialog(Context context,String titleicon,final String url,String message,boolean isOK,boolean timeisfinish){
		int screenWidth=getScreenWidth(context);
		dialog = new Dialog(context, R.style.alertView);
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = (View) inflater.inflate(R.layout.hint_dialog, null);
		// 标题和内容
		TextView textView = (TextView) view.findViewById(R.id.tv_hint_dialog);
		TextView	btn = (TextView) view.findViewById(R.id.tv_hint_dialog_btn);
		MyRoundImageView icon=(MyRoundImageView)view.findViewById(R.id.iv_hint_dialog);
		icon.setLayoutParams(new RelativeLayout.LayoutParams(screenWidth*2/3, screenWidth*2/3));
		icon.setRadius(DisplayUtil.dip2px(context,10));
		ImageView icon_cancel=(ImageView)view.findViewById(R.id.iv_cancel);
		Drawable drawable= context.getResources().getDrawable(R.drawable.vote_bg);
		if(null != titleicon && !titleicon.equalsIgnoreCase("")){
			DisplayImageOptions options=new DisplayImageOptions.Builder()
			.showImageForEmptyUri(drawable)
			.showImageOnFail(drawable)
			.showImageOnLoading(drawable)
			.cacheOnDisk(true).cacheInMemory(true)
			.build();
			ImageLoaderUtil.getInstance().setImagebyurl(titleicon, icon, options);
		}
		if (null != message && !message.equalsIgnoreCase("")) {
			textView.setText(message);
		}
			 btn.setTextColor(Color.GREEN);
		if (isOK) {
			 btn.setText("投票成功！");
		}else {
			if (timeisfinish) {
				textView.setVisibility(View.GONE);
				btn.setText(message);
			}else {
				btn.setTextColor(Color.RED);
				btn.setText("请稍后再来投票！");
			}
		}

		 btn.setOnClickListener(new OnClickListener() {
			 
			 @Override
			 public void onClick(View v) {
				 dialog.cancel();
			 }
		 });
		 icon_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
		 icon.setOnClickListener(new OnClickListener() {
			 
			 @Override
			 public void onClick(View v) {
				if (url!=null&&!url.equalsIgnoreCase("")) {
					
				}
			 }
		 });
		dialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
			}
		});
		dialog.setCanceledOnTouchOutside(true);
		dialog.setContentView(view);
		Window mWindow=dialog.getWindow();
		WindowManager.LayoutParams lp = mWindow.getAttributes();
		lp.width= screenWidth*2/3;
		mWindow.setAttributes(lp);
		dialog.show();
		return dialog;
	}




	public interface DialogItemClickListener{
		public abstract void itemClick(String result, int position);
	}

	/**获取屏幕分辨率宽*/
	public static int getScreenWidth(Context context){
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
	}
	/**获取屏幕分辨率高*/
	public static int getScreenHeight(Context context){
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
	}
//	/**获取屏幕分辨率宽计算dialog的宽度*/
//	private static int dip2px(Context context, float dipValue) {
//		final float scale = context.getResources().getDisplayMetrics().density;
//		return (int) (dipValue * scale + 0.5f);
//	}
}
