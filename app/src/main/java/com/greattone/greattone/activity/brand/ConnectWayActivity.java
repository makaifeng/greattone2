package com.greattone.greattone.activity.brand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.adapter.ConnectWayListAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.ConnectWay;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;
/**
 *品牌的联系我们
 * @author makaifeng
 *
 */
public class ConnectWayActivity extends BaseActivity {
	List<ConnectWay> leavemessageList=new ArrayList<ConnectWay>();
	private int page=1;
	
	private PullToRefreshView pull_to_refresh;
	private ListView lv_content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connect_way);
		initView();
		getLeaveMessage();
	}

	private void initView() {
		setHead("联系我们", true, true);
		// 渠道列表
		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);//
		lv_content = (ListView) findViewById(R.id.lv_content);// 正文
		lv_content.setOnItemClickListener(listener);
		addPullRefreshListener();

//		initViewData();
	}

//	protected void initViewData() {
//		m_name.setText(useMsg.getUsername());
//		m_company.setText(useMsg.getCompany());
//		m_mobile.setText(useMsg.getTelephone());
//		m_year.setText(useMsg.getChusheng());
//		m_address.setText(useMsg.getAddres());
//		m_descr.setText(useMsg.getSaytext());
//	}


	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long arg3) {
			// Intent intent=new Intent(context, ClassRoomActivity.class);
			// intent.putExtra("id", classRoomList.get(position).getUserid());
			// startActivity(intent);
		}
	};


	/**
	 * 获取留言板信息
	 */
	private void getLeaveMessage() {
		 MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "brand/brandconnnectwaylist");
		map.put("userid", getIntent().getStringExtra("userid"));
		map.put("pageSize", 20+"");
		map.put("pageIndex", page+"");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData()!=null&&message.getData().startsWith("{")) {
				
							List<ConnectWay> mList = JSON.parseArray(
									JSON.parseObject(message.getData()).getString("main"), ConnectWay.class);
							List<ConnectWay> sList = JSON.parseArray(
									JSON.parseObject(message.getData()).getString("branch"), ConnectWay.class);
							if (sList.size() == 0) {
								toast(getResources().getString(R.string.cannot_load_more));
							}
							leavemessageList.addAll(mList);
							leavemessageList.addAll(sList);
							leavemessageList.addAll(sList);
						} else {
							toast(getResources().getString(R.string.cannot_load_more));
						}
						pull_to_refresh.onHeaderRefreshComplete();
						pull_to_refresh.onFooterRefreshComplete();
							initContentAdapter();
						MyProgressDialog.Cancel();
					}
				}, null));
	}

	protected void initContentAdapter() {
		Parcelable listState = lv_content.onSaveInstanceState();
		ConnectWayListAdapter adapter = new ConnectWayListAdapter(context,
				leavemessageList);
		lv_content.setAdapter(adapter);
		lv_content.onRestoreInstanceState(listState);
	}

	/**
	 * 添加上下拉刷新功能的监听
	 */
	private void addPullRefreshListener() {
		pull_to_refresh
				.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {

					@Override
					public void onHeaderRefresh(PullToRefreshView view) {
						page = 1;
						leavemessageList.clear();
						getLeaveMessage();
					}
				});
		pull_to_refresh
				.setOnFooterRefreshListener(new OnFooterRefreshListener() {

					@Override
					public void onFooterRefresh(PullToRefreshView view) {
						page++;
						getLeaveMessage();
					}
				});
	}
}
