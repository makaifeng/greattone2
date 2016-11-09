package com.greattone.greattone.activity.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.Listener.OnBtnItemClickListener;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.adapter.MyCourseListAdapter;
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
/**我的课程*/
public class MyCoursesActivity extends BaseActivity {
	protected List<Course> courseList = new ArrayList<Course>();
	private TextView sendCourse;
	private PullToRefreshView pull_to_refresh;
	private ListView lv_content;
	/** 页数 */
	private int page = 1;

	private MyCourseListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_list);
		initView();
		getData();
	}

	private void initView() {
		setHead(getResources().getString(R.string.我的课程), true, true);
		sendCourse = (TextView) findViewById(R.id.tv_head_other);
		sendCourse.setText(getResources().getString(R.string.发布课程));
		sendCourse.setTextSize(13);
		sendCourse.setOnClickListener(lis);

		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);//
		lv_content = (ListView) findViewById(R.id.lv_content);
		pull_to_refresh.setOnHeaderRefreshListener(headerRefreshListener);
		pull_to_refresh.setOnFooterRefreshListener(footerRefreshListener);
		adapter = new MyCourseListAdapter(context, courseList);
		adapter.setOnBtnClick(btnItemClickListener);
		lv_content.setAdapter(adapter);
		lv_content.setOnItemClickListener(listener);
		lv_content.setOnScrollListener(ImageLoaderUtil.getPauseOnScrollListener());

	}
	/**删除按钮*/
	OnBtnItemClickListener btnItemClickListener=new OnBtnItemClickListener() {
		
		@Override
		public void onItemClick(View v, final int position) {
			new AlertDialog.Builder(context)
			.setMessage(getResources().getString(R.string.你确定删除吗))
			.setPositiveButton(getResources().getString(R.string.确定),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(
								DialogInterface dialog,
								int which) {
							delect(position);
						}
					}).setNegativeButton(getResources().getString(R.string.取消), null).show();
		}
	};

	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(context, CourseDeailsActivity.class);
			intent.putExtra("id", courseList.get(position).getId());
			intent.putExtra("classid", courseList.get(position).getClassid());
			intent.putExtra("type", "center");
			startActivity(intent);
		}
	};
	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_head_other:// 发布课程
				startActivityForResult(new Intent(context,
						PostCourseActivity.class), 121);
				break;

			default:
				break;
			}
		}
	};

	OnFooterRefreshListener footerRefreshListener = new OnFooterRefreshListener() {

		@Override
		public void onFooterRefresh(PullToRefreshView view) {
			page++;
			getData();
		}
	};
	OnHeaderRefreshListener headerRefreshListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
			page = 1;
			courseList.clear();
			getData();
		}
	};

	/***/
	protected void getData() {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "info/list");
		map.put("ismember", "1");
		map.put("extra", "price,tprice,ke_hour");
		map.put("userid", Data.myinfo.getUserid());
		map.put("classid", ClassId.音乐教室_课程_ID + "");
		map.put("pageIndex", page + "");
		map.put("pageSize", "20");
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
							if (mList != null && !mList.isEmpty()) {
								courseList.addAll(mList);
							} else {
								toast(getResources().getString(R.string.cannot_load_more));
							}
						}
						pull_to_refresh.onHeaderRefreshComplete();
						pull_to_refresh.onFooterRefreshComplete();
						initContentAdapter();
						MyProgressDialog.Cancel();
					}
				}, null));
	}
	/** 删除课程 
	 * @param position */
	private void delect(final int position) {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "post/ecms");
		map.put("enews", "MDelInfo");
		map.put("classid", courseList.get(position).getClassid()+"");
		map.put("id", courseList.get(position).getId()+"");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		((BaseActivity) context).addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						toast(getResources().getString(R.string.删除成功));
						courseList.remove(position);
						MyProgressDialog.Cancel();
						page=1;
						courseList.clear();
						getData();
					}

				}, null));
}
	/**
	 * 加载ContentAdapter数据
	 */
	protected void initContentAdapter() {
		Parcelable listState = lv_content.onSaveInstanceState();
		adapter.notifyDataSetChanged();
		lv_content.onRestoreInstanceState(listState);

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && requestCode == 121) {// 发布
			page=1;
			courseList.clear();
			getData();
		}
	}
}
