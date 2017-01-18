package com.greattone.greattone.activity.celebrity;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.entity.UserInfo;
import com.greattone.greattone.util.MessageUtil;


public class CelebrityInfoActivity extends BaseActivity {

	private TextView m_name,m_descr;
	private TextView m_cname,m_level,m_music,m_mobile,m_email,m_city,m_address,m_birthday;
	private ImageView m_isvip;
	private UserInfo userInfo=new UserInfo();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		userInfo= (UserInfo) getIntent().getSerializableExtra("info");
		findView();
	}
	
	private void findView(){
		setHead("基本信息", true, true);
		m_name = (TextView)findViewById(R.id.act_info_name);
		m_isvip = (ImageView)findViewById(R.id.act_info_isvip);
		m_cname = (TextView)findViewById(R.id.act_info_cname);
		m_level = (TextView)findViewById(R.id.act_info_level);
		m_music = (TextView)findViewById(R.id.act_info_music);
		m_mobile = (TextView)findViewById(R.id.act_info_mobile);
		m_email = (TextView)findViewById(R.id.act_info_email);
		m_city = (TextView)findViewById(R.id.act_info_city);
		m_address = (TextView)findViewById(R.id.act_info_address);
		m_birthday = (TextView)findViewById(R.id.act_info_birthday);
		m_descr = (TextView)findViewById(R.id.act_info_descr);
	initViewData();
	
	}
	
	private void initViewData() {
		m_isvip.setVisibility(View.GONE);
		if (userInfo!=null&&userInfo.getVerification()==1) {
			m_isvip.setVisibility(View.VISIBLE);
		}else {
			m_isvip.setVisibility(View.GONE);
		}
		m_name.setText(userInfo.getUsername());
		m_cname.setText(MessageUtil.getIdentity(userInfo));
		if (userInfo.getLevel()!=null) {
			m_level.setText(userInfo.getLevel().getName());
		}
		m_music.setText(userInfo.getAihao());
		m_mobile.setText(userInfo.getPhone());
		m_email.setText(userInfo.getEmail());
		m_city.setText(userInfo.getAddress1());
		m_address.setText(userInfo.getAddres());
		m_birthday.setText(userInfo.getChusheng());
		m_descr.setText(userInfo.getSaytext());
	}


}
