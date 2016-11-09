package com.greattone.greattone.activity.haixuan_and_activitise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseFragment;
import com.greattone.greattone.activity.EntryActivity;
import com.greattone.greattone.activity.ListActivity;
import com.greattone.greattone.adapter.ActivitiesGridAdapter;
import com.greattone.greattone.data.ClassId;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Blog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;

/** 各地海选历史回顾  */
public class HaiXuanfragment3 extends BaseFragment {
	/**
	 * fragment 主布局
	 */
	private View rootView;
	private List<Blog> competitionList = new ArrayList<Blog>();
	private PullToRefreshView pull_to_refresh;
	/** 页数 */
	private int page = 1;
	/** 是否是历史回顾 */
	private int history = 1;
	protected int hd_activity=0;

	private final int NO_PULL = 0;// 其他
	private final int PULL_UP = 2;// 上拉
	private final int PULL_DOWN = 1;// 下拉
	/**
	 * 加载数据时的状态 判断是其他，上拉还是下拉，NO_PULL其他，PULL_DOWN下拉，PULL_UP上拉
	 * */
	int state = NO_PULL;
	private RadioGroup radiogroup;
	private GridView gv_content;
	private boolean isInitView;
	private String province;
	private String city;
	private TextView tv_filter;
	private int classid = ClassId.音乐海选_ID;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivities();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_competition2, container,
				false);// 关联布局文件
		// screenWidth = ((BaseActivity) getActivity()).screenWidth;
		initView();
		return rootView;
	}

	private void initView() {
		tv_filter = ((EntryActivity) context).tv_head_other;//
		tv_filter.setVisibility(View.VISIBLE);
		tv_filter.setText(getResources().getString(R.string.filter));//筛选
		tv_filter.setTextSize(15);
		tv_filter.setOnClickListener(lis);

		radiogroup = (RadioGroup) rootView.findViewById(R.id.radiogroup);//
		radiogroup.check(R.id.radioButton1);
		radiogroup.setOnCheckedChangeListener(checkedChangeListener);
		pull_to_refresh = (PullToRefreshView) rootView
				.findViewById(R.id.pull_to_refresh);//
		gv_content = (GridView) rootView.findViewById(R.id.gv_content);
		pull_to_refresh.setOnHeaderRefreshListener(headerRefreshListener);
		pull_to_refresh.setOnFooterRefreshListener(footerRefreshListener);
		gv_content.setOnItemClickListener(itemClickListener);
		isInitView = true;
		initContentAdapter();
	}

	OnCheckedChangeListener checkedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			state = NO_PULL;
			competitionList.clear();
			switch (checkedId) {
			case R.id.radioButton1:// 赛事
				page = 1;
				hd_activity=1;
				history = 0;
				getActivities();
				break;
			case R.id.radioButton2:// 历史回顾
				page = 1;
				hd_activity=0;
				history = 1;
				getActivities();
				break;

			default:
				break;
			}
		}
	};

	OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long arg3) {
			Intent intent = new Intent(context, ActivitiyDetailsActivity1.class);
			intent.putExtra("id", competitionList.get(position).getId());
			intent.putExtra("classid", competitionList.get(position)
					.getClassid());// 1活动 2赛事
			intent.putExtra("history", history);// 1活动 2赛事
			intent.putExtra("type", 2);// 1活动 2赛事
			startActivity(intent);
		}
	};
	OnFooterRefreshListener footerRefreshListener = new OnFooterRefreshListener() {

		@Override
		public void onFooterRefresh(PullToRefreshView view) {
			state = PULL_UP;
			page++;
			getActivities();
		}
	};
	OnHeaderRefreshListener headerRefreshListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
			state = PULL_DOWN;
			page = 1;
			competitionList.clear();
			getActivities();
		}
	};
	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v == tv_filter) {
				startActivityForResult(new Intent(context, ListActivity.class),
						0);
			}
		}
	};

	/**
	 * 获取海选列表
	 */
	private void getActivities() {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "info/list");
		map.put("classid", classid + "");
		map.put("extra", "dizhi");
		map.put("address", province);
		map.put("address1", city);
		if (history==1) {
			map.put("hd_history", history + "");
		}
		if (hd_activity==1) {
			map.put("hd_activity", hd_activity + "");
		}
		map.put("pageSize", "10");
		map.put("pageIndex", page + "");
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData() != null
								&& !message.getData().isEmpty()) {
							List<Blog> mList = JSON.parseArray(
									message.getData(), Blog.class);
							competitionList.addAll(mList);
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
		if (isInitView) {
			Parcelable listState = gv_content.onSaveInstanceState();
			ActivitiesGridAdapter adapter = new ActivitiesGridAdapter(context,
					competitionList);
			gv_content.setAdapter(adapter);
			gv_content.onRestoreInstanceState(listState);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
			classid = data.getIntExtra("classid", 0);
			page = 1;
			competitionList.clear();
			getActivities();
		}
	}
}
