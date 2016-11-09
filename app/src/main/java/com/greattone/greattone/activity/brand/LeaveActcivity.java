package com.greattone.greattone.activity.brand;

import java.util.HashMap;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;

/**
 * 我要留言
 * @author makaifeng
 */
public class LeaveActcivity extends BaseActivity {
private EditText et_desc;
private EditText et_name;
private EditText et_phone;
private EditText et_email;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_leave);
//		this.id = getIntent().getStringExtra("id");
//		this.price = getIntent().getStringExtra("price");
//		this.bitype = getIntent().getStringExtra("bitype");//货币类型
//String		type = getIntent().getStringExtra("baotype");//报名上传类型
//		 baotype= getBaoType(type);//报名上传类型
		initView();
	}

	/**
	 * 加载视图
	 */
	private void initView() {
		setHead("我要留言", true, true);//我要留言
		
		findViewById(R.id.et_commit).setOnClickListener(lis);
		this.et_name = ((EditText) findViewById(R.id.et_name));
		this.et_phone = ((EditText) findViewById(R.id.et_phone));
		this.et_email = ((EditText) findViewById(R.id.et_email));
		this.et_desc = ((EditText) findViewById(R.id.et_desc));

	}


	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.et_commit://提交
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
		String email = et_email.getText().toString().trim();
		String desc = et_desc.getText().toString().trim();
		if (TextUtils.isEmpty(name)) {
			toast("请填写留言主题");
			return;
		}
		if (TextUtils.isEmpty(phone)) {
			toast("请填写联系方式");
			return;
		}
		if (TextUtils.isEmpty(email)) {
			toast("请填写电子邮箱");
			return;
		}
		if (TextUtils.isEmpty(desc)) {
			toast("请填写留言内容");
			return;
		}

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "brand/leave_message");
		map.put("title", name);
		map.put("userid", getIntent().getStringExtra("userid"));
		map.put("phone", phone);
		map.put("url", email);
		map.put("text", desc);
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {
			
			@Override
			public void setResponseHandle(Message2 message) {
				toast(message.getInfo());
				MyProgressDialog.Cancel();
				finish();
			}
		}, null));
	
	}
}
