package com.greattone.greattone.activity2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.VideoPlayActivity;
import com.greattone.greattone.adapter.TeacherVideoContentListAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.Video;
import com.greattone.greattone.util.FileUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;

/*** 推荐视频 */
public class RecommendedVideoActivity extends BaseActivity {
	/** 老师视频 */
	private final int CID_TEACHER = 1;
	/** 学生视频 */
	private final int CID_STUDENT = 2;
	/** 优秀视频 */
	private final int CID_EXCELLENT = 3;
	/** 视频类型 */
	int cid = CID_EXCELLENT;

	private List<Video> videoList = new ArrayList<Video>();
	private RadioGroup radiogroup;
	private ListView lv_content;
	/** 页数 */
	private int page = 1;
	private int pagesize =20;

//	private final int NO_PULL = 0;// 其他
//	private final int PULL_UP = 2;// 上拉
//	private final int PULL_DOWN = 1;// 下拉
//	/**
//	 * 加载数据时的状态 判断是其他，上拉还是下拉，NO_PULL其他，PULL_DOWN下拉，PULL_UP上拉
//	 * */
//	int state = NO_PULL;
	private PullToRefreshView pull_to_refresh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recommended_video);
		initView();
		getTeacherVideo();
	}

	private void initView() {
		setHead(getResources().getString(R.string.recommended_video), true, true);//推荐视频

		radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
		radiogroup.check(R.id.rb_video1);
		radiogroup.setOnCheckedChangeListener(listener);
		radiogroup.setVisibility(View.GONE);
		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);//
		lv_content = (ListView) findViewById(R.id.lv_content);
		pull_to_refresh.setOnHeaderRefreshListener(headerRefreshListener);
		pull_to_refresh.setOnFooterRefreshListener(footerRefreshListener);
		lv_content.setOnItemClickListener(itemClickListener);
	}

	OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long arg3) {
			Intent intent = new Intent(context, VideoPlayActivity.class);
			intent.putExtra("url", FileUtil.getNetWorkUrl( videoList.get(position).getShipin()));
			startActivity(intent);
		}
	};

	private void getTeacherVideo() {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "jiaoshi/video");
		map.put("userid", getIntent().getStringExtra("id"));
		map.put("extra", "shipin");
		map.put("pageSize", pagesize+"");
		map.put("pageIndex", page+"");
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
							if (mList.size() == 0) {
								toast(getResources().getString(R.string.cannot_load_more));
							}
							videoList.addAll(mList);
						}
						pull_to_refresh.onHeaderRefreshComplete();
						pull_to_refresh.onFooterRefreshComplete();
						initContentAdapter();
						MyProgressDialog.Cancel();
					}
				}, null));
 	}

	OnCheckedChangeListener listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.radioButton1:
				cid = CID_EXCELLENT;
//				state = NO_PULL;
				videoList.clear();
				getTeacherVideo();

				break;
			case R.id.radioButton2:
				cid = CID_TEACHER;
//				state = NO_PULL;
				videoList.clear();
				getTeacherVideo();

				break;
			case R.id.radioButton3:
				cid = CID_STUDENT;
//				state = NO_PULL;
				videoList.clear();
				getTeacherVideo();
				break;

			default:
				break;
			}
		}
	};

	OnFooterRefreshListener footerRefreshListener = new OnFooterRefreshListener() {

		@Override
		public void onFooterRefresh(PullToRefreshView view) {
//			state = PULL_UP;
			page++;
			getTeacherVideo();
		}
	};
	OnHeaderRefreshListener headerRefreshListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
//			state = PULL_DOWN;
			page = 1;
			videoList.clear();
			getTeacherVideo();
		}
	};

	/**
	 * 加载ContentAdapter数据
	 */
	protected void initContentAdapter() {
		Parcelable listState = lv_content.onSaveInstanceState();
		TeacherVideoContentListAdapter adapter2 = new TeacherVideoContentListAdapter(
				context, videoList);
		lv_content.setAdapter(adapter2);
		lv_content.onRestoreInstanceState(listState);
	}
}
