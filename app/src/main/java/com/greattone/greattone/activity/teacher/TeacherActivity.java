package com.greattone.greattone.activity.teacher;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.celebrity.CelebrityActivity;
import com.greattone.greattone.activity.chat.MyChatActivity;
import com.greattone.greattone.activity.classroom.ClassRoomStudentActivity;
import com.greattone.greattone.activity.classroom.IntroAndCommActivity;
import com.greattone.greattone.activity.classroom.NoticeActivity;
import com.greattone.greattone.activity.classroom.TlqActivity;
import com.greattone.greattone.activity.course.CourseCenterActivity;
import com.greattone.greattone.activity.qa.AskQuestionActivity;
import com.greattone.greattone.activity.timetable.TimeTablesActivity;
import com.greattone.greattone.activity2.RecommendedVideoActivity;
import com.greattone.greattone.data.ClassId;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.SharePopWindow;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.UserInfo;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.util.MessageUtil;
import com.greattone.greattone.widget.MyRoundImageView;

/** 老师详情 */
@SuppressWarnings("deprecation")
public class TeacherActivity extends BaseActivity {
	/** 我要提问 */
	private TextView ask;
	/** 头像 */
	private ImageView icon;
	/** 名字 */
	private TextView name;
	/** 身份 */
	private TextView identity;
	/** 等级 */
//	private ImageView level;
	private TextView level;
	/** 城市 */
	private TextView city;
	/** 我的关注 */
	private TextView my_focus;
	/** 我的粉丝 */
	private TextView my_fans;
	/** 推荐视频 */
	private TextView recommended_video;
	/** 课程中心 */
	private TextView course_center;
	/** 介绍评论 */
	private TextView comments;
	/** 活动公告 */
	private TextView announcements;
	/** 私信 */
	private TextView talk;
	/** 关注 */
	private TextView focus;
	protected  UserInfo people;
	private TextView tv_student;
	private TextView tv_tlq;
	private TextView tv_table;
	private ImageView iv_share;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teacher);
		try {
			initView();
			getTeacherInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initView() {
		setHead(getResources().getString(R.string.老师详情), true, true);
		ask = (TextView) findViewById(R.id.tv_head_other);
		ask.setVisibility(View.VISIBLE);
		ask.setTextSize(13);
		ask.setText(getResources().getString(R.string.I_need_to_ask_a_question));
		ask.setOnClickListener(lis);

		icon = (MyRoundImageView) findViewById(R.id.iv_icon);
		name = (TextView) findViewById(R.id.tv_name);
		iv_share = (ImageView) findViewById(R.id.iv_share);
		iv_share.setOnClickListener(lis);
		identity = (TextView) findViewById(R.id.tv_identity);
//		level = (ImageView) findViewById(R.id.iv_level);
		level = (TextView) findViewById(R.id.tv_level);
		city = (TextView) findViewById(R.id.tv_city);
		my_focus = (TextView) findViewById(R.id.tv_my_focus);
		my_fans = (TextView) findViewById(R.id.tv_my_fans);
		recommended_video = (TextView) findViewById(R.id.tv_recommended_video);
		Drawable drawable = getResources().getDrawable(R.drawable.icon_next);
		drawable.setBounds(0, 0, DisplayUtil.dip2px(context, 15),
				DisplayUtil.dip2px(context, 15));
		recommended_video.setCompoundDrawables(null, null, drawable, null);
		recommended_video.setOnClickListener(lis);
		course_center = (TextView) findViewById(R.id.tv_course_center);
		course_center.setCompoundDrawables(null, null, drawable, null);
		course_center.setOnClickListener(lis);
		tv_student = (TextView) findViewById(R.id.tv_student);
		tv_student.setCompoundDrawables(null, null, drawable, null);
		tv_student.setOnClickListener(lis);
		tv_table = (TextView) findViewById(R.id.tv_table);
		tv_table.setCompoundDrawables(null, null, drawable, null);
		tv_table.setOnClickListener(lis);
		comments = (TextView) findViewById(R.id.tv_comments);
		comments.setCompoundDrawables(null, null, drawable, null);
		comments.setOnClickListener(lis);
		announcements = (TextView) findViewById(R.id.tv_announcements);
		announcements.setCompoundDrawables(null, null, drawable, null);
		announcements.setOnClickListener(lis);
		tv_tlq = (TextView) findViewById(R.id.tv_tlq);
		tv_tlq.setCompoundDrawables(null, null, drawable, null);
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
			try {
				if (v == recommended_video) {// 推荐视频
                    Intent intent = new Intent(context,
                            RecommendedVideoActivity.class);
                    intent.putExtra("id", people.getUserid());
                    startActivity(intent);
                } else if (v == course_center) {// 课程中心
                    Intent intent = new Intent(context, CourseCenterActivity.class);
                    intent.putExtra("id", people.getUserid());
                    startActivity(intent);
                } else if (v == tv_student) {// 全部学员
                    if (people.getIsstudent()== 1) {
                        Intent intent = new Intent(context,
                                ClassRoomStudentActivity.class);
                        intent.putExtra("id", people.getUserid());
                        startActivity(intent);
                    }else{
                        toast("你不是本老师下的学生，无法查看!");
                    }
                } else if (v == comments) {// 介绍评论
                    Intent intent = new Intent(context, IntroAndCommActivity.class);
                    intent.putExtra("id", people.getUserid());
                    intent.putExtra("content", people.getSaytext());
                    intent.putExtra("type", "teacher");
                    intent.putExtra("classid", ClassId.音乐教室_介绍评论_ID+"");
                    startActivity(intent);
                } else if (v == announcements) {// 活动公告
                    Intent intent = new Intent(context, NoticeActivity.class);
                    intent.putExtra("userid", people.getUserid());
                    startActivity(intent);
                } else if (v == tv_table) {// 课程表
                    if (people.getIsstudent()== 1) {
                    Intent intent = new Intent(context,TimeTablesActivity.class);
                    intent.putExtra("userid", people.getUserid());
                    startActivity(intent);
                    }else{
                        toast("你不是本老师下的学生，无法查看!");
                    }
                } else if (v == tv_tlq) {// 讨论区
                    if (people.getIsstudent()== 1) {
                    Intent intent = new Intent(context,TlqActivity.class);
                    intent.putExtra("userid", people.getUserid());
                    startActivity(intent);
                    }else{
                        toast("你不是本老师下的学生，无法查看!");
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
                    addattention();
                } else if (v == ask) {// 我要提问
                    if (people.getUserid().equals(Data.myinfo.getUserid())) {
                        toast("不能向自己提问");
                        return ;
                    }
                    Intent intent = new Intent(context, AskQuestionActivity.class);
                    intent.putExtra("id",people.getUserid());
                    intent.putExtra("name",people.getUsername());
                    startActivity(intent);

                } else if (v == icon) {// 头像
                    Intent intent = new Intent(context, CelebrityActivity.class);
                        intent.putExtra("id", people.getUserid() + "");
                        intent.putExtra("groupid",people.getGroupid());
                    context.startActivity(intent);
                } else if (v == iv_share) {// 分享
                    SharePopWindow.build(context).setTitle(people.getUsername() +"的空间——好琴声，音乐人的交流平台！")
                            .setContent(people.getUsername() +"的空间")
                            .setTOargetUrl(people.getShareurl())
                            .setIconPath(people.getUserpic()).show();
                }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	private void getTeacherInfo() {
		MyProgressDialog.show(context);
		HttpProxyUtil.getUserInfo(context, getIntent().getStringExtra("id"), null, new ResponseListener() {

			@Override
			public void setResponseHandle(Message2 message) {
				people = JSON.parseObject(message.getData(),
						UserInfo.class);
				initViewData();
				MyProgressDialog.Cancel();
			}

		}, null);
	}

	/** 加载数据 */
	protected void initViewData() {
		// 头像
		ImageLoaderUtil.getInstance().setImagebyurl(people.getUserpic(), icon);
		icon.setOnClickListener(lis);
		// 名字
		name.setText(people.getUsername());
		// 身份
		identity.setText(getResources().getString(R.string.identity)+ MessageUtil.getIdentity(people));
		// 等级
//		ImageLoaderUtil.getInstance().setImagebyurl(people.getLevel().getPic(), level);
		level.setText(getResources().getString(R.string.level_hint)+ people.getLevel().getName());
		// 城市
		city.setText(getResources().getString(R.string.城市)+ people.getAddress()+ people.getAddress1()+ people.getAddress2());
		// 我的关注
		my_focus.setText(getResources().getString(R.string.focus) + " "+people.getFeednum());
		// 我的粉丝
		my_fans.setText(getResources().getString(R.string.fans) + " "+people.getFollownum());
		if ( people.getIsfeed()==1) {
			focus.setText(getResources().getString(R.string.已关注));
		}
	
	}
	/**关注*/
	protected void addattention() {
		MyProgressDialog.show(context);
		HttpProxyUtil.addattention(context, people.getUserid(),new ResponseListener() {

			@Override
			public void setResponseHandle(Message2 message) {
				((BaseActivity) context).toast(message.getInfo());
				if (message.getInfo().equals("增加关注成功")) {
					focus.setText(getResources().getString(R.string.取消关注));//取消关注
				} else {
					focus.setText(getResources().getString(R.string.focus) );//关注
				}
				MyProgressDialog.Cancel();
			}

		}, null);
	}
}
