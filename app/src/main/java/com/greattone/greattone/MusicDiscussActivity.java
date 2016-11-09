package com.greattone.greattone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.adapter.PlazaMusicDetailsListAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Comment;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.PullToRefreshView;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
//乐谱评论列表
public class MusicDiscussActivity extends BaseActivity {
	
	List<Comment> commList = new ArrayList<Comment>();
	private EditText et_disscuss;
	private TextView tv_send;
	private ListView lv_content;
	private String classid;
	private String id;
	int page=1;
	int pageSize=20;
	private PullToRefreshView pull_to_refresh;
	private PlazaMusicDetailsListAdapter adapter;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_discuss);
		classid=getIntent().getStringExtra("classid");
		id=getIntent().getStringExtra("id");
		initView();
		getCommentList();
	}

	private void initView() {
		setHead("评论", true, true);
		et_disscuss=(EditText)findViewById(R.id.et_disscuss);
		tv_send=(TextView)findViewById(R.id.tv_send);
		pull_to_refresh=(PullToRefreshView)findViewById(R.id.pull_to_refresh);
		lv_content=(ListView)findViewById(R.id.lv_content);
		tv_send.setOnClickListener(lis);
		initViewData();
	}
	private void initViewData() {
		adapter = new PlazaMusicDetailsListAdapter(context, commList);
		lv_content.setAdapter(adapter);
	}
	/** 获取评论信息 */
	protected void getCommentList() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "comment/list");
		map.put("classid", classid+"");
		map.put("id", id+"");
		map.put("pageSize", pageSize+"");
		map.put("pageIndex", page+"");
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData().startsWith("[")) {
							List<Comment>  mlist=JSON.parseArray(message.getData(), Comment.class);
							commList.addAll(mlist);
						}
						pull_to_refresh.onHeaderRefreshComplete();
						pull_to_refresh.onFooterRefreshComplete();
						initContentAdapter();
						MyProgressDialog.Cancel();
					}
				}, null));
	}
	private void comment(String text) {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "comment/post");
		map.put("classid",classid);
		map.put("id", id);
		map.put("saytext", text);
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		((BaseActivity) context).addRequest(HttpUtil
				.httpConnectionByPost(context, map,
						new ResponseListener() {

							@Override
							public void setResponseHandle(
									Message2 message) {
								et_disscuss.setText("");
								toast("评论成功");
								getCommentList();
							}

						}, null));

	}
	OnClickListener lis=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			String text=et_disscuss.getText().toString().trim();
			if (TextUtils.isEmpty(text)) {
				toast("请输入内容");
				return;
			}
			comment(text);
		}
	};
	
	/**
	 * 加载ContentAdapter数据
	 */
	protected void initContentAdapter() {
		 Parcelable listState = lv_content.onSaveInstanceState();
		adapter.notifyDataSetChanged();
		 lv_content.onRestoreInstanceState(listState);

	}
}
