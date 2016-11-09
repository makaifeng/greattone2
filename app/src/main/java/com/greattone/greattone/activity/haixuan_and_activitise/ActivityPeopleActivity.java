package com.greattone.greattone.activity.haixuan_and_activitise;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;

/** 活动人员 */
public class ActivityPeopleActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_list);
		initView();
	}

	private void initView() {
		setHead(getResources().getString(R.string.活动详情), true, true);
	}

//	/** 获取活动详情 */
//	private void getHaiXuan() {
//		MyProgressDialog.show(context);
//		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
//		map.put("api", "info/detail");
//		map.put("classid", getIntent().getIntExtra("classid", 0) + "");
//		map.put("id", getIntent().getIntExtra("id", 0) + "");
//		map.put("loginuid", Data.user.getUserid());
//		map.put("logintoken", Data.user.getToken());
//		addRequest(HttpUtil.httpConnectionByPost(context, map,
//				new ResponseListener() {
//
//					@Override
//					public void setResponseHandle(Message2 message) {
//						if (message.getData() != null
//								&& message.getData().startsWith("{")) {
//							haiXuan = JSON.parseObject(
//									JSON.parseObject(message.getData())
//											.getString("content"),
//									HaiXuan.class);
//							initViewData();
//						}
//						MyProgressDialog.Cancel();
//					}
//				}, null));
//	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
		}
	};

	/** 加载数据 */
	protected void initViewData() {
	}

}
