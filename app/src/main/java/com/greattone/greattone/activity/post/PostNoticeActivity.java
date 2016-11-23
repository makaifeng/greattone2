package com.greattone.greattone.activity.post;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.Notice;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;

import java.util.HashMap;
/**发布公告*/
public class PostNoticeActivity extends BaseActivity {
	private EditText et_name;
	private EditText et_content;
	private String mid="17";
	private String classid="62";
	String type;
	private Notice notice;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_room);
		type=getIntent().getType();
		initView();
	}

	private void initView() {
		setHead("发布动态", true, true);

		this.et_name = ((EditText) findViewById(R.id.et_name));
		this.	et_name.setHint("动态名称");
		findViewById(R.id.ll_price).setVisibility(View.GONE);
		findViewById(R.id.ll_phone).setVisibility(View.GONE);
		findViewById(R.id.ll_num).setVisibility(View.GONE);
		this.et_content = ((EditText) findViewById(R.id.et_content));
		this.et_content.setHint("动态内容");
	findViewById(R.id.ll_pic).setVisibility(View.GONE);
		findViewById(R.id.btn_commit).setOnClickListener(lis);
		if (null!=type&&type.equals("edit")) {//编辑公告
			setHead("编辑动态", true, true);
	(	(Button)	findViewById(R.id.btn_commit)).setText("编辑");
			 notice=(Notice) getIntent().getSerializableExtra("notice");
			et_name.setText(notice.getTitle());
			et_content.setText(notice.getIntro());
		}
	}
	OnClickListener lis=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_commit://发送
				if (null!=type&&type.equals("edit")) {
				edit();
				}else{
					post();
				}
				break;
			default:
				break;
			}
		}
	};
	
	/**	发帖*/
	protected void post() {
	String title= et_name.getText().toString().trim();
	String content= et_content.getText().toString().trim();
	if (TextUtils.isEmpty(title)) {
		toast(getResources().getString(R.string.请输入姓名));
		return;
	}
	if (TextUtils.isEmpty(content)) {
		toast(getResources().getString(R.string.请输入简介));
		return;
	}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "post/ecms");
		map.put("mid", mid);
		map.put("enews", "MAddInfo");
		map.put("classid", classid);
		map.put("title", title);
		map.put("intro", content);
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

	protected void edit() {
		String title= et_name.getText().toString().trim();
		String content= et_content.getText().toString().trim();
		if (TextUtils.isEmpty(title)) {
			toast(getResources().getString(R.string.请输入姓名));
			return;
		}
		if (TextUtils.isEmpty(content)) {
			toast(getResources().getString(R.string.请输入简介));
			return;
		}
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("api", "post/ecms");
			map.put("mid", mid);
			map.put("enews", "MEditInfo");
			map.put("classid", classid);
			map.put("id", notice.getId());
			map.put("title", title);
			map.put("intro", content);
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
