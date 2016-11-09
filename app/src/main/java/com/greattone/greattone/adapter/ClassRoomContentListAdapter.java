package com.greattone.greattone.adapter;

import java.util.List;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.entity.UserInfo;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.ImageLoaderUtil;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
@SuppressWarnings("deprecation")
public class ClassRoomContentListAdapter extends BaseAdapter {
	private Context context;
	private List<UserInfo> classRoomList;
	private int screenWidth;

	public ClassRoomContentListAdapter(Context context,
			List<UserInfo> classRoomList) {
		this.context = context;
		this.classRoomList = classRoomList;
		screenWidth = ((BaseActivity) context).screenWidth;
	}

	@Override
	public int getCount() {
		return classRoomList.size();
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
					R.layout.adapter_classroom_content, group, false);
			holder.name = (TextView) convertView.findViewById(R.id.tv_name);//
			holder.address = (TextView) convertView
					.findViewById(R.id.tv_address);//
			holder.telphone = (TextView) convertView
					.findViewById(R.id.tv_telphone);//
			holder.distance = (TextView) convertView
					.findViewById(R.id.tv_distance);//
			Drawable drawable = context.getResources().getDrawable(
					R.drawable.icon_jl);
			drawable.setBounds(0, 0, DisplayUtil.dip2px(context, 10),
					DisplayUtil.dip2px(context, 15));
			holder.distance.setCompoundDrawables(drawable, null, null, null);
			holder.distance.setCompoundDrawablePadding(DisplayUtil.dip2px(
					context, 5));
			holder.vip = (ImageView) convertView.findViewById(R.id.iv_vip);//
			holder.icon = (ImageView) convertView.findViewById(R.id.iv_icon);//
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					screenWidth * 2 / 7, screenWidth * 2 / 7 * 4 / 5);
			holder.icon.setLayoutParams(params);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setPosition(position);
		return convertView;
	}

	class ViewHolder {
		/** 教室姓名 */
		TextView name;
		/** 地址 */
		TextView address;
		/** 电话 */
		TextView telphone;
		/** 距离 */
		TextView distance;
		/** 头像 */
		ImageView icon;
		/** v符号 */
		ImageView vip;
		int position;
		UserInfo userInfo = new UserInfo();

		public void setPosition(int position) {
			this.position = position;
			if (classRoomList.get(position) != null) {
				userInfo = classRoomList.get(position);
				ImageLoaderUtil.getInstance().setImagebyurl(
						userInfo.getUserpic(), icon);
				name.setText(userInfo.getUsername());
				if (userInfo.getPhone() != null) {
					telphone.setText(context.getResources().getString(
							R.string.电话_hint)
							+ userInfo.getPhone());
				}
				if (userInfo.getAddress() != null) {
					address.setText(context.getResources().getString(
							R.string.地址_hint)
							+ userInfo.getAddress()
							+ userInfo.getAddress1()
							+ userInfo.getAddress2()	+ userInfo.getAddres());
				}
				distance.setText(userInfo.getDistance());
//				icon.setOnClickListener(lis);
				if (userInfo.getVerification() == 1) {
					vip.setVisibility(View.VISIBLE);
				} else {
					vip.setVisibility(View.GONE);
				}
			}
		}

//		OnClickListener lis = new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if (userInfo != null) {
//					Intent intent = new Intent();
//					intent.setClass(context, CelebrityActivity.class);
//					intent.putExtra("id", userInfo.getUserid() + "");
//					intent.putExtra("groupid", userInfo.getGroupid());
//					context.startActivity(intent);
//				}
//			}
//		};
	}

}
