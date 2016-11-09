package com.greattone.greattone.activity2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.adapter.MyRecomendationListAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.NormalPopuWindow;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.Video;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;
/**推荐视频*/
public class MyRecomendationActivity extends BaseActivity {
	protected List<Video> videoList = new ArrayList<Video>();
	private RadioGroup radiogroup;
	private RadioButton radioButton1;
	private RadioButton radioButton2;
	private PullToRefreshView pull_to_refresh;
	private ListView lv_content;
	/** 页数 */
	private int page = 1;
	int cid = 0;
	String api ="tuijian/classroom/mylist";
	private MyRecomendationListAdapter adapter;
	private TextView tv_search;
	private View ll_search;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_post);
		initView();
		getData();
	}

	private void getData() {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api",api);
//		map.put("ismember", "1");
//		map.put("extra", "price,tprice,ke_hour");
//		map.put("userid", Data.myinfo.getUserid());
//		map.put("classid", ClassId.音乐教室_课程_ID + "");
		map.put("pageIndex", page + "");
		map.put("pageSize", "20");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData() != null
								&& message.getData().startsWith("[")) {
							List<Video> mList = JSON.parseArray(
									message.getData(), Video.class);
							if (mList != null && !mList.isEmpty()) {
								videoList.addAll(mList);
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

	private void initView() {
		setHead(getResources().getString(R.string.新增视频推荐), true, true);
		radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
		radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
		radioButton1.setText(getResources().getString(R.string.recommended_video));//推荐视频
		radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
		radioButton2.setText(getResources().getString(R.string.全部视频));
	findViewById(R.id.radioButton3).setVisibility(View.GONE);
	findViewById(R.id.radioButton4).setVisibility(View.GONE);
		radiogroup.check(R.id.radioButton1);
		radiogroup.setOnCheckedChangeListener(listener);

		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);//
		pull_to_refresh.setOnHeaderRefreshListener(headerRefreshListener);
		pull_to_refresh.setOnFooterRefreshListener(footerRefreshListener);
		lv_content = (ListView) findViewById(R.id.lv_content);
		adapter = new MyRecomendationListAdapter(context, videoList);
		adapter.setType(0);
		lv_content.setAdapter(adapter);
		ll_search = findViewById(R.id.ll_search);
		ll_search.setVisibility(View.GONE);
		ll_search.setOnClickListener(lis);//
		tv_search = (TextView) findViewById(R.id.tv_search);
		tv_search.setText(getResources().getString(R.string.全部));

	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (pop == null) {
				initPop();
			}
			pop.show();
		}
	};

	private OnCheckedChangeListener listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.radioButton1:
				page = 1;
				ll_search.setVisibility(View.GONE);
				adapter.setType(0);
				api="tuijian/classroom/mylist";
				videoList.clear();
				getData();
				break;
			case R.id.radioButton2:
				page = 1;
				ll_search.setVisibility(View.GONE);
				api="tuijian/classroom/list";
				adapter.setType(1);
				videoList.clear();
				getData();
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
			getData();
		}
	};
	OnHeaderRefreshListener headerRefreshListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
			page = 1;
			videoList.clear();
			getData();
		}
	};
	private NormalPopuWindow pop;

	/** 加载PopuWindow */
	private void initPop() {
		final String[] arrayOfString =getResources().getStringArray(R.array.video_type);
		this.pop = new NormalPopuWindow(context, Arrays.asList(arrayOfString),
				ll_search);
		this.pop.setOnItemClickBack(new NormalPopuWindow.OnItemClickBack() {
			public void OnClick(int position,String text) {
				pop.dismisss();
				cid = (position + 1);
				tv_search.setText(arrayOfString[position]);
				page = 1;
				videoList.clear();
				getData();
			}
		});
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
