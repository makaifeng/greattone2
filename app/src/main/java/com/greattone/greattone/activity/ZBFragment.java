package com.greattone.greattone.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.adapter.ZBContentListAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.HaiXuanZB;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.PullToRefreshView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * 直播
 * @author makaifeng
 *
 */
public class ZBFragment extends BaseFragment {
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
	private ListView lv_content;

	/**
	 * 新闻数据
	 */
	// private List<News> newsList;
	/**
	 * 第一页
	 */
//	private final int FIRST_PAGE = 1;
	/**
	 * 当前加载的页数
	 */
//	private int page = FIRST_PAGE;
	private List<HaiXuanZB> ZBList = new ArrayList<HaiXuanZB>();
	private PullToRefreshView pull_to_refresh;
//	private int pageSize = 30;
//	private String province;
//	private	String city;
//	private int order;
//	private int aid;
//	private String orderby;
	int orderbyposition;
private RadioGroup radiogroup;
private int fillter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getZB();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.activity_my_post, container, false);// 关联布局文件
		// screenWidth = ((BaseActivity) getActivity()).screenWidth;
		initView();
		return rootView;
	}

	/**
	 * 加载视图
	 */
	private void initView() {
//		//搜索
//		((BaseActivity)context).setOtherText(getResources().getString(R.string.search), new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				 Intent intent = new Intent(context,SearchAct.class);
//				 startActivityForResult(intent, 1);
//			}
//		});
		radiogroup = (RadioGroup) rootView.findViewById(R.id.radiogroup);
		((RadioButton)rootView.findViewById(R.id.radioButton1)).setText("正在直播");
		((RadioButton)rootView.findViewById(R.id.radioButton2)).setText("直播预告");
		((RadioButton)rootView.findViewById(R.id.radioButton3)).setText("历史回顾");
		((RadioButton)rootView.findViewById(R.id.radioButton4)).setVisibility(View.GONE);
		radiogroup.check(R.id.radioButton1);
		radiogroup.setOnCheckedChangeListener(listener);
		pull_to_refresh = (PullToRefreshView)rootView. findViewById(R.id.pull_to_refresh);//
		lv_content = (ListView)rootView. findViewById(R.id.lv_content);
		lv_content.setOnItemClickListener(itemClickListener);
		lv_content.setDividerHeight(DisplayUtil.dip2px(context, 10));
//		addPullRefreshListener();
		isInitView=true;
		initContentAdapter();

	}
	OnCheckedChangeListener listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.radioButton1:
				fillter=0;
				break;
			case R.id.radioButton2:
				fillter=1;
				break;
			case R.id.radioButton3:
				fillter=2;
				break;

			default:
				break;
			}
			ZBList.clear();
			getZB();
		}
	};


	OnClickListener lis=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
		}
	};
//	/**
//	 * 添加上下拉刷新功能的监听
//	 */
//	private void addPullRefreshListener() {
//		pull_to_refresh
//				.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {
//
//					@Override
//					public void onHeaderRefresh(PullToRefreshView view) {
////						page = FIRST_PAGE;
//						ZBList.clear();
//						getZB();
//					}
//				});
//		pull_to_refresh
//				.setOnFooterRefreshListener(new OnFooterRefreshListener() {
//
//					@Override
//					public void onFooterRefresh(PullToRefreshView view) {
////						page++;
//						getZB();
//					}
//				});
//	}

	OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long arg3) {
			if (fillter==0) {//直播
				Intent intent=new Intent(context,WebActivity.class);
				intent.putExtra("urlPath",ZBList.get(position).getH5url());
				intent.putExtra("action","shareZB");
				intent.putExtra("shareTitle",ZBList.get(position).getTitle());
				intent.putExtra("title",ZBList.get(position).getTitle());
				intent.putExtra("shareContent",ZBList.get(position).getSmalltext());
				intent.putExtra("sharePic",ZBList.get(position).getTitlepic());
//			intent.putExtra("orientation",1);
				startActivity(intent);
//				Intent intent=new Intent(context,ZBActivity.class);
//				intent.putExtra("urlPath",ZBList.get(position).getH5url());
//		        boolean useHls = false;
//				   intent.putExtra("data", LetvParamsUtils.setActionLiveParams(ZBList.get(position).getActivityid(), useHls));
//				intent.putExtra("action","shareZB");
//				intent.putExtra("shareTitle",ZBList.get(position).getTitle());
//				intent.putExtra("title",ZBList.get(position).getTitle());
//				intent.putExtra("shareContent",ZBList.get(position).getSmalltext());
//				intent.putExtra("sharePic",ZBList.get(position).getTitlepic());
////			intent.putExtra("orientation",1);
//				startActivity(intent);
			}else 	if (fillter==1){//直播预告
				if (!TextUtils.isEmpty( ZBList.get(position).getVideo())) {
					Intent intent=new Intent(context,VideoPlayActivity.class);
					intent.putExtra("url", ZBList.get(position).getVideo());
					startActivity(intent);
				}
			}else 	if (fillter==2){//历史回顾
				Intent intent=new Intent(context,VideoPlayActivity.class);
				intent.putExtra("url", ZBList.get(position).getVideo());
				startActivity(intent);
			}
		}
	};
	private boolean isInitView;
//private String username;

	/**
	 * 获取教室数据
	 */
	private void getZB() {
		 MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "info/zblist");
		map.put("zb_fillter",fillter+"");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (!message.getData().isEmpty()&&message.getData().startsWith("[")) {
							List<HaiXuanZB> mList = JSON.parseArray(
									message.getData(), HaiXuanZB.class);
							if (mList.size() == 0) {
								toast(getResources().getString(R.string.cannot_load_more));
							}
							ZBList.addAll(mList);
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
		Parcelable listState = lv_content.onSaveInstanceState();
		ZBContentListAdapter adapter = new ZBContentListAdapter(
					context, ZBList);
			lv_content.setAdapter(adapter);
			lv_content.onRestoreInstanceState(listState);
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
if ( resultCode==Activity.RESULT_OK&&requestCode==1) {
//	 username=data.getStringExtra("data");
	ZBList.clear();
//	 page=1; 
	 getZB();
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
