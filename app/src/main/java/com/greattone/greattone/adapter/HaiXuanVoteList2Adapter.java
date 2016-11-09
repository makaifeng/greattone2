package com.greattone.greattone.adapter;

import java.util.List;

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
import com.greattone.greattone.entity.ActivityVideo;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.ImageLoaderUtil;
/**
 * 海选投票列表-乐器品牌
 *
 */
public class HaiXuanVoteList2Adapter extends BaseAdapter {
	private Context context;
	private List<ActivityVideo> activitiesList;
	private int screenWidth;

	public HaiXuanVoteList2Adapter(Context context, List<ActivityVideo> activitiesList) {
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
					R.layout.adapter_video, group, false);
			holder.name = (TextView) convertView.findViewById(R.id.tv_name);//
			holder.title = (TextView) convertView.findViewById(R.id.tv_title);//
			holder.plays = (TextView) convertView.findViewById(R.id.tv_plays);//
			holder.commnum = (TextView) convertView.findViewById(R.id.tv_commnum);//
			holder.vote_num = (TextView) convertView.findViewById(R.id.tv_vote_num);//
			holder.icon = (ImageView) convertView.findViewById(R.id.iv_icon);//
			holder.iv_play = (ImageView) convertView.findViewById(R.id.iv_play);//
			LayoutParams params = new LayoutParams(
					(screenWidth - DisplayUtil.dip2px(context, 10)) / 2,
					(screenWidth - DisplayUtil.dip2px(context, 10)) / 2 * 5 / 3);
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
		TextView title;
		TextView vote_num;
		TextView commnum;
		TextView plays;
		ImageView icon;
		ImageView iv_play;

		public void setPosition(int position) {
			iv_play.setVisibility(View.VISIBLE);
			ImageLoaderUtil.getInstance().setImagebyurl(
					activitiesList.get(position).getHai_photo(), icon);
//			name.setText(activitiesList.get(position).getHai_petition());
			name.setVisibility(View.INVISIBLE);
			title.setText(activitiesList.get(position).getHai_name());
			vote_num.setText(activitiesList.get(position).getTou_num()+context.getResources().getString(R.string.票));
			plays.setText(activitiesList.get(position).getOnclick());
			commnum.setText(activitiesList.get(position).getPlnum());
			if (activitiesList.get(position).getClassid().equals("104")) {
				iv_play.setVisibility(View.GONE);
			}
		}
	}

}
