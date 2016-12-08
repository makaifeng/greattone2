package com.greattone.greattone.activity.plaza;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseFragment;
import com.greattone.greattone.activity.EntryActivity;
import com.greattone.greattone.activity.yuepu.YuePuAct;

/**
 * 音乐资讯
 */
public class NewsFragment extends BaseFragment {
	int num;
	final int maxNum=2;
	/**
	 * fragment 主布局
	 */
	private View rootView;
	// /**
	// * 屏幕宽度
	// */
	// private int screenWidth;

//	/**
//	 * viewPager轮播框
//	 */
//	private LoopViewPager viewPager;
//	/**
//	 * 轮播框中的圆点的布局框
//	 */
//	private LinearLayout pointGroup;
//	/**
//	 * 轮播框中的选中圆点的位置
//	 */
//	private int lastPosition;
//	/**
//	 * 计时器
//	 */
//	private Timer timer = new Timer();
//	/**
//	 * 轮播的时间
//	 */
//	private static final int AD_TIME = 3 * 1000;
//	/**
//	 * 二级标题栏
//	 */
//	private MyGridView gv_news;
//	/**
//	 * 正文内容
//	 */
//	private ListView lv_news;
//	/**
//	 * 子标题栏的背景颜色集合
//	 */
//	private int colors[] = { R.color.news_subtitle_color1_f25e84,
//			R.color.news_subtitle_color2_b677dd,
//			R.color.news_subtitle_color3_47b9e9,
//			R.color.news_subtitle_color4_728be7,
//			R.color.news_subtitle_color5_35c977,
//			R.color.news_subtitle_color6_ea5fba,
//			R.color.news_subtitle_color7_ff8901,
//			R.color.news_subtitle_color8_ed5656 };
//	/**
//	 * viewpager加载的图片控件集合
//	 */
//	private ArrayList<ImageView> imageList;
//	/**
//	 * viewpager加载的图圆点控件集合
//	 */
//	private ArrayList<ImageView> pointList;
//	Map<String, List<Blog2>> blogMap=new HashMap<String, List<Blog2>>();
//	private NewsContentListAdapter adapter;
//	private String[] name;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		name=getResources().getStringArray(
//				R.array.news_subtitle);
//		getAdvList();
//		MyProgressDialog.show(context);
//		getNews1();
////		getNews2();
//		getNews3();
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.activity_information, container, false);// 关联布局文件
		initView();
		return rootView;
	}

	/**
	 * 加载视图
	 */
	private void initView() {
//		lv_news = (ListView) rootView.findViewById(R.id.lv_content);// 正文
//		addViewPager();
//		initSubTitle();
//		isInitView = true;
//		 adapter = new NewsContentListAdapter(
//				context, blogMap);
//		lv_news.setAdapter(adapter);
		
		
		rootView.findViewById(R.id.activity_information_interview).setOnClickListener(lis);
		rootView.  findViewById(R.id.activity_information_video).setOnClickListener(lis);
		rootView.   findViewById(R.id.activity_information_event).setOnClickListener(lis);
		rootView.   findViewById(R.id.activity_yp).setOnClickListener(lis);
	}
	OnClickListener lis=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent ;
			switch (v.getId()) {
			case R.id.activity_information_interview://新闻
				context.startActivity(new Intent(context,
						InterviewActivity.class));
				break;
			case R.id.activity_information_video://原创新闻
				context.startActivity(new Intent(context,
						InstrumentInfoActivity.class));
				break;
			case R.id.activity_information_event://活动
				 intent = new Intent(context, EntryActivity.class);
				intent.putExtra("type", context.getResources().getString(R.string.new_activity));
				startActivity(intent);
				break;
			case R.id.activity_yp://乐谱
				 intent = new Intent(context, YuePuAct.class);
				startActivity(intent);
				break;

			default:
				break;
			}
		}
	};

//	OnItemClickListener listener = new OnItemClickListener() {
//
//		@Override
//		public void onItemClick(AdapterView<?> adapter, View v, int position,
//				long arg3) {
//			if (name[position].equals("音乐新闻")) {//
//				context.startActivity(new Intent(context,
//						InterviewActivity.class));
//			}else if (name[position].equals("乐器资讯")) {
//				context.startActivity(new Intent(context,
//						InstrumentInfoActivity.class));
//			}else if (name[position].equals("音乐活动")) {
//				context.startActivity(new Intent(context,
//						ActivitiesActivity.class));
//				
//			}
//		}
//	};

//	protected boolean isInitView;

