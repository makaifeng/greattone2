package com.greattone.greattone.activity.haixuan_and_activitise;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.Listener.OnSelectCityListener;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseFragment;
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
import com.kf_test.kfcalendar.CalendarListener.OnDateClickListener;
import com.kf_test.kfcalendar.KFCalendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 活动
 */
public class ActivitiesFragment extends BaseFragment {
	/**
	 * fragment 主布局
	 */
	private View rootView;

	private int page = 1;
	List<Blog> activitiesList = new ArrayList<Blog>();
	private TextView tv_location;

	private PullToRefreshView pull_to_refresh;

	private TextView tv_calendar;

	private GridView gv_content;
	String province;
	String city;
	String date;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_activities, container,
				false);// 关联布局文件
		// screenWidth = ((BaseActivity) getActivity()).screenWidth;
		initView();
		return rootView;
	}

	/**
	 * 加载视图
	 */
	private void initView() {
		pull_to_refresh = (PullToRefreshView) rootView
				.findViewById(R.id.pull_to_refresh);//
		tv_location = (TextView) rootView.findViewById(R.id.tv_location);// 位置筛选
		tv_location.setOnClickListener(lis);
		tv_calendar = (TextView) rootView.findViewById(R.id.tv_calendar);// 日历
		tv_calendar.setOnClickListener(lis);
		gv_content = (GridView) rootView.findViewById(R.id.gv_content);// 正文
		gv_content.setOnItemClickListener(listener);
		addPullRefreshListener();
		getActivities();
		// getClassRoom2(FIRST_PAGE);

	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v == tv_location) {//城市
				SelectCityPopwindow.build(context)
				.setOnSelectCityListener(new OnSelectCityListener() {

					@Override
					public void ClickSure(String province, String city,
							String district) {
						ActivitiesFragment.this.province = province;
						ActivitiesFragment.this.city = city;
						MyProgressDialog.show(context);
						activitiesList.clear();
						getActivities();
					}
				}).show(tv_location);
			} else if (v == tv_calendar) {//时间
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
			page=1;
			activitiesList.clear();
			date=year+"-"+month+"-"+day;
			mPopupWindow.dismiss();
			MyProgressDialog.show(context);
			getActivities();
			toast(date);
		}
	};

	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long arg3) {
			Intent intent = new Intent(context, ActivitiyDetailsActivity1.class);
			intent.putExtra("id", activitiesList.get(position).getId());
			intent.putExtra("type", 1);// 1活动 2赛事
			if (activitiesList.get(position).getIsend()==1) {
				intent.putExtra("history", 1);
			}
			intent.putExtra("classid",ClassId.会员活动_ID);//
			startActivity(intent);
		}
	};

	/**
	 * 获取活动数据
	 */
	private void getActivities() {
		// MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "info/list");
		map.put("classid", ClassId.音乐活动_ID+"");
		map.put("extra", "dizhi");
		map.put("address", province);
		map.put("address1", city);
		map.put("hd_datetime", date);
		map.put("pageSize", "10");
		map.put("pageIndex",page+"");
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData() != null
								&& !message.getData().isEmpty()) {
							List<Blog> mList = JSON.parseArray(
									message.getData(), Blog.class);
							activitiesList.addAll(mList);
							if (mList.size() > 0) {
							} else {
								toast(getResources().getString(R.string.cannot_load_more));
							}
						}
						initContentAdapter();
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
			ActivitiesGridAdapter adapter = new ActivitiesGridAdapter(
					getActivity(), activitiesList);
			gv_content.setAdapter(adapter);
			gv_content.onRestoreInstanceState(listState);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
