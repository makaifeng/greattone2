package com.greattone.greattone.activity.brand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import com.greattone.greattone.adapter.SalesChannelsListAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.SalesChannels;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;
/**
 * 销售渠道
 * @author makaifeng
 *
 */
public class SalesChannelsActivity extends BaseActivity {

	List<SalesChannels> SalesChannelsList=new ArrayList<SalesChannels>();
	String url;
	String editurl;
	private int page=1;
	String province;
	String city;
	String district;
	String keyboard;
	
	// 渠道列表
	private PullToRefreshView pull_to_refresh;
	private ListView lv_content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sales_channels);
		initView();
		getSalesChannels();
	}

	private void initView() {
		setHead("销售渠道", true, true);
		// 渠道列表
		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);//
		lv_content = (ListView) findViewById(R.id.lv_content);// 正文
		lv_content.setOnItemClickListener(listener);
		lv_content.setOnScrollListener(ImageLoaderUtil
				.getPauseOnScrollListener());
		addPullRefreshListener();

//		initViewData();
	}

//	protected void initViewData() {
//		m_name.setText(useMsg.getUsername());
//		m_company.setText(useMsg.getCompany());
//		m_mobile.setText(useMsg.getTelephone());
//		m_year.setText(useMsg.getChusheng());
//		m_address.setText(useMsg.getAddres());
//		m_descr.setText(useMsg.getSaytext());
//	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			default:
				break;
			}
		}
	};

	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long arg3) {
			// Intent intent=new Intent(context, ClassRoomActivity.class);
			// intent.putExtra("id", classRoomList.get(position).getUserid());
			// startActivity(intent);
		}
	};


	/**
	 * 获取销售渠道
	 */
	private void getSalesChannels() {
		 MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "brand/saleschannelslist");
		map.put("address", province);
		map.put("address1", city);
		map.put("address2", district);
		map.put("keyboard", keyboard);
		map.put("userid", getIntent().getStringExtra("userid"));
		map.put("classid", 116+"");
		map.put("pageSize", 20+"");
		map.put("pageIndex", page+"");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData()!=null&&message.getData().startsWith("[")) {
							List<SalesChannels> mList = JSON.parseArray(
									message.getData(), SalesChannels.class);
							if (mList.size() == 0) {
								toast(getResources().getString(R.string.cannot_load_more));
							}
							SalesChannelsList.addAll(mList);
						} else {
							toast(getResources().getString(R.string.cannot_load_more));
						}
						pull_to_refresh.onHeaderRefreshComplete();
						pull_to_refresh.onFooterRefreshComplete();
							initContentAdapter();
						MyProgressDialog.Cancel();
					}
				}, null));
	}

	protected void initContentAdapter() {
		Parcelable listState = lv_content.onSaveInstanceState();
		SalesChannelsListAdapter adapter = new SalesChannelsListAdapter(context,
				SalesChannelsList);
		lv_content.setAdapter(adapter);
		lv_content.onRestoreInstanceState(listState);
	}

	/**
	 * 添加上下拉刷新功能的监听
	 */
	private void addPullRefreshListener() {
		pull_to_refresh
				.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {

					@Override
					public void onHeaderRefresh(PullToRefreshView view) {
						page = 1;
						SalesChannelsList.clear();
						getSalesChannels();
					}
				});
		pull_to_refresh
				.setOnFooterRefreshListener(new OnFooterRefreshListener() {

					@Override
					public void onFooterRefresh(PullToRefreshView view) {
						page++;
						getSalesChannels();
					}
				});
	}
}
