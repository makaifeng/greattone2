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

/**标价*/
public class MarkThePriceAct extends BaseActivity {

	private EditText edit;
	private String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mark_the_price);
		id=getIntent().getStringExtra("id");
		initView();
	}

	private void initView() {
		setHead(getResources().getString(R.string.标价), true, true);
		edit = (EditText)findViewById(R.id.act_mark_price_edit);
		findViewById(R.id.act_mark_price_button).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String price=edit.getText().toString().trim();
				if(TextUtils.isEmpty(price)){
				toast(getResources().getString(R.string.请输入价格));
					return;
				}
				double dPrice=Double.valueOf(price);
				if (dPrice<1) {
					toast("价格不得小于1");
				}
				markPrice(price);
			}
		});		
	}
/**标价
 * @param price */
	private void  markPrice(String price){
		MyProgressDialog.show(context);
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("api", "post/ecms");
		map.put("enews", "MAddInfo");
		map.put("classid", "86");
		map.put("mid", "29");
		map.put("qa_titleid", id+"");
		map.put("dingdan", "1");
		map.put("qa_dingjia", price);
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						toast(getResources().getString(R.string.标价成功));
							setResult(RESULT_OK, getIntent());
							finish();
						MyProgressDialog.Cancel();
					}
				}, null));
	}

}
