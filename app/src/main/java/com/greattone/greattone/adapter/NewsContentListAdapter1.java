package com.greattone.greattone.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.entity.News;
import com.greattone.greattone.widget.MyGridView;

public class NewsContentListAdapter1 extends BaseAdapter {
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
	private Context context;
	private String texts[];
	private	List<News> newsList;
	private int pager_style[];
	
	public NewsContentListAdapter1(Context context,List<News> newsList) {
		this.context = context;
		this.newsList = newsList;
		this.pager_style = context.getResources().getIntArray(
				R.array.news_style);
		texts = context.getResources().getStringArray(R.array.news_subtitle);
	}

	@Override
	public int getCount() {
		return texts.length;
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
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.adapter_news_content1, group, false);
			holder.name = (TextView) convertView.findViewById(R.id.tv_title);// 名称
			holder.more = (TextView) convertView.findViewById(R.id.tv_more);// 更多
			holder.gridView = (MyGridView) convertView
					.findViewById(R.id.gv_content);// 内容
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setPosition(position);
		return convertView;
	}

	class ViewHolder {
		TextView name;
		TextView more;
		MyGridView gridView;

		public void setPosition(int position) {
			name.setText(texts[position]);
			NewsContentItemGridAdapter1 adapter=new NewsContentItemGridAdapter1(context, newsList,pager_style[position]);
			if (pager_style[position] == PAGER_STYLE_VIDEO) {// 视频样式
				gridView.setNumColumns(2);
			} else if (pager_style[position] == PAGER_STYLE_ONE_PICTURE) {// 单图样式
				gridView.setNumColumns(1);
			} else if (pager_style[position] == PAGER_STYLE_MORE_PICTURE) {// 多图样式
				gridView.setNumColumns(1);
			} else if (pager_style[position] == PAGER_STYLE_NO_PICTURE) {// 无图样式
				gridView.setNumColumns(1);
			} else {
			}
			gridView.setAdapter(adapter);
			
			more.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
				}
			});
		}
	}

}
