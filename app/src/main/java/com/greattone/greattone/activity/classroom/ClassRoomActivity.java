package com.greattone.greattone.activity.classroom;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.celebrity.CelebrityActivity;
import com.greattone.greattone.activity.chat.MyChatActivity;
import com.greattone.greattone.activity.course.CourseCenterActivity;
import com.greattone.greattone.activity.map.ShowMapActivity;
import com.greattone.greattone.activity.qa.AskQuestionActivity;
import com.greattone.greattone.activity.rent.RentKotofusaActivity;
import com.greattone.greattone.activity2.RecommendedVideoActivity;
import com.greattone.greattone.data.ClassId;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.SharePopWindow;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.UserInfo;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.MyBanner;

import java.util.ArrayList;
import java.util.List;

/** 教室详情 */
@SuppressWarnings("deprecation")
public class ClassRoomActivity extends BaseActivity {
	/** 我要提问 */
	private TextView ask;
	/** 名字 */
	private TextView name;
	/** 推荐视频 */
	private View recommended_video;
	/** 课程中心 */
	private View course_center;
	/** 介绍评论 */
	private View comments;
	/** 活动公告 */
	private View announcements;
	/** 私信 */
	private TextView talk;
	/** 关注 */
	private TextView focus;
//	/** 星数 */
//	private RatingBar ratingbar;
	/** 服务 */
	private TextView tv_service;
	/** 环境 */
	private TextView tv_environment;
	/** 教学质量 */
	private TextView tv_quality;
	/** 电话 */
	private TextView tv_telphone;
	/** 地址 */
	private TextView tv_address;
	/** 查看地图 */
	private TextView tv_see_map;
	/** 音乐老师 */
	private View tv_teacher;
	/** 琴房租赁 */
	private View tv_room_lease;
	/** 轮播控件 */
	private MyBanner mybanner;
	private View tv_student;
	private View tv_tlq;
	private ImageView iv_share;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classroom);
		initView();
		getpeopleInfo();
	}

	private void initView() {
		setHead(getResources().getString(R.string.classroom_detail), true, true);//教室详情
		ask = (TextView) findViewById(R.id.tv_head_other);
		ask.setVisibility(View.VISIBLE);
		ask.setTextSize(13);
		ask.setText(getResources().getString(R.string.I_need_to_ask_a_question));
		ask.setOnClickListener(lis);

		mybanner = (MyBanner) findViewById(R.id.mybanner);
		mybanner.setLayoutParams(new LinearLayout.LayoutParams(screenWidth,
				screenWidth ));
		name = (TextView) findViewById(R.id.tv_name);
		name.setOnClickListener(lis);
		iv_share = (ImageView) findViewById(R.id.iv_share);
		iv_share.setOnClickListener(lis);
//		ratingbar = (RatingBar) findViewById(R.id.ratingbar);
		tv_service = (TextView) findViewById(R.id.tv_company);
		tv_environment = (TextView) findViewById(R.id.tv_environment);
		tv_quality = (TextView) findViewById(R.id.tv_quality);
		tv_telphone = (TextView) findViewById(R.id.tv_telphone);
		tv_address = (TextView) findViewById(R.id.tv_address);
		tv_see_map = (TextView) findViewById(R.id.tv_see_map);
		tv_see_map.setOnClickListener(lis);

		recommended_video =  findViewById(R.id.tv_recommended_video);
		recommended_video.setOnClickListener(lis);
		course_center =  findViewById(R.id.tv_course_center);
		course_center.setOnClickListener(lis);
		tv_room_lease =  findViewById(R.id.tv_room_lease);
		tv_room_lease.setOnClickListener(lis);
		tv_student =  findViewById(R.id.tv_student);
		tv_student.setOnClickListener(lis);
		tv_teacher =  findViewById(R.id.tv_teacher);
		tv_teacher.setOnClickListener(lis);
		comments =  findViewById(R.id.tv_comments);
		comments.setOnClickListener(lis);
		announcements =  findViewById(R.id.tv_announcements);
		announcements.setOnClickListener(lis);
		announcements.setVisibility(View.VISIBLE);
		tv_tlq =  findViewById(R.id.tv_tlq);
		tv_tlq.setOnClickListener(lis);
		talk = (TextView) findViewById(R.id.tv_talk);
		talk.setOnClickListener(lis);
		focus = (TextView) findViewById(R.id.tv_focus);
		focus.setSelected(true);
		focus.setOnClickListener(lis);

	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v == recommended_video) {// 推荐视频
				Intent intent = new Intent(context,
						RecommendedVideoActivity.class);
				intent.putExtra("id", people.getUserid());
				startActivity(intent);
			} else if (v == course_center) {// 课程中心
				Intent intent = new Intent(context, CourseCenterActivity.class);
				intent.putExtra("id", people.getUserid());
				startActivity(intent);
			} else if (v == tv_room_lease) {// 琴房租赁
				Intent intent = new Intent(context, RentKotofusaActivity.class);
				intent.putExtra("id", people.getUserid());
				intent.putExtra("classid", ClassId.音乐教室_琴房租赁_ID);
				startActivity(intent);
			} else if (v == comments) {// 介绍评论
				Intent intent = new Intent(context, IntroAndCommActivity.class);
				intent.putExtra("id", people.getUserid());
				intent.putExtra("name", people.getUsername());
				intent.putExtra("content", people.getSaytext());
				intent.putExtra("type", "classroom");
				intent.putExtra("classid", ClassId.音乐教室_介绍评论_ID);
				startActivity(intent);
			} else if (v == tv_teacher) {// 音乐老师
				if (people.getIsstudent()== 1) {
					Intent intent = new Intent(context,
							ClassRoomTeactherActivity.class);
					intent.putExtra("id", people.getUserid());
					// intent.putExtra("classid", ClassId.音乐教室_介绍评论_ID);
					startActivity(intent);
				}else{
					toast("你不是当前琴行内部成员，无法查看\n请登录重试或者关注当前琴行后加入琴行!");
				}
			} else if (v == tv_student) {// 全部学员
				if (people.getIsstudent()== 1) {
				Intent intent = new Intent(context,
						ClassRoomStudentActivity.class);
				intent.putExtra("id", people.getUserid());
				startActivity(intent);
				}else{
					toast("你不是当前琴行内部成员，无法查看\n请登录重试或者关注当前琴行后加入琴行!");
				}
			} else if (v == announcements) {// 活动公告
				Intent intent = new Intent(context, NoticeActivity.class);
				intent.putExtra("userid", people.getUserid());
				startActivity(intent);
			} else if (v == tv_tlq) {// 讨论区
				if (people.getIsstudent()== 1) {
					Intent intent = new Intent(context,TlqActivity.class);
					intent.putExtra("userid", people.getUserid());
					startActivity(intent);
				}else{
					toast("你不是当前琴行内部成员，无法查看\n请登录重试或者关注当前琴行后加入琴行!");
				}
			} else if (v == talk) {// 私信
				MyProgressDialog.show(context);
				HttpProxyUtil.isFriend(context, people.getUserid(), new ResponseListener() {
					
					@Override
					public void setResponseHandle(Message2 message) {
						MyProgressDialog.Cancel();
						if (message.getData().equals("1")) {
							Intent intent = new Intent(context, MyChatActivity.class);
							intent.putExtra("name", people.getUsername());
							intent.putExtra("image", people.getUserpic());
							startActivity(intent);
						} else {
							toast(getResources().getString(R.string.互相关注后才能发送私信));
						}
					}
				}, null);
			} else if (v == focus) {// 关注
				addAttention();
			} else if (v == ask) {// 我要提问
				if (people.getUserid().equals(Data.myinfo.getUserid())) {
					toast("不能向自己提问");
					return ;
				}
				Intent intent = new Intent(context, AskQuestionActivity.class);
				intent.putExtra("id",people.getUserid());
				intent.putExtra("name",people.getUsername());
				startActivity(intent);
			} else if (v == tv_see_map) {// 查看地图
				Intent intent = new Intent(context, ShowMapActivity.class);
				intent.putExtra("city", people.getAddress1());
				
				if(TextUtils.isEmpty(people.getAddres())){
					intent.putExtra("address", people.getAddress()+people.getAddress1()+people.getAddress2());
				}else{
					intent.putExtra("address", people.getAddres());
				}
				startActivity(intent);
			} else if (v == iv_share) {// 分享
				SharePopWindow.build(context).setTitle(people.getUsername() +"的空间——好琴声，音乐人的交流平台！")
						.setContent(people.getUsername() +"的空间")
						.setTOargetUrl(people.getShareurl())
						.setIconPath(people.getUserpic()).show();
			} else if (v == name) {// 去他空间
				Intent intent = new Intent();
				intent.setClass(context, CelebrityActivity.class);
				intent.putExtra("id", people.getUserid() + "");
				intent.putExtra("groupid",people.getGroupid());
				startActivity(intent);
			}
		}
	};
	protected UserInfo people;

	private void getpeopleInfo() {
		MyProgressDialog.show(context);
		HttpProxyUtil.getUserInfo(context, getIntent().getStringExtra("id"),  "photos",	new ResponseListener() {

			@Override
			public void setResponseHandle(Message2 message) {
				if (message.getData() != null
						&& message.getData().startsWith("{")) {
					people = JSON.parseObject(message.getData(),
							UserInfo.class);
				}
				initViewData();
				// num++;
				// MyProgressDialog.Cancel(num, 2);
				MyProgressDialog.Cancel();
			}

		}, null);
	}

	/** 关注 */
	protected void addAttention() {
		if (people.getIsfeed() == 1) {
			return;
		}
		HttpProxyUtil.addattention(context, people.getUserid(), 	new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						((BaseActivity) context).toast(message.getInfo());
						if (message.getInfo().equals("增加关注成功")) {
							focus.setText(getResources().getString(R.string.已关注));//已关注
						} else {
							focus.setText(getResources().getString(R.string.guanzhu));//关注
						}
						MyProgressDialog.Cancel();
					}

				}, null);
	}

	/** 加载数据 */
	protected void initViewData() {
		 // 轮播
		 List<String> uriList = new ArrayList<String>();
		 uriList.add(people.getUserpic());
		 if (people.getPhoto()!=null&&!TextUtils.isEmpty(people.getPhoto())) {
			String pics[]=people.getPhoto().split("\\::::::");
		 for (int i = 0; i < pics.length; i++) {
		 uriList.add(pics[i]);
		 }
		}
		 mybanner.setImageURI(uriList);
		 mybanner.start();

		name.setText(people.getUsername());
//		ratingbar.setRating(5);
		tv_service.setText(getResources().getString(R.string.fans)  +people.getFollownum());
		tv_environment.setText(getResources().getString(R.string.post)+ people.getPostnum());
		tv_quality.setText("评论" + people.getPlnum());
		tv_telphone.setText(getResources().getString(R.string.电话_hint) + people.getTelephone());
		tv_address.setText(getResources().getString(R.string.地址_hint) + people.getAddress() + people.getAddress1()
				+ people.getAddress2()+people.getAddres());
		if (people.getIsfeed() == 1) {
			focus.setText(getResources().getString(R.string.已关注) );
		}
		if (people.getIsstudent()== 1) {
			tv_student.setVisibility(View.VISIBLE);
			tv_tlq.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onRestart() {
		super.onRestart();
		mybanner.start();
	}

	@Override
	public void onStop() {
		mybanner.stop();
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		mybanner.stop();
		super.onDestroy();
	}
}
