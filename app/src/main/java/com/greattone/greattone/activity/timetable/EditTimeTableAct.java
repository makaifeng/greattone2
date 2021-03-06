package com.greattone.greattone.activity.timetable;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.greattone.greattone.EditTextActivity;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.MyTimePickerPopWindow;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.PersonList;
import com.greattone.greattone.entity.TimeTable_Day;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * Created by Administrator on 2016/11/2.
 */
public class EditTimeTableAct extends BaseActivity{
    private TextView tv_name,
//            tv_location,
            tv_starttime,tv_stoptime,tv_student;
    private EditText et_remark;
    private  final int Result_Name=1;
    private  final int Result_Location=2;
    private  final int Result_student=3;
    private int state=-1;
    TimeTable_Day timeTable;
    private int year,month,day,hour,minute;//开始时间
    private int eHour,eMins;//结束时间
//    private List<PersonList> personList=new ArrayList<>();
//    private String[] students;
private ArrayList<String> students=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_timetable);
        timeTable= (TimeTable_Day) getIntent().getSerializableExtra("data");
        initView();
        getStudents();
    }

    /**
     * 获取学生
     */
    private void getStudents() {
        MyProgressDialog.show(context);
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("api", "my/invite/list");
    map.put("pageIndex", 1 + "");
    map.put("pageSize", 500 + "");
    map.put("loginuid", Data.user.getUserid());
    map.put("logintoken", Data.user.getToken());
    addRequest(HttpUtil.httpConnectionByPost(context, map,
               new HttpUtil.ResponseListener() {

        @Override
        public void setResponseHandle(Message2 message) {
            if (message.getData() != null
                    && message.getData().startsWith("[")) {
                List<PersonList> mList = JSON.parseArray(
                        message.getData(), PersonList.class);
//                students=new String[mList.size()];
                for (int i=0;i<mList.size();i++) {
                    students.add(mList.get(i).getUsername());
//                                students[i]=mList.get(i).getUsername();
                }
            }
            MyProgressDialog.Cancel();
        }
    }, null));
    }

    private void initView() {
        setHead("修改课程",true,true);
        setOtherText("保存课程", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit();
            }
        });
        tv_name=(TextView)findViewById(R.id.tv_name);
//        tv_location=(TextView)findViewById(tv_location);
        tv_starttime=(TextView)findViewById(R.id.tv_starttime);
        tv_stoptime=(TextView)findViewById(R.id.tv_stoptime);
       findViewById(R.id.tv_repick).setVisibility(View.GONE);
       findViewById(R.id.tv_repick_hint).setVisibility(View.GONE);
        tv_student=(TextView)findViewById(R.id.tv_student);
//        tv_state=(TextView)findViewById(R.id.tv_state);
        et_remark=(EditText)findViewById(R.id.et_remark);

        tv_name.setOnClickListener(lis);
//        tv_location.setOnClickListener(lis);
        tv_starttime.setOnClickListener(lis);
        tv_stoptime.setOnClickListener(lis);
        tv_student.setOnClickListener(lis);
