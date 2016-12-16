package com.greattone.greattone.activity.haixuan_and_activitise;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseFragment;
import com.greattone.greattone.adapter.HaiXuanListAdapter;
import com.greattone.greattone.adapter.HaiXuanListAdapter2;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Blog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/** 各地海选列表*/
public class HaiXuanTypeFragment extends BaseFragment {
	private View rootView;
	private PullToRefreshView pull_to_refresh;
	private ListView lv_content;
	private String classid;
	/** 页数 */
	private int page = 1;
	/** 是否是历史回归 */
	private int history = 0;
	protected int hd_activity = 1;
	private boolean isInitView;
	private List<Blog> competitionList = new ArrayList<Blog>();
	 public HaiXuanTypeFragment(){
		super();
	}
	public void setData(String name, String classid,int history) {
		this.classid = classid;
		if (history==1) {
			this.history=history;
			this.hd_activity=0;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		competitionList.clear();
		classid = getArguments().getInt("classid")+"";
		history= getArguments().getInt("ishistory");
		if (history==1) {
			this.hd_activity=0;
		}
		getActivities();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.layout_list, container, false);// 关联布局文件
		initView();
		return rootView;
	}

	private void initView() {
		pull_to_refresh = (PullToRefreshView) rootView.findViewById(R.id.pull_to_refresh);//
		lv_content = (ListView) rootView.findViewById(R.id.lv_content);
		pull_to_refresh.setOnHeaderRefreshListener(headerRefreshListener);
		pull_to_refresh.setOnFooterRefreshListener(footerRefreshListener);
		lv_content.setOnItemClickListener(itemClickListener);
		isInitView = true;
		initContentAdapter();
	}

	/**
	 * 获取海选列表
	 */
	private void getActivities() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "info/list");
		map.put("classid", classid + "");
		map.put("extra", "dizhi");
		if (history == 1) {
			map.put("hd_history", history + "");
		}
		if (hd_activity == 1) {
			map.put("hd_activity", hd_activity + "");
		}
		map.put("pageSize", "10");
		map.put("pageIndex", page + "");
		addRequest(HttpUtil.httpConnectionByPost(context, map, new ResponseListener() {

			@Override
			public void setResponseHandle(Message2 message) {
				if (message.getData() != null && !message.getData().isEmpty()) {
					List<Blog> mList = JSON.parseArray(message.getData(), Blog.class);
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
			Parcelable listState = lv_content.onSaveInstanceState();
				lv_content.setAdapter(classid.equals("112") ?( new HaiXuanListAdapter2(context, competitionList))
						: (new HaiXuanListAdapter(context, competitionList)));
			lv_content.onRestoreInstanceState(listState);
		}
	}

	OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
			Intent intent = classid.equals("112") ? (new Intent(context, ActivitiyDetailsActivity2.class))
					: (new Intent(context, ActivitiyDetailsActivity1.class));
			intent.putExtra("id", competitionList.get(position).getId());
			intent.putExtra("classid", competitionList.get(position).getClassid());
			intent.putExtra("history", history);
			intent.putExtra("type", 2);// 1活动 2赛事
			startActivity(intent);
		}
	};
	OnFooterRefreshListener footerRefreshListener = new OnFooterRefreshListener() {

		@Override
		public void onFooterRefresh(PullToRefreshView view) {
			page++;
			getActivities();
		}
	};
	OnHeaderRefreshListener headerRefreshListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
			page = 1;
			competitionList.clear();
			getActivities();
		}
	};


}
