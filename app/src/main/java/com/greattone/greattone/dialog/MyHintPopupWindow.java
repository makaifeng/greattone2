package com.greattone.greattone.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.LogUtil;

/**
 *提示框
 */
public class MyHintPopupWindow {
	private static MyHintPopupWindow myHintPopupWindow;
	private  PopupWindow mPopupWindow;
	private Context context;
	private long dismissTime=3000;//消失的时间
	private View popupView;
		
	private MyHintPopupWindow(Context context,String msg,Boolean isOK) {
		this.context = context;
		init( msg,isOK);
	}
	/**
	 * 单例构建
	 * 
	 * @param context
	 * @param dialogEnum
	 * @return
	 */
	public static MyHintPopupWindow build(Context context,String msg,Boolean isOK) {
		myHintPopupWindow = new MyHintPopupWindow(context, msg, isOK);
		return myHintPopupWindow;
	}
	
	/**
	 * 弹框自动消失时间
	 */
	public MyHintPopupWindow setDismissTime(long dismissTime){
		this.dismissTime=dismissTime;
		return myHintPopupWindow;
	}
	/*
	 * 显示界面
	 */
	public void show() {
		if (dismissTime!=0) {
			dismissTime=3000;
			new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					if (mPopupWindow!=null) {
						mPopupWindow.dismiss();
					}
				}
			}, dismissTime);
		}
		mPopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
	}
	/**
	 *
	 * 
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("InflateParams") 
	void init(String msg,Boolean isOK) {
		int screenWidth=DisplayUtil.getScreenWidth(context);
		 popupView = LayoutInflater.from(context).inflate(R.layout.popupwondow_log, null);
			// 创建一个PopuWidow对象
			mPopupWindow = new PopupWindow(popupView,2*screenWidth/3,
					screenWidth/2, true);
			mPopupWindow.setTouchable(true);
			// 设置允许在外点击消失
			mPopupWindow.setOutsideTouchable(true);
			mPopupWindow.setBackgroundDrawable(new ColorDrawable(context
					.getResources().getColor(android.R.color.transparent)));
			// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
			 mPopupWindow.getBackground().setAlpha(153);
			 
			 
			 ImageView imageView=(ImageView) popupView.findViewById(R.id.iv_popupwindow);
			 TextView textView=(TextView) popupView.findViewById(R.id.tv_popupwindow);
//			 imageView.setLayoutParams(new LinearLayout.LayoutParams(2*(screenWidth/3/3), 2*screenWidth/3/3));
//			 if (isOK) {
//				 imageView.setImageResource(R.drawable.sure);
//			}else {
//				imageView.setImageResource(R.drawable.error);
//			}
			 imageView.setVisibility(View.GONE);
			 textView.setText(msg);
			
	}
	


/**
 * 关闭弹框
 */
	public static void cancel(){
		if (myHintPopupWindow.mPopupWindow!=null) {
			myHintPopupWindow.mPopupWindow.dismiss();
		}
	}

}
