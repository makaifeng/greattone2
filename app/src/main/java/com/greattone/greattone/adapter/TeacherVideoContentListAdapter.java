package com.greattone.greattone.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.entity.Video;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.ImageLoaderUtil;

public class TeacherVideoContentListAdapter extends BaseAdapter {
	private Context context;
	private List<Video> videoList;
	private int screenWidth;

	public TeacherVideoContentListAdapter(Context context, List<Video> videoList) {
		this.context = context;
		this.videoList = videoList;
		screenWidth = ((BaseActivity) context).screenWidth;
	}

	@Override
	public int getCount() {
		return videoList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup group) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.adapter_teacher_video_content, group, false);
			holder.icon = (ImageView) convertView.findViewById(R.id.iv_icon);//
			holder.rl = (RelativeLayout) convertView.findViewById(R.id.rl_icon);//
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					screenWidth * 2 / 5, screenWidth * 2 * 3 / 5 / 5);
			holder.rl.setLayoutParams(params);
			holder.title = (TextView) convertView.findViewById(R.id.tv_title);//
			holder.name = (TextView) convertView.findViewById(R.id.tv_name);//

			holder.plays = (TextView) convertView.findViewById(R.id.tv_plays);//
			Drawable drawable = context.getResources().getDrawable(
					R.drawable.icon_bf);
			drawable.setBounds(0, 0, DisplayUtil.dip2px(context, 15),
					DisplayUtil.dip2px(context, 15));
			holder.plays.setCompoundDrawables(drawable, null, null, null);
			holder.plays.setCompoundDrawablePadding(DisplayUtil.dip2px(context, 10));
			holder.commets = (TextView) convertView
					.findViewById(R.id.tv_commets);//
			Drawable drawable2 = context.getResources().getDrawable(
					R.drawable.icon_pl);
			drawable2.setBounds(0, 0, DisplayUtil.dip2px(context, 15),
					DisplayUtil.dip2px(context, 15));
			holder.commets.setCompoundDrawables(drawable2, null, null, null);
			holder.commets.setCompoundDrawablePadding(DisplayUtil.dip2px(context, 10));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setPosition(position);
		return convertView;
	}

	class ViewHolder {
		/** 标题 */
		TextView title;
		/** 名字 */
		TextView name;
		/** 视频播放数 */
		TextView plays;
		/** 评论次数 */
		TextView commets;
		/** 图片 */
		ImageView icon;
		/** 播放按钮 */
		RelativeLayout rl;

		public void setPosition(final int position) {
			title.setText(videoList.get(position).getTitle());
			name.setText(videoList.get(position).getUserInfo().getUsername());
			plays.setText(videoList.get(position).getOnclick()+"");
			commets.setText(videoList.get(position).getPlnum()+"");
			ImageLoaderUtil.getInstance().setImagebyurl(
					videoList.get(position).getTitlepic(), icon);
//			rl.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					Intent intent = new Intent(context, VideoPlayActivity.class);
//					intent.putExtra("url", videoList.get(position).getVideourl());
//					context.startActivity(intent);
//				}
//			});
		}
	}

}
