package com.greattone.greattone.widget;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.greattone.greattone.R;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.ImageLoaderUtil;
/**轮播*/
public class MyBanner extends RelativeLayout {
	Context context;
	List<String> uriList = new ArrayList<String>();
	/** 图片集 */
	private ArrayList<ImageView> imageList = new ArrayList<ImageView>();
	/** 圆点集 */
	// private ArrayList<ImageView> pointList = new ArrayList<ImageView>();
	/** 当前位置 */
	private int lastPosition=0;
	/**
	 * 计时器
	 */
	private Timer timer = new Timer();
	/**
	 * 轮播的时间
	 */
	private static int AD_TIME = 3 * 1000;
	private LoopViewPager viewpager;
	private LinearLayout pointGroup;
	private OnPageChangeListener onPageChangeListener;
	/**
	 * 图片是否自适应
	 */
	boolean adaptive;
	public MyBanner(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		init();
	}

	public MyBanner(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	public MyBanner(Context context) {
		super(context);
		this.context = context;

		init();
	}

	private void init() {
		getScreenWidth();
		viewpager = new LoopViewPager(context);
		LayoutParams params = new LayoutParams(screenWidth,
				screenWidth * 3 / 5);
		viewpager.setLayoutParams(params);

		pointGroup = new LinearLayout(context);
		LayoutParams params2 = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		params2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params2.addRule(RelativeLayout.CENTER_HORIZONTAL);
		params2.setMargins(0, 0, 0, DisplayUtil.dip2px(context, 20));
		pointGroup.setLayoutParams(params2);
		 addView(viewpager);
		 addView(pointGroup);
		 initViewPager();
	}

	/**
	 * 加载ViewPager
	 */
	private void initViewPager() {

		viewpager.setCurrentItem(0);
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			/**
			 * 页面切换后调用 
			 * position  新的页面位置
			 */
			public void onPageSelected(int position) {

				position = position % imageList.size();
				if (pointGroup != null && pointGroup.getChildCount() > 0) {
					// 改变指示点的状态
					// 把当前点enbale 为true
					// pointList.get(position).setEnabled(true);
					pointGroup.getChildAt(position).setEnabled(true);
					// 把上一个点设为false
					// pointList.get(lastPosition).setEnabled(false);
					pointGroup.getChildAt(lastPosition).setEnabled(false);
				}
				lastPosition = position;
				if (onPageChangeListener!=null) {
					onPageChangeListener.onPageSelected(position);
				}
			}

			@Override
			/**
			 * 页面正在滑动的时候，回调
			 */
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				if (onPageChangeListener!=null) {
					onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
				}
			}

			@Override
			/**
			 * 当页面状态发生变化的时候，回调
			 */
			public void onPageScrollStateChanged(int state) {
				if (uriList.size() > 1) {
					timer.cancel();
					timer = new Timer();
					timer.schedule(new mTimerTask(), AD_TIME, AD_TIME);
				}
				if (onPageChangeListener!=null) {
					onPageChangeListener.onPageScrollStateChanged(state);
				}
			}
		});

	}

	class mTimerTask extends TimerTask {

		@Override
		public void run() {
			handler.sendEmptyMessage(0);
		}
	};

	/**
	 * 判断是否自动滚动
	 */
	Handler handler = new Handler(new Callback() {

		@Override
		public boolean handleMessage(android.os.Message arg0) {
			if (viewpager != null) {
				int p = viewpager.getCurrentItem();
				// 让viewPager 滑动到下一页
				viewpager.setCurrentItem(p + 1);
			}
			return false;
		}

	});
	private int screenWidth;
