package com.greattone.greattone.activity.plaza;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.VideoPlayActivity;
import com.greattone.greattone.activity.WebActivity;
import com.greattone.greattone.activity.celebrity.CelebrityActivity;
import com.greattone.greattone.activity.classroom.ClassRoomActivity;
import com.greattone.greattone.activity.teacher.TeacherActivity;
import com.greattone.greattone.adapter.PlazaMusicDetailsListAdapter;
import com.greattone.greattone.data.ClassId;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.NormalPopuWindow.OnItemClickBack;
import com.greattone.greattone.dialog.ReplayDialog;
import com.greattone.greattone.dialog.SharePopWindow;
import com.greattone.greattone.entity.Blog;
import com.greattone.greattone.entity.Comment;
import com.greattone.greattone.entity.ImageData;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.Pic;
import com.greattone.greattone.entity.UserInfo;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.FileUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.util.MessageUtil;
import com.greattone.greattone.widget.MyRoundImageView;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/** 音乐广场详情 */
public class PlazaMusicDetailsActivity extends BaseActivity {
	private int id;
	private int classid;
	private boolean isInitView = false;
	/**
	 * 第一页
	 */
	private final int FIRST_PAGE = 1;
	/**
	 * 当前加载的页数
	 */
	private int page = FIRST_PAGE;
	List<Comment> commList = new ArrayList<Comment>();
	Blog blog;
	PlazaMusicDetailsListAdapter adapter;
	private PullToRefreshView pull_to_refresh;
	private ListView lv_content;
	private MyRoundImageView iv_icon;
	private TextView tv_name;
//	private TextView tv_city;
	private TextView tv_time;
	private TextView tv_title;
	private TextView tv_content;
	private LinearLayout ll_pic;
	private RelativeLayout rl_video;
	private ImageView iv_videopic;
	private TextView tv_comment;
	private LinearLayout ll_pinglun;
	private TextView tv_commnum;
	private TextView tv_zfnum;
	private LinearLayout ll_zf;
//	private LinearLayout ll_collect;
	private LinearLayout ll_share;
	private LinearLayout headView;
//	private ImageView iv_level;
//	private TextView tv_collect;
//private ImageView iv_music_play;
private ImageView iv_ad;
private LinearLayout ll_like;
private WebView wv_music_play;
	private TextView tv_like;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_list);
		this.id = getIntent().getIntExtra("id", 0);
		this.classid = getIntent().getIntExtra("classid",0);
		initView();
		getBlogsDynamicDetail();
		getCommentList();
	}

	@SuppressLint("InflateParams")
	private void initView() {
		if ( getIntent().getStringExtra("title")==null) {
			setHead(getResources().getString(R.string.音乐广场详情), true, true);
		}else {
			setHead(getIntent().getStringExtra("title"), true, true);
		}
		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);//
		lv_content = (ListView) findViewById(R.id.lv_content);// 正文
		lv_content.setOnScrollListener(ImageLoaderUtil.getPauseOnScrollListener());
		headView = (LinearLayout) LayoutInflater.from(context).inflate(
				R.layout.head_plaza_music_details, null);
//		lv_content.setOnItemClickListener(itemClickListener);
		pull_to_refresh.setOnHeaderRefreshListener(headerRefreshListener);
		pull_to_refresh.setOnFooterRefreshListener(footerRefreshListener);
		adapter = new PlazaMusicDetailsListAdapter(context, commList);
		lv_content.addHeaderView(headView);
		adapter.setOnItemClick(clickBack);
		lv_content.setAdapter(adapter);
		
		tv_name = (TextView) headView.findViewById(R.id.tv_name);//
//		iv_level = (ImageView) headView.findViewById(R.id.iv_level);//
		tv_time = (TextView) headView.findViewById(R.id.tv_time);//
//		tv_city = (TextView) headView.findViewById(R.id.tv_city);//
		tv_title = (TextView) headView.findViewById(R.id.tv_title);//
		tv_content = (TextView) headView.findViewById(R.id.tv_content);//
		ll_pic = (LinearLayout) headView.findViewById(R.id.ll_pic);//
		iv_videopic = (ImageView) headView.findViewById(R.id.iv_videopic);//
		RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(
				(screenWidth - DisplayUtil.dip2px(context,
						5 * 5 + 8)) / 4,
				(screenWidth - DisplayUtil.dip2px(context,
						5 * 5 + 8)) / 4);
		iv_videopic.setLayoutParams(params1);
		rl_video = (RelativeLayout) headView.findViewById(R.id.rl_video);//
		tv_comment = (TextView) headView.findViewById(R.id.tv_comment);//
