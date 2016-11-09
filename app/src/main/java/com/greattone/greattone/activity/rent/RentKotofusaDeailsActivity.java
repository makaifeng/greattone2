package com.greattone.greattone.activity.rent;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
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

public class RentKotofusaDeailsActivity extends BaseActivity {
	private Course course;
	private TextView tv_oldprice;
	private TextView tv_type;
	private ImageView iv_icon;
	private TextView tv_content;
	private TextView tv_address;
	private TextView tv_time;
	private TextView tv_pay_price;
	private TextView tv_name;
	private TextView tv_bm;
	private TextView tv_type_hint;

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
		setHead("琴房租赁详情", true, true);

		tv_oldprice = (TextView) findViewById(R.id.tv_old_price);//
		tv_type_hint = (TextView) findViewById(R.id.tv_price_hint);//
		tv_type = (TextView) findViewById(R.id.tv_price);//
		iv_icon = (ImageView) findViewById(R.id.iv_icon);//
		int width = screenWidth - DisplayUtil.dip2px(context, 20);
		iv_icon.setLayoutParams(new LinearLayout.LayoutParams(width,
				width * 4 / 5));
		tv_content = (TextView) findViewById(R.id.tv_content);//
		tv_address = (TextView) findViewById(R.id.tv_address);//
		tv_time = (TextView) findViewById(R.id.tv_time);//
		tv_name = (TextView) findViewById(R.id.tv_name);//
		tv_pay_price = (TextView) findViewById(R.id.tv_pay_price);//
		tv_bm=	 (TextView) findViewById(R.id.tv_bm);
		if ("center".equals(getIntent().getStringExtra("type")))
			findViewById(R.id.act_course_apply).setVisibility(View.GONE);

	}

	@SuppressWarnings("deprecation")
	private void initViewData() {
		ImageLoaderUtil.getInstance().setImagebyurl(course.getTitlepic(), iv_icon);
		tv_name.setText(course.getTitle());
		tv_type_hint.setText("教室类型：");
		tv_type.setText(course.getUserinfo().getClassroom_type());
		if (!  course.getTprice().equals("0")&&  course.getTprice()!=null) {
		SpannableString spannableString = new SpannableString("￥"
				+ course.getTprice());
		spannableString.setSpan(new StrikethroughSpan(), 0,
				spannableString.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		tv_oldprice.setText(spannableString);
		tv_oldprice.setVisibility(View.VISIBLE);
		}
		tv_time.setText(getResources().getString(R.string.电话_hint)  +course.getPbrand()+ getResources().getString(R.string.下单前请打电话咨询));
		tv_address.setCompoundDrawables(null, null, null, null);
		tv_address.setText("人数上限："+course.getPmaxnum());
		tv_pay_price.setText("￥" + course.getPrice()+getResources().getString(R.string.每天));
	Spanned sp = Html.fromHtml(course.getIntro());
//	Spanned sp = Html.fromHtml(course.getIntro(),Html.FROM_HTML_MODE_LEGACY);
		tv_content.setText(sp);
		tv_bm.setText(getResources().getString(R.string.to_rant));//我要租
		tv_bm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 Intent intent = new Intent(context, RentActivity.class);
				 intent.putExtra("pic", course.getTitlepic());
				 intent.putExtra("id", course.getId());
				 intent.putExtra("title", course.getTitle());
				 intent.putExtra("price", course.getPrice());
				 startActivity(intent);
			}
		});
	}
}
