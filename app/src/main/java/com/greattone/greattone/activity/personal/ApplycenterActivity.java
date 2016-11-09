package com.greattone.greattone.activity.personal;

import java.util.LinkedHashMap;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
/**提现*/
public class ApplycenterActivity extends BaseActivity {
//	protected HashMap<String, Object> baseMap = new HashMap<String, Object>();
	private EditText bank;
	private EditText bank_name;
	private EditText code;
	String getPrice;
	String action = "blank";
	private EditText mobile;
	private EditText money;
	private EditText name;
	private EditText openbank;
	private RadioGroup radio;
	private TextView sendcode;
	private TextView tv_price;
//	private String url = HttpConstants.CENTRE_TX_BANK_URL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apply_center);
		if (getIntent().getBundleExtra("data") != null) {
			this.getPrice = getIntent().getBundleExtra("data").getString(
					"price");
		}
		initView();
	}

	private void initView() {
		setHead(getResources().getString(R.string.提现), true, true);//提现
		
		radio = ((RadioGroup) findViewById(R.id.activity_center_Withdraw_radio));
		bank = ((EditText) findViewById(R.id.activity_center_Withdraw_bank));
		bank_name = ((EditText) findViewById(R.id.activity_center_Withdraw_bankname));
		openbank = ((EditText) findViewById(R.id.activity_center_Withdraw_openbank));
		name = ((EditText) findViewById(R.id.activity_center_Withdraw_name));
		mobile = ((EditText) findViewById(R.id.activity_center_Withdraw_mobile));
		money = ((EditText) findViewById(R.id.activity_center_Withdraw_money));
		code = ((EditText) findViewById(R.id.activity_center_Withdraw_code));
		sendcode = ((TextView) findViewById(R.id.activity_center_Withdraw_sendcode));
		tv_price = ((TextView) findViewById(R.id.tv_price_prompt));
		if (!TextUtils.isEmpty(this.getPrice))
			this.tv_price.setHint(getResources().getString(R.string.当前可提现金额)+ this.getPrice);

		radio.check(R.id.activity_center_Withdraw_radiobutton1);
		radio.setOnCheckedChangeListener(listener);
		findViewById(R.id.activity_center_Withdraw_commit).setOnClickListener(
				lis);
		sendcode.setOnClickListener(lis);
		openbank.setVisibility(8);
		bank_name.setVisibility(8);
		bank.setHint(getResources().getString(R.string.支付宝账号));
		ti_type = "1";
	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.activity_center_Withdraw_commit:
				commit();
				break;
			case R.id.activity_center_Withdraw_sendcode:
				sendCode();
				break;

			default:
				break;
			}
		}
	};
	protected String ti_type;

	OnCheckedChangeListener listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.activity_center_Withdraw_radiobutton1:
				openbank.setVisibility(8);
				bank_name.setVisibility(8);
				bank.setHint(getResources().getString(R.string.支付宝账号));
				ti_type = "1";
//				url = HttpConstants.CENTRE_TX_ALIPAY_URL;
				break;
			case R.id.activity_center_Withdraw_radiobutton2:
				ti_type = "2";
				openbank.setVisibility(0);
				bank_name.setVisibility(0);
				bank.setHint(getResources().getString(R.string.银行卡));
//				url = HttpConstants.CENTRE_TX_BANK_URL;
				break;
			default:
				break;
			}

		}
	};

	/** 发送验证码 */
	void sendCode() {
		String str1 = this.mobile.getText().toString();
		if (TextUtils.isEmpty(str1)) {
			toast(getResources().getString(R.string.请输入手机号));
			return;
		}
		MyProgressDialog.show(context);
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("api","user/sendSms");
		map.put("sendtype","withdraw");
		map.put("phone",str1);
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						toast(message.getInfo());
						MyProgressDialog.Cancel();
					}
				}, null));

	}

	/** 倒计时 */
	@SuppressWarnings("deprecation")
	protected void CountDownTime() {
		new CountDownTimer(60 * 1000L, 1000L) {
			public void onFinish() {
				sendcode.setText(getResources().getString(R.string.发送验证码));
				sendcode.setEnabled(true);
				sendcode.setBackgroundColor(getResources().getColor(
						R.color.red_c30000));
			}

			public void onTick(long millisUntilFinished) {
				sendcode.setText(getResources().getString(R.string.剩余) + millisUntilFinished / 1000L + getResources().getString(R.string.秒));
				sendcode.setEnabled(false);
				sendcode.setBackgroundColor(getResources().getColor(
						R.color.gray_7e7c7d));
			}
		}.start();

	}

	private void commit() {
		String str1 = this.bank.getText().toString().trim();
		String str2 = this.bank_name.getText().toString();
		String str3 = this.openbank.getText().toString();
		String str4 = this.name.getText().toString();
		String str5 = this.money.getText().toString();
		String str6 = this.code.getText().toString();
		String str7 = this.mobile.getText().toString();
		if (TextUtils.isEmpty(str1)) {
			String str8;
			if (this.ti_type.equals("1")) {
				str8 = getResources().getString(R.string.支付宝账号不能为空);
			} else {
				str8 = getResources().getString(R.string.银行卡号不能为空);
			}
			toast(str8);
			return;
		}
		if (!this.ti_type.equals("1")) {//银行支付
			if (TextUtils.isEmpty(str2)) {
				toast(getResources().getString(R.string.银行名称不能为空));
				return;
			}
			if (TextUtils.isEmpty(str3)) {
				toast(getResources().getString(R.string.开户行不能为空));
				return;
			}
		}
		if (TextUtils.isEmpty(str4)) {
			toast(getResources().getString(R.string.请输入姓名));
			return;
		}
		if (TextUtils.isEmpty(str5)) {
			toast(getResources().getString(R.string.请输入金额));
			return;
		}
		if (TextUtils.isEmpty(str7)) {
			toast(getResources().getString(R.string.请输入手机号));
			return;
		}
		if (TextUtils.isEmpty(str6)) {
			toast(getResources().getString(R.string.请输入验证码));
			return;
		}
		try {
			if (Float.parseFloat(str5) > Float.parseFloat(this.getPrice)) {
				toast(getResources().getString(R.string.提现金额不能大于当前余额));
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		this.baseMap.clear();
//		this.baseMap.put("uid", Data.user.getUserid());
//		this.baseMap.put("token", Data.user.getToken());
//		this.baseMap.put("account", str1);
//		this.baseMap.put("name", str4);
//		this.baseMap.put("price", str5);
//		this.baseMap.put("mobile", str7);
//		this.baseMap.put("code", str6);
//		if (!this.action.equals("alipay")) {
//			this.baseMap.put("bank", str2);
//			this.baseMap.put("banks", str3);
//		}
		MyProgressDialog.show(context);
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("api","QA/withdraw/submit");
		map.put("ti_type",ti_type);
		map.put("ti_account",str2);
		map.put("ti_name",str4);
		map.put("ti_money",str5);
		map.put("code",str6);
		map.put("ti_phone",str7);
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
