package com.greattone.greattone.activity.brand;

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

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.WebActivity;
import com.greattone.greattone.adapter.BrandNewsListAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.Product;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.FileUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;
/**
 * 品牌的新闻列表
 * @author makaifeng
 */
public class BrandNewsActivity extends BaseActivity {
	private PullToRefreshView pull_to_refresh;
	/** 页数 */
	private int page = 1;
	private int pageSize = 30;
	private boolean isInitView;
	private String name;
	private List<Product> productList = new ArrayList<Product>();
	private ListView lv_content;
	private int classid=115;
	private String userid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_list);
		userid=getIntent().getStringExtra("userid");
		initView();
		getNews();
	}
	/**
	 * 加载视图
	 */
	private void initView() {
		setHead("公司新闻", true, true);
		
		pull_to_refresh = (PullToRefreshView)findViewById(R.id.pull_to_refresh);//
		lv_content = (ListView) findViewById(R.id.lv_content);
		lv_content.setPadding(DisplayUtil.dip2px(context,5), DisplayUtil.dip2px(context, 10), DisplayUtil.dip2px(context,5), 0);
		lv_content.setClipToPadding(false);
		pull_to_refresh.setOnHeaderRefreshListener(headerRefreshListener);
		pull_to_refresh.setOnFooterRefreshListener(footerRefreshListener);
		lv_content.setOnItemClickListener(itemClickListener);
		isInitView = true;
		initContentAdapter();
	}
	
	/**
	 * 获取乐器品牌的新闻列表
	 */
	private void getNews() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "brand/brandnewslist");
		map.put("ismember", "1");
		map.put("userid", userid);
		map.put("type", name);
		map.put("classid", classid+"");
		map.put("pageIndex", page+"");
		map.put("pageSize",pageSize+"");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData()!=null&&message.getData().startsWith("[")) {
							List<Product> mList = JSON.parseArray(
									message.getData(), Product.class);
							if (mList.size() == 0) {
								toast(getResources().getString(R.string.cannot_load_more));
							}
							productList.addAll(mList);
						} else {
							toast(getResources().getString(R.string.cannot_load_more));
						}
						pull_to_refresh.onHeaderRefreshComplete();
						pull_to_refresh.onFooterRefreshComplete();
						if (isInitView) {
							initContentAdapter();
						}
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
			BrandNewsListAdapter adapter = new BrandNewsListAdapter(context,
					productList);
			lv_content.setAdapter(adapter);
			lv_content.onRestoreInstanceState(listState);
		}
	}

	OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long arg3) {
			Intent intent = new Intent(context,
					WebActivity.class);
		    intent.putExtra("title","公司新闻");
	        intent.putExtra("urlPath", FileUtil.getPinpaiNewsH5Url(productList.get(position).getClassid(),productList.get(position).getId()));
			startActivity(intent);
		}
	};
	OnFooterRefreshListener footerRefreshListener = new OnFooterRefreshListener() {

		@Override
		public void onFooterRefresh(PullToRefreshView view) {
			page++;
			getNews();
		}
	};
	OnHeaderRefreshListener headerRefreshListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
			page = 1;
			productList.clear();
			getNews();
		}
	};
}
