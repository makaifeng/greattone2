package com.greattone.greattone.activity.timetable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.greattone.greattone.R;
import com.greattone.greattone.entity.TimeTable_Month;
import com.greattone.greattone.util.DisplayUtil;
import com.kf_test.kfcalendar.SpecialCalendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 日历gridview中的每一个item显示的textview
 * 
 * @author chaogege
 * 
 */
@SuppressWarnings("unused")
public class TimeTableAdapter extends BaseAdapter {
	JSONObject jb;
	int screenWidth;
	private boolean isLeapyear = false; // 是否为闰年
	private int daysOfMonth = 0; // 某月的天数
	private int dayOfWeek = 0; // 具体某一天是星期几
	private int lastDaysOfMonth = 0; // 上一个月的总天数
	private Context context;
	private String[] dayNumber = new String[42]; // 一个gridview中的日期存入此数组中
	// private static String week[] = {"周日","周一","周二","周三","周四","周五","周六"};
	private SpecialCalendar sc = null;
//	private LunarCalendar lc = null;
	private Resources res = null;
	private Drawable drawable = null;

	private String currentYear = "";
	private String currentMonth = "";
	private String currentDay = "";

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
	private int currentFlag = -1; // 用于标记当天
//	private int[] schDateTagFlag = null; // 存储当月所有的日程日期

	private String showYear = ""; // 用于在头部显示的年份
	private String showMonth = ""; // 用于在头部显示的月份
	private String leapMonth = ""; // 闰哪一个月
	private String cyclical = ""; // 天干地支
	// 系统当前时间
	private String sysDate = "";
	private String sys_year = "";
	private String sys_month = "";
	private String sys_day = "";
	private String TAG="TimeTableAdapter";

	public TimeTableAdapter() {
		Date date = new Date();
		sysDate = sdf.format(date); // 当期日期
		sys_year = sysDate.split("-")[0];
		sys_month = sysDate.split("-")[1];
		sys_day = sysDate.split("-")[2];

	}

	public TimeTableAdapter(Context context, Resources rs, int jumpMonth,
			int jumpYear, int currentYear, int currentMonth, int currentDay) {
		this();
		this.context = context;
		sc = new SpecialCalendar();
		this.res = rs;

		int stepYear = currentYear + jumpYear;
		int stepMonth = currentMonth + jumpMonth;
		if (stepMonth > 0) {
			// 往下一个月滑动
			if (stepMonth % 12 == 0) {
				stepYear = currentYear + stepMonth / 12 - 1;
				stepMonth = 12;
			} else {
				stepYear = currentYear + stepMonth / 12;
				stepMonth = stepMonth % 12;
			}
		} else {
			// 往上一个月滑动
			stepYear = currentYear - 1 + stepMonth / 12;
			stepMonth = stepMonth % 12 + 12;
			if (stepMonth % 12 == 0) {

			}
		}

		this.currentYear = String.valueOf(stepYear); // 得到当前的年份
		this.currentMonth = String.valueOf(stepMonth); // 得到本月
														// （jumpMonth为滑动的次数，每滑动一次就增加一月或减一月）
		this.currentDay = String.valueOf(currentDay); // 得到当前日期是哪天

		getCalendar(Integer.parseInt(this.currentYear),
				Integer.parseInt(this.currentMonth));

	}

