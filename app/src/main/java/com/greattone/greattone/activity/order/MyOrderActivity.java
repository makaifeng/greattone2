package com.greattone.greattone.activity.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.SwipeMenu.SwipeMenuListView;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.adapter.Adapter;
import com.greattone.greattone.adapter.MyOrderAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.data.HttpConstants;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.NormalPopuWindow;
import com.greattone.greattone.dialog.NormalPopuWindow.OnItemClickBack;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.Order;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;

public class MyOrderActivity extends BaseActivity {
	List<Order> qaList = new ArrayList<Order>();
	private PullToRefreshView pull_to_refresh;
	private SwipeMenuListView lv_content;
	String url = HttpConstants.CENTRE_MY_FAQS_URL;
	/** 页数 */
	private int page = 1;
	/**
	 * 加载数据时的状态 判断是其他，上拉还是下拉，NO_PULL其他，PULL_DOWN下拉，PULL_UP上拉
	 * */
	private Adapter<Order> adapter;
	private TextView tv_order;
	private TextView tv_filter;
	String checked;
	String outproduct;
	String haveprice;
	String keyboard;
	String starttime;
	String endtime;
	String sear="1";
	String type=getResources().getString(R.string.课程);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_activity);
		initView();
		getData();
	}

	private void initView() {
		setHead(getResources().getString(R.string.我的订单), true, true);
	 findViewById(R.id.ll_title).setVisibility(View.VISIBLE);
		
		tv_order = (TextView) findViewById(R.id.tv_my_activity);
		tv_order.setText(getResources().getString(R.string.课程订单));
		tv_filter = (TextView) findViewById(R.id.tv_post_activity);
		tv_filter.setText(getResources().getString(R.string.intelligent_sorting));//智能排序
		tv_order.setOnClickListener(lis);
		tv_filter.setOnClickListener(lis);
		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);//
		lv_content = (SwipeMenuListView) findViewById(R.id.lv_content);
		pull_to_refresh.setOnHeaderRefreshListener(headerRefreshListener);
		pull_to_refresh.setOnFooterRefreshListener(footerRefreshListener);

		adapter = new MyOrderAdapter(context, qaList,
				R.layout.adapter_qa_order);
		lv_content.setAdapter(adapter);
		lv_content.setOnItemClickListener(itemClickListener);
	}
	OnClickListener lis=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (v==tv_order) {//课程订单
				selectType();
			}else {//智能排序
				filter();
			}
		}
	};


	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long arg3) {
			// if (url == HttpConstants.CENTRE_MY_FAQS_URL) {
			// Intent localIntent = new Intent(context, ProblemDetails.class);
			// localIntent.putExtra("state",
			// Integer.parseInt(qaList.get(position).getStatus()));
			// localIntent.putExtra("type", 1);
			// localIntent.putExtra("id", qaList.get(position).getId());
			// context.startActivity(localIntent);
			// } else if (url == HttpConstants.CENTRE_OBTAIN_QUIZ_URL) {
			// Intent localIntent = new Intent(context, ProblemDetails.class);
			// localIntent.putExtra("state",
			// Integer.parseInt(qaList.get(position).getStatus()));
			// localIntent.putExtra("type", 0);
			// localIntent.putExtra("id", qaList.get(position).getId());
			// startActivity(localIntent);
			// } else if (url == HttpConstants.CENTRE_QA_ORDER_URL) {
			// Intent localIntent = new Intent(context, QAOrderDetails.class);
			// localIntent.putExtra("id", qaList.get(position).getId());
			// localIntent.putExtra("type", 0);
			// context.startActivity(localIntent);
			// }
		}
	};


	/** 获取数据 */
	private void getData() {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "order/list");
		map.put("sear", sear);// 默认空，是否进行刷选，如果为真，checked、outproduct、haveprice必须同时传值
		map.put("pageSize", 20 + "");
		map.put("laiyuan", type);// 分类
		map.put("starttime", starttime);// 开始时间
		map.put("endtime", endtime);// 结束时间
		map.put("checked", checked);// 1：已确认,2：取消，3：退货
		map.put("outproduct", outproduct);// 1：已发货，2：备货，0：未发货
		map.put("haveprice", haveprice);// 1：已付款，0：未付款
		map.put("pageIndex", page + "");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData() != null
								&& message.getData().startsWith("[")) {
							List<Order> mList = JSON.parseArray(
									message.getData(), Order.class);
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

	protected void filter() {
		// TODO Auto-generated method stub
		
	}

	protected void selectType() {
		List<String> mlList=new ArrayList<String>();
		mlList.add(getResources().getString(R.string.课程订单));
		mlList.add(getResources().getString(R.string.租赁订单));
		NormalPopuWindow popuWindow=new NormalPopuWindow(context, mlList, tv_order);
		popuWindow.setOnItemClickBack(new OnItemClickBack() {
			
			@Override
			public void OnClick(int position, String text) {
				switch (position) {
				case 0:
					type=getResources().getString(R.string.课程);
					break;
				case 1:
					type=getResources().getString(R.string.租赁);
					break;

				default:
					break;
				}
				getData();
			}
		});
		popuWindow.show();
		
	}
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
		adapter.notifyDataSetChanged();
		lv_content.onRestoreInstanceState(listState);
	}
}
