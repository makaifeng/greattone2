package com.greattone.greattone.activity.teacher;

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

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.greattone.greattone.Listener.OnSelectCityListener;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.BaseFragment;
import com.greattone.greattone.activity.SearchAct;
import com.greattone.greattone.adapter.TeacherContentListAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.CitySelectDialog;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.NormalPopuWindow;
import com.greattone.greattone.dialog.NormalPopuWindow.OnItemClickBack;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.OrderBy;
import com.greattone.greattone.entity.UserInfo;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ErrorResponseListener;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.MyGridView;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 老师列表
 */
public class TeacherFragment extends BaseFragment {
	/**
	 * fragment 主布局
	 */
	private View rootView;
	// /**
	// * 屏幕宽度
	// */
	// private int screenWidth;

	/**
	 * 二级标题栏
	 */
	private MyGridView gv_subtitle;
	/**
	 * 正文内容
	 */
	private GridView gv_content;
//	private int colors[] = { R.color.news_subtitle_color1_f25e84,
//			R.color.news_subtitle_color2_b677dd,
//			R.color.news_subtitle_color3_47b9e9,
//			R.color.news_subtitle_color4_728be7,
//			R.color.news_subtitle_color5_35c977,
//			R.color.news_subtitle_color6_ea5fba,
//			R.color.news_subtitle_color7_ff8901,
//			R.color.news_subtitle_color8_ed5656 };

	List<UserInfo> teacherList = new ArrayList<UserInfo>();
	TeacherContentListAdapter teacherAdapter;
	private PullToRefreshView pull_to_refresh;
	private int pageSize = 30;
	private int page = 1;
	private int sear = 1;
	private String teacher_type;
	String province;
	String city;
	String district;
	int order;
	int aid;
	int orderbyposition;
	String orderby;
//	private TextView tv_location;
//
//	private TextView tv_sort;
	private LinearLayout ll_radiobutton1;

	private LinearLayout ll_radiobutton2;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getTeacher();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.activity_music_teacher, container, false);// 关联布局文件
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
//		tv_location = (TextView) rootView.findViewById(R.id.activity_musicteacher_radiobutton1);// 位置筛选
//		tv_location.setOnClickListener(lis);
//		tv_sort = (TextView) rootView.findViewById(R.id.activity_musicteacher_radiobutton2);// 排序方式
//		tv_sort.setOnClickListener(lis);
		ll_radiobutton1 = (LinearLayout) rootView.findViewById(R.id.activity_musicteacher_radiobutton1_ll);// 位置筛选
		ll_radiobutton1.setOnClickListener(lis);
		ll_radiobutton2= (LinearLayout) rootView.findViewById(R.id.activity_musicteacher_radiobutton2_ll);// 排序方式
		ll_radiobutton2.setOnClickListener(lis);
		pull_to_refresh = (PullToRefreshView) rootView
				.findViewById(R.id.pull_to_refresh);//
		gv_content = (GridView) rootView.findViewById(R.id.gv_content);// 正文
		gv_content.setNumColumns(2);
		pull_to_refresh.setOnHeaderRefreshListener(headerRefreshListener);
		pull_to_refresh.setOnFooterRefreshListener(footerRefreshListener);
		isInitView = true;
		addgridView();
	}

	OnFooterRefreshListener footerRefreshListener = new OnFooterRefreshListener() {

		@Override
		public void onFooterRefresh(PullToRefreshView view) {
			page++;
			getTeacher();
		}
	};
	OnHeaderRefreshListener headerRefreshListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
			page = 1;
			teacherList.clear();
			getTeacher();
		}
	};
	private OnClickListener lis=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (v==ll_radiobutton1) {// 位置筛选
//				SelectCityPopwindow.build(context).setOnSelectCityListener(new OnSelectCityListener() {
//
//					@Override
//					public void ClickSure(String province, String city,
//							String district) {
//						TeacherFragment.this.province = province;
//						TeacherFragment.this.city = city;
//						page=1;
//						teacherList.clear();
//						getTeacher();
//					}
//				}).show(v);
				CitySelectDialog citySelectDialog = new CitySelectDialog(
						context);
				citySelectDialog
						.setonClickSureListener(new OnSelectCityListener() {
							public void ClickSure(String province, String city,
									String district) {
								TeacherFragment.this.province = province;
								TeacherFragment.this.city =  city.endsWith("全部")?"":city;
								TeacherFragment.this.district =  district.endsWith("全部")?"":district;
								page=1;
								teacherList.clear();
								getTeacher();
							}
						});
				citySelectDialog.show();
				}else if (v==ll_radiobutton2){// 排序方式
					sort();
//					orderbyposition++;
//					orderby=Data.filter_teacher.getOrderby().get(orderbyposition%2)
//							.getField();
//					page=1;
//					teacherList.clear();
//					getTeacher();
				}
		}
	};
	private void addgridView() {
//		gv_subtitle = new MyGridView(context);// 标题
//		gv_subtitle.setId(100);
//		gv_subtitle.setNumColumns(4);
//		gv_subtitle.setVerticalSpacing(20);
//		gv_subtitle.setHorizontalSpacing(20);
//		gv_subtitle.setPadding(0, DisplayUtil.dip2px(context, 10), 0,
//				DisplayUtil.dip2px(context, 10));
//		NewsTitleGridAdapter1 adapter = new NewsTitleGridAdapter1(
//				getActivity(), colors, getResources().getStringArray(
//						R.array.teacher_subtitle));
//		gv_subtitle.setAdapter(adapter);
//		gv_subtitle.setOnItemClickListener(listener);
////		gv_content.addHeaderView(gv_subtitle);
		
		
		gv_content.setOnItemClickListener(listener);
		teacherAdapter = new TeacherContentListAdapter((BaseActivity) getActivity(),
				teacherList);
		gv_content.setAdapter(teacherAdapter);
		initContentAdapter();
	}

	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long arg3) {
			if (adapter == gv_subtitle) {

			} else if (adapter == gv_content) {
//				if (position >= 1) {
					Intent intent = new Intent(context, TeacherActivity.class);
					intent.putExtra("id", teacherList.get(position)
							.getUserid());
					startActivity(intent);
//				}
			}
		}
	};
	protected boolean isInitView;
