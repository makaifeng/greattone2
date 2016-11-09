package com.greattone.greattone.activity.haixuan_and_activitise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.SwipeMenu.MyDeleteSwipeMenuCreator;
import com.greattone.greattone.SwipeMenu.SwipeMenu;
import com.greattone.greattone.SwipeMenu.SwipeMenuListView;
import com.greattone.greattone.SwipeMenu.SwipeMenuListView.OnMenuItemClickListener;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.adapter.MyActivityGridAdapter;
import com.greattone.greattone.data.ClassId;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Blog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;
/**我的活动*/
public class MyActivityActivity extends BaseActivity {
	protected List<Blog> competitionList = new ArrayList<Blog>();
	private TextView tv_my_activity;
	private TextView tv_post_activity;
	// private FrameLayout frament;
	private SwipeMenuListView lv_content;
	private MyActivityGridAdapter adapter;
	private PullToRefreshView pull_to_refresh;
	private int page = 1;
	private String province;
	private	String city;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_activity);
		initView();
		getData();
	}

	private void initView() {
			setHead(getResources().getString(R.string.我的活动), true, true);
setOtherText(getResources().getString(R.string.发布), new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		startActivityForResult(new Intent(context, ReleaseActivityAct.class),0);
	}
});
		tv_my_activity = (TextView) findViewById(R.id.tv_my_activity);
		tv_post_activity = (TextView) findViewById(R.id.tv_post_activity);
		tv_my_activity.setOnClickListener(lis);
		tv_post_activity.setOnClickListener(lis);
		// frament=(FrameLayout)findViewById(R.id.frament);

		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);//
		pull_to_refresh.setOnHeaderRefreshListener(headerRefreshListener);
		pull_to_refresh.setOnFooterRefreshListener(footerRefreshListener);
		lv_content = (SwipeMenuListView) findViewById(R.id.lv_content);
		adapter = new MyActivityGridAdapter(context, competitionList);
		lv_content.setAdapter(adapter);
		lv_content.setOnItemClickListener(listener);
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

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_my_activity:

				break;
			case R.id.tv_post_activity:

				break;

			default:
				break;
			}
		}
	};
	/**删除*/
	protected void delect(final int position) {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "post/ecms");
		map.put("enews", "MDelInfo");
		map.put("classid", competitionList.get(position).getClassid() + "");
		map.put("id", competitionList.get(position).getId() + "");
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
								competitionList.remove(position);
								adapter.notifyDataSetChanged();
								MyProgressDialog.Cancel();
							}

						}, null));
	}
	/**获取活动列表*/
	protected void getData() {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "info/list");
		map.put("ismember", "1");
//			map.put("extra", "price,pmaxnum");
		map.put("userid", Data.myinfo.getUserid());
		map.put("classid", ClassId.音乐活动_ID+"");
		map.put("extra", "dizhi");
		
		map.put("address", province);
		map.put("address1", city);
		map.put("pageIndex", page+"");
		map.put("pageSize","20");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData() != null
								&& message.getData().startsWith("[")) {
						List<Blog> mList = JSON.parseArray(
							message.getData(), Blog.class);
						if (mList.size() == 0) {
							toast(getResources().getString(R.string.cannot_load_more));
						}
						competitionList.addAll(mList);
						}
							pull_to_refresh.onHeaderRefreshComplete();
							pull_to_refresh.onFooterRefreshComplete();
						initContentAdapter();
						MyProgressDialog.Cancel();
					}

				}, null));
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
			page = 1;
			competitionList.clear();
			getData();
		}
	};
	private OnItemClickListener listener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(context, ActivitiyDetailsActivityCenter.class);
			intent.putExtra("id", competitionList.get(position).getId());
			intent.putExtra("type", 1);// 1活动 2赛事
			intent.putExtra("classid",ClassId.会员活动_ID);//
			startActivity(intent);
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
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg1==RESULT_OK) {
			getData();
		}
	}
}
