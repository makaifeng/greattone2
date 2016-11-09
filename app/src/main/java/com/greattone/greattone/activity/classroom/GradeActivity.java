package com.greattone.greattone.activity.classroom;

import java.util.LinkedHashMap;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;

/**评论 */
public class GradeActivity extends BaseActivity {
//	private ArrayList<String> videoFileList = new ArrayList<String>();
//	private String price;
//	private String id;
//private View ll_game_area;
//private View ll_sing_up;
private RatingBar rating1;
private RatingBar rating2;
private RatingBar rating3;
//private RatingBar rating4;
private EditText m_edit;
private Float sScore = Float.valueOf(4.0F);
//private Float score = Float.valueOf(4.0F);
private Float eScore = Float.valueOf(4.0F);
private Float qScore = Float.valueOf(4.0F);
private String type;
private View ll_comment_rating2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grade);
//		this.id = getIntent().getStringExtra("id");
//		this.price = getIntent().getStringExtra("price");
		type = getIntent().getStringExtra("type");
		initView();
	}

	/**
	 * 加载视图
	 */
	private void initView() {
		setHead(getResources().getString(R.string.to_comment), true, true);//我要评论
		 this.rating1 = ((RatingBar)findViewById(R.id.adapter_teacher_comment_rating1));
		    this.rating2 = ((RatingBar)findViewById(R.id.adapter_teacher_comment_rating2));
		    this.rating3 = ((RatingBar)findViewById(R.id.adapter_teacher_comment_rating3));
//		    this.rating4 = ((RatingBar)findViewById(R.id.adapter_teacher_comment_rating4));
		    this.m_edit = ((EditText)findViewById(R.id.act_grade_edit));
		    findViewById(R.id.act_grade_send).setOnClickListener(lis);
			this.rating1.setOnRatingBarChangeListener(ratingBarChangeListener);
		    this.rating2.setOnRatingBarChangeListener(ratingBarChangeListener);
		    this.rating3.setOnRatingBarChangeListener(ratingBarChangeListener);
//		    this.rating4.setOnRatingBarChangeListener(ratingBarChangeListener);
		    this.ll_comment_rating2 = (findViewById(R.id.ll_comment_rating2));
			if (type.equals("classroom")) {
				ll_comment_rating2.setVisibility(View.VISIBLE);
			} else {
				ll_comment_rating2.setVisibility(View.GONE);
			}
	}
	OnRatingBarChangeListener ratingBarChangeListener=new OnRatingBarChangeListener() {
		
		@Override
		public void onRatingChanged(RatingBar ratingBar, float rating,
				boolean fromUser) {
			switch (ratingBar.getId()) {
			case R.id.adapter_teacher_comment_rating1:
				qScore = Float.valueOf(rating);
				break;
			case R.id.adapter_teacher_comment_rating2:
				eScore = Float.valueOf(rating);
				break;
			case R.id.adapter_teacher_comment_rating3:
				sScore = Float.valueOf(rating);
				break;
//			case R.id.adapter_teacher_comment_rating4:
//				score = Float.valueOf(rating);
//				break;

			default:
				break;
			}
		}
	};


	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.act_grade_send:// 发送
				postComment();
				break;
			default:
				break;
			}
		}
	};

	/**发布评论
	 * @param content */
	protected void postComment() {
		String content=m_edit.getText().toString().trim();
		if (TextUtils.isEmpty(content)) {
			toast(getResources().getString(R.string.请输入评论内容));
			return;
		}
		MyProgressDialog.show(context);
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("api", "jiaoshi/comment/post");
		map.put("classid", getIntent().getStringExtra("classid"));
		map.put("uid",getIntent().getStringExtra("id"));
		map.put("uname",getIntent().getStringExtra("name"));
		map.put("mid", 414+"");
		map.put("mname", Data.myinfo.getUsername());
		map.put("stuDisContent", content);
		map.put("techStar2", qScore+"");
		map.put("soundStar2", eScore+"");
		map.put("serverStar2", sScore+"");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						toast(message.getInfo());
						MyProgressDialog.Cancel();
						setResult(RESULT_OK);
						finish();
					}
				}, null));
	}

}
