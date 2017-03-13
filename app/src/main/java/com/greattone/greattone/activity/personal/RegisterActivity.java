package com.greattone.greattone.activity.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;

/** 注册 */
public class RegisterActivity extends BaseActivity {
//	private HorizontalScrollView horizontalScrollView;
	private RadioGroup rg_title;
	private android.support.v4.app.FragmentManager fm;

	int buttonWidth;
	private NormalMemberFragment fragment;
	private int groupid=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		groupid=getIntent().getIntExtra("groupid",1);
		initView();
	}

	private void initView() {
		if (groupid==1){
			setHead("注册普通会员", true, true);
		}else 	if (groupid==3){
			setHead("注册音乐老师", true, true);
		}else 	if (groupid==4){
			setHead("注册琴行教室", true, true);
		}else 	if (groupid==5){
			setHead("注册乐器品牌", true, true);
		}else {
			setHead(getResources().getString(R.string.用户注册), true, true);
		}

//		horizontalScrollView = (HorizontalScrollView) findViewById(R.id.scrollView);
		rg_title = (RadioGroup) findViewById(R.id.rg_title);
		RadioGroup.LayoutParams layoutParams=new RadioGroup.LayoutParams(screenWidth/4,-1);
		((RadioButton) findViewById(R.id.radioButton1)).setLayoutParams(layoutParams);
		((RadioButton) findViewById(R.id.radioButton2)).setLayoutParams(layoutParams);
		((RadioButton) findViewById(R.id.radioButton3)).setLayoutParams(layoutParams);
		((RadioButton) findViewById(R.id.radioButton4)).setLayoutParams(layoutParams);
		this.fm = getSupportFragmentManager();
		fragment = new NormalMemberFragment();
		displayFragment(fragment);
		fragment.setGroupId(groupid);
		rg_title.check(R.id.radioButton1);
		rg_title.setOnCheckedChangeListener(listener);
	}

	// @SuppressWarnings("deprecation")
	// private void horizontalScrollViewAddView() {
	// title_names = getResources().getStringArray(R.array.register_names);
	// for (int i = 0; i < title_names.length; i++) {
	// if (title_names[i].equals("普通会员")) {
	// fragments.add(new NormalMemberFragment());
	// } else if (title_names[i].equals("音乐之星")) {
	// fragments.add(new NormalMemberFragment());
	// // fragments.add(new ExpertMemberFrg());
	// } else if (title_names[i].equals("音乐老师")) {
	// fragments.add(new NormalMemberFragment());
	// // fragments.add(new TeacherMemberFrag());
	// } else if (title_names[i].equals("音乐教室")) {
	// fragments.add(new NormalMemberFragment());
	// // fragments.add(new OrganizeMemberFrg());
	// } else {
	// // fragments.add(new NormalMemberFrg());
	// }
	// }
	// buttons = new ArrayList<RadioButton>();
	// for (int i = 0; i < title_names.length; i++) {
	// RadioButton button = new RadioButton(context);
	// button.setId(i);
	// button.setText(title_names[i]);
	// button.setTextSize(15);
	// if (i == 0) {
	// button.setTextColor(getResources().getColor(R.color.red_b90006));
	// } else {
	// button.setTextColor(getResources().getColor(R.color.black));
	// }
	// LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
	// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	// button.setLayoutParams(layoutParams);
	// button.setPadding(DisplayUtil.dip2px(context, 20),
	// DisplayUtil.dip2px(context, 10),
	// DisplayUtil.dip2px(context, 20),
	// DisplayUtil.dip2px(context, 10));
	// button.setButtonDrawable(new ColorDrawable(
	// android.R.color.transparent));
	// button.setBackgroundColor(Color.rgb(240, 230, 229));
	// rg_title.addView(button);
	// buttons.add(button);
	// rg_title.check(buttons.get(0).getId());
	// rg_title.setOnCheckedChangeListener(listener);
	// }
	// }

	OnCheckedChangeListener listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			int position = 0;
			switch (checkedId) {
			case R.id.radioButton1://普通会员
				groupid=1;
				position = 0;
				break;
			case R.id.radioButton2://老师
				groupid=3;
				position = 1;
				break;
			case R.id.radioButton3://教室
				groupid=4;
				position = 2;
				break;
			case R.id.radioButton4://乐器品牌
				groupid=5;
				position = 3;
				break;

			default:
				break;
			}
			fragment.setGroupId(groupid);
			if (buttonWidth == 0) {
				buttonWidth = group.getChildAt(position).getMeasuredWidth();
			}
//			if (group.getChildAt(position).getRight() > screenWidth) {
//				horizontalScrollView.scrollTo(buttonWidth * position
//						+ buttonWidth / 2, 0);
//			} else if (group.getChildAt(position).getLeft() < horizontalScrollView
//					.getScrollX()) {
//				horizontalScrollView.scrollTo(buttonWidth * position
//						- buttonWidth / 2, 0);
//			}
			// for (int i = 0; i < buttons.size(); i++) {
			// buttons.get(i).setTextColor(
			// getResources().getColor(R.color.black));
			// }
			// buttons.get(checkedId).setTextColor(
			// getResources().getColor(R.color.red_b90006));
		}
	};

	private void displayFragment(Fragment fragment) {
		FragmentTransaction fragmentTransaction = this.fm.beginTransaction();
		fragmentTransaction.replace(R.id.fl_fragment, fragment);
		fragmentTransaction.commit();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		fragment.onActivityResult(requestCode, resultCode, data);
	}


}
