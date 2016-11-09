package com.greattone.greattone.activity.celebrity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.classroom.ClassRoomActivity;
import com.greattone.greattone.activity.teacher.TeacherActivity;
import com.greattone.greattone.adapter.FensGridAdapter;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Friend;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;

/** 名人的关注，粉丝，相似的人 */
public class CelebrityfansActivity extends BaseActivity {
	int page = 1;
	private GridView gv_content;
	private List<Friend>list = new ArrayList<Friend>();
	private FensGridAdapter adapter;
	private PullToRefreshView pull_to_refresh;
	private String type;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_grid);
		type=getIntent().getStringExtra("type");
		initView();
		getData();
	}

	private void getData() {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "extend/getFollowList");
		map.put("userid",getIntent().getStringExtra("id"));
		map.put("type",type);
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {
			
			@Override
			public void setResponseHandle(Message2 message) {
				list=JSON.parseArray(message.getData(), Friend.class);
				adapter = new FensGridAdapter(context,list);
				gv_content.setAdapter(adapter);
				pull_to_refresh.onHeaderRefreshComplete();
				MyProgressDialog.Cancel();
			}
			
		}, null));
	}

	private void initView() {
		if (type.equals("feed")) {
			setHead(getResources().getString(R.string.my_friend), true, true);//我的关注
		}else 	if (type.equals("follow")){
			setHead(getResources().getString(R.string.my_fans), true, true);//我的粉丝
		}
		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);//
		gv_content = (GridView) findViewById(R.id.gv_content);
		gv_content.setNumColumns(2);
		gv_content.setOnItemClickListener(listener);
		pull_to_refresh.setOnHeaderRefreshListener(headerRefreshListener);
	}
	OnItemClickListener listener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
//			Intent intent=new Intent(context,CelebrityActivity.class);
//			intent.putExtra("id", list.get(position).getUserid());
//			intent.putExtra("groupid", list.get(position).getGroupid());
//			startActivity(intent);
			int group =Integer.valueOf( list.get(position).getGroupid());
			Intent intent = new Intent();
			if (group == 1 || group == 2) {// 普通会员和名人
				intent.setClass(context, CelebrityActivity.class);
				intent.putExtra("id",  list.get(position).getUserid() + "");
				intent.putExtra("groupid",group);
			} else if (group == 3) {// 老师
				intent.setClass(context, TeacherActivity.class);
				intent.putExtra("id",   list.get(position).getUserid() + "");
			} else if (group == 4) {// 教室
				intent.setClass(context, ClassRoomActivity.class);
				intent.putExtra("id",   list.get(position).getUserid() + "");
			}
			context.startActivity(intent);
		}
	};
	private OnHeaderRefreshListener headerRefreshListener=new OnHeaderRefreshListener() {
		
		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
			list.clear();
			getData();
		}
	};
}
