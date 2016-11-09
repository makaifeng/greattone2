package com.greattone.greattone.adapter;


import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.UserInfo;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.util.MessageUtil;

/**
 * 添加朋友
 * @author yff
 * 2015-9-2
 */ 
public class AddNewFriendAdapter extends Adapter<UserInfo>{

	public AddNewFriendAdapter(Context context,
			List<UserInfo> list) {
		super(context, list, R.layout.adapter_add_new_friend);
	 
	}

	@Override
	public void getview(ViewHolder vh, final int position, final UserInfo T) {
		ImageView iv_head = (ImageView) vh.getView(R.id.iv_add_head);
		ImageView iv_statue = (ImageView) vh.getView(R.id.iv_add_freidnd_approve);
		TextView tv_name = (TextView) vh.getView(R.id.tv_add_friend_name);
		TextView tv_statue = (TextView) vh.getView(R.id.tv_add_frend_statue);
		final TextView tv_add = (TextView) vh.getView(R.id.tv_add_freind);
		
		ImageLoaderUtil.getInstance().setImagebyurl(T.getUserpic(), iv_head);
		iv_statue.setVisibility(T.getVerification()==1 ? View.VISIBLE : View.INVISIBLE);
		tv_name.setText(T.getUsername());
		tv_statue.setText(MessageUtil.getIdentity(T));
		tv_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(T.getUserid().equals(Data.myinfo.getUserid())){
					((BaseActivity) context).toast(context.getResources().getString(R.string.不能关注自己));
					return;
				}
				HttpProxyUtil.addattention(context, T.getUserid(), new ResponseListener() {

						@Override
						public void setResponseHandle(Message2 message) {
							((BaseActivity) context).toast(message.getInfo());
							mlist.remove(position);
							notifyDataSetChanged();
							MyProgressDialog.Cancel();
						}

					}, null);
			}
		});
	}

}
