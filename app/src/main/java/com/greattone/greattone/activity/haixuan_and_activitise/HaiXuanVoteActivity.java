package com.greattone.greattone.activity.haixuan_and_activitise;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.VoteFilterActivity;
import com.greattone.greattone.adapter.HaiXuanVoteList2Adapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.ActivityVideo;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**海选投票列表*/
public class HaiXuanVoteActivity extends BaseActivity {
	private int page = 1;
	List<ActivityVideo> activitiesList = new ArrayList<ActivityVideo>();
	private TextView tv_Division;

	private PullToRefreshView pull_to_refresh;

	private TextView tv_group;

	private ListView lv_content;
	String keyboard;
	private int history;
	private EditText et_search;
	private ImageView iv_search;
	private String hai_grouping;
	private String hai_grouping1;
	private String pclassid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_haixuan_vote);
		history=getIntent().getIntExtra("history", 0);
		pclassid= getIntent().getStringExtra("classid");
		initView();
	}

	/**
	 * 加载视图
	 */
	private void initView() {
		setHead(getResources().getString(R.string.海选投票), true, true);
//		setOtherText(getResources().getString(R.string.search), new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				startActivityForResult(getIntent().setClass(context, SearchAct.class), 0);
//			}
//		});
		if (pclassid.equals("32")) {
			setOtherText("筛选", new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					startActivityForResult(getIntent().setClass(context, VoteFilterActivity.class), 1);
				}
			});
		}
		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);//
		  findViewById(R.id.activity_musicteachert_radiogroup).setVisibility(View.GONE);;//
		tv_Division = (TextView) findViewById(R.id.tv_location);// 赛区
		tv_Division.setOnClickListener(lis);
		tv_group = (TextView) findViewById(R.id.tv_sort);// 组别
		tv_group.setOnClickListener(lis);
		et_search = (EditText) findViewById(R.id.et_search);// 搜索
		iv_search = (ImageView) findViewById(R.id.iv_search);// 搜索
		iv_search.setOnClickListener(lis);
		lv_content = (ListView) findViewById(R.id.lv_content);// 正文
		lv_content.setOnItemClickListener(listener);
		lv_content.setOnScrollListener(ImageLoaderUtil.getPauseOnScrollListener());
		addPullRefreshListener();
		getVideos(null);
		// getClassRoom2(FIRST_PAGE);

	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v == tv_Division) {// 赛区
			} else if (v == tv_group) {// 组别
			}else if (v==iv_search) {//搜索
				keyboard=et_search.getText().toString().trim();
				et_search.setText("");
				page = 1;
				activitiesList.clear();
				getVideos(keyboard);
			}
		}
	};

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
						getVideos(keyboard);
					}
				});
		pull_to_refresh
				.setOnFooterRefreshListener(new OnFooterRefreshListener() {

					@Override
					public void onFooterRefresh(PullToRefreshView view) {
						page++;
						getVideos(keyboard);
					}
				});
	}

	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long arg3) {
			Intent intent = new Intent(context, VoteDetailsActivity.class);
			 intent.putExtra("classid", activitiesList.get(position).getClassid());
			 intent.putExtra("pclassid",pclassid);
			 intent.putExtra("id", activitiesList.get(position).getId());
			 intent.putExtra("title", getIntent().getStringExtra("title"));
			 intent.putExtra("history",history);
//			 intent.putExtra("type", 1);// 1活动 2赛事
			startActivity(intent);
		}
	};


	/**
	 * 获取活动数据
	 */
	private void getVideos(String keyboard) {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "info/hxvotelist");
		map.put("hai_id", getIntent().getStringExtra("id"));
		map.put("keyboard", keyboard);
		map.put("hai_grouping", hai_grouping);
		map.put("hai_grouping1", hai_grouping1);
		map.put("pageSize", "20");
		map.put("pageIndex", page + "");
		map.put("extra", "tou_num,hai_petition,hai_video,hai_name,hai_photo");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData() != null
								&& !message.getData().isEmpty()) {
							List<ActivityVideo> mList = JSON.parseArray(
									message.getData(), ActivityVideo.class);
							if (mList.size() > 0) {
								activitiesList.addAll(mList);
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
		Parcelable listState = lv_content.onSaveInstanceState();
		HaiXuanVoteList2Adapter adapter = new HaiXuanVoteList2Adapter(context,
				activitiesList);
		lv_content.setAdapter(adapter);
		lv_content.onRestoreInstanceState(listState);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult( requestCode,  resultCode,  data);
		if (requestCode==0&&resultCode==RESULT_OK) {
//			keyboard=data.getStringExtra("data");
//			page = 1;
//			activitiesList.clear();
//			getVideos(keyboard);
		}else if (requestCode==1&&resultCode==RESULT_OK) {
			hai_grouping=data.getStringExtra("filter1");
			hai_grouping1=data.getStringExtra("filter2");
			page = 1;
			activitiesList.clear();
			getVideos(keyboard);
		}
	}
}