//		tv_collect = (TextView) headView.findViewById(R.id.tv_collect);//
		ll_pinglun = (LinearLayout) headView.findViewById(R.id.ll_comments_co);//
		tv_commnum = (TextView) headView.findViewById(R.id.adapter_comments_commnum);//
		tv_zfnum = (TextView) headView.findViewById(R.id.tv_reprnum);//
		ll_zf = (LinearLayout) headView.findViewById(R.id.ll_zhuanfa);//
//		ll_collect = (LinearLayout) headView.findViewById(R.id.ll_shoucang);//
		ll_share = (LinearLayout) headView.findViewById(R.id.ll_share);//
		ll_like=	(LinearLayout)headView.findViewById(R.id.ll_like);//
		tv_like = (TextView) headView.findViewById(R.id.tv_like);//
		iv_icon = (MyRoundImageView) headView.findViewById(R.id.iv_icon);//
		iv_icon.setRadius(DisplayUtil.dip2px(context, 15));
		wv_music_play = (WebView) headView.findViewById(R.id.wv_music_play);//
		
//		iv_music_play = (ImageView) headView.findViewById(R.id.iv_music_play);//
		iv_ad = (ImageView) headView.findViewById(R.id.iv_ad);//
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				screenWidth, screenWidth/3/3);
		params.topMargin=DisplayUtil.dip2px(context, 10);
		iv_ad.setLayoutParams(params);

		// lv_content.addHeaderView(headView);
	}

	/** 获取音乐广场信息详情 */
	protected void getBlogsDynamicDetail() {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "info/detail");
		map.put("classid", classid+"");
		map.put("id", id+"");
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
							blog = JSON.parseObject(
									JSON.parseObject(message.getData()).getString(
											"content"), Blog.class);
							initViewData();
					}
				}, null));
	}

	/** 获取评论信息 */
	protected void getCommentList() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "comment/list");
		map.put("classid", classid+"");
		map.put("id", id+"");
		map.put("pageSize", 20+"");
		map.put("pageIndex", page+"");
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData().startsWith("[")) {
							List<Comment>  mlist=JSON.parseArray(message.getData(), Comment.class);
							commList.addAll(mlist);
						}
						pull_to_refresh.onHeaderRefreshComplete();
						pull_to_refresh.onFooterRefreshComplete();
						initContentAdapter();
						MyProgressDialog.Cancel();
					}

				}, null));
	}

	OnHeaderRefreshListener headerRefreshListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
			page = FIRST_PAGE;
			commList.clear();
			getCommentList();
		}
	};
	OnFooterRefreshListener footerRefreshListener = new OnFooterRefreshListener() {

		@Override
		public void onFooterRefresh(PullToRefreshView view) {
			page++;
			getCommentList();
		}
	};

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v == tv_comment) {//评论
				Intent intent = new Intent(context, EditCommentActivity.class);
				intent.putExtra("id", blog.getId()+"");
//				intent.putExtra("cid", mList.get(position).getCid());
				intent.putExtra("classid", blog.getClassid()+"");
			startActivityForResult(intent, 101);
			} else if (v == rl_video) {//视频播放
				Intent intent = new Intent(context, VideoPlayActivity.class);
				intent.putExtra("url", FileUtil.getNetWorkUrl( blog.getShipin()));
				context.startActivity(intent);
			} else if (v == ll_zf) {
			} else if (v == ll_share) {//分享
				share();
//			} else if (v == ll_collect) {//收藏
//				collect();
			} else if (v == iv_icon) {//头像
				toCenter();
//			} else if (v == iv_music_play) {//音乐播放
//				Intent intent = new Intent(context, VideoPlayActivity.class);
//				intent.putExtra("url", FileUtil.getNetWorkUrl( blog.getYinyue())); 
//				intent.putExtra("type", 1); 
//				context.startActivity(intent);
			} else if (v == ll_like) {//点赞
				ClickLike();
			} else if (v == iv_ad) {//广告
				toWeb(imageData);
			}
		}
	};
	/**点击回复*/
	private OnItemClickBack clickBack=new OnItemClickBack() {
		
		@Override
		public void OnClick(int position, final String repid) {
			ReplayDialog localReplayDialog = new ReplayDialog(context);
			localReplayDialog.setListener(new ReplayDialog.OnReplayListener() {

				@Override
				public void OnReplay(String text) {
					MyProgressDialog.show(context);
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("api", "comment/post");
					map.put("classid", blog.getClassid() + "");
					map.put("id", blog.getId() + "");
					map.put("repid", repid);
					map.put("saytext", text);
					map.put("loginuid", Data.user.getUserid());
					map.put("logintoken", Data.user.getToken());
					((BaseActivity) context).addRequest(HttpUtil
							.httpConnectionByPost(context, map,
									new ResponseListener() {

										@Override
										public void setResponseHandle(
												Message2 message) {
											((BaseActivity) context)
													.toast(message.getInfo());
											page=1;
											commList.clear();
											getCommentList();
											MyProgressDialog.Cancel();
										}

									}, null));
				}
			});
			localReplayDialog.show();
		}
	};
	private ImageData imageData;
	private webViewClient client;
	private WebChromeClient chromeClient;
	protected void initViewData() {
		if (isInitView) {
			return;
		}
		isInitView = true;
		UserInfo info = JSON.parseObject(blog.getUserInfo(), UserInfo.class);
		ll_pic.setVisibility(View.GONE);
		rl_video.setVisibility(View.GONE);
		ImageLoaderUtil.getInstance().setImagebyurl(info.getUserpic(), iv_icon);
		iv_icon.setOnClickListener(lis);
//		ImageLoaderUtil.getInstance().setImagebyurl(info.getLevel().getPic(), iv_level);
		tv_name.setText(info.getUsername() + "  |  " +MessageUtil.getIdentity(info)+"  |  " + info.getLevel().getName());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm",
				Locale.CHINA);
		tv_time.setText(format.format(new Date(Long.valueOf(blog
				.getLastdotime()) * 1000)));
		tv_title.setText(blog.getTitle());
		tv_content.setText(blog.getNewstext());
		tv_like.setText(blog.getDiggtop());
		// tv_city.setText(blog.getCity());
		tv_commnum.setText(blog.getPlnum());
		tv_zfnum.setText(blog.getResendnum());
		tv_comment.setOnClickListener(lis);
		rl_video.setOnClickListener(lis);
		ll_pinglun.setOnClickListener(lis);
		ll_zf.setOnClickListener(lis);
