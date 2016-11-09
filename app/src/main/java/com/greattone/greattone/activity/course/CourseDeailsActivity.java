package com.greattone.greattone.activity.course;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Course;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;

public class CourseDeailsActivity extends BaseActivity {
	private Course course;
	private TextView tv_oldprice;
	private TextView tv_price;
	private ImageView iv_icon;
	private TextView tv_content;
	private TextView tv_address;
	private TextView tv_time;
	private TextView tv_pay_price;
	private TextView tv_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_deails);
		initView();
		getData();
	}

	private void getData() {
			MyProgressDialog.show(context);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("api", "info/detail");
			map.put("id", getIntent().getStringExtra("id"));
			map.put("classid",  getIntent().getStringExtra("classid"));
			map.put("loginuid", Data.user.getUserid());
			map.put("logintoken", Data.user.getToken());
			addRequest(HttpUtil.httpConnectionByPost(context, map,
					new ResponseListener() {

						@Override
						public void setResponseHandle(Message2 message) {
							if (message.getData() != null
									&& message.getData().startsWith("{")) {
							course = JSON.parseObject( JSON.parseObject(message.getData()).getString("content"),
									Course.class);
							initViewData();
							}
							MyProgressDialog.Cancel();
						}

					}, null));
	}

	private void initView() {
		setHead(getResources().getString(R.string.课程中心详情), true, true);//课程中心详情

		tv_oldprice = (TextView) findViewById(R.id.tv_old_price);//
		tv_price = (TextView) findViewById(R.id.tv_price);//
		iv_icon = (ImageView) findViewById(R.id.iv_icon);//
		int width = screenWidth - DisplayUtil.dip2px(context, 20);
		iv_icon.setLayoutParams(new LinearLayout.LayoutParams(width,
				width * 4 / 5));
		tv_content = (TextView) findViewById(R.id.tv_content);//
		tv_address = (TextView) findViewById(R.id.tv_address);//
		tv_address.setVisibility(View.GONE);
		tv_time = (TextView) findViewById(R.id.tv_time);//
		tv_name = (TextView) findViewById(R.id.tv_name);//
		tv_pay_price = (TextView) findViewById(R.id.tv_pay_price);//
		if ("center".equals(getIntent().getStringExtra("type")))
			findViewById(R.id.act_course_apply).setVisibility(View.GONE);

	}

	private void initViewData() {
		ImageLoaderUtil.getInstance().setImagebyurl(course.getTitlepic(), iv_icon);
		tv_name.setText(course.getTitle());
		tv_price.setText("￥" + course.getPrice());
		SpannableString spannableString = new SpannableString("￥"
				+ course.getTprice());
		spannableString.setSpan(new StrikethroughSpan(), 0,
				spannableString.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		tv_oldprice.setText(spannableString);
		tv_time.setText(getResources().getString(R.string.课时) + course.getKe_hour() +getResources().getString(R.string.节));
		tv_address.setText(course.getDizhi());
		tv_pay_price.setText("￥" + course.getPrice());
		tv_content.setText(course.getIntro());
		findViewById(R.id.tv_bm).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 Intent intent = new Intent(context, RegistrationActivity.class);
				 intent.putExtra("pic", course.getTitlepic());
				 intent.putExtra("id", course.getId());
				 intent.putExtra("title", course.getTitle());
				 intent.putExtra("price", course.getPrice());
				 intent.putExtra("ke_hour", course.getKe_hour());
				 startActivity(intent);
			}
		});
	}
}
