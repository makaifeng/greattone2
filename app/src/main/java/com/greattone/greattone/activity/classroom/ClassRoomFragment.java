package com.greattone.greattone.activity.classroom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.Listener.OnSelectCityListener;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.BaseFragment;
import com.greattone.greattone.activity.SearchAct;
import com.greattone.greattone.adapter.ClassRoomContentGridAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.CitySelectDialog;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.NormalPopuWindow;
import com.greattone.greattone.dialog.NormalPopuWindow.OnItemClickBack;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.OrderBy;
import com.greattone.greattone.entity.UserInfo;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;

/**
 * 教室
 */
public class ClassRoomFragment extends BaseFragment {
	/**
	 * fragment 主布局
	 */
	private View rootView;
	// /**
	// * 屏幕宽度
	// */
	// private int screenWidth;

	/**
	 * 正文内容
	 */
	private GridView gv_content;

	/**
	 * 新闻数据
	 */
	// private List<News> newsList;
	/**
	 * 第一页
	 */
	private final int FIRST_PAGE = 1;
	/**
	 * 当前加载的页数
	 */
	private int page = FIRST_PAGE;
	private List<UserInfo> classRoomList = new ArrayList<UserInfo>();
	private PullToRefreshView pull_to_refresh;
	private int sear = 1;
	private int pageSize = 30;
	private String classroom_type;
	private String province;
	private	String city;
	private	String district;
	private String orderby;
	int orderbyposition;

	private LinearLayout ll_radiobutton1;

	private LinearLayout ll_radiobutton2;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getClassRoom();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_room, container, false);// 关联布局文件
		// screenWidth = ((BaseActivity) getActivity()).screenWidth;
		initView();
		return rootView;
	}

	/**
	 * 加载视图
	 */
	private void initView() {
		//搜索
		((BaseActivity)context).setOtherText(getResources().getString(R.string.search), new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Intent intent = new Intent(context,SearchAct.class);
				 startActivityForResult(intent, 1);
			}
		});
		pull_to_refresh = (PullToRefreshView) rootView
				.findViewById(R.id.pull_to_refresh);//
		ll_radiobutton1 = (LinearLayout) rootView.findViewById(R.id.activity_musicteacher_radiobutton1_ll);// 位置筛选
		ll_radiobutton1.setOnClickListener(lis);
		ll_radiobutton2= (LinearLayout) rootView.findViewById(R.id.activity_musicteacher_radiobutton2_ll);// 排序方式
		ll_radiobutton2.setOnClickListener(lis);
		gv_content = (GridView) rootView.findViewById(R.id.gv_content);// 正文
		gv_content.setNumColumns(1);
		gv_content.setOnItemClickListener(listener);
		gv_content.setOnScrollListener(ImageLoaderUtil.getPauseOnScrollListener());
		addPullRefreshListener();
		isInitView=true;
		initContentAdapter();
//		getClassRoom2(FIRST_PAGE);

	}


	OnClickListener lis=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (v==ll_radiobutton1) {// 位置筛选
			CitySelectDialog citySelectDialog = new CitySelectDialog(
					context);
			citySelectDialog
					.setonClickSureListener(new OnSelectCityListener() {
						public void ClickSure(String province, String city,
								String district) {
							ClassRoomFragment.this.province = province;
							ClassRoomFragment.this.city = city.endsWith("全部")?"":city;
							ClassRoomFragment.this.district = district.endsWith("全部")?"":district;
							page=1;
							classRoomList.clear();
							getClassRoom();
						}
					});
			citySelectDialog.show();
			}else if (v==ll_radiobutton2){// 排序方式
				sort();
			}
		}
	};
	
	
	/**
	 * 添加上下拉刷新功能的监听
	 */
	private void addPullRefreshListener() {
		pull_to_refresh
				.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {

					@Override
					public void onHeaderRefresh(PullToRefreshView view) {
//						state = PULL_DOWN;
						page = FIRST_PAGE;
						classRoomList.clear();
						getClassRoom();
					}
				});
		pull_to_refresh
				.setOnFooterRefreshListener(new OnFooterRefreshListener() {

					@Override
					public void onFooterRefresh(PullToRefreshView view) {
//						state = PULL_UP;
						page++;
						getClassRoom();
					}
				});
	}

	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long arg3) {
			Intent intent=new Intent(context, ClassRoomActivity.class);
			intent.putExtra("id", classRoomList.get(position).getUserid());
			startActivity(intent);
		}
	};

	private boolean isInitView;
private String username;


/** 排序 */
protected void sort() {
	List<String> list = new ArrayList<String>();
	for (OrderBy orderBy : Data.filter_classroom.getOrderby()) {
		list.add(orderBy.getName());
	}
	NormalPopuWindow popuWindow = new NormalPopuWindow(context, list,
			ll_radiobutton2);
	popuWindow.setOnItemClickBack(new OnItemClickBack() {

		@Override
		public void OnClick(int position, String text) {
			orderby = Data.filter_classroom.getOrderby().get(position)
					.getField();
			page=1;
			classRoomList.clear();
			getClassRoom();
		}
	});
	popuWindow.show();
}
	/**
	 * 获取教室数据
	 */
	private void getClassRoom() {
		 MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "search/musicClassroom");
		map.put("sear", sear+"");
		map.put("classroom_type", classroom_type);
		map.put("orderby", orderby);
		map.put("address", province);
		map.put("address1", city);
		map.put("address2", district);
		map.put("username", username);
		map.put("pageSize", pageSize+"");
		map.put("pageIndex", page+"");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (!message.getData().isEmpty()) {
							List<UserInfo> mList = JSON.parseArray(
									message.getData(), UserInfo.class);
							if (mList.size() == 0) {
								toast(getResources().getString(R.string.cannot_load_more));
							}
							classRoomList.addAll(mList);
						} else {
							toast(getResources().getString(R.string.cannot_load_more));
						}
						pull_to_refresh.onHeaderRefreshComplete();
						pull_to_refresh.onFooterRefreshComplete();
						if (isInitView) {
							initContentAdapter();
						}
						MyProgressDialog.Cancel();
					}
				}, null));
	}

	/**
	 * 加载ContentAdapter数据
	 */
	protected void initContentAdapter() {
		if (isInitView) {
		Parcelable listState = gv_content.onSaveInstanceState();
//			ClassRoomContentListAdapter adapter = new ClassRoomContentListAdapter(
//					context, classRoomList);
			ClassRoomContentGridAdapter adapter = new ClassRoomContentGridAdapter(
					context, classRoomList);
			gv_content.setAdapter(adapter);
			gv_content.onRestoreInstanceState(listState);
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
if ( resultCode==Activity.RESULT_OK&&requestCode==1) {
	 username=data.getStringExtra("data");
	 classRoomList.clear();
	 page=1; 
	 getClassRoom();
}
	}
	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
