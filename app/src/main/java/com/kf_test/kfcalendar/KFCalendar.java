package com.kf_test.kfcalendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.greattone.greattone.R;
import com.kf_test.kfcalendar.CalendarListener.MonthChangeListener;
import com.kf_test.kfcalendar.CalendarListener.OnDateClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class KFCalendar extends LinearLayout {
	private  int jumpMonth = 0; // 每次滑动，增加或减去一个月,默认为0（即显示当前月）
	private  int jumpYear = 0; // 滑动跨越一年，则增加或者减去一年,默认为0(即当前年)
	List<GridView> gridList = new ArrayList<GridView>();
	private OnDateClickListener onDateClickListener;
	private MonthChangeListener changeListener;
	String week[] = { "日", "一", "二", "三", "四", "五", "六" };
	private TextView monthView;
	private int currentYear;
	private int currentMonth;
	private int currentDate;
	SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy年MM月",
			Locale.CHINA);
	private GridView gridView;
	private CalendarAdapter adapter;
	private ViewFlipper flipper;
	private GestureDetector gestureDetector;
	private long currentTime;

	public KFCalendar(Context context, AttributeSet attrs) {
		super(context, attrs);
		gestureDetector = new GestureDetector(getContext(),
				new MyGestureListener());
		init();
	}

	public KFCalendar(Context context) {
		super(context);
		gestureDetector = new GestureDetector(getContext(),
				new MyGestureListener());
		init();
	}

	private void init() {
		setOrientation(VERTICAL);
		getCurrentDate();
		createYearAndMonthLayout();
		createWeekLayout();
		createDateLayout();
	}

	/** 获取当前时间 */
	private void getCurrentDate() {
		Calendar calendar = Calendar.getInstance();
		currentYear = calendar.get(Calendar.YEAR);
		currentMonth = calendar.get(Calendar.MONTH);
		currentDate = calendar.get(Calendar.DAY_OF_MONTH);
		currentTime = calendar.getTimeInMillis();
	}

	/** 创建年月布局 */
	private void createYearAndMonthLayout() {
		monthView = new TextView(getContext());
		LayoutParams monthParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		monthView.setLayoutParams(monthParams);
		monthView.setPadding(0, dip2px(5), 0, dip2px(5));
		monthView.setGravity(Gravity.CENTER);
		monthView.setText(monthFormat.format(new Date(System.currentTimeMillis())));
		addView(monthView);
	}

	private void createWeekLayout() {
		LinearLayout layout = new LinearLayout(getContext());
		LayoutParams linearLayoutParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		layout.setLayoutParams(linearLayoutParams);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		for (int i = 0; i < 7; i++) {
			TextView textView = new TextView(getContext());
			LayoutParams textViewParams = new LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 1);
			textView.setLayoutParams(textViewParams);
			textView.setPadding(0, dip2px(5), 0, dip2px(5));
			textView.setGravity(Gravity.CENTER);
			textView.setText(week[i]);
			layout.addView(textView);
		}
		addView(layout);
	}

	private void createDateLayout() {
		flipper = new ViewFlipper(getContext());
		LayoutParams flipperParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		flipperParams.gravity = Gravity.CENTER;
		flipper.setLayoutParams(flipperParams);

		flipper.removeAllViews();
		addGridView();
		flipper.addView(gridView, 0);
		addView(flipper);
	}

	private void addGridView() {
		gridView = new GridView(getContext());
		FrameLayout.LayoutParams linearLayoutParams = new FrameLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		gridView.setLayoutParams(linearLayoutParams);
		gridView.setNumColumns(7);
		gridView.setGravity(Gravity.CENTER_VERTICAL);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		// 去除gridView边框
		gridView.setVerticalSpacing(1);
		gridView.setHorizontalSpacing(1);
		gridView.setOnItemClickListener(itemClickListener);
		gridView.setOnTouchListener(touchListener);
		setGridViewAdapter();
	}

	/** 加载adapter */
	private void setGridViewAdapter() {
		adapter = new CalendarAdapter(getContext(), getResources(), jumpMonth,
				jumpYear, currentYear, currentMonth + 1, currentDate);
		gridView.setAdapter(adapter);
	}

	OnTouchListener touchListener = new OnTouchListener() {

		@SuppressLint("ClickableViewAccessibility")
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			return gestureDetector.onTouchEvent(event);
		}
	};

	OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// 点击任何一个item，得到这个item的日期(排除点击的是周日到周六(点击不响应))
			 int startPosition = adapter.getStartPositon();
			 int endPosition = adapter.getEndPosition();
			 if (startPosition <= position + 7 && position <= endPosition - 7)
			 {
			String scheduleDay = adapter.getDateByClickItem(position).split(
					"\\.")[0]; // 这一天的阳历
			// String scheduleLunarDay =
			// calV.getDateByClickItem(position).split("\\.")[1];
			// //这一天的阴历
			String scheduleYear = adapter.getShowYear();
			String scheduleMonth = adapter.getShowMonth();
//			Toast.makeText(getContext(),
//					scheduleYear + "-" + scheduleMonth + "-" + scheduleDay,
//					Toast.LENGTH_SHORT).show();
			Calendar calendar = Calendar.getInstance(Locale.CHINA);
			calendar.set(Integer.valueOf(scheduleYear),
					Integer.valueOf(scheduleMonth)-1,
					Integer.valueOf(scheduleDay));
			onDateClickListener.OnDateClick(scheduleYear ,scheduleMonth
				,scheduleDay, calendar.getTimeInMillis());
			// Toast.makeText(CalendarActivity.this, "点击了该条目",
			// Toast.LENGTH_SHORT).show();
			 }
		}
	};

	private class MyGestureListener extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			int gvFlag = 0; // 每次添加gridview到viewflipper中时给的标记
			if (e1.getX() - e2.getX() > 120) {
				// 像左滑动
				enterNextMonth(gvFlag);
				return true;
			} else if (e1.getX() - e2.getX() < -120) {
				// 向右滑动
				enterPrevMonth(gvFlag);
				return true;
			}
			return false;
		}
	}

	/**
	 * 移动到下一个月
	 * 
	 * @param gvFlag
	 */
	private void enterNextMonth(int gvFlag) {
		jumpMonth++; // 下一个月
		addGridView(); // 添加一个gridView

		addTextToTopTextView(monthView); // 移动到下一月后，将当月显示在头标题中
		gvFlag++;
		flipper.addView(gridView, gvFlag);
		flipper.setInAnimation(AnimationUtils.loadAnimation(getContext(),
				R.anim.push_left_in));
		flipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(),
				R.anim.push_left_out));
		flipper.showNext();
		flipper.removeViewAt(0);
	}

	/**
	 * 移动到上一个月
	 * 
	 * @param gvFlag
	 */
	private void enterPrevMonth(int gvFlag) {
		jumpMonth--; // 上一个月
		addGridView(); // 添加一个gridView

		gvFlag++;
		addTextToTopTextView(monthView); // 移动到上一月后，将当月显示在头标题中
		flipper.addView(gridView, gvFlag);
		flipper.setInAnimation(AnimationUtils.loadAnimation(getContext(),
				R.anim.push_right_in));
		flipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(),
				R.anim.push_right_out));
		flipper.showPrevious();
		flipper.removeViewAt(0);
	}

	/**
	 * 添加头部的年份 闰哪月等信息
	 * 
	 * @param view
	 */
	public void addTextToTopTextView(TextView view) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(currentTime);
		int stepYear;
		int stepMonth = currentMonth + jumpMonth;
		// 往下一个月滑动
		if (stepMonth % 12 == 0) {
			stepYear = currentYear + stepMonth / 12 - 1;
			stepMonth = 12;
		} else {
			stepYear = currentYear + stepMonth / 12;
			stepMonth = stepMonth % 12;
		}
		calendar.set(Calendar.YEAR, stepYear);
		calendar.set(Calendar.MONTH, stepMonth);
		if (changeListener != null) {
			changeListener.MonthChange(monthFormat.format(calendar.getTime()));
		}
		view.setText(monthFormat.format(calendar.getTime()));
	}

	/**
	 * 将dip或dp值转换为px值，保证尺寸大小不变
	 * 
	 * @param dipValue
	 * @return
	 */
	public int dip2px(float dipValue) {
		return (int) (dipValue
				* getContext().getResources().getDisplayMetrics().density + 0.5f);
	}

	/**
	 * 月份变更监听
	 * 
	 * @param changeListener
	 */
	public void setMonthChangeListener(MonthChangeListener changeListener) {
		this.changeListener = changeListener;
	}

	/**
	 * 日期选择监听
	 */
	public void setOnDateClickListener(OnDateClickListener onDateClickListener) {
		this.onDateClickListener = onDateClickListener;
	}
}
