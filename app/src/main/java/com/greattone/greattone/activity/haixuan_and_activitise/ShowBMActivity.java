package com.greattone.greattone.activity.haixuan_and_activitise;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.SwipeMenu.SwipeMenuListView;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.adapter.Adapter;
import com.greattone.greattone.adapter.ViewHolder;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.ActivityBM;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;

public class ShowBMActivity extends BaseActivity {
	private SwipeMenuListView lv_content;
	private MyAdapter adapter;
	int size;
	private TextView tv_title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_bm);
		initView();
		getData();
	}

	private void initView() {
		setHead("查看报名", true, true);//查看报名
		
//		setOtherText("发布", lis);
		tv_title = (TextView) findViewById(R.id.tv_title);
		lv_content = (SwipeMenuListView) findViewById(R.id.lv_content);
		adapter = new MyAdapter(context, bmlist, R.layout.adapter_notice);
		lv_content.setAdapter(adapter);
//		lv_content.setOnItemClickListener(listener);
//		lv_content.setOnItemLongClickListener(onItemLongClickListener);
//		SwipeMenuCreator creator = new MySwipeMenuCreator(context);
//		// set creator
//		lv_content.setMenuCreator(creator);
//		lv_content.setOnMenuItemClickListener(new OnMenuItemClickListener() {
//			
//			@Override
//			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
//				 switch (index) {
//			        case 0://编辑
//			        	edit(position);
//			            break;
//			        case 1://删除
//			        	delect(position);
//			        	break;
//			        }
//				return true;
//			}
//		});
	}


	List<ActivityBM> bmlist = new ArrayList<ActivityBM>();

	private void getData() {
		MyProgressDialog.show(context);
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("api", "info/huodongbmlist");
		map.put("hai_id", getIntent().getStringExtra("id"));
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData() != null
								&& message.getData().startsWith("{")) {
							bmlist = JSON.parseArray( JSON.parseObject(message.getData()).getString("content"),
									ActivityBM.class);
							size=JSON.parseObject(message.getData()).getInteger("size");
							initViewData();
						}
						MyProgressDialog.Cancel();
					}
				}, null));
	}
	protected void initViewData() {
		tv_title.setText("当前已有 "+size+"人报名");
		Parcelable listState = lv_content.onSaveInstanceState();
		adapter.setList(bmlist);
		lv_content.onRestoreInstanceState(listState);
	}
	class MyAdapter extends Adapter<ActivityBM> {

		public MyAdapter(Context context, List<ActivityBM> list, int resId) {
			super(context, list, resId);
		}

		@Override
		public void getview(ViewHolder holder, int position, ActivityBM bm) {
			TextView tv_title = (TextView) holder.getView(R.id.tv_title);
			TextView tv_time = (TextView) holder.getView(R.id.tv_time);
			tv_title.setText(bm.getBmName());
			tv_time.setText(bm.getBmPhone());
		}
	}
	
}
