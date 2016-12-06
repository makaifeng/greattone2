package com.greattone.greattone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.entity.Blog;
import com.greattone.greattone.util.ImageLoaderUtil;

import java.util.List;

public class ActivitiesGridAdapter extends BaseAdapter {
	private Context context;
	private List<Blog> activitiesList;
	private int screenWidth;

	public ActivitiesGridAdapter(Context context, List<Blog> activitiesList) {
		this.context = context;
		this.activitiesList = activitiesList;
		screenWidth = ((BaseActivity) context).screenWidth;
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
					R.layout.adapter_activities, group, false);
			holder.name = (TextView) convertView.findViewById(R.id.tv_name);//
			holder.introduction = (TextView) convertView
					.findViewById(R.id.tv_introduction);//
			holder.address = (TextView) convertView
					.findViewById(R.id.tv_text3);//
			holder.icon = (ImageView) convertView.findViewById(R.id.iv_icon);//
			LayoutParams params = new LayoutParams(
					((screenWidth )* 2 / 5),
					(screenWidth ) * 3* 2 / 5 / 4);
			holder.icon.setLayoutParams(params);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setPosition(position);
		return convertView;
	}

	class ViewHolder {
		TextView name;
		TextView introduction;
		TextView address;
		ImageView icon;

		public void setPosition(int position) {
			// if (activitiesList.get(position).getPic().startsWith("http://"))
			// {
			// ImageLoader.getInstance().displayImage(
			// activitiesList.get(position).getPic(), icon,
			// ((BaseActivity) context).options);
			// } else {
			// ImageLoader.getInstance().displayImage(
			// new HttpConstants(Constants.isDebug).ServerUrl + "/"
			// + activitiesList.get(position).getPic(), icon,
			// ((BaseActivity) context).options);
			// }
			ImageLoaderUtil.getInstance().setImagebyurl(
					activitiesList.get(position).getTitlepic(), icon);
			// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",
			// Locale.CHINA);
			name.setText(activitiesList.get(position).getTitle());
			introduction.setText(activitiesList.get(position).getHuodong_1()+"-"+activitiesList.get(position).getHuodong_2()
					);
			address.setText(activitiesList.get(position).getDizhi());
		}
	}

}
