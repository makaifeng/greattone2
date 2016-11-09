package com.greattone.greattone.activity.timetable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.TimeTable_Day;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil;

/**
 * 课程详情
 * Created by Administrator on 2016/11/2.
 */
public class TimeTableDetailAct extends BaseActivity{
    TimeTable_Day timeTable=new TimeTable_Day();
    String id;
    private TextView tv_name;
    private TextView tv_student;
    private TextView tv_starttime;
    private TextView tv_stoptime;
    private TextView tv_location;
    private TextView tv_state;
    private TextView tv_remark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable_detail);
        id=getIntent().getStringExtra("id");
        initView();
        getData();
    }
    private void initView() {
        setHead("课程表",true,true);

        tv_name=    (TextView)findViewById(R.id.tv_name);
        tv_student=    (TextView)findViewById(R.id.tv_student);
        tv_starttime=    (TextView)findViewById(R.id.tv_starttime);
        tv_stoptime=    (TextView)findViewById(R.id.tv_stoptime);
        tv_location=    (TextView)findViewById(R.id.tv_location);
        tv_state=    (TextView)findViewById(R.id.tv_state);
        tv_remark=    (TextView)findViewById(R.id.tv_remark);
    }
    private void initViewData() {
        tv_name.setText(timeTable.getCouname());
        tv_student.setText(timeTable.getStuname());
        String date[]=timeTable.getClasstime().split("\\-");
        tv_starttime.setText(date[0]+"年"+date[1]+"月"+date[2]+"日 "+timeTable.getStarttime());
        tv_stoptime.setText(date[0]+"年"+date[1]+"月"+date[2]+"日 "+timeTable.getStoptime());
        tv_location.setText(timeTable.getLocation());
        String state="";
        if (timeTable.getState()==1)   state="未开始";
        tv_state.setText(state);
        tv_remark.setText(timeTable.getRemarks());
    }
    /**
     * 获取数据
     */
    private void getData() {
        MyProgressDialog.show(context);
        HttpProxyUtil.getTimeTableDetailDate(context, id, new HttpUtil.ResponseListener() {
            @Override
            public void setResponseHandle(Message2 message) {
                if (message!=null&&message.getData()!=null&&message.getData().startsWith("{")){
                    timeTable= JSON.parseObject(message.getData(),TimeTable_Day.class);
                    initViewData();
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
