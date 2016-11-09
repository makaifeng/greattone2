package com.greattone.greattone.activity.plaza;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.WebActivity;
import com.greattone.greattone.adapter.InterviewAdapter;
import com.greattone.greattone.data.ClassId;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Blog;
import com.greattone.greattone.entity.ImageData;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.FileUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ErrorResponseListener;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;

/** 音乐新闻 专访 */
public class InterviewActivity extends BaseActivity {
	private ListView lv_content;
	private PullToRefreshView pull_to_refresh;
	private int page = 1;
	private InterviewAdapter adapter;
	protected List<ImageData> imageUrlList;
	protected List<Blog> newsList=new ArrayList<Blog>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_interview);
		initView();
		getData();
		getAd();
	}


	private void initView() {
		setHead(getResources().getString(R.string.音乐新闻), true, true);

		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);
		lv_content = (ListView) findViewById(R.id.lv_content);
		adapter = new InterviewAdapter(context, newsList,imageUrlList,1);
		lv_content.setAdapter(this.adapter);
		lv_content.setOnItemClickListener(listener);
		pull_to_refresh.setOnHeaderRefreshListener(headerRefreshListener);
		pull_to_refresh.setOnFooterRefreshListener(footerRefreshListener);
	}
	/**
	 * 获取广告
	 */
	private void getAd() {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "extend/getAdvList");
		map.put("classid",16+"");
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {
					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData() != null
								&& !TextUtils.isEmpty(message.getData())) {
							imageUrlList=JSON.parseArray(message.getData(), ImageData.class);
						}
						getData();
					}
				}, new ErrorResponseListener() {
					
					@Override
					public void setServerErrorResponseHandle(Message2 message) {
						getData();
						
					}
					
					@Override
					public void setErrorResponseHandle(VolleyError error) {
						getData();
						
					}
				}));
	
	}
	private void getData() {
		MyProgressDialog.show(context);

			HashMap<String, String> map = new HashMap<String, String>();
			map.put("api", "info/list");
			map.put("classid", ClassId.音乐新闻_ID+"");
			map.put("pageIndex", page+"");
			addRequest(HttpUtil.httpConnectionByPost(context, map,
					new ResponseListener() {
				
				
				@Override
				public void setResponseHandle(Message2 message) {
					if (message.getData()!=null&&!message.getData().isEmpty()) {
						List<Blog> mList 		 = JSON.parseArray(
								message.getData(), Blog.class);
						if (mList != null && mList.size() > 0) {
							newsList.addAll(mList);
							adapter = new InterviewAdapter(context, newsList,imageUrlList,1);
							lv_content.setAdapter(adapter);
						} else {
							toast(getResources().getString(R.string.cannot_load_more));
						}
					}else{
						toast(getResources().getString(R.string.cannot_load_more));
					}
					pull_to_refresh.onHeaderRefreshComplete();
					pull_to_refresh.onFooterRefreshComplete();
					MyProgressDialog.Cancel();
				}
				
			},null));

	}
	private OnHeaderRefreshListener headerRefreshListener=new OnHeaderRefreshListener() {
		
		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
			page=1;
			newsList.clear();
			getData();
		}
	};
	private OnFooterRefreshListener footerRefreshListener=new OnFooterRefreshListener() {
		
		@Override
		public void onFooterRefresh(PullToRefreshView view) {
			page++;
			getData();
		}
	};
	OnItemClickListener listener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
		    Intent intent = new Intent(context, WebActivity.class);
		    intent.putExtra("title", getResources().getString(R.string.新闻详情));
		    intent.putExtra("shareTitle",newsList.get(position).getTitle());
		    intent.putExtra("shareContent", newsList.get(position).getSmalltext());
		    intent.putExtra("sharePic", newsList.get(position).getThumbnail());
	        intent.putExtra("urlPath", FileUtil.getNewsH5Url(newsList.get(position).getClassid()+"", newsList.get(position).getId()+""));
			intent.putExtra("id", newsList.get(position).getId()+"");
			intent.putExtra("classid", newsList.get(position).getClassid()+"");
			intent.putExtra("action", "functionForNews");//调用回复方法
	        InterviewActivity.this.startActivity(intent);
		}
	};
}
