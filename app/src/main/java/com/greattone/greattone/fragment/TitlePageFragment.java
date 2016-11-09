package com.greattone.greattone.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;

import com.greattone.greattone.Listener.ScrollViewListener;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseFragment;
import com.greattone.greattone.activity.celebrity.MusicCelebritiesFragment;
import com.greattone.greattone.activity.classroom.ClassRoomFragment;
import com.greattone.greattone.activity.discuss.DiscussFragment;
import com.greattone.greattone.activity.haixuan_and_activitise.HaiXuanfragment;
import com.greattone.greattone.activity.plaza.MusicPlazaFragment;
import com.greattone.greattone.activity.plaza.NewsFragment;
import com.greattone.greattone.activity.teacher.TeacherFragment;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.widget.MyHorizontalScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 *
 */
@SuppressWarnings("deprecation")
public class TitlePageFragment extends BaseFragment {
	/**
	 * fragment 主布局
	 */
	private View rootView;
	/**
	 * 标题栏
	 */
	private RadioGroup rg_title;
	/**
	 * 屏幕宽度
	 */
	// private int screenWidth;
	/**
	 * title栏单个按钮的宽度
	 */
	private int buttonWidth;
	/**
	 * 标题栏的名字集合
	 */
	private String[] title_names;
	private List<Fragment> fragments = new ArrayList<Fragment>();
	private List<RadioButton> buttons = new ArrayList<RadioButton>();
	private ViewPager mPager;
	/**
	 * 标题栏的外框
	 */
	private MyHorizontalScrollView scrollView;
	/**
	 * 标题栏下的下标线
	 */
	private RelativeLayout rl_line;
	/**
	 * 下标线x的左坐标
	 */
	private float lineX;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			rootView = inflater.inflate(R.layout.fragment_title, container,
					false);// 关联布局文件
			// screenWidth = ((MainActivity) getActivity()).screenWidth;
			initView();
		return rootView;
	}

	/**
	 * 加载视图
	 */
	private void initView() {
		scrollView = (MyHorizontalScrollView) rootView
				.findViewById(R.id.scrollView);
		scrollView.setScrollViewListener(scrollViewListener);
		// 标题栏
		rg_title = (RadioGroup) rootView.findViewById(R.id.rg_title);
		horizontalScrollViewAddView();
		rg_title.check(buttons.get(0).getId());
		rg_title.setOnCheckedChangeListener(checkedChangeListener);
		// 标题栏下的下标线
		rl_line = (RelativeLayout) rootView.findViewById(R.id.rl_line);
		setLineWidth();
		InitViewPager();
//		final SlidingLayout slidingLayout = ((MainActivity) getActivity()).slidingLayout;
//		Button button = (Button) rootView.findViewById(R.id.btn);// 左侧页面显示按钮
//		button.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if (slidingLayout.isLeftLayoutVisible()) {
//					slidingLayout.scrollToRightLayout();
//				} else {
//					slidingLayout.scrollToLeftLayout();
//				}
//			}
//		});
	}

	/**
	 * 设置下标线的宽度
	 */
	private void setLineWidth() {
		ViewTreeObserver vto = buttons.get(0).getViewTreeObserver();
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			public boolean onPreDraw() {
				buttonWidth = buttons.get(0).getMeasuredWidth();
				LayoutParams layoutParams = rl_line.getLayoutParams();
				layoutParams.width = buttonWidth;
				rl_line.setLayoutParams(layoutParams);
				return true;
			}
		});
	}

	/**
	 * 加载ViewPager
	 */
	private void InitViewPager() {
		mPager = (ViewPager) rootView.findViewById(R.id.viewpager);

		mPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(),
				fragments));
		mPager.setCurrentItem(0);
		mPager.addOnPageChangeListener(new MyOnPageChangeListener());
	}

	/**
	 * 添加横向滚动框内的子视图
	 */
	
	private void horizontalScrollViewAddView() {
		title_names = getResources().getStringArray(R.array.title_names);
		for (int i = 0; i < title_names.length; i++) {
			if (title_names[i].equals("音乐广场")) {
				fragments.add(new MusicPlazaFragment());
			} else if (title_names[i].equals("音乐资讯")) {
				fragments.add(new NewsFragment());
			} else if (title_names[i].equals("音乐老师")) {
				fragments.add(new TeacherFragment());
			} else if (title_names[i].equals("音乐教室")) {
				fragments.add(new ClassRoomFragment());
			} else if (title_names[i].equals("音乐名人")) {
				fragments.add(new MusicCelebritiesFragment());
			} else if (title_names[i].equals("音乐海选")) {
				HaiXuanfragment fragment=new HaiXuanfragment();Bundle bundle=new Bundle();
				bundle.putString("type","音乐海选");
				fragment.setArguments(bundle);
				fragments.add(fragment);
			} else if (title_names[i].equals("音乐乐团")) {
				fragments.add(new MusicFragment());
			} else if (title_names[i].equals("声粉论坛")) {
				fragments.add(new DiscussFragment());
			} else {
				fragments.add(new MusicFragment());
			}
		}
		buttons = new ArrayList<RadioButton>();
		for (int i = 0; i < title_names.length; i++) {
			RadioButton button = new RadioButton(getActivity());
			button.setId(i);
			button.setText(title_names[i]);
			button.setTextSize(15);
			if (i ==0) {
				button.setTextColor(getResources().getColor(R.color.red_b90006));
			}else {
				button.setTextColor(getResources().getColor(R.color.black));
			}
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			button.setLayoutParams(layoutParams);
			button.setPadding(DisplayUtil.dip2px(getActivity(), 20),
					DisplayUtil.dip2px(getActivity(), 10),
					DisplayUtil.dip2px(getActivity(), 20),
					DisplayUtil.dip2px(getActivity(), 10));
			button.setButtonDrawable(new ColorDrawable(
					getResources().getColor(android.R.color.transparent)));
			button.setBackgroundColor(Color.rgb(240, 230, 229));
			button.setOnClickListener(lis);
			rg_title.addView(button);
			buttons.add(button);
		}
	}

	private OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(v.getId());
		}
	};
	ScrollViewListener scrollViewListener = new ScrollViewListener() {

		@Override
		public void onScrollChanged(MyHorizontalScrollView scrollView, int x,
				int y, int oldx, int oldy) {
			int id = rg_title.getCheckedRadioButtonId();
			startAnimation(id, 0);
		}
	};

	class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
		List<Fragment> fragments;

		public MyFragmentPagerAdapter(FragmentManager fm,
				List<Fragment> fragments) {
			super(fm);
			this.fragments = fragments;
		}

		@Override
		public int getCount() {
			return title_names.length;
		}

		// 得到每个item
		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}

		// 初始化每个页卡选项
		@Override
		public Object instantiateItem(ViewGroup arg0, int arg1) {
			return super.instantiateItem(arg0, arg1);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			super.destroyItem(container, position, object);
		}
	}

	class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}

		@Override
		public void onPageSelected(int position) {
			rg_title.check(buttons.get(position).getId());
		}
	}

	private int selectId;
	OnCheckedChangeListener checkedChangeListener = new OnCheckedChangeListener() {


		@Override
		public void onCheckedChanged(RadioGroup group, int id) {
			buttons.get(selectId).setTextColor(getResources().getColor(R.color.black));
			buttons.get(id).setTextColor(getResources().getColor(R.color.red_b90006));
			selectId=id;
			scrollView.scrollTo(buttonWidth * id - buttonWidth / 2, 0);
			startAnimation(id, 200);
		}
	};

	/**
	 * 下标线的动画
	 * 
	 * @param id
	 */
	protected void startAnimation(int id, int time) {
		RadioButton button = (RadioButton) rootView.findViewById(id);
		
		int[] location = new int[2];
		button.getLocationOnScreen(location); // 计算该视图在全局坐标系中的x，y值
		TranslateAnimation animation = new TranslateAnimation(lineX,
				location[0], button.getTop(), button.getTop());
		animation.setDuration(time);
		animation.setFillAfter(true);
		lineX = location[0];
		rl_line.setAnimation(animation);

	}
}
