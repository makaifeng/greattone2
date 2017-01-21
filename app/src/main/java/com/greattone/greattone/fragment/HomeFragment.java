package com.greattone.greattone.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseFragment;
import com.greattone.greattone.activity.EntryActivity;
import com.greattone.greattone.activity.SearchAct;
import com.greattone.greattone.activity.WebActivity;
import com.greattone.greattone.activity.brand.MusicalInstrumentBrandActivity2;
import com.greattone.greattone.activity.yuepu.YuePuAct;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.entity.ImageData;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.widget.LoopViewPager;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * 首页
 * @author makaifeng
 *
 */
public class HomeFragment extends BaseFragment {
	/**
	 * 轮播的时间
	 */
	private static final int AD_TIME = 5 * 1000;
	/**
	 * fragment 主布局
	 */
	private View rootView;
	private LinearLayout ll_musicclassroom;
	private LinearLayout ll_musichipster;
	private TextView m_seach;
	private LoopViewPager viewPager;
	/**
	 * viewpager加载的图片控件集合
	 */
	private ArrayList<ImageView> imageList;
	private boolean isInitView;
	private int screenWidth;
@Override
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	getAdvList();
}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_home, container, false);// 关联布局文件
		screenWidth =DisplayUtil.getScreenWidth(context);
		initView();
//		getAdvList();
		return this.rootView;
	}

	private void initView() {
		rootView.findViewById(R.id.fragment_home_information)
				.setOnClickListener(lis);
		rootView.findViewById(R.id.fragment_home_music_teacher)
				.setOnClickListener(lis);
		rootView.findViewById(R.id.fragment_home_musicplaza)
				.setOnClickListener(lis);
		this.ll_musicclassroom = ((LinearLayout) rootView
				.findViewById(R.id.ll_musicclassroom));
		this.viewPager = ((LoopViewPager) rootView
				.findViewById(R.id.frag_home_viewpager));
		 LayoutParams layoutParams = viewPager.getLayoutParams();
		 layoutParams.height=(screenWidth-DisplayUtil.dip2px(context, 20))*400/880;
		viewPager.setLayoutParams(layoutParams);
		this.ll_musichipster = ((LinearLayout) rootView
				.findViewById(R.id.ll_musichipster));
		this.m_seach = ((TextView) rootView.findViewById(R.id.frag_home_seach));
		isInitView=true;
		this.ll_musicclassroom.setOnClickListener(lis);
		this.ll_musichipster.setOnClickListener(lis);
		rootView.findViewById(R.id.fragment_home_discuss).setOnClickListener(
				lis);
		rootView.findViewById(R.id.fragment_home_yuepu).setOnClickListener(
				lis);
		rootView.findViewById(R.id.fragment_home_auditions).setOnClickListener(
				lis);
		rootView.findViewById(R.id.ll_pingpai).setOnClickListener(
				lis);
		rootView.findViewById(R.id.frag_home_seach).setOnClickListener(
				lis);
		this.m_seach.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((keyCode == KeyEvent.KEYCODE_ENTER)
						&& (event.getAction() == KeyEvent.ACTION_DOWN)) {
					((InputMethodManager) context
							.getSystemService(Context.INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(((Activity) context)
									.getCurrentFocus().getWindowToken(), 2);
					// Intent intent = new Intent(context, HomeSeachAct.class);
					// intent.putExtra("key",
					// HomeFragment.this.m_seach.getText().toString());
					// HomeFragment.this.startActivity(intent);
				}
				return false;
			}
		});


	}

	private OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent	 intent;
		String titles[]=context.getResources().getStringArray(R.array.title_names);
			switch (v.getId()) {
			case R.id.fragment_home_musicplaza:// 音乐广场
				startTo(titles[0]);
				break;
			case R.id.fragment_home_information://音乐资讯
				startTo(titles[1]);
				break;
//			case R.id.ll_musichipster://音乐名人
			case R.id.ll_musichipster://直播课堂
				startTo(titles[2]);
				break;
			case R.id.fragment_home_music_teacher:// 音乐老师
				startTo(titles[3]);
				break;
			case R.id.ll_musicclassroom://琴行教室
				startTo(titles[4]);
				break;
			case R.id.fragment_home_auditions://网络海选
				startTo(titles[5]);
				break;
			case R.id.fragment_home_discuss://声粉论坛
				startTo(titles[7]);

				break;
			case R.id.ll_pingpai://乐器品牌
				Intent intent2 = new Intent(context, MusicalInstrumentBrandActivity2.class);
				startActivity(intent2);
				break;
			case R.id.fragment_home_yuepu://乐谱中心
				intent = new Intent(context, YuePuAct.class);
					startActivity(intent);
				break;
			case R.id.frag_home_seach://搜索
				intent=new Intent(context, SearchAct.class);
				startActivityForResult(intent, 22);
				break;

			default:
				break;
			}
		}
	};

	/**
	 * 获取广告栏数据
	 * 
	 *
	 */
	private void getAdvList() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "extend/getAdvList");
		map.put("classid", "5");
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {


					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData() != null
								&& !TextUtils.isEmpty(message.getData())) {
							Data.bannerList = JSON.parseArray(
									message.getData(), ImageData.class);
							if (isInitView) {
								initViewPager();
							}
						}
					}

				}, null));
	}

	protected void startTo( String type) {
		Intent intent = new Intent(context, EntryActivity.class);
		intent.putExtra("type", type);
		startActivity(intent);
	}

	/**
	 * 加载ViewPager
	 */
	private void initViewPager() {
		imageList = new ArrayList<ImageView>();
		// pointList = new ArrayList<ImageView>();
		for (int i = 0; i < Data.bannerList.size(); i++) {
			// 初始化图片资源
			ImageView image = new ImageView(context);
			// image.setBackgroundResource(R.drawable.bg);
			LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			image.setLayoutParams(params2);
			image.setScaleType(ScaleType.FIT_XY);
			final int ps=i;
			image.setOnClickListener(new  OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(context, WebActivity.class);
//					intent.putExtra("urlPath","http://www.greattone.net/zjk/player/demoX1.php");
					intent.putExtra("urlPath", Data.bannerList.get( ps).getUrl());
					intent.putExtra("title", "");
					startActivity(intent);
				}
			});
			// image.setImageResource(resIds[i]);
			ImageLoaderUtil.getInstance().setImagebyurl(
					Data.bannerList.get(i).getPic(), image);
			// ImageLoader.getInstance().displayImage(
			// Data.bannerList.get(i).getPic(), image,
			// ((BaseActivity) context).options);
			imageList.add(image);

			// 添加指示点
			// ImageView point = new ImageView(context);
			// LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
			// LinearLayout.LayoutParams.WRAP_CONTENT,
			// LinearLayout.LayoutParams.WRAP_CONTENT);
			//
			// params.rightMargin = (int) (DisplayUtil.dip2px(context, 5));
			// point.setLayoutParams(params);
			//
			// point.setBackgroundResource(R.drawable.point_bg);
			// if (i == lastPosition) {
			// point.setEnabled(true);
			// } else {
			// point.setEnabled(false);
			// }
			// pointList.add(point);
		}

		// for (int i = 0; i < Data.bannerList.size(); i++) {
		// pointGroup.addView(pointList.get(i));
		// }
		viewPager.setAdapter(new MyPagerAdapter());

		viewPager.setCurrentItem(0);
	
		viewPager.setOnPageChangeListener(pageChangeListener);
		handler.sendEmptyMessageDelayed(0, AD_TIME);
	}
	OnPageChangeListener pageChangeListener=new OnPageChangeListener() {

		@Override
		/**
		 * 页面切换后调用 
		 * position  新的页面位置
		 */
		public void onPageSelected(int position) {

			// position = position % imageList.size();
			// if (pointGroup != null && pointGroup.getChildCount() > 0) {
			// // 改变指示点的状态
			// // 把当前点enbale 为true
			// pointList.get(position).setEnabled(true);
			// // pointGroup.getChildAt(position).setEnabled(true);
			// // 把上一个点设为false
			// pointList.get(lastPosition).setEnabled(false);
			// // pointGroup.getChildAt(lastPosition).setEnabled(false);
			// }
			// lastPosition = position;

		}

		@Override
		/**
		 * 页面正在滑动的时候，回调
		 */
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
		}

		@Override
		/**
		 * 当页面状态发生变化的时候，回调
		 */
		public void onPageScrollStateChanged(int state) {
			if (Data.bannerList.size() > 1) {
				handler.removeMessages(0);
				handler.sendEmptyMessageDelayed(0, AD_TIME);
			}
		}
	};
	/**
	 * 判断是否自动滚动
	 */
	Handler handler = new Handler(new Callback() {

		@Override
		public boolean handleMessage(android.os.Message arg0) {
			if (viewPager != null) {
				int p = viewPager.getCurrentItem();
				// 让viewPager 滑动到下一页
				viewPager.setCurrentItem(p + 1);
			}
			return false;
		}

	});
