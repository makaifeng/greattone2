package com.greattone.greattone.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;



public class MyEmojiView extends ViewPager {
	List<Integer> resIdList = new ArrayList<Integer>();
	List<GridView> gridList = new ArrayList<GridView>();

	int page = 1;
	int pageIndex = 18;
	private onExpressionItemClicklistener expressionItemClicklistener;

	public MyEmojiView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MyEmojiView(Context context) {
		super(context);
		init();
	}

	private void init() {
		for (int i = 0; i < 105; i++) {
			String resName = "expression_" +( i+1);
			resIdList.add(getDrawableId(resName));
		}
		LayoutParams layoutParams=new LayoutParams();
		layoutParams.width= LayoutParams.MATCH_PARENT;
		layoutParams.height=dip2px(40*3+15*2);
		setLayoutParams(layoutParams);
		for (int i = 0; i < (105 / pageIndex); i++) {
			final int page=i;
			GridView gridView = new GridView(getContext());
			gridView.setLayoutParams(layoutParams);
			gridView.setGravity(Gravity.CENTER);
			gridView.setNumColumns(6);
			gridView.setVerticalSpacing(15);
			gridView.setHorizontalSpacing(15);
			gridView.setBackgroundColor(Color.rgb(255, 255, 255));
			gridView.setAdapter(new GridViewAdapter(i));
			gridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					if (expressionItemClicklistener != null) {
						expressionItemClicklistener
								.onExpressionItemClick("expression_" + (page
										* pageIndex + position+1));
					}
				}
			});
			gridList.add(gridView);
		}

		setAdapter(new PagerAdapter() {

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView(gridList.get(position));
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0==arg1;
			}

			@Override
			public int getCount() {
				return gridList.size();
			}
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				container.addView(gridList.get(position));
				return gridList.get(position);
			}
		});
	}
	

	public int getDrawableId(String resName) {
		return getContext().getResources().getIdentifier(resName, "drawable",
				getContext().getPackageName());
	}

	class GridViewAdapter extends BaseAdapter {
		int page;

		public GridViewAdapter(int page) {
			this.page = page;
		}

		@Override
		public int getCount() {
			return pageIndex;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ImageView imageView = new ImageView(getContext());
			GridView.LayoutParams params = new GridView.LayoutParams(
					dip2px(40), dip2px(40));
			imageView.setLayoutParams(params);
			imageView.setImageResource(resIdList.get(page*pageIndex+position));
			return imageView;
		}

	}

	public void setOnExpressionItemClicklistener(
			onExpressionItemClicklistener expressionItemClicklistener) {
		this.expressionItemClicklistener = expressionItemClicklistener;
	}

	public interface onExpressionItemClicklistener {
		void onExpressionItemClick(String resName);
	}

	/**
	 * 将dip或dp值转换为px值，保证尺寸大小不变
	 * 
	 * @param dipValue
	 * @return
	 */
	public int dip2px(float dipValue) {
		final float density = getContext().getResources().getDisplayMetrics().density;
		return (int) (dipValue * density + 0.5f);
	}
}
