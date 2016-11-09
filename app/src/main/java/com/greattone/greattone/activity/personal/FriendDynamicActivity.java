package com.greattone.greattone.activity.personal;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.plaza.PlazaMusicDetailsActivity;
import com.greattone.greattone.adapter.MusicPlazaListAdapter;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Blog;
import com.greattone.greattone.entity.ImageData;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;

/** 知音动态 */
public class FriendDynamicActivity extends BaseActivity {
	private PullToRefreshView pull_to_refresh;
	private ListView lv_content;
	List<Blog> blogsList = new ArrayList<Blog>();
	/** 页数 */
	private int page = 1;
	private int pageSize = 30;
	protected List<ImageData> imageUrlList;

	private MusicPlazaListAdapter adapter;
//	private TextView tv_post;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_list);
		initView();
		getBlogs();
	}

	private void initView() {
		setHead(getResources().getString(R.string.friend_dynamics), true, true);//知音动态

//		tv_post = (TextView) findViewById(R.id.tv_head_other);// 发帖
//		tv_post.setVisibility(View.VISIBLE);
//		tv_post.setText("发帖");
//		tv_post.setTextSize(15);
//		tv_post.setOnClickListener(lis);
		
		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);//
		lv_content = (ListView) findViewById(R.id.lv_content);
		pull_to_refresh.setOnHeaderRefreshListener(headerRefreshListener);
		pull_to_refresh.setOnFooterRefreshListener(footerRefreshListener);
		lv_content.setOnItemClickListener(itemClickListener);
		adapter = new MusicPlazaListAdapter(context, blogsList, imageUrlList, 0);
		lv_content.setAdapter(adapter);
	}
	/** 获取发帖数据 */
	protected void getBlogs() {
		MyProgressDialog.show(context);
		HttpProxyUtil.getFriendsBlogs(context, "shipin", pageSize, page, 	new ResponseListener() {

			@Override
			public void setResponseHandle(Message2 message) {
				if (message.getData()!=null&&message.getData().startsWith("[")) {
					List<Blog> mList = JSON.parseArray(
							message.getData(), Blog.class);
					if (mList.size() == 0) {
						toast(getResources().getString(R.string.cannot_load_more));
					} else {
						blogsList.addAll(mList);
					}
				} else {
					toast(getResources().getString(R.string.cannot_load_more));
				}
					pull_to_refresh.onHeaderRefreshComplete();
					pull_to_refresh.onFooterRefreshComplete();
				initContentAdapter();
				MyProgressDialog.Cancel();
			}

		}, null);
	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {

		}
	};
	OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long arg3) {
			Intent intent = new Intent(context, PlazaMusicDetailsActivity.class);
			intent.putExtra("id", blogsList.get(position).getId());
			intent.putExtra("classid", blogsList.get(position).getClassid());
			intent.putExtra("videourl", blogsList.get(position).getShipin());
			startActivity(intent);
		}
	};

	OnFooterRefreshListener footerRefreshListener = new OnFooterRefreshListener() {

		@Override
		public void onFooterRefresh(PullToRefreshView view) {
			page++;
			getBlogs();
		}
	};
	OnHeaderRefreshListener headerRefreshListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
			page = 1;
			blogsList.clear();
			getBlogs();
		}
	};

	/**
	 * 加载ContentAdapter数据
	 */
	protected void initContentAdapter() {
		Parcelable listState = lv_content.onSaveInstanceState();
		adapter.notifyDataSetChanged();
//		adapter = new MusicPlazaListAdapter(context, blogsList,imageUrlList,1);
//		lv_content.setAdapter(adapter);
			lv_content.onRestoreInstanceState(listState);

	}
}