//		ll_collect.setOnClickListener(lis);
		ll_share.setOnClickListener(lis);
		ll_like.setOnClickListener(lis);//
		//广告
		if (!TextUtils.isEmpty(blog.getAd())) {
			iv_ad.setVisibility(View.VISIBLE);
			imageData=JSON.parseObject(blog.getAd(), ImageData.class);
			ImageLoaderUtil.getInstance().setImagebyurl(imageData.getPic(),
					iv_ad);
			iv_ad.setOnClickListener(lis);
		}
		if (blog.getClassid() == ClassId.音乐广场_视频_ID) {// 视频
			rl_video.setVisibility(View.VISIBLE);
			ImageLoaderUtil.getInstance().setImagebyurl(blog.getTitlepic(),
					iv_videopic);
		} else if (blog.getClassid() == ClassId.音乐广场_音乐_ID) {// 音乐
			initWebViewToPlayMusic();
//			iv_music_play.setVisibility(View.VISIBLE);
//			iv_music_play.setOnClickListener(lis);
		} else if (blog.getClassid() == ClassId.音乐广场_图片_ID) {// 图片
			ll_pic.setVisibility(View.VISIBLE);
			ll_pic.removeAllViews();
			if (blog.getMorepic() != null) {
				addPic(JSON.parseArray(blog.getMorepic(), Pic.class));
			}
		}
		// private LinearLayout ll_pic;

	}

	@SuppressLint("SetJavaScriptEnabled")
	private void initWebViewToPlayMusic() {
		WebSettings webSettings = wv_music_play.getSettings();
//		 设置WebView属性，能够执行Javascript脚本
		webSettings.setJavaScriptEnabled(true);
		 // 设置可以访问文件
//		 webSettings.setAllowFileAccess(true);
		// 设置支持缩放
//		webSettings.setBuiltInZoomControls(true);
		// 加载需要显示的网页
		String	 urlPath = "http://M.greattone.net/app/music.php?classid="+blog.getClassid()+"&id="+blog.getId();
		wv_music_play.loadUrl(urlPath);
		 client=new webViewClient();
		  chromeClient=new WebChromeClient();
		// 设置Web视图
		wv_music_play.setWebViewClient(client);
		wv_music_play.setWebChromeClient(chromeClient);
		wv_music_play.setVisibility(View.VISIBLE);
	}

	protected void ClickLike() {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "comment/dianzan");
		map.put("classid", blog.getClassid()+"");
		map.put("id", blog.getId()+"");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		((BaseActivity) context).addRequest(HttpUtil.httpConnectionByPost(
				context, map, new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						blog.setDiggtop(Integer.valueOf(blog.getDiggtop())+1+"");
						tv_like.setText(blog.getDiggtop());
						((BaseActivity) context).toast(message.getInfo());
						MyProgressDialog.Cancel();
					}
				}, null));
	}

	// /** 添加图片 */
	// private void addPic2(List<Pic> list) {
	// for (int i = 0; i < list.size(); i++) {
	// ImageView imageView = new ImageView(context);
	// LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
	// (screenWidth - DisplayUtil.dip2px(context, 16)) / 4,
	// (screenWidth - DisplayUtil.dip2px(context, 16)) / 4);
	//
	// params.setMargins(DisplayUtil.dip2px(context, 5),
	// DisplayUtil.dip2px(context, 5),
	// DisplayUtil.dip2px(context, 5),
	// DisplayUtil.dip2px(context, 5));
	// imageView.setPadding(DisplayUtil.dip2px(context, 5),
	// DisplayUtil.dip2px(context, 5),
	// DisplayUtil.dip2px(context, 5),
	// DisplayUtil.dip2px(context, 5));
	// imageView.setLayoutParams(params);
	// imageView.setScaleType(ScaleType.FIT_XY);
	// com.greattone.greattone.util.ImageLoaderUtil.getInstance()
	// .setImagebyurl(list.get(i).getSmallpic(), imageView);
	// ll_pic.addView(imageView);
	// }
	// }
	/** 添加图片 */
	private void addPic(final List<Pic> list) {
		int lineNum=3;
		for (int i = 0; i <= (list.size() - 1) / lineNum; i++) {
			int num = lineNum;
			if (i == (list.size() - 1) / lineNum) {
				if (list.size() % lineNum != 0) {
					num = list.size() % lineNum;
				}
			}
			LinearLayout layout = new LinearLayout(context);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			layoutParams.topMargin = 20;
			layoutParams.leftMargin=(screenWidth - DisplayUtil.dip2px(context, 5 * 5 + 8)) / 8;
			layout.setLayoutParams(layoutParams);
			layout.setOrientation(LinearLayout.HORIZONTAL);
			for (int j = 0; j < num; j++) {
				ImageView imageView = new ImageView(context);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						(screenWidth - DisplayUtil.dip2px(context, 5 * 5 + 8)) / 4,
						(screenWidth - DisplayUtil.dip2px(context, 5 * 5 + 8)) / 4);
				if (j == lineNum-1) {
					params.setMargins(DisplayUtil.dip2px(context, 5), 0,
							DisplayUtil.dip2px(context, 5), 0);
				} else {
					params.setMargins(DisplayUtil.dip2px(context, 5), 0, 0, 0);
				}
				imageView.setLayoutParams(params);
				imageView.setScaleType(ScaleType.FIT_XY);
				final int  mPosition=j + i * lineNum;
				ImageLoaderUtil.getInstance().setImagebyurl(
						list.get(mPosition).getSmallpic(), imageView);
				imageView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						ArrayList<String> mList=new ArrayList<String>();
						for (int k = 0; k < list.size(); k++) {
							mList.add(list.get(k ).getSmallpic());
						}
						Intent intent=new Intent(context, ShowPictureActivity.class);
						intent.putStringArrayListExtra("uriList", mList);
						intent.putExtra("position",mPosition);
						((Activity)context).startActivity(intent);
					}
				});
				layout.addView(imageView);

			}
			ll_pic.addView(layout);
		}

	}

	/** 评论 */
	protected void comment() {
		ReplayDialog localReplayDialog = new ReplayDialog(context);
		localReplayDialog.setListener(new ReplayDialog.OnReplayListener() {

			@Override
			public void OnReplay(String text) {
				MyProgressDialog.show(context);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("api", "comment/post");
				map.put("classid", blog.getClassid()+"");
				map.put("id", blog.getId()+"");
				map.put("saytext", text);
				map.put("loginuid", Data.user.getUserid());
				map.put("logintoken", Data.user.getToken());
				((BaseActivity) context).addRequest(HttpUtil
						.httpConnectionByPost(context, map,
								new ResponseListener() {

									@Override
									public void setResponseHandle(
											Message2 message) {
										((BaseActivity) context).toast(message
												.getInfo());
									}

								}, null));
			}
		});
		localReplayDialog.show();
	}

