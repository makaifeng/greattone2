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

public class MyActivityGridAdapter extends BaseAdapter {
	private Context context;
	private List<Blog> competitionList;
	private int screenWidth;
	public MyActivityGridAdapter(Context context, List<Blog> competitionList) {
		this.context = context;
		this.competitionList = competitionList;
		screenWidth = ((BaseActivity) context).screenWidth;
	}

	@Override
	public int getCount() {
		return competitionList.size();
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
					R.layout.adapter_my_activity, group, false);
			holder.title = (TextView) convertView.findViewById(R.id.tv_title);//
			holder.descr = (TextView) convertView
					.findViewById(R.id.tv_descr);//
			holder.address = (TextView) convertView
					.findViewById(R.id.tv_address);//
			holder.icon = (ImageView) convertView.findViewById(R.id.iv_pic);//
					RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					((screenWidth )*3/10),
					(screenWidth ) * 3/10);
//					(screenWidth - DisplayUtil.dip2px(context, 10)),
//					(screenWidth - DisplayUtil.dip2px(context, 10)) * 3 / 5);
			holder.icon.setLayoutParams(params);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setPosition(position);
		return convertView;
	}

	class ViewHolder {
		TextView title;
		TextView descr;
		TextView address;
		ImageView icon;

		public void setPosition(int position) {
//			if (activitiesList.get(position).getPic().startsWith("http://")) {
//				ImageLoader.getInstance().displayImage(
//						activitiesList.get(position).getPic(), icon,
//						((BaseActivity) context).options);
//			} else {
//				ImageLoader.getInstance().displayImage(
//						new HttpConstants(Constants.isDebug).ServerUrl + "/"
//								+ activitiesList.get(position).getPic(), icon,
//						((BaseActivity) context).options);
//			}
			ImageLoaderUtil.getInstance().setImagebyurl(
					competitionList.get(position).getTitlepic(), icon);
//			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",
//					Locale.CHINA);
			title.setText(competitionList.get(position).getTitle());
			descr.setText(competitionList.get(position).getHuodong_1()+"-"+competitionList.get(position).getHuodong_2()
			);
			address.setText(competitionList.get(position).getDizhi());
		}
	}

}
