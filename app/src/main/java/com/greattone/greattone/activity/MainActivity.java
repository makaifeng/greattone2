package com.greattone.greattone.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.chat.MyMessageFragment;
import com.greattone.greattone.activity.haixuan_and_activitise.ActivitiesFragment;
import com.greattone.greattone.activity.personal.PersonalCenterFragment;
import com.greattone.greattone.activity.post.PostFragment;
import com.greattone.greattone.entity.Chat;
import com.greattone.greattone.fragment.HomeFragment;
import com.greattone.greattone.service.PostVideoService;
import com.greattone.greattone.util.ActivityUtil;
import com.greattone.greattone.util.SQLManager;
import com.greattone.greattone.widget.BadgeView;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends BaseActivity {
	private long exitTime;
	// public FragmentTabHost tabHost;
	// public MyLocationListenner myListener = new MyLocationListenner();
	// ArrayList<Class<?>> fragments = new ArrayList<Class<?>>();
	Request<?> request;// 网络请求对象

	public RadioGroup tab_rg_menu;

	public RelativeLayout slidingLayout;
	int messagecount;
	protected boolean initview;
	private RadioButton button1;
	private RadioButton button2;
	private RadioButton button3;
	private static  RadioButton button4;
	private RadioButton button5;
	private static  BadgeView badgeView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.activity_main);
			preferences.edit().putInt("updateState", PostVideoService.Flag_Init).commit();
		ActivityUtil.getVesion(this);
			initView();
			
		} catch (Exception e) {
			// Log.e("MainActivity", e.getMessage());
		}

	}

	/**
	 * 加载控件
	 */
	private void initView() {
//		setHead(getResources().getString(R.string.home_page), true, false);
		// slidingLayout = (SlidingLayout) findViewById(R.id.slidingLayout);
		slidingLayout = (RelativeLayout) findViewById(R.id.slidingLayout);
		// slidingLayout.setScrollEvent(slidingLayout.getChildAt(1));// 右侧界面滑动事件
		// menuButton = (Button) findViewById(R.id.menuButton);
		// contentListView = (ListView) findViewById(R.id.contentList);
		// contentListAdapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_1,
		// contentItems);
		// contentListView.setAdapter(contentListAdapter);
		// 将监听滑动事件绑定在contentListView上
		// slidingLayout.setScrollEvent(contentListView);
		// menuButton.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// if (slidingLayout.isLeftLayoutVisible()) {
		// slidingLayout.scrollToRightLayout();
		// } else {
		// slidingLayout.scrollToLeftLayout();
		// }
		// }
		// });
		// contentListView.setOnItemClickListener(new OnItemClickListener() {
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view, int
		// position, long id) {
		// String text = contentItems[position];
		// }
		// });

		// view_progress=findViewById(R.id.view_progress);
		// fragments.add(TitlePageFragment.class);
		// fragments.add(MusicFragment.class);
		// fragments.add(MusicFragment.class);
		// fragments.add(MusicFragment.class);
		// fragments.add(MusicFragment.class);

		// Fragment fragment = new HomeFragment();
		// replaceFragment(fragment);
		// tab_rg_menu = (RadioGroup) findViewById(R.id.tab_rg_menu);
		// tab_rg_menu.setOnCheckedChangeListener(listener);
		// tab_rg_menu.check(R.id.tab_rb_1);
		button1 = (RadioButton) findViewById(R.id.tab_rb_1);
		button2 = (RadioButton) findViewById(R.id.tab_rb_2);
		button3 = (RadioButton) findViewById(R.id.tab_rb_3);
		button4 = (RadioButton) findViewById(R.id.tab_rb_4);
		button5 = (RadioButton) findViewById(R.id.tab_rb_5);
		button1.setOnClickListener(lis);
		button2.setOnClickListener(lis);
		button3.setOnClickListener(lis);
		button4.setOnClickListener(lis);
		button5.setOnClickListener(lis);

		badgeView = new BadgeView(this);
		badgeView.setTargetView(button4);

		replaceFragment(new HomeFragment());
		button1.setChecked(true);
		//数据库读取
		SQLManager sql=SQLManager.bulid(context);
		List<Chat> mlList=sql.queryChat(context, "all");
		if (mlList.size()>0) {
		setNum(mlList.get(0).getIssys());
		}
		sql.closeDB();
	}

	/**
	 * 替换fragment
	 * 
	 * @param fragment
	 */
	private void replaceFragment(Fragment fragment) {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.framre_content, fragment).commit();
	}

