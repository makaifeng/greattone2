package com.greattone.greattone.activity.personal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.NormalPopuWindow;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;

/** 修改密码 */
@SuppressWarnings("deprecation")
public class RevisedPasswordActivity extends BaseActivity {
	private EditText et_phone;
	private EditText et_code;
	private TextView tv_get_code;
	private EditText et_password;
	private EditText et_double_password;
	private TextView tv_sure;
	private TextView tv_phone_district_num;
	private String code_num="+86";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_revised_password);
		initView();
	}

	private void initView() {
		setHead(getResources().getString(R.string.forget_password), true, true);
		tv_phone_district_num = (TextView) 				findViewById(R.id.tv_phone_district_num);
		tv_phone_district_num.setText("+86");
		tv_phone_district_num.setOnClickListener(lis);
		et_phone = (EditText) findViewById(R.id.et_phone_num);
		et_code = (EditText) findViewById(R.id.et_code);
		tv_get_code = (TextView) findViewById(R.id.tv_get_code);
		tv_get_code.setOnClickListener(lis);
		et_password = (EditText) findViewById(R.id.et_password);
		et_double_password = (EditText) findViewById(R.id.et_double_password);
		tv_sure = (TextView) findViewById(R.id.tv_sure);

		tv_sure.setOnClickListener(lis);
	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_get_code:
				getcode();
				break;
			case R.id.tv_sure:
				revisedPassword();
				break;
			case R.id.tv_phone_district_num:// 区号
				closeKeyBoard();
				List<String> mlist=new  ArrayList<String>();
				 String[] code=context.getResources().getStringArray(R.array.AREA_CODE);
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

		/** 获取验证码 */
		private void getcode() {
			String phone = et_phone.getText().toString().trim();
			if (phone.isEmpty()) {
				toast(getResources().getString(R.string.请输入手机号));
				return;
			}
			MyProgressDialog.show(context);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("api", "user/sendSms");
			map.put("phone", phone);
			map.put("Area", code_num);
			map.put("sendtype", "findpassword");
			addRequest(HttpUtil.httpConnectionByPost(context, map,
					new ResponseListener() {


						@Override
						public void setResponseHandle(Message2 message) {
							toast(message.getInfo());
							MyProgressDialog.Cancel();
							CountDownTime();

						}

					},null));
		}
	};
	private CountDownTimer downTimer;

	/** 修改密码 */
	protected void revisedPassword() {
		final String phone = et_phone.getText().toString().trim();
		String code = et_code.getText().toString().trim();
		final String password = et_password.getText().toString().trim();
		String double_password = et_double_password.getText().toString().trim();
		if (phone.isEmpty()) {
			toast(getResources().getString(R.string.请输入手机号));
			return;
		}
		if (code.isEmpty()) {
			toast(getResources().getString(R.string.请输入验证码));
			return;
		}
		if (password.isEmpty()) {
			toast(getResources().getString(R.string.请输入密码));
			return;
		}
		if (double_password.isEmpty()) {
			toast(getResources().getString(R.string.请再次输入密码));
			return;
		}
		if (!password.equals(double_password)) {
			toast(getResources().getString(R.string.二次密码不相同));
			return;
		}
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "user/findPassword");
		map.put("phone", phone);
		map.put("newpassword", password);
		map.put("smscode", code);
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {


					@Override
					public void setResponseHandle(Message2 message) {
						toast(message.getInfo());
						MyProgressDialog.Cancel();
						Intent data=new Intent();
						data.putExtra("username", phone);
						data.putExtra("password", password);
						setResult(RESULT_OK, data);
						finish();
					}

				},null));
	}

	/** 倒计时 */
	protected void CountDownTime() {
		 downTimer=	new CountDownTimer(60 * 1000L, 1000L) {
			public void onFinish() {
				tv_get_code.setText(getResources().getString(R.string.发送验证码));
				tv_get_code.setEnabled(true);
				tv_get_code.setBackgroundColor(getResources().getColor(
						R.color.red_c30000));
			}

			public void onTick(long millisUntilFinished) {
				tv_get_code.setText(getResources().getString(R.string.剩余) + millisUntilFinished / 1000L + getResources().getString(R.string.秒));
				tv_get_code.setEnabled(false);
				tv_get_code.setBackgroundColor(getResources().getColor(
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
