package com.greattone.greattone.activity.personal;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
/**
 * 没签约提示
 * @author 马凯锋
 *
 */
public class SignActivity extends BaseActivity {
	private Button btn_sign;
	private TextView tv_content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign);
		initView();
	}

	private void initView() {
		setHead("", true, true);
		tv_content=(TextView)findViewById(R.id.tv_content);
		btn_sign=(Button)findViewById(R.id.btn_sign);
		btn_sign.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(SignActivity.this,ToSignActivity.class));
			}
		});
		
		tv_content.setText(getIntent().getStringExtra("content"));
	}
}
