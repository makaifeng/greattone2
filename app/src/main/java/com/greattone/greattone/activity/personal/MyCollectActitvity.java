package com.greattone.greattone.activity.personal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.plaza.PlazaMusicDetailsActivity;
import com.greattone.greattone.adapter.MyCollectListAdapter;
import com.greattone.greattone.data.ClassId;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Collection;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;

/** 我的收藏 */
public class MyCollectActitvity extends BaseActivity {
	private PullToRefreshView pull_to_refresh;
	private ListView lv_content;
	List<Collection> blogsList = new ArrayList<Collection>();
	/** 页数 */
	private int page = 1;
	private int pageSize = 20;

	private MyCollectListAdapter adapter;
	private RadioGroup radiogroup;
	String classid = ClassId.音乐广场_视频_ID+"";
	int checkId =1;
	private RadioButton radioButton1;
	private RadioButton radioButton2;
	private RadioButton radioButton3;
	private RadioButton radioButton4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_post);
	
		initView();
	}

	private void initView() {
		setHead(getResources().getString(R.string.我的收藏), true, true);

//		tv_post = (TextView) findViewById(R.id.tv_head_other);// 发帖
//		tv_post.setVisibility(View.VISIBLE);
//		tv_post.setText("发帖");
//		tv_post.setTextSize(15);
//		tv_post.setOnClickListener(lis);
		String[] posts_type = getResources().getStringArray(R.array.posts_type);
		radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
		radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
		radioButton1.setText(posts_type[1]);//视频
		radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
		radioButton2.setText(posts_type[2]);//音乐
		radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
		radioButton3.setText(posts_type[3]);//图片
		radioButton4 = (RadioButton) findViewById(R.id.radioButton4);
		radioButton4.setVisibility(View.GONE);
		radiogroup.check(R.id.radioButton1);
		radiogroup.setOnCheckedChangeListener(listener);
		getMyPosts();
		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);//
		lv_content = (ListView) findViewById(R.id.lv_content);
		pull_to_refresh.setOnHeaderRefreshListener(headerRefreshListener);
		pull_to_refresh.setOnFooterRefreshListener(footerRefreshListener);
		lv_content.setOnItemClickListener(itemClickListener);
		adapter = new MyCollectListAdapter(context, blogsList,checkId);
		lv_content.setAdapter(adapter);
	}

	/** 获取我的收藏数据 */
	protected void getMyPosts() {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "fava/list");
		map.put("classid", classid);
		map.put("pageSize", pageSize+"");
		map.put("pageIndex", page+"");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData()!=null&&message.getData().startsWith("[")) {
							List<Collection> mList = JSON.parseArray(
									message.getData(), Collection.class);
							blogsList.addAll(mList);
							if (blogsList.size() == 0) {
								toast(getResources().getString(R.string.cannot_load_more));
							}
						}
						initContentAdapter();
							pull_to_refresh.onHeaderRefreshComplete();
							pull_to_refresh.onFooterRefreshComplete();
						// adapter.notifyDataSetChanged();
						MyProgressDialog.Cancel();
					}
				}, null));
	}

	OnCheckedChangeListener listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			blogsList.clear();
			switch (checkedId) {
			case R.id.radioButton1:
				classid = ClassId.音乐广场_视频_ID+"";
				checkId=1;
				getMyPosts();
				break;
			case R.id.radioButton2:
				classid =ClassId.音乐广场_音乐_ID+"";
				checkId=2;
				getMyPosts();
				break;
			case R.id.radioButton3:
				classid =ClassId.音乐广场_图片_ID+"";
				checkId=3;
				getMyPosts();
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
//			intent.putExtra("type", blogsList.get(position).getType());
//			intent.putExtra("videourl", blogsList.get(position).getVideourl());
			startActivity(intent);
		}
	};

	OnFooterRefreshListener footerRefreshListener = new OnFooterRefreshListener() {

		@Override
		public void onFooterRefresh(PullToRefreshView view) {
			page++;
			getMyPosts();
		}
	};
	OnHeaderRefreshListener headerRefreshListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
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
		adapter = new MyCollectListAdapter(context, blogsList,checkId);
		lv_content.setAdapter(adapter);
			lv_content.onRestoreInstanceState(listState);


	}

}
