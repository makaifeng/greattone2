package com.greattone.greattone.activity2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.haixuan_and_activitise.ActivitiyDetailsActivity1;
import com.greattone.greattone.adapter.ActivitiesGridAdapter;
import com.greattone.greattone.data.ClassId;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.SelectCityPopwindow;
import com.greattone.greattone.entity.Blog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;
import com.kf_test.kfcalendar.KFCalendar;
import com.kf_test.kfcalendar.CalendarListener.OnDateClickListener;
/**活动列表*/
public class ActivitiesActivity extends BaseActivity {
	private int page = 1;
	List<Blog> activitiesList = new ArrayList<Blog>();
	private TextView tv_location;

	private PullToRefreshView pull_to_refresh;

	private TextView tv_calendar;

	private GridView gv_content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_activities);
		initView();
	}

	/**
	 * 加载视图
	 */
	private void initView() {
		setHead(getResources().getString(R.string.各地活动), true, true);
		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);//
		tv_location = (TextView) findViewById(R.id.tv_location);// 位置筛选
		tv_location.setOnClickListener(lis);
		tv_calendar = (TextView) findViewById(R.id.tv_calendar);// 日历
		tv_calendar.setOnClickListener(lis);
		gv_content = (GridView) findViewById(R.id.gv_content);// 正文
		gv_content.setOnItemClickListener(listener);
		addPullRefreshListener();
		getActivities();
		// getClassRoom2(FIRST_PAGE);

	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v == tv_location) {
				SelectCityPopwindow.build(context).show(v);
			} else if (v == tv_calendar) {
				showCalender();
			}
		}
	};

	private PopupWindow mPopupWindow;

	/**
	 * 添加上下拉刷新功能的监听
	 */
	private void addPullRefreshListener() {
		pull_to_refresh
				.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {

					@Override
					public void onHeaderRefresh(PullToRefreshView view) {
						page = 1;
						activitiesList.clear();
						getActivities();
					}
				});
		pull_to_refresh
				.setOnFooterRefreshListener(new OnFooterRefreshListener() {

					@Override
					public void onFooterRefresh(PullToRefreshView view) {
						page++;
						getActivities();
					}
				});
	}

	/**
	 * 显示日历
	 */
	@SuppressWarnings("deprecation")
	protected void showCalender() {
		KFCalendar kfCalendar = new KFCalendar(context);
		kfCalendar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		kfCalendar.setOnDateClickListener(onDateClickListener);
		kfCalendar.setBackgroundColor(Color.rgb(255, 255, 255));
		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		linearLayout.setBackgroundColor(Color.argb(150, 238, 238, 238));
		linearLayout.addView(kfCalendar);
		mPopupWindow = new PopupWindow(linearLayout, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		mPopupWindow.setTouchable(true);
		// 设置允许在外点击消失
		mPopupWindow.setOutsideTouchable(true);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		mPopupWindow.setBackgroundDrawable(new ColorDrawable(context
				.getResources().getColor(android.R.color.transparent)));
		mPopupWindow.showAsDropDown(tv_calendar);
	}

	/**
	 * 点击日历时间监听
	 */
	OnDateClickListener onDateClickListener = new OnDateClickListener() {

		@Override
		public void OnDateClick(String year, String month, String day,
				long selectDate) {
			mPopupWindow.dismiss();

		}
	};

	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long arg3) {
			Intent intent = new Intent(context, ActivitiyDetailsActivity1.class);
			intent.putExtra("id", activitiesList.get(position).getId());
			intent.putExtra("type", 1);// 1活动 2赛事
			intent.putExtra("classid",ClassId.会员活动_ID);//
			startActivity(intent);
		}
	};

	/**
	 * 获取活动数据
	 */
	private void getActivities() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "info/list");
		map.put("classid", ClassId.音乐活动_ID+"");
//		map.put("hd_history", "1");
		map.put("extra", "dizhi");
		map.put("pageSize", "10");
		map.put("pageIndex", page+"");
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData() != null
								&& !message.getData().isEmpty()) {
							List<Blog> mList = JSON.parseArray(
									message.getData(), Blog.class);
							activitiesList.addAll(mList);
							initContentAdapter();
						}
						pull_to_refresh.onHeaderRefreshComplete();
						pull_to_refresh.onFooterRefreshComplete();
						MyProgressDialog.Cancel();
					}

				}, null));
	}

	/**
	 * 加载ContentAdapter数据
	 */
	protected void initContentAdapter() {
		Parcelable listState = gv_content.onSaveInstanceState();
		if (activitiesList.size() > 0) {
			ActivitiesGridAdapter adapter = new ActivitiesGridAdapter(context,
					activitiesList);
			gv_content.setAdapter(adapter);
			gv_content.onRestoreInstanceState(listState);
		} else {
			toast(getResources().getString(R.string.cannot_load_more));
		}

	}
}
