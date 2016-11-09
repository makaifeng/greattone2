package com.greattone.greattone.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.entity.Blog;
import com.greattone.greattone.entity.ImageData;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.ImageLoaderUtil;

public class InterviewAdapter extends BaseAdapter {
	private Context context;
	private List<Blog> newsList;
	private int screenWidth;
	private List<ImageData> imageUrlList=new  ArrayList<ImageData>(); 
	private int type;

	public InterviewAdapter(Context context, List<Blog> newsList, List<ImageData> imageUrlList, int type) {
		this.context = context;
		this.newsList = newsList;
		if (imageUrlList!=null) {
			this.imageUrlList = imageUrlList;
		}
		this.type = type;
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
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.adapter_interview, group, false);
			holder.title = (TextView) convertView.findViewById(R.id.tv_title);//
			holder.time = (TextView) convertView.findViewById(R.id.tv_time);//
			holder.icon = (ImageView) convertView.findViewById(R.id.iv_pic);//
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					screenWidth/4+ DisplayUtil.dip2px(context, 20),
					screenWidth*3/4/ 5 + DisplayUtil.dip2px(context, 20));
			holder.icon.setLayoutParams(params);
			holder.iv_ad = (ImageView) convertView
					.findViewById(R.id.iv_ad);//
			LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
					screenWidth, screenWidth/3);
			holder.iv_ad.setLayoutParams(params2);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setPosition(position);
		return convertView;
	}

	class ViewHolder {
		TextView title;
		TextView time;
		ImageView icon;
		ImageView iv_ad;

		public void setPosition(int position) {
			iv_ad.setVisibility(View.GONE);
			ImageLoaderUtil.getInstance().setImagebyurl(
					newsList.get(position).getTitlepic(), icon);
			title.setText(newsList.get(position).getTitle());
			time.setText(newsList.get(position).getNewstime());
			if (position%3==2&&type==1&&imageUrlList.size()>0) {
				iv_ad.setVisibility(View.VISIBLE);
				ImageLoaderUtil.getInstance().setImagebyFile(imageUrlList.get((position/3)%imageUrlList.size()).getPic(), iv_ad);
			}
		}
	}

}
