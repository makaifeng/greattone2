package com.greattone.greattone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.entity.UserInfo;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.util.MessageUtil;

import java.util.List;

public class ClassRoomTeactherGridAdapter extends BaseAdapter {
	private Context context;
	private List<UserInfo> userList;
	private int screenWidth;

	public ClassRoomTeactherGridAdapter(Context context, List<UserInfo> userList) {
		this.context = context;
		this.userList = userList;
		screenWidth = ((BaseActivity) context).screenWidth;
	}

	@Override
	public int getCount() {
		return userList.size();
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
					R.layout.adapter_celebrity_content, group, false);
			holder.name = (TextView) convertView.findViewById(R.id.tv_name);//
			holder.identity = (TextView) convertView.findViewById(R.id.tv_identity);//
			holder.fans = (TextView) convertView.findViewById(R.id.tv_fans);//
//			holder.level = (ImageView) convertView.findViewById(R.id.iv_level);//
			holder.level = (TextView) convertView.findViewById(R.id.tv_level);//
			holder.vip = (ImageView) convertView.findViewById(R.id.iv_vip);//
			holder.icon = (ImageView) convertView.findViewById(R.id.iv_icon);//
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					screenWidth/5+DisplayUtil.dip2px(context, 20), screenWidth / 5+DisplayUtil.dip2px(context, 20));
			holder.icon.setLayoutParams(params);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setPosition(position);
		return convertView;
	}

	class ViewHolder {
		/**
		 * 姓名
		 */
		TextView name;
		/**
		 * 身份   
		 */
		TextView identity;
		/**
		 * 粉丝数
		 */
		TextView fans;
		/**
		 *  等级
		 */
//		ImageView level;
		TextView level;
		/**
		 * 头像
		 */
		ImageView icon;
		/**vip图标*/
		ImageView vip;

		public void setPosition(int position) {
			ImageLoaderUtil.getInstance().setImagebyurl(
					userList.get(position).getUserpic(), icon);
			name.setText(userList.get(position).getUsername());
			//粉丝数
			fans.setText(context.getResources().getString(R.string.粉丝数_hint)+userList.get(position).getFollownum());
			//身份
			identity.setText(context.getResources().getString(R.string.identity)+ MessageUtil.getIdentity(userList.get(position)));
			//等级
			level.setText(context.getResources().getString(R.string.level_hint)+userList.get(position).getLevel().getName());
//			ImageLoaderUtil.getInstance().setImagebyurl(
//					userList.get(position).getLevel().getPic(), level);
			
			if (userList.get(position).getVerification()==1) {
				vip.setVisibility(View.VISIBLE);
			}else {
				vip.setVisibility(View.GONE);
			}
		}
	}

}
