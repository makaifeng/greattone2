package com.greattone.greattone.activity.haixuan_and_activitise;

import android.content.Intent;
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

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.BaseFragment;
import com.greattone.greattone.activity.EntryActivity;
import com.greattone.greattone.data.ClassId;
import com.greattone.greattone.util.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/** 各地海选*/
public class HaiXuanfragment extends BaseFragment {
	/**
	 * fragment 主布局
	 */
	private View rootView;
	/**标题栏	 */
	private RadioGroup rg_title;
	/**标题栏下的下标线 */
	private RelativeLayout rl_line;
	/**	 * title栏选中按钮的宽度	 */
	private int buttonWidth;
	/** title栏选中按钮的id*/
	private int selectId;
	/**	 * 下标线x的左坐标	 */
	private float lineX;
	private List<Fragment> fragments = new ArrayList<Fragment>();
	private List<RadioButton> buttons = new ArrayList<RadioButton>();
	private ViewPager mPager;
private int  ishistory;
//	public HaiXuanfragment( String type) {
//
//		if ("历史回顾".equals(type)) {
//			ishistory=1;
//		}
//	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_competition, container,
				false);// 关联布局文件
		if ("历史回顾".equals(getArguments().getString("type"))) {
			ishistory=1;
		}
		initView();
		return rootView;
	}

	private void initView() {
		if (	ishistory==0) {
			((BaseActivity) context).setOtherText("历史回顾",new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startActivity(new Intent(context, EntryActivity.class).putExtra("type", "历史回顾"));
				}
			} );
		}
		// 标题栏
		rg_title = (RadioGroup) rootView.findViewById(R.id.rg_title);
		rl_line = (RelativeLayout) rootView.findViewById(R.id.rl_line);
		horizontalScrollViewAddView();
		InitViewPager();
		rg_title.setOnCheckedChangeListener(checkedChangeListener);
		rg_title.check(buttons.get(0).getId());
	}
	/**
	 * 加载ViewPager
	 */
	private void InitViewPager() {
		mPager = (ViewPager) rootView.findViewById(R.id.viewpager);

		mPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(),
				fragments));
		mPager.addOnPageChangeListener(new MyOnPageChangeListener());
	}
	@SuppressWarnings("deprecation")
	private void horizontalScrollViewAddView() {
		buttons = new ArrayList<RadioButton>();
		String title_names[]=getResources().getStringArray(R.array.haixuan_type);
		for (int i = 0; i < title_names.length; i++) {
			HaiXuanTypeFragment fragment=new HaiXuanTypeFragment();
			Bundle bundle=new Bundle();
			bundle.putString("name",title_names[i]);
			bundle.putInt("ishistory",ishistory);
			if (title_names[i].equals("全部")) {
				bundle.putString("name",title_names[i]);
				bundle.putInt("classid",ClassId.音乐海选_ID);
			} else if (title_names[i].equals(getResources().getString(R.string.haixuan_type1))) {
				bundle.putInt("classid",32);
			} else if (title_names[i].equals(getResources().getString(R.string.haixuan_type2))) {
				bundle.putInt("classid",33);
			} else if (title_names[i].equals(getResources().getString(R.string.haixuan_type3))) {
				bundle.putInt("classid",34);
			} else if (title_names[i].equals(getResources().getString(R.string.haixuan_type4))) {
				bundle.putInt("classid",110);
			} else if (title_names[i].equals(getResources().getString(R.string.haixuan_type5))) {
				bundle.putInt("classid",112);
			} else if (title_names[i].equals(getResources().getString(R.string.haixuan_type6))) {
				bundle.putInt("classid",120);
			} else {
				bundle.putInt("classid",ClassId.音乐海选_ID);
			}
			fragment.setArguments(bundle);
			fragments.add(fragment);
		}
		for (int i = 0; i < title_names.length; i++) {
			RadioButton button = new RadioButton(getActivity());
			button.setId(i);
			button.setText(title_names[i]);
			button.setTextSize(18);
			if (i ==0) {
				button.setTextColor(getResources().getColor(R.color.red_b90006));
			}else {
				button.setTextColor(getResources().getColor(R.color.gray_7e7c7d));
			}
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			button.setLayoutParams(layoutParams);
			button.setPadding(DisplayUtil.dip2px(getActivity(), 10),
					DisplayUtil.dip2px(getActivity(), 10),
					DisplayUtil.dip2px(getActivity(), 10),
					DisplayUtil.dip2px(getActivity(), 5));
			button.setButtonDrawable(new ColorDrawable(
					getResources().getColor(android.R.color.transparent)));
			rg_title.addView(button);
			buttons.add(button);
		}
	}
	OnCheckedChangeListener checkedChangeListener = new OnCheckedChangeListener() {


		@SuppressWarnings("deprecation")
		@Override
		public void onCheckedChanged(RadioGroup group, int id) {
			buttons.get(selectId).setTextColor(getResources().getColor(R.color.gray_7e7c7d));
			buttons.get(id).setTextColor(getResources().getColor(R.color.red_b90006));
			selectId=id;
			startAnimation(id, 200);
			mPager.setCurrentItem(id);
		}
	};
	/**
	 * 设置下标线的宽度
	 */
	private void setLineWidth(final RadioButton button) {
		ViewTreeObserver vto = button.getViewTreeObserver();
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			public boolean onPreDraw() {
				buttonWidth = button.getMeasuredWidth();
				LayoutParams layoutParams = rl_line.getLayoutParams();
				layoutParams.width = buttonWidth-button.getPaddingLeft()-button.getPaddingRight();
				rl_line.setLayoutParams(layoutParams);
				return true;
			}
		});
	}
	/**
	 * 下标线的动画
	 * 
	 * @param id
	 */
	protected void startAnimation(int id, int time) {
		RadioButton button = (RadioButton) rootView.findViewById(id);
		TranslateAnimation animation = new TranslateAnimation(lineX,
				button.getX()+button.getPaddingLeft(), button.getTop(), button.getTop());
		animation.setDuration(time);
		animation.setFillAfter(true);
		lineX = button.getX();
		rl_line.setAnimation(animation);
		setLineWidth(button);
	}

	class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
		List<Fragment> fragments;

		public MyFragmentPagerAdapter(FragmentManager fm,
				List<Fragment> fragments) {
			super(fm);
			this.fragments = fragments;
		}

		@Override
		public int getCount() {
			return fragments.size();
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
}
