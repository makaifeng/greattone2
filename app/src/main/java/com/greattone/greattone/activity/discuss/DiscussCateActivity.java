package com.greattone.greattone.activity.discuss;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioButton;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.WebActivity;
import com.greattone.greattone.adapter.DiscussListAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Discuss;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.FileUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**讨论专区列表*/
public class DiscussCateActivity extends BaseActivity {
	private PullToRefreshView pull_to_refresh;
	private RadioButton button1;
	private RadioButton button2;
	private RadioButton button3;
	private List<Discuss> discussList = new ArrayList<Discuss>();
	/**
	 * 当前加载的页数
	 */
	private int page = 1;
	private ListView lv_content;
	private DiscussListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_discuss);
		initView();
		getDiscussCate();
	}

	private void getDiscussCate() {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "info/list");
		map.put("classid", getIntent().getStringExtra("id") + "");
		map.put("pageSize", 20+ "");
		map.put("pageIndex", page + "");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData().startsWith("[")) {
						List<Discuss> mList = JSON.parseArray(
								message.getData(), Discuss.class);
						if (mList.size() > 0) {
							discussList.addAll(mList);
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

	private void initView() {
		setHead(getIntent().getStringExtra("title"), true, true);//讨论专区
		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);//
		lv_content = (ListView) findViewById(R.id.lv_content);//
		adapter = new DiscussListAdapter(context, discussList);
		lv_content.setAdapter(adapter);
		lv_content.setOnItemClickListener(listener);
		lv_content.setOnScrollListener(ImageLoaderUtil.getPauseOnScrollListener());
		this.button1 = ((RadioButton) findViewById(R.id.radioButton1));
		this.button1.setOnClickListener(lis);
		this.button1.setVisibility(View.GONE);
		this.button2 = ((RadioButton) findViewById(R.id.radioButton2));
		this.button2.setOnClickListener(lis);
		this.button2.setVisibility(View.GONE);
		this.button3 = ((RadioButton) findViewById(R.id.radioButton3));
		this.button3.setOnClickListener(lis);
		this.button3.setVisibility(View.GONE);
		pull_to_refresh
		.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {

			@Override
			public void onHeaderRefresh(PullToRefreshView view) {
				page = 1;
				discussList.clear();
				getDiscussCate();
			}
		});
pull_to_refresh
		.setOnFooterRefreshListener(new OnFooterRefreshListener() {

			@Override
			public void onFooterRefresh(PullToRefreshView view) {
				page++;
				getDiscussCate();
			}
		});
	}

	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapterview, View v,
				int position, long arg3) {
			Intent intent = new Intent(context, WebActivity.class);
			intent.putExtra("urlPath",
					FileUtil.getNewsH5Url(discussList.get(position).getClassid(), discussList.get(position).getId()));
			intent.putExtra("title", getResources().getString(R.string.讨论区详情));//讨论区详情
			intent.putExtra("id", discussList.get(position).getId());
			intent.putExtra("classid", discussList.get(position).getClassid());
			intent.putExtra("action", "replyPost");
			startActivity(intent);
		}
	};
	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.radioButton1:

				break;
			case R.id.radioButton2:

				break;
			case R.id.radioButton3:

				break;

			default:
				break;
			}
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
