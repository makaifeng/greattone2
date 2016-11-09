package com.greattone.greattone.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.entity.Discuss;

public class DiscussListAdapter extends BaseAdapter {
	private Context context;
	private List<Discuss> list;

	public DiscussListAdapter(Context context, List<Discuss> list) {
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
			holder.title = ((TextView) convertView
					.findViewById(R.id.tv_name));
			holder.content = ((TextView) convertView
					.findViewById(R.id.tv_content));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (list.get(position).getContent()==null||list.get(position).getContent().equals("")) {
			holder.content.setVisibility(View.GONE);
		}else {
			holder.content.setVisibility(View.VISIBLE);
			holder.content.setText(list.get(position).getContent());
		}
		holder.title.setText(list.get(position).getTitle());
		if (position != 0) {
			holder.content.setVisibility(View.GONE);
		}
		return convertView;
	}

	private class ViewHolder {
		private TextView content;
		private TextView title;
		
	}
}