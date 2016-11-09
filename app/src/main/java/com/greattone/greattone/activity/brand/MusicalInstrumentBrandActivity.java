package com.greattone.greattone.activity.brand;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
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
import com.greattone.greattone.activity.SearchAct;
import com.greattone.greattone.util.DisplayUtil;

import java.util.ArrayList;
import java.util.List;
/**
 * 乐器品牌列表
 * @author makaifeng
 */
public class MusicalInstrumentBrandActivity extends BaseActivity {

//	private MyHorizontalScrollView scrollView;
	private ViewPager mPager;
	private RadioGroup rg_title;
	protected int selectId;
	/**	 * title栏选中按钮的宽度	 */
	private int buttonWidth;
	/**	 * 下标线x的左坐标	 */
	private float lineX;
	List<Fragment> fragments=new ArrayList<Fragment>();
	private List<RadioButton> buttons = new ArrayList<RadioButton>();
	private RelativeLayout rl_line;
	Fragment fragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_musical_instrument_brand);
		// screenWidth = ((BaseActivity) getActivity()).screenWidth;
		initView();
	}
	/**
	 * 加载视图
	 */
	private void initView() {
		setHead("乐器品牌", true, true);
		//搜索
		((BaseActivity)context).setOtherText(getResources().getString(R.string.search), new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Intent intent = new Intent(context,SearchAct.class);
				 startActivityForResult(intent, 1);
			}
		});
//		scrollView = (MyHorizontalScrollView) findViewById(R.id.scrollView);
		// 标题栏
		rg_title = (RadioGroup) findViewById(R.id.rg_title);
		rl_line = (RelativeLayout) findViewById(R.id.rl_line);
		mPager = (ViewPager) findViewById(R.id.viewpager);
		
		horizontalScrollViewAddView();
		InitViewPager();
		rg_title.setOnCheckedChangeListener(checkedChangeListener);
		rg_title.check(buttons.get(0).getId());
	}
	/**
	 * 加载ViewPager
	 */
	private void InitViewPager() {
		mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(),fragments));
		mPager.addOnPageChangeListener(new MyOnPageChangeListener());
	}
	@SuppressWarnings("deprecation")
	private void horizontalScrollViewAddView() {
	 buttons = new ArrayList<RadioButton>();
		String title_names[]=getResources().getStringArray(R.array.brand_type);
		for (int i = 0; i < title_names.length; i++) {
			BrandTypeFragment fragment=new BrandTypeFragment();
			fragment.setName(title_names[i]);
			fragments.add(fragment);
		}
		for (int i = 0; i < title_names.length; i++) {
			RadioButton button = new RadioButton(this);
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
			button.setPadding(DisplayUtil.dip2px(this, 10),
					DisplayUtil.dip2px(this, 10),
					DisplayUtil.dip2px(this, 10),
					DisplayUtil.dip2px(this, 5));
			button.setButtonDrawable(new ColorDrawable(
					getResources().getColor(android.R.color.transparent)));
//			button.setBackgroundColor(Color.rgb(240, 230, 229));
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
//			scrollView.scrollTo(buttonWidth * id - buttonWidth / 2, 0);
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
		RadioButton button = (RadioButton) findViewById(id);
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
	@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO 自动生成的方法存根
			super.onActivityResult(requestCode, resultCode, data);
			fragments.get(selectId).onActivityResult(requestCode, resultCode, data);
		}
}