	public TimeTableAdapter(Context context, Resources rs, int year, int month,
			int day) {
		this();
		this.context = context;
		sc = new SpecialCalendar();
		this.res = rs;
		currentYear = String.valueOf(year);// 得到跳转到的年份
		currentMonth = String.valueOf(month); // 得到跳转到的月份
		currentDay = String.valueOf(day); // 得到跳转到的天
		getCalendar(Integer.parseInt(currentYear),
				Integer.parseInt(currentMonth));
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dayNumber.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.adapter_time_table, parent, false);
			holder.date = (TextView) convertView.findViewById(R.id.tv_date);//
			int width=(DisplayUtil.getScreenWidth(context)-dip2px(10))/7-dip2px(10)*2;
			width=width<0?0:width;
			RelativeLayout.LayoutParams layoutParams=	new RelativeLayout.LayoutParams(width, width);
			layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
			layoutParams.topMargin=dip2px(5);
			layoutParams.bottomMargin=dip2px(5);
			holder.date.setLayoutParams(layoutParams);
			holder.sleep = (TextView) convertView.findViewById(R.id.tv_sleep);//
			holder.entry1 = (TextView) convertView.findViewById(R.id.tv_entry1);//
			holder.entry2 = (TextView) convertView.findViewById(R.id.tv_entry2);//
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
//		if (currentFlag == position) {
//			// 设置当天的背景
//			// drawable = res.getDrawable(R.drawable.calendar_item_selected_bg);
//			// drawable = new ColorDrawable(Color.rgb(23, 126, 214));
//			textView.setBackgroundColor(Color.rgb(23, 126, 214));
//			textView.setTextColor(Color.WHITE);
//		}
		holder.setPosition(position);
		return convertView;
	}

	public void setData(String data) {
		this.jb= JSON.parseObject(data);
		notifyDataSetChanged();
	}

	class ViewHolder {
		TextView date;
		TextView sleep;
		TextView entry1;
		TextView entry2;

		public void setPosition(int position) {
			String d = dayNumber[position];
			List<TimeTable_Month> mlist;
			if (jb!=null) {
				 mlist = JSON.parseArray(jb.getString(d), TimeTable_Month.class);//课程数据
			}else {
				mlist=new ArrayList<>();
			}
			date.setVisibility(View.VISIBLE);
			sleep.setVisibility(View.GONE);
			entry1.setVisibility(View.INVISIBLE);
			entry2.setVisibility(View.INVISIBLE);
			date.setBackgroundResource(R.drawable.time_table_text_bg_unselect);
			//课程判断
		switch (mlist.size()) {
			case 0://没课程
				break;
			case 1://有课程 ,只有一节
				entry1.setText(mlist.get(0).getStuname());
				entry1.setVisibility(View.VISIBLE);
			case 2://有课程 ,有多节
				entry2.setText(mlist.get(1).getStuname());
				entry2.setVisibility(View.VISIBLE);
				break;
			default:
				entry1.setText(mlist.get(0).getStuname());
				entry1.setVisibility(View.VISIBLE);
				entry2.setText(mlist.get(1).getStuname());
				entry2.setVisibility(View.VISIBLE);
				break;
		}
//			if (position==15) {
//				entry1.setVisibility(View.INVISIBLE);
//				entry2.setVisibility(View.INVISIBLE);
//				sleep.setVisibility(View.VISIBLE);
//				date.setBackgroundResource(R.drawable.time_table_text_bg_select);
//
//			}

			if (position < daysOfMonth + dayOfWeek && position >= dayOfWeek) {//当月
				date.setText(d);

				// 当前月信息显示
				date.setTextColor(Color.BLACK);// 当月字体设黑
				// drawable = res.getDrawable(R.drawable.calendar_item_selected_bg);
//				drawable = new ColorDrawable(Color.rgb(23, 126, 214));
//				if (position % 7 == 0 || position % 7 == 6) {//星期六和星期天
//					// 当前月信息显示
//					textView.setTextColor(Color.rgb(23, 126, 214));// 当月字体设黑
//					// drawable =
//					// res.getDrawable(R.drawable.calendar_item_selected_bg);
//					drawable = new ColorDrawable(Color.rgb(23, 126, 214));
//				}
			}else{
				date.setText("");
//				date.setVisibility(View.GONE);
				sleep.setVisibility(View.GONE);
				entry1.setVisibility(View.INVISIBLE);
				entry2.setVisibility(View.INVISIBLE);
			}


		}
	}
	// 得到某年的某月的天数且这月的第一天是星期几
	public void getCalendar(int year, int month) {
		isLeapyear = sc.isLeapYear(year); // 是否为闰年
		daysOfMonth = sc.getDaysOfMonth(isLeapyear, month); // 某月的总天数
		dayOfWeek = sc.getWeekdayOfMonth(year, month); // 某月第一天为星期几
		lastDaysOfMonth = sc.getDaysOfMonth(isLeapyear, month - 1); // 上一个月的总天数
		Log.d("DAY", isLeapyear + " ======  " + daysOfMonth
				+ "  ============  " + dayOfWeek + "  =========   "
				+ lastDaysOfMonth);
		Log.d("screenWidth",	screenWidth+"");
		getweek(year, month);
	}

	// 将一个月中的每一天的值添加入数组dayNuber中
	private void getweek(int year, int month) {
		int j = 1;
		int flag = 0;

		// 得到当前月的所有日程日期(这些日期需要标记)

		for (int i = 0; i < dayNumber.length; i++) {
			// 周一
			// if(i<7){
			// dayNumber[i]=week[i]+"."+" ";
			// }
			if (i < dayOfWeek) { // 前一个月
				int temp = lastDaysOfMonth - dayOfWeek + 1;
				dayNumber[i] = (temp + i) + "";

			} else if (i < daysOfMonth + dayOfWeek) { // 本月
				String day = String.valueOf(i - dayOfWeek + 1); // 得到的日期
				dayNumber[i] = i - dayOfWeek + 1 + "" ;
				// 对于当前月才去标记当前日期
				if (sys_year.equals(String.valueOf(year))
						&& sys_month.equals(String.valueOf(month))
						&& sys_day.equals(day)) {
					// 标记当前日期
					currentFlag = i;
				}
				setShowYear(String.valueOf(year));
				setShowMonth(String.valueOf(month));
			} else { // 下一个月
				dayNumber[i] = j + "";
				j++;
			}
		}

		String abc = "";
		for (String day:dayNumber) {
			abc = abc + day+ ":";
		}
//		Log.d("DAYNUMBER", abc);

	}

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     * 
     * @param dipValue
     *            （DisplayMetrics类中属性density）
     * @return
     */ 
    public  int dip2px( float dipValue) { 
        return (int) (dipValue * context.getResources().getDisplayMetrics().density + 0.5f); 
    } 

	/**
	 * 点击每一个item时返回item中的日期
	 * 
	 * @param position
	 * @return
	 */
	public String getDateByClickItem(int position) {
		return dayNumber[position];
	}

	/**
	 * 在点击gridView时，得到这个月中第一天的位置
	 * 
	 * @return
	 */
	public int getStartPositon() {
		return dayOfWeek + 7;
	}

	/**
	 * 在点击gridView时，得到这个月中最后一天的位置
	 * 
	 * @return
	 */
	public int getEndPosition() {
		return (dayOfWeek + daysOfMonth + 7) - 1;
	}

	public String getShowYear() {
		return showYear;
	}

	public void setShowYear(String showYear) {
		this.showYear = showYear;
	}

	public String getShowMonth() {
		return showMonth;
	}

	public void setShowMonth(String showMonth) {
		this.showMonth = showMonth;
	}


	public String getLeapMonth() {
		return leapMonth;
	}

	public void setLeapMonth(String leapMonth) {
		this.leapMonth = leapMonth;
	}

	public String getCyclical() {
		return cyclical;
	}

	public void setCyclical(String cyclical) {
		this.cyclical = cyclical;
	}

}
