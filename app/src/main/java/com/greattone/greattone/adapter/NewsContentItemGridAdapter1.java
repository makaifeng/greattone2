package com.greattone.greattone.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.entity.News;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.ImageLoaderUtil;

public class NewsContentItemGridAdapter1 extends BaseAdapter {
	/**
	 * 视频样式
	 */
	private final int PAGER_STYLE_VIDEO = 1;
	/**
	 * 单图样式
	 */
	private final int PAGER_STYLE_ONE_PICTURE = 2;
	/**
	 * 多图样式
	 */
	private final int PAGER_STYLE_MORE_PICTURE = 3;
	/**
	 * 无图样式
	 */
	private final int PAGER_STYLE_NO_PICTURE = 4;
	private int pager_style;
	private Context context;
	private int screenWidth;
	private List<News> newsList;

	public NewsContentItemGridAdapter1(Context context, List<News> newsList,
			int pager_style) {
		this.context = context;
		this.newsList = newsList;
		this.pager_style = pager_style;
		screenWidth = ((BaseActivity) context).screenWidth;

	}

	@Override
	public int getCount() {
		return newsList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup group) {
		ViewHolder holder = null;

		holder = new ViewHolder();
		int viewId = getViewId(position);
		convertView = LayoutInflater.from(context)
				.inflate(viewId, group, false);
		initView(position, convertView, holder);
		// holder.setPosition(position);
		return convertView;
	}

	private void initView(int position, View convertView, ViewHolder holder) {
		if (pager_style == PAGER_STYLE_VIDEO) {// 视频样式
			initVideoView(position, convertView, holder);
		} else if (pager_style == PAGER_STYLE_ONE_PICTURE) {// 单图样式
			initOnePictureView(position, convertView, holder);
//			 } else if (pager_style == PAGER_STYLE_MORE_PICTURE) {//多图样式
			// initMorePictureView(position, convertView, holder);
		} else if (pager_style == PAGER_STYLE_NO_PICTURE) {// 无图样式
			initNoPictureView(position, convertView, holder);
		} else {
			initNoPictureView(position, convertView, holder);
		}
	}

	/**
	 * 视频样式
	 * 
	 * @param position
	 * @param convertView
	 * @param holder
	 */
	private void initVideoView(int position, View convertView, ViewHolder holder) {
		holder.title = (TextView) convertView.findViewById(R.id.tv_title);//
		holder.icon = (ImageView) convertView.findViewById(R.id.iv_icon);//
		int imageWidth = screenWidth / 2 - DisplayUtil.dip2px(context, 10);
		LayoutParams params = new LinearLayout.LayoutParams(imageWidth,
				imageWidth * 3 / 5);
		holder.icon.setLayoutParams(params);
		ImageLoaderUtil.getInstance().setImagebyurl(
				newsList.get(position).getImageUrl(), holder.icon);
		holder.title.setText(newsList.get(position).getTitle());
	}

	/**
	 * 单图样式
	 * 
	 * @param position
	 * @param convertView
	 * @param holder
	 */
	private void initOnePictureView(int position, View convertView,
			ViewHolder holder) {
		holder.title = (TextView) convertView.findViewById(R.id.tv_title);//
		holder.content = (TextView) convertView.findViewById(R.id.tv_content);//
		holder.icon = (ImageView) convertView.findViewById(R.id.iv_icon);//
		int imageWidth = screenWidth * 2 / 5;
		LayoutParams params = new RelativeLayout.LayoutParams(imageWidth,
				imageWidth * 3 / 5);
		holder.icon.setLayoutParams(params);

		ImageLoaderUtil.getInstance().setImagebyurl(
				newsList.get(position).getImageUrl(), holder.icon);
		holder.title.setText(newsList.get(position).getTitle());
		holder.content.setText(newsList.get(position).getContent());
	}

//	/**
//	 * 多图样式
//	 * 
//	 * @param position
//	 * @param convertView
//	 * @param holder
//	 */
//	private void initMorePictureView(int position, View convertView,
//			ViewHolder holder) {
//
//	}

	/**
	 * 无图样式
	 * 
	 * @param position
	 * @param convertView
	 * @param holder
	 */
	private void initNoPictureView(int position, View convertView,
			ViewHolder holder) {
		holder.title = (TextView) convertView.findViewById(R.id.tv_title);//
		holder.title.setText(newsList.get(position).getTitle());
	}

	private int getViewId(int position) {
		if (pager_style == PAGER_STYLE_VIDEO) {// 视频样式
			return R.layout.adapter_news_content_item1;
		} else if (pager_style == PAGER_STYLE_ONE_PICTURE) {// 单图样式
			return R.layout.adapter_news_content_item2;
		} else if (pager_style == PAGER_STYLE_MORE_PICTURE) {// 多图样式
			// return R.layout.adapter_news_content_item3;
		} else if (pager_style == PAGER_STYLE_NO_PICTURE) {// 无图样式
			 return R.layout.adapter_news_content_item4;
		}
		return R.layout.adapter_news_content_item4;
	}

	class ViewHolder {
		TextView title;
		TextView content;
		ImageView icon;
	}

}