String username;
	/**
	 * 老师集合数据
	 */
	private void getTeacher() {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "search/musicTeacher");
		map.put("sear", sear+"");
		map.put("teacher_type", teacher_type);
		map.put("city", Data.myLocation.getCity().replace("市","").replace("省",""));
		map.put("orderby", orderby);
		map.put("address", province);
		map.put("username", username);
		map.put("address1", city);
		map.put("address2", district);
		map.put("pageSize", pageSize+"");
		map.put("pageIndex", page+"");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData().startsWith("[")) {
							List<UserInfo> mList = JSON.parseArray(
									message.getData(), UserInfo.class);
							if (mList.size() == 0) {
								toast(getResources().getString(R.string.cannot_load_more));
							}
							teacherList.addAll(mList);
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
				}, new ErrorResponseListener() {
					
					@Override
					public void setServerErrorResponseHandle(Message2 message) {
						pull_to_refresh.onHeaderRefreshComplete();
						pull_to_refresh.onFooterRefreshComplete();
					}
					
					@Override
					public void setErrorResponseHandle(VolleyError error) {
						
					}
				}));
	}

/** 排序 */
protected void sort() {
	List<String> list = new ArrayList<String>();
	for (OrderBy orderBy : Data.filter_teacher.getOrderby()) {
		list.add(orderBy.getName());
	}
	NormalPopuWindow popuWindow = new NormalPopuWindow(context, list,
			ll_radiobutton2);
	popuWindow.setOnItemClickBack(new OnItemClickBack() {

		@Override
		public void OnClick(int position, String text) {
			orderby = Data.filter_teacher.getOrderby().get(position)
					.getField();
			page=1;
			teacherList.clear();
			getTeacher();
		}
	});
	popuWindow.show();
}
	/**
	 * 加载ContentAdapter数据
	 */
	protected void initContentAdapter() {
		Parcelable listState = gv_content.onSaveInstanceState();
		teacherAdapter.notifyDataSetChanged();
		gv_content.onRestoreInstanceState(listState);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
if ( resultCode==Activity.RESULT_OK&&requestCode==1) {
	 username=data.getStringExtra("data");
	 teacherList.clear();
	 page=1;
	 getTeacher();
}
	}
	@Override
	public void onResume() {
		super.onResume();
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
