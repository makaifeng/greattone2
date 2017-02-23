package com.greattone.greattone.activity.personal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.mall.MyMallOrderFragment;

/** 我的账户 */
public class MyAccountActivity extends BaseActivity {
	private RadioGroup radiogroup;
	private String balance;


	private TextView tv_cash;
	

	boolean isPresent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_account);
		balance=getIntent().getStringExtra("money");
		initView();
	}

	private void initView() {
		setHead(getResources().getString(R.string.我的账户), true, true);
		radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
		((RadioButton) findViewById(R.id.radioButton1)).setText(getResources().getString(R.string.收入记录));
		((RadioButton) findViewById(R.id.radioButton2)).setText(getResources().getString(R.string.提现记录));
		((RadioButton) findViewById(R.id.radioButton3)).setText("商城订单");
		radiogroup.setOnCheckedChangeListener(listener);
		radiogroup.check(R.id.radioButton1);


	}



	private OnCheckedChangeListener listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.radioButton1:// 收入记录
				isPresent=false;
			addFragment(new MyAccountFragment());
				
				break;
			case R.id.radioButton2:// 提现记录
				isPresent=true;
				addFragment(new MyAccountFragment());
				break;
			case R.id.radioButton3:// 商城订单
				isPresent=true;
				addFragment(new MyMallOrderFragment());
				break;
			default:
				break;
			}
		}
	};

	protected void addFragment(Fragment fragment) {
		Bundle bundle=new Bundle();
		bundle.putBoolean("isPresent", isPresent);
		bundle.putString("money",balance);
		fragment.setArguments(bundle);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.fl_fragment, fragment).commit();
	}
}
