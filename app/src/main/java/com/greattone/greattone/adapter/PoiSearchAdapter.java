package com.greattone.greattone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.greattone.greattone.R;

import java.util.ArrayList;
import java.util.List;

public class PoiSearchAdapter extends BaseAdapter {
	private Context context;
	private ViewHolder holder;
	private List<PoiItem> list = new ArrayList<PoiItem>();

	public PoiSearchAdapter(Context context, List<PoiItem> list) {
		this.context = context;
		this.list = list;
	}

	public void addAll(List<PoiItem> list) {
		if (list != null){
			for (PoiItem poiInfo : list) {
				list.add(poiInfo);
			}
			notifyDataSetChanged();
		}
	}

	public void addObject(List<PoiItem> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return this.list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup group) {
		if (convertView == null) {
			this.holder = new ViewHolder();
			convertView = LayoutInflater.from(this.context).inflate(R.layout.activity_poi_search_item,
					group,false);
			this.holder.mpoi_name = ((TextView) convertView
					.findViewById(R.id.tv_name));
			this.holder.mpoi_address = ((TextView) convertView
					.findViewById(R.id.tv_address));
			convertView.setTag(this.holder);
		}else {
			this.holder = ((ViewHolder) convertView.getTag());
		}
			this.holder.mpoi_name
					.setText(((PoiItem) this.list.get(position)).getAdName());
			this.holder.mpoi_address
					.setText(((PoiItem) this.list.get(position)).getAdName());
		return convertView;
	}

	public class ViewHolder {
		public TextView mpoi_address;
		public TextView mpoi_name;

		public ViewHolder() {
		}
	}
}
