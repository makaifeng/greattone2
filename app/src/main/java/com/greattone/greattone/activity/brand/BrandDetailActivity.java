package com.greattone.greattone.activity.brand;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import com.greattone.greattone.activity.WebActivity;
import com.greattone.greattone.activity.celebrity.CelebrityActivity;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.UserInfo;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.FileUtil;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.MyBanner;

import java.util.ArrayList;
import java.util.List;
/**
 * 乐器品牌详情
 * @author makaifeng
 *
 */
@SuppressWarnings("deprecation")
public class BrandDetailActivity extends BaseActivity {
	/** 名字 */
	private TextView name;
	/**品牌介绍 */
	private TextView tv_introduction;
	/**产品中心 */
	private TextView tv_products;
	/**  公司动态 */
	private TextView tv_news;
	/** 销售渠道 */
	private TextView tv_channel;
	/** 留言板*/
	private TextView tv_message;
	/** 联系我们 */
	private TextView tv_contact_us;
	/** 关注 */
	private TextView focus;
	/** 国家 */
	private TextView tv_company;
	/** 粉丝数 */
	private TextView tv_fans;
	/** 乐器种类*/
	private TextView tv_type;
	/** 电话 */
	private TextView tv_telphone;
	/** 地址 */
	private TextView tv_address;


	/** 轮播控件 */
	private MyBanner mybanner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_brand_detail);
		initView();
		getpeopleInfo();
	}

	private void initView() {
		setHead("乐器品牌详情", true, true);//乐器品牌详情

		mybanner = (MyBanner) findViewById(R.id.mybanner);
		mybanner.setLayoutParams(new LinearLayout.LayoutParams(screenWidth,
				screenWidth * 3 / 5));
		mybanner.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
		name = (TextView) findViewById(R.id.tv_name);
		name.setOnClickListener(lis);
//		ratingbar = (RatingBar) findViewById(R.id.ratingbar);
		tv_company = (TextView) findViewById(R.id.tv_company);
		tv_fans = (TextView) findViewById(R.id.tv_fans);
		tv_type = (TextView) findViewById(R.id.tv_type);
		tv_telphone = (TextView) findViewById(R.id.tv_telphone);
		tv_address = (TextView) findViewById(R.id.tv_address);

		tv_products = (TextView) findViewById(R.id.tv_products);
		Drawable drawable = getResources().getDrawable(R.drawable.icon_next);
		drawable.setBounds(0, 0, DisplayUtil.dip2px(context, 15),
				DisplayUtil.dip2px(context, 15));
		tv_products.setCompoundDrawables(null, null, drawable, null);
		tv_products.setOnClickListener(lis);
		tv_news = (TextView) findViewById(R.id.tv_news);
		tv_news.setCompoundDrawables(null, null, drawable, null);
		tv_news.setOnClickListener(lis);
		tv_channel = (TextView) findViewById(R.id.tv_channel);
		tv_channel.setCompoundDrawables(null, null, drawable, null);
		tv_channel.setOnClickListener(lis);
		tv_message = (TextView) findViewById(R.id.tv_message);
		tv_message.setCompoundDrawables(null, null, drawable, null);
		tv_message.setOnClickListener(lis);
		tv_introduction = (TextView) findViewById(R.id.tv_introduction);
		tv_introduction.setCompoundDrawables(null, null, drawable, null);
		tv_introduction.setOnClickListener(lis);
		tv_contact_us = (TextView) findViewById(R.id.tv_contact_us);
		tv_contact_us.setCompoundDrawables(null, null, drawable, null);
		tv_contact_us.setOnClickListener(lis);
		tv_contact_us.setVisibility(View.VISIBLE);
		focus = (TextView) findViewById(R.id.tv_focus);
		focus.setSelected(true);
		focus.setOnClickListener(lis);

	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v == name) {// 空间
				Intent intent = new Intent(context, CelebrityActivity.class);
				intent.putExtra("id", people.getUserid());
				intent.putExtra("groupid", people.getGroupid());
				startActivity(intent);
			} else if (v == tv_introduction) {// 品牌介绍
				Intent intent = new Intent(context,
						WebActivity.class);
			    intent.putExtra("title","品牌介绍");
		        intent.putExtra("urlPath", FileUtil.getBrondH5Url("113",people.getUserid()+""));
				startActivity(intent);
			} else if (v == tv_products) {// 产品中心
				Intent intent = new Intent(context,
						ProductArticleActivity.class);
				intent.putExtra("userid", people.getUserid());
				startActivity(intent);
			} else if (v == tv_news) {// 公司动态
				Intent intent = new Intent(context, BrandNewsActivity.class);
				intent.putExtra("userid", people.getUserid());
				startActivity(intent);
			} else if (v == tv_channel) {// "销售渠道"
				Intent intent = new Intent(context, SalesChannelsActivity.class);
				intent.putExtra("userid", people.getUserid());
				startActivity(intent);
			} else if (v == tv_message) {// 留言板
				Intent intent = new Intent(context, LeaveMessageActivity.class);
				intent.putExtra("userid", people.getUserid());
				startActivity(intent);
			} else if (v == tv_contact_us) {//联系我们
				Intent intent = new Intent(context, ConnectWayActivity.class);
				intent.putExtra("userid", people.getUserid());
				startActivity(intent);
			} else if (v == focus) {// 关注
				addAttention();
			}
		}
	};
	protected UserInfo people;

	private void getpeopleInfo() {
		MyProgressDialog.show(context);
		HttpProxyUtil.getUserInfo(context, getIntent().getStringExtra("userid"), null,	new ResponseListener() {

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
		tv_company.setText("创建国家："  +people.getCompany());
		tv_fans.setText("粉丝数: "+ people.getFollownum());
		tv_type.setText("乐器种类:"+ people.getAihao());
		tv_telphone.setText("联系电话：" + people.getTelephone());
		tv_address.setText(getResources().getString(R.string.地址_hint) + people.getAddress() + people.getAddress1()
				+ people.getAddress2()+people.getAddres());
		if (people.getIsfeed() == 1) {
			focus.setText(getResources().getString(R.string.已关注) );
		}
		if (people.getCked()== 1) {
			Drawable right=context.getResources().getDrawable(R.drawable.icon_v);
			right.setBounds(0, 0, DisplayUtil.dip2px(context, 15), DisplayUtil.dip2px(context, 15));
			name.setCompoundDrawablePadding( DisplayUtil.dip2px(context, 5));
			name.setCompoundDrawables(null, null, right, null);
		} else {
			name.setCompoundDrawables(null, null, null, null);
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