//	/** 收藏 */
//	protected void collect() {
//		// String url;
//		// if (blog.getCollect().equals("1")) {
//		// url = HttpConstants.BLOG_UNCOLLECT_URL;
//		// } else {
//		// url = HttpConstants.BLOG_COLLECT_URL;
//		// }
//		MyProgressDialog.show(context);
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("api", "fava/addFava");
//		map.put("classid", blog.getClassid()+"");
//		map.put("id", blog.getId()+"");
//		if (Data.favaList.size() > 0) {
//			map.put("cid", Data.favaList.get(0).getCid()+"");
//		}
//		map.put("loginuid", Data.user.getUserid());
//		map.put("logintoken", Data.user.getToken());
//		((BaseActivity) context).addRequest(HttpUtil.httpConnectionByPost(
//				context, map, new ResponseListener() {
//
//					@Override
//					public void setResponseHandle(Message2 message) {
//						((BaseActivity) context).toast(message.getInfo());
//						if (blog.getIsfava() == 1) {
//							tv_collect.setText(getResources().getString(R.string.收藏));
//							blog.setIsfava(0);
//						} else {
//							tv_collect.setText(getResources().getString(R.string.已收藏));
//							blog.setIsfava(1);
//						}
//						MyProgressDialog.Cancel();
//					}
//
//				}, null));
//	}

	/** 分享 */
	protected void share() {
		String titlePic = null;
		if (blog.getClassid() == ClassId.音乐广场_视频_ID) {// 视频
			titlePic=blog.getTitlepic();
		} else if (blog.getClassid() == ClassId.音乐广场_音乐_ID) {// 音乐
			titlePic=JSON.parseObject(blog.getUserInfo(), UserInfo.class).getUserpic();
		} else if (blog.getClassid() == ClassId.音乐广场_图片_ID) {// 图片
			titlePic=JSON.parseArray(blog.getMorepic(), Pic.class).get(0).getSmallpic();
		}
		SharePopWindow.build(context).setTitle(blog.getTitle())
		.setContent(blog.getSmalltext())
		.setTOargetUrl(blog.getTitleurl()).setIconPath(titlePic)
		.show();
//		ShareUtil
//				.startShare(
//						context,
//						blog.getTitle(),
//						blog.getSmalltext(),
//						titlePic,
//						blog.getTitleurl()
//						,
//						new UMShareListener() {
//
//							@Override
//							public void onResult(SHARE_MEDIA arg0) {
//								((BaseActivity) context).toast(getResources().getString(R.string.分享成功));
//							}
//
//							@Override
//							public void onError(SHARE_MEDIA arg0, Throwable arg1) {
//								((BaseActivity) context).toast(getResources().getString(R.string.分享失败));
//							}
//
//							@Override
//							public void onCancel(SHARE_MEDIA arg0) {
//								((BaseActivity) context).toast(getResources().getString(R.string.分享取消));
//							}
//						}
//						);
	}
	/**广告跳转*/
	protected void toWeb(ImageData imageData) {
		if (!TextUtils.isEmpty((imageData.getUrl()))) {
			Intent intent=new Intent(context, WebActivity.class);
			intent.putExtra("urlPath", imageData.getUrl());
			intent.putExtra("title", imageData.getTitle());
			context.startActivity(intent);
		}
	}
	/** 跳转到个人中心 */
	protected void toCenter() {
		int group = JSON.parseObject(blog.getUserInfo(), UserInfo.class)
				.getGroupid();
//		Intent intent = new Intent();
//			intent.setClass(context, CelebrityActivity.class);
//			intent.putExtra("id", blog.getUserid() + "");
//			intent.putExtra("groupid", blog.getGroupid());
//		context.startActivity(intent);
		Intent intent = new Intent();
		if (group == 1 || group == 2) {// 普通会员和名人
			intent.setClass(context, CelebrityActivity.class);
			intent.putExtra("id",blog.getUserid() + "");
			intent.putExtra("groupid",group);
		} else if (group == 3) {// 老师
			intent.setClass(context, TeacherActivity.class);
			intent.putExtra("id",  blog.getUserid() + "");
		} else if (group == 4) {// 教室
			intent.setClass(context, ClassRoomActivity.class);
			intent.putExtra("id",  blog.getUserid() + "");
		}
		context.startActivity(intent);
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK&&requestCode==101) {//评论
			commList.clear();
			blog.setPlnum(	(Integer.valueOf(blog.getPlnum())+1)+"");
			tv_commnum.setText(blog.getPlnum());
			getCommentList();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	// Web视图
	private class webViewClient extends WebViewClient {
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
//			view.loadUrl(url);
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		} 
	}
	@Override
	protected void onPause() {
//		chromeClient.onCloseWindow(wv_music_play);
		if (wv_music_play!=null) {
			wv_music_play.reload();
		}
		super.onPause();
	}
}
