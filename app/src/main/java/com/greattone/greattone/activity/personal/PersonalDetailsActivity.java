package com.greattone.greattone.activity.personal;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.data.Data;

/** 我的资料 */
public class PersonalDetailsActivity extends BaseActivity {

	int type;
	private int state;
	private RadioButton radioButton1;
	private List<Fragment> fragments;
	Fragment fragment;
	private RadioGroup radiogroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_details);
		this.state = Data.user.getGroupid();
		addFragmentList();
		initView();
		initViewData();
	}

	private void initView() {
		setHead(getResources().getString(R.string.我的资料), true, true);
		this.radioButton1 = ((RadioButton) findViewById(R.id.radioButton1));
		this.radiogroup = ((RadioGroup) findViewById(R.id.radiogroup));
		radiogroup.check(R.id.radioButton1);
		this.radiogroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					public void onCheckedChanged(RadioGroup group, int resId) {
						switch (resId) {
						case R.id.radioButton1:// **资料
							switch (state) {
							case 1:// 个人资料
								fragment=fragments.get(0);
								initFragment(fragment);
								break;
							case 2:// 达人资料
								fragment=fragments.get(1);
								initFragment(fragment);
								break;
							case 3:// 我的资料
								fragment=fragments.get(2);
								initFragment(fragment);
								break;
							case 4:// 教室资料
								fragment=fragments.get(3);
								initFragment(fragment);
								break;
							case 5:// 品牌资料
								fragment=fragments.get(5);
								initFragment(fragment);
								break;
							default:
								break;
							}
							break;
						case R.id.radioButton2:// 账户安全
							initFragment(fragments.get(4));
							fragment=fragments.get(4);
							initFragment(fragment);
							break;

						default:
							break;
						}
					}
				});
	}

	private void initFragment(Fragment paramFragment) {
		FragmentTransaction localFragmentTransaction = getSupportFragmentManager()
				.beginTransaction();
		localFragmentTransaction.replace(R.id.fl_fragment, paramFragment);
		localFragmentTransaction.commit();
	}

	protected void initViewData() {

		switch (this.state) {
		case 1:
			setHead(getResources().getString(R.string.个人资料), true, true);
			this.radioButton1.setText(getResources().getString(R.string.个人资料));
			fragment=fragments.get(0);
			initFragment(fragment);
			break;
		case 2:
			setHead(getResources().getString(R.string.达人资料), true, true);
			this.radioButton1.setText(getResources().getString(R.string.达人资料));
			fragment=fragments.get(1);
			initFragment(fragment);
			break;
		case 3:
			setHead(getResources().getString(R.string.我的资料), true, true);
			this.radioButton1.setText(getResources().getString(R.string.教师资料));
			fragment=fragments.get(2);
			initFragment(fragment);
			break;
		case 4:
			setHead(getResources().getString(R.string.教室资料), true, true);
			this.radioButton1.setText(getResources().getString(R.string.教室资料));
			fragment=fragments.get(3);
			initFragment(fragment);
			break;
		case 5:
			setHead("品牌资料", true, true);
			this.radioButton1.setText("品牌资料");
			fragment=fragments.get(5);
			initFragment(fragment);
			break;

		default:
			break;
		}
	}

	/** 添加fragment集合 */
	private void addFragmentList() {
		this.fragments = new ArrayList<Fragment>();
		this.fragments.add(new PersonalDetailsFragment());// 个人资料
		this.fragments.add(new PersonalDetailsFragment());// 达人资料
		 this.fragments.add(new PersonalTeachertFragment());//老师资料
		 this.fragments.add(new PersonalRoomtFragment());// 教室资料
		 this.fragments.add(new PersonalAccountFragment());// 账户安全
		 this.fragments.add(new PersonalBrandFragment());// 品牌资料
	}
@Override
protected void onActivityResult(int arg0, int arg1, Intent arg2) {
	// TODO Auto-generated method stub
	super.onActivityResult(arg0, arg1, arg2);
	fragment.onActivityResult(arg0, arg1, arg2);
}
}
