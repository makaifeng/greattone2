package com.greattone.greattone.activity.celebrity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.Listener.OnSelectCityListener;
import com.greattone.greattone.activity.BaseFragment;
import com.greattone.greattone.adapter.CelebritiesContentGridAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.NormalPopuWindow;
import com.greattone.greattone.dialog.NormalPopuWindow.OnItemClickBack;
import com.greattone.greattone.dialog.SelectCityPopwindow;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.OrderBy;
import com.greattone.greattone.entity.UserInfo;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;

/** 音乐名人 */
public class MusicCelebritiesFragment extends BaseFragment {
	List<UserInfo> userList = new ArrayList<UserInfo>();
	/**
	 * fragment 主布局
	 */
	private View rootView;
	/** 地区筛选 */
	private LinearLayout ll_radiobutton1;
	/** 分类 */
	private LinearLayout ll_radiobutton2;
	/** 排序 */
	private LinearLayout ll_radiobutton3;
	private int pageSize = 30;
	private int page = 1;
	private int sear = 1;
	private String music_star;
	String province;
	String city;
	int order;
	int aid;
	String orderby;
	
	// /**
	// * 屏幕宽度
	// */
	// private int screenWidth;
	private PullToRefreshView pull_to_refresh;
	private GridView gv_content;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getCelebrities();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_music_celebrities,
				container, false);// 关联布局文件
		// screenWidth = ((BaseActivity) getActivity()).screenWidth;
		initView();
		return rootView;
	}

	/**
	 * 加载视图
	 */
	private void initView() {
		ll_radiobutton1 = (LinearLayout) rootView.findViewById(R.id.activity_radiobutton1_ll);
		ll_radiobutton1.setOnClickListener(lis);
		ll_radiobutton2= (LinearLayout) rootView.findViewById(R.id.activity_radiobutton2_ll);
		ll_radiobutton2.setOnClickListener(lis);
		ll_radiobutton3 = (LinearLayout) rootView.findViewById(R.id.activity_radiobutton3_ll);
		ll_radiobutton3.setOnClickListener(lis);

		pull_to_refresh = (PullToRefreshView) rootView
				.findViewById(R.id.pull_to_refresh);//
		gv_content = (GridView) rootView.findViewById(R.id.gv_content);// 正文
		gv_content.setOnItemClickListener(listener);
		pull_to_refresh.setOnHeaderRefreshListener(headerRefreshListener);
		pull_to_refresh.setOnFooterRefreshListener(footerRefreshListener);
		isInitView = true;
		initContentAdapter();
	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.activity_radiobutton1_ll:// 地区筛选
				SelectCityPopwindow.build(context)
				.setOnSelectCityListener(new OnSelectCityListener() {

					@Override
					public void ClickSure(String province, String city,
							String district) {
						MusicCelebritiesFragment.this.province = province;
						MusicCelebritiesFragment.this.city = city;
						page=1;
						userList.clear();
						getCelebrities();
					}
				}).show(ll_radiobutton1);
				break;
			case R.id.activity_radiobutton2_ll:// 分类
				type();	
				break;
			case R.id.activity_radiobutton3_ll:// 排序
			sort();
				break;

			default:
				break;
			}
//			if (v == tv_location) {// 地区筛选
//				SelectCityPopwindow.build(context)
//						.setOnSelectCityListener(new OnSelectCityListener() {
//
//							@Override
//							public void ClickSure(String province, String city,
//									String district) {
//								MusicCelebritiesFragment.this.province = province;
//								MusicCelebritiesFragment.this.city = city;
//								page=1;
//								userList.clear();
//								getCelebrities();
//							}
//						}).show(tv_location);
//			} else if (v == tv_type) {// 分类
//				type();
//			} else if (v == tv_sort) {// 排序
//				sort();
//			}
		
		}
	};

	/** 获取音乐名人 */
	private void getCelebrities() {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "search/musicStar");
		map.put("sear", sear+"");
		map.put("music_star", music_star);
		map.put("orderby", orderby);
		map.put("address", province);
		map.put("address1", city);
		map.put("pageSize", pageSize+"");
		map.put("pageIndex", page+"");
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
							userList.addAll(mList);
						}else {
							toast(getResources().getString(R.string.cannot_load_more));
						}
						pull_to_refresh.onHeaderRefreshComplete();
						pull_to_refresh.onFooterRefreshComplete();
						initContentAdapter();
						MyProgressDialog.Cancel();
					}

				}, null));
	}

	/** 分类 */
	protected void type() {
		NormalPopuWindow popuWindow = new NormalPopuWindow(context,
				Data.filter_star.getClassname(), ll_radiobutton2);
		popuWindow.setOnItemClickBack(new OnItemClickBack() {

			@Override
			public void OnClick(int position, String text) {
				music_star = text;
				page=1;
				userList.clear();
				getCelebrities();
			}
		});
		popuWindow.show();
	}

	/** 排序 */
	protected void sort() {
		List<String> list = new ArrayList<String>();
		for (OrderBy orderBy : Data.filter_star.getOrderby()) {
			list.add(orderBy.getName());
		}
		NormalPopuWindow popuWindow = new NormalPopuWindow(context, list,
				ll_radiobutton3);
		popuWindow.setOnItemClickBack(new OnItemClickBack() {

			@Override
			public void OnClick(int position, String text) {
				orderby = Data.filter_star.getOrderby().get(position)
						.getField();
				page=1;
				userList.clear();
				getCelebrities();
			}
		});
		popuWindow.show();
	}

	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long arg3) {
			Intent intent = new Intent(context, CelebrityActivity.class);
			intent.putExtra("id", userList.get(position).getUserid());
			intent.putExtra("groupid", userList.get(position).getGroupid());
			startActivity(intent);
		}
	};
	OnFooterRefreshListener footerRefreshListener = new OnFooterRefreshListener() {

		@Override
		public void onFooterRefresh(PullToRefreshView view) {
			page++;
			getCelebrities();
		}
	};
	OnHeaderRefreshListener headerRefreshListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
			page = 1;
			userList.clear();
			getCelebrities();
		}
	};
	private boolean isInitView;

	/**
	 * 加载ContentAdapter数据
	 */
	protected void initContentAdapter() {
		if (isInitView) {
			Parcelable listState = gv_content.onSaveInstanceState();
			CelebritiesContentGridAdapter adapter = new CelebritiesContentGridAdapter(
					context, userList);
			gv_content.setAdapter(adapter);
			gv_content.onRestoreInstanceState(listState);
		}
	}

}
