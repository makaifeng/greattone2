package com.greattone.greattone.activity.haixuan_and_activitise;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
import com.greattone.greattone.activity.plaza.EditCommentActivity;
import com.greattone.greattone.adapter.CommentDetailsAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Comment;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;
/**海选评论*/
public class HaiXuanCommentActivity extends BaseActivity {
	private int page = 1;
	List<Comment> commentList = new ArrayList<Comment>();

	private PullToRefreshView pull_to_refresh;

	private ListView lv_content;
String classid;
String id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_list);
		id=getIntent().getStringExtra("id");
		classid=getIntent().getStringExtra("classid");
		initView();
	}

	/**
	 * 加载视图
	 */
	private void initView() {
		setHead(getResources().getString(R.string.活动评论), true, true);
		setOtherText(getResources().getString(R.string.我要评论), new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, EditCommentActivity.class);
				intent.putExtra("id", id);
				intent.putExtra("classid", classid);
				startActivityForResult(intent, 0);
			}
		});
		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);//
		lv_content = (ListView) findViewById(R.id.lv_content);// 正文
		lv_content.setOnItemClickListener(listener);
		lv_content.setOnScrollListener(ImageLoaderUtil.getPauseOnScrollListener());
		addPullRefreshListener();
		getComments();
		// getClassRoom2(FIRST_PAGE);

	}

	// OnClickListener lis = new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// };


	/**
	 * 添加上下拉刷新功能的监听
	 */
	private void addPullRefreshListener() {
		pull_to_refresh
				.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {

					@Override
					public void onHeaderRefresh(PullToRefreshView view) {
						page = 1;
						commentList.clear();
						getComments();
					}
				});
		pull_to_refresh
				.setOnFooterRefreshListener(new OnFooterRefreshListener() {

					@Override
					public void onFooterRefresh(PullToRefreshView view) {
						page++;
						getComments();
					}
				});
	}

	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long arg3) {
			// Intent intent = new Intent(context,
			// ActivitiyDetailsActivity1.class);
			// intent.putExtra("id", activitiesList.get(position).getId());
			// intent.putExtra("type", 1);// 1活动 2赛事
			// startActivity(intent);
		}
	};

	/**
	 * 获取评论
	 */
	private void getComments() {
		MyProgressDialog.show(context);
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("api", "comment/list");
		map.put("classid", classid);
		map.put("id", id);
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
								List<Comment> mList = JSON.parseArray(
										message.getData(), Comment.class);
								if (mList.size() > 0) {
									commentList.addAll(mList);
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
		CommentDetailsAdapter adapter = new CommentDetailsAdapter(context,
				commentList);
		lv_content.setAdapter(adapter);
		lv_content.onRestoreInstanceState(listState);

	}
}
