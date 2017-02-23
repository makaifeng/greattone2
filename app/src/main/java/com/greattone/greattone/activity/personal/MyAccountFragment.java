package com.greattone.greattone.activity.personal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseFragment;
import com.greattone.greattone.adapter.MyAccountListAdapter;
import com.greattone.greattone.adapter.MyOrderAdapter2;
import com.greattone.greattone.allpay.Config;
import com.greattone.greattone.allpay.PaymentActivity;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.Order;
import com.greattone.greattone.entity.Record;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/** 我的账户 */
public class MyAccountFragment extends BaseFragment {
	/**
	 * fragment 主布局
	 */
	private View rootView;
	protected List<Order> orderList = new ArrayList<Order>();
	protected List<Record> recordList = new ArrayList<Record>();
	private RadioGroup radiogroup;
	private String balance;
	// private String url = HttpConstants.CENTRE_STATISTICS_URL;
	/** 页数 */
	private int page = 1;

	private PullToRefreshView pull_to_refresh;
	private ListView lv_content;
	private TextView tv_withdraw;
	private ListAdapter adapter;
	private TextView tv_cash;
	
	String checked;
	String outproduct;
	String haveprice;
	String keyboard;
	String starttime;
	String endtime;
	String sear="1";
//	String type="课程";
	boolean isPresent;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		isPresent=getArguments().getBoolean("isPresent");
		balance=getArguments().getString("money");
		getData();
	}
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		try {
			rootView = inflater.inflate(R.layout.adapter_my_account, container,
                    false);// 关联布局文件
			// screenWidth = ((BaseActivity) getActivity()).screenWidth;
			initView();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rootView;
	}

	private void initView() {


		tv_withdraw = (TextView) rootView.findViewById(R.id.tv_withdraw);
		tv_withdraw.setOnClickListener(lis);
		tv_cash = (TextView) rootView.findViewById(R.id.tv_cash);
		tv_cash.setText("￥"+balance);

		pull_to_refresh = (PullToRefreshView) rootView.findViewById(R.id.pull_to_refresh);//
		lv_content = (ListView) rootView.findViewById(R.id.lv_content);
		pull_to_refresh.setOnHeaderRefreshListener(headerRefreshListener);
		pull_to_refresh.setOnFooterRefreshListener(footerRefreshListener);
		adapter = new MyAccountListAdapter(context, recordList);
		lv_content.setAdapter(adapter);
	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if ((TextUtils.isEmpty(balance))
					|| (Float.parseFloat(balance) <= 0.0F)) {
				toast(getResources().getString(R.string.提现金额不足));
				return;
			}
			Intent intent = new Intent(context, ApplycenterActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("price", balance);
			intent.putExtra("data", bundle);
			startActivity(intent);
		}
	};

	private OnCheckedChangeListener listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.radioButton1:// 收入记录
				page = 1;
				recordList.clear();
				isPresent=false;
				getData();
				
				break;
			case R.id.radioButton2:// 提现记录
				isPresent=true;
				page = 1;
				recordList.clear();
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
			page = 1;
			recordList.clear();
			orderList.clear();
			getData();
		}
	};

	private void getData() {
		if (isPresent) {//提现
			getPresentRecord();
		}else {//收入
			getIncomeRecord();
		}
	}
	/** 获取我的提现记录 */
	private void getPresentRecord() {
		MyProgressDialog.show(context);
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("api","QA/withdraw/record");
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
							List<Record> mList = JSON.parseArray(
									message.getData(), Record.class);
							if (mList.size() > 0) {
								recordList.addAll(mList);
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

	/** 获取我的收入记录 */
	protected void getIncomeRecord() {
		MyProgressDialog.show(context);
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("api","order/list");
//		map.put("sear", sear);// 默认空，是否进行刷选，如果为真，checked、outproduct、haveprice必须同时传值
		map.put("pageSize", 20 + "");
//		map.put("laiyuan", type);// 分类
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
								orderList.addAll(mList);
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

	/**
	 * 加载ContentAdapter数据
	 */
	protected void initContentAdapter() {
		Parcelable listState = lv_content.onSaveInstanceState();
//		if (recordList.size() > 0) {
		if (isPresent) {//提现
			adapter = new MyAccountListAdapter(context, recordList);
			lv_content.setAdapter(adapter);
		}else {//收入
			adapter = new MyOrderAdapter2(context, orderList,
					R.layout.adapter_center_my_account);
			lv_content.setAdapter(adapter);
		}
			lv_content.onRestoreInstanceState(listState);
//		} else {
//			toast(getResources().getString(R.string.cannot_load_more));
//		}

	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == Config.REQUEST_CODE){
			page = 1;
			recordList.clear();
			orderList.clear();
			getData();
			if(resultCode == PaymentActivity.RESULT_EXTRAS_NULL){
            	Log.d(Config.LOGTAG, "EXTRA_PAYMENT is NULL ");
            }else if (resultCode == PaymentActivity.RESULT_EXTRAS_CANCEL) {
                Log.d(Config.LOGTAG, "The user canceled.");
            } 
		}
					
	}
}
