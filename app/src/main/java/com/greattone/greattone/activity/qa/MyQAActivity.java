package com.greattone.greattone.activity.qa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.SwipeMenu.MyDeleteSwipeMenuCreator;
import com.greattone.greattone.SwipeMenu.SwipeMenu;
import com.greattone.greattone.SwipeMenu.SwipeMenuListView;
import com.greattone.greattone.SwipeMenu.SwipeMenuListView.OnMenuItemClickListener;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.adapter.MyDraftAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.MyQA;
import com.greattone.greattone.entity.Order;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;

/** 我的QA */
public class MyQAActivity extends BaseActivity {
	List<MyQA> qaList = new ArrayList<MyQA>();
	List<Order> orderList = new ArrayList<Order>();
	private RadioGroup radiogroup;
	private PullToRefreshView pull_to_refresh;
	private SwipeMenuListView lv_content;
	/** 页数 */
	private int page = 1;
	private MyDraftAdapter adapter;
	String type = "myAsk";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_post);
		initView();
		getData();
	}

	private void initView() {
		setHead(getResources().getString(R.string.我的问答), true, true);

		radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
		((RadioButton) findViewById(R.id.radioButton1)).setText(getResources().getString(R.string.我的问题));
		((RadioButton) findViewById(R.id.radioButton2)).setText(getResources().getString(R.string.收到问题));
		((RadioButton) findViewById(R.id.radioButton3)).setText(getResources().getString(R.string.完成记录));
//		((RadioButton) findViewById(R.id.radioButton3)).setText("Q&A订单");
		if (Data.userData.getGroupid() == 2) {
			((RadioButton) findViewById(R.id.radioButton2))
					.setVisibility(View.GONE);
		}
		((RadioButton) findViewById(R.id.radioButton4))
				.setVisibility(View.GONE);
		if (getIntent().getIntExtra("num", 0)==1) {
			radiogroup.check(R.id.radioButton1);
		}else 	if (getIntent().getIntExtra("num", 0)==2) {
			radiogroup.check(R.id.radioButton2);
		}else 	if (getIntent().getIntExtra("num", 0)==3) {
			radiogroup.check(R.id.radioButton3);
		}else 	 {
			radiogroup.check(R.id.radioButton1);
		}
		radiogroup.setOnCheckedChangeListener(listener);

		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);//
		lv_content = (SwipeMenuListView) findViewById(R.id.lv_content);
		pull_to_refresh.setOnHeaderRefreshListener(headerRefreshListener);
		pull_to_refresh.setOnFooterRefreshListener(footerRefreshListener);

		adapter = new MyDraftAdapter(context, qaList,
				R.layout.adapter_my_problem);
		lv_content.setAdapter(adapter);
		lv_content.setOnItemClickListener(itemClickListener);
		lv_content.setOnScrollListener(ImageLoaderUtil.getPauseOnScrollListener());
		MyDeleteSwipeMenuCreator creator = new MyDeleteSwipeMenuCreator(context);
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
		map.put("api", "QA/delete");
		map.put("id", qaList.get(position).getId());
		map.put("classid", qaList.get(position).getClassid());
		map.put("mid", qaList.get(position).getMid());
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						MyProgressDialog.Cancel();
						qaList.remove(position);
						initContentAdapter();
					}
				}, null));
	}

	/** 获取数据 */
	private void getData() {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "QA/tj/ListInfo");
		map.put("type", type);
		map.put("pageSize", 20 + "");
		map.put("pageIndex", page + "");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData() != null
								&& message.getData().startsWith("[")) {
							List<MyQA> mList = JSON.parseArray(
									message.getData(), MyQA.class);
							if (mList.size() > 0) {
								qaList.addAll(mList);
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

//	/** 获取qa订单 */
//	private void getMyOrder() {
//		MyProgressDialog.show(context);
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("api", "order/list");
//		map.put("sear", "1");// 默认空，是否进行刷选，如果为真，checked、outproduct、haveprice必须同时传值
//		map.put("pageSize", 20 + "");
//		map.put("laiyuan", type);// 分类
//		map.put("pageIndex", page + "");
//		map.put("loginuid", Data.user.getUserid());
//		map.put("logintoken", Data.user.getToken());
//		addRequest(HttpUtil.httpConnectionByPost(context, map,
//				new ResponseListener() {
//
//					@Override
//					public void setResponseHandle(Message2 message) {
//						if (message.getData() != null
//								&& message.getData().startsWith("[")) {
//							List<Order> mList = JSON.parseArray(
//									message.getData(), Order.class);
//							if (mList.size() > 0) {
//								orderList.addAll(mList);
//							} else {
//								toast(getResources().getString(R.string.cannot_load_more));
//							}
//						}
//						pull_to_refresh.onHeaderRefreshComplete();
//						pull_to_refresh.onFooterRefreshComplete();
//						Parcelable listState = lv_content.onSaveInstanceState();
//						MyOrderAdapter adapter = new MyOrderAdapter(context,
//								orderList, R.layout.adapter_qa_order);
//						lv_content.setAdapter(adapter);
//						lv_content.onRestoreInstanceState(listState);
//						MyProgressDialog.Cancel();
//					}
//				}, null));
//	}

	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long arg3) {
			Intent intent = new Intent();
			if (type.equals("QA问答")) {// QA订单
				intent.setClass(context, QAOrderDetails.class);
				intent.putExtra("id", orderList.get(position).getDdid());
				intent.putExtra("type", 2);
			}else {
				intent.putExtra("id", qaList.get(position).getId());
				intent.putExtra("classid", qaList.get(position).getClassid());
				intent.putExtra("state", qaList.get(position).getQa_dingjia());
				intent.putExtra("cando", qaList.get(position).getCando());
				intent.putExtra("userpic", qaList.get(position).getUserpic());
				if (type.equals("myAsk")) {// 我的问题
					intent.setClass(context, ProblemDetails.class);
					intent.putExtra("type", 0);
				} else if (type.equals("receive")) {// 收到的问题
					intent.setClass(context, ProblemDetails.class);
					intent.putExtra("type", 1);
				} else if (type.equals("finished")) {//完成的问题
					intent.setClass(context, ProblemDetails.class);
					intent.putExtra("type", 2);
			}
			}
			((Activity)context).startActivityForResult(intent,1);
		}
	};
	private OnCheckedChangeListener listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			qaList.clear();
			page = 1;
			switch (checkedId) {
			case R.id.radioButton1:// 我的问题
				type = "myAsk";
				getData();
				break;
			case R.id.radioButton2:// 收到的问题
				type = "receive";
				getData();
				break;
			case R.id.radioButton3:// QA订单
				type = "finished";
				getData();
				break;

			default:
				break;
			}

		}
	};
	OnFooterRefreshListener footerRefreshListener = new OnFooterRefreshListener() {

		@Override
		public void onFooterRefresh(PullToRefreshView view) {
			page++;
			getData();
		}
	};
	OnHeaderRefreshListener headerRefreshListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
			qaList.clear();
			page = 1;
			getData();
		}
	};

	protected void initContentAdapter() {
		Parcelable listState = lv_content.onSaveInstanceState();
		adapter.setType(type);
		lv_content.setAdapter(adapter);
		lv_content.onRestoreInstanceState(listState);
	}
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0==1) {
			qaList.clear();
			page = 1;
			getData();
		}
	}
}
