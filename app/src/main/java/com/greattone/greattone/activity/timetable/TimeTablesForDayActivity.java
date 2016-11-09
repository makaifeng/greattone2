package com.greattone.greattone.activity.timetable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.TimeTable_Day;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.widget.CourseView;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import noman.weekcalendar.WeekCalendar;
import noman.weekcalendar.listener.OnDateClickListener;
import noman.weekcalendar.listener.OnWeekChangeListener;

/**
 * 课表
 * @author makaifeng
 *
 */
public class TimeTablesForDayActivity extends BaseActivity {
    private List<TimeTable_Day> courseList=new ArrayList();
    private WeekCalendar weekCalendar;
    long dateMillis;
    private CourseView courseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable_for_day);
        dateMillis = getIntent().getLongExtra("date",0);
        init();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String date=simpleDateFormat.format( new Date(dateMillis));
        getData(date);
    }


    private void init() {
		setHead("课程表", true, true);


         courseView= (CourseView) findViewById(R.id.courseView);
//        TimeCouse TimeCouse =new TimeCouse(0,"钢琴基础课程","小张","10:30","12:30");
//        courseList.add(TimeCouse);
//        TimeCouse =new TimeCouse(1,"钢琴基础课程","小王","15:00","17:00");
//        courseList.add(TimeCouse);
//        TimeCouse =new TimeCouse(2,"钢琴基础课程","小王","18:00","19:15");
//        courseList.add(TimeCouse);

        courseView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                startActivity(new Intent(context,TimeTableDetailAct.class).putExtra("id",courseList.get(position).getId()));
//                Toast.makeText(context,courseList.get(position).getCouname(),Toast.LENGTH_SHORT).show();
            }
        });

//        Button todaysDate = (Button) findViewById(R.id.today);
//        Button selectedDate = (Button) findViewById(R.id.selectedDateButton);
//        Button startDate = (Button) findViewById(R.id.startDate);
//        todaysDate.setText(new DateTime().toLocalDate().toString() + " (Reset Button)");
//        selectedDate.setText(new DateTime().plusDays(50).toLocalDate().toString()
//                + " (Set Selected Date Button)");
//        startDate.setText(new DateTime().plusDays(7).toLocalDate().toString()
//                + " (Set Start Date Button)");
//
        weekCalendar = (WeekCalendar) findViewById(R.id.weekCalendar);
        weekCalendar.setSelectedDate(new DateTime(dateMillis));
        weekCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(DateTime dateTime) {
                getData(dateTime.toString().substring(0,10));
//                Toast.makeText(TimeTablesForDayActivity.this, "You Selected " + dateTime.toString().substring(0,10),
//                        Toast.LENGTH_SHORT).show();
            }

        });
        weekCalendar.setOnWeekChangeListener(new OnWeekChangeListener() {
            @Override
            public void onWeekChange(DateTime firstDayOfTheWeek, boolean forward) {
//                Toast.makeText(TimeTablesForDayActivity.this, "Week changed: " + firstDayOfTheWeek +
//                        " Forward: " + forward, Toast.LENGTH_SHORT).show();
            }
        });
    }



//    public void onNextClick(View veiw) {
//        weekCalendar.moveToNext();
//    }
//
//
//    public void onPreviousClick(View view) {
//        weekCalendar.moveToPrevious();
//    }
//
//    public void onResetClick(View view) {
//        weekCalendar.reset();
//
//    }
//    public void onSelectedDateClick(View view){
//        weekCalendar.setSelectedDate(new DateTime().plusDays(50));
//    }
//    public void onStartDateClick(View view){
//        weekCalendar.setStartDate(new DateTime().plusDays(7));
//    }

    /**
     * 获取数据
     */
    private void getData(String date) {
        MyProgressDialog.show(context);
        HttpProxyUtil.getCourseDateList(context, date, new HttpUtil.ResponseListener() {
            @Override
            public void setResponseHandle(Message2 message) {
                if (message!=null&&message.getData()!=null&&message.getData().startsWith("[")){
                    courseList.clear();
                    courseList= JSON.parseArray(message.getData(),TimeTable_Day.class);
                    courseView.setCouseList(courseList);
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

}

