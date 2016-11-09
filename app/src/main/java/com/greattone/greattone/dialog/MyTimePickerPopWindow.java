package com.greattone.greattone.dialog;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.widget.TimeWheel.DateUtils;
import com.greattone.greattone.widget.TimeWheel.JudgeDate;
import com.greattone.greattone.widget.TimeWheel.MinutesAdapter;
import com.greattone.greattone.widget.TimeWheel.NumericWheelAdapter;
import com.greattone.greattone.widget.TimeWheel.WheelMain;
import com.greattone.greattone.widget.TimeWheel.WheelView;

import java.util.Calendar;
import java.util.Date;

public class MyTimePickerPopWindow {
	private static WheelMain wheelMainDate;

	/**
	 *选择全部  年月日 时分秒
	 * @param context
	 * @param onAllSureListener
     */
	@SuppressLint("InflateParams")
	public static void showAllPopupWindow(final Context context, final OnAllSureListener onAllSureListener) {
		WindowManager manager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		Display defaultDisplay = manager.getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		defaultDisplay.getMetrics(outMetrics);
		int width = outMetrics.widthPixels;
		 View menuView = LayoutInflater.from(context).inflate(R.layout.show_all_time_popup_window,null);
		final PopupWindow mPopupWindow = new PopupWindow(menuView, (int)(width*0.8),
				ActionBar.LayoutParams.WRAP_CONTENT);
		 wheelMainDate = new WheelMain(menuView, true);
		wheelMainDate.screenheight =  outMetrics.heightPixels;
		String time = DateUtils.currentMonth().toString();
		Calendar calendar = Calendar.getInstance();
		if (JudgeDate.isDate(time, "yyyy-MM-DD")) {
			try {
				calendar.setTime(new Date(time));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		wheelMainDate.initDateTimePicker(year, month, day, hours,minute);
		String currentTime = wheelMainDate.getTime().toString();
		mPopupWindow.setAnimationStyle(R.style.AnimationPreview);
		mPopupWindow.setTouchable(true);
		mPopupWindow.setFocusable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		mPopupWindow.showAtLocation(menuView, Gravity.CENTER, 0, 0);
		mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				backgroundAlpha(context,1.0f);
			}
		});
		backgroundAlpha(context,0.6f);
		TextView tv_cancle = (TextView) menuView.findViewById(R.id.tv_cancle);
		TextView tv_ensure = (TextView) menuView.findViewById(R.id.tv_ensure);
		TextView tv_pop_title = (TextView) menuView.findViewById(R.id.tv_pop_title);
		tv_pop_title.setText("选择起始时间");
		tv_cancle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mPopupWindow.dismiss();
				backgroundAlpha(context,1f);
			}
		});
		tv_ensure.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
			String	currentTime = wheelMainDate.getTime().toString();
//				beginTime = wheelMainDate.getTime().toString();
				onAllSureListener.onAllSure(currentTime,wheelMainDate.getYear(),wheelMainDate.getMonth(),wheelMainDate.getDay(),wheelMainDate.getHour(),wheelMainDate.getMinute());
//				try {
//					Date begin = dateFormat.parse(currentTime);
//					Date end = dateFormat.parse(beginTime);
//					tv_house_time.setText(DateUtils.formateStringH(beginTime,DateUtils.yyyyMMddHHmm));
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}
				mPopupWindow.dismiss();
				backgroundAlpha(context,1f);
			}
		});
	}

	/**
	 * 选择小时和分钟
	 * @param context
	 * @param onHourSureListener
     */
	public static void showHourPopupWindow(final Context context, final OnHourSureListener onHourSureListener) {
		WindowManager manager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		Display defaultDisplay = manager.getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		defaultDisplay.getMetrics(outMetrics);
		int width = outMetrics.widthPixels;
		int height = outMetrics.heightPixels;
		View menuView = LayoutInflater.from(context).inflate(R.layout.show_hour_popup_window,null);
		final PopupWindow mPopupWindow = new PopupWindow(menuView, (int)(width*0.8),
				ActionBar.LayoutParams.WRAP_CONTENT);
	final 	WheelView	wv_hours = (WheelView) menuView.findViewById(R.id.hour);
		final	WheelView	wv_mins = (WheelView) menuView.findViewById(R.id.mins);
		Calendar calendar = Calendar.getInstance();
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		wv_hours.setAdapter(new NumericWheelAdapter(
				0, 23));
		wv_hours.setCyclic(true);// 可循环滚动
		wv_hours.setLabel("时");// 添加文字
		wv_hours.setCurrentItem(hours);

		MinutesAdapter adapter =new MinutesAdapter(
				0, 45);

	int	textSize = (height / 140) * 4;
		wv_hours.TEXT_SIZE = textSize;
		wv_mins.TEXT_SIZE = textSize;
		wv_mins.setAdapter(adapter);
		wv_mins.setCyclic(true);// 可循环滚动
		wv_mins.setLabel("分");// 添加文字
		wv_mins.setCurrentItem(minute);


		mPopupWindow.setAnimationStyle(R.style.AnimationPreview);
		mPopupWindow.setTouchable(true);
		mPopupWindow.setFocusable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		mPopupWindow.showAtLocation(menuView, Gravity.CENTER, 0, 0);
		mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				backgroundAlpha(context,1.0f);
			}
		});
		backgroundAlpha(context,0.6f);
		TextView tv_cancle = (TextView) menuView.findViewById(R.id.tv_cancle);
		TextView tv_ensure = (TextView) menuView.findViewById(R.id.tv_ensure);
		TextView tv_pop_title = (TextView) menuView.findViewById(R.id.tv_pop_title);
		tv_pop_title.setText("选择结束时间");
		tv_cancle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mPopupWindow.dismiss();
				backgroundAlpha(context,1f);
			}
		});
		tv_ensure.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onHourSureListener.onHourSure(wv_hours.getCurrentItem(),Integer.valueOf(wv_mins.getAdapter().getItem(wv_mins.getCurrentItem())));
				mPopupWindow.dismiss();
				backgroundAlpha(context,1f);
			}
		});
	}
	public static void backgroundAlpha(Context context,float bgAlpha) {
		WindowManager.LayoutParams lp =((BaseActivity)context).getWindow().getAttributes();
		lp.alpha = bgAlpha;
		((BaseActivity)context).getWindow().setAttributes(lp);
	}
	public abstract interface OnHourSureListener {
		void onHourSure(int hour,int mins);

	}
	public abstract interface OnAllSureListener {
			void onAllSure(String time,int year,int month,int day,int hour,int minute);

	}
}
