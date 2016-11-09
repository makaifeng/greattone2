package com.greattone.greattone.activity.qa;

import java.util.LinkedHashMap;

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

public class AnswerPriceAct extends BaseActivity {

	private EditText edit;
	private String id;
	private int type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_answer_price);
		id = getIntent().getStringExtra("id");
		type = getIntent().getIntExtra("type",0);
		setHead(getResources().getString(R.string.回复), true, true);
		setOtherText(getResources().getString(R.string.确定), new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				answer();	
			}
		});
		edit = (EditText)findViewById(R.id.act_answer_price_edit);
		if (type==0) {//回复
			edit.setHint(getResources().getString(R.string.回复内容));
		}else if (type==1){//拒绝回复
			edit.setHint(getResources().getString(R.string.拒绝理由));
		}else if (type==2){//免费回答
			edit.setHint(getResources().getString(R.string.回复内容));
		}
	}

	protected void answer() {
		String str=edit.getText().toString().trim();
		if(TextUtils.isEmpty(str)){
			toast(getResources().getString(R.string.请填写内容));
			return;
		}
		MyProgressDialog.show(context);
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("api", "post/ecms");
		map.put("enews", "MAddInfo");
		if (type==0) {//回复
			map.put("classid", "88");
			map.put("mid", "31");
			map.put("qa_huifu", str);
		}else if (type==1){//拒绝回复
			map.put("classid", "87");
			map.put("mid", "30");
			map.put("qa_jujue", str);
		}else if (type==2){//免费回答
			map.put("classid", "88");
			map.put("mid", "31");
			map.put("qa_huifu", str);
			map.put("zhuangtai", 1+"");
		}
		map.put("qa_titleid", id+"");
		
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						toast(getResources().getString(R.string.发送成功));
							setResult(RESULT_OK, getIntent());
							finish();
						MyProgressDialog.Cancel();
					}
				}, null));
	}


}
