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
import com.greattone.greattone.util.ImageLoaderUtil;
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
	public static Dialog showVoteHintDialog(Context context,String titleicon,final String url,String message,boolean isOK){
		int screenWidth=getScreenWidth(context);
		dialog = new Dialog(context, R.style.alertView);
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = (View) inflater.inflate(R.layout.hint_dialog, null);
		// 标题和内容
		TextView textView = (TextView) view.findViewById(R.id.tv_hint_dialog);
		TextView	btn = (TextView) view.findViewById(R.id.tv_hint_dialog_btn);
		ImageView icon=(ImageView)view.findViewById(R.id.iv_hint_dialog);
		icon.setLayoutParams(new RelativeLayout.LayoutParams(screenWidth*2/3, screenWidth/2));
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
			btn.setTextColor(Color.RED);
			btn.setText("请稍后再来投票！");
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


//	/**
//	 * 实现一个按钮，2个按钮
//	 * @param mContext 上下文
//	 * @param titleicon  头部图标
//	 * @param title   头部文字
//	 * @param message  内容
//	 * @param positiveButton  一个默认按钮
//	 * @param otherButtons  其他按钮
//	 * @param positiveListener 回调监听
//	 * @return
//	 */
//	@SuppressWarnings("deprecation")
//	public static Dialog showAlertView(Context mContext, int titleicon,String title,
//			String message, String positiveButton,
//			final String[] otherButtons,
//			final OnAlertViewClickListener positiveListener) {
//		final Dialog dialog = new Dialog(mContext, R.style.alertView);
//
//		LayoutInflater inflater = LayoutInflater.from(mContext);
//		View view = (View) inflater.inflate(R.layout.alertview, null);
//		// 标题和内容
//		TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
//		TextView messageTextView = (TextView) view.findViewById(R.id.messageTextView);
//		ImageView icon=(ImageView)view.findViewById(R.id.titileicon);
//		if(0!=titleicon){
//			icon.setBackgroundResource(titleicon);
//		}else{
//			icon.setVisibility(View.GONE);
//		}
//		if (null != title && !title.equalsIgnoreCase("")) {
//			titleTextView.setText(title);
//		} else {
//			titleTextView.setVisibility(View.GONE);
//		}
//		if (null != message && !message.equalsIgnoreCase("")) {
//			messageTextView.setText(message);
//		} else {
//			messageTextView.setVisibility(View.GONE);
//		}
//		Button pButton = new Button(mContext);
//		pButton.setLayoutParams(new LinearLayout.LayoutParams(
//				LayoutParams.MATCH_PARENT, dip2px(mContext, 44), 1.0f));
//		pButton.setTextColor(mContext.getResources().getColor(R.color.dialogTxtColor));
//		// 动态添加按钮
//		LinearLayout buttonLayout = (LinearLayout) view.findViewById(R.id.buttonLayout);
//		if (null == otherButtons || otherButtons.length == 0) {
//			// 一个按钮
//			buttonLayout.setOrientation(LinearLayout.VERTICAL);
//			pButton.setBackgroundResource(R.drawable.alert_bottom_button);
//		} else if (null != otherButtons && otherButtons.length == 1) {
//			// 两个按钮
//			buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
//			pButton.setBackgroundResource(R.drawable.alert_left_button);
//		} else {
//			// 三个或三个以上按钮
//			buttonLayout.setOrientation(LinearLayout.VERTICAL);
//			pButton.setBackgroundResource(R.drawable.alert_middle_button);
//		}
//
//		pButton.setText(positiveButton);
//		pButton.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if (null != positiveListener) {
//					positiveListener.cancel();
//				}
//				dialog.dismiss();
//			}
//		});
//		buttonLayout.addView(pButton);
//		if (null != otherButtons && otherButtons.length > 0) {
//			for (int i = 0; i < otherButtons.length; i++) {
//				final int index = i;
//				Button otherButton = new Button(mContext);
//				otherButton.setLayoutParams(new LinearLayout.LayoutParams(
//						LayoutParams.MATCH_PARENT, dip2px(mContext, 44), 1.0f));
//				otherButton.setText(otherButtons[i]);
//				otherButton.setTextColor(mContext.getResources().getColor(R.color.dialogTxtColor));
//				if (1 == otherButtons.length) {
//					otherButton
//					.setBackgroundResource(R.drawable.alert_right_button);
//				} else if (i < (otherButtons.length - 1)) {
//					otherButton
//					.setBackgroundResource(R.drawable.alert_middle_button);
//				} else {
//					otherButton
//					.setBackgroundResource(R.drawable.alert_bottom_button);
//				}
//				otherButton.setOnClickListener(new OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						if (null != positiveListener) {
//							positiveListener.confirm(otherButtons[index]);
//						}
//						dialog.dismiss();
//					}
//				});
//				buttonLayout.addView(otherButton);
//			}
//		}
//		final int viewWidth = dip2px(mContext, 250);
//		view.setMinimumWidth(viewWidth);
//		dialog.setCanceledOnTouchOutside(false);
//		dialog.setContentView(view);
//		dialog.show();
//		return dialog;
//	}
//	/**
//	 * 底部弹出dialog
//	 * @param mContext
//	 * @param title
//	 * @param items
//	 * @param dialogClickListener
//	 * @return
//	 */
//	@SuppressWarnings("deprecation")
//	public static android.app.Dialog ShowBottomDialog(Context mContext,String title,String[] items,final DialogItemClickListener dialogClickListener){ 
//		final android.app.Dialog dialog=new android.app.Dialog(mContext, R.style.DialogStyle);
//		dialog.setCancelable(true);
//		dialog.setCanceledOnTouchOutside(false);
//		View view=LayoutInflater.from(mContext).inflate(R.layout.dialog_radio, null);
//		dialog.setContentView(view);
//		if(title.equals("")||title==null){
//			((TextView)view.findViewById(R.id.title)).setVisibility(View.GONE);
//			((TextView)view.findViewById(R.id.title_divider)).setVisibility(View.GONE);
//		}else{
//			((TextView)view.findViewById(R.id.title)).setText(title);
//		}
//		//根据items动态创建
//		LinearLayout parent=(LinearLayout) view.findViewById(R.id.dialogLayout);
//		parent.removeAllViews();
//		int length=items.length;
//		for ( int i = 0; i < items.length; i++) {
//			final int position=i;
//			LayoutParams params1=new LayoutParams(-1,-2);
//			params1.rightMargin=1;
//			final TextView tv=new TextView(mContext);
//			tv.setLayoutParams(params1);
//			tv.setTextSize(18);
//			tv.setText(items[i]);
//			tv.setTextColor(mContext.getResources().getColor(R.color.dialogTxtColor));
//			int pad=mContext.getResources().getDimensionPixelSize(R.dimen.padding10);
//			tv.setPadding(pad,pad,pad,pad);
//			tv.setSingleLine(true);
//			tv.setGravity(Gravity.CENTER);
//			if(i!=length-1)
//				if((title.equals("")||title==null)&&i==0)
//					tv.setBackgroundResource(R.drawable.menudialog_top2_selector);
//				else
//					tv.setBackgroundResource(R.drawable.menudialog_center_selector);
//			else
//				if(length==1)
//					tv.setBackgroundResource(R.drawable.menudialog_bottom_selector);
//				else
//					tv.setBackgroundResource(R.drawable.menudialog_bottom2_selector);
//
//			tv.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View arg0) {
//					dialog.dismiss();
//					dialogClickListener.itemClick(tv.getText().toString(),position);
//				}
//			});
//			parent.addView(tv);
//			if(i!=length-1){
//				TextView divider=new TextView(mContext);
//				LayoutParams params=new LayoutParams(-1,(int)1);
//				divider.setLayoutParams(params);
//				divider.setBackgroundResource(android.R.color.darker_gray);
//				parent.addView(divider);
//			}
//		}
//		view.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				dialog.dismiss();
//			}
//		});
//		Window mWindow=dialog.getWindow();
//		WindowManager.LayoutParams lp = mWindow.getAttributes();
//		lp.width= getScreenWidth(mContext);
//		mWindow.setGravity(Gravity.BOTTOM);
//		//添加动画
//		mWindow.setWindowAnimations(R.style.dialogAnim);
//		mWindow.setAttributes(lp);
//		dialog.show();
//		return dialog;
//	}
	/**
	 * 回调接口
	 * @author Administrator
	 *
	 */
	public interface OnAlertViewClickListener {
		public abstract void confirm(String result);
		public abstract void cancel();
	}
	/**
	 * 时间dialog回调接口
	 * @author Administrator
	 *
	 */
	public interface OnTimeAlertViewClickListener {
		public abstract void confirm();
	}

	public interface DialogItemClickListener{
		public abstract void itemClick(String result, int position);
	}
//	public void setPositiveButton(String positiveButton,android.content.DialogInterface.OnClickListener onClickListener){
//		this.positiveButton=positiveButton;
//		this.onClickListener=onClickListener;
//	}
//	public void setNeutralButton(String neutralButton,android.content.DialogInterface.OnClickListener onClickListener){
//		this.neutralButton=neutralButton;
//		this.onClickListener2=onClickListener;
//	}
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
