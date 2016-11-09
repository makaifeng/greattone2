package com.greattone.greattone.activity.classroom;

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
import com.greattone.greattone.activity.EntryActivity;
import com.greattone.greattone.activity.plaza.PlazaMusicDetailsActivity;
import com.greattone.greattone.adapter.MusicPlazaListAdapter;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Blog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;

/** 讨论区 */
public class TlqActivity extends BaseActivity {
	private PullToRefreshView pull_to_refresh;
	private ListView lv_content;
	List<Blog> blogsList = new ArrayList<Blog>();
	/** 页数 */
	private int page = 1;
	private int pageSize = 30;

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
		setHead("讨论区", true, true);//讨论区

setOtherText("发帖", lis);
		
		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);//
		lv_content = (ListView) findViewById(R.id.lv_content);
		pull_to_refresh.setOnHeaderRefreshListener(headerRefreshListener);
		pull_to_refresh.setOnFooterRefreshListener(footerRefreshListener);
		lv_content.setOnItemClickListener(itemClickListener);
		adapter = new MusicPlazaListAdapter(context, blogsList, null, 0);
		lv_content.setAdapter(adapter);
		lv_content.setOnScrollListener(ImageLoaderUtil.getPauseOnScrollListener());
	}

	/** 获取发帖数据 */
	protected void getBlogs() {
		MyProgressDialog.show(context);
		HttpProxyUtil.getTLList(context, getIntent().getStringExtra("userid"),"shipin", pageSize, page, 	new ResponseListener() {

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
			switch (v.getId()) {
			case R.id.tv_head_other:
				Intent intent = new Intent(context, EntryActivity.class);
				intent.putExtra("type", context.getResources().getString(R.string.post));
				startActivity(intent);
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
			lv_content.onRestoreInstanceState(listState);

	}
}
