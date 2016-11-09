package com.greattone.greattone.activity.rent;

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
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.adapter.MyRoomRentListAdapter;
import com.greattone.greattone.data.ClassId;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Lease;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;
/**琴房租赁*/
public class MyRoomRentActivity extends BaseActivity {
	private List<Lease>courseList = new ArrayList<Lease>();
//	protected List<CenterRoom> roomList = new ArrayList<CenterRoom>();
	private PullToRefreshView pull_to_refresh;
	private ListView lv_content;
	/** 页数 */
	private int page = 1;
	private MyRoomRentListAdapter adapter;
int classid=59;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_list);
		initView();
		getData();
	}

	private void initView() {
		setHead(getResources().getString(R.string.room_lease), true, true);//琴房租赁
		setOtherText(getResources().getString(R.string.发布), lis);

		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);//
		lv_content = (ListView) findViewById(R.id.lv_content);
		pull_to_refresh.setOnHeaderRefreshListener(headerRefreshListener);
		pull_to_refresh.setOnFooterRefreshListener(footerRefreshListener);
		adapter = new MyRoomRentListAdapter(context, courseList);
		lv_content.setAdapter(adapter);
		lv_content.setOnItemClickListener(listener);
		lv_content.setOnScrollListener(ImageLoaderUtil.getPauseOnScrollListener());
	}
	OnItemClickListener listener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			   Intent intent = new Intent(context, RentKotofusaDeailsActivity.class);
			   intent.putExtra("id",courseList.get(position).getId());
			   intent.putExtra("classid",classid+"");
			   intent.putExtra("type", "center");
		        startActivity(intent);
		}
	};

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(context, PostRoomRentActivity.class);
			 intent.putExtra("classid",classid);
			context.startActivity(intent);
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
			courseList.clear();
			getData();
		}
	};

	/***/
	protected void getData() {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "info/list");
		map.put("ismember", "1");
			map.put("extra", "price,pmaxnum");
		map.put("userid", Data.myinfo.getUserid());
		map.put("classid", ClassId.音乐教室_琴房租赁_ID+"");
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
						List<Lease> mList = JSON.parseArray(
							message.getData(), Lease.class);
						if (mList.size() == 0) {
							toast(getResources().getString(R.string.cannot_load_more));
						}
						courseList.addAll(mList);
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
		adapter.notifyDataSetChanged();
		lv_content.onRestoreInstanceState(listState);

	}
}
