package com.greattone.greattone.activity.plaza;

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
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.BaseFragment;
import com.greattone.greattone.activity.EntryActivity;
import com.greattone.greattone.activity.haixuan_and_activitise.VoteDetailsActivity;
import com.greattone.greattone.adapter.MusicPlazaListAdapter;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Blog;
import com.greattone.greattone.entity.ImageData;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** 音乐广场 */
public class MusicPlazaFragment extends BaseFragment {
	/**
	 * fragment 主布局
	 */
	private View rootView;
	private RadioGroup radiogroup;
	/**
	 * 第一页
	 */
	private final int FIRST_PAGE = 1;
	/**
	 * 当前加载的页数
	 */
	private int page = FIRST_PAGE;
	private int pageSize = 20;
	List<Blog> blogsList = new ArrayList<Blog>();
	private String query;
	MusicPlazaListAdapter adapter;
	private PullToRefreshView pull_to_refresh;
	private final int NO_PULL = 0;// 上拉
	private final int PULL_UP = 2;// 上拉
	private final int PULL_DOWN = 1;// 下拉
	int state = NO_PULL;// 判断是其他，上拉还是下拉，NO_PULL其他，PULL_DOWN下拉，PULL_UP上拉
	private ListView lv_content;
	boolean isInitView = false;
String keyboard ;
protected List<ImageData> imageUrlList;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		keyboard=getArguments().getString("keyboard");
		if (keyboard==null) {
			query = "isgood";// 热门贴
		}
		((BaseActivity) context).setOtherText(context.getResources().getString(R.string.post), new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, EntryActivity.class);
				intent.putExtra("type", context.getResources().getString(R.string.post));
				startActivity(intent);
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_musicplaza, container,
				false);// 关联布局文件
		// screenWidth = ((BaseActivity) getActivity()).screenWidth;
		initView();
		getAd();
		return rootView;
	}


	/**
	 * 加载视图
	 */
	private void initView() {
		radiogroup = (RadioGroup) rootView.findViewById(R.id.radiogroup);
		if (keyboard!=null) {//点搜索进来，显示全部
			radiogroup.check(R.id.radioButton1);
		}else {//默认显示热门贴
			radiogroup.check(R.id.radioButton2);
		}
		radiogroup.setOnCheckedChangeListener(listener);

		pull_to_refresh = (PullToRefreshView) rootView
				.findViewById(R.id.pull_to_refresh);//
		lv_content = (ListView) rootView.findViewById(R.id.lv_content);// 正文
		adapter = new MusicPlazaListAdapter(getActivity(), blogsList,imageUrlList,1);
		lv_content.setAdapter(adapter);
		lv_content.setOnItemClickListener(itemClickListener);
		lv_content.setOnScrollListener(ImageLoaderUtil.getPauseOnScrollListener());
		pull_to_refresh
				.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {

					@Override
					public void onHeaderRefresh(PullToRefreshView view) {
						state = PULL_DOWN;
						page = FIRST_PAGE;
						blogsList.clear();
						getBlogs();
					}
				});
		pull_to_refresh
				.setOnFooterRefreshListener(new OnFooterRefreshListener() {

					@Override
					public void onFooterRefresh(PullToRefreshView view) {
						state = PULL_UP;
						page++;
						getBlogs();
					}
				});
		isInitView = true;
	}

	/** 获取发帖数据 */
	protected void getBlogs() {
		MyProgressDialog.show(context);
		if (api.equals("info/list")) {//广场数据
		HttpProxyUtil.getBlogs(context, "shipin,hai_id,hai_video,hai_photo,hai_petition,hai_name", query, keyboard, pageSize, page,	new ResponseListener() {

			@Override
			public void setResponseHandle(Message2 message) {
				initResponse(message);
			}

		}, null);
		}else {//知音动态
			HttpProxyUtil.getFriendsBlogs(context, "shipin", pageSize, page, 	new ResponseListener() {

				@Override
				public void setResponseHandle(Message2 message) {
					initResponse(message);
				}

			}, null);
		}
	}
	/**
	 * 获取广告
	 */
	private void getAd() {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "extend/getAdvList");
		map.put("classid", 6+"");
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {
					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData() != null
								&& !TextUtils.isEmpty(message.getData())) {
							imageUrlList=JSON.parseArray(message.getData(), ImageData.class);
						}
						getBlogs();
					}
				}, null));
	
	}
void initResponse(Message2 message){
	if (!message.getData().isEmpty()&&message.getData().startsWith("[")) {
		List<Blog> mList = JSON.parseArray(
				message.getData(), Blog.class);
		if (mList.size() == 0) {
			toast(getResources().getString(R.string.cannot_load_more));
		} else {
			blogsList.addAll(mList);
		}
	} else {
		toast(getResources().getString(R.string.cannot_load_more));
	}
		pull_to_refresh.onHeaderRefreshComplete();
		pull_to_refresh.onFooterRefreshComplete();
	initContentAdapter();
	MyProgressDialog.Cancel();
}
	OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long arg3) {
			if (blogsList.get(position).getCanvote() == 1) {//海选
				Intent intent = new Intent(context, VoteDetailsActivity.class);
				intent.putExtra("id", blogsList.get(position).getId() + "");
				intent.putExtra("classid",  blogsList.get(position).getClassid()+"");
				context.startActivity(intent);
			} else {//帖子
				Intent intent = new Intent(context,
						PlazaMusicDetailsActivity.class);
				intent.putExtra("id", blogsList.get(position).getId());
				intent.putExtra("classid", blogsList.get(position).getClassid());
				intent.putExtra("videourl", blogsList.get(position).getShipin());
				startActivity(intent);
			}
		}
	};
	protected String api = "info/list";
	OnCheckedChangeListener listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			state = NO_PULL;
			page = FIRST_PAGE;
			blogsList.clear();
			switch (checkedId) {
			case R.id.radioButton1:
				api = "info/list";
				query = null;
				break;
			case R.id.radioButton2:
				api = "info/list";
				query = "isgood";// 热门贴
				break;
			case R.id.radioButton3:
				api = "zhiyin/list";
				query = null;
				// type = 3;
				break;

			default:
				break;
			}
			getBlogs();
		}
	};

	/**
	 * 加载ContentAdapter数据
	 */
	protected void initContentAdapter() {
		if (isInitView) {
			Parcelable listState = lv_content.onSaveInstanceState();
//			if (api =="info/list") {
//				adapter = new MusicPlazaListAdapter(getActivity(), blogsList,imageUrlList,1);
//			}else {
				adapter = new MusicPlazaListAdapter(getActivity(), blogsList,imageUrlList,1);
//			}
			lv_content.setAdapter(adapter);
			lv_content.onRestoreInstanceState(listState);
		}

	}
}