//	/**获取新闻信息*/
//	private void getNews1() {
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("api", "info/list");
//		map.put("classid", ClassId.音乐新闻_ID+"");
//		map.put("query", "isgood");
//		map.put("pageSize", "4");
//		addRequest(HttpUtil2.httpConnectionByPost(context, map,
//				new ResponseListener() {
//			
//			
//			@Override
//			public void setResponseHandle(Message2 message) {
//				if (message.getData()!=null&&!message.getData().isEmpty()) {
//					List<Blog2>			list = JSON.parseArray(
//							message.getData(), Blog2.class);
//					blogMap.put("音乐新闻", list);
//				}
//				addnum();
//			}
//			
//		},null));
//	}
//	/**获取活动信息*/
//	private void getNews2() {
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("api", "info/list");
//		map.put("classid",ClassId.音乐活动_ID+"");
//		map.put("query", "isgood");
//		map.put("pageSize", "4");
//		addRequest(HttpUtil2.httpConnectionByPost(context, map,
//				new ResponseListener() {
//			
//			
//			@Override
//			public void setResponseHandle(Message2 message) {
//				if (message.getData()!=null&&!message.getData().isEmpty()) {
//					List<Blog2>			list = JSON.parseArray(
//						message.getData(), Blog2.class);
//					blogMap.put("音乐活动", list);
//				}
//				addnum();
//			}
//			
//		},null));
//	}
//	/**获取乐器资讯信息*/
//	private void getNews3() {
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("api", "info/list");
//		map.put("classid", ClassId.乐器资讯_ID+"");
//		map.put("query", "isgood");
//		map.put("pageSize", "4");
//		addRequest(HttpUtil2.httpConnectionByPost(context, map,
//				new ResponseListener() {
//
//
//					@Override
//					public void setResponseHandle(Message2 message) {
//						if (message.getData()!=null&&!message.getData().isEmpty()) {
//							List<Blog2>			list = JSON.parseArray(
//									message.getData(), Blog2.class);
//								blogMap.put("乐器资讯", list);
//						}
//						addnum();
//					}
//
//				},null));
//	}
//	protected void addnum() {
//		num++;
//		if (maxNum==num) {
//			 adapter = new NewsContentListAdapter(
//						context,blogMap);
//				lv_news.setAdapter(adapter);
//				MyProgressDialog.Cancel();
//		}
//		
//	}
//
//
////	/**
////	 * 获取广告栏数据
////	 * @return
////	 */
////	private void getAdvList() {
////		HashMap<String, String> map = new HashMap<String, String>();
////		map.put("api", "extend/getAdvList");
////		addRequest(HttpUtil2.httpConnectionByPost(context, map,
////				new ResponseListener() {
////
////
////					@Override
////					public void setResponseHandle(Message2 message) {
////						if (message.getData()!=null&&!message.getData().isEmpty()) {
////						Data.bannerList = JSON.parseArray(
////								message.getData(), BaseData.class);
////						if (isInitView) {
////							initViewPager();
////						}
////						}
////					}
////
////				},null));
////	}
//	/**
//	 * 加载子标题
//	 */
//	@SuppressLint("InflateParams")
//	private void initSubTitle() {
//		View view = LayoutInflater.from(context).inflate(R.layout.mygridview,
//				null);
//		gv_news = (MyGridView) view.findViewById(R.id.gv_subtitle);// 标题
//		gv_news.setNumColumns(name.length);
//		NewsTitleGridAdapter1 adapter = new NewsTitleGridAdapter1(
//				context, colors, name);
//		gv_news.setAdapter(adapter);
//		gv_news.setOnItemClickListener(listener);
//		lv_news.addHeaderView(view);
//	}
//
//	/**
//	 * 添加ViewPager
//	 */
//	@SuppressLint("InflateParams")
//	private void addViewPager() {
//		View viewPagerGroup = LayoutInflater.from(context).inflate(
//				R.layout.viewpager, null);
//		viewPager = (LoopViewPager) viewPagerGroup.findViewById(R.id.viewpager);
//		pointGroup = (LinearLayout) viewPagerGroup
//				.findViewById(R.id.point_group);
//		lv_news.addHeaderView(viewPagerGroup);
//	}
//
//	/**
//	 * 加载ViewPager
//	 */
//	private void initViewPager() {
//		imageList = new ArrayList<ImageView>();
//		pointList = new ArrayList<ImageView>();
//		for (int i = 0; i < Data.bannerList.size(); i++) {
//			// 初始化图片资源
//			ImageView image = new ImageView(context);
//			// image.setBackgroundResource(R.drawable.bg);
//			LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
//					LinearLayout.LayoutParams.MATCH_PARENT,
//					LinearLayout.LayoutParams.WRAP_CONTENT);
//			image.setLayoutParams(params2);
//			image.setScaleType(ScaleType.FIT_XY);
//			// image.setImageResource(resIds[i]);
//			ImageLoaderUtil.getInstance().setImagebyurl(
//					Data.bannerList.get(i).getPic(), image);
////			ImageLoader.getInstance().displayImage(
////					Data.bannerList.get(i).getPic(), image,
////					((BaseActivity) context).options);
//			imageList.add(image);
//
//			// 添加指示点
//			ImageView point = new ImageView(context);
//			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//					LinearLayout.LayoutParams.WRAP_CONTENT,
//					LinearLayout.LayoutParams.WRAP_CONTENT);
//
//			params.rightMargin = (int) (DisplayUtil.dip2px(context, 5));
//			point.setLayoutParams(params);
//
//			point.setBackgroundResource(R.drawable.point_bg);
//			if (i == lastPosition) {
//				point.setEnabled(true);
//			} else {
//				point.setEnabled(false);
//			}
//			pointList.add(point);
//		}
//
//		for (int i = 0; i < Data.bannerList.size(); i++) {
//			pointGroup.addView(pointList.get(i));
//		}
//		viewPager.setAdapter(new MyPagerAdapter());
//
//		viewPager.setCurrentItem(0);
//		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
//
//			@Override
//			/**
//			 * 页面切换后调用 
//			 * position  新的页面位置
//			 */
//			public void onPageSelected(int position) {
//
//				position = position % imageList.size();
//				if (pointGroup != null && pointGroup.getChildCount() > 0) {
//					// 改变指示点的状态
//					// 把当前点enbale 为true
//					pointList.get(position).setEnabled(true);
//					// pointGroup.getChildAt(position).setEnabled(true);
//					// 把上一个点设为false
//					pointList.get(lastPosition).setEnabled(false);
//					// pointGroup.getChildAt(lastPosition).setEnabled(false);
//				}
//				lastPosition = position;
//
//			}
//
//			@Override
//			/**
//			 * 页面正在滑动的时候，回调
//			 */
//			public void onPageScrolled(int position, float positionOffset,
//					int positionOffsetPixels) {
//			}
//
//			@Override
//			/**
//			 * 当页面状态发生变化的时候，回调
//			 */
//			public void onPageScrollStateChanged(int state) {
//				timer.cancel();
//				timer = new Timer();
//				timer.schedule(new mTimerTask(), AD_TIME, AD_TIME);
//			}
//		});
//		timer = new Timer();
//		timer.schedule(new mTimerTask(), AD_TIME, AD_TIME);
//	}
//
//	class mTimerTask extends TimerTask {
//
//		@Override
//		public void run() {
//			handler.sendEmptyMessage(0);
//		}
//	};
//
//	/**
//	 * 判断是否自动滚动
//	 */
//	Handler handler = new Handler(new Callback() {
//
//		@Override
//		public boolean handleMessage(Message arg0) {
//			if (viewPager != null) {
//				int p = viewPager.getCurrentItem();
//				// 让viewPager 滑动到下一页
//				viewPager.setCurrentItem(p + 1);
//			}
//			return false;
//		}
//	});
//
//
//	private class MyPagerAdapter extends PagerAdapter {
//
//		@Override
//		/**
//		 * 获得页面的总数
//		 */
//		public int getCount() {
//			return imageList.size();
//		}
//
//		@Override
//		/**
//		 * 获得相应位置上的view
//		 * container  view的容器，其实就是viewpager自身
//		 * position 	相应的位置
//		 */
//		public Object instantiateItem(ViewGroup container, int position) {
//			// 给 container 添加一个view
//			try {
//				container
//						.removeView(imageList.get(position % imageList.size()));
//				container.addView(imageList.get(position % imageList.size()));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			// 返回一个和该view相对的object
//			return imageList.get(position % imageList.size());
//		}
//
//		@Override
//		/**
//		 * 判断 view和object的对应关系 
//		 */
//		public boolean isViewFromObject(View view, Object object) {
//			if (view == object) {
//				return true;
//			} else {
//				return false;
//			}
//		}
//
//		@Override
//		/**
//		 * 销毁对应位置上的object
//		 */
//		public void destroyItem(ViewGroup container, int position, Object object) {
//		}
//	}
//
//	@Override
//	public void onStart() {
//		super.onStart();
//		if (pointList != null && pointList.size() > 0) {
//			pointList.get(lastPosition).setEnabled(true);
//		}
//	}
//
//	@Override
//	public void onDestroy() {
//		if (timer != null) {
//			timer.cancel();
//		}
//		super.onDestroy();
//	}
}
