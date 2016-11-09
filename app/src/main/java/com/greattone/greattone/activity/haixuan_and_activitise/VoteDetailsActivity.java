package com.greattone.greattone.activity.haixuan_and_activitise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.VideoPlayActivity;
import com.greattone.greattone.activity.WebActivity;
import com.greattone.greattone.activity.plaza.EditCommentActivity;
import com.greattone.greattone.adapter.PlazaMusicDetailsListAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyHintDialog;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.SharePopWindow;
import com.greattone.greattone.entity.ActivityVideo;
import com.greattone.greattone.entity.Comment;
import com.greattone.greattone.entity.ImageData;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.Pic;
import com.greattone.greattone.entity.VoteOK;
import com.greattone.greattone.util.FileUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ErrorResponseListener;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.widget.MyBanner;
import com.greattone.greattone.widget.PullToRefreshView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/** 海选投票详情页面 */
public class VoteDetailsActivity extends BaseActivity {
	protected List<ImageData> imageUrlList;
	private boolean isplay;
//	private MyListView lv;
	private TextView m_title;
	private TextView m_name;
	private TextView m_hits;
	private ImageView m_pic;
	private TextView m_comment;
	private TextView m_vote;
//	private CommentDetailsAdapter adapter;
	ActivityVideo videoMsg;
	int page = 1;
	String classid;
	String id;
	int num,all=2;
	private ListView lv_content;
	List<Comment> commList = new ArrayList<Comment>();
	private PlazaMusicDetailsListAdapter adapter;
	private LinearLayout headView;
	private PullToRefreshView pull_to_refresh;
	private int history;
	private LinearLayout ll_gg;
	private ImageView m_gg1;
	private ImageView m_gg2;
	private MyBanner mybanner;
	private TextView tv_content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_list);
		// msg = (ActivityVideo) getIntent().getSerializableExtra("data");
		history = getIntent().getIntExtra("history", 0);
		id = getIntent().getStringExtra("id");
		classid = getIntent().getStringExtra("classid");
		MyProgressDialog.show(context);
		initView();
		getGG();
		getData();
//		getComments();
	}

	@SuppressLint("InflateParams")
	private void initView() {
		setHead(getResources().getString(R.string.活动详情), true, true);
		
		setOtherText("分享", lis);
		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);//
		lv_content = (ListView) findViewById(R.id.lv_content);// 正文
		headView = (LinearLayout) LayoutInflater.from(context).inflate(
				R.layout.activity_video_details, null);
		headView.	findViewById(R.id.activity_video_details_isvisibity).setVisibility(
				View.VISIBLE);
		if (classid.equals("104")) {//图片报名
			headView.	findViewById(R.id.rl_play_video).setVisibility(
					View.GONE);
			mybanner = (MyBanner)headView. findViewById(R.id.mybanner);
			mybanner.setLayoutParams(new LinearLayout.LayoutParams(screenWidth,
					screenWidth *2/3));
			mybanner.setAdaptive(true);
//			mybanner.seWidthToHeightRatio(3, 2);
		}
		this.m_title = ((TextView)headView. findViewById(R.id.activity_video_details_title));
		this.m_name = ((TextView)headView. findViewById(R.id.activity_video_details_name));
		this.m_hits = ((TextView) headView.findViewById(R.id.activity_video_details_hits));
		this.m_pic = ((ImageView) headView.findViewById(R.id.video_details_pic));
		this.tv_content = ((TextView) headView.findViewById(R.id.tv_content));//描述
		this.	m_gg1=(ImageView) (headView.findViewById(R.id.video_details_gg1));
		this.m_gg1.setOnClickListener(lis);
		this.	m_gg2=(ImageView) (headView.findViewById(R.id.video_details_gg2));
		this.m_gg2.setOnClickListener(lis);
		( headView.findViewById(R.id.video_details_gg2)).setOnClickListener(lis);
		this.ll_gg = ((LinearLayout) headView.findViewById(R.id.ll_gg));
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.m_pic
				.getLayoutParams();
		layoutParams.width = screenWidth;
		layoutParams.height = (2 * screenWidth / 3);
		this.m_pic.setLayoutParams(layoutParams);
		this.m_pic.setOnClickListener(lis);
		this.m_comment = ((TextView) headView.findViewById(R.id.activity_video_details_comment));
		this.m_vote = ((TextView) headView.findViewById(R.id.activity_video_details_vote));
