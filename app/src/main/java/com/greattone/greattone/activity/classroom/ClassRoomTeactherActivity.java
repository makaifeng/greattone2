package com.greattone.greattone.activity.classroom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.celebrity.CelebrityActivity;
import com.greattone.greattone.activity.teacher.TeacherActivity;
import com.greattone.greattone.adapter.ClassRoomTeactherGridAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.UserInfo;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;

/** 音乐教室的老师列表 */
public class ClassRoomTeactherActivity extends BaseActivity {
	private List<UserInfo> userList = new ArrayList<UserInfo>();
	private GridView gv_content;
	private PullToRefreshView pull_to_refresh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_grid);
		initView();
		getTeacher();
	}

	private void getTeacher() {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "jiaoshi/teacher");
		map.put("userid", getIntent().getStringExtra("id"));
		map.put("extra", "shipin");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());

		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						List<UserInfo> mList = JSON.parseArray(
								message.getData(), UserInfo.class);
						if (mList.size() == 0) {
							toast(getResources().getString(R.string.cannot_load_more));
						}
						userList.addAll(mList);
						pull_to_refresh.onHeaderRefreshComplete();
						pull_to_refresh.onFooterRefreshComplete();
						initContentAdapter();
						MyProgressDialog.Cancel();
					}
				}, null));
	}

	private void initView() {
		setHead(getResources().getStringArray(R.array.title_names)[3], true, true);//音乐老师

		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);//
		gv_content = (GridView) findViewById(R.id.gv_content);// 正文
		gv_content.setNumColumns(2);
		// gv_content.setOnItemClickListener(listener);
		// pull_to_refresh.setOnHeaderRefreshListener(headerRefreshListener);
		// pull_to_refresh.setOnFooterRefreshListener(footerRefreshListener);
	}

	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long arg3) {
			// Intent intent = new Intent(context, CelebrityActivity.class);
			// intent.putExtra("id", userList.get(position).getUserid());
//			intent.putExtra("groupid", userList.get(position).getGroupid());
			// startActivity(intent);
			int group =  userList.get(position).getGroupid();
			Intent intent = new Intent();
			if (group == 1 || group == 2) {// 普通会员和名人
				intent.setClass(context, CelebrityActivity.class);
				intent.putExtra("id",  userList.get(position).getUserid() + "");
				intent.putExtra("groupid", userList.get(position).getGroupid());
			} else if (group == 3) {// 老师
				intent.setClass(context, TeacherActivity.class);
				intent.putExtra("id",  userList.get(position).getUserid() + "");
			} else if (group == 4) {// 教室
				intent.setClass(context, ClassRoomActivity.class);
				intent.putExtra("id",  userList.get(position).getUserid() + "");
			}
			context.startActivity(intent);
		}
	};
	OnFooterRefreshListener footerRefreshListener = new OnFooterRefreshListener() {

		@Override
		public void onFooterRefresh(PullToRefreshView view) {
			// page++;
			// getCelebrities();
		}
	};
	OnHeaderRefreshListener headerRefreshListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
			// page = 1;
			// userList.clear();
			// getCelebrities();
		}
	};

	protected void initContentAdapter() {
		Parcelable listState = gv_content.onSaveInstanceState();
		ClassRoomTeactherGridAdapter adapter = new ClassRoomTeactherGridAdapter(
				context, userList);
		gv_content.setAdapter(adapter);
		gv_content.onRestoreInstanceState(listState);
	}

}
