package com.greattone.greattone.activity.classroom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.adapter.TeacherCommentDetailsAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.Score;
import com.greattone.greattone.entity.TeacherComment;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.MyListView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
/**介绍评论*/
public class IntroAndCommActivity extends BaseActivity {

	private 	List<TeacherComment> commentList = new ArrayList<TeacherComment>();
	private RadioGroup radiogroup;
//	private RadioButton radioButton1;
//	private RadioButton radioButton2;
//	private int classid;
//	private WebView web;
	private MyListView lv;
	private RatingBar bar3;
	private RatingBar bar2;
	private RatingBar bar1;
	private View ll_comment;
	private View ll_dese;
	private int page=1;
	private String type;
	private int num,all;
	private TextView tv_rating1;
	private TextView tv_rating2;
	private TextView tv_rating3;
	private RatingBar bar4;
	private TextView tv_rating4;
	private View ll_comment_rating3;
	private TextView tv_content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teacher_details_comment);
//		classid = getIntent().getIntExtra("classid", 0);
		type = getIntent().getStringExtra("type");
		initView();
//		getComments();
//		getCommentStar();
	}


	/**
	 * 加载视图
	 */
	private void initView() {
//		setHead(getResources().getString(R.string.介绍评论), true, true);//介绍评论
		setHead("介绍评论", true, true);//介绍评论
//		//我要评论
//		setOtherText(getResources().getString(R.string.to_comment), lis);
		this.radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
		radiogroup.setVisibility(View.GONE);
//		this.radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
//		this.radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
//		this.web = ((WebView) findViewById(R.id.act_teacher_comment_web));
		this.bar1 = ((RatingBar) findViewById(R.id.act_teacher_comment_rating1));
		this.tv_rating1 = ((TextView) findViewById(R.id.tv_rating1));
		this.bar2 = ((RatingBar) findViewById(R.id.act_teacher_comment_rating2));
		this.tv_rating2 = ((TextView) findViewById(R.id.tv_rating2));
		this.ll_comment_rating3 =  findViewById(R.id.ll_comment_rating3);
		this.bar3 = ((RatingBar) findViewById(R.id.act_teacher_comment_rating3));
		this.tv_rating3 = ((TextView) findViewById(R.id.tv_rating3));
		this.bar4 = ((RatingBar) findViewById(R.id.act_teacher_comment_rating4));
		this.tv_rating4 = ((TextView) findViewById(R.id.tv_rating4));
		this.ll_comment = findViewById(R.id.activity_teacher_details_comment_v);
		this.ll_dese = findViewById(R.id.activity_teacher_details_comment_vs);
		this.tv_content = ((TextView) findViewById(R.id.tv_content));
		this.lv = ((MyListView) findViewById(R.id.activity_teacher_details_comment_lv));

		if (type.equals("classroom")) {
//			radioButton1.setText("教室介绍");
//			radioButton2.setText("教室评论");
			setHead("琴行介绍", true, true);
			ll_comment_rating3.setVisibility(View.VISIBLE);
		} else {
//			radioButton1.setText("老师介绍");
//			radioButton2.setText("老师评论");
			setHead("老师介绍", true, true);
			ll_comment_rating3.setVisibility(View.GONE);
		}
		radiogroup.setOnCheckedChangeListener(listener);
//		radiogroup.check(R.id.radioButton2);
		ll_dese.setVisibility(View.VISIBLE);
		ll_comment.setVisibility(View.GONE);
		tv_content.setText(getIntent().getStringExtra("content"));
	}

	private OnCheckedChangeListener listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.radioButton1:
				ll_comment.setVisibility(View.GONE);
				ll_dese.setVisibility(View.VISIBLE);
				break;
			case R.id.radioButton2:
				ll_comment.setVisibility(View.VISIBLE);
				ll_dese.setVisibility(View.GONE);
				setOtherText(getResources().getString(R.string.to_comment), lis);
				getComments();
				getCommentStar();
				break;

			default:
				break;
			}
		}
	};
	private OnClickListener lis= new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			startActivityForResult(getIntent().setClass(context, GradeActivity.class),0);
		}
	};
	/**
	 * 获取评论星数
	 */
	private void getCommentStar() {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("api", "jiaoshi/comment/pf");
		map.put("userid", getIntent().getStringExtra("id"));
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData() != null
								&& message.getData().startsWith("{")) {
							Score score=JSON.parseObject(message.getData(), Score.class);
							bar1.setRating(score.getNum_score());
							tv_rating1.setText(score.getNum_score()+"/5");
							bar2.setRating(score.getNum_service());
							tv_rating2.setText(score.getNum_service()+"/5");
							bar3.setRating(score.getNum_score());
							tv_rating3.setText(score.getNum_score()+"/5");
							bar4.setRating(score.getNum_ambient());
							tv_rating4.setText(score.getNum_ambient()+"/5");
							}
							num++;
							MyProgressDialog.Cancel(num,all);
					}
				}, null));
	}

	/**
	 * 获取评论
	 */
	private void getComments() {
		MyProgressDialog.show(context);
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("api", "jiaoshi/comment/list");
		map.put("userid", getIntent().getStringExtra("id"));
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
								List<TeacherComment> mList = JSON.parseArray(
										message.getData(), TeacherComment.class);
								if (mList.size() > 0) {
									commentList.addAll(mList);
								} else {
									toast(getResources().getString(R.string.cannot_load_more));
								}
							}
							initContentAdapter();
//							pull_to_refresh.onHeaderRefreshComplete();
//							pull_to_refresh.onFooterRefreshComplete();
							num++;
							MyProgressDialog.Cancel(num,all);
					}
				}, null));
	}

	/**
	 * 加载ContentAdapter数据
	 */
	protected void initContentAdapter() {
		Parcelable listState = lv.onSaveInstanceState();
		TeacherCommentDetailsAdapter adapter = new TeacherCommentDetailsAdapter(context,
				commentList);
		lv.setAdapter(adapter);
		lv.onRestoreInstanceState(listState);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK&&requestCode==0) {
			page=1;
			commentList.clear();
			num=0;
			getComments();
			getCommentStar();
		}
	}
}
