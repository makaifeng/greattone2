package com.greattone.greattone.adapter;

import java.util.List;

import com.greattone.greattone.R;
import com.greattone.greattone.entity.ConnectWay;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class ConnectWayListAdapter extends Adapter<ConnectWay> {

	public ConnectWayListAdapter(Context context, List<ConnectWay>mList) {
		super(context, mList, 	R.layout.adapter_connect_way);
	}
	@Override
	public int getViewTypeCount() {
		return 2;
	}
	 @Override
     public int getItemViewType(int position) {
		 if (position==0) {
			 return 0;
		}
         return 1;
     }
	@Override
	public void getview(ViewHolder holder, int position, ConnectWay salesChannels) {
		TextView tv_head= (TextView) holder.getView(R.id.tv_head);
		TextView tv_title= (TextView) holder.getView(R.id.tv_title);
		TextView tv_phone= (TextView) holder.getView(R.id.tv_phone);
		TextView tv_email= (TextView) holder.getView(R.id.tv_email);
		TextView tv_url= (TextView) holder.getView(R.id.tv_url);
		View ll_url= (View) holder.getView(R.id.ll_url);
		TextView tv_address= (TextView) holder.getView(R.id.tv_address);
		tv_head.setVisibility(View.GONE);
		ll_url.setVisibility(View.GONE);
		if (position==0) {
			tv_head.setVisibility(View.VISIBLE);
			ll_url.setVisibility(View.VISIBLE);
			tv_url.setText(salesChannels.getUrl());
			tv_head.setText("总部");
		}else if (position==1){
			tv_head.setVisibility(View.VISIBLE);
			ll_url.setVisibility(View.GONE);
			tv_head.setText("分支机构");
		}
		tv_title.setText(salesChannels.getTitle());
		tv_email.setText(salesChannels.getMail());
		tv_address.setText(salesChannels.getAddress());
		tv_phone.setText(salesChannels.getPhone());
	}



}
