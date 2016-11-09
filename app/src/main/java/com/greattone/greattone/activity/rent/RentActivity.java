package com.greattone.greattone.activity.rent;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.Listener.TimePickerDismissCallback;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.MyTimePickerDialog;
import com.greattone.greattone.dialog.NormalPopuWindow;
import com.greattone.greattone.dialog.NormalPopuWindow.OnItemClickBack;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;
/***我要租*/
public class RentActivity extends BaseActivity {
	private TextView m_price;
	private TextView m_pay;
	private View pay_ll;
	private EditText et_name;
	private EditText et_phone;
	private ImageView m_pic;
	private TextView m_title;
	private View m_btimell;
	private TextView m_begintime;
	private View m_endtimell;
	private TextView m_endtime;
private String title;
private String price;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rent);
		title=getIntent().getStringExtra("title");
		price=getIntent().getStringExtra("price");
		initView();
	}

	private void initView() {
		setHead(getResources().getString(R.string.to_rant), true, true);
		
		 this.m_pic = ((ImageView)findViewById(R.id.act_rent_pic));
		    this.m_title = ((TextView)findViewById(R.id.act_rent_title));
		    this.et_name = ((EditText)findViewById(R.id.et_name));
		    this.et_phone = ((EditText)findViewById(R.id.et_phone));
		    this.m_price = ((TextView)findViewById(R.id.act_rent_price));
		    this.m_pay = ((TextView)findViewById(R.id.act_rent_pay));
		    this.m_btimell = findViewById(R.id.act_rent_begintime_ll);
		    this.m_btimell.setOnClickListener(lis);
		    this.m_begintime = ((TextView)findViewById(R.id.act_rent_begintime));
		    this.m_endtimell = findViewById(R.id.act_rent_endtime_ll);
		    this.m_endtimell.setOnClickListener(lis);
		    findViewById(R.id.act_rent_commit).setOnClickListener(lis);
		    this.m_endtime = ((TextView)findViewById(R.id.act_rent_endtime));
		    ImageLoaderUtil.getInstance().setImagebyurl(getIntent().getStringExtra("pic"), this.m_pic);
		    this.m_title.setText(title);
		    this.m_price.setText("￥" +price);
	}
	OnClickListener lis=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
				switch (v.getId()) {
				case R.id.act_registration_applytype_ll://支付方式\
					List<String > mlist=new ArrayList<String>();
					String names[]=getResources().getStringArray(R.array.pay_type);
					for (String string : names) {
						mlist.add(string);
					}
					NormalPopuWindow normalPopuWindow=new NormalPopuWindow(context, mlist, pay_ll);
					normalPopuWindow.setOnItemClickBack(new OnItemClickBack() {
						
						@Override
						public void OnClick(int position, String text) {
							m_pay.setText(text);
						}
					});
					normalPopuWindow.show();
					break;
				case R.id.act_rent_commit://提交
					commit();
					break;
				case R.id.act_rent_begintime_ll://开始时间
					getBeginTime();
					break;
				case R.id.act_rent_endtime_ll://结束时间
					getEndTime();
					break;

				default:
					break;
				}
		}
	};
/**提交*/
	protected void commit() {
			String name=et_name.getText().toString().trim();
			String phone=et_phone.getText().toString().trim();
			String begintime=m_begintime.getText().toString().trim();
			String endtime=m_endtime.getText().toString().trim();
			if (TextUtils.isEmpty(name)) {
				toast(getResources().getString(R.string.请输入姓名));
				return;
			}
			if (TextUtils.isEmpty(phone)) {
				toast(getResources().getString(R.string.请输入手机号));
				return;
			}
			if (TextUtils.isEmpty(endtime)) {
				toast(getResources().getString(R.string.请选择时间));
				return;
			}
			MyProgressDialog.show(context);
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			map.put("api", "post/ecms_bm");
			map.put("enews", "MAddInfo");
				map.put("classid", "80");
				map.put("mid", "25");
				map.put("bao_type","1");//租赁
				map.put("bao_name", name);
				map.put("bao_id", getIntent().getStringExtra("id"));
				map.put("bao_phone", phone);
				map.put("bao_jin", price);
				map.put("title", title);
				map.put("bao_datestart", begintime);
				map.put("bao_datestop", endtime);
//			map.put("dingdan", "1");
			map.put("loginuid", Data.user.getUserid());
			map.put("logintoken", Data.user.getToken());
			addRequest(HttpUtil.httpConnectionByPost(context, map,
					new ResponseListener() {

						@Override
						public void setResponseHandle(Message2 message) {
							toast(getResources().getString(R.string.提交成功));
								setResult(RESULT_OK, getIntent());
								finish();
							MyProgressDialog.Cancel();
						}
					}, null));
	}

protected void getEndTime() {
	if (TextUtils.isEmpty(m_begintime.getText().toString().trim())) {
		toast(getResources().getString(R.string.请选择开始时间));
		return;
	}
	new MyTimePickerDialog(context, null, new TimePickerDismissCallback() {
		
		@Override
		public void finish(String dateTime) {
			m_endtime.setText(dateTime);
		}
	}).show();
}

protected void getBeginTime() {
	new MyTimePickerDialog(context, null, new TimePickerDismissCallback() {
		
		@Override
		public void finish(String dateTime) {
			m_begintime.setText(dateTime);
			
		}
	}).show();
}
}
