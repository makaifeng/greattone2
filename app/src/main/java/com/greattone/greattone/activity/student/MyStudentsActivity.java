package com.greattone.greattone.activity.student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.Listener.OnBtnItemClickListener;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.celebrity.CelebrityActivity;
import com.greattone.greattone.adapter.MyTescherListAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.PersonList;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.PullToRefreshView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
/**学生管理*/
public class MyStudentsActivity extends BaseActivity {
	protected List<PersonList> dataList = new ArrayList<PersonList>();
	private MyTescherListAdapter adapter;
	private RadioGroup radiogroup;
	private RadioButton radioButton1;
	private RadioButton radioButton2;
	private RadioButton radioButton3;
	private RadioButton radioButton4;
	private PullToRefreshView pull_to_refresh;
	private ListView lv_content;
	/** 页数 */
	private int page = 1;
	private int pagesize = 20;

//	private String key ;
//	private String groupid ;
//	private String isrec = "";
	private 	String api = "my/invite/list";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_post);
		initView();
		getData(true);
	}

	/***/
	protected void getData(Boolean isShowDialog) {
		if (isShowDialog) {
			MyProgressDialog.show(context);
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", api);
//		map.put("groupid", groupid + "");
		map.put("pageIndex", page + "");
		map.put("pageSize", pagesize + "");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData() != null
								&& message.getData().startsWith("[")) {
							List<PersonList> mList = JSON.parseArray(
									message.getData(), PersonList.class);
							if (mList != null && !mList.isEmpty()) {
								dataList.addAll(mList);
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



	private void initView() {
		setHead(getResources().getString(R.string.学生管理), true, true);
		radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
		radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
		radioButton1.setText(getResources().getString(R.string.全部学生));
		radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
		radioButton2.setText(getResources().getString(R.string.等待列表));
		radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
		radioButton3.setText(getResources().getString(R.string.邀请列表));
		radioButton4 = (RadioButton) findViewById(R.id.radioButton4);
		radioButton4.setVisibility(View.GONE);
		radiogroup.check(R.id.radioButton1);
		radiogroup.setOnCheckedChangeListener(listener);
		
		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);//
		lv_content = (ListView) findViewById(R.id.lv_content);
		adapter = new MyTescherListAdapter(context, dataList,"邀请");
		adapter.setOnBtnItemClicklistener(btnItemClickListener);
		lv_content.setAdapter(adapter);
		lv_content.setOnItemClickListener(itemClickListener);
	}

	OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent=new Intent(context	, CelebrityActivity.class);
			intent.putExtra("id", dataList.get(position).getUserid());
			intent.putExtra("groupid", 0);
			startActivity(intent);
		}
	};

	OnCheckedChangeListener listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			dataList.clear();
			switch (checkedId) {
			case R.id.radioButton1:
				api = "my/invite/list";
				pagesize = 15;
				getData(true);
				break;
			case R.id.radioButton2:
				pagesize = 200;
				api = "my/invite/wait";
				getData(true);
				break;
			case R.id.radioButton3:
				pagesize = 20;
				api = "my/invite/reply";
				getData(true);
				break;

			default:
				break;
			}

		}
	};
	OnBtnItemClickListener btnItemClickListener = new OnBtnItemClickListener() {

		@Override
		public void onItemClick(View v, int position) {
			reply(position);
		}
	};
	protected void reply(int position) {
		MyProgressDialog.show(context);
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("api", "my/invite/doInvite");
		map.put("userid", dataList.get(position).getUserid());
//		map.put("groupid", groupid+"");
		map.put("action", dataList.get(position).getStatus());
		map.put("pageindex", 1 + "");
		map.put("pagesize", 20 + "");
		map.put("logintoken", Data.user.getToken());
		map.put("loginuid", Data.user.getUserid());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						dataList.clear();
						getData(false);
					}
				}, null));

	}
	private void initContentAdapter() {
		Parcelable listState = lv_content.onSaveInstanceState();
		lv_content.setAdapter(adapter);
		lv_content.onRestoreInstanceState(listState);
	}

}
