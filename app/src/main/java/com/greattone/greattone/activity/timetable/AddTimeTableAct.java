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
import com.greattone.greattone.dialog.MyIosDialog;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.MyTimePickerPopWindow;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.PersonList;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * Created by Administrator on 2016/11/2.
 */
public class AddTimeTableAct extends BaseActivity{
    private TextView tv_name,tv_location,tv_starttime,tv_stoptime,tv_repick,tv_student;
    private EditText et_remark;
private static final int Result_Name=1;
private static final int Result_Location=2;
private static final int Result_student=3;
    private int state=-1;
    private int week=1;

    private int year,month,day,hour,minute;
    //    private List<PersonList> personList=new ArrayList<>();
//    private String[] students;
    private ArrayList<String> students=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_timetable);
        initView();
        getStudents();
    }

    private void initView() {
        setHead("新增课程",true,true);
        setOtherText("保存课程", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post();
            }
        });
        tv_name=(TextView)findViewById(R.id.tv_name);
        tv_location=(TextView)findViewById(R.id.tv_location);
        tv_starttime=(TextView)findViewById(R.id.tv_starttime);
        tv_stoptime=(TextView)findViewById(R.id.tv_stoptime);
        tv_repick=(TextView)findViewById(R.id.tv_repick);
        tv_student=(TextView)findViewById(R.id.tv_student);
//        tv_state=(TextView)findViewById(R.id.tv_state);
        et_remark=(EditText)findViewById(R.id.et_remark);

        tv_name.setOnClickListener(lis);
        tv_location.setOnClickListener(lis);
        tv_starttime.setOnClickListener(lis);
        tv_stoptime.setOnClickListener(lis);
        tv_repick.setOnClickListener(lis);
        tv_student.setOnClickListener(lis);
//        tv_state.setOnClickListener(lis);
    }

    /**
     * 发帖
     */
    private void post() {
        String couname=tv_name.getText().toString().trim();//课程名称
        String location=tv_location.getText().toString().trim();//上课地点
        String classtime=year+"-"+(month<10?"0"+month:""+month)+"-"+(day<10?"0"+day:""+day);//开始日期  格式2016-11-01
        String starttime=(hour<10?"0"+hour:""+hour)+":"+(minute<10?"0"+minute:""+minute);//开始时间  	格式08:15
        String stoptime=tv_stoptime.getText().toString().trim();//结束时间   	格式08:15
        String repeat=tv_repick.getText().toString().trim();//重复周期
        String stuname=tv_student.getText().toString().trim();//学生姓名
        String remarks=et_remark.getText().toString().trim();//备注信息
        if (TextUtils.isEmpty(couname)){ toast("请填写课程名称"); return;}
//        if (TextUtils.isEmpty(location)){ toast("请填写上课地点"); return;}
        if (year==0){ toast("请选择开始时间"); return;}
        if (TextUtils.isEmpty(stoptime)){ toast("请选择结束时间"); return;}
        if (week==0){ toast("请选择重复周期"); return;}
        if (TextUtils.isEmpty(stuname)){ toast("请填写学生姓名"); return;}
//        if (TextUtils.isEmpty(remarks)){ toast("请填写备注信息"); return;}
        MyProgressDialog.show(context);
        HttpProxyUtil.postCourse(context, couname, location, classtime, starttime, stoptime, repeat, stuname, remarks, new HttpUtil.ResponseListener() {
            @Override
            public void setResponseHandle(Message2 message) {
                toast("发布课程成功");
                MyProgressDialog.Cancel();
                setResult(RESULT_OK);
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
                 case R.id.tv_location:
                     String location=tv_location.getText().toString().trim();
                     startActivityForResult(new Intent(context, EditTextActivity.class).putExtra("title","上课地点").putExtra("text",location),Result_Location);
                     break;
                 case R.id.tv_starttime:
                     MyTimePickerPopWindow.showAllPopupWindow(context, new MyTimePickerPopWindow.OnAllSureListener() {
                         @Override
                         public void onAllSure(String time, int year, int month, int day, int hour, int minute) {
                             AddTimeTableAct.this.year=year;
                             AddTimeTableAct.this.month=month;
                             AddTimeTableAct.this.day=day;
                             AddTimeTableAct.this.hour=hour;
                             AddTimeTableAct.this.minute=minute;
                             if (hour<8||hour>20) {
                                 toast("开始时间不正确，发布课表时间段为8:00-20:00");
                             }else {
                                 tv_starttime.setText(time);
                             }
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
                             if (mHour<8||mHour>20||(mHour==20&&mMins!=0)) {
                                 toast("结束时间不正确，发布课表时间段为8:00-20:00");
                             }else {
                                 if (mHour > hour || (mHour == hour && mMins > minute)) {
                                     String xHour = mHour < 10 ? "0" + mHour : "" + mHour;
                                     String xMins = mMins < 10 ? "0" + mMins : "" + mMins;
                                     tv_stoptime.setText(xHour + ":" + xMins);
                                 } else {
                                     toast("结束时间不能小于开始时间");
                                 }
                             }
                         }
                     });
                     break;
                 case R.id.tv_repick:
                     showRepickDialog();

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
//                 case R.id.tv_state:
//                     showStateDialog();
//
//                     break;
                 default:
                     break;
             }
         }
     };
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
//                            students=new String[mList.size()];
                            for (int i=0;i<mList.size();i++) {
                                students.add(mList.get(i).getUsername());
//                                students[i]=mList.get(i).getUsername();
                            }
                        }
                        MyProgressDialog.Cancel();
                    }
                }, null));
    }
//    public  void showListSelectDialog( ){
//        if (students!=null) {
//           new AlertDialog.Builder(context)
////                .setTitle(R.string.title)
//                    .setItems(students, new DialogInterface.OnClickListener() { // 列表内容和点击事件
//
//            @Override
//            public void onClick(DialogInterface dialog, int whichPlace) {
//                //String[] place = getResources().getStringArray(R.array.place);
//                tv_student.setText(students[whichPlace]);
//            }
//        }).show();
//        }
//    }
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

    private void showRepickDialog() {
        List<String> mlist=new ArrayList<>();
        mlist.add("1周");
        mlist.add("2周");
        mlist.add("3周");
        mlist.add("4周");
        mlist.add("5周");
        mlist.add("6周");
        mlist.add("7周");
        mlist.add("8周");
        mlist.add("9周");
        mlist.add("10周");
        MyIosDialog.ShowListDialog(context,"周期" ,mlist, new MyIosDialog.DialogItemClickListener() {
            @Override
            public void itemClick(String result, int position) {
                tv_repick.setText(result);
                week=position+1;
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            String text=data==null?"": data.getStringExtra("text");
            if (requestCode==Result_Name) {
                tv_name.setText(text);
            } else if (requestCode==Result_Location){
                tv_location.setText(text);
            } else   if (requestCode==Result_student){
                tv_student.setText(text);
            }
        }
    }
}