//	private int screenHeight;
//	private int wRatio=5;
//	private int hRatio=3;

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

	/**
	 * 添加轮播集合
	 */
	private void addList() {
		imageList = new ArrayList<ImageView>();
		// pointList = new ArrayList<ImageView>();
		if (uriList.size() > 0) {
			for (int i = 0; i < uriList.size(); i++) {
				addImageView(i, false);
			}
		} else {
			addImageView(0, true);
		}
	}

	/**
	 * 添加imageview
	 * 
	 * @param i
	 */
	private void addImageView(int position, boolean NoPic) {
		// 初始化图片资源
		ImageView image = new ImageView(context);
		// image.setBackgroundResource(R.drawable.bg);
		LayoutParams params2 = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		image.setLayoutParams(params2);
		if (adaptive) {
			image.setScaleType(ScaleType.FIT_CENTER);
		}else{
			image.setScaleType(ScaleType.FIT_XY);
		}
		// image.setImageResource(resIds[i]);
		if (!NoPic) {
			ImageLoaderUtil.getInstance().setImagebyurl(
					uriList.get(position), image);
			imageList.add(image);

			// 添加指示点
			ImageView point = new ImageView(context);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);

			params.rightMargin = (int) (DisplayUtil.dip2px(context, 5));
			point.setLayoutParams(params);

			point.setBackgroundResource(R.drawable.point_bg);
			if (position == lastPosition) {
				point.setEnabled(true);
			} else {
				point.setEnabled(false);
			}
			pointGroup.addView(point);
		} else {
			image.setImageResource(R.drawable.image_empty);
			imageList.add(image);
		}
	}

	// /**
	// * 设置页面的个数
	// *
	// * @param count
	// */
	// public void setCount(int count) {
	// this.count = count;
	// addList();
	// viewpager.setAdapter(new MyPagerAdapter());
	// addView(viewpager);
	// addView(pointGroup);
	// }

	/**
	 * 加载图片资源
	 * 
	 * @param uri
	 * @param position
	 */
	public void setImageURI(List<String> uriList) {
		this.uriList = uriList;
		addList();
		initViewPager();
		viewpager.setAdapter(new MyPagerAdapter());
		if (pointGroup.getChildCount() > 0) {
			pointGroup.getChildAt(lastPosition).setEnabled(true);
		}
	}
//	/**
//	 * 设置宽高比
//	 * 比如宽高比1:2，wRatio=1，hRatio=2
//	 * @param wRatio 宽的比例 
//	 * @param hRatio 高的比例
//	 */
//	public void seWidthToHeightRatio(int wRatio,int hRatio){
//		this.wRatio=wRatio;
//		this.hRatio=hRatio;
//	}
	// /**
	// * 加载图片资源
	// *
	// * @param resId
	// * @param position
	// */
	// public void setImageResource(int resId, int position) {
	// imageList.get(position).setImageResource(resId);
	// }
	//
	// /**
	// * 加载图片资源
	// *
	// * @param bm
	// * @param position
	// */
	// public void setImageBitmap(Bitmap bm, int position) {
	// imageList.get(position).setImageBitmap(bm);
	// }
	// /**
	// * 加载图片资源
	// * @param drawable
	// * @param position
	// */
	// public void setImageDrawable(Drawable drawable, int position) {
	// imageList.get(position).setImageDrawable(drawable);
	// }
	/**
	 * 轮播开始
	 */
	public void start() {
		if (uriList.size() > 1) {
			timer = new Timer();
			timer.schedule(new mTimerTask(), AD_TIME, AD_TIME);
		}
	}

	/**
	 * 轮播停止
	 */
	public void stop() {
		if (timer != null) {
			timer.cancel();
		}
	}

	/**
	 * 获取屏幕宽度和高度
	 * 
	 * @return
	 */
	private void getScreenWidth() {
		DisplayMetrics metric = new DisplayMetrics();
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		manager.getDefaultDisplay().getMetrics(metric);
		screenWidth= metric.widthPixels;
//		screenHeight= metric.heightPixels;

	}
	public void setCurrentItem(int item){
		viewpager.setCurrentItem(item);
	}
	public void addOnPageChangeListener(OnPageChangeListener onPageChangeListener){
		this.onPageChangeListener=onPageChangeListener;
	}
	/**图片是否自适应*/
	public void  setAdaptive(boolean adaptive){
	this.adaptive=adaptive;
	}
	public boolean onInterceptTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        int mLastXIntercept = 0;
		int mLastYIntercept = 0;
		switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN: {
        	mLastXIntercept=(int) event.getX();
        	mLastYIntercept= (int) event.getY();
            getParent().requestDisallowInterceptTouchEvent(true); //父布局不要拦截此事件
            break;
        }
        case MotionEvent.ACTION_MOVE: {
            int deltaX=Math.abs(x-mLastXIntercept);
            int deltaY=Math.abs(y-mLastYIntercept);
            if(deltaX<deltaY){
            	getParent().requestDisallowInterceptTouchEvent(false); //父布局需要要拦截此事件
            }
            break;
        }
        case MotionEvent.ACTION_UP: {
            break;
        }
        default:
            break;
        }
        mLastXIntercept=x;
        mLastYIntercept=y;
        return super.onInterceptTouchEvent(event);
    } 
}
