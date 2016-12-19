package com.greattone.greattone.activity.haixuan_and_activitise;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.WebActivity;
import com.greattone.greattone.activity.map.ShowMapActivity;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.SharePopWindow;
import com.greattone.greattone.entity.HaiXuan;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.FileUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;

import java.util.LinkedHashMap;

/** 海选详情 */
@SuppressWarnings("deprecation")
public class ActivitiyDetailsActivity1 extends BaseActivity {
	/** 图片 */
	private ImageView iv_icon;
	/** 分享 */
	private ImageView iv_shard;
	/** 名字 */
	private TextView tv_name;
	/** 时间 */
	private TextView tv_time;
	/** 举办地 */
	private TextView tv_address;
	/** 查看地图 */
	private TextView tv_see_map;
	/** 发布人 */
	private TextView tv_iss;
	/** 活动介绍 */
	private TextView tv_activity_des;
	/** 活动视频 */
	private TextView tv_activity_video;
	/** 活动评价 */
	private TextView tv_activity_comment;
	/** 活动总结 */
	private TextView tv_activity_summary;
	/** 活动费用 */
	private TextView tv_activity_cost;
	/** 评价数 */
	private TextView tv_evaluation_number;
	/** 报名 */
	private TextView tv_sign_up;
	private int type;
	protected HaiXuan haiXuan;
private int history;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activities);
		type = getIntent().getIntExtra("type", 0);
		history = getIntent().getIntExtra("history", 0);
		initView();
		getHaiXuan();
	}

	private void initView() {
		setHead(getResources().getString(R.string.活动详情), true, true);
		iv_shard = (ImageView) findViewById(R.id.iv_head_other);
		iv_shard.setVisibility(View.VISIBLE);
		iv_shard.setImageResource(R.drawable.icon_share);
		iv_shard.setOnClickListener(lis);

		iv_icon = (ImageView) findViewById(R.id.iv_icon);
		LayoutParams params = new LayoutParams(screenWidth
				- DisplayUtil.dip2px(context, 20),
				screenWidth- DisplayUtil.dip2px(context, 20));
//				(screenWidth*3/5 - DisplayUtil.dip2px(context, 10)));
		params.setMargins(DisplayUtil.dip2px(context, 10),
				DisplayUtil.dip2px(context, 10),
				DisplayUtil.dip2px(context, 10),
				DisplayUtil.dip2px(context, 10));
		iv_icon.setLayoutParams(params);

		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_address = (TextView) findViewById(R.id.tv_address);
		tv_see_map = (TextView) findViewById(R.id.tv_see_map);
		tv_see_map.setOnClickListener(lis);
		tv_iss = (TextView) findViewById(R.id.tv_iss);
		Drawable drawable = getResources().getDrawable(R.drawable.icon_next);
		drawable.setBounds(0, 0, DisplayUtil.dip2px(context, 15),
				DisplayUtil.dip2px(context, 15));
		tv_activity_des = (TextView) findViewById(R.id.tv_activity_des);
		tv_activity_des.setCompoundDrawables(null, null, drawable, null);
		tv_activity_des.setOnClickListener(lis);
		tv_activity_video = (TextView) findViewById(R.id.tv_activity_video);
		tv_activity_video.setCompoundDrawables(null, null, drawable, null);
		tv_activity_video.setOnClickListener(lis);
		tv_activity_comment = (TextView) findViewById(R.id.tv_activity_comment);
		tv_activity_comment.setCompoundDrawables(null, null, drawable, null);
		tv_activity_comment.setOnClickListener(lis);
		tv_activity_summary = (TextView) findViewById(R.id.tv_activity_summary);
		tv_activity_summary.setCompoundDrawables(null, null, drawable, null);
		tv_activity_summary.setOnClickListener(lis);
		tv_activity_cost = (TextView) findViewById(R.id.tv_activity_cost);
		params = new LayoutParams(screenWidth*2 / 5, LayoutParams.MATCH_PARENT);
		tv_activity_cost.setLayoutParams(params);
		tv_evaluation_number = (TextView) findViewById(R.id.tv_evaluation_number);
		params = new LayoutParams(screenWidth / 5, LayoutParams.MATCH_PARENT);
		tv_evaluation_number.setLayoutParams(params);
		tv_sign_up = (TextView) findViewById(R.id.tv_sign_up);
		params = new LayoutParams(screenWidth*2 / 5, LayoutParams.WRAP_CONTENT);
		tv_sign_up.setLayoutParams(params);
		if (type == 1) {// 活动
//			tv_activity_video.setText("活动视频");
			tv_activity_video.setVisibility(View.GONE);
			tv_activity_comment.setText(getResources().getString(R.string.活动评论));
			tv_activity_summary.setText(getResources().getString(R.string.活动总结));
		} else {// 海选
			tv_activity_video.setText(getResources().getString(R.string.海选投票));
			tv_activity_comment.setText(getResources().getString(R.string.活动评论));
			tv_activity_summary.setText(getResources().getString(R.string.活动总结));
		}
	}

	/** 获取活动详情 */
	private void getHaiXuan() {
		MyProgressDialog.show(context);
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("api", "info/detail");
		map.put("classid", getIntent().getIntExtra("classid", 0) + "");
		map.put("id", getIntent().getIntExtra("id", 0) + "");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData() != null
								&& message.getData().startsWith("{")) {
							haiXuan = JSON.parseObject(
									JSON.parseObject(message.getData())
											.getString("content"),
									HaiXuan.class);
							initViewData();
						}
						MyProgressDialog.Cancel();
					}
				}, null));
	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v == tv_see_map) {// 查看地图
				Intent intent = new Intent(context, ShowMapActivity.class);
				intent.putExtra("city", haiXuan.getKe_city());
				intent.putExtra("address", haiXuan.getDizhi());
				startActivity(intent);
			} else if (v == iv_shard) {// 分享
				SharePopWindow.build(context).setTitle(haiXuan.getTitle())
				.setContent("时间："+haiXuan.getHuodong_1()+"到"+haiXuan.getHuodong_2()+"\n举办地："+haiXuan.getDizhi())
				.setTOargetUrl(haiXuan.getTitleurl())
				.setIconPath(haiXuan.getTitlepic()).show();
			} else if (v == tv_activity_des) {// 活动介绍
				Intent intent = new Intent(context, WebActivity.class);
				   intent.putExtra("urlPath", FileUtil.getNoticeH5Url(haiXuan.getClassid()+"",haiXuan.getId()+""));
				intent.putExtra("title",getResources().getString(R.string.活动介绍));
				startActivity(intent);
			} else if (v == tv_activity_video) {// 活动视频 /海选投票
				Intent intent = new Intent();
				if (type == 1) {// 活动-活动视频
//					intent.setClass(context, ActivityVideoActivity.class);
				} else {// 海选-海选投票
					intent.setClass(context, HaiXuanVoteActivity.class);
				}
				intent.putExtra("id", haiXuan.getId());
				intent.putExtra("classid", haiXuan.getClassid());
				intent.putExtra("title", haiXuan.getTitle());
				intent.putExtra("history", history);
				startActivity(intent);
			} else if (v == tv_activity_comment) {// 活动评论
//				if (type == 1) {// 活动-活动评价
					Intent intent = new Intent(context,
							HaiXuanCommentActivity.class);
					intent.putExtra("classid", haiXuan.getClassid());
					intent.putExtra("id", haiXuan.getId());
					startActivity(intent);
//				} else {// 海选- 比赛公告
//					Intent intent = new Intent(context, WebActivity.class);
//					   intent.putExtra("urlPath", FileUtil.getNoticeH5Url(haiXuan.getClassid()+"",haiXuan.getId()+""));
//					intent.putExtra("title", "比赛公告");
//					startActivity(intent);
//				}
			} else if (v == tv_activity_summary) {// 活动总结
//				if (type == 1) {// 活动-活动总结
					Intent intent = new Intent(context, WebActivity.class);
					   intent.putExtra("urlPath", FileUtil.getSummaryH5Url(haiXuan.getClassid()+"",haiXuan.getId()+""));
					intent.putExtra("title",getResources().getString(R.string.活动总结));
					startActivity(intent);
//				} else {// 海选-活动评论
//					Intent intent = new Intent(context,
//							HaiXuanCommentActivity.class);
//					intent.putExtra("classid", haiXuan.getClassid());
//					intent.putExtra("id", haiXuan.getId());
//					startActivity(intent);
//				}
	
			} else if (v == tv_sign_up) {// 报名
				Intent intent = new Intent();
				if (type == 1) {// 活动-活动总结
					intent.setClass(context, ActivityApplyActivity.class);//活动报名
				}else {//海选报名
					if (haiXuan.getClassid().equals("32")) {//中华好琴声
						intent.setClass(context, ApplyActivity.class);
					}else if (haiXuan.getClassid().equals("33")) {//音乐精灵
						intent.setClass(context, ApplyActivity2.class);
					}else if (haiXuan.getClassid().equals("34")) {//好琴师
						intent.setClass(context, ApplyActivity3.class);
					}else if (haiXuan.getClassid().equals("110")) {//人气琴行
						intent.setClass(context, ApplyActivity4.class);
					}else if (haiXuan.getClassid().equals("112")) {//乐器品牌
						intent.setClass(context, ApplyActivity5.class);
					}else {//其他
						if (haiXuan.getClassid().equals("120")) {
							Class cls = null;
							try {
								cls = Class.forName("com.greattone.greattone.activity.haixuan_and_activitise.ApplyActivity"+haiXuan.getHai_bao_group());
							} catch (ClassNotFoundException e) {
								cls=ApplyActivity2.class;
								e.printStackTrace();
							}

							intent.setClass(context, cls);
						}else
						intent.setClass(context, ApplyActivity2.class);
					}
					intent.putExtra("title", haiXuan.getTitle());
					intent.putExtra("bitype", haiXuan.getBitype());
					intent.putExtra("baotype", haiXuan.getHai_baotype());
				}
				intent.putExtra("price", haiXuan.getPrice());
				intent.putExtra("id", haiXuan.getId());
				startActivityForResult(intent,1);
			}
		}
	};

	/** 加载数据 */
	protected void initViewData() {
		ImageLoaderUtil.getInstance().setImagebyurl(haiXuan.getTitlepic(),
				iv_icon);
		// ImageLoaderUtil.getInstance().setImagebyurl(
		// new HttpConstants(Constants.isDebug).ServerUrl + "/"
		// + haiXuan.getPic(), iv_icon);
		// 名字
		tv_name.setText(haiXuan.getTitle());
		// 时间
		// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",
		// Locale.CHINA);
		SpannableString span = new SpannableString(getResources().getString(R.string.活动时间)
				+ haiXuan.getHuodong_1() + getResources().getString(R.string.到) + haiXuan.getHuodong_2());
		span.setSpan(
				new ForegroundColorSpan(getResources().getColor(R.color.black)),
				0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		span.setSpan(
				new ForegroundColorSpan(getResources().getColor(
						R.color.red_b90006)), 4, span.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv_time.setText(span);
		// 举办地
		tv_address.setText(getResources().getString(R.string.举办活动地点)+ haiXuan.getDizhi());
		// 发布人
		tv_iss.setText(getResources().getString(R.string.发布人) + haiXuan.getUsername());
		// 活动费用
		if (haiXuan.getBitype().endsWith("人民币")) {
			span = new SpannableString(getResources().getString(R.string.活动费用)+"\n￥" + haiXuan.getPrice());
			span.setSpan(
					new ForegroundColorSpan(getResources().getColor(
							R.color.red_b90006)), 5, span.length(),
							Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			tv_activity_cost.setText(span);
		}else {
			span = new SpannableString(getResources().getString(R.string.活动费用)+"\n"+haiXuan.getBitype()+"$" + haiXuan.getPrice());
			span.setSpan(
					new ForegroundColorSpan(getResources().getColor(
							R.color.red_b90006)), 5, span.length(),
							Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			tv_activity_cost.setText(span);
		}
		// 评论数
		span = new SpannableString(getResources().getString(R.string.evaluation_number) + haiXuan.getPlnum());
		span.setSpan(
				new ForegroundColorSpan(getResources().getColor(
						R.color.red_b90006)), 2, span.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv_evaluation_number.setText(span);
		// 报名
		// if (haiXuan.getIssign() == 1) {
		// tv_sign_up.setText("已报名");
		// } else {
		tv_sign_up.setText(getResources().getString(R.string.sign_up));//我要报名
		if (haiXuan.getIshistory()==0&&haiXuan.getIsstart()==1) {
			tv_sign_up.setOnClickListener(lis);
		}else{
			tv_sign_up.setBackgroundColor(getResources().getColor(R.color.gray_7e7c7d));
		}
//		if (history==0) {
//			tv_sign_up.setOnClickListener(lis);
//		}else{
//			tv_sign_up.setBackgroundColor(getResources().getColor(R.color.gray_7e7c7d));
//		}
		// }
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
	}
}
