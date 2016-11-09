package com.greattone.greattone.activity.teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

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
import android.widget.TextView;

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
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;

public class MyTeacherActivity extends BaseActivity {
	protected List<PersonList> personList = new ArrayList<PersonList>();
	private RadioGroup radiogroup;
	private RadioButton radioButton1;
	private RadioButton radioButton2;
	private RadioButton radioButton3;
	private RadioButton radioButton4;
	private PullToRefreshView pull_to_refresh;
	private ListView lv_content;
	/** 页数 */
	private int page = 1;
	private int pageSize = 20;

	// private final int NO_PULL = 0;// 其他
	// private final int PULL_DOWN = 1;// 下拉
	// private final int PULL_UP = 2;// 上拉
	// /**
	// * 加载数据时的状态 判断是其他，上拉还是下拉，NO_PULL其他，PULL_DOWN下拉，PULL_UP上拉
	// * */
	// int state = NO_PULL;
	private MyTescherListAdapter adapter;
	private String type;
	private String api = "my/reply/jiaoshi_list";
	private String groupid;
	private TextView tv_hint;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_post);
		this.type = getIntent().getStringExtra("type");
		initView();
		getData(true);
	}

	private void initView() {
		radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
		radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
		radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
		radioButton2.setText(getResources().getString(R.string.等待列表));
		radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
		radioButton3.setText(getResources().getString(R.string.申请加入));
		// radioButton3.setVisibility(View.GONE);
		radioButton4 = (RadioButton) findViewById(R.id.radioButton4);
		radioButton4.setVisibility(View.GONE);
		radiogroup.check(R.id.radioButton1);
		radiogroup.setOnCheckedChangeListener(listener);

		tv_hint = (TextView) findViewById(R.id.tv_hint);//
		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);//
		lv_content = (ListView) findViewById(R.id.lv_content);
		lv_content.setOnScrollListener(ImageLoaderUtil.getPauseOnScrollListener());
		pull_to_refresh.setOnHeaderRefreshListener(headerRefreshListener);
		pull_to_refresh.setOnFooterRefreshListener(footerRefreshListener);
		adapter = new MyTescherListAdapter(context, personList,"申请");

		adapter.setOnBtnItemClicklistener(btnItemClickListener);
		lv_content.setAdapter(adapter);
		lv_content.setOnItemClickListener(itemClickListener);
		if (type.equals("room")) {
			groupid = "4";
			radioButton1.setText(getResources().getString(R.string.我的教室));
			setHead(getResources().getString(R.string.我的教室), true, true);

		} else if (type.equals("teacher")) {
			groupid = "3";
			setHead(getResources().getString(R.string.我的老师), true, true);
			radioButton1.setText(getResources().getString(R.string.我的老师));
		}
	}
	OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent=new Intent(context	, CelebrityActivity.class);
			intent.putExtra("id", personList.get(position).getUserid());
			intent.putExtra("groupid", 1);
			startActivity(intent);
//			int group = personList.get(position).getGroupid();
//			Intent intent = new Intent();
//			if (group == 1 || group == 2) {// 普通会员和名人
//				intent.setClass(context, CelebrityActivity.class);
//				intent.putExtra("id", blog.getUserid() + "");
//				intent.putExtra("groupid",blog.getGroupid());
//			} else if (group == 3) {// 老师
//				intent.setClass(context, TeacherActivity.class);
//				intent.putExtra("id", blog.getUserid() + "");
//			} else if (group == 4) {// 教室
//				intent.setClass(context, ClassRoomActivity.class);
//				intent.putExtra("id", blog.getUserid() + "");
//			}
//			context.startActivity(intent);
		}
	};

	OnBtnItemClickListener btnItemClickListener = new OnBtnItemClickListener() {

		@Override
		public void onItemClick(View v, int position) {
				reply(position);
		}
	};
	private OnCheckedChangeListener listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.radioButton1:
				tv_hint.setVisibility(View.GONE);
				tv_hint.setText("");
				personList.clear();
					api = "my/reply/jiaoshi_list";
				getData(true);
				break;
			case R.id.radioButton2:
				tv_hint.setVisibility(View.VISIBLE);
				tv_hint.setText("此处显示的是已经发出的邀请，等待你或他人的同意");
				personList.clear();
				api = "my/reply/jiaoshi_wait";
				getData(true);
				break;
			case R.id.radioButton3:
				tv_hint.setVisibility(View.VISIBLE);
				if (type.equals("room")) {
					tv_hint.setText("当与其他教室互相关注成为知音后，就可以在此处向他申请成为其琴行里的老师");
				}else {
					tv_hint.setText("当与其他老师互相关注成为知音后，就可以在此处邀请他成为你琴行里的老师");
				}
				personList.clear();
					api = "my/invite/reply";
				getData(true);
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
			getData(true);
		}
	};
	OnHeaderRefreshListener headerRefreshListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
			page = 1;
			personList.clear();
			getData(true);
		}
	};

	/***/
	protected void getData(Boolean isShowDialog) {
		if (isShowDialog) {
			MyProgressDialog.show(context);
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", api);
			map.put("groupid", groupid);
		map.put("pageIndex", page + "");
		map.put("pageSize", pageSize + "");
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
							personList.addAll(mList);
						}
						adapter.setList(personList);
						MyProgressDialog.Cancel();
					}
				}, null));
	}

	protected void reply(int position) {
		MyProgressDialog.show(context);
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("api", "my/reply/doReply");
		map.put("userid", personList.get(position).getUserid());
		map.put("groupid", groupid);
		map.put("action", personList.get(position).getStatus());
		map.put("pageindex", 1 + "");
		map.put("pagesize", 20 + "");
		map.put("logintoken", Data.user.getToken());
		map.put("loginuid", Data.user.getUserid());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						personList.clear();
						getData(false);
					}
				}, null));

	}

	/**
	 * 加载ContentAdapter数据
	 */
	protected void initContentAdapter() {
		Parcelable listState = lv_content.onSaveInstanceState();
		adapter.notifyDataSetChanged();
		lv_content.onRestoreInstanceState(listState);

	}
}
