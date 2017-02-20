package com.greattone.greattone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.entity.MallOrder;

import java.util.List;

public class MyMallOrderAdapter extends BaseAdapter {
	private Context context;
	private List<MallOrder> orderList;
	private int screenWidth;

	public MyMallOrderAdapter(Context context, List<MallOrder> orderList) {
		this.context = context;
		this.orderList = orderList;
		screenWidth = ((BaseActivity) context).screenWidth;
	}

	@Override
	public int getCount() {
//		return orderList.size();
		return 2;
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
					R.layout.adapter_my_mall_adapter, group, false);
			holder.name = (TextView) convertView.findViewById(R.id.tv_name);//
			holder.orderid = (TextView) convertView.findViewById(R.id.tv_orderid);//
			holder.state = (TextView) convertView.findViewById(R.id.tv_state);//
			holder.title = (TextView) convertView.findViewById(R.id.tv_title);//
			holder.price = (TextView) convertView.findViewById(R.id.tv_price);//
			holder.type = (TextView) convertView.findViewById(R.id.tv_type);//
			holder.total = (TextView) convertView.findViewById(R.id.tv_total);//
			holder.button = (TextView) convertView.findViewById(R.id.tv_button1);//
			holder.button2 = (TextView) convertView.findViewById(R.id.tv_button2);//
			holder.icon = (ImageView) convertView.findViewById(R.id.iv_pic);//
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setPosition(position);
		return convertView;
	}

	class ViewHolder {
		TextView name;
		TextView orderid;
		TextView state;
		TextView title;
		TextView type;
		TextView price;
		TextView total;
		TextView button;
		TextView button2;
		ImageView icon;

		public void setPosition(int position) {
//			ImageLoaderUtil.getInstance().setImagebyurl(
//					activitiesList.get(position).getTitlepic(), icon);
//			// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",
//			// Locale.CHINA);
//			name.setText(activitiesList.get(position).getTitle());
//			introduction.setText(activitiesList.get(position).getHuodong_1()+"-"+activitiesList.get(position).getHuodong_2()
//					);
//			address.setText(activitiesList.get(position).getDizhi());
		}
	}

}
