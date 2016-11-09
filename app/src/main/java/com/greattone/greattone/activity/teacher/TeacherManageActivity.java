package com.greattone.greattone.activity.teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.Listener.OnBtnItemClickListener;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.adapter.MyTescherListAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.PersonList;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;
/**教师管理*/
public class TeacherManageActivity extends BaseActivity {
	protected List<PersonList> dataList = new ArrayList<PersonList>();
	private RadioGroup radiogroup;
	private RadioButton radioButton1;
	private RadioButton radioButton2;
	private RadioButton radioButton3;
	private RadioButton radioButton4;
	private PullToRefreshView pull_to_refresh;
	private ListView lv_content;
	/** 页数 */
	private int page = 1;
	private int pagesize = 15;
	private int groupid = 3;
	private MyTescherListAdapter adapter;
	String api = "my/invite/list";
	String key;
	String cid;
	private TextView tv_hint;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_post);
		initView();
		getData(true);
	}

	private void initView() {
		setHead(getResources().getString(R.string.教师管理), true, true);
		radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
		radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
		radioButton1.setText(getResources().getString(R.string.教师团队));
		radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
		radioButton2.setText(getResources().getString(R.string.等待列表));
		radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
		radioButton3.setText(getResources().getString(R.string.邀请列表));
		radioButton4 = (RadioButton) findViewById(R.id.radioButton4);
		radioButton4.setVisibility(View.GONE);
		radiogroup.check(R.id.radioButton1);
		radiogroup.setOnCheckedChangeListener(listener);

		tv_hint = (TextView) findViewById(R.id.tv_hint);//
		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);//
		lv_content = (ListView) findViewById(R.id.lv_content);
		pull_to_refresh.setOnHeaderRefreshListener(headerRefreshListener);
		pull_to_refresh.setOnFooterRefreshListener(footerRefreshListener);
		adapter = new MyTescherListAdapter(context, dataList,"邀请");
		adapter.setOnBtnItemClicklistener(btnItemClickListener);
		lv_content.setAdapter(adapter);
	}

	private OnCheckedChangeListener listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			dataList.clear();
			switch (checkedId) {
			case R.id.radioButton1:
				tv_hint.setVisibility(View.GONE);;
				api = "my/invite/list";
				pagesize = 15;
				getData(true);
				break;
			case R.id.radioButton2:
				tv_hint.setText("此处显示的是已经发出的邀请，等待你或他人的同意");
				pagesize = 200;
				api = "my/invite/wait";
				getData(true);
				break;
			case R.id.radioButton3:
					tv_hint.setText("当与其他老师互相关注成为知音后，就可以在此处邀请他成为你琴行里的老师");
				pagesize = 20;
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
			dataList.clear();
			getData(true);
		}
	};
	OnBtnItemClickListener btnItemClickListener = new OnBtnItemClickListener() {

		@Override
		public void onItemClick(View v, int position) {
			reply(position);
		}
	};
	/***/
	protected void getData(Boolean isShowDialog) {
		if (isShowDialog) {
			MyProgressDialog.show(context);
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", api);
		map.put("groupid", groupid + "");
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
	protected void reply(int position) {
		MyProgressDialog.show(context);
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("api", "my/reply/doReply");
		map.put("userid", dataList.get(position).getUserid());
		map.put("groupid", groupid+"");
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
	protected void initContentAdapter() {
		Parcelable listState = lv_content.onSaveInstanceState();
		adapter.setList(dataList);
		lv_content.onRestoreInstanceState(listState);
	}

}
