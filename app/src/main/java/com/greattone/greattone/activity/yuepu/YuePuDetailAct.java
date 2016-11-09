package com.greattone.greattone.activity.yuepu;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.greattone.greattone.MusicDiscussActivity;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.dialog.SharePopWindow;
import com.greattone.greattone.util.ImageLoaderUtil;


public class YuePuDetailAct extends BaseActivity {
	private ViewPager viewpager;
	private List<String> uriList = new ArrayList<String>();
	private int mPosition;
	private TextView tv_title,tv_content;
	private TextView tv_num;
int maxSize;
private ImageView iv_pl;
private String title,content,titleurl;
private String classid;
private String id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yue_pu_detail);
		uriList=getIntent().getStringArrayListExtra("uriList");
		title=getIntent().getStringExtra("title");
		content=getIntent().getStringExtra("content");
		titleurl=getIntent().getStringExtra("titleurl");
		classid=getIntent().getStringExtra("classid");
		id=getIntent().getStringExtra("id");
		maxSize=uriList.size();
		initView();
	}
	/**
	 * 加载视图
	 */
	private void initView() {
		setHead(getIntent().getStringExtra("title"), true, true);
		setOtherText("分享", new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharePopWindow.build(context).setTitle(title)
				.setContent(content)
				.setTOargetUrl(titleurl).setIconPath(uriList.get(0))
				.show();
			}
		});
		viewpager = (ViewPager) findViewById(R.id.viewpager);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_content = (TextView) findViewById(R.id.tv_content);
		tv_num = (TextView) findViewById(R.id.tv_num);
		tv_num.setText("1/"+maxSize);
		iv_pl = (ImageView) findViewById(R.id.iv_pl);
		iv_pl.setOnClickListener(lis);
		tv_title.setText(title);
		tv_content.setText(content);
//		addList();
		initViewPager();

	}
	OnClickListener lis=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent=new Intent(context,MusicDiscussActivity.class);
			intent.putExtra("classid",classid);
			intent.putExtra("id",id);
			startActivity(intent);
		}
	};
//	/**
//	 * 添加轮播集合
//	 */
//	private void addList() {
//		imageList = new ArrayList<ImageView>();
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
			tv_num.setText((arg0+1)+"/"+maxSize);
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
		}

		@Override
		/**
		 * 获得相应位置上的view
		 * container  view的容器，其实就是viewpager自身
		 * position 	相应的位置
		 */
		public Object instantiateItem(ViewGroup container, int position) {
 
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
//				PhotoViewAttacher        mAttacher = new PhotoViewAttacher(image);
//				mAttacher.update();
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
//		final ImageView image = new ImageView(context);
//		// image.setBackgroundResource(R.drawable.bg);
//		LayoutParams params2 = new LayoutParams(LayoutParams.MATCH_PARENT,
//				LayoutParams.WRAP_CONTENT);
//		image.setLayoutParams(params2);
//		image.setScaleType(ScaleType.FIT_CENTER);
//		if (!NoPic) {
//			ImageLoaderUtil.getInstance().setImagebyurl(uriList.get(position),
//					image);
//		} else {
//			image.setImageResource(R.drawable.image_empty);
//		}
//		imageList.add(image);
//	}
}
