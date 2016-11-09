package com.kf_test.kfcalendar;


public class CalendarListener  {
	public interface MonthChangeListener {
		void MonthChange(String month);
	}
	public interface OnDateClickListener {
		void OnDateClick(String year, String month, String day, long selectDate);
	}
}