//	CountDownTimer downTimer = new CountDownTimer(AD_TIME-1, AD_TIME) {
//
//		@Override
//		public void onTick(long millisUntilFinished) {
//			if (viewPager != null) {
//				int p = viewPager.getCurrentItem();
//				// 让viewPager 滑动到下一页
//				viewPager.setCurrentItem(p + 1);
//			}
//		}
//
//		@Override
//		public void onFinish() {
//
//		}
//	};

	private class MyPagerAdapter extends PagerAdapter {

		@Override
		/**
		 * 获得页面的总数
		 */
		public int getCount() {
			return imageList.size();
		}

		@Override
		/**
		 * 获得相应位置上的view
		 * container  view的容器，其实就是viewpager自身
		 * position 	相应的位置
		 */
		public Object instantiateItem(ViewGroup container, int position) {
			// 给 container 添加一个view
			try {
				container
						.removeView(imageList.get(position % imageList.size()));
				container.addView(imageList.get(position % imageList.size()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 返回一个和该view相对的object
			return imageList.get(position % imageList.size());
		}

		@Override
		/**
		 * 判断 view和object的对应关系 
		 */
		public boolean isViewFromObject(View view, Object object) {
			if (view == object) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		/**
		 * 销毁对应位置上的object
		 */
		public void destroyItem(ViewGroup container, int position, Object object) {
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if ( resultCode==Activity.RESULT_OK&&requestCode==22) {
			String str=data.getStringExtra("data");
			Intent intent = new Intent(context, EntryActivity.class);
			intent.putExtra("type", "音乐广场");
			intent.putExtra("data", str);
			startActivity(intent);
		}
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
