package com.greattone.greattone.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.celebrity.CelebrityActivity;
import com.greattone.greattone.activity.classroom.ClassRoomActivity;
import com.greattone.greattone.activity.teacher.TeacherActivity;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.UserInfo;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.util.MessageUtil;

import java.util.List;

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
		if (T.getIsfeed()==1){
			tv_add.setText("已关注");
		}else {
			tv_add.setText("关注");
		}
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
							if (T.getIsfeed()==1){
								mlist.get(position).setIsfeed(0);
							}else {
								mlist.get(position).setIsfeed(1);
							}
							notifyDataSetChanged();
							MyProgressDialog.Cancel();
						}

					}, null);
			}
		});
		iv_head.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				toCenter(T);
			}
		});
	}
	/** 跳转到个人中心 */
	protected void toCenter(UserInfo userInfo) {
		int group =userInfo
				.getGroupid();
		Intent intent = new Intent();
		if (group == 1 || group == 2) {// 普通会员和名人
			intent.setClass(context, CelebrityActivity.class);
			intent.putExtra("id", userInfo.getUserid() + "");
			intent.putExtra("groupid",userInfo.getGroupid());
		} else if (group == 3) {// 老师
			intent.setClass(context, TeacherActivity.class);
			intent.putExtra("id", userInfo.getUserid() + "");
		} else if (group == 4) {// 教室
			intent.setClass(context, ClassRoomActivity.class);
			intent.putExtra("id", userInfo.getUserid() + "");
		}
		context.startActivity(intent);
	}
}
