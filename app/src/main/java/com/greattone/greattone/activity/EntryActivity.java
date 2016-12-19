package com.greattone.greattone.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.classroom.ClassRoomFragment;
import com.greattone.greattone.activity.discuss.DiscussFragment;
import com.greattone.greattone.activity.haixuan_and_activitise.ActivitiesFragment;
import com.greattone.greattone.activity.haixuan_and_activitise.HaiXuanfragment;
import com.greattone.greattone.activity.plaza.MusicPlazaFragment;
import com.greattone.greattone.activity.plaza.NewsFragment;
import com.greattone.greattone.activity.post.PostFragment;
import com.greattone.greattone.activity.teacher.TeacherFragment;
import com.greattone.greattone.fragment.MusicFragment;
import com.greattone.greattone.widget.MyTextView;

public class EntryActivity extends BaseActivity {
	String type,data;
	public MyTextView tv_head_other;
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_entry);
	data=getIntent().getStringExtra("data");
	type=getIntent().getStringExtra("type");
	initView();
}
private void initView() {
	setHead(type, true, true);
	tv_head_other = (MyTextView)findViewById(R.id.tv_head_other);
	addFragment();
}
private void addFragment() {
	Fragment fragment = null;
String titles[]=getResources().getStringArray(R.array.title_names);
	if (type.equals(titles[0])) {//音乐广场
		fragment=new MusicPlazaFragment();Bundle bundle=new Bundle();
		bundle.putString("keyboard",data);
		fragment.setArguments(bundle);
	} else if (type.equals(titles[1])) {//音乐资讯
		fragment=(new NewsFragment());
//	} else if (type.equals(titles[2])) {//音乐名人
//		fragment=(new MusicCelebritiesFragment());
	} else if (type.equals(titles[2])) {//直播课堂
		fragment=(new ZBFragment());
	} else if (type.equals(titles[3])) {//音乐老师
		fragment=(new TeacherFragment());
	} else if (type.equals(titles[4])) {//琴行教室
		fragment=(new ClassRoomFragment());
	} else if (type.equals(titles[5])) {//音乐海选
		 fragment=new HaiXuanfragment();
		Bundle bundle=new Bundle();
		bundle.putString("type","音乐海选");
		fragment.setArguments(bundle);
	} else if (type.equals(titles[6])) {//音乐乐团
		fragment=(new MusicFragment());
	} else if (type.equals(titles[7])) {//声粉论坛
		fragment=(new DiscussFragment());
	} else if (type.equals(getResources().getString(R.string.post))) {//发帖
		fragment=(new PostFragment());
	} else if (type.equals(getResources().getString(R.string.new_activity))) {//活动
		fragment=(new ActivitiesFragment());
	} else if (type.equals(getResources().getString(R.string.live))) {//直播
		fragment=(new ZBFragment());
	} else if (type.equals("历史回顾")) {//历史回顾
		fragment=new HaiXuanfragment();Bundle bundle=new Bundle();
		bundle.putString("type","历史回顾");
		fragment.setArguments(bundle);
	}else {
		fragment=(new MusicFragment());
	}
	getSupportFragmentManager().beginTransaction()
	.replace(R.id.ll_fragment, fragment).commit();
}
}
