package com.greattone.greattone.adapter;

import java.util.List;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.chat.FaceImageDeal;
import com.greattone.greattone.entity.Comment;
import com.greattone.greattone.util.ImageLoaderUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentDetailsAdapter extends BaseAdapter {
	private Context context;
	private List<Comment> activitiesList;

	public CommentDetailsAdapter(Context context, List<Comment> activitiesList) {
		this.context = context;
		this.activitiesList = activitiesList;
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
					R.layout.adapter_video_details, group, false);
			holder.name = (TextView) convertView.findViewById(R.id.adapter_video_details_name);//
			holder.content = (TextView) convertView.findViewById(R.id.adapter_video_details_content);//
			holder.time = (TextView) convertView.findViewById(R.id.adapter_video_details_time);//
			holder.replay = (TextView) convertView.findViewById(R.id.adapter_video_details_replay);//
			holder.icon = (ImageView) convertView.findViewById(R.id.adapter_video_details_pic);//
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
		TextView content;
		TextView time;
		TextView replay;
		ImageView icon;

		public void setPosition(int position) {
			ImageLoaderUtil.getInstance().setImagebyurl(
					activitiesList.get(position).getUserpic(), icon);
			name.setText(activitiesList.get(position).getPlusername());
			content.setText( FaceImageDeal.changeString(context,activitiesList.get(position).getSaytext(),true));
			time.setText(activitiesList.get(position).getSaytime());
//			replay.setText(activitiesList.get(position).getPlstep()+"");
		}
	}

}