//		this.lv_content = ((MyListView) findViewById(R.id.activity_video_details_lv));
		if (history==0) {
			headView.	findViewById(R.id.activity_video_details_vote_on).setOnClickListener(
					lis);
		}else{
			headView.	findViewById(R.id.activity_video_details_vote_on).setVisibility(View.GONE);
		}
		headView.findViewById(R.id.activity_video_details_mycomment).setOnClickListener(
				lis);
		
		adapter = new PlazaMusicDetailsListAdapter(context, commList);
		lv_content.addHeaderView(headView);
//		adapter.setOnItemClick(clickBack);
		lv_content.setAdapter(adapter);
		lv_content.setOnScrollListener(ImageLoaderUtil.getPauseOnScrollListener());
//		initViewData();
		getComments();
	}

	/** 获取广告*/
	private void getGG() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "extend/getAdvList");
		map.put("classid",13+"");
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {
					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData() != null
								&& !TextUtils.isEmpty(message.getData())) {
							imageUrlList=JSON.parseArray(message.getData(), ImageData.class);
							initGG();
						}
					}
				}, null));
	}
	protected void initGG() {
		LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.ll_gg
				.getLayoutParams();
		layoutParams2.width=screenWidth;
		layoutParams2.height=screenWidth*9/64;
		this.ll_gg.setLayoutParams(layoutParams2);
	if (imageUrlList!=null&&imageUrlList.size()>1) {
		ImageLoaderUtil.getInstance().setImagebyurl(imageUrlList.get(0).getPic(), m_gg1);
		ImageLoaderUtil.getInstance().setImagebyurl(imageUrlList.get(1).getPic(), m_gg2);
	}
	}

	/** 获取海选详情*/
	private void getData() {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("api", "info/detail");
		map.put("classid", classid);
		map.put("id", id);
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {
			
			@Override
			public void setResponseHandle(Message2 message) {
				if (message.getData() != null
						&& message.getData().startsWith("{")) {
					videoMsg = JSON.parseObject((JSON.parseObject(message.getData()).getString("content")),
							ActivityVideo.class);
					initViewData();
				}
				num++;
				MyProgressDialog.Cancel(num,all);
			}
		}, null));
	}
	/** 获取海选详情评论 */
	private void getComments() {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("api", "comment/list");
		map.put("classid", classid);
		map.put("id", id);
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
								List<Comment>  mlist=JSON.parseArray(message.getData(), Comment.class);
							commList.addAll(mlist);
						}
						pull_to_refresh.onHeaderRefreshComplete();
						pull_to_refresh.onFooterRefreshComplete();
						initContentAdapter();
						num++;
						MyProgressDialog.Cancel(num,all);
					}
				}, null));
	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.activity_video_details_mycomment:// 评论
				Intent intent = new Intent(context, EditCommentActivity.class);
				intent.putExtra("id", id);
				intent.putExtra("classid", classid);
				startActivityForResult(intent, 0);
				break;
			case R.id.activity_video_details_vote_on:// 投票
					voteOn();
				break;
			case R.id.video_details_pic:// 视频
				isplay = true;
				intent = new Intent(context, VideoPlayActivity.class);
				intent.putExtra("url",
						FileUtil.getNetWorkUrl( videoMsg.getHai_video()));
				context.startActivity(intent);
				break;
			case R.id.tv_head_other:// 分享
				SharePopWindow.build(context).setTitle(videoMsg.getHai_name()+"参加"+getIntent().getStringExtra("title")+"的比赛")
				.setContent("曲目："+videoMsg.getHai_petition())
				.setTOargetUrl(videoMsg.getTitleurl())
				.setIconPath(videoMsg.getHai_photo()).show();
				break;
			case R.id.video_details_gg1:// 广告1
				if (imageUrlList!=null&&imageUrlList.size()>1) {
					if (TextUtils.isEmpty(	imageUrlList.get(0).getUrl())) {
					intent = new Intent(context, WebActivity.class);
					intent.putExtra("urlPath",
							imageUrlList.get(0).getUrl());
					context.startActivity(intent);
					}
				}
				break;
			case R.id.video_details_gg2:// 广告2
				if (imageUrlList!=null&&imageUrlList.size()>1) {
					if (TextUtils.isEmpty(	imageUrlList.get(1).getUrl())) {
						intent = new Intent(context, WebActivity.class);
						intent.putExtra("urlPath",
								imageUrlList.get(1).getUrl());
						context.startActivity(intent);	
					}
				}
				break;
			default:
				break;
			}
		}
	};

	/** 加载数据 */
	protected void initViewData() {
		if (classid.equals("104")) {//图片报名
			mybanner.setVisibility(View.VISIBLE);
		 // 轮播
		 List<String> uriList = new ArrayList<String>();
		 if (videoMsg.getMorepic()!=null&&!TextUtils.isEmpty(videoMsg.getMorepic())) {
			List<Pic> mList = JSON.parseArray(videoMsg.getMorepic(), Pic.class);
		 for (int i = 0; i < mList.size(); i++) {
		 uriList.add(mList.get(i).getUrl());
		 }
		}
		 mybanner.setImageURI(uriList);
		 mybanner.start();
		}
		 
		ImageLoaderUtil.getInstance().setImagebyurl(videoMsg.getHai_photo(), m_pic);
		m_title.setText(videoMsg.getHai_petition());
		m_name.setText(videoMsg.getUsername());
		m_hits.setText(videoMsg.getOnclick());
		m_comment.setText(videoMsg.getPlnum());
		m_vote.setText(videoMsg.getTou_num() + "票");
		if (getIntent().getStringExtra("pclassid").equals("32")) {
			tv_content.setVisibility(View.GONE);
		}else{
			tv_content.setVisibility(View.VISIBLE);
			tv_content.setText(videoMsg.getSmalltext());//描述
		}
		if (videoMsg.getIshistory()==0) {
			headView.	findViewById(R.id.activity_video_details_vote_on).setOnClickListener(
					lis);
		}else{
			headView.	findViewById(R.id.activity_video_details_vote_on).setVisibility(View.GONE);
		}
	}

	protected void voteOn() {
		if (!isplay&&classid.equals("73")) {
			toast(getResources().getString(R.string.请先播放视频));
			return;
		}
		MyProgressDialog.show(context);
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("api", "extend/toupiao");
		map.put("classid", classid);
		map.put("id", id);
		map.put("hai_id", videoMsg.getHai_id());
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						VoteOK vote = new VoteOK();
						if (message.getData().startsWith("{")) {
							 vote=JSON.parseObject(message.getData(), VoteOK.class);
							 int position=(int) (Math.random()*vote.getGg().size());
							 MyHintDialog.showVoteHintDialog(context, vote.getGg().get(position).getPic(), vote.getGg().get(position).getUrl(), "总计："+vote.getTou_num()+"票\n排名第"+vote.getPm()+"名", true);
						}else {
							 MyHintDialog.showVoteHintDialog(context, "","", "", true);
						}
					
						MyProgressDialog.Cancel();
					}
				}, new ErrorResponseListener() {
					
					@Override
					public void setServerErrorResponseHandle(Message2 message) {
						VoteOK vote = new VoteOK();
						if (message.getData().startsWith("{")) {
							 vote=JSON.parseObject(message.getData(), VoteOK.class);
							 int position=(int) (Math.random()*vote.getGg().size());
							 MyHintDialog.showVoteHintDialog(context,vote.getGg().get(position).getPic(), vote.getGg().get(position).getUrl(), message.getInfo(), false);
						}else {
							 MyHintDialog.showVoteHintDialog(context, "","", message.getInfo(), false);
						}
						MyProgressDialog.Cancel();
					}
					
					@Override
					public void setErrorResponseHandle(VolleyError error) {
						
					}
				}));
	}
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
		if (arg1==RESULT_OK&&arg0==0) {//评论成功
			commList.clear();
			getComments();
		}
	}
	
	@Override
	public void onRestart() {
		super.onRestart();
		if (classid.equals("104")) {
			mybanner.start();
			
		}
	}

	@Override
	public void onStop() {
		if (classid.equals("104")) {
			mybanner.stop();
		}
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		if (classid.equals("104")) {
			mybanner.stop();
		}
		super.onDestroy();
	}
}
