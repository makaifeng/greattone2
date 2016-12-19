package com.greattone.greattone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.entity.Blog;
import com.greattone.greattone.util.ImageLoaderUtil;

import java.util.List;

public class HaiXuanListAdapter2 extends BaseAdapter {
	private Context context;
	private List<Blog> activitiesList;
	private int screenWidth;

	public HaiXuanListAdapter2(Context context, List<Blog> activitiesList) {
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
					R.layout.adapter_haixuan, group, false);
			holder.name = (TextView) convertView.findViewById(R.id.tv_name);//
			holder.time = (TextView) convertView
					.findViewById(R.id.tv_time);//
			holder.address = (TextView) convertView
					.findViewById(R.id.tv_address);//
			holder.address.setVisibility(View.GONE);
			holder.icon = (ImageView) convertView.findViewById(R.id.iv_icon);//
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					(screenWidth/5),
					screenWidth/5);
//					(screenWidth/4),
//					screenWidth*3/4/4);
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
		TextView time;
		TextView address;
		ImageView icon;

		public void setPosition(int position) {
			ImageLoaderUtil.getInstance().setImagebyurl(
					activitiesList.get(position).getTitlepic(), icon);
			name.setText(activitiesList.get(position).getTitle());
			time.setText("时间："+activitiesList.get(position).getHuodong_1()+"-"+activitiesList.get(position).getHuodong_2());
			address.setText("地点："+activitiesList.get(position).getDizhi());
		}
	}

}
