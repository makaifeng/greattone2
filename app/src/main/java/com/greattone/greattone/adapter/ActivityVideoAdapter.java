package com.greattone.greattone.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.entity.Blog;

public class ActivityVideoAdapter extends BaseAdapter {
	private Context context;
	private List<Blog> activitiesList;
//	private int screenWidth;

	public ActivityVideoAdapter(Context context, List<Blog> activitiesList) {
		this.context = context;
		this.activitiesList = activitiesList;
//		screenWidth = ((BaseActivity) context).screenWidth;
	}

	@Override
	public int getCount() {
		return activitiesList.size();
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
					R.layout.adapter_activity_video, group, false);
			holder.name = (TextView) convertView.findViewById(R.id.adapter_act_video_name);//
			holder.icon = (ImageView) convertView.findViewById(R.id.adapter_act_video_pic);//
//			LayoutParams params = new LayoutParams(
//					(screenWidth - DisplayUtil.dip2px(context, 10)) / 2,
//					(screenWidth - DisplayUtil.dip2px(context, 10)) / 2 * 5 / 3);
//			holder.icon.setLayoutParams(params);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setPosition(position);
		return convertView;
	}

	class ViewHolder {
		TextView name;
		ImageView icon;

		public void setPosition(int position) {
//			ImageLoaderUtil.getInstance().setImagebyurl(
//					activitiesList.get(position).getTitlepic(), icon);
//			name.setText(activitiesList.get(position).getTitle());
		}
	}

}
