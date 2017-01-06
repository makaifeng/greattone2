package com.greattone.greattone.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.greattone.greattone.Listener.OnBtnItemClickListener;
import com.greattone.greattone.entity.ImageData;
import com.greattone.greattone.util.ImageLoaderUtil;

import java.util.ArrayList;
import java.util.List;

public class StartPagerAdapter extends PagerAdapter {
	private List<ImageData> imageUrlList=new ArrayList<ImageData>();
	OnBtnItemClickListener btnItemClickListener;
	Context context;
	Bitmap bm;
	public StartPagerAdapter(Context context,List<ImageData> imageList,Bitmap bm) {
		this.imageUrlList=imageList;
		this.bm=bm;
		this.context=context;
	}
	@Override
	/**
	 * 获得页面的总数
	 */
	public int getCount() {
		return imageUrlList.size();
	}

	public void setOnBtnItemClickListener(OnBtnItemClickListener btnItemClickListener) {
		this.btnItemClickListener=btnItemClickListener;
	}
	@Override
	/**
	 * 获得相应位置上的view
	 * container  view的容器，其实就是viewpager自身
	 * position 	相应的位置
	 */
	public Object instantiateItem(ViewGroup container, final int position) {
			// 初始化图片资源
			ImageView image = new ImageView(context);
			LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT);
			image.setLayoutParams(params2);
			image.setScaleType(ScaleType.FIT_XY);
			if ( bm !=null&position==0) {
				image.setImageBitmap(bm);
			}else {
				ImageLoaderUtil.getInstance().setImagebyurl(imageUrlList.get(position).getPic(),image);
			}
			image.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (btnItemClickListener!=null) {
						btnItemClickListener.onItemClick(v, position);
					}
				}
			});
		// 给 container 添加一个view
		 ((ViewPager ) container).addView(image);  
		// 返回一个和该view相对的object
		 
		return image;
	}

	@Override
	/**
	 * 判断 view和object的对应关系 
	 */
	public boolean isViewFromObject(View view, Object object) {
			return view == object;
	}

	@Override
	/**
	 * 销毁对应位置上的object
	 */
	public void destroyItem(ViewGroup container, int position, Object object) {
		 ((ViewPager ) container).removeView((View) object);  
	}
}
