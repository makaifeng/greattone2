package com.greattone.greattone.adapter;

import java.util.List;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.entity.UserInfo;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.widget.MyRoundImageView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
@SuppressWarnings("deprecation")
public class BrandTypeGirdAdapter extends BaseAdapter {
	private Context context;
	private List<UserInfo> classRoomList;
	private int screenWidth;

	public BrandTypeGirdAdapter(Context context,
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
					R.layout.adapter_brand_type, group, false);
			holder.name = (TextView) convertView.findViewById(R.id.tv_name);//
			holder.address = (TextView) convertView
					.findViewById(R.id.tv_address);//
			holder.address.setSingleLine();
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
//			holder.vip = (ImageView) convertView.findViewById(R.id.iv_vip);//
			holder.icon = (MyRoundImageView) convertView.findViewById(R.id.iv_icon);//
			holder.icon.setRadius(DisplayUtil.dip2px(context, 15));
//			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//					screenWidth * 2 , screenWidth * 2* 4 / 5);
			LayoutParams params =(LayoutParams) holder.icon.getLayoutParams();
			params.width=screenWidth /2-DisplayUtil.dip2px(context, 5);
			params.height=(screenWidth /2-DisplayUtil.dip2px(context, 5));
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
		MyRoundImageView icon;
//		/** v符号 */
//		ImageView vip;
		int position;
		UserInfo userInfo = new UserInfo();

		public void setPosition(int position) {
			this.position = position;
			distance.setVisibility(View.GONE);
			if (classRoomList.get(position) != null) {
				userInfo = classRoomList.get(position);
				ImageLoaderUtil.getInstance().setImagebyurl(
						userInfo.getUserpic(), icon);
				name.setText(userInfo.getUsername());
		
				if (userInfo.getPhone() != null) {
					telphone.setText("咨询电话："
							+ userInfo.getPhone());
				}
				if (userInfo.getCompany() != null) {
					address.setText("创立国家："+userInfo.getCompany());
				}else{
					address.setText("创立国家：");
				}
				distance.setText(userInfo.getDistance());
//				icon.setOnClickListener(lis);
				if (userInfo.getCked()== 1) {
					Drawable right=context.getResources().getDrawable(R.drawable.icon_v);
					right.setBounds(0, 0, DisplayUtil.dip2px(context, 15), DisplayUtil.dip2px(context, 15));
					name.setCompoundDrawablePadding( DisplayUtil.dip2px(context, 5));
					name.setCompoundDrawables(null, null, right, null);
				} else {
					name.setCompoundDrawables(null, null, null, null);
				}
			}
		}

	}

}
