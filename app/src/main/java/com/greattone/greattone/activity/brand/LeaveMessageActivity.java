package com.greattone.greattone.activity.brand;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.greattone.greattone.adapter.LeaveMessageListAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.LeaveMessage;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;
/**
 * 留言板
 * @author makaifeng
 *
 */
public class LeaveMessageActivity extends BaseActivity {
	List<LeaveMessage> leavemessageList=new ArrayList<LeaveMessage>();
	String url;
	String editurl;
	private int page=1;
	String province;
	String city;
	String district;
	String keyboard;
	
	private PullToRefreshView pull_to_refresh;
	private ListView lv_content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_leave_message);
		initView();
		getLeaveMessage();
	}

	private void initView() {
		setHead("留言板", true, true);
		// 渠道列表
		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);//
		lv_content = (ListView) findViewById(R.id.lv_content);// 正文
		 findViewById(R.id.btn_sure).setOnClickListener(lis);// 
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
			case R.id.btn_sure:
				Intent intent=new Intent(context ,LeaveActcivity.class);
				intent.putExtra("userid", getIntent().getStringExtra("userid"));
				startActivity(intent);
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
	 * 获取留言板信息
	 */
	private void getLeaveMessage() {
		 MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "brand/leavemessagelist");
		map.put("userid", getIntent().getStringExtra("userid"));
		map.put("classid", 117+"");
		map.put("pageSize", 20+"");
		map.put("pageIndex", page+"");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData()!=null&&message.getData().startsWith("[")) {
							List<LeaveMessage> mList = JSON.parseArray(
									message.getData(), LeaveMessage.class);
							if (mList.size() == 0) {
								toast(getResources().getString(R.string.cannot_load_more));
							}
							leavemessageList.addAll(mList);
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
		LeaveMessageListAdapter adapter = new LeaveMessageListAdapter(context,
				leavemessageList);
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
						leavemessageList.clear();
						getLeaveMessage();
					}
				});
		pull_to_refresh
				.setOnFooterRefreshListener(new OnFooterRefreshListener() {

					@Override
					public void onFooterRefresh(PullToRefreshView view) {
						page++;
						getLeaveMessage();
					}
				});
	}
}
