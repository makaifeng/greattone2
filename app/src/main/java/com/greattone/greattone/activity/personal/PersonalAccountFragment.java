package com.greattone.greattone.activity.personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseFragment;
import com.greattone.greattone.data.Data;
/**个人中心的修改密码*/
public class PersonalAccountFragment extends BaseFragment {
	/** fragment 主布局 */
	private View rootView;
	private TextView m_mobile;
	private EditText m_nickname;
	private EditText m_username;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.rootView = inflater.inflate(R.layout.fragment_personal_account,
				container, false);
		initView();
		return rootView;
	}

	private void initView() {
		this.m_nickname = ((EditText) this.rootView
				.findViewById(R.id.frag_account_nickname));
//		m_nickname.setVisibility(View.GONE);
			this.m_username = ((EditText) this.rootView
					.findViewById(R.id.frag_account_username));
		this.m_mobile = ((TextView) this.rootView
				.findViewById(R.id.frag_account_mobile));
//		this.m_pwd = ((EditText) this.rootView
//				.findViewById(R.id.frag_account_pwd));
//		this.m_newpwd = ((EditText) this.rootView
//				.findViewById(R.id.frag_account_newpwd));
//		this.m_newpwds = ((EditText) this.rootView
//				.findViewById(R.id.frag_account_newpwds));
		this.rootView.findViewById(R.id.fragment_personal_account_commit)
				.setOnClickListener(lis);
		this.rootView.findViewById(R.id.frag_account_bandphone)
				.setOnClickListener(lis);
		initViewData();
	}

	private void initViewData() {
		m_nickname.setText(Data.myinfo.getUsername());
		m_username.setText(Data.myinfo.getTruename());
		m_mobile.setText(Data.myinfo.getPhone());
	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.fragment_personal_account_commit://修改密码
//				commit();
				startActivity(new Intent(context, ChangPasswordActivity.class));
				break;
			case R.id.frag_account_bandphone://修改手机号
				 startActivityForResult(new Intent(context, BindPhoneAct.class),22);
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==22&&resultCode== Activity.RESULT_OK){
			initViewData();
		}
	}
}
