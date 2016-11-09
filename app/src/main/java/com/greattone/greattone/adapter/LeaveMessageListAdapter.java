package com.greattone.greattone.adapter;

import java.util.List;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.entity.LeaveMessage;
import com.greattone.greattone.entity.Level;
import com.greattone.greattone.util.ImageLoaderUtil;

public class LeaveMessageListAdapter extends Adapter<LeaveMessage> {

	public LeaveMessageListAdapter(Context context, List<LeaveMessage>mList) {
		super(context, mList, 	R.layout.adapter_leave_message);
	}

	@Override
	public void getview(ViewHolder holder, int position, LeaveMessage salesChannels) {
		ImageView iv_icon= (ImageView) holder.getView(R.id.iv_icon);
		TextView tv_name= (TextView) holder.getView(R.id.tv_name);
		TextView tv_address= (TextView) holder.getView(R.id.tv_address);
		TextView tv_level= (TextView) holder.getView(R.id.tv_level);
		TextView tv_title= (TextView) holder.getView(R.id.tv_title);
		TextView tv_time= (TextView) holder.getView(R.id.tv_time);
		TextView tv_content= (TextView) holder.getView(R.id.tv_content);
		TextView tv_reply= (TextView) holder.getView(R.id.tv_reply);
		tv_name.setText(salesChannels.getUsername());
		tv_address.setText(salesChannels.getIdentity());
		tv_level.setText(JSON.parseObject(salesChannels.getLevel(), Level.class).getName());
		tv_title.setText(salesChannels.getTitle());
		tv_time.setText(salesChannels.getNewstime());
		tv_content.setText(salesChannels.getText());
		tv_reply.setText(salesChannels.getReply());
		ImageLoaderUtil.getInstance().setImagebyurl(salesChannels.getUserpic(), iv_icon);
	}



}
