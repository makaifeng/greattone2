package com.greattone.greattone.activity.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.adapter.CourseCenterListAdapter;
import com.greattone.greattone.data.ClassId;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Course;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;

/*** 课程中心 */
public class CourseCenterActivity extends BaseActivity {

	private List<Course>courseList = new ArrayList<Course>();
	private ListView lv_content;
	private PullToRefreshView pull_to_refresh;
	/** 页数 */
	private int page = 1;

	private final int NO_PULL = 0;// 其他
	private final int PULL_UP = 2;// 上拉
	private final int PULL_DOWN = 1;// 下拉
	/**
	 * 加载数据时的状态 判断是其他，上拉还是下拉，NO_PULL其他，PULL_DOWN下拉，PULL_UP上拉
	 * */
	int state = NO_PULL;
	int classid=ClassId.音乐教室_课程_ID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_list);
		initView();
		getCourse();
	}

	private void initView() {
			setHead(getResources().getString(R.string.course_center), true, true);//课程中心

		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);//
		lv_content = (ListView) findViewById(R.id.lv_content);
		pull_to_refresh.setOnHeaderRefreshListener(headerRefreshListener);
		pull_to_refresh.setOnFooterRefreshListener(footerRefreshListener);
		lv_content.setOnItemClickListener(itemClickListener);
		lv_content.setOnScrollListener(ImageLoaderUtil.getPauseOnScrollListener());
	}

	OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long arg3) {
			   Intent intent = new Intent(context, CourseDeailsActivity.class);
			   intent.putExtra("id",courseList.get(position).getId());
			   intent.putExtra("classid",classid+"");
			   intent.putExtra("type", "ground");
		        startActivity(intent);
		}
	};
/**获取发布的课程*/
	private void getCourse() {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "info/list");
		map.put("ismember", "1");
			map.put("extra", "price,tprice,ke_hour");
		map.put("userid", getIntent().getStringExtra("id"));
		map.put("classid", classid+"");
		map.put("pageIndex", page+"");
		map.put("pageSize","20");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData() != null
								&& message.getData().startsWith("[")) {
						List<Course> mList = JSON.parseArray(
							message.getData(), Course.class);
						if (mList.size() == 0) {
							toast(getResources().getString(R.string.cannot_load_more));
						}
						courseList.addAll(mList);
						}
							pull_to_refresh.onHeaderRefreshComplete();
							pull_to_refresh.onFooterRefreshComplete();
						initContentAdapter();
						MyProgressDialog.Cancel();
					}

				}, null));
	}


	OnFooterRefreshListener footerRefreshListener = new OnFooterRefreshListener() {

		@Override
		public void onFooterRefresh(PullToRefreshView view) {
			state = PULL_UP;
			page++;
			getCourse();
		}
	};
	OnHeaderRefreshListener headerRefreshListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
			state = PULL_DOWN;
			page = 1;
			courseList.clear();
			getCourse();
		}
	};

	/**
	 * 加载ContentAdapter数据
	 */
	protected void initContentAdapter() {
		Parcelable listState = lv_content.onSaveInstanceState();
		 CourseCenterListAdapter adapter = new  CourseCenterListAdapter(
				context, courseList);
		lv_content.setAdapter(adapter);
		lv_content.onRestoreInstanceState(listState);
	}
}