//	OnCheckedChangeListener listener = new OnCheckedChangeListener() {
//
//		@Override
//		public void onCheckedChanged(RadioGroup arg0, int arg1) {
//			try {
//				switch (arg1) {
//				case R.id.tab_rb_1:// 首页
//					setHead(getResources().getString(R.string.home_page), true,
//							false);
//					replaceFragment(new HomeFragment());
//					// replaceFragment(new TitlePageFragment());
//					break;
//				case R.id.tab_rb_2:// 发帖
//					setHead(getResources().getString(R.string.post), true,
//							false);
//					replaceFragment(new PostFragment());
//					break;
//				case R.id.tab_rb_3:// 个人中心
//					setHead(getResources().getString(R.string.personal_center),
//							true, false);
//					replaceFragment(new PersonalCenterFragment());
//					break;
//				case R.id.tab_rb_4:// 消息
//					badgeView.setVisibility(View.GONE);
//					setHead(getResources().getString(R.string.message), true,
//							false);
//					replaceFragment(new MyMessageFragment());
//					break;
//				case R.id.tab_rb_5:// 活动
//					setHead(getResources().getString(R.string.activity), true,
//							false);
//					replaceFragment(new ActivitiesFragment());
//					break;
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	};
	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			initButton(v);
			try {
				switch (v.getId()) {
				case R.id.tab_rb_1:// 首页
					setHead(getResources().getString(R.string.home_page), false,
							false);
					replaceFragment(new HomeFragment());
//					 replaceFragment(new TitlePageFragment());
					break;
				case R.id.tab_rb_2:// 发帖
					setHead(getResources().getString(R.string.post), true,
							false);
					replaceFragment(new PostFragment());
					break;
				case R.id.tab_rb_3:// 个人中心
					setHead(getResources().getString(R.string.personal_center),
							true, false);
					replaceFragment(new PersonalCenterFragment());
					break;
				case R.id.tab_rb_4:// 消息
					badgeView.setVisibility(View.GONE);
					setHead(getResources().getString(R.string.message), true,
							false);
					replaceFragment(new MyMessageFragment());
				//写入sql	
					SQLManager sql=SQLManager.bulid(context);
					ContentValues 		values=new ContentValues();
					values.put("no_read_num", 0);
					sql.update(context, values, "all");
					sql.closeDB();
					break;
				case R.id.tab_rb_5:// 活动
					setHead(getResources().getString(R.string.activity), true,
							false);
					replaceFragment(new ActivitiesFragment());
					
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private void initButton(View v) {
			button1.setChecked(false);
			button2.setChecked(false);
			button3.setChecked(false);
			button4.setChecked(false);
			button5.setChecked(false);
			((CompoundButton) v).setChecked(true);
		}
	};

	// /**
	// * 显示对话框
	// */
	// public void showMyDialog(final Request<?> request) {
	// new AlertDialog.Builder(this).setMessage("连接失败!")
	// .setPositiveButton("重试", new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// addRequest(request);
	// }
	// })
	// .setNegativeButton("取消", new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// dialog.cancel();
	// }
	// }).show();
	// };

	public static  void setNum(int position) {
		if (badgeView!=null&&button4!=null) {
		if (position == 0) {
			badgeView.setVisibility(View.GONE);
		} else {
			if (button4.isChecked()) {
				badgeView.setVisibility(View.GONE);
			} else {
				badgeView.setVisibility(View.VISIBLE);
				badgeView.setBadgeCount(position);
			}
		}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		// if (slidingLayout.isLeftLayoutVisible()) {
		// slidingLayout.scrollToRightLayout();
		// } else {
		exit();
		// }
	}

	/**
	 * 按2次返回退出
	 */
	public void exit() {
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			// 弹窗显示文本"再按一次退出程序"
			toast(getResources().getString(R.string.click_again_from_exit));
			exitTime = System.currentTimeMillis();
		} else {
			finish();
			System.exit(0);// 关闭整个程序
		}
	}

@Override
public void finish() {
    MobclickAgent.onProfileSignOff();
	super.finish();
}
	@Override
	protected void onResume() {
		super.onResume();
		JPushInterface.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		for (int i = 0; i < getSupportFragmentManager().getFragments().size(); i++) {
			if (getSupportFragmentManager().getFragments().get(i)==null){
				continue;
			}else {
				getSupportFragmentManager().getFragments().get(i)
						.onActivityResult(requestCode, resultCode, data);
			}
		}

	}
}
