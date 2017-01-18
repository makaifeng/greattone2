package com.greattone.greattone.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.entity.UserInfo;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.widget.MyRoundImageView;

import java.util.List;

public class TeacherContentListAdapter extends BaseAdapter {
	private BaseActivity activity;
	private List<UserInfo> teacherList;
	private int screenWidth;

	public TeacherContentListAdapter(BaseActivity activity, List<UserInfo> teacherList) {
		this.activity = activity;
		this.teacherList = teacherList;
		screenWidth =activity.screenWidth;
	}

	@Override
	public int getCount() {
		return teacherList.size();
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
			convertView = LayoutInflater.from(activity).inflate(
					R.layout.adapter_musicteacher2, group, false);
			holder.name = (TextView) convertView.findViewById(R.id.tv_name);//
			holder.identity = (TextView) convertView
					.findViewById(R.id.tv_identity);//
			holder.tv_fans = (TextView) convertView
					.findViewById(R.id.tv_fans);//
//			holder.level = (TextView) convertView.findViewById(R.id.tv_level);//
//			holder.tv_isvip = (TextView) convertView.findViewById(R.id.tv_isvip);//
			holder.iv_isvip = (ImageView) convertView.findViewById(R.id.iv_isvip);//
			holder.icon = (MyRoundImageView) convertView.findViewById(R.id.iv_icon);//
			LayoutParams params =(LayoutParams) holder.icon.getLayoutParams();
			params.width=screenWidth /2-DisplayUtil.dip2px(activity, 5);
			params.height=(screenWidth /2-DisplayUtil.dip2px(activity, 5));
			holder.icon.setLayoutParams(params);
			holder.icon.setRadius(DisplayUtil.dip2px(activity, 15));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setPosition(position);
		return convertView;
	}

	class ViewHolder {
		/**
		 * 老师姓名
		 */
		TextView name;
		/**
		 * 身份 **老师
		 */
		TextView identity;
		TextView tv_fans;
//		/**
//		 * 等级
//		 */
//		TextView level;
		/**
		 * 头像
		 */
		MyRoundImageView icon;
		/**
		 * 会员
		 */
//		TextView tv_isvip;
		ImageView iv_isvip;
int position;
		public void setPosition(int position) {
			this.position=position;
//			introduction2.setText(teacherList.get(position).getGroupname());
			ImageLoaderUtil.getInstance().setImagebyurl(
					teacherList.get(position).getUserpic(), icon);
			if (teacherList.get(position).getVerification()==1) {
				iv_isvip.setVisibility(View.VISIBLE);
//				tv_isvip.setVisibility(View.VISIBLE);
			}else {
//				tv_isvip.setVisibility(View.GONE);
				iv_isvip.setVisibility(View.GONE);
			}
//			level.setText(teacherList.get(position).getLevel().getName());
			identity.setText(getIdentity(teacherList.get(position)));
			name.setText(teacherList.get(position).getUsername());
			tv_fans.setText("粉丝数："+teacherList.get(position).getFollownum());
//			icon.setOnClickListener(lis);
		}
//		OnClickListener lis=new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent();
//				intent.setClass(context, CelebrityActivity.class);
//				intent.putExtra("id", teacherList.get(position).getUserid() + "");
//				intent.putExtra("groupid", teacherList.get(position).getGroupid());
//			context.startActivity(intent);
//			}
//		};

		private String getIdentity(UserInfo info) {
			String identity;
			if (info.getTeacher_type() != null
					&& info.getTeacher_type().length() > 1) {
				identity = info.getTeacher_type();
			} else {
				identity = info.getGroupname();
			}
			return identity;

		}
	}

}
