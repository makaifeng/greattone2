package com.greattone.greattone.activity.brand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.SwipeMenu.ConnectWayCreator;
import com.greattone.greattone.SwipeMenu.SwipeMenu;
import com.greattone.greattone.SwipeMenu.SwipeMenuListView;
import com.greattone.greattone.SwipeMenu.SwipeMenuListView.OnMenuItemClickListener;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.adapter.ConnectWayListAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.ConnectWay;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
/**
 *个人中心的联系我们
 * @author makaifeng
 *
 */
public class ConnectWayActivityCenter extends BaseActivity {
	List<ConnectWay> leavemessageList=new ArrayList<ConnectWay>();
	private int page=1;
	
	private PullToRefreshView pull_to_refresh;
	private SwipeMenuListView lv_content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connect_way);
		initView();
	}

	private void initView() {
		setHead("联系我们", true, true);
		setOtherText("添加", new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(ConnectWayActivityCenter.this,AddConnectWayAct.class);
				if (mList.size()>0) {
						intent.putExtra("main", mList.get(0));
				}
			startActivity(intent);
			}
		});
		// 渠道列表
		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);//
		lv_content = (SwipeMenuListView) findViewById(R.id.lv_content);// 正文
		lv_content.setOnItemClickListener(listener);
		addPullRefreshListener();
		ConnectWayCreator creator = new ConnectWayCreator(context);
		lv_content.smoothCloseMenu();
		// set creator
		lv_content.setMenuCreator(creator);
		lv_content.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
				 switch (index) {
			        case 0://删除
			        	delect(position);
			            break;
			        }
				return true;
			}
		});
	}


	/**
	 * 删除
	 * @param position
	 */
	protected void delect(final int position) {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "post/ecms");
		map.put("enews", "MDelInfo");
		map.put("classid",  "118");
		map.put("id", leavemessageList.get(position).getId() );
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		((BaseActivity) context).addRequest(HttpUtil
				.httpConnectionByPost(context, map,
						new ResponseListener() {

							@Override
							public void setResponseHandle(
									Message2 message) {
								((BaseActivity) context)
										.toast(message.getInfo());
								leavemessageList.remove(position);
								initContentAdapter();
								MyProgressDialog.Cancel();
							}

						}, null));
	
	}



	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long arg3) {
		}
	};
	protected List<ConnectWay> mList;
	protected List<ConnectWay> sList;


	/**
	 * 获取留言板信息
	 */
	private void getLeaveMessage() {
		 MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "brand/brandconnnectwaylist");
		map.put("userid", getIntent().getStringExtra("userid"));
		map.put("pageSize", 20+"");
		map.put("pageIndex", page+"");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData()!=null&&message.getData().startsWith("{")) {
				
						 mList = JSON.parseArray(
									JSON.parseObject(message.getData()).getString("main"), ConnectWay.class);
							 sList = JSON.parseArray(
									JSON.parseObject(message.getData()).getString("branch"), ConnectWay.class);
//							if (sList.size() == 0) {
//								toast(getResources().getString(R.string.cannot_load_more));
//							}
							leavemessageList.addAll(mList);
							leavemessageList.addAll(sList);
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
		ConnectWayListAdapter adapter = new ConnectWayListAdapter(context,
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
	@Override
	protected void onResume() {
		super.onResume();
		page = 1;
		leavemessageList.clear();
		getLeaveMessage();
	}
}
