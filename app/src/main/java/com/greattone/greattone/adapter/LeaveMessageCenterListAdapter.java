package com.greattone.greattone.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.Listener.OnBtnItemClickListener;
import com.greattone.greattone.entity.LeaveMessage;
import com.greattone.greattone.entity.Level;
import com.greattone.greattone.util.ImageLoaderUtil;

public class LeaveMessageCenterListAdapter extends Adapter<LeaveMessage> {
	OnBtnItemClickListener btnItemClickListener;
	public LeaveMessageCenterListAdapter(Context context, List<LeaveMessage>mList,OnBtnItemClickListener btnItemClickListener) {
		super(context, mList, 	R.layout.adapter_leave_message);
		this. btnItemClickListener= btnItemClickListener;
	}

	@Override
	public void getview(ViewHolder holder, final int position, LeaveMessage salesChannels) {
		ImageView iv_icon= (ImageView) holder.getView(R.id.iv_icon);
		TextView tv_name= (TextView) holder.getView(R.id.tv_name);
		TextView tv_address= (TextView) holder.getView(R.id.tv_address);
		TextView tv_level= (TextView) holder.getView(R.id.tv_level);
		TextView tv_title= (TextView) holder.getView(R.id.tv_title);
		TextView tv_time= (TextView) holder.getView(R.id.tv_time);
		TextView tv_content= (TextView) holder.getView(R.id.tv_content);
		TextView tv_reply= (TextView) holder.getView(R.id.tv_reply);
		TextView btn_reply= (TextView) holder.getView(R.id.btn_reply);
		btn_reply.setVisibility(View.VISIBLE);
		tv_name.setText(salesChannels.getUsername());
		tv_address.setText(salesChannels.getIdentity());
		tv_level.setText(JSON.parseObject(salesChannels.getLevel(), Level.class).getName());
		tv_title.setText(salesChannels.getTitle());
		tv_time.setText(salesChannels.getNewstime());
		tv_content.setText(salesChannels.getText());
		tv_reply.setText(salesChannels.getReply());
		ImageLoaderUtil.getInstance().setImagebyurl(salesChannels.getUserpic(), iv_icon);
		btn_reply.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				btnItemClickListener.onItemClick(v, position);
			}
		});
	}



}
