package com.greattone.greattone.activity.personal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.Listener.OnBtnItemClickListener;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.EntryActivity;
import com.greattone.greattone.activity.haixuan_and_activitise.VoteDetailsActivity;
import com.greattone.greattone.activity.plaza.PlazaMusicDetailsActivity;
import com.greattone.greattone.adapter.MyPostListAdapter;
import com.greattone.greattone.data.ClassId;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Blog;
import com.greattone.greattone.entity.Cate;
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

/** 我的发帖 */
public class MyPostActitvity extends BaseActivity {
	private PullToRefreshView pull_to_refresh;
	private ListView lv_content;
	List<Blog> blogsList = new ArrayList<Blog>();
	/** 页数 */
	private int page = 1;

	private final int NO_PULL = 0;// 其他
	private final int PULL_DOWN = 1;// 下拉
	private final int PULL_UP = 2;// 上拉
	/**
	 * 加载数据时的状态 判断是其他，上拉还是下拉，NO_PULL其他，PULL_DOWN下拉，PULL_UP上拉
	 * */
	int state = NO_PULL;
	private MyPostListAdapter adapter;
	protected List<Cate> careList;
	private RadioGroup radiogroup;
	String classid = ClassId.音乐广场_ID + "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_post);
		try {
			initView();
			getMyPosts();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initView() {
		setHead(getResources().getString(R.string.我的发帖), true, true);

		setOtherText(getResources().getString(R.string.post), lis);// 发帖

		radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
		radiogroup.check(R.id.radioButton1);
		radiogroup.setOnCheckedChangeListener(listener);
		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);//
		lv_content = (ListView) findViewById(R.id.lv_content);
		pull_to_refresh.setOnHeaderRefreshListener(headerRefreshListener);
		pull_to_refresh.setOnFooterRefreshListener(footerRefreshListener);
		lv_content.setOnItemClickListener(itemClickListener);
		adapter = new MyPostListAdapter(context, blogsList);
		adapter.setOnBtnItemClickListener(btnItemClickListener);
		lv_content.setAdapter(adapter);
		lv_content.setOnScrollListener(ImageLoaderUtil.getPauseOnScrollListener());
	}

	/** 获取发帖数据 */
	protected void getMyPosts() {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "info/list");
		map.put("classid", classid);
		map.put("userid", Data.user.getUserid());
		map.put("ismember", "1");
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
							List<Blog> mList = JSON.parseArray(
									message.getData(), Blog.class);
							blogsList.addAll(mList);
						}
						initContentAdapter();
						// MyProgressDialog.Cancel(num, 2);
						pull_to_refresh.onHeaderRefreshComplete();
						pull_to_refresh.onFooterRefreshComplete();
						MyProgressDialog.Cancel();
					}

				}, null));
	}

	OnCheckedChangeListener listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.radioButton1:
				classid = ClassId.音乐广场_ID + "";
				break;
			case R.id.radioButton2:
				classid = ClassId.音乐广场_视频_ID + "";
				break;
			case R.id.radioButton3:
				classid = ClassId.音乐广场_音乐_ID + "";
				break;
			case R.id.radioButton4:
				classid = ClassId.音乐广场_图片_ID + "";
				break;

			default:
				break;
			}
			blogsList.clear();
			getMyPosts();
		}
	};

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_head_other:
					Intent intent = new Intent(context, EntryActivity.class);
					intent.putExtra("type",
							context.getResources().getString(R.string.post));//
					startActivity(intent);
				break;

			default:
				break;
			}

		}
	};
	//adapter内的按钮点击事件
	OnBtnItemClickListener btnItemClickListener=new OnBtnItemClickListener() {
		
		@Override
		public void onItemClick(View v, int position) {
			//删除
			blogsList.remove(position);
			initContentAdapter();
		}
	};
	OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long arg3) {
			if (blogsList.get(position).getCanvote() == 1) {//海选
				Intent intent = new Intent(context, VoteDetailsActivity.class);
				intent.putExtra("id", blogsList.get(position).getId() + "");
				intent.putExtra("classid",  blogsList.get(position).getClassid()+"");
				context.startActivity(intent);
			}else {
				Intent intent = new Intent(context, PlazaMusicDetailsActivity.class);
				intent.putExtra("id", blogsList.get(position).getId());
				intent.putExtra("title", "我的发帖");
				intent.putExtra("classid", blogsList.get(position).getClassid());
				intent.putExtra("videourl", blogsList.get(position).getShipin());
				startActivity(intent);
			}
		}
	};

	OnFooterRefreshListener footerRefreshListener = new OnFooterRefreshListener() {

		@Override
		public void onFooterRefresh(PullToRefreshView view) {
			state = PULL_UP;
			page++;
			getMyPosts();
		}
	};
	OnHeaderRefreshListener headerRefreshListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
			state = PULL_DOWN;
			page = 1;
			blogsList.clear();
			getMyPosts();
		}
	};

	/**
	 * 加载ContentAdapter数据
	 */
	protected void initContentAdapter() {
		Parcelable listState = lv_content.onSaveInstanceState();
		adapter.setList(blogsList);
		if (blogsList.size() > 0) {
			lv_content.onRestoreInstanceState(listState);
		} else {
			toast(getResources().getString(R.string.cannot_load_more));
		}

	}

}
