package com.greattone.greattone.activity.haixuan_and_activitise;

import java.util.HashMap;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;

public class ActivitySummaryAct extends BaseActivity {
	private String id;
	TextView tv_title;
	EditText et_content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_summary);
		id = getIntent().getStringExtra("id");
		initView();
	}

	private void initView() {
		setHead("发布总结", true, true);
		tv_title = (TextView) findViewById(R.id.tv_title);
		et_content = (EditText) findViewById(R.id.et_content);
		tv_title.setText("当前活动：" + getIntent().getStringExtra("name"));
		 findViewById(R.id.btn_sign_in).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				update();				
			}
		});;
	}

	/** 发布总结 */
	protected void update() {
		String content = et_content.getText().toString().trim();
		if (TextUtils.isEmpty(content)) {
			toast("请填写总结");
		}
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "post/summary");
		map.put("titleid", id);
		map.put("newstext_1", content);
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {
					@Override
					public void setResponseHandle(Message2 message) {
						toast("发布成功");
						MyProgressDialog.Cancel();
						finish();
					}

				}, null));

	}
}
