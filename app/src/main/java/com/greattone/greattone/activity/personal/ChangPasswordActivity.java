package com.greattone.greattone.activity.personal;

import java.util.HashMap;

import android.os.Bundle;
import android.text.InputType;
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
/**修改密码*/
public class ChangPasswordActivity extends BaseActivity {
	private EditText et_old_password;
	private EditText et_password;
	private EditText et_double_password;
	private TextView tv_sure;
	private TextView tv_phone_district_num;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_revised_password);
		initView();
	}

	private void initView() {
		setHead(getResources().getString(R.string.修改密码), true, true);

		tv_phone_district_num = (TextView) 		findViewById(R.id.tv_phone_district_num);
		tv_phone_district_num.setVisibility(View.GONE);
		et_old_password = (EditText) findViewById(R.id.et_phone_num);
		et_old_password.setHint(getResources().getString(R.string.旧密码));
		et_old_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
  findViewById(R.id.ll_code).setVisibility(View.GONE);
		et_password = (EditText) findViewById(R.id.et_password);
		et_double_password = (EditText) findViewById(R.id.et_double_password);
		tv_sure = (TextView) findViewById(R.id.tv_sure);
		tv_sure.setOnClickListener(lis);
	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_sure:
				 commit();
				break;

			default:
				break;
			}
		}
	};


	/** 提交 */
	private void commit() {
	String oldPassword=et_old_password.getText().toString().trim();
	String newPassword=et_password.getText().toString().trim();
	String newPassword2=et_double_password.getText().toString().trim();
		
	if (TextUtils.isEmpty(oldPassword)) {
		toast(getResources().getString(R.string.请输入旧密码));
		return;
	}
	if (TextUtils.isEmpty(newPassword)) {
		toast(getResources().getString(R.string.请输入新密码));
		return;
	}
	if (TextUtils.isEmpty(newPassword2)) {
		toast(getResources().getString(R.string.请再次输入新密码));
		return;
	}
	if (!newPassword2.equals(newPassword)) {
		toast(getResources().getString(R.string.二次密码不相同));
		return;
	}
	HashMap<String, String> map = new HashMap<String, String>();
	map.put("api", "user/changePassword");
	map.put("loginuid", Data.user.getUserid());
	map.put("logintoken", Data.user.getToken());
	map.put("oldpassword", oldPassword);
	map.put("password",newPassword);
	map.put("repassword",newPassword2);
	addRequest(HttpUtil.httpConnectionByPost(context, map,
			new ResponseListener() {

				@Override
				public void setResponseHandle(Message2 message) {
					toast(message.getInfo());
					((BaseActivity) context).finish();
					MyProgressDialog.Cancel();
				}
			}, null));
}


}
