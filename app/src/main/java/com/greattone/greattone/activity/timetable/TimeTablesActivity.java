package com.greattone.greattone.activity.timetable;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.android.volley.VolleyError;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.TimeTable_Month;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil;
import com.kf_test.kfcalendar.CalendarListener;
import com.kf_test.kfcalendar.CalendarListener.OnDateClickListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 *课程表月视图
 * @author makaifeng
 *
 */
public class TimeTablesActivity extends BaseActivity {
	private Map<String,List<TimeTable_Month>> map=new HashMap<>();
	private TimeTableLinlayout ll_timetable;
	private String userid;
	private String month;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timetable);
		userid=getIntent().getStringExtra("userid");
		initView();
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		 month=simpleDateFormat.format( new Date());
		 getData(month);
	}



	private void initView() {
		setHead("课程表", true, true);
		if (userid==null) {
			setOtherText("发布课表", new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					startActivityForResult(new Intent(context, AddTimeTableAct.class),11);
				}
			});
		}
		ll_timetable=(TimeTableLinlayout)findViewById(R.id.ll_timetable);
		ll_timetable.setOnDateClickListener(onDateClickListener);
		ll_timetable.setMonthChangeListener(monthChangeListener);
		ll_timetable.setBackgroundColor(Color.rgb(255, 255, 255));
		if (userid==null) {
			ll_timetable.setIsMy(true);
		}else {
			ll_timetable.setIsMy(false);
		}
	}
	OnDateClickListener onDateClickListener=new OnDateClickListener() {
		
		@Override
		public void OnDateClick(String year, String month, String day, long selectDate) {
//			toast(year+"-"+month+"-"+day);
			startActivity(new Intent(context, TimeTablesForDayActivity.class).putExtra("date", selectDate).putExtra("userid", userid));
		}
	};
	CalendarListener.MonthChangeListener monthChangeListener =new CalendarListener.MonthChangeListener() {
		@Override
		public void MonthChange(String month) {
			TimeTablesActivity.	this.month=month;
			getData(month);
		}
	};
	/**
	 *
	 * @param month
     */
	private void getData(String month) {
		MyProgressDialog.show(context);
		HttpProxyUtil.getCourseMonthList(context, userid,month, new HttpUtil.ResponseListener() {
			@Override
			public void setResponseHandle(Message2 message) {
				if (message!=null&&message.getData()!=null&&message.getData().startsWith("{")){
					ll_timetable.setData(message.getData());
				}
				MyProgressDialog.Cancel();
			}
		}, new HttpUtil.ErrorResponseListener() {
			@Override
			public void setServerErrorResponseHandle(Message2 message) {

			}

			@Override
			public void setErrorResponseHandle(VolleyError error) {

			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode,resultCode,data);
		switch (resultCode){
			case RESULT_OK:
				getData(month);
				break;
		}
	}
}

