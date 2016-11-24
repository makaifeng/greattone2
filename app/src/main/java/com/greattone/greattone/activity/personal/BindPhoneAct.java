package com.greattone.greattone.activity.personal;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.NormalPopuWindow;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**修改绑定手机*/
public class BindPhoneAct extends BaseActivity {
	private EditText m_mobile;
	private EditText m_code;
	private Button m_sendcode;
	private TextView tv_phone_district_num;
	private String code_num="+86";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bind_phone);
		initView();
	}

	private void initView() {
		setHead(getResources().getString(R.string.绑定手机), true, true);
		tv_phone_district_num = (TextView) 				findViewById(R.id.tv_phone_district_num);
		tv_phone_district_num.setText("+86");
		tv_phone_district_num.setOnClickListener(lis);
		this.m_mobile = ((EditText) findViewById(R.id.et_phone_num));
		this.m_code = ((EditText) findViewById(R.id.activity_bindphone_code));
		this.m_sendcode = ((Button) findViewById(R.id.activity_bindphone_sendcode));
		this.m_sendcode.setOnClickListener(lis);
		findViewById(R.id.activity_bindphone_commit).setOnClickListener(lis);
	}
	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.activity_bindphone_sendcode:
				 sendcode();
				break;
			case R.id.activity_bindphone_commit:
				 commit();
				break;
			case R.id.tv_phone_district_num:// 区号
				List<String> mlist=new  ArrayList<String>();
	final String[] code=context.getResources().getStringArray(R.array.AREA_CODE);
	final String[] codes=context.getResources().getStringArray(R.array.area_codes);
	for(String str:code){
		mlist.add(str);
	}
				 final NormalPopuWindow		popu1 = new NormalPopuWindow(context, mlist,
						 tv_phone_district_num);
					popu1.setOnItemClickBack(new NormalPopuWindow.OnItemClickBack() {
					

						public void OnClick(int position, String text) {
							code_num=codes[position];
							tv_phone_district_num.setText(code_num);
							popu1.dismisss();
						}
					});
					 popu1.show();
				break;
			default:
				break;
			}
		}
	};
	private CountDownTimer downTimer;

	/** 发送验证码 */
	protected void sendcode() {
		String str1 = this.m_mobile.getText().toString().trim();
		if (str1.isEmpty()) {
			toast(getResources().getString(R.string.请输入手机号));
			return;
		}
		MyProgressDialog.show(context);
		HashMap< String , String> map=new HashMap<String, String>();
		map.put("api", "user/sendSms");
		map.put("phone", str1);
		map.put("Area", code_num);
		map.put("sendtype", "changephone");
		map.put("loginuid",Data.user.getUserid());
		map.put("logintoken",Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						toast(message.getInfo());
						MyProgressDialog.Cancel();
						CountDownTime();

					}

				}, null));
	}

	/** 提交 */
	protected void commit() {
	final 	String str1 = this.m_mobile.getText().toString();
		if (str1.isEmpty()) {
			toast(getResources().getString(R.string.请输入手机号));
			return;
		}
		String str2 = this.m_code.getText().toString();
		if (str2.isEmpty()) {
			toast(getResources().getString(R.string.请输入验证码));
			return;
		}
		if (str2.length()!=6) {
			toast("请输入正确的验证码");
			return;
		}
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "user/changePhone");
		map.put("phone", str1);
		map.put("smscode", str2);
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						Data.myinfo.setPhone(str1);
						toast(message.getInfo());
						MyProgressDialog.Cancel();
						finish();
					}
				}, null));
	}

	/** 倒计时 */
	@SuppressWarnings("deprecation")
	protected void CountDownTime() {
		downTimer = new CountDownTimer(60 * 1000L, 1000L) {
			public void onFinish() {
				m_sendcode.setText(getResources().getString(R.string.发送验证码));
				m_sendcode.setEnabled(true);
				m_sendcode.setBackgroundColor(getResources().getColor(
						R.color.red_c30000));
			}

			public void onTick(long millisUntilFinished) {
				m_sendcode.setText(getResources().getString(R.string.剩余) + millisUntilFinished / 1000L + getResources().getString(R.string.秒));
				m_sendcode.setEnabled(false);
				m_sendcode.setBackgroundColor(getResources().getColor(
						R.color.gray_7e7c7d));
			}
		}.start();

	}

	@Override
	protected void onDestroy() {
		if (downTimer!=null) {
			downTimer.cancel();
		}
		super.onDestroy();
	}
}
