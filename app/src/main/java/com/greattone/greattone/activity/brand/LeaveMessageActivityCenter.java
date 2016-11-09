package com.greattone.greattone.activity.brand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.Listener.OnBtnItemClickListener;
import com.greattone.greattone.SwipeMenu.SwipeMenuListView;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.personal.SignActivity;
import com.greattone.greattone.adapter.LeaveMessageCenterListAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.ReplayDialog;
import com.greattone.greattone.entity.LeaveMessage;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
/**
 * 留言板
 * @author makaifeng
 *
 */
public class LeaveMessageActivityCenter extends BaseActivity {
	List<LeaveMessage> leavemessageList=new ArrayList<LeaveMessage>();
	String url;
	String editurl;
	private int page=1;
	String province;
	String city;
	String district;
	String keyboard;
	
	private PullToRefreshView pull_to_refresh;
	private SwipeMenuListView lv_content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_leave_message2);
		initView();
		getLeaveMessage();
	}

	private void initView() {
		setHead("留言板", true, true);
		// 渠道列表
		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);//
		lv_content = (SwipeMenuListView) findViewById(R.id.lv_content);// 正文
		lv_content.setOnItemClickListener(listener);
		lv_content.setOnScrollListener(ImageLoaderUtil
				.getPauseOnScrollListener());
		addPullRefreshListener();
//		MySwipeMenuCreator creator = new MySwipeMenuCreator(context);
//		creator.setTexts(new String[]{"回复"});
//		// set creator
//		lv_content.setMenuCreator(creator);
//		lv_content.setOnMenuItemClickListener(new OnMenuItemClickListener() {
//			
//			@Override
//			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
//				 switch (index) {
//			        case 0://回复
//			        	reply(position);
//			            break;
//			        }
//				return true;
//			}
//		});
	}



	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long arg3) {
			// Intent intent=new Intent(context, ClassRoomActivity.class);
			// intent.putExtra("id", classRoomList.get(position).getUserid());
			// startActivity(intent);
		}
	};
	OnBtnItemClickListener btnItemClickListener=new OnBtnItemClickListener() {
		
		@Override
		public void onItemClick(View v, int position) {
			if (Data.myinfo.getCked()==1) {//已认证
				reply(position);
			}else{//未认证
				startActivity(new Intent(context, SignActivity.class).putExtra("content", "留言回复功能仅向签约用户开放，在这里可以回复用户给您的留言，增强品牌与普通用户的互动性并扩大您品牌的知名度！"));
			}
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
	/**回复 
	 * @param position */
	protected void reply(final int position) {
		ReplayDialog localReplayDialog = new ReplayDialog(context);
		localReplayDialog.setSendText("回复");
		localReplayDialog.setText(leavemessageList.get(position).getReply());
		localReplayDialog.setListener(new ReplayDialog.OnReplayListener() {

			@Override
			public void OnReplay(final String text) {
				MyProgressDialog.show(context);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("api", "brand/reply");
				map.put("reply", text);
				map.put("titid",leavemessageList.get(position).getId());
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
										MyProgressDialog.Cancel();
									LeaveMessage lm = leavemessageList.get(position);
									lm.setReply(text);
										leavemessageList.set(position, lm);
										initContentAdapter();
									}

								}, null));
			}
		});
		localReplayDialog.show();
	}
	protected void initContentAdapter() {
		Parcelable listState = lv_content.onSaveInstanceState();
		LeaveMessageCenterListAdapter adapter = new LeaveMessageCenterListAdapter(context,
				leavemessageList,btnItemClickListener);
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
