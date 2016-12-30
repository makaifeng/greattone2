package com.greattone.greattone.adapter;

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
import com.greattone.greattone.entity.HaiXuanZB;
import com.greattone.greattone.util.ImageLoaderUtil;

import java.util.List;

public class ZBContentListAdapter extends BaseAdapter {
	private Context context;
	private List<HaiXuanZB> ZBList;
	private int screenWidth;

	public ZBContentListAdapter(Context context,
			List<HaiXuanZB>ZBList) {
		this.context = context;
		this.ZBList = ZBList;
		screenWidth = ((BaseActivity) context).screenWidth;
	}

	@Override
	public int getCount() {
		return ZBList.size();
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
					R.layout.adapter_zb, group, false);
			holder.title = (TextView) convertView.findViewById(R.id.tv_name);//
			holder.name = (TextView) convertView
					.findViewById(R.id.tv_introduction);//
			holder.time = (TextView) convertView
					.findViewById(R.id.tv_text3);//
			holder.time.setVisibility(View.VISIBLE);
			holder.icon = (ImageView) convertView.findViewById(R.id.iv_icon);//
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//					screenWidth, screenWidth * 3 / 5);
					screenWidth, screenWidth );
			holder.icon.setLayoutParams(params);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setPosition(position);
		return convertView;
	}

	class ViewHolder {
		/** 教室姓名 */
		TextView title;
		/** 直播人 */
		TextView name;
		/** 时间 */
		TextView time;
		/** 头像 */
		ImageView icon;

		public void setPosition(int position) {
			ImageLoaderUtil.getInstance().setImagebyurl(
					ZBList.get(position).getTitlepic(), icon);
			title.setText(ZBList.get(position).getTitle());
			if (ZBList.get(position).getUsername()!=null) {
				name.setText("直播人：" + ZBList.get(position).getTitname());
			}
			time.setText("直播时间:"+ZBList.get(position).getTittime_start().substring(0, ZBList.get(position).getTittime_start().length()-3)+"至"+ZBList.get(position).getTittime_stop().substring(0, ZBList.get(position).getTittime_stop().length()-3));
		}
	}

}
