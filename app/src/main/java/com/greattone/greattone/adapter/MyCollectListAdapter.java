package com.greattone.greattone.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.entity.Collection;
import com.greattone.greattone.entity.UserInfo;
import com.greattone.greattone.util.ImageLoaderUtil;

public class MyCollectListAdapter extends BaseAdapter {
	Context context;
	List<Collection> blogsList;
	int checkId;

	public MyCollectListAdapter(Context context, List<Collection> blogsList,
			int checkId) {
		this.context = context;
		this.blogsList = blogsList;
		this.checkId = checkId;
	}

	@Override
	public int getCount() {
		return blogsList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = getView(holder, parent);
			convertView.setTag(R.id.tv_title, holder);
			convertView.setTag(R.id.tv_content, checkId);
		} else {
			if (checkId == (Integer) convertView.getTag(R.id.tv_content)) {
				holder = (ViewHolder) convertView.getTag(R.id.tv_title);
			} else {
				holder = new ViewHolder();
				convertView = getView(holder, parent);
				convertView.setTag(R.id.tv_title, holder);
				convertView.setTag(R.id.tv_content, checkId);
			}
		}
		holder.setPosition(position);
		return convertView;
	}

	private View getView(ViewHolder holder, ViewGroup parent) {
		View view = null;
		if (checkId == 1) {
			view = LayoutInflater.from(context).inflate(
					R.layout.adapter_mycollect_video, parent, false);
		} else if (checkId == 2) {
			view = LayoutInflater.from(context).inflate(
					R.layout.adapter_center_photo, parent, false);
		} else if (checkId == 3) {
			view = LayoutInflater.from(context).inflate(
					R.layout.adapter_center_photo, parent, false);
		}
		initView(view, holder);
		return view;
	}

	private void initView(View view, ViewHolder holder) {
		holder.time = (TextView) view.findViewById(R.id.tv_time);
		holder.title = (TextView) view.findViewById(R.id.tv_title);
		holder.content = (TextView) view.findViewById(R.id.tv_content);
		holder.name = (TextView) view.findViewById(R.id.tv_name);
		holder.icon = (ImageView) view.findViewById(R.id.iv_icon);
		holder.plays = (TextView) view.findViewById(R.id.tv_plays);
		holder.comments = (TextView) view.findViewById(R.id.tv_comments);
	}

	class ViewHolder {
		TextView time;
		TextView name;
		ImageView icon;
		TextView title;
		TextView content;
		TextView plays;
		TextView comments;
		UserInfo info;

		private void setPosition(int position) {
//			info = JSON.parseArray(blogsList.get(position).getUserinfo(), UserInfo.class).get(0);
			info = blogsList.get(position).getUserinfo();
			title.setText(blogsList.get(position).getTitle());
			if (checkId == 1) {
				setVideoView(position);
			} else if (checkId == 2) {
				setMusicView(position);
			} else if (checkId == 3) {
				setPhotoView(position);
			}
		}

		public void setVideoView(int position) {
			ImageLoaderUtil.getInstance().setImagebyurl(
					blogsList.get(position).getTitlepic(), icon);
			String shengfen = null;
			if (info.getGroupid() == 1) {
				shengfen = info.getPutong_shenfen();
			} else if (info.getGroupid() == 2) {
				shengfen = info.getMusic_star();
			} else if (info.getGroupid() == 3) {
				shengfen = info.getTeacher_type();
			} else if (info.getGroupid() == 4) {
				shengfen = info.getClassroom_type();
			}
			if (TextUtils.isEmpty(shengfen)) {
				shengfen= info.getGroupname();
			}
			name.setText(info.getUsername() + "(" + shengfen + ")");
//			 plays.setText(	blogsList.get(position).getReprnum() + "");
			 comments.setText(blogsList.get(position).getPlnum() + "");
		}

		private void setMusicView(int position) {

		}

		private void setPhotoView(int position) {
			 ImageLoaderUtil.getInstance().setImagebyurl(
						blogsList.get(position).getTitlepic(),
			 icon);
//			 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",
//			 Locale.CHINA);
//			 time.setText(format.format(new Date(blogsList.get(position)
//			 .getCtime() * 1000)));
			 time.setText(blogsList.get(position).getNewstime());
			 content.setText(blogsList.get(position).getSmalltext());
		}
	}

}