//        tv_state.setOnClickListener(lis);
        initViewData();
    }

    private void initViewData() {
        if (timeTable!=null){
            if (timeTable.getClasstime()!=null){
                String []s=timeTable.getClasstime().split("\\-");
                if (s.length==3){
                    year= Integer.valueOf(s[0]);
                    month= Integer.valueOf(s[1]);
                    day= Integer.valueOf(s[2]);
                }
            }
            tv_name.setText(timeTable.getCouname());
//            tv_location.setText(timeTable.getLocation());
            tv_starttime.setText(timeTable.getClasstime()+" "+timeTable.getStarttime());
            try {
                String days[]=timeTable.getClasstime().split("\\-");
                String time[]=timeTable.getStarttime().split("\\:");
                if (days.length==3) {
                    year = Integer.valueOf(days[0]);
                    month = Integer.valueOf(days[1]);
                    day = Integer.valueOf(days[2]);
                }
                if (time.length==2) {
                    hour = Integer.valueOf(time[0]);
                    minute = Integer.valueOf(time[1]);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            tv_stoptime.setText(timeTable.getStoptime());
            try {
                String time[]=timeTable.getStoptime().split("\\:");
                if (time.length==2) {
                    eHour = Integer.valueOf(time[0]);
                    eMins = Integer.valueOf(time[1]);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            tv_student.setText(timeTable.getStuname());
            et_remark.setText(timeTable.getRemarks());
        }
    }

    /**
     * 发帖
     */
    private void edit() {
        String couname=tv_name.getText().toString().trim();//课程名称
//        String location=tv_location.getText().toString().trim();//上课地点
        String classtime=year+"-"+(month<10?"0"+month:""+month)+"-"+(day<10?"0"+day:""+day);//开始日期  格式2016-11-01
        String starttime=(hour<10?"0"+hour:""+hour)+":"+(minute<10?"0"+minute:""+minute);//开始时间  	格式08:15
        String stoptime=tv_stoptime.getText().toString().trim();//结束时间   	格式08:15
        String stuname=tv_student.getText().toString().trim();//学生姓名
        String remarks=et_remark.getText().toString().trim();//备注信息
        if (eHour>hour||(eHour==hour&&eMins>minute)){
        }else {
            toast("结束时间不能小于开始时间");
        }
        if (TextUtils.isEmpty(couname)){ toast("请填写课程名称"); return;}
//        if (TextUtils.isEmpty(location)){ toast("请填写上课地点"); return;}
        if (year==0){ toast("请选择开始时间"); return;}
        if (TextUtils.isEmpty(stoptime)){ toast("请选择结束时间"); return;}
        if (TextUtils.isEmpty(stuname)){ toast("请填写学生姓名"); return;}
//        if (TextUtils.isEmpty(remarks)){ toast("请填写备注信息"); return;}
        MyProgressDialog.show(context);
        HttpProxyUtil.editCourse(context,timeTable.getId(), couname, null, classtime, starttime, stoptime, stuname, remarks, new HttpUtil.ResponseListener() {
            @Override
            public void setResponseHandle(Message2 message) {
                toast("发布课程成功");
                setResult(RESULT_OK);
                MyProgressDialog.Cancel();
                finish();
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


    View.OnClickListener lis=new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             switch (view.getId()){
                 case R.id.tv_name:
                     String name=tv_name.getText().toString().trim();
                    startActivityForResult(new Intent(context, EditTextActivity.class).putExtra("title","课程名称").putExtra("text",name),Result_Name);
                     break;
//                 case tv_location:
//                     String location=tv_location.getText().toString().trim();
//                     startActivityForResult(new Intent(context, EditTextActivity.class).putExtra("title","上课地点").putExtra("text",location),Result_Location);
//                     break;
                 case R.id.tv_starttime:
                     MyTimePickerPopWindow.showAllPopupWindow(context, new MyTimePickerPopWindow.OnAllSureListener() {
                         @Override
                         public void onAllSure(String time, int year, int month, int day, int hour, int minute) {
                             EditTimeTableAct.this.year=year;
                             EditTimeTableAct.this.month=month;
                             EditTimeTableAct.this.day=day;
                             EditTimeTableAct.this.hour=hour;
                             EditTimeTableAct.this.minute=minute;
                             tv_starttime.setText(time);
                         }
                     });
                     break;
                 case R.id.tv_stoptime:
                     if (year==0){
                         toast("请先选开始时间");
                         return;
                     }
                     MyTimePickerPopWindow.showHourPopupWindow(context, new MyTimePickerPopWindow.OnHourSureListener() {
                         @Override
                         public void onHourSure(int mHour, int mMins) {
                             eHour=mHour; eMins=mMins;
                             if (mHour>hour||(mHour==hour&&mMins>minute)){
                                 String xHour=mHour<10?"0"+mHour:""+mHour;
                                 String  xMins=mMins<10?"0"+mMins:""+mMins;
                                 tv_stoptime.setText(xHour+":"+xMins);
                             }else {
                                 toast("结束时间不能小于开始时间");
                             }
                         }
                     });
                     break;
                 case R.id.tv_student://学生
                     String student=tv_student.getText().toString().trim();
                     startActivityForResult(new Intent(context, EditTextActivity.class)
                             .putExtra("title","学生名称")
                             .putExtra("isShowList",true)
                             .putExtra("list",students)
                             .putExtra("text",student),Result_student);
//                     showListSelectDialog();
                     break;
//                 case R.id.tv_state://状态
//                     showStateDialog();
//                     break;
                 default:
                     break;
             }
         }
     };

//    private void showStateDialog() {
//        List<String>       mlist=new ArrayList<>();
//        mlist.add("未开始");
//        mlist.add("已完成");
//        MyIosDialog.ShowListDialog(context,"课程状态" , mlist, new MyIosDialog.DialogItemClickListener() {
//            @Override
//            public void itemClick(String result, int position) {
//                tv_state.setText(result);
//                state=position+1;
//            }
//        });
//    }

//    public  void showListSelectDialog( ){
//        if (students!=null) {
//             new AlertDialog.Builder(context)
////                .setTitle(R.string.title)
//                    .setItems(students, new DialogInterface.OnClickListener() { // 列表内容和点击事件
//
//                        @Override
//                        public void onClick(DialogInterface dialog, int whichPlace) {
//                            //String[] place = getResources().getStringArray(R.array.place);
//                            tv_student.setText(students[whichPlace]);
//                        }
//                    }).show();
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            String text=data==null?"": data.getStringExtra("text");
            if (requestCode==Result_Name) {
                tv_name.setText(text);
//            } else if (requestCode==Result_Location){
//                tv_location.setText(text);
            } else   if (requestCode==Result_student){
                tv_student.setText(text);
            }
        }
    }
}
