package com.greattone.greattone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.entity.Discuss;

import java.util.List;

public class DiscussListAdapter2 extends BaseAdapter {
	private Context context;
	private List<Discuss> list;

	public DiscussListAdapter2(Context context, List<Discuss> list) {
		this.list = list;
		this.context = context;
	}

	public int getCount() {
		return list.size();
	}

	public Object getItem(int position) {
		return this.list.get(position);
	}

	public long getItemId(int paramInt) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup group) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.adapter_discuss_list, group, false);
			holder.content = ((TextView) convertView
					.findViewById(R.id.tv_content));
			holder.name = ((TextView) convertView
					.findViewById(R.id.tv_name));
			holder.level = ((TextView) convertView
					.findViewById(R.id.tv_level));
			holder.time = ((TextView) convertView
					.findViewById(R.id.tv_time));
			holder.onclick = ((TextView) convertView
					.findViewById(R.id.tv_onclick));
			holder.icon = ((ImageView) convertView
					.findViewById(R.id.iv_icon));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.setPosition(position);
		return convertView;
	}

	private class ViewHolder {
		private TextView content;
		private TextView name;
		private TextView level;
		private TextView time;
		private TextView onclick;
		private ImageView icon;
   private  void setPosition(int position ){
		   name.setVisibility(View.GONE);
		   level.setVisibility(View.GONE);
		   time.setVisibility(View.GONE);
		   onclick.setVisibility(View.GONE);

	   content.setText(list.get(position).getTitle());
	   icon.setImageResource(R.drawable.icon_next);
   }
	}
}