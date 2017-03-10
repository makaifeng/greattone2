package com.greattone.greattone.activity.haixuan_and_activitise;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.PayActivity;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;

import java.util.HashMap;

/** 活动报名 */
public class ActivityApplyActivity extends BaseActivity {
	private String price;
	private String id;
	private EditText et_phone;
	private EditText et_name;
	private EditText et_song;
String mid = "22";
String classid = "78";//会员活动 78
private TextView tv_price;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act_apply);
		this.id = getIntent().getStringExtra("id");
		this.price = getIntent().getStringExtra("price");
		initView();
	}

	/**
	 * 加载视图
	 */
	private void initView() {
		setHead(getResources().getString(R.string.sign_up), true, true);//我要报名
		findViewById(R.id.activity_apply_commit).setOnClickListener(lis);
		this.et_phone = ((EditText) findViewById(R.id.activity_apply_phone));
		this.et_name = ((EditText) findViewById(R.id.activity_apply_name));
		this.et_song = ((EditText) findViewById(R.id.activity_apply_song));
		this.tv_price = ((TextView) findViewById(R.id.activity_apply_paymoney));
		initViewData();
	}

	private void initViewData() {
		this.et_name.setText(Data.myinfo.getUsername());
		this.et_phone.setText(Data.myinfo.getPhone());
		this.tv_price.setText(price);

	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.activity_apply_commit:
				submiitData();
				break;

			default:
				break;
			}
		}
	};


	/** 提交 */
	protected void submiitData() {
		String name = et_name.getText().toString().trim();
		String phone = et_phone.getText().toString().trim();
		String title = et_song.getText().toString().trim();

		if (TextUtils.isEmpty(name)) {
			toast(getResources().getString(R.string.请输入姓名));
			return;
		}
		if (TextUtils.isEmpty(phone)) {
			toast(getResources().getString(R.string.请输入手机号));
			return;
		}
		if (TextUtils.isEmpty(title)) {
			toast("请输入参赛曲目");
			return;
		}
		MyProgressDialog.show(context);
		 post2(name, phone,title);
	}
	/**	报名*/
	protected void post2(String name, String phone,String title) {
		HashMap<String, String> map = new HashMap<>();
		map.put("api", "post/ecms_bm");
		map.put("mid", mid);
		map.put("enews", "MAddInfo");
		map.put("bao_type","4");//活动
		map.put("classid", classid);
		map.put("hai_id", id);
		map.put("hai_cost",price);
		map.put("hai_name",name);//选手姓名
		map.put("hai_phone", phone);//联系电话
		map.put("title", title);//参赛曲目
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
//						toast("已生成订单，去支付");
						if (message.getData()!=null&&message.getData().startsWith("{")) {
							com.alibaba.fastjson.JSONObject js = JSON.parseObject(message.getData());
							if(js.getString("price").equals("0")){
								toast("报名成功");
								finish();
							}else {
								Intent intent = new Intent(context, PayActivity.class);
								intent.putExtra("name", js.getString("payname"));
								intent.putExtra("contant", js.getString("payname"));
								intent.putExtra("price", js.getString("price"));
								intent.putExtra("bitype", js.getString("bitype"));
								intent.putExtra("orderId", js.getString("orderid"));
								((Activity) context).startActivityForResult(intent, 3);
								finish();
							}
						}
					}
				}, null));
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 3&&resultCode==RESULT_OK){
			finish();
			setResult(RESULT_OK);
		}

	}
}
