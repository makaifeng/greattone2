package com.greattone.greattone.activity.plaza;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout.LayoutParams;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.util.ImageLoaderUtil;

public class ShowPictureActivity extends BaseActivity {
	private ViewPager viewpager;
//	private LoopViewPager viewpager;
//	private List<PhotoView> imageList = new ArrayList<PhotoView>();
	private List<String> uriList = new ArrayList<String>();
	private int mPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_picture);
		uriList=getIntent().getStringArrayListExtra("uriList");
		mPosition=getIntent().getIntExtra("position",0);
		initView();
	}

	/**
	 * 加载视图
	 */
	private void initView() {
		setHead(getResources().getString(R.string.图片预览), true, true);
		getScreenWidth();
		viewpager = (ViewPager) findViewById(R.id.viewpager);
//		viewpager = (LoopViewPager) findViewById(R.id.viewpager);
//		addList();
		initViewPager();
	}

//	/**
//	 * 添加轮播集合
//	 */
//	private void addList() {
//		imageList = new ArrayList<PhotoView>();
//		if (uriList.size() > 0) {
//			for (int i = 0; i < uriList.size(); i++) {
//				addImageView(i, false);
//			}
//		} else {
//			addImageView(0, true);
//		}
//	}

	/**
	 * 加载ViewPager
	 */
	private void initViewPager() {
		viewpager.setAdapter(new MyPagerAdapter());
		viewpager.setCurrentItem(mPosition);
		viewpager.addOnPageChangeListener(listener);

	}

	private OnPageChangeListener listener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}
	};

	private class MyPagerAdapter extends PagerAdapter {

		@Override
		/**
		 * 获得页面的总数
		 */
		public int getCount() {
			return uriList==null?0:uriList.size();
//			return imageList.size();
		}

		@Override
		/**
		 * 获得相应位置上的view
		 * container  view的容器，其实就是viewpager自身
		 * position 	相应的位置
		 */
		public Object instantiateItem(ViewGroup container, int position) {

//			// 给 container 添加一个view
//			try {
//				container
//						.removeView(imageList.get(position % imageList.size()));
//				container.addView(imageList.get(position % imageList.size()));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			// 返回一个和该view相对的object
//			return imageList.get(position % imageList.size());
			
			PhotoView image = new PhotoView(context);
			// 给 container 添加一个view
			try {
				// 初始化图片资源
				// image.setBackgroundResource(R.drawable.bg);
				LayoutParams params2 = new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT);
				image.setLayoutParams(params2);
				image.setScaleType(ScaleType.FIT_CENTER);
				if (uriList!=null) {
					ImageLoaderUtil.getInstance().setImagebyurl(uriList.get(position),
							image);
				} else {
					image.setImageResource(R.drawable.image_empty);
				}
				container.addView(image);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 返回一个和该view相对的object
			return image;
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

//	/**
//	 * 添加imageview
//	 * 
//	 * @param i
//	 */
//	private void addImageView(int position, boolean NoPic) {
//		// 初始化图片资源
//		final PhotoView image = new PhotoView(context);
//		// image.setBackgroundResource(R.drawable.bg);
//		LayoutParams params2 = new LayoutParams(LayoutParams.MATCH_PARENT,
//				LayoutParams.WRAP_CONTENT);
//		image.setLayoutParams(params2);
//		image.setScaleType(ScaleType.FIT_CENTER);
//		// image.setImageResource(resIds[i]);
//		if (!NoPic) {
//			ImageLoaderUtil.getInstance().setImagebyurl(uriList.get(position),
//					image);
////			ImageLoaderUtil.getInstance().loadImage(uriList.get(position), new ImageLoadingListener() {
////				
////				@Override
////				public void onLoadingStarted(String arg0, View arg1) {
////					image.setImageResource(R.drawable.image_loading);
////				}
////				
////				@Override
////				public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
////					image.setImageResource(R.drawable.image_error);
////				}
////				
////				@Override
////				public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
////					image.setImageBitmap(arg2);
////				}
////				
////				@Override
////				public void onLoadingCancelled(String arg0, View arg1) {
////					image.setImageResource(R.drawable.image_empty);
////				}
////			});
//		} else {
//			image.setImageResource(R.drawable.image_empty);
//		}
//		imageList.add(image);
//	}

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
		screenWidth = metric.widthPixels;
		// screenHeight= metric.heightPixels;

	}
}
